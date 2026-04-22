import request from '@/utils/request'

export function listEnergyPlan(query) {
  return request({
    url: '/system/dispatch/energyPlan/list',
    method: 'get',
    params: query
  })
}

export function getEnergyPlan(planId) {
  return request({
    url: '/system/dispatch/energyPlan/' + planId,
    method: 'get'
  })
}

export function addEnergyPlan(data) {
  return request({
    url: '/system/dispatch/energyPlan',
    method: 'post',
    data: data
  })
}

export function updateEnergyPlan(data) {
  return request({
    url: '/system/dispatch/energyPlan',
    method: 'put',
    data: data
  })
}

export function delEnergyPlan(planIds) {
  return request({
    url: '/system/dispatch/energyPlan/' + planIds,
    method: 'delete'
  })
}

export function exportEnergyPlan(query) {
  return request({
    url: '/system/dispatch/energyPlan/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
