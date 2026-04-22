import request from '@/utils/request'

export function listModelConfig(query) {
  return request({ url: '/system/dispatch/modelConfig/list', method: 'get', params: query })
}
export function getModelConfig(configId) {
  return request({ url: '/system/dispatch/modelConfig/' + configId, method: 'get' })
}
export function addModelConfig(data) {
  return request({ url: '/system/dispatch/modelConfig', method: 'post', data: data })
}
export function updateModelConfig(data) {
  return request({ url: '/system/dispatch/modelConfig', method: 'put', data: data })
}
export function delModelConfig(configIds) {
  return request({ url: '/system/dispatch/modelConfig/' + configIds, method: 'delete' })
}
export function trainModel(configId) {
  return request({ url: '/system/dispatch/modelConfig/train/' + configId, method: 'put' })
}
