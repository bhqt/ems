import request from '@/utils/request'

export function listPriceForecast(query) {
  return request({ url: '/system/dispatch/priceForecast/list', method: 'get', params: query })
}
export function getPriceForecast(priceId) {
  return request({ url: '/system/dispatch/priceForecast/' + priceId, method: 'get' })
}
export function addPriceForecast(data) {
  return request({ url: '/system/dispatch/priceForecast', method: 'post', data: data })
}
export function updatePriceForecast(data) {
  return request({ url: '/system/dispatch/priceForecast', method: 'put', data: data })
}
export function delPriceForecast(priceIds) {
  return request({ url: '/system/dispatch/priceForecast/' + priceIds, method: 'delete' })
}
export function doPriceForecast(energyType, priceType) {
  return request({ url: '/system/dispatch/priceForecast/doForecast', method: 'post', params: { energyType, priceType } })
}
