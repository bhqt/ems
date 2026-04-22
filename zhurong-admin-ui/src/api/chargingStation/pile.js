import request from '@/utils/request'

// 充电桩管理API
const api = {
  list: '/chargingStation/pile/list',
  info: '/chargingStation/pile/info/',
  add: '/chargingStation/pile/add',
  edit: '/chargingStation/pile/edit',
  remove: '/chargingStation/pile/remove/',
  openOrClose: '/chargingStation/pile/openOrClose',
  export: '/chargingStation/pile/export',
  statistics: '/chargingStation/pile/statistics'
}

// 查询充电桩列表
export function listPile(data) {
  return request({
    url: api.list,
    method: 'get',
    params: data
  })
}

// 获取充电桩详情
export function getPile(id) {
  return request({
    url: api.info + id,
    method: 'get'
  })
}

// 新增充电桩
export function addPile(data) {
  return request({
    url: api.add,
    method: 'post',
    data: data
  })
}

// 修改充电桩
export function updatePile(data) {
  return request({
    url: api.edit,
    method: 'put',
    data: data
  })
}

// 删除充电桩
export function delPile(ids) {
  return request({
    url: api.remove + ids.join(','),
    method: 'delete'
  })
}

// 启用/停用充电桩
export function openOrClosePile(data) {
  return request({
    url: api.openOrClose,
    method: 'put',
    params: data
  })
}

// 导出充电桩列表
export function exportChargingPileList(data) {
  return request({
    url: api.export,
    method: 'post',
    params: data,
    responseType: 'blob'
  })
}

// 获取充电桩统计信息
export function getChargingPileStatistics() {
  return request({
    url: api.statistics,
    method: 'get'
  })
}
