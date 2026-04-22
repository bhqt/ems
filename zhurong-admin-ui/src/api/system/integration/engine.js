import request from '@/utils/request'

export function executeSyncTask(taskId) {
  return request({
    url: '/system/integration/sync/engine/execute/' + taskId,
    method: 'post'
  })
}

export function executeAllSyncTasks() {
  return request({
    url: '/system/integration/sync/engine/executeAll',
    method: 'post'
  })
}

export function startSyncScheduler() {
  return request({
    url: '/system/integration/sync/engine/start',
    method: 'post'
  })
}

export function stopSyncScheduler() {
  return request({
    url: '/system/integration/sync/engine/stop',
    method: 'post'
  })
}

export function getSyncSchedulerStatus() {
  return request({
    url: '/system/integration/sync/engine/status',
    method: 'get'
  })
}
