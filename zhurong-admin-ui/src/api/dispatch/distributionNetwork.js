import request from '@/utils/request'

export function listDistributionNetwork(query) {
  return request({
    url: '/system/dispatch/network/list',
    method: 'get',
    params: query
  })
}

export function getDistributionNetwork(networkId) {
  return request({
    url: '/system/dispatch/network/' + networkId,
    method: 'get'
  })
}

export function addDistributionNetwork(data) {
  return request({
    url: '/system/dispatch/network',
    method: 'post',
    data: data
  })
}

export function updateDistributionNetwork(data) {
  return request({
    url: '/system/dispatch/network',
    method: 'put',
    data: data
  })
}

export function delDistributionNetwork(networkIds) {
  return request({
    url: '/system/dispatch/network/' + networkIds,
    method: 'delete'
  })
}

export function exportDistributionNetwork(query) {
  return request({
    url: '/system/dispatch/network/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
