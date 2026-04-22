import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolAlarm from './autoee/patrolAlarm';
export default {
  selectPageListPatrolAlarm,
  selectDataListByLikePatrolAlarm,
  selectDataListByEqPatrolAlarm,
  selectDetailListByLikePatrolAlarm,
  selectDetailListByEqPatrolAlarm,
  selectDataByPkPatrolAlarm,
  selectDetailByPkPatrolAlarm,
  addPatrolAlarm,
  updateNullValueByPatrolAlarm,
  updateNotNullValueByPatrolAlarm,
  submitTableEditPatrolAlarm,
  deletePatrolAlarmByIds,
  deletePatrolAlarmOneByOne,
  deletePatrolAlarmAllData,
};

// 查询巡更报警分页列表
export function selectPageListPatrolAlarm(query) {
  return request({
    url: '/autoee/patrolAlarm/selectPageListPatrolAlarm',
    method: 'get',
    params: query
  })
}

// 查询巡更报警数据列表，不分页
export function selectDataListByLikePatrolAlarm(query) {
  return request({
    url: '/autoee/patrolAlarm/selectDataListByLikePatrolAlarm',
    method: 'get',
    params: query
  })
}

// 精确查询巡更报警数据列表，不分页
export function selectDataListByEqPatrolAlarm(query) {
  return request({
    url: '/autoee/patrolAlarm/selectDataListByEqPatrolAlarm',
    method: 'get',
    params: query
  })
}

// 查询巡更报警详细列表，不分页
export function selectDetailListByLikePatrolAlarm(query) {
  return request({
    url: '/autoee/patrolAlarm/selectDetailListByLikePatrolAlarm',
    method: 'get',
    params: query
  })
}

// 精确查询巡更报警详细列表，不分页
export function selectDetailListByEqPatrolAlarm(query) {
  return request({
    url: '/autoee/patrolAlarm/selectDetailListByEqPatrolAlarm',
    method: 'get',
    params: query
  })
}

// 查询巡更报警数据信息
export function selectDataByPkPatrolAlarm(id) {
  return request({
    url: '/autoee/patrolAlarm/selectDataByPkPatrolAlarm/' + id,
    method: 'get'
  })
}
// 查询巡更报警详细信息，已转码
export function selectDetailByPkPatrolAlarm(id) {
  return request({
    url: '/autoee/patrolAlarm/selectDetailByPkPatrolAlarm/' + id,
    method: 'get'
  })
}

// 新增巡更报警
export function addPatrolAlarm(data) {
  return request({
    url: '/autoee/patrolAlarm',
    method: 'post',
    data: data
  })
}

// 修改巡更报警：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByPatrolAlarm(data) {
  return request({
    url: '/autoee/patrolAlarm/updateNullValueByPatrolAlarm',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByPatrolAlarm(data) {
  return request({
    url: '/autoee/patrolAlarm/updateNotNullValueByPatrolAlarm',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditPatrolAlarm(data) {
  return request({
    url: '/autoee/patrolAlarm/submitTableEditPatrolAlarm',
    method: 'put',
    data: data
  })
}

// 删除巡更报警
export function deletePatrolAlarmByIds(id) {
	return request({
		url: '/autoee/patrolAlarm/deletePatrolAlarmByIds/' + id,
		method: 'delete'
	})
}

// 删除巡更报警
export function deletePatrolAlarmOneByOne(id) {
  return request({
    url: '/autoee/patrolAlarm/deletePatrolAlarmOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deletePatrolAlarmAllData() {
  return request({
    url: '/autoee/patrolAlarm/deletePatrolAlarmAllData',
    method: 'delete'
  })
}
