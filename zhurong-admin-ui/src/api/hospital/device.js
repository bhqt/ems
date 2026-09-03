import request from '@/utils/request'

// 查询设备列表
export function listDevice(query) {
  return request({
    url: '/hospital/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getDevice(id) {
  return request({
    url: '/hospital/device/info/' + id,
    method: 'get'
  })
}

// 新增设备
export function addDevice(data) {
  return request({
    url: '/hospital/device',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateDevice(data) {
  return request({
    url: '/hospital/device',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delDevice(ids) {
  return request({
    url: '/hospital/device/' + ids,
    method: 'delete'
  })
}

// 绑定/解绑 IOT 设备
export function bindIotDevice(id, iotDeviceId) {
  return request({
    url: '/hospital/device/bind',
    method: 'put',
    params: { id, iotDeviceId: iotDeviceId || '' }
  })
}

// 查询设备数据点
export function listDeviceData(params) {
  return request({
    url: '/hospital/device/dataList',
    method: 'get',
    params: params
  })
}
