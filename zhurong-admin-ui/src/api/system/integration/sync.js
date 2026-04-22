import request from '@/utils/request'

export function listSyncTask(query) {
  return request({
    url: '/system/integration/sync/task/list',
    method: 'get',
    params: query
  })
}

export function getSyncTask(id) {
  return request({
    url: '/system/integration/sync/task/' + id,
    method: 'get'
  })
}

export function addSyncTask(data) {
  return request({
    url: '/system/integration/sync/task',
    method: 'post',
    data: data
  })
}

export function updateSyncTask(data) {
  return request({
    url: '/system/integration/sync/task',
    method: 'put',
    data: data
  })
}

export function delSyncTask(id) {
  return request({
    url: '/system/integration/sync/task/' + id,
    method: 'delete'
  })
}

export function getEnabledSyncTasks() {
  return request({
    url: '/system/integration/sync/task/enabled',
    method: 'get'
  })
}

export function enableSyncTask(id) {
  return request({
    url: '/system/integration/sync/task/enable/' + id,
    method: 'put'
  })
}

export function disableSyncTask(id) {
  return request({
    url: '/system/integration/sync/task/disable/' + id,
    method: 'put'
  })
}

export function listSyncExecution(query) {
  return request({
    url: '/system/integration/sync/execution/list',
    method: 'get',
    params: query
  })
}

export function getSyncExecution(id) {
  return request({
    url: '/system/integration/sync/execution/' + id,
    method: 'get'
  })
}

export function getSyncExecutionByTaskId(taskId) {
  return request({
    url: '/system/integration/sync/execution/byTaskId/' + taskId,
    method: 'get'
  })
}

export function getRecentSyncExecutions(limit) {
  return request({
    url: '/system/integration/sync/execution/recent/' + limit,
    method: 'get'
  })
}

export function getSyncStatistics(taskId) {
  return request({
    url: '/system/integration/sync/execution/statistics',
    method: 'get',
    params: { taskId }
  })
}
