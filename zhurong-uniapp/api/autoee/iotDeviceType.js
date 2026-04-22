import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import iotDeviceType from './autoee/iotDeviceType';
export default {
  selectPageListIotDeviceType,
  selectDataListByLikeIotDeviceType,
  selectDataListByEqIotDeviceType,
  selectDetailListByLikeIotDeviceType,
  selectDetailListByEqIotDeviceType,
  selectDataByPkIotDeviceType,
  selectDetailByPkIotDeviceType,
  addIotDeviceType,
  updateNullValueByIotDeviceType,
  updateNotNullValueByIotDeviceType,
  submitTableEditIotDeviceType,
  deleteIotDeviceTypeByIds,
  deleteIotDeviceTypeOneByOne,
};

// 查询设备类型分页列表
export function selectPageListIotDeviceType(query) {
  return request({
    url: '/autoee/iotDeviceType/selectPageListIotDeviceType',
    method: 'get',
    params: query
  })
}

// 查询设备类型数据列表，不分页
export function selectDataListByLikeIotDeviceType(query) {
  return request({
    url: '/autoee/iotDeviceType/selectDataListByLikeIotDeviceType',
    method: 'get',
    params: query
  })
}

// 精确查询设备类型数据列表，不分页
export function selectDataListByEqIotDeviceType(query) {
  return request({
    url: '/autoee/iotDeviceType/selectDataListByEqIotDeviceType',
    method: 'get',
    params: query
  })
}

// 查询设备类型详细列表，不分页
export function selectDetailListByLikeIotDeviceType(query) {
  return request({
    url: '/autoee/iotDeviceType/selectDetailListByLikeIotDeviceType',
    method: 'get',
    params: query
  })
}

// 精确查询设备类型详细列表，不分页
export function selectDetailListByEqIotDeviceType(query) {
  return request({
    url: '/autoee/iotDeviceType/selectDetailListByEqIotDeviceType',
    method: 'get',
    params: query
  })
}

// 查询设备类型数据信息
export function selectDataByPkIotDeviceType(id) {
  return request({
    url: '/autoee/iotDeviceType/selectDataByPkIotDeviceType/' + id,
    method: 'get'
  })
}
// 查询设备类型详细信息，已转码
export function selectDetailByPkIotDeviceType(id) {
  return request({
    url: '/autoee/iotDeviceType/selectDetailByPkIotDeviceType/' + id,
    method: 'get'
  })
}

// 新增设备类型
export function addIotDeviceType(data) {
  return request({
    url: '/autoee/iotDeviceType',
    method: 'post',
    data: data
  })
}

// 修改设备类型：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIotDeviceType(data) {
  return request({
    url: '/autoee/iotDeviceType/updateNullValueByIotDeviceType',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIotDeviceType(data) {
  return request({
    url: '/autoee/iotDeviceType/updateNotNullValueByIotDeviceType',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIotDeviceType(data) {
  return request({
    url: '/autoee/iotDeviceType/submitTableEditIotDeviceType',
    method: 'put',
    data: data
  })
}

// 删除设备类型
export function deleteIotDeviceTypeByIds(id) {
	return request({
		url: '/autoee/iotDeviceType/deleteIotDeviceTypeByIds/' + id,
		method: 'delete'
	})
}

// 删除设备类型
export function deleteIotDeviceTypeOneByOne(id) {
  return request({
    url: '/autoee/iotDeviceType/deleteIotDeviceTypeOneByOne/' + id,
    method: 'delete'
  })
}
