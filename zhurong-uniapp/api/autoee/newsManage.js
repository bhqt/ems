import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import newsManage from './autoee/newsManage';
export default {
  selectPageListNewsManage,
  selectDataListByLikeNewsManage,
  selectDataListByEqNewsManage,
  selectDetailListByLikeNewsManage,
  selectDetailListByEqNewsManage,
  selectDataByPkNewsManage,
  selectDetailByPkNewsManage,
  addNewsManage,
  updateNullValueByNewsManage,
  updateNotNullValueByNewsManage,
  submitTableEditNewsManage,
  deleteNewsManageByIds,
  deleteNewsManageOneByOne,
};

// 查询内容管理分页列表
export function selectPageListNewsManage(query) {
  return request({
    url: '/autoee/newsManage/selectPageListNewsManage',
    method: 'get',
    params: query
  })
}

// 查询内容管理数据列表，不分页
export function selectDataListByLikeNewsManage(query) {
  return request({
    url: '/autoee/newsManage/selectDataListByLikeNewsManage',
    method: 'get',
    params: query
  })
}

// 精确查询内容管理数据列表，不分页
export function selectDataListByEqNewsManage(query) {
  return request({
    url: '/autoee/newsManage/selectDataListByEqNewsManage',
    method: 'get',
    params: query
  })
}

// 查询内容管理详细列表，不分页
export function selectDetailListByLikeNewsManage(query) {
  return request({
    url: '/autoee/newsManage/selectDetailListByLikeNewsManage',
    method: 'get',
    params: query
  })
}

// 精确查询内容管理详细列表，不分页
export function selectDetailListByEqNewsManage(query) {
  return request({
    url: '/autoee/newsManage/selectDetailListByEqNewsManage',
    method: 'get',
    params: query
  })
}

// 查询内容管理数据信息
export function selectDataByPkNewsManage(id) {
  return request({
    url: '/autoee/newsManage/selectDataByPkNewsManage/' + id,
    method: 'get'
  })
}
// 查询内容管理详细信息，已转码
export function selectDetailByPkNewsManage(id) {
  return request({
    url: '/autoee/newsManage/selectDetailByPkNewsManage/' + id,
    method: 'get'
  })
}

// 新增内容管理
export function addNewsManage(data) {
  return request({
    url: '/autoee/newsManage',
    method: 'post',
    data: data
  })
}

// 修改内容管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByNewsManage(data) {
  return request({
    url: '/autoee/newsManage/updateNullValueByNewsManage',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByNewsManage(data) {
  return request({
    url: '/autoee/newsManage/updateNotNullValueByNewsManage',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditNewsManage(data) {
  return request({
    url: '/autoee/newsManage/submitTableEditNewsManage',
    method: 'put',
    data: data
  })
}

// 删除内容管理
export function deleteNewsManageByIds(id) {
	return request({
		url: '/autoee/newsManage/deleteNewsManageByIds/' + id,
		method: 'delete'
	})
}

// 删除内容管理
export function deleteNewsManageOneByOne(id) {
  return request({
    url: '/autoee/newsManage/deleteNewsManageOneByOne/' + id,
    method: 'delete'
  })
}
