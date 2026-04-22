import request from '@/utils/request'

// 批次记录相关API

// 查询批次记录列表
export function listBatchRecord(query) {
  return request({
    url: '/energy/batch/list',
    method: 'get',
    params: query
  })
}

// 查询批次记录详细信息
export function getBatchRecord(batchId) {
  return request({
    url: '/energy/batch/getInfo/' + batchId,
    method: 'get'
  })
}

// 新增批次记录
export function addBatchRecord(data) {
  return request({
    url: '/energy/batch/add',
    method: 'post',
    data: data
  })
}

// 修改批次记录
export function updateBatchRecord(data) {
  return request({
    url: '/energy/batch/edit',
    method: 'put',
    data: data
  })
}

// 删除批次记录
export function delBatchRecord(batchIds) {
  return request({
    url: '/energy/batch/remove/' + batchIds,
    method: 'delete'
  })
}

// 能效标杆相关API

// 查询标杆标准列表
export function listBenchmarkStandard(query) {
  return request({
    url: '/energy/benchmark/list',
    method: 'get',
    params: query
  })
}

// 查询标杆标准详细信息
export function getBenchmarkStandard(standardId) {
  return request({
    url: '/energy/benchmark/getInfo/' + standardId,
    method: 'get'
  })
}

// 新增标杆标准
export function addBenchmarkStandard(data) {
  return request({
    url: '/energy/benchmark/add',
    method: 'post',
    data: data
  })
}

// 修改标杆标准
export function updateBenchmarkStandard(data) {
  return request({
    url: '/energy/benchmark/edit',
    method: 'put',
    data: data
  })
}

// 删除标杆标准
export function delBenchmarkStandard(standardIds) {
  return request({
    url: '/energy/benchmark/remove/' + standardIds,
    method: 'delete'
  })
}

// 能源平衡相关API

// 查询能源平衡列表
export function listEnergyBalance(query) {
  return request({
    url: '/energy/balance/list',
    method: 'get',
    params: query
  })
}

// 查询能源平衡详细信息
export function getEnergyBalance(balanceId) {
  return request({
    url: '/energy/balance/getInfo/' + balanceId,
    method: 'get'
  })
}

// 新增能源平衡
export function addEnergyBalance(data) {
  return request({
    url: '/energy/balance/add',
    method: 'post',
    data: data
  })
}

// 修改能源平衡
export function updateEnergyBalance(data) {
  return request({
    url: '/energy/balance/edit',
    method: 'put',
    data: data
  })
}

// 删除能源平衡
export function delEnergyBalance(balanceIds) {
  return request({
    url: '/energy/balance/remove/' + balanceIds,
    method: 'delete'
  })
}

// 计算能源平衡
export function calculateEnergyBalance(data) {
  return request({
    url: '/energy/balance/calculate',
    method: 'post',
    data: data
  })
}

// 能源质量相关API

// 查询能源质量列表
export function listEnergyQuality(query) {
  return request({
    url: '/energy/quality/list',
    method: 'get',
    params: query
  })
}

// 查询能源质量详细信息
export function getEnergyQuality(qualityId) {
  return request({
    url: '/energy/quality/getInfo/' + qualityId,
    method: 'get'
  })
}

// 新增能源质量
export function addEnergyQuality(data) {
  return request({
    url: '/energy/quality/add',
    method: 'post',
    data: data
  })
}

// 修改能源质量
export function updateEnergyQuality(data) {
  return request({
    url: '/energy/quality/edit',
    method: 'put',
    data: data
  })
}

// 删除能源质量
export function delEnergyQuality(qualityIds) {
  return request({
    url: '/energy/quality/remove/' + qualityIds,
    method: 'delete'
  })
}

// 能源趋势相关API

// 获取日趋势
export function getDayTrend(query) {
  return request({
    url: '/energy/trend/day',
    method: 'get',
    params: query
  })
}

// 获取月趋势
export function getMonthTrend(query) {
  return request({
    url: '/energy/trend/month',
    method: 'get',
    params: query
  })
}

// 获取年趋势
export function getYearTrend(query) {
  return request({
    url: '/energy/trend/year',
    method: 'get',
    params: query
  })
}

// 获取环比数据
export function getChainData(query) {
  return request({
    url: '/energy/chain',
    method: 'get',
    params: query
  })
}

// 获取日用电功率
export function getDailyP(query) {
  return request({
    url: '/energy/daily/power',
    method: 'get',
    params: query
  })
}

// 获取日水流量
export function getWTrendByDay(query) {
  return request({
    url: '/energy/daily/water',
    method: 'get',
    params: query
  })
}

// 获取年份分析数据
export function getYearAnalysis(query) {
  return request({
    url: '/energy/analysis/year',
    method: 'get',
    params: query
  })
}

// 获取设备环比数据
export function getChainByDevice(query) {
  return request({
    url: '/energy/chain/device',
    method: 'get',
    params: query
  })
}

// 获取月度水流量趋势
export function getWTrendByMonth(query) {
  return request({
    url: '/energy/trend/month',
    method: 'get',
    params: query
  })
}

// 获取年度水流量趋势
export function getWTrendByYear(query) {
  return request({
    url: '/energy/trend/year',
    method: 'get',
    params: query
  })
}

// 获取能源流向数据
export function getFlowData(query) {
  return request({
    url: '/energy/flow',
    method: 'get',
    params: query
  })
}

// 获取能耗费用报表
export function getConsumptionExpenseReport(query) {
  return request({
    url: '/energy/expense/report',
    method: 'get',
    params: query
  })
}

// 获取能耗损耗分析
export function getLossAnalysis(query) {
  return request({
    url: '/energy/loss/analysis',
    method: 'get',
    params: query
  })
}

// 获取今日用能统计
export function getConsumptionStatistics(query) {
  return request({
    url: '/data/energy/getConsumptionStatistics',
    method: 'get',
    params: query
  })
}

// 获取今日能源趋势和碳排放量
export function getTrendAndCarbon(query) {
  return request({
    url: '/data/energy/getTrendAndCarbon',
    method: 'get',
    params: query
  })
}

// 获取设备状态
export function getAllStatus(query) {
  return request({
    url: '/equipment/getAllStatus',
    method: 'get',
    params: query
  })
}
