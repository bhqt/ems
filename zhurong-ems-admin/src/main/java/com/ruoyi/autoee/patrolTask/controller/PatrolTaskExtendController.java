package com.ruoyi.autoee.patrolTask.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapper;
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;
import com.ruoyi.autoee.patrolTask.service.IPatrolTaskService;
import com.ruoyi.autoee.patrolPoint.domain.PatrolPoint;
import com.ruoyi.autoee.patrolPoint.mapper.PatrolPointMapper;
import com.ruoyi.autoee.patrolRecord.domain.PatrolRecord;
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapper;
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 巡更任务Controller（扩展：获取未开始/未完成任务）
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@RestController
@RequestMapping("/autoee/patrolTaskExtend")
public class PatrolTaskExtendController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PatrolTaskExtendController.class);

    @Autowired
    private IPatrolTaskService patrolTaskService;

    // 需注入关联的Mapper（若Service层已封装，可替换为Service调用）
    @Autowired
    private PatrolRecordMapper patrolRecordMapper; // 巡更记录Mapper
    @Autowired
    private PatrolPathMapper patrolPathMapper;     // 巡更路线Mapper
    @Autowired
    private PatrolPointMapper patrolPointMapper;   // 巡更点位Mapper
    @Autowired
    private PatrolTaskMapper patrolTaskMapper;

    /**
     * 查询未开始或未完成的巡更任务
     * @return 包含未开始、未完成 任务的列表
     */
    @GetMapping("/selectUnfinishedOrUnstartedTask")
    public TableDataInfo selectUnfinishedOrUnstartedTask() {
        try {
            // 1. 获取当前日期
            LocalDate today = LocalDate.now();
            Date todayDate = DateUtils.parseDate(today.toString());
            Long userId = getUserId();

            // 2. 查询今日的所有巡更任务
            PatrolTask patrolTask = new PatrolTask();
            patrolTask.setPatrolUserId(userId);
            patrolTask.setPatrolDate(new Date());
            patrolTask.getParams().put("patrolTaskStatusInValueArr", new String[]{"nostart", "nofinish"});
            List<PatrolTask> todayTasks = patrolTaskMapper.selectDataListByEqPatrolTask(patrolTask);

            if (CollectionUtils.isEmpty(todayTasks)) {
                logger.info("暂无今日巡更任务");
                return getDataTable(Collections.emptyList());
            }

            return getDataTable(todayTasks);
        } catch (Exception e) {
            logger.error("查询巡更任务失败", e);
            return getDataTable(Collections.emptyList()); // 异常时返回空列表，避免前端报错
        }
    }

    /**
     * 查询某任务今日的所有巡更记录（已排序）
     * @param planId 任务ID
     * @param today  今天日期
     * @return 今日巡更记录列表（按点位顺序升序、到达时间降序）
     */
    private List<PatrolRecord> getTodayPatrolRecords(Long planId, LocalDate today) {
        // 时间范围：今天00:00:00 ~ 现在
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        LambdaQueryWrapper<PatrolRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PatrolRecord::getPatrolTaskId, planId)       // 关联任务ID
            .ge(PatrolRecord::getArriveTime, todayStart)    // 今日记录
            .le(PatrolRecord::getArriveTime, now)           // 不包含未来记录
            .orderByAsc(PatrolRecord::getPointOrder)        // 按点位顺序升序
            .orderByDesc(PatrolRecord::getArriveTime);      // 同点位取最新的记录

        return patrolRecordMapper.selectList(wrapper);
    }

    /**
     * 获取今日已完成的最大点位顺序
     * @param todayRecords 今日巡更记录
     * @return 最大点位顺序（0=无有效记录）
     */
    private int getMaxFinishedPointOrder(List<PatrolRecord> todayRecords) {
        if (CollectionUtils.isEmpty(todayRecords)) {
            return 0;
        }

        // 取所有记录中的最大点位顺序（点位顺序由业务层保证递增）
        return todayRecords.stream()
            .filter(record -> record.getPointOrder() != null)
            .mapToInt(PatrolRecord::getPointOrder)
            .max()
            .orElse(0);
    }

    /**
     * 通过巡更任务id查询对应的巡更路线和巡更点位，判断当前点位是否已经巡更完成
     * @param taskId 巡更任务ID
     * @return 包含巡更任务、路线、点位及完成状态的详细信息
     */
    @GetMapping("/getPatrolTaskDetails")
    public Map<String, Object> getPatrolTaskDetails(Long taskId) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. 查询巡更任务详情
            PatrolTask patrolTask = patrolTaskService.selectDataByPkPatrolTask(taskId);
            if (patrolTask == null) {
                logger.warn("未找到ID为{}的巡更任务", taskId);
                result.put("code", 404);
                result.put("msg", "未找到巡更任务");
                return result;
            }
            result.put("patrolTask", patrolTask);

            // 2. 查询巡更路线
            Long pathId = patrolTask.getPatrolPathId();
            if (pathId != null) {
                PatrolPath patrolPath = patrolPathMapper.selectById(pathId);
                if (patrolPath != null) {
                    result.put("patrolPath", patrolPath);

                    // 3. 查询巡更点位列表
                    List<Map<String, Object>> pointList = new ArrayList<>();
                    if (StrUtil.isNotBlank(patrolPath.getPointList())) {
                        String[] pointIds = patrolPath.getPointList().split(",");
                        LocalDate today = LocalDate.now();

                        // 查询今日该任务的巡更记录
                        List<PatrolRecord> todayRecords = getTodayPatrolRecords(taskId, today);
                        // 找出已完成的点位ID和顺序
                        Map<Integer, PatrolRecord> finishedPoints = new HashMap<>();
                        for (PatrolRecord record : todayRecords) {
                            if (record.getPointOrder() != null) {
                                // 对于同一个点位，保留最新的记录
                                if (!finishedPoints.containsKey(record.getPointOrder()) ||
                                    finishedPoints.get(record.getPointOrder()).getArriveTime().before(record.getArriveTime())) {
                                    finishedPoints.put(record.getPointOrder(), record);
                                }
                            }
                        }

                        // 获取最大已完成点位顺序
                        int maxFinishedOrder = getMaxFinishedPointOrder(todayRecords);
                        int currentPointIndex = -1;

                        // 遍历所有点位，添加详细信息和完成状态
                        for (int i = 0; i < pointIds.length; i++) {
                            try {
                                Long pointId = Long.parseLong(pointIds[i].trim());
                                PatrolPoint point = patrolPointMapper.selectById(pointId);
                                if (point != null) {
                                    Map<String, Object> pointInfo = new HashMap<>();
                                    pointInfo.put("id", point.getId());
                                    pointInfo.put("pointName", point.getPointName());
                                    pointInfo.put("pointLocation", point.getPointLocation());
                                    pointInfo.put("pointOrder", i + 1); // 点位顺序从1开始

                                    // 判断是否已完成
                                    int pointOrder = i + 1;
                                    boolean isFinished = finishedPoints.containsKey(pointOrder);
                                    pointInfo.put("isFinished", isFinished);

                                    // 如果已完成，添加完成时间和结果
                                    if (isFinished) {
                                        PatrolRecord record = finishedPoints.get(pointOrder);
                                        pointInfo.put("finishTime", record.getArriveTime());
                                        pointInfo.put("patrolResult", record.getPatrolResult());
                                        pointInfo.put("resultDesc", record.getResultDesc());
                                    }

                                    pointList.add(pointInfo);

                                    // 找到当前需要打开的点位（下一个未完成的点位）
                                    if (currentPointIndex == -1 && !isFinished) {
                                        currentPointIndex = i;
                                    }
                                }
                            } catch (NumberFormatException e) {
                                logger.warn("点位ID格式无效: {}", pointIds[i]);
                            }
                        }

                        result.put("pointList", pointList);

                        // 4. 设置当前需要打开的点位
                        if (currentPointIndex >= 0) {
                            result.put("currentPoint", pointList.get(currentPointIndex));
                            result.put("currentPointIndex", currentPointIndex);
                        } else if (!pointList.isEmpty()) {
                            // 如果所有点位都已完成，返回最后一个点位
                            result.put("currentPoint", pointList.get(pointList.size() - 1));
                            result.put("currentPointIndex", pointList.size() - 1);
                            result.put("allPointsFinished", true);
                        }
                    }
                }
            }

            result.put("code", 200);
            result.put("msg", "查询成功");
        } catch (Exception e) {
            logger.error("查询巡更任务详情失败", e);
            result.put("code", 500);
            result.put("msg", "查询失败");
        }
        return result;
    }


    /**
     * 执行巡更打卡操作
     * @param requestData 包含计划id、路线id、任务id、点位id的请求数据
     * @return 打卡结果信息，包含是否成功、消息和最新的任务状态
     */
    @PostMapping("/performCheckin")
    public AjaxResult performCheckin(@RequestBody Map<String, Object> requestData) {
        try {
            // 1. 获取必要参数
            Long planId = requestData.containsKey("planId") ? Long.valueOf(requestData.get("planId").toString()) : null;
            Long routeId = requestData.containsKey("routeId") ? Long.valueOf(requestData.get("routeId").toString()) : null;
            Long taskId = requestData.containsKey("taskId") ? Long.valueOf(requestData.get("taskId").toString()) : null;
            Long pointId = requestData.containsKey("pointId") ? Long.valueOf(requestData.get("pointId").toString()) : null;

            // 参数校验
            if (taskId == null || pointId == null) {
                logger.warn("打卡失败：任务ID或点位ID不能为空");
                return AjaxResult.error("打卡失败：任务ID或点位ID不能为空");
            }

            // 2. 检查任务是否存在
            PatrolTask patrolTask = patrolTaskService.selectDataByPkPatrolTask(taskId);
            if (patrolTask == null) {
                logger.warn("打卡失败：未找到ID为{}的巡更任务", taskId);
                return AjaxResult.error("打卡失败：未找到巡更任务");
            }

            // 3. 检查点位是否存在
            PatrolPoint patrolPoint = patrolPointMapper.selectById(pointId);
            if (patrolPoint == null) {
                logger.warn("打卡失败：未找到ID为{}的巡更点位", pointId);
                return AjaxResult.error("打卡失败：未找到巡更点位");
            }

            // 4. 查询当前任务的路线信息，确定点位顺序
            PatrolPath patrolPath = null;
            if (routeId != null) {
                patrolPath = patrolPathMapper.selectById(routeId);
            } else if (patrolTask.getPatrolPathId() != null) {
                patrolPath = patrolPathMapper.selectById(patrolTask.getPatrolPathId());
            }

            // 5. 构建并完善巡更记录信息
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setPatrolPlanId(planId);
            patrolRecord.setPatrolPathId(routeId != null ? routeId : patrolTask.getPatrolPathId());
            patrolRecord.setPatrolTaskId(taskId);
            patrolRecord.setPatrolPointId(pointId);
            patrolRecord.setPatrolUserId(getUserId()); // 当前登录用户ID
            patrolRecord.setArriveTime(new Date()); // 到达时间为当前时间
            patrolRecord.setPatrolResult("210"); // 默认正常，可根据实际业务调整
            patrolRecord.setResultDesc("");
            patrolRecord.setCreateTime(new Date());
            patrolRecord.setUpdateTime(new Date());
            patrolRecord.setUserId(getUserId()); // 设置所属用户
            patrolRecord.setDeptId(getDeptId()); // 设置所属部门

            // 6. 确定点位顺序
            int pointOrder = 0;
            if (patrolPath != null && StrUtil.isNotBlank(patrolPath.getPointList())) {
                String[] pointIds = patrolPath.getPointList().split(",");
                for (int i = 0; i < pointIds.length; i++) {
                    if (Long.parseLong(pointIds[i].trim()) == pointId) {
                        pointOrder = i + 1; // 点位顺序从1开始
                        break;
                    }
                }
            }
            patrolRecord.setPointOrder(pointOrder);

            // 7. 保存巡更记录
            patrolRecordMapper.insertPatrolRecord(patrolRecord);
            logger.info("用户{}成功打卡点位{}，任务ID：{}", getUserId(), pointId, taskId);

            // 8. 检查是否所有点位已完成，更新任务状态
            updateTaskStatusIfNeeded(taskId);

            // 9. 返回成功结果，包含最新的任务状态
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "打卡成功");
            result.put("taskStatus", patrolTask.getPatrolTaskStatus());

            return AjaxResult.success(result);
        } catch (Exception e) {
            logger.error("打卡操作失败", e);
            return AjaxResult.error("打卡失败，请重试");
        }
    }

    /**
     * 检查并更新任务状态
     * @param taskId 任务ID
     */
    private void updateTaskStatusIfNeeded(Long taskId) {
        try {
            // 查询任务信息
            PatrolTask patrolTask = patrolTaskService.selectDataByPkPatrolTask(taskId);
            if (patrolTask == null) {
                return;
            }

            // 查询任务对应的路线
            PatrolPath patrolPath = null;
            if (patrolTask.getPatrolPathId() != null) {
                patrolPath = patrolPathMapper.selectById(patrolTask.getPatrolPathId());
            }

            // 如果没有关联路线或点位列表为空，无法判断完成状态
            if (patrolPath == null || StrUtil.isBlank(patrolPath.getPointList())) {
                // 如果任务状态是未开始，更新为未完成
                if ("nostart".equals(patrolTask.getPatrolTaskStatus())) {
                    patrolTask.setPatrolTaskStatus("nofinish");
                    patrolTaskService.updateNotNullValueByPatrolTask(patrolTask);
                }
                return;
            }

            // 获取点位总数
            int totalPoints = patrolPath.getPointList().split(",").length;

            // 查询今日已完成的点位数量
            LocalDate today = LocalDate.now();
            LambdaQueryWrapper<PatrolRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PatrolRecord::getPatrolTaskId, taskId);
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setPatrolTaskId(taskId);
            int finishedPoints = patrolRecordMapper.selectCountByEqPatrolRecord(patrolRecord);

            // 更新任务状态
            if (finishedPoints == totalPoints) {
                // 所有点位已完成
                patrolTask.setPatrolTaskStatus("finish");
            } else {
                // 部分点位已完成，任务状态应为未完成
                patrolTask.setPatrolTaskStatus("nofinish");
            }

            patrolTaskService.updateNotNullValueByPatrolTask(patrolTask);
        } catch (Exception e) {
            logger.error("更新任务状态失败", e);
        }
    }
}
