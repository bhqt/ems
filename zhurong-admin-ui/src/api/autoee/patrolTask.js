import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolTask from './autoee/patrolTask';
export default {
  selectPageListPatrolTask,
  selectDataListByLikePatrolTask,
  selectDataListByEqPatrolTask,
  selectDetailListByLikePatrolTask,
  selectDetailListByEqPatrolTask,
  selectDataByPkPatrolTask,
  selectDetailByPkPatrolTask,
  addPatrolTask,
  updateNullValueByPatrolTask,
  updateNotNullValueByPatrolTask,
  submitTableEditPatrolTask,
  deletePatrolTaskByIds,
  deletePatrolTaskOneByOne,
  deletePatrolTaskAllData,
};

// 查询巡更任务分页列表
export function selectPageListPatrolTask(query) {
  return request({
    url: '/autoee/patrolTask/selectPageListPatrolTask',
    method: 'get',
    params: query
  })
}

// 查询巡更任务数据列表，不分页
export function selectDataListByLikePatrolTask(query) {
  return request({
    url: '/autoee/patrolTask/selectDataListByLikePatrolTask',
    method: 'get',
    params: query
  })
}

// 精确查询巡更任务数据列表，不分页
export function selectDataListByEqPatrolTask(query) {
  return request({
    url: '/autoee/patrolTask/selectDataListByEqPatrolTask',
    method: 'get',
    params: query
  })
}

// 查询巡更任务详细列表，不分页
export function selectDetailListByLikePatrolTask(query) {
  return request({
    url: '/autoee/patrolTask/selectDetailListByLikePatrolTask',
    method: 'get',
    params: query
  })
}

// 精确查询巡更任务详细列表，不分页
export function selectDetailListByEqPatrolTask(query) {
  return request({
    url: '/autoee/patrolTask/selectDetailListByEqPatrolTask',
    method: 'get',
    params: query
  })
}

// 查询巡更任务数据信息
export function selectDataByPkPatrolTask(id) {
  return request({
    url: '/autoee/patrolTask/selectDataByPkPatrolTask/' + id,
    method: 'get'
  })
}
// 查询巡更任务详细信息，已转码
export function selectDetailByPkPatrolTask(id) {
  return request({
    url: '/autoee/patrolTask/selectDetailByPkPatrolTask/' + id,
    method: 'get'
  })
}

// 新增巡更任务
export function addPatrolTask(data) {
  return request({
    url: '/autoee/patrolTask',
    method: 'post',
    data: data
  })
}

// 修改巡更任务：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByPatrolTask(data) {
  return request({
    url: '/autoee/patrolTask/updateNullValueByPatrolTask',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByPatrolTask(data) {
  return request({
    url: '/autoee/patrolTask/updateNotNullValueByPatrolTask',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditPatrolTask(data) {
  return request({
    url: '/autoee/patrolTask/submitTableEditPatrolTask',
    method: 'put',
    data: data
  })
}

// 删除巡更任务
export function deletePatrolTaskByIds(id) {
	return request({
		url: '/autoee/patrolTask/deletePatrolTaskByIds/' + id,
		method: 'delete'
	})
}

// 删除巡更任务
export function deletePatrolTaskOneByOne(id) {
  return request({
    url: '/autoee/patrolTask/deletePatrolTaskOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deletePatrolTaskAllData() {
  return request({
    url: '/autoee/patrolTask/deletePatrolTaskAllData',
    method: 'delete'
  })
}
