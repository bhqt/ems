import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import iotDeviceData from './autoee/iotDeviceData';
export default {
  selectPageListIotDeviceData,
  selectDataListByLikeIotDeviceData,
  selectDataListByEqIotDeviceData,
  selectDetailListByLikeIotDeviceData,
  selectDetailListByEqIotDeviceData,
  selectDataByPkIotDeviceData,
  selectDetailByPkIotDeviceData,
  addIotDeviceData,
  updateNullValueByIotDeviceData,
  updateNotNullValueByIotDeviceData,
  submitTableEditIotDeviceData,
  deleteIotDeviceDataByIds,
  deleteIotDeviceDataOneByOne,
};

// 查询设备数据分页列表
export function selectPageListIotDeviceData(query) {
  return request({
    url: '/autoee/iotDeviceData/selectPageListIotDeviceData',
    method: 'get',
    params: query
  })
}

// 查询设备数据数据列表，不分页
export function selectDataListByLikeIotDeviceData(query) {
  return request({
    url: '/autoee/iotDeviceData/selectDataListByLikeIotDeviceData',
    method: 'get',
    params: query
  })
}

// 精确查询设备数据数据列表，不分页
export function selectDataListByEqIotDeviceData(query) {
  return request({
    url: '/autoee/iotDeviceData/selectDataListByEqIotDeviceData',
    method: 'get',
    params: query
  })
}

// 查询设备数据详细列表，不分页
export function selectDetailListByLikeIotDeviceData(query) {
  return request({
    url: '/autoee/iotDeviceData/selectDetailListByLikeIotDeviceData',
    method: 'get',
    params: query
  })
}

// 精确查询设备数据详细列表，不分页
export function selectDetailListByEqIotDeviceData(query) {
  return request({
    url: '/autoee/iotDeviceData/selectDetailListByEqIotDeviceData',
    method: 'get',
    params: query
  })
}

// 查询设备数据数据信息
export function selectDataByPkIotDeviceData(id) {
  return request({
    url: '/autoee/iotDeviceData/selectDataByPkIotDeviceData/' + id,
    method: 'get'
  })
}
// 查询设备数据详细信息，已转码
export function selectDetailByPkIotDeviceData(id) {
  return request({
    url: '/autoee/iotDeviceData/selectDetailByPkIotDeviceData/' + id,
    method: 'get'
  })
}

// 新增设备数据
export function addIotDeviceData(data) {
  return request({
    url: '/autoee/iotDeviceData',
    method: 'post',
    data: data
  })
}

// 修改设备数据：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIotDeviceData(data) {
  return request({
    url: '/autoee/iotDeviceData/updateNullValueByIotDeviceData',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIotDeviceData(data) {
  return request({
    url: '/autoee/iotDeviceData/updateNotNullValueByIotDeviceData',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIotDeviceData(data) {
  return request({
    url: '/autoee/iotDeviceData/submitTableEditIotDeviceData',
    method: 'put',
    data: data
  })
}

// 删除设备数据
export function deleteIotDeviceDataByIds(id) {
	return request({
		url: '/autoee/iotDeviceData/deleteIotDeviceDataByIds/' + id,
		method: 'delete'
	})
}

// 删除设备数据
export function deleteIotDeviceDataOneByOne(id) {
  return request({
    url: '/autoee/iotDeviceData/deleteIotDeviceDataOneByOne/' + id,
    method: 'delete'
  })
}
