import request from '@/utils/request'

// 查询指标列表
export function listMetric(query) {
  return request({
    url: '/hospital/metric/list',
    method: 'get',
    params: query
  })
}

// 查询指标详细
export function getMetric(id) {
  return request({
    url: '/hospital/metric/info/' + id,
    method: 'get'
  })
}

// 新增指标
export function addMetric(data) {
  return request({
    url: '/hospital/metric',
    method: 'post',
    data: data
  })
}

// 修改指标
export function updateMetric(data) {
  return request({
    url: '/hospital/metric',
    method: 'put',
    data: data
  })
}

// 删除指标
export function delMetric(ids) {
  return request({
    url: '/hospital/metric/' + ids,
    method: 'delete'
  })
}
