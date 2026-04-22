import request from '@/utils/request'

export function listThreeDModel() {
  return request({ url: '/system/digitaltwin/model/list', method: 'get' })
}
export function getThreeDModel(modelId) {
  return request({ url: '/system/digitaltwin/model/' + modelId, method: 'get' })
}
export function addThreeDModel(data) {
  return request({ url: '/system/digitaltwin/model', method: 'post', data: data })
}
export function updateThreeDModel(data) {
  return request({ url: '/system/digitaltwin/model', method: 'put', data: data })
}
export function delThreeDModel(modelIds) {
  return request({ url: '/system/digitaltwin/model/' + modelIds, method: 'delete' })
}
export function getModelTree() {
  return request({ url: '/system/digitaltwin/model/tree', method: 'get' })
}
