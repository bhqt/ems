import request from '@/utils/request'

export function listForecast(query) {
  return request({
    url: '/system/dispatch/forecast/list',
    method: 'get',
    params: query
  })
}

export function getForecast(forecastId) {
  return request({
    url: '/system/dispatch/forecast/' + forecastId,
    method: 'get'
  })
}

export function addForecast(data) {
  return request({
    url: '/system/dispatch/forecast',
    method: 'post',
    data: data
  })
}

export function updateForecast(data) {
  return request({
    url: '/system/dispatch/forecast',
    method: 'put',
    data: data
  })
}

export function delForecast(forecastIds) {
  return request({
    url: '/system/dispatch/forecast/' + forecastIds,
    method: 'delete'
  })
}

export function exportForecast(query) {
  return request({
    url: '/system/dispatch/forecast/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
