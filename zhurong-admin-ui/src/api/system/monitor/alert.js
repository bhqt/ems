import request from '@/utils/request'

// 查询监控告警列表
export function listMonitorAlert(query) {
  return request({
    url: '/system/monitor/alert/list',
    method: 'get',
    params: query
  })
}

// 查询监控告警详情
export function getMonitorAlert(id) {
  return request({
    url: `/system/monitor/alert/${id}`,
    method: 'get'
  })
}

// 处理监控告警
export function handleMonitorAlert(id, handleResult) {
  return request({
    url: `/system/monitor/alert/handle/${id}`,
    method: 'put',
    data: handleResult
  })
}

// 删除监控告警
export function delMonitorAlert(ids) {
  return request({
    url: `/system/monitor/alert/${ids}`,
    method: 'delete'
  })
}

// 检查系统健康状态
export function checkSystemHealth() {
  return request({
    url: '/system/monitor/health',
    method: 'get'
  })
}
