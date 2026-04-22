import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolPoint from './autoee/patrolPoint';
export default {
  selectPageListPatrolPoint,
  selectDataListByLikePatrolPoint,
  selectDataListByEqPatrolPoint,
  selectDetailListByLikePatrolPoint,
  selectDetailListByEqPatrolPoint,
  selectDataByPkPatrolPoint,
  selectDetailByPkPatrolPoint,
  addPatrolPoint,
  updateNullValueByPatrolPoint,
  updateNotNullValueByPatrolPoint,
  submitTableEditPatrolPoint,
  deletePatrolPointByIds,
  deletePatrolPointOneByOne,
  deletePatrolPointAllData,
};

// 查询巡更点位分页列表
export function selectPageListPatrolPoint(query) {
  return request({
    url: '/autoee/patrolPoint/selectPageListPatrolPoint',
    method: 'get',
    params: query
  })
}

// 查询巡更点位数据列表，不分页
export function selectDataListByLikePatrolPoint(query) {
  return request({
    url: '/autoee/patrolPoint/selectDataListByLikePatrolPoint',
    method: 'get',
    params: query
  })
}

// 精确查询巡更点位数据列表，不分页
export function selectDataListByEqPatrolPoint(query) {
  return request({
    url: '/autoee/patrolPoint/selectDataListByEqPatrolPoint',
    method: 'get',
    params: query
  })
}

// 查询巡更点位详细列表，不分页
export function selectDetailListByLikePatrolPoint(query) {
  return request({
    url: '/autoee/patrolPoint/selectDetailListByLikePatrolPoint',
    method: 'get',
    params: query
  })
}

// 精确查询巡更点位详细列表，不分页
export function selectDetailListByEqPatrolPoint(query) {
  return request({
    url: '/autoee/patrolPoint/selectDetailListByEqPatrolPoint',
    method: 'get',
    params: query
  })
}

// 查询巡更点位数据信息
export function selectDataByPkPatrolPoint(id) {
  return request({
    url: '/autoee/patrolPoint/selectDataByPkPatrolPoint/' + id,
    method: 'get'
  })
}
// 查询巡更点位详细信息，已转码
export function selectDetailByPkPatrolPoint(id) {
  return request({
    url: '/autoee/patrolPoint/selectDetailByPkPatrolPoint/' + id,
    method: 'get'
  })
}

// 新增巡更点位
export function addPatrolPoint(data) {
  return request({
    url: '/autoee/patrolPoint',
    method: 'post',
    data: data
  })
}

// 修改巡更点位：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByPatrolPoint(data) {
  return request({
    url: '/autoee/patrolPoint/updateNullValueByPatrolPoint',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByPatrolPoint(data) {
  return request({
    url: '/autoee/patrolPoint/updateNotNullValueByPatrolPoint',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditPatrolPoint(data) {
  return request({
    url: '/autoee/patrolPoint/submitTableEditPatrolPoint',
    method: 'put',
    data: data
  })
}

// 删除巡更点位
export function deletePatrolPointByIds(id) {
	return request({
		url: '/autoee/patrolPoint/deletePatrolPointByIds/' + id,
		method: 'delete'
	})
}

// 删除巡更点位
export function deletePatrolPointOneByOne(id) {
  return request({
    url: '/autoee/patrolPoint/deletePatrolPointOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deletePatrolPointAllData() {
  return request({
    url: '/autoee/patrolPoint/deletePatrolPointAllData',
    method: 'delete'
  })
}
