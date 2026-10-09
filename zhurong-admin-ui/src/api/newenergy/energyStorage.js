import request from '@/utils/request'

// 查询储能系统列表
export function listEnergyStorage(query) {
  return request({
    url: '/system/newenergy/energyStorage/list',
    method: 'get',
    params: query
  })
}

// 查询储能系统详细
export function getEnergyStorage(id) {
  return request({
    url: '/newenergy/energyStorage/' + id,
    method: 'get'
  })
}

// 新增储能系统
export function addEnergyStorage(data) {
  return request({
    url: '/system/newenergy/energyStorage',
    method: 'post',
    data: data
  })
}

// 修改储能系统
export function updateEnergyStorage(data) {
  return request({
    url: '/system/newenergy/energyStorage',
    method: 'put',
    data: data
  })
}

// 删除储能系统
export function delEnergyStorage(id) {
  return request({
    url: '/system/newenergy/energyStorage/' + id,
    method: 'delete'
  })
}

// 导出储能系统
export function exportEnergyStorage(query) {
  return request({
    url: '/system/newenergy/energyStorage/export',
    method: 'post',
    data: query,
    responseType: 'blob'
  })
}

// 更新储能系统状态
export function updateStorageStatus(id, status) {
  return request({
    url: '/system/newenergy/energyStorage/updateStatus/' + id + '/' + status,
    method: 'put'
  })
}

// 获取储能系统统计数据
export function getStorageStatistics() {
  return request({
    url: '/system/newenergy/energyStorage/statistics',
    method: 'get'
  })
}

// 获取储能系统实时数据
export function getStorageRealTimeData(storageId) {
  return request({
    url: '/system/newenergy/energyStorage/realTimeData/' + storageId,
    method: 'get'
  })
}

// 获取充放电统计
export function getChargeDischargeStatistics(storageId, dateType, startTime, endTime) {
  return request({
    url: '/system/newenergy/energyStorage/chargeDischargeStatistics',
    method: 'get',
    params: {
      storageId,
      dateType,
      startTime,
      endTime
    }
  })
}

// 获取电池组状态统计
export function getBatteryStatusStatistics(storageId) {
  return request({
    url: '/system/newenergy/energyStorage/batteryStatusStatistics/' + storageId,
    method: 'get'
  })
}
