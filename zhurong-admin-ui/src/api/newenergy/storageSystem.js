import request from '@/utils/request'

// 查询储能系统列表
export function listStorageSystem(query) {
  return request({
    url: '/system/newenergy/storage/list',
    method: 'get',
    params: query
  })
}

// 查询储能系统详细
export function getStorageSystem(systemId) {
  return request({
    url: '/system/newenergy/storage/' + systemId,
    method: 'get'
  })
}

// 新增储能系统
export function addStorageSystem(data) {
  return request({
    url: '/system/newenergy/storage',
    method: 'post',
    data: data
  })
}

// 修改储能系统
export function updateStorageSystem(data) {
  return request({
    url: '/system/newenergy/storage',
    method: 'put',
    data: data
  })
}

// 删除储能系统
export function delStorageSystem(systemIds) {
  return request({
    url: '/system/newenergy/storage/' + systemIds,
    method: 'delete'
  })
}

// 导出储能系统
export function exportStorageSystem(query) {
  return request({
    url: '/system/newenergy/storage/export',
    method: 'post',
    params: query
  })
}
