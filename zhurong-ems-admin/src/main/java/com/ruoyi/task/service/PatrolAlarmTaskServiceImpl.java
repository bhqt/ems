package com.ruoyi.task.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.autoee.patrolAlarm.domain.PatrolAlarm;
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapper;
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapperExtend;
import com.ruoyi.autoee.patrolAlarm.service.impl.PatrolAlarmServiceExtend;
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapper;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;
import com.ruoyi.autoee.patrolPlan.mapper.PatrolPlanMapper;
import com.ruoyi.autoee.patrolRecord.domain.PatrolRecord;
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapper;
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapper;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 巡更报警Service业务层处理
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolAlarmTaskServiceImpl implements IPatrolAlarmTaskService {
    private static final Logger logger = LoggerFactory.getLogger(PatrolAlarmTaskServiceImpl.class);
    @Autowired
    private PatrolAlarmMapper patrolAlarmMapper;
    @Autowired
    private PatrolAlarmMapperExtend patrolAlarmMapperExtend;
    @Autowired
    private PatrolAlarmServiceExtend patrolAlarmServiceExtend;
    @Autowired
    protected Validator validator;
    @Autowired
    private ISysDictDataService iSysDictDataService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private PatrolPlanMapper patrolPlanMapper; // 巡更计划Mapper
    @Autowired
    private PatrolRecordMapper patrolRecordMapper; // 巡更记录Mapper
    @Autowired
    private PatrolPathMapper patrolPathMapper; // 巡更路线Mapper
    @Autowired
    private PatrolTaskMapper patrolTaskMapper; // 巡更任务Mapper

    @Override
    public void checkPatrolAlarm() {
        logger.info("=== 巡更超时报警定时任务开始执行，当前时间：{} ===", LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();

        try {
            // 查询所有「今天」的「未开始」和「进行中」的巡更任务
            PatrolTask patrolTask = new PatrolTask();
            patrolTask.setPatrolDate(DateUtils.toDate(now.toLocalDate()));
            // 添加任务状态条件：nostart(待执行) 或 inprogress(进行中)
            List<String> statusList = new ArrayList<>();
            statusList.add("nostart");
            statusList.add("inprogress");
            Map<String, Object> params = new HashMap<>();
            params.put("patrolTaskStatusList", statusList);
            patrolTask.setParams(params);
            List<PatrolTask> validTasks = patrolTaskMapper.selectDataListByLikePatrolTask(patrolTask);
            if (CollectionUtil.isEmpty(validTasks)) {
                logger.info("当前无符合条件的巡更任务，任务终止");
                return;
            }

            logger.info("查询到符合条件的巡更任务总数：{}，开始逐个处理报警逻辑", validTasks.size());
            // 遍历任务，分别处理两类报警场景
            for (PatrolTask task : validTasks) {
                try {
                    handleTaskNotStartAlarm(task, now);
                    handleTaskPointTimeoutAlarm(task, now);
                } catch (Exception e) {
                    logger.error("处理巡更任务【{}】（ID：{}）时发生异常，跳过该任务", task.getPatrolTaskName(), task.getId(), e);
                }
            }

            logger.info("=== 巡更超时报警定时任务执行完成，当前时间：{} ===", LocalDateTime.now());
        } catch (Exception e) {
            logger.error("=== 巡更超时报警定时任务整体执行异常，任务终止 ===", e);
        }
    }



    /**
     * 场景1：处理「巡更任务开始后30分钟未启动」报警
     */
    private void handleTaskNotStartAlarm(PatrolTask task, LocalDateTime now) {
        // 1. 基础参数校验
        if (task.getStartTime() == null) {
            logger.warn("任务【{}】（ID：{}）的开始时间不完整，跳过检查", task.getPatrolTaskName(), task.getId());
            return;
        }

        // 2. 计算任务超时判定时间（任务开始时间 + 30分钟）
        LocalDateTime taskStartDateTime = LocalDateTime.of(now.toLocalDate(), task.getStartTime());
        LocalDateTime timeoutTime = taskStartDateTime.plusMinutes(30);

        // 3. 未超时则不报警
        if (!now.isAfter(timeoutTime)) {
            logger.info("任务【{}】（ID：{}）未超时（开始时间：{}，超时时间：{}），跳过检查", task.getPatrolTaskName(), task.getId(), DateTimeFormatter.ofPattern("HH:mm").format(task.getStartTime()),
                DateTimeFormatter.ofPattern("HH:mm").format(timeoutTime));
            return;
        }

        // 4. 检查该任务是否有未删除的巡更记录
        LambdaQueryWrapper<PatrolRecord> recordWrapper = new LambdaQueryWrapper<>();
        recordWrapper.eq(PatrolRecord::getPatrolTaskId, task.getId())
            // 仅查询今天的记录（避免跨天记录干扰）
            .ge(PatrolRecord::getArriveTime, taskStartDateTime)
            .last("limit 1");
        PatrolRecord existRecord = patrolRecordMapper.selectOne(recordWrapper);

        // 5. 有巡更记录则任务已启动，不报警
        if (existRecord != null) {
            logger.info("任务【{}】（ID：{}）今日已存在巡更记录（ID：{}），跳过检查", task.getPatrolTaskName(), task.getId(), existRecord.getId());
            return;
        }

        // 6. 检查是否已存在未处理的同类报警（限定今日）
        LambdaQueryWrapper<PatrolAlarm> alarmWrapper = new LambdaQueryWrapper<>();
        // 基于任务名称和日期进行唯一性检查，因为PatrolAlarm类可能没有patrolTaskId字段
        alarmWrapper.eq(PatrolAlarm::getPatrolTaskId, task.getId())
            .eq(PatrolAlarm::getPatrolAlarmType, "210")
            .eq(PatrolAlarm::getPatrolAlarmStatus, "210")
            // 仅检查今天的报警（避免历史报警干扰）
            .ge(PatrolAlarm::getCreateTime, now.toLocalDate().atStartOfDay())
            .le(PatrolAlarm::getCreateTime, now.toLocalDate().atTime(23, 59, 59))
            .last("limit 1");
        PatrolAlarm existAlarm = patrolAlarmMapper.selectOne(alarmWrapper);

        // 7. 已存在未处理报警则不重复生成
        if (existAlarm != null) {
            logger.info("任务【{}】（ID：{}）今日已存在未处理报警（编号：{}），跳过检查", task.getPatrolTaskName(), task.getId(), existAlarm.getAlarmNo());
            return;
        }

        // 8. 生成新报警记录并推送通知
        PatrolAlarm newAlarm = buildPatrolAlarm(task, null, "210", now);
        int insertCount = patrolAlarmMapper.insert(newAlarm);
        if (insertCount > 0) {
            logger.info("任务【{}】（ID：{}）生成「未启动」报警记录，编号：{}", task.getPatrolTaskName(), task.getId(), newAlarm.getAlarmNo());
            // alarmNoticeService.sendNotice(newAlarm);
        } else {
            logger.error("任务【{}】（ID：{}）生成「未启动」报警记录失败", task.getPatrolTaskName(), task.getId());
        }
    }

    /**
     * 判断当前日期是否为计划的「实际执行日」
     * @param plan 巡更计划
     * @param now  当前时间
     * @return true=今天需要执行，false=今天无需执行
     */
    private boolean isExecutionDate(PatrolPlan plan, LocalDateTime now) {
        LocalDate currentDate = now.toLocalDate();
        LocalDate planStartDate = DateUtils.toLocalDate(plan.getStartDate());
        LocalDate planEndDate = DateUtils.toLocalDate(plan.getEndDate());

        // 基础校验：当前日期必须在计划的总日期范围内
        if (currentDate.isBefore(planStartDate) || currentDate.isAfter(planEndDate)) {
            return false;
        }

        // 根据周期类型判断
        switch (plan.getPatrolCycleType()) {
            case "day":
                // 每天：只要在总日期范围内，每天都是执行日
                return true;

            case "month":
                // 每月：当前日期的「日」需与计划开始日期的「日」一致
                // 特殊处理：若计划开始日是31日，当月没有31日则取当月最后一天
                int targetDay = planStartDate.getDayOfMonth();
                int currentMonthLastDay = currentDate.lengthOfMonth();
                int actualTargetDay = Math.min(targetDay, currentMonthLastDay);
                return currentDate.getDayOfMonth() == actualTargetDay;

            case "year":
                // 每年：当前日期的「月+日」需与计划开始日期的「月+日」一致
                return currentDate.getMonthValue() == planStartDate.getMonthValue() && currentDate.getDayOfMonth() == planStartDate.getDayOfMonth();

            default:
                logger.warn("计划【{}】（ID：{}）存在未知周期类型：{}，默认不执行", plan.getPatrolPlanName(), plan.getId(), plan.getPatrolCycleType());
                return false;
        }
    }

    /**
     * 场景2：处理「点位完成后30分钟无下一个点位记录」报警
     */
    private void handleTaskPointTimeoutAlarm(PatrolTask task, LocalDateTime now) {
        // 1. 基础参数校验
        if (task.getStartTime() == null || task.getEndTime() == null || null == task.getPatrolPathId()) {
            logger.warn("任务【{}】（ID：{}）的时间参数或路线ID不完整，跳过点位超时检查", task.getPatrolTaskName(), task.getId());
            return;
        }

        // 2. 查询该任务「今日」的未删除巡更记录
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LambdaQueryWrapper<PatrolRecord> recordWrapper = new LambdaQueryWrapper<>();
        recordWrapper.eq(PatrolRecord::getPatrolTaskId, task.getId())
            .ge(PatrolRecord::getArriveTime, todayStart) // 今日的记录
            .le(PatrolRecord::getArriveTime, now).orderByAsc(PatrolRecord::getPointOrder) // 按点位顺序升序
            .orderByDesc(PatrolRecord::getArriveTime); // 同点位按到达时间降序（取最新）
        List<PatrolRecord> recordList = patrolRecordMapper.selectList(recordWrapper);

        // 3. 无今日巡更记录 → 无需处理点位超时
        if (CollectionUtils.isEmpty(recordList)) {
            logger.info("任务【{}】（ID：{}）今日无巡更记录，跳过点位超时检查", task.getPatrolTaskName(), task.getId());
            return;
        }

        // 4. 获取最新完成的点位记录
        PatrolRecord lastRecord = recordList.get(0);
        // 校验最新记录的必要参数
        if (lastRecord.getArriveTime() == null || lastRecord.getPointOrder() == null || null == task.getPatrolPathId()) {
            logger.warn("任务【{}】（ID：{}）的最新巡更记录（ID：{}）缺少点位顺序/到达时间/点位ID，跳过检查", task.getPatrolTaskName(), task.getId(), lastRecord.getId());
            return;
        }

        // 5. 解析巡更路线的点位列表
        PatrolPath patrolPath = patrolPathMapper.selectById(task.getPatrolPathId());
        if (patrolPath == null || StringUtils.isBlank(patrolPath.getPointList())) {
            logger.error("任务【{}】（ID：{}）关联的巡更路线（ID：{}）不存在或点位列表为空，跳过检查", task.getPatrolTaskName(), task.getId(), task.getPatrolPathId());
            return;
        }
        List<Long> pathPointList = parsePointList(patrolPath.getPointList());
        int totalPointCount = pathPointList.size();
        // 若总点位为0 → 路线配置异常，跳过
        if (totalPointCount == 0) {
            logger.error("任务【{}】（ID：{}）关联的巡更路线（ID：{}）点位列表解析后为空，跳过检查", task.getPatrolTaskName(), task.getId(), patrolPath.getId());
            return;
        }

        // 6. 最新点位是最后一个 → 已完成全部巡更，无需报警
        if (lastRecord.getPointOrder() >= totalPointCount) {
            logger.info("任务【{}】（ID：{}）已完成最后一个点位（当前完成：{}，总点位：{}），跳过点位超时检查", task.getPatrolTaskName(), task.getId(), lastRecord.getPointOrder(), totalPointCount);
            return;
        }

        // 7. 计算点位超时判定时间（最新点位到达时间 + 30分钟）
        LocalDateTime lastPointArriveTime = DateUtil.toLocalDateTime(lastRecord.getArriveTime());
        LocalDateTime timeoutTime = lastPointArriveTime.plusMinutes(30);
        // 未超时 → 不报警
        if (!now.isAfter(timeoutTime)) {
            logger.info("任务【{}】（ID：{}）的最新点位（顺序：{}）未超时（到达时间：{}，超时时间：{}），跳过检查", task.getPatrolTaskName(), task.getId(), lastRecord.getPointOrder(),
                DateTimeFormatter.ofPattern("HH:mm:ss").format(lastPointArriveTime), DateTimeFormatter.ofPattern("HH:mm:ss").format(timeoutTime));
            return;
        }

        // 8. 检查是否已存在「今日未处理」的同类报警
        LambdaQueryWrapper<PatrolAlarm> alarmWrapper = new LambdaQueryWrapper<>();
        alarmWrapper.eq(PatrolAlarm::getPatrolTaskId, lastRecord.getPatrolTaskId())
                   .eq(PatrolAlarm::getPatrolAlarmType, "220") // 点位超时
                   .eq(PatrolAlarm::getPatrolAlarmStatus, "210") // 未处理
                   .ge(PatrolAlarm::getCreateTime, todayStart) // 限定今日报警
                   .le(PatrolAlarm::getCreateTime, now).last("limit 1");
        PatrolAlarm existAlarm = patrolAlarmMapper.selectOne(alarmWrapper);

        // 9. 已存在未处理报警 → 不重复生成
        if (existAlarm != null) {
            logger.info("任务【{}】（ID：{}）的最新点位（记录ID：{}）今日已存在未处理报警（编号：{}），跳过检查", task.getPatrolTaskName(), task.getId(), lastRecord.getId(), existAlarm.getAlarmNo());
            return;
        }

        // 10. 生成新报警记录
        try {
            PatrolAlarm newAlarm = buildPatrolAlarm(task, lastRecord, "220", now);
            int insertCount = patrolAlarmMapper.insert(newAlarm);
            if (insertCount > 0) {
                logger.info("任务【{}】（ID：{}）的最新点位（顺序：{}）生成「点位超时」报警记录，编号：{}", task.getPatrolTaskName(), task.getId(), lastRecord.getPointOrder(), newAlarm.getAlarmNo());
                // alarmNoticeService.sendNotice(newAlarm);
            } else {
                logger.error("任务【{}】（ID：{}）的最新点位（记录ID：{}）生成「点位超时」报警记录失败", task.getPatrolTaskName(), task.getId(), lastRecord.getId());
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("任务【{}】（ID：{}）的最新点位生成报警时触发数据库约束异常", task.getPatrolTaskName(), task.getId(), e);
        } catch (Exception e) {
            logger.error("任务【{}】（ID：{}）的最新点位生成报警时发生未知异常", task.getPatrolTaskName(), task.getId(), e);
        }
    }



    /**
     * 构建巡更报警记录（基于巡更任务）
     */
    private PatrolAlarm buildPatrolAlarm(PatrolTask task, PatrolRecord lastRecord, String alarmType, LocalDateTime now) {
        PatrolAlarm alarm = new PatrolAlarm();

        LambdaQueryWrapper<PatrolAlarm> alarmWrapper = new LambdaQueryWrapper<>();
        Long aLong = patrolAlarmMapper.selectCount(alarmWrapper);
        // 生成唯一报警编号
        String nowStr = DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(now);
        String nowFormatStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
        String alarmNo = "报警：" + StrUtil.toString(aLong);
        // String alarmNo = "报警-" + nowStr + "-" + task.getPatrolTaskName();
        alarm.setAlarmNo(alarmNo);

        // 关联基础信息
        // PatrolAlarm类没有patrolTaskId字段，使用patrolPlanId进行关联
        if (task.getPatrolPlanId() != null) {
            alarm.setPatrolPlanId(task.getPatrolPlanId()); // 关联计划ID
        }
        alarm.setPatrolTaskId(task.getId());
        alarm.setPatrolUserId(task.getPatrolUserId());
        alarm.setPatrolAlarmType(alarmType);
        alarm.setPatrolAlarmTime(DateUtils.toDate(now));
        alarm.setPatrolAlarmStatus("210"); // 初始状态：未处理
        alarm.setCreateBy("system");

        // 构建差异化报警内容
        if ("210".equals(alarmType)) {
            // 场景1：任务未启动报警内容
            String taskStartStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.of(DateUtils.toLocalDate(task.getPatrolDate()), task.getStartTime()));

            alarm.setPatrolAlarmContent(
                String.format("【巡更任务未启动报警】\n" + "任务名称：%s；\n" + "任务开始时间：%s；\n" + "报警触发时间：%s；\n" + "报警原因：任务开始后30分钟未生成任何巡更记录，请巡更人员及时巡查！",
                    task.getPatrolTaskName(), taskStartStr, nowFormatStr));
        } else if ("220".equals(alarmType) && lastRecord != null) {
            // 场景2：点位间超时报警内容
            String lastPointArriveStr = DateUtil.formatDateTime(lastRecord.getArriveTime());
            alarm.setPatrolAlarmContent(String.format(
                "【巡更点位超时报警】\n" + "任务名称：%s；\n" + "上一个点位到达时间：%s；\n" + "报警触发时间：%s；\n" + "报警原因：上一个点位完成后30分钟未生成下一个点位记录，请巡更人员及时巡查！",
                task.getPatrolTaskName(), lastPointArriveStr, nowFormatStr));
        }

        return alarm;
    }

    /**
     * 解析巡更路线的点位列表字符串为Long类型列表
     * @param pointListStr 点位ID逗号分隔字符串（如"1001,1002,1003"）
     * @return 点位ID列表
     */
    private List<Long> parsePointList(String pointListStr) {
        List<Long> pointIdList = new ArrayList<>();
        if (pointListStr == null || pointListStr.trim().isEmpty()) {
            return pointIdList;
        }

        String[] pointIdStrs = pointListStr.split(",");
        for (String pointIdStr : pointIdStrs) {
            if (pointIdStr == null || pointIdStr.trim().isEmpty()) {
                continue;
            }
            try {
                pointIdList.add(Long.parseLong(pointIdStr.trim()));
            } catch (NumberFormatException e) {
                logger.error("解析点位ID失败，无效的数字格式：{}", pointIdStr, e);
            }
        }
        return pointIdList;
    }


        /**
     * 场景1：处理「巡更计划开始后30分钟未启动」报警
     * 新增周期判断：仅在计划的实际执行日才进行超时检查
     */
    private void handlePlanNotStartAlarm(PatrolPlan plan, LocalDateTime now) {
        // 1. 基础参数校验
        Date planStartDate = plan.getStartDate();
        LocalDate startDate = DateUtils.toLocalDate(planStartDate);
        if (startDate == null || plan.getStartTime() == null || plan.getEndDate() == null || plan.getPatrolCycleType() == null) {
            logger.warn("计划【{}】（ID：{}）的时间参数或周期类型不完整，跳过检查", plan.getPatrolPlanName(), plan.getId());
            return;
        }

        // 2. 判断当前日期是否为计划的「实际执行日」（核心周期逻辑）
        if (!isExecutionDate(plan, now)) {
            logger.info("计划【{}】（ID：{}）今天不是执行日（周期：{}），跳过检查", plan.getPatrolPlanName(), plan.getId(), plan.getPatrolCycleType());
            return;
        }

        // 3. 计算计划超时判定时间（计划开始时间 + 30分钟）
        LocalDateTime planStartDateTime = LocalDateTime.of(startDate, plan.getStartTime());
        // 注意：周期任务需将开始日期替换为当前日期（仅保留时间部分）
        planStartDateTime = LocalDateTime.of(now.toLocalDate(), plan.getStartTime());
        LocalDateTime timeoutTime = planStartDateTime.plusMinutes(30);

        // 4. 未超时则不报警
        if (!now.isAfter(timeoutTime)) {
            logger.info("计划【{}】（ID：{}）未超时（开始时间：{}，超时时间：{}），跳过检查", plan.getPatrolPlanName(), plan.getId(), DateTimeFormatter.ofPattern("HH:mm").format(plan.getStartTime()),
                DateTimeFormatter.ofPattern("HH:mm").format(timeoutTime));
            return;
        }

        // 5. 检查该计划是否有未删除的巡更记录
        LambdaQueryWrapper<PatrolRecord> recordWrapper = new LambdaQueryWrapper<>();
        recordWrapper.eq(PatrolRecord::getPatrolPlanId, plan.getId())
            // 新增：仅查询今天的记录（避免跨天记录干扰）
            .ge(PatrolRecord::getArriveTime, planStartDateTime)
            // .le(PatrolRecord::getArriveTime, now.toLocalDate().atTime(23, 59, 59))
            .last("limit 1");
        PatrolRecord existRecord = patrolRecordMapper.selectOne(recordWrapper);

        // 6. 有巡更记录则计划已启动，不报警
        if (existRecord != null) {
            logger.info("计划【{}】（ID：{}）今日已存在巡更记录（ID：{}），跳过检查", plan.getPatrolPlanName(), plan.getId(), existRecord.getId());
            return;
        }

        // 7. 检查是否已存在未处理的同类报警（限定今日）
        LambdaQueryWrapper<PatrolAlarm> alarmWrapper = new LambdaQueryWrapper<>();
        alarmWrapper.eq(PatrolAlarm::getPatrolPlanId, plan.getId()).eq(PatrolAlarm::getPatrolAlarmType, "210").eq(PatrolAlarm::getPatrolAlarmStatus, "210")
            // 新增：仅检查今天的报警（避免历史报警干扰）
            .ge(PatrolAlarm::getCreateTime, now.toLocalDate().atStartOfDay()).le(PatrolAlarm::getCreateTime, now.toLocalDate().atTime(23, 59, 59)).last("limit 1");
        PatrolAlarm existAlarm = patrolAlarmMapper.selectOne(alarmWrapper);

        // 8. 已存在未处理报警则不重复生成
        if (existAlarm != null) {
            logger.info("计划【{}】（ID：{}）今日已存在未处理报警（编号：{}），跳过检查", plan.getPatrolPlanName(), plan.getId(), existAlarm.getAlarmNo());
            return;
        }

        // 9. 生成新报警记录并推送通知
        PatrolAlarm newAlarm = buildPatrolAlarm(plan, null, "210", now);
        int insertCount = patrolAlarmMapper.insert(newAlarm);
        if (insertCount > 0) {
            logger.info("计划【{}】（ID：{}）生成「未启动」报警记录，编号：{}", plan.getPatrolPlanName(), plan.getId(), newAlarm.getAlarmNo());
            // alarmNoticeService.sendNotice(newAlarm);
        } else {
            logger.error("计划【{}】（ID：{}）生成「未启动」报警记录失败", plan.getPatrolPlanName(), plan.getId());
        }
    }


        /**
     * 场景2：处理「点位完成后30分钟无下一个点位记录」报警
     * 优化点：1.补充周期执行日校验 2.精准控制时间范围 3.统一报警状态/类型常量 4.增强异常兼容
     */
    private void handlePointTimeoutAlarm(PatrolPlan plan, LocalDateTime now) {
        // 1. 前置校验：先判断当前日期是否为计划的「实际执行日」（同场景1逻辑，避免非执行日误判）
        if (!isExecutionDate(plan, now)) {
            logger.info("计划【{}】（ID：{}）今天不是执行日（周期：{}），跳过点位超时检查", plan.getPatrolPlanName(), plan.getId(), plan.getPatrolCycleType());
            return;
        }

        // 2. 基础参数校验：避免空指针或无效参数导致后续逻辑异常
        if (plan.getStartTime() == null || plan.getEndTime() == null || null == plan.getPatrolPathId()) {
            logger.warn("计划【{}】（ID：{}）的时间参数或路线ID不完整，跳过点位超时检查", plan.getPatrolPlanName(), plan.getId());
            return;
        }

        // 3. 查询该计划「今日」的未删除巡更记录（限定时间范围，避免历史记录干扰）
        // 计划今日的时间范围：[今日00:00:00, 当前时间]
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LambdaQueryWrapper<PatrolRecord> recordWrapper = new LambdaQueryWrapper<>();
        recordWrapper.eq(PatrolRecord::getPatrolPlanId, plan.getId())
            .ge(PatrolRecord::getArriveTime, todayStart) // 今日的记录
            .le(PatrolRecord::getArriveTime, now).orderByAsc(PatrolRecord::getPointOrder) // 按点位顺序升序
            .orderByDesc(PatrolRecord::getArriveTime); // 同点位按到达时间降序（取最新）
        List<PatrolRecord> recordList = patrolRecordMapper.selectList(recordWrapper);

        // 4. 无今日巡更记录 → 无需处理点位超时（计划未启动的场景由场景1覆盖）
        if (CollectionUtils.isEmpty(recordList)) {
            logger.info("计划【{}】（ID：{}）今日无巡更记录，跳过点位超时检查", plan.getPatrolPlanName(), plan.getId());
            return;
        }

        // 5. 获取最新完成的点位记录（列表已排序，第一个即为最新）
        PatrolRecord lastRecord = recordList.get(0);
        // 校验最新记录的必要参数
        if (lastRecord.getArriveTime() == null || lastRecord.getPointOrder() == null || null == plan.getPatrolPathId()) {
            logger.warn("计划【{}】（ID：{}）的最新巡更记录（ID：{}）缺少点位顺序/到达时间/点位ID，跳过检查", plan.getPatrolPlanName(), plan.getId(), lastRecord.getId());
            return;
        }

        // 6. 解析巡更路线的点位列表（获取总点位数量，判断是否已完成全部）
        PatrolPath patrolPath = patrolPathMapper.selectById(plan.getPatrolPathId());
        if (patrolPath == null || StringUtils.isBlank(patrolPath.getPointList())) {
            logger.error("计划【{}】（ID：{}）关联的巡更路线（ID：{}）不存在或点位列表为空，跳过检查", plan.getPatrolPlanName(), plan.getId(), plan.getPatrolPathId());
            return;
        }
        List<Long> pathPointList = parsePointList(patrolPath.getPointList());
        int totalPointCount = pathPointList.size();
        // 若总点位为0 → 路线配置异常，跳过
        if (totalPointCount == 0) {
            logger.error("计划【{}】（ID：{}）关联的巡更路线（ID：{}）点位列表解析后为空，跳过检查", plan.getPatrolPlanName(), plan.getId(), patrolPath.getId());
            return;
        }

        // 7. 最新点位是最后一个 → 已完成全部巡更，无需报警
        if (lastRecord.getPointOrder() >= totalPointCount) {
            logger.info("计划【{}】（ID：{}）已完成最后一个点位（当前完成：{}，总点位：{}），跳过点位超时检查", plan.getPatrolPlanName(), plan.getId(), lastRecord.getPointOrder(), totalPointCount);
            return;
        }

        // 8. 计算点位超时判定时间（最新点位到达时间 + 30分钟）
        LocalDateTime lastPointArriveTime = DateUtil.toLocalDateTime(lastRecord.getArriveTime());
        LocalDateTime timeoutTime = lastPointArriveTime.plusMinutes(30);
        // 未超时 → 不报警（当前时间 <= 超时时间）
        if (!now.isAfter(timeoutTime)) {
            logger.info("计划【{}】（ID：{}）的最新点位（顺序：{}）未超时（到达时间：{}，超时时间：{}），跳过检查", plan.getPatrolPlanName(), plan.getId(), lastRecord.getPointOrder(),
                DateTimeFormatter.ofPattern("HH:mm:ss").format(lastPointArriveTime), DateTimeFormatter.ofPattern("HH:mm:ss").format(timeoutTime));
            return;
        }

        // 9. 检查是否已存在「今日未处理」的同类报警（避免重复生成）
        LambdaQueryWrapper<PatrolAlarm> alarmWrapper = new LambdaQueryWrapper<>();
        alarmWrapper.eq(PatrolAlarm::getPatrolPlanId, plan.getId()).eq(PatrolAlarm::getPatrolTaskId, lastRecord.getPatrolTaskId()) // 关联最新点位记录
            .eq(PatrolAlarm::getPatrolAlarmType, "220") // 统一类型：点位超时（同场景1的编码规范）
            .eq(PatrolAlarm::getPatrolAlarmStatus, "210") // 统一状态：未处理（与场景1一致）
            .ge(PatrolAlarm::getCreateTime, todayStart) // 限定今日报警
            .le(PatrolAlarm::getCreateTime, now).last("limit 1"); // 仅需判断是否存在，取1条即可
        PatrolAlarm existAlarm = patrolAlarmMapper.selectOne(alarmWrapper);

        // 10. 已存在未处理报警 → 不重复生成
        if (existAlarm != null) {
            logger.info("计划【{}】（ID：{}）的最新点位（记录ID：{}）今日已存在未处理报警（编号：{}），跳过检查", plan.getPatrolPlanName(), plan.getId(), lastRecord.getId(), existAlarm.getAlarmNo());
            return;
        }

        // 11. 生成新报警记录并推送通知（复用buildPatrolAlarm方法，保证格式统一）
        try {
            PatrolAlarm newAlarm = buildPatrolAlarm(plan, lastRecord, "220", now);
            int insertCount = patrolAlarmMapper.insert(newAlarm);
            if (insertCount > 0) {
                logger.info("计划【{}】（ID：{}）的最新点位（顺序：{}）生成「点位超时」报警记录，编号：{}", plan.getPatrolPlanName(), plan.getId(), lastRecord.getPointOrder(), newAlarm.getAlarmNo());
                // 推送报警通知（确保通知服务非空，避免空指针）
                // if (alarmNoticeService != null) {
                //     alarmNoticeService.sendNotice(newAlarm);
                // } else {
                //     logger.warn("计划【{}】（ID：{}）的报警通知服务未注入，跳过通知推送",
                //         plan.getPatrolPlanName(), plan.getId());
                // }
            } else {
                logger.error("计划【{}】（ID：{}）的最新点位（记录ID：{}）生成「点位超时」报警记录失败（插入数据库返回0）", plan.getPatrolPlanName(), plan.getId(), lastRecord.getId());
            }
        } catch (DataIntegrityViolationException e) {
            // 捕获数据库唯一约束/完整性异常（避免重复报警的极端情况）
            logger.error("计划【{}】（ID：{}）的最新点位生成报警时触发数据库约束异常（可能重复插入）", plan.getPatrolPlanName(), plan.getId(), e);
        } catch (Exception e) {
            // 捕获其他未知异常，避免单个计划异常影响整体任务
            logger.error("计划【{}】（ID：{}）的最新点位生成报警时发生未知异常", plan.getPatrolPlanName(), plan.getId(), e);
        }
    }


        /**
     * 构建巡更报警记录（基于巡更计划）
     */
    private PatrolAlarm buildPatrolAlarm(PatrolPlan plan, PatrolRecord lastRecord, String alarmType, LocalDateTime now) {
        PatrolAlarm alarm = new PatrolAlarm();

        // 生成唯一报警编号
        String nowStr = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
        String nowFormatStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
        String alarmNo = "报警-" + nowStr + "-" + plan.getId();
        alarm.setAlarmNo(alarmNo);

        // 关联基础信息
        alarm.setPatrolPlanId(plan.getId());
        alarm.setPatrolTaskId(lastRecord != null ? lastRecord.getPatrolTaskId() : null);
        alarm.setPatrolUserId(plan.getPatrolUserId());
        alarm.setPatrolAlarmType(alarmType);
        alarm.setPatrolAlarmTime(DateUtils.toDate(now));
        alarm.setPatrolAlarmStatus("210"); // 初始状态：未处理
        alarm.setCreateBy("system");

        // 构建差异化报警内容
        if ("210".equals(alarmType)) {
            // 场景1：计划未启动报警内容
            String planStartStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.of(DateUtils.toLocalDate(plan.getStartDate()), plan.getStartTime()));

            alarm.setPatrolAlarmContent(
                String.format("【巡更计划未启动报警】\n" + "计划名称：%s；\n" + "计划开始时间：%s；\n" + "报警触发时间：%s；\n" + "报警原因：计划开始后30分钟未生成任何巡更记录，请巡更人员及时巡查！",
                    plan.getPatrolPlanName(), planStartStr, nowFormatStr));
        } else if ("220".equals(alarmType) && lastRecord != null) {
            // 场景2：点位间超时报警内容
            String lastPointArriveStr = DateUtil.formatDateTime(lastRecord.getArriveTime());
            alarm.setPatrolAlarmContent(String.format(
                "【巡更点位超时报警】\n" + "计划名称：%s；\n" + "上一个点位到达时间：%s；\n" + "报警触发时间：%s；\n" + "报警原因：上一个点位完成后30分钟未生成下一个点位记录，请巡更人员及时巡查！",
                plan.getPatrolPlanName(), lastPointArriveStr, nowFormatStr));
        }

        return alarm;
    }

}
