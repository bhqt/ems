import request from '@/utils/request'

export function listInterfaceConfig(query) {
  return request({
    url: '/system/integration/interface/list',
    method: 'get',
    params: query
  })
}

export function getInterfaceConfig(id) {
  return request({
    url: '/system/integration/interface/' + id,
    method: 'get'
  })
}

export function addInterfaceConfig(data) {
  return request({
    url: '/system/integration/interface',
    method: 'post',
    data: data
  })
}

export function updateInterfaceConfig(data) {
  return request({
    url: '/system/integration/interface',
    method: 'put',
    data: data
  })
}

export function delInterfaceConfig(id) {
  return request({
    url: '/system/integration/interface/' + id,
    method: 'delete'
  })
}

export function getInterfaceConfigByConfigId(configId) {
  return request({
    url: '/system/integration/interface/byConfigId/' + configId,
    method: 'get'
  })
}

export function testInterfaceConnection(id) {
  return request({
    url: '/system/integration/interface/test/' + id,
    method: 'get'
  })
}
