import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolPlan from './autoee/patrolPlan';
export default {
  selectUnfinishedOrUnstartedTask,
  getPatrolTaskDetails,
  performCheckin
};

// 查询巡更计划分页列表
export function selectUnfinishedOrUnstartedTask(query) {
  return request({
    url: '/autoee/patrolTaskExtend/selectUnfinishedOrUnstartedTask',
    method: 'get',
    params: query
  })
}

// 通过巡更计划id查询对应的巡更路线和巡更点位
export function getPatrolTaskDetails(taskId) {
  return request({
    url: '/autoee/patrolTaskExtend/getPatrolTaskDetails',
    method: 'get',
    params: {
      taskId: taskId
    }
  })
}

// 执行巡更打卡操作
export function performCheckin(data) {
  return request({
    url: '/autoee/patrolTaskExtend/performCheckin',
    method: 'post',
    data: data
  })
}

