import request from '@/utils/request'

export function listEnergyFlow() {
  return request({ url: '/system/digitaltwin/flow/list', method: 'get' })
}
export function getFlowByType(flowType) {
  return request({ url: '/system/digitaltwin/flow/byType', method: 'get', params: { flowType } })
}
export function getEnergyBalance() {
  return request({ url: '/system/digitaltwin/flow/balance', method: 'get' })
}
export function addEnergyFlow(data) {
  return request({ url: '/system/digitaltwin/flow', method: 'post', data: data })
}
export function updateEnergyFlow(data) {
  return request({ url: '/system/digitaltwin/flow', method: 'put', data: data })
}
export function delEnergyFlow(flowIds) {
  return request({ url: '/system/digitaltwin/flow/' + flowIds, method: 'delete' })
}
