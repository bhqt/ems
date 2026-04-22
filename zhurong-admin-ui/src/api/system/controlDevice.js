import request from '@/utils/request'

// 查询控制设备列表
export function listControlDevice(query) {
  return request({
    url: '/control/device/list',
    method: 'get',
    params: query
  })
}

// 查询控制设备详细信息
export function getControlDevice(deviceId) {
  return request({
    url: '/control/device/getInfo/' + deviceId,
    method: 'get'
  })
}

// 新增控制设备
export function addControlDevice(data) {
  return request({
    url: '/control/device/add',
    method: 'post',
    data: data
  })
}

// 修改控制设备
export function updateControlDevice(data) {
  return request({
    url: '/control/device/edit',
    method: 'put',
    data: data
  })
}

// 删除控制设备
export function delControlDevice(deviceIds) {
  return request({
    url: '/control/device/remove/' + deviceIds,
    method: 'delete'
  })
}

// 控制设备操作
export function controlDeviceOperation(data) {
  return request({
    url: '/control/device/operate',
    method: 'post',
    data: data
  })
}
