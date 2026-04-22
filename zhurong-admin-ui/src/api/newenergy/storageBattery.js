import request from '@/utils/request'

// 查询储能电池组列表
export function listStorageBattery(query) {
  return request({
    url: '/newenergy/storageBattery/list',
    method: 'get',
    params: query
  })
}

// 查询储能电池组详细
export function getStorageBattery(id) {
  return request({
    url: '/newenergy/storageBattery/' + id,
    method: 'get'
  })
}

// 新增储能电池组
export function addStorageBattery(data) {
  return request({
    url: '/newenergy/storageBattery',
    method: 'post',
    data: data
  })
}

// 修改储能电池组
export function updateStorageBattery(data) {
  return request({
    url: '/newenergy/storageBattery',
    method: 'put',
    data: data
  })
}

// 删除储能电池组
export function delStorageBattery(id) {
  return request({
    url: '/newenergy/storageBattery/' + id,
    method: 'delete'
  })
}

// 导出储能电池组
export function exportStorageBattery(query) {
  return request({
    url: '/newenergy/storageBattery/export',
    method: 'post',
    data: query,
    responseType: 'blob'
  })
}

// 更新电池组状态
export function updateBatteryStatus(id, status) {
  return request({
    url: '/newenergy/storageBattery/updateStatus/' + id + '/' + status,
    method: 'put'
  })
}

// 根据储能系统ID查询电池组列表
export function getBatteriesByStorageId(storageId) {
  return request({
    url: '/newenergy/storageBattery/byStorageId/' + storageId,
    method: 'get'
  })
}

// 获取电池组实时数据
export function getBatteryRealTimeData(batteryId) {
  return request({
    url: '/newenergy/storageBattery/realTimeData/' + batteryId,
    method: 'get'
  })
}

// 获取电池组历史数据
export function getBatteryHistoryData(batteryId, startTime, endTime, dataType) {
  return request({
    url: '/newenergy/storageBattery/historyData',
    method: 'get',
    params: {
      batteryId,
      startTime,
      endTime,
      dataType
    }
  })
}

// 获取电池组健康状态统计
export function getBatteryHealthStatistics(storageId) {
  return request({
    url: '/newenergy/storageBattery/healthStatistics/' + storageId,
    method: 'get'
  })
}
