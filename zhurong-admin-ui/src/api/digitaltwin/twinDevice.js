import request from '@/utils/request'

export function listTwinDevice() {
  return request({ url: '/system/digitaltwin/device/list', method: 'get' })
}
export function getTwinDevice(deviceId) {
  return request({ url: '/system/digitaltwin/device/' + deviceId, method: 'get' })
}
export function addTwinDevice(data) {
  return request({ url: '/system/digitaltwin/device', method: 'post', data: data })
}
export function updateTwinDevice(data) {
  return request({ url: '/system/digitaltwin/device', method: 'put', data: data })
}
export function delTwinDevice(deviceIds) {
  return request({ url: '/system/digitaltwin/device/' + deviceIds, method: 'delete' })
}
