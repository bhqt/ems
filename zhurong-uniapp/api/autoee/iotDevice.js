import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import iotDevice from './autoee/iotDevice';
export default {
  selectPageListIotDevice,
  selectDataListByLikeIotDevice,
  selectDataListByEqIotDevice,
  selectDetailListByLikeIotDevice,
  selectDetailListByEqIotDevice,
  selectDataByPkIotDevice,
  selectDetailByPkIotDevice,
  addIotDevice,
  updateNullValueByIotDevice,
  updateNotNullValueByIotDevice,
  submitTableEditIotDevice,
  deleteIotDeviceByIds,
  deleteIotDeviceOneByOne,
};

// 查询设备管理分页列表
export function selectPageListIotDevice(query) {
  return request({
    url: '/autoee/iotDevice/selectPageListIotDevice',
    method: 'get',
    params: query
  })
}

// 查询设备管理数据列表，不分页
export function selectDataListByLikeIotDevice(query) {
  return request({
    url: '/autoee/iotDevice/selectDataListByLikeIotDevice',
    method: 'get',
    params: query
  })
}

// 精确查询设备管理数据列表，不分页
export function selectDataListByEqIotDevice(query) {
  return request({
    url: '/autoee/iotDevice/selectDataListByEqIotDevice',
    method: 'get',
    params: query
  })
}

// 查询设备管理详细列表，不分页
export function selectDetailListByLikeIotDevice(query) {
  return request({
    url: '/autoee/iotDevice/selectDetailListByLikeIotDevice',
    method: 'get',
    params: query
  })
}

// 精确查询设备管理详细列表，不分页
export function selectDetailListByEqIotDevice(query) {
  return request({
    url: '/autoee/iotDevice/selectDetailListByEqIotDevice',
    method: 'get',
    params: query
  })
}

// 查询设备管理数据信息
export function selectDataByPkIotDevice(id) {
  return request({
    url: '/autoee/iotDevice/selectDataByPkIotDevice/' + id,
    method: 'get'
  })
}
// 查询设备管理详细信息，已转码
export function selectDetailByPkIotDevice(id) {
  return request({
    url: '/autoee/iotDevice/selectDetailByPkIotDevice/' + id,
    method: 'get'
  })
}

// 新增设备管理
export function addIotDevice(data) {
  return request({
    url: '/autoee/iotDevice',
    method: 'post',
    data: data
  })
}

// 修改设备管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIotDevice(data) {
  return request({
    url: '/autoee/iotDevice/updateNullValueByIotDevice',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIotDevice(data) {
  return request({
    url: '/autoee/iotDevice/updateNotNullValueByIotDevice',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIotDevice(data) {
  return request({
    url: '/autoee/iotDevice/submitTableEditIotDevice',
    method: 'put',
    data: data
  })
}

// 删除设备管理
export function deleteIotDeviceByIds(id) {
	return request({
		url: '/autoee/iotDevice/deleteIotDeviceByIds/' + id,
		method: 'delete'
	})
}

// 删除设备管理
export function deleteIotDeviceOneByOne(id) {
  return request({
    url: '/autoee/iotDevice/deleteIotDeviceOneByOne/' + id,
    method: 'delete'
  })
}
