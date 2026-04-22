import request from '@/utils/request'

// 充电站品牌管理API
const api = {
  list: '/chargingStation/brand/list',
  info: '/chargingStation/brand/info/',
  add: '/chargingStation/brand/add',
  edit: '/chargingStation/brand/edit',
  remove: '/chargingStation/brand/remove/',
  export: '/chargingStation/brand/export',
  statistics: '/chargingStation/brand/statistics'
}

// 查询品牌列表
export function listBrand(data) {
  return request({
    url: api.list,
    method: 'get',
    params: data
  })
}

// 获取品牌详情
export function getBrand(id) {
  return request({
    url: api.info + id,
    method: 'get'
  })
}

// 新增品牌
export function addBrand(data) {
  return request({
    url: api.add,
    method: 'post',
    data: data
  })
}

// 修改品牌
export function updateBrand(data) {
  return request({
    url: api.edit,
    method: 'put',
    data: data
  })
}

// 删除品牌
export function delBrand(ids) {
  return request({
    url: api.remove + ids.join(','),
    method: 'delete'
  })
}

// 导出品牌列表
export function exportBrandList(data) {
  return request({
    url: api.export,
    method: 'post',
    params: data,
    responseType: 'blob'
  })
}

// 获取品牌统计信息
export function getBrandStatistics() {
  return request({
    url: api.statistics,
    method: 'get'
  })
}
