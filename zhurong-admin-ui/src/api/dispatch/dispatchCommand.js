import request from '@/utils/request'

export function listDispatchCommand(query) {
  return request({
    url: '/system/dispatch/command/list',
    method: 'get',
    params: query
  })
}

export function getDispatchCommand(commandId) {
  return request({
    url: '/system/dispatch/command/' + commandId,
    method: 'get'
  })
}

export function addDispatchCommand(data) {
  return request({
    url: '/system/dispatch/command',
    method: 'post',
    data: data
  })
}

export function updateDispatchCommand(data) {
  return request({
    url: '/system/dispatch/command',
    method: 'put',
    data: data
  })
}

export function delDispatchCommand(commandIds) {
  return request({
    url: '/system/dispatch/command/' + commandIds,
    method: 'delete'
  })
}

export function executeCommand(commandId) {
  return request({
    url: '/system/dispatch/command/execute/' + commandId,
    method: 'put'
  })
}

export function exportDispatchCommand(query) {
  return request({
    url: '/system/dispatch/command/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
