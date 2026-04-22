import request from '@/utils/request'

// 微电网管理API
const api = {
  list: '/newenergy/microGrid/list',
  info: '/newenergy/microGrid/info/',
  add: '/newenergy/microGrid/add',
  edit: '/newenergy/microGrid/edit',
  remove: '/newenergy/microGrid/remove/',
  export: '/newenergy/microGrid/export',
  statistics: '/newenergy/microGrid/statistics'
}

// 查询微电网列表
export function listMicroGrid(data) {
  return request({
    url: api.list,
    method: 'get',
    params: data
  })
}

// 获取微电网详情
export function getMicroGrid(id) {
  return request({
    url: api.info + id,
    method: 'get'
  })
}

// 新增微电网
export function addMicroGrid(data) {
  return request({
    url: api.add,
    method: 'post',
    data: data
  })
}

// 修改微电网
export function updateMicroGrid(data) {
  return request({
    url: api.edit,
    method: 'put',
    data: data
  })
}

// 删除微电网
export function deleteMicroGrid(ids) {
  return request({
    url: api.remove + ids.join(','),
    method: 'delete'
  })
}

// 导出微电网列表
export function exportMicroGrid(data) {
  return request({
    url: api.export,
    method: 'post',
    params: data,
    responseType: 'blob'
  })
}

// 获取微电网统计信息
export function getMicroGridStatistics() {
  return request({
    url: api.statistics,
    method: 'get'
  })
}
