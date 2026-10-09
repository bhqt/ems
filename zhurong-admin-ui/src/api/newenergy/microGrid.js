import request from '@/utils/request'

// 查询微电网列表
export function listMicroGrid(query) {
  return request({
    url: '/system/newenergy/microgrid/list',
    method: 'get',
    params: query
  })
}

// 查询微电网详细
export function getMicroGrid(id) {
  return request({
    url: '/system/newenergy/microGrid/' + id,
    method: 'get'
  })
}

// 新增微电网
export function addMicroGrid(data) {
  return request({
    url: '/system/newenergy/microgrid',
    method: 'post',
    data: data
  })
}

// 修改微电网
export function updateMicroGrid(data) {
  return request({
    url: '/system/newenergy/microgrid',
    method: 'put',
    data: data
  })
}

// 删除微电网
export function deleteMicroGrid(ids) {
  return request({
    url: '/system/newenergy/microgrid/' + ids,
    method: 'delete'
  })
}

// 导出微电网
export function exportMicroGrid(query) {
  return request({
    url: '/system/newenergy/microgrid/export',
    method: 'post',
    params: query
  })
}

// 获取微电网统计数据
export function getMicroGridStatistics() {
  return request({
    url: '/system/newenergy/microgrid/statistics',
    method: 'get'
  })
}
