import request from '@/utils/request'

export function listIntegrationConfig(query) {
  return request({
    url: '/system/integration/config/list',
    method: 'get',
    params: query
  })
}

export function getIntegrationConfig(id) {
  return request({
    url: '/system/integration/config/' + id,
    method: 'get'
  })
}

export function addIntegrationConfig(data) {
  return request({
    url: '/system/integration/config',
    method: 'post',
    data: data
  })
}

export function updateIntegrationConfig(data) {
  return request({
    url: '/system/integration/config',
    method: 'put',
    data: data
  })
}

export function delIntegrationConfig(id) {
  return request({
    url: '/system/integration/config/' + id,
    method: 'delete'
  })
}

export function enableIntegrationConfig(id) {
  return request({
    url: '/system/integration/config/enable/' + id,
    method: 'put'
  })
}

export function disableIntegrationConfig(id) {
  return request({
    url: '/system/integration/config/disable/' + id,
    method: 'put'
  })
}

export function getIntegrationConfigByCode(systemCode) {
  return request({
    url: '/system/integration/config/byCode/' + systemCode,
    method: 'get'
  })
}
