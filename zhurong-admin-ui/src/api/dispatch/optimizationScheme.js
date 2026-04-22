import request from '@/utils/request'

export function listOptimizationScheme(query) {
  return request({
    url: '/system/dispatch/optimization/list',
    method: 'get',
    params: query
  })
}

export function getOptimizationScheme(schemeId) {
  return request({
    url: '/system/dispatch/optimization/' + schemeId,
    method: 'get'
  })
}

export function addOptimizationScheme(data) {
  return request({
    url: '/system/dispatch/optimization',
    method: 'post',
    data: data
  })
}

export function updateOptimizationScheme(data) {
  return request({
    url: '/system/dispatch/optimization',
    method: 'put',
    data: data
  })
}

export function delOptimizationScheme(schemeIds) {
  return request({
    url: '/system/dispatch/optimization/' + schemeIds,
    method: 'delete'
  })
}

export function executeOptimization(schemeId) {
  return request({
    url: '/system/dispatch/optimization/execute/' + schemeId,
    method: 'put'
  })
}

export function exportOptimizationScheme(query) {
  return request({
    url: '/system/dispatch/optimization/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
