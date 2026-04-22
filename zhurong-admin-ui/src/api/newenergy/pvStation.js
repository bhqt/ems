import request from '@/utils/request'

// 查询光伏电站列表
export function listPvStation(query) {
  return request({
    url: '/system/newenergy/pv/station/list',
    method: 'get',
    params: query
  })
}

// 查询光伏电站详细
export function getPvStation(id) {
  return request({
    url: '/system/newenergy/pv/station/' + id,
    method: 'get'
  })
}

// 新增光伏电站
export function addPvStation(data) {
  return request({
    url: '/system/newenergy/pv/station',
    method: 'post',
    data: data
  })
}

// 修改光伏电站
export function updatePvStation(data) {
  return request({
    url: '/system/newenergy/pv/station',
    method: 'put',
    data: data
  })
}

// 删除光伏电站
export function delPvStation(stationIds) {
  return request({
    url: '/system/newenergy/pv/station/' + stationIds,
    method: 'delete'
  })
}

// 导出光伏电站
export function exportPvStation(query) {
  return request({
    url: '/system/newenergy/pv/station/export',
    method: 'post',
    params: query
  })
}

// 获取光伏电站统计数据
export function getStatistics() {
  return request({
    url: '/system/newenergy/pv/station/statistics',
    method: 'get'
  })
}
