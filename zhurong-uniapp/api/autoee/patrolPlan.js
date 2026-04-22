import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolPlan from './autoee/patrolPlan';
export default {
  selectPageListPatrolPlan,
  selectDataListByLikePatrolPlan,
  selectDataListByEqPatrolPlan,
  selectDetailListByLikePatrolPlan,
  selectDetailListByEqPatrolPlan,
  selectDataByPkPatrolPlan,
  selectDetailByPkPatrolPlan,
  addPatrolPlan,
  updateNullValueByPatrolPlan,
  updateNotNullValueByPatrolPlan,
  submitTableEditPatrolPlan,
  deletePatrolPlanByIds,
  deletePatrolPlanOneByOne,
};

// 查询巡更计划分页列表
export function selectPageListPatrolPlan(query) {
  return request({
    url: '/autoee/patrolPlan/selectPageListPatrolPlan',
    method: 'get',
    params: query
  })
}

// 查询巡更计划数据列表，不分页
export function selectDataListByLikePatrolPlan(query) {
  return request({
    url: '/autoee/patrolPlan/selectDataListByLikePatrolPlan',
    method: 'get',
    params: query
  })
}

// 精确查询巡更计划数据列表，不分页
export function selectDataListByEqPatrolPlan(query) {
  return request({
    url: '/autoee/patrolPlan/selectDataListByEqPatrolPlan',
    method: 'get',
    params: query
  })
}

// 查询巡更计划详细列表，不分页
export function selectDetailListByLikePatrolPlan(query) {
  return request({
    url: '/autoee/patrolPlan/selectDetailListByLikePatrolPlan',
    method: 'get',
    params: query
  })
}

// 精确查询巡更计划详细列表，不分页
export function selectDetailListByEqPatrolPlan(query) {
  return request({
    url: '/autoee/patrolPlan/selectDetailListByEqPatrolPlan',
    method: 'get',
    params: query
  })
}

// 查询巡更计划数据信息
export function selectDataByPkPatrolPlan(id) {
  return request({
    url: '/autoee/patrolPlan/selectDataByPkPatrolPlan/' + id,
    method: 'get'
  })
}
// 查询巡更计划详细信息，已转码
export function selectDetailByPkPatrolPlan(id) {
  return request({
    url: '/autoee/patrolPlan/selectDetailByPkPatrolPlan/' + id,
    method: 'get'
  })
}

// 新增巡更计划
export function addPatrolPlan(data) {
  return request({
    url: '/autoee/patrolPlan',
    method: 'post',
    data: data
  })
}

// 修改巡更计划：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByPatrolPlan(data) {
  return request({
    url: '/autoee/patrolPlan/updateNullValueByPatrolPlan',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByPatrolPlan(data) {
  return request({
    url: '/autoee/patrolPlan/updateNotNullValueByPatrolPlan',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditPatrolPlan(data) {
  return request({
    url: '/autoee/patrolPlan/submitTableEditPatrolPlan',
    method: 'put',
    data: data
  })
}

// 删除巡更计划
export function deletePatrolPlanByIds(id) {
	return request({
		url: '/autoee/patrolPlan/deletePatrolPlanByIds/' + id,
		method: 'delete'
	})
}

// 删除巡更计划
export function deletePatrolPlanOneByOne(id) {
  return request({
    url: '/autoee/patrolPlan/deletePatrolPlanOneByOne/' + id,
    method: 'delete'
  })
}
