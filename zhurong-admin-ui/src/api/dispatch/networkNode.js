import request from '@/utils/request'

export function listNetworkNode(query) {
  return request({
    url: '/system/dispatch/node/list',
    method: 'get',
    params: query
  })
}

export function listNetworkNodeByNetwork(networkId) {
  return request({
    url: '/system/dispatch/node/listByNetwork/' + networkId,
    method: 'get'
  })
}

export function getNetworkNode(nodeId) {
  return request({
    url: '/system/dispatch/node/' + nodeId,
    method: 'get'
  })
}

export function addNetworkNode(data) {
  return request({
    url: '/system/dispatch/node',
    method: 'post',
    data: data
  })
}

export function updateNetworkNode(data) {
  return request({
    url: '/system/dispatch/node',
    method: 'put',
    data: data
  })
}

export function delNetworkNode(nodeIds) {
  return request({
    url: '/system/dispatch/node/' + nodeIds,
    method: 'delete'
  })
}

export function exportNetworkNode(query) {
  return request({
    url: '/system/dispatch/node/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
