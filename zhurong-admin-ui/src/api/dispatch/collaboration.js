import request from '@/utils/request'

export function listCollaboration(query) {
  return request({
    url: '/system/dispatch/collaboration/list',
    method: 'get',
    params: query
  })
}

export function getCollaboration(collabId) {
  return request({
    url: '/system/dispatch/collaboration/' + collabId,
    method: 'get'
  })
}

export function addCollaboration(data) {
  return request({
    url: '/system/dispatch/collaboration',
    method: 'post',
    data: data
  })
}

export function updateCollaboration(data) {
  return request({
    url: '/system/dispatch/collaboration',
    method: 'put',
    data: data
  })
}

export function delCollaboration(collabIds) {
  return request({
    url: '/system/dispatch/collaboration/' + collabIds,
    method: 'delete'
  })
}

export function executeCollaboration(collabId) {
  return request({
    url: '/system/dispatch/collaboration/execute/' + collabId,
    method: 'put'
  })
}

export function exportCollaboration(query) {
  return request({
    url: '/system/dispatch/collaboration/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
