import request from '@/utils/request'

export function listWeatherForecast(query) {
  return request({ url: '/system/dispatch/weatherForecast/list', method: 'get', params: query })
}
export function getWeatherForecast(weatherId) {
  return request({ url: '/system/dispatch/weatherForecast/' + weatherId, method: 'get' })
}
export function addWeatherForecast(data) {
  return request({ url: '/system/dispatch/weatherForecast', method: 'post', data: data })
}
export function updateWeatherForecast(data) {
  return request({ url: '/system/dispatch/weatherForecast', method: 'put', data: data })
}
export function delWeatherForecast(weatherIds) {
  return request({ url: '/system/dispatch/weatherForecast/' + weatherIds, method: 'delete' })
}
export function doWeatherForecast() {
  return request({ url: '/system/dispatch/weatherForecast/doForecast', method: 'post' })
}
