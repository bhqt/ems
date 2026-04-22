import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import address from './autoee/address';
export default {
  selectPageListAddress,
  selectDataListByLikeAddress,
  selectDataListByEqAddress,
  selectDetailListByLikeAddress,
  selectDetailListByEqAddress,
  selectDataByPkAddress,
  selectDetailByPkAddress,
  addAddress,
  updateNullValueByAddress,
  updateNotNullValueByAddress,
  submitTableEditAddress,
  deleteAddressByIds,
  deleteAddressOneByOne,
};

// 查询用户地址信息管理分页列表
export function selectPageListAddress(query) {
  return request({
    url: '/autoee/address/selectPageListAddress',
    method: 'get',
    params: query
  })
}

// 查询用户地址信息管理数据列表，不分页
export function selectDataListByLikeAddress(query) {
  return request({
    url: '/autoee/address/selectDataListByLikeAddress',
    method: 'get',
    params: query
  })
}

// 精确查询用户地址信息管理数据列表，不分页
export function selectDataListByEqAddress(query) {
  return request({
    url: '/autoee/address/selectDataListByEqAddress',
    method: 'get',
    params: query
  })
}

// 查询用户地址信息管理详细列表，不分页
export function selectDetailListByLikeAddress(query) {
  return request({
    url: '/autoee/address/selectDetailListByLikeAddress',
    method: 'get',
    params: query
  })
}

// 精确查询用户地址信息管理详细列表，不分页
export function selectDetailListByEqAddress(query) {
  return request({
    url: '/autoee/address/selectDetailListByEqAddress',
    method: 'get',
    params: query
  })
}

// 查询用户地址信息管理数据信息
export function selectDataByPkAddress(id) {
  return request({
    url: '/autoee/address/selectDataByPkAddress/' + id,
    method: 'get'
  })
}
// 查询用户地址信息管理详细信息，已转码
export function selectDetailByPkAddress(id) {
  return request({
    url: '/autoee/address/selectDetailByPkAddress/' + id,
    method: 'get'
  })
}

// 新增用户地址信息管理
export function addAddress(data) {
  return request({
    url: '/autoee/address',
    method: 'post',
    data: data
  })
}

// 修改用户地址信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByAddress(data) {
  return request({
    url: '/autoee/address/updateNullValueByAddress',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByAddress(data) {
  return request({
    url: '/autoee/address/updateNotNullValueByAddress',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditAddress(data) {
  return request({
    url: '/autoee/address/submitTableEditAddress',
    method: 'put',
    data: data
  })
}

// 删除用户地址信息管理
export function deleteAddressByIds(id) {
	return request({
		url: '/autoee/address/deleteAddressByIds/' + id,
		method: 'delete'
	})
}

// 删除用户地址信息管理
export function deleteAddressOneByOne(id) {
  return request({
    url: '/autoee/address/deleteAddressOneByOne/' + id,
    method: 'delete'
  })
}
