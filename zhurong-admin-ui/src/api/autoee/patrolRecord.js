import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolRecord from './autoee/patrolRecord';
export default {
  selectPageListPatrolRecord,
  selectDataListByLikePatrolRecord,
  selectDataListByEqPatrolRecord,
  selectDetailListByLikePatrolRecord,
  selectDetailListByEqPatrolRecord,
  selectDataByPkPatrolRecord,
  selectDetailByPkPatrolRecord,
  addPatrolRecord,
  updateNullValueByPatrolRecord,
  updateNotNullValueByPatrolRecord,
  submitTableEditPatrolRecord,
  deletePatrolRecordByIds,
  deletePatrolRecordOneByOne,
  deletePatrolRecordAllData,
};

// 查询巡更记录分页列表
export function selectPageListPatrolRecord(query) {
  return request({
    url: '/autoee/patrolRecord/selectPageListPatrolRecord',
    method: 'get',
    params: query
  })
}

// 查询巡更记录数据列表，不分页
export function selectDataListByLikePatrolRecord(query) {
  return request({
    url: '/autoee/patrolRecord/selectDataListByLikePatrolRecord',
    method: 'get',
    params: query
  })
}

// 精确查询巡更记录数据列表，不分页
export function selectDataListByEqPatrolRecord(query) {
  return request({
    url: '/autoee/patrolRecord/selectDataListByEqPatrolRecord',
    method: 'get',
    params: query
  })
}

// 查询巡更记录详细列表，不分页
export function selectDetailListByLikePatrolRecord(query) {
  return request({
    url: '/autoee/patrolRecord/selectDetailListByLikePatrolRecord',
    method: 'get',
    params: query
  })
}

// 精确查询巡更记录详细列表，不分页
export function selectDetailListByEqPatrolRecord(query) {
  return request({
    url: '/autoee/patrolRecord/selectDetailListByEqPatrolRecord',
    method: 'get',
    params: query
  })
}

// 查询巡更记录数据信息
export function selectDataByPkPatrolRecord(id) {
  return request({
    url: '/autoee/patrolRecord/selectDataByPkPatrolRecord/' + id,
    method: 'get'
  })
}
// 查询巡更记录详细信息，已转码
export function selectDetailByPkPatrolRecord(id) {
  return request({
    url: '/autoee/patrolRecord/selectDetailByPkPatrolRecord/' + id,
    method: 'get'
  })
}

// 新增巡更记录
export function addPatrolRecord(data) {
  return request({
    url: '/autoee/patrolRecord',
    method: 'post',
    data: data
  })
}

// 修改巡更记录：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByPatrolRecord(data) {
  return request({
    url: '/autoee/patrolRecord/updateNullValueByPatrolRecord',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByPatrolRecord(data) {
  return request({
    url: '/autoee/patrolRecord/updateNotNullValueByPatrolRecord',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditPatrolRecord(data) {
  return request({
    url: '/autoee/patrolRecord/submitTableEditPatrolRecord',
    method: 'put',
    data: data
  })
}

// 删除巡更记录
export function deletePatrolRecordByIds(id) {
	return request({
		url: '/autoee/patrolRecord/deletePatrolRecordByIds/' + id,
		method: 'delete'
	})
}

// 删除巡更记录
export function deletePatrolRecordOneByOne(id) {
  return request({
    url: '/autoee/patrolRecord/deletePatrolRecordOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deletePatrolRecordAllData() {
  return request({
    url: '/autoee/patrolRecord/deletePatrolRecordAllData',
    method: 'delete'
  })
}
