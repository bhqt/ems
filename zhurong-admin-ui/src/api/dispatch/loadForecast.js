import request from '@/utils/request'

export function listLoadForecast(query) {
  return request({ url: '/system/dispatch/loadForecast/list', method: 'get', params: query })
}
export function getLoadForecast(loadId) {
  return request({ url: '/system/dispatch/loadForecast/' + loadId, method: 'get' })
}
export function addLoadForecast(data) {
  return request({ url: '/system/dispatch/loadForecast', method: 'post', data: data })
}
export function updateLoadForecast(data) {
  return request({ url: '/system/dispatch/loadForecast', method: 'put', data: data })
}
export function delLoadForecast(loadIds) {
  return request({ url: '/system/dispatch/loadForecast/' + loadIds, method: 'delete' })
}
export function doLoadForecast(forecastType, loadType) {
  return request({ url: '/system/dispatch/loadForecast/doForecast', method: 'post', params: { forecastType, loadType } })
}
