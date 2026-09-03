import request from '@/utils/request'

// 全院能耗概览（院区/科室/设备钻取，附环比）
export function getEnergyOverview(query) {
  return request({
    url: '/hospital/energy/overview',
    method: 'get',
    params: query
  })
}

// 能耗趋势（DAY/HOUR）
export function getEnergyTrend(query) {
  return request({
    url: '/hospital/energy/trend',
    method: 'get',
    params: query
  })
}

// 设备耗电排名
export function getEnergyRank(query) {
  return request({
    url: '/hospital/energy/rank',
    method: 'get',
    params: query
  })
}

// 设备能效评估
export function getEfficiency(query) {
  return request({
    url: '/hospital/energy/efficiency',
    method: 'get',
    params: query
  })
}

// 节能建议清单
export function getSuggestions(query) {
  return request({
    url: '/hospital/energy/suggestions',
    method: 'get',
    params: query
  })
}

// 分项能耗汇总（照明/空调/医疗设备/动力等）
export function getEnergyCategory(query) {
  return request({
    url: '/hospital/energy/category',
    method: 'get',
    params: query
  })
}

// 分项能耗按天趋势
export function getEnergyCategoryTrend(query) {
  return request({
    url: '/hospital/energy/categoryTrend',
    method: 'get',
    params: query
  })
}
