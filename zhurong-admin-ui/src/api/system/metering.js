import request from '@/utils/request'

// 计量器具相关API

// 查询计量器具列表
export function listMeterInfo(query) {
  return request({
    url: '/metering/info/list',
    method: 'get',
    params: query
  })
}

// 查询计量器具详细信息
export function getMeterInfo(meterId) {
  return request({
    url: '/metering/info/getInfo/' + meterId,
    method: 'get'
  })
}

// 新增计量器具
export function addMeterInfo(data) {
  return request({
    url: '/metering/info/add',
    method: 'post',
    data: data
  })
}

// 修改计量器具
export function updateMeterInfo(data) {
  return request({
    url: '/metering/info/edit',
    method: 'put',
    data: data
  })
}

// 删除计量器具
export function delMeterInfo(meterIds) {
  return request({
    url: '/metering/info/remove/' + meterIds,
    method: 'delete'
  })
}

// 校准计划相关API

// 查询校准计划列表
export function listCalibrationPlan(query) {
  return request({
    url: '/metering/plan/list',
    method: 'get',
    params: query
  })
}

// 查询校准计划详细信息
export function getCalibrationPlan(planId) {
  return request({
    url: '/metering/plan/getInfo/' + planId,
    method: 'get'
  })
}

// 新增校准计划
export function addCalibrationPlan(data) {
  return request({
    url: '/metering/plan/add',
    method: 'post',
    data: data
  })
}

// 修改校准计划
export function updateCalibrationPlan(data) {
  return request({
    url: '/metering/plan/edit',
    method: 'put',
    data: data
  })
}

// 删除校准计划
export function delCalibrationPlan(planIds) {
  return request({
    url: '/metering/plan/remove/' + planIds,
    method: 'delete'
  })
}

// 执行校准计划
export function executeCalibrationPlan(planId) {
  return request({
    url: '/metering/plan/execute/' + planId,
    method: 'post'
  })
}

// 校准记录相关API

// 查询校准记录列表
export function listCalibrationRecord(query) {
  return request({
    url: '/metering/record/list',
    method: 'get',
    params: query
  })
}

// 查询校准记录详细信息
export function getCalibrationRecord(recordId) {
  return request({
    url: '/metering/record/getInfo/' + recordId,
    method: 'get'
  })
}

// 新增校准记录
export function addCalibrationRecord(data) {
  return request({
    url: '/metering/record/add',
    method: 'post',
    data: data
  })
}

// 修改校准记录
export function updateCalibrationRecord(data) {
  return request({
    url: '/metering/record/edit',
    method: 'put',
    data: data
  })
}

// 删除校准记录
export function delCalibrationRecord(recordIds) {
  return request({
    url: '/metering/record/remove/' + recordIds,
    method: 'delete'
  })
}
