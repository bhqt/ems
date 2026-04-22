import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolPath from './autoee/patrolPath';
export default {
  selectPageListPatrolPath,
  selectDataListByLikePatrolPath,
  selectDataListByEqPatrolPath,
  selectDetailListByLikePatrolPath,
  selectDetailListByEqPatrolPath,
  selectDataByPkPatrolPath,
  selectDetailByPkPatrolPath,
  addPatrolPath,
  updateNullValueByPatrolPath,
  updateNotNullValueByPatrolPath,
  submitTableEditPatrolPath,
  deletePatrolPathByIds,
  deletePatrolPathOneByOne,
};

// 查询巡更路线分页列表
export function selectPageListPatrolPath(query) {
  return request({
    url: '/autoee/patrolPath/selectPageListPatrolPath',
    method: 'get',
    params: query
  })
}

// 查询巡更路线数据列表，不分页
export function selectDataListByLikePatrolPath(query) {
  return request({
    url: '/autoee/patrolPath/selectDataListByLikePatrolPath',
    method: 'get',
    params: query
  })
}

// 精确查询巡更路线数据列表，不分页
export function selectDataListByEqPatrolPath(query) {
  return request({
    url: '/autoee/patrolPath/selectDataListByEqPatrolPath',
    method: 'get',
    params: query
  })
}

// 查询巡更路线详细列表，不分页
export function selectDetailListByLikePatrolPath(query) {
  return request({
    url: '/autoee/patrolPath/selectDetailListByLikePatrolPath',
    method: 'get',
    params: query
  })
}

// 精确查询巡更路线详细列表，不分页
export function selectDetailListByEqPatrolPath(query) {
  return request({
    url: '/autoee/patrolPath/selectDetailListByEqPatrolPath',
    method: 'get',
    params: query
  })
}

// 查询巡更路线数据信息
export function selectDataByPkPatrolPath(id) {
  return request({
    url: '/autoee/patrolPath/selectDataByPkPatrolPath/' + id,
    method: 'get'
  })
}
// 查询巡更路线详细信息，已转码
export function selectDetailByPkPatrolPath(id) {
  return request({
    url: '/autoee/patrolPath/selectDetailByPkPatrolPath/' + id,
    method: 'get'
  })
}

// 新增巡更路线
export function addPatrolPath(data) {
  return request({
    url: '/autoee/patrolPath',
    method: 'post',
    data: data
  })
}

// 修改巡更路线：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByPatrolPath(data) {
  return request({
    url: '/autoee/patrolPath/updateNullValueByPatrolPath',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByPatrolPath(data) {
  return request({
    url: '/autoee/patrolPath/updateNotNullValueByPatrolPath',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditPatrolPath(data) {
  return request({
    url: '/autoee/patrolPath/submitTableEditPatrolPath',
    method: 'put',
    data: data
  })
}

// 删除巡更路线
export function deletePatrolPathByIds(id) {
	return request({
		url: '/autoee/patrolPath/deletePatrolPathByIds/' + id,
		method: 'delete'
	})
}

// 删除巡更路线
export function deletePatrolPathOneByOne(id) {
  return request({
    url: '/autoee/patrolPath/deletePatrolPathOneByOne/' + id,
    method: 'delete'
  })
}
