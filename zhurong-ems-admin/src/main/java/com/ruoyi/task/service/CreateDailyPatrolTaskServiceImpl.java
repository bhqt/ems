package com.ruoyi.task.service;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapper;
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapperExtend;
import com.ruoyi.autoee.patrolAlarm.service.impl.PatrolAlarmServiceExtend;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapper;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;
import com.ruoyi.autoee.patrolPlan.mapper.PatrolPlanMapper;
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapper;
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapper;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysDictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 巡更任务Service业务层处理
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class CreateDailyPatrolTaskServiceImpl implements ICreateDailyPatrolTaskService {
    private static final Logger logger = LoggerFactory.getLogger(CreateDailyPatrolTaskServiceImpl.class);
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
    private PatrolTaskMapper patrolTaskMapper;

    @Override
    public void createDailyPatrolTask() {
        logger.info("开始生成当天巡更任务");

        try {
            LocalDateTime now = LocalDateTime.now(); // 当前时间
            LocalDate today = now.toLocalDate();     // 今天日期

            // -------------------------- 1. 基础筛选：生效中的计划 + 时间范围包含今天 --------------------------
            // 状态：生效中（210，与业务常量保持一致）
            PatrolPlan patrolPlan = new PatrolPlan();
            patrolPlan.setPatrolPlanStatus("210");

            // 时间范围：计划的「开始日期 ≤ 今天 ≤ 结束日期」
            Map<String, Object> params = new HashMap<>();
            params.put("startDateLte", today); // 计划开始日期 ≤ 今天
            params.put("endDateGte", today);   // 计划结束日期 ≥ 今天
            patrolPlan.setParams(params);

            // 查询符合基础条件的所有计划
            List<PatrolPlan> validPlans = patrolPlanMapper.selectDetailListByLikePatrolPlan(patrolPlan);
            if (CollectionUtils.isEmpty(validPlans)) {
                logger.info("当前筛选条件下无生效的巡更计划");
                return;
            }

            logger.info("查询到生效的巡更计划数量：{}", validPlans.size());

            // -------------------------- 2. 周期筛选：筛选出今天需要执行的计划 --------------------------
            List<PatrolPlan> todayExecutionPlans = validPlans.stream()
                .filter(plan -> isExecutionDate(plan, now)) // 判断是否为执行日
                .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(todayExecutionPlans)) {
                logger.info("今天无需要执行的巡更计划");
                return;
            }

            logger.info("今天需要执行的巡更计划数量：{}", todayExecutionPlans.size());

            // -------------------------- 3. 为每个符合条件的计划创建巡更任务 --------------------------
            for (PatrolPlan plan : todayExecutionPlans) {
                try {
                    // 检查该计划今天是否已存在任务
                    if (isTaskExistsForToday(plan.getId(), today)) {
                        logger.info("计划ID {} 今天已存在巡更任务，跳过创建", plan.getId());
                        continue;
                    }

                    // 创建巡更任务
                    PatrolTask patrolTask = createPatrolTaskFromPlan(plan, today);
                    int result = patrolTaskMapper.insertPatrolTask(patrolTask);

                    if (result > 0) {
                        logger.info("成功创建巡更任务：计划ID={}, 任务ID={}", plan.getId(), patrolTask.getId());
                    } else {
                        logger.error("创建巡更任务失败：计划ID={}", plan.getId());
                    }
                } catch (Exception e) {
                    logger.error("处理计划ID={}时发生异常：{}", plan.getId(), e.getMessage(), e);
                    // 继续处理其他计划，不中断整体流程
                }
            }

            logger.info("当天巡更任务生成完成");
        } catch (Exception e) {
            logger.error("生成当天巡更任务时发生异常：{}", e.getMessage(), e);
        }
    }

    /**
     * 判断当前日期是否为计划的「实际执行日」（支持日/月/年周期）
     */
    private boolean isExecutionDate(PatrolPlan plan, LocalDateTime now) {
        LocalDate today = now.toLocalDate();
        LocalDate planStartDate = DateUtils.toLocalDate(plan.getStartDate());
        LocalDate planEndDate = DateUtils.toLocalDate(plan.getEndDate());

        // 基础校验：当前日期必须在计划总时间范围内
        if (today.isBefore(planStartDate) || today.isAfter(planEndDate)) {
            return false;
        }

        // 周期类型：day=每日，month=每月，year=每年，week=每周（与业务常量保持一致）
        String cycleType = plan.getPatrolCycleType();
        switch (cycleType) {
            case "day":
                // 每日：只要在总时间范围内，每天都是执行日
                return true;
            case "month":
                // 每月：今天的「日」需与计划开始日期的「日」一致（特殊处理：当月无对应日则取最后一天）
                int targetDay = planStartDate.getDayOfMonth();
                int todayLastDay = today.lengthOfMonth();
                int actualTargetDay = Math.min(targetDay, todayLastDay);
                return today.getDayOfMonth() == actualTargetDay;
            case "year":
                // 每年：今天的「月-日」需与计划开始日期的「月-日」一致
                return today.getMonth() == planStartDate.getMonth() &&
                       today.getDayOfMonth() == planStartDate.getDayOfMonth();
            case "week":
                // 每周：检查今天是否在指定的星期几列表中
                String patrolCycleValue = plan.getPatrolCycleValue();
                if (patrolCycleValue != null && !patrolCycleValue.isEmpty()) {
                    // 按逗号分隔获取指定的星期几列表
                    String[] weekDays = patrolCycleValue.split(",");
                    // 获取今天是星期几（1=周一，7=周日）
                    int todayDayOfWeek = today.getDayOfWeek().getValue();
                    // 检查今天是否在指定的星期几列表中
                    for (String weekDay : weekDays) {
                        try {
                            int targetWeekDay = Integer.parseInt(weekDay.trim());
                            if (todayDayOfWeek == targetWeekDay) {
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            logger.warn("无效的星期几格式：{}", weekDay);
                        }
                    }
                }
                return false;
            default:
                logger.warn("未知的巡更周期类型：{}", cycleType);
                return false;
        }
    }

    /**
     * 检查该计划今天是否已存在任务
     */
    private boolean isTaskExistsForToday(Long patrolPlanId, LocalDate today) {
        PatrolTask patrolTask = new PatrolTask();
        patrolTask.setPatrolPlanId(patrolPlanId);
        patrolTask.setPatrolDate(DateUtils.toDate(today));

        List<PatrolTask> tasks = patrolTaskMapper.selectDataListByEqPatrolTask(patrolTask);
        return !CollectionUtils.isEmpty(tasks);
    }

    /**
     * 根据巡更计划创建巡更任务
     */
    private PatrolTask createPatrolTaskFromPlan(PatrolPlan plan, LocalDate patrolDate) {
        PatrolTask task = new PatrolTask();

        // 设置任务基本信息
        task.setPatrolTaskName("巡更任务-" + plan.getPatrolPlanName() + "-" + DateUtil.formatDate(DateUtils.toDate(patrolDate)));
        task.setPatrolPlanId(plan.getId());
        task.setPatrolPathId(plan.getPatrolPathId());
        task.setPatrolUserId(plan.getPatrolUserId());
        task.setPatrolDate(DateUtils.toDate(patrolDate));
        task.setStartTime(plan.getStartTime());
        task.setEndTime(plan.getEndTime());
        task.setPatrolTaskStatus("nostart"); // 任务状态：待执行（与业务常量保持一致）
        task.setRemark(plan.getRemark());
        task.setUserId(plan.getUserId());
        task.setDeptId(plan.getDeptId());

        // 设置创建时间和创建人
        task.setCreateTime(DateUtils.getNowDate());
        task.setCreateBy("system"); // 系统创建
        task.setUpdateTime(DateUtils.getNowDate());
        task.setUpdateBy("system");

        return task;
    }
}
