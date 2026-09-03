import request from '@/utils/request'

// 查询工作量列表
export function listWorkload(query) {
  return request({
    url: '/hospital/workload/list',
    method: 'get',
    params: query
  })
}

// 查询工作量详情
export function getWorkload(id) {
  return request({
    url: '/hospital/workload/info/' + id,
    method: 'get'
  })
}

// 新增工作量
export function addWorkload(data) {
  return request({
    url: '/hospital/workload',
    method: 'post',
    data: data
  })
}

// 修改工作量
export function updateWorkload(data) {
  return request({
    url: '/hospital/workload',
    method: 'put',
    data: data
  })
}

// 删除工作量
export function delWorkload(ids) {
  return request({
    url: '/hospital/workload/' + ids,
    method: 'delete'
  })
}
