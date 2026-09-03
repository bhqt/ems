import request from '@/utils/request'

// 查询设备实时监测总览
export function getMonitorOverview(query) {
  return request({
    url: '/hospital/monitor/overview',
    method: 'get',
    params: query
  })
}

// 查询单设备近期趋势数据点
export function getMonitorTrend(deviceId, metricCode, limit) {
  return request({
    url: '/hospital/monitor/trend',
    method: 'get',
    params: { deviceId, metricCode, limit }
  })
}
