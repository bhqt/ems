import request from '@/utils/request'

// 查询院区列表
export function listArea(query) {
  return request({
    url: '/hospital/area/list',
    method: 'get',
    params: query
  })
}

// 院区下拉选项
export function getAreaOptions(query) {
  return request({
    url: '/hospital/area/options',
    method: 'get',
    params: query
  })
}

// 查询院区详情
export function getArea(id) {
  return request({
    url: '/hospital/area/info/' + id,
    method: 'get'
  })
}

// 新增院区
export function addArea(data) {
  return request({
    url: '/hospital/area',
    method: 'post',
    data: data
  })
}

// 修改院区
export function updateArea(data) {
  return request({
    url: '/hospital/area',
    method: 'put',
    data: data
  })
}

// 删除院区
export function delArea(ids) {
  return request({
    url: '/hospital/area/' + ids,
    method: 'delete'
  })
}
