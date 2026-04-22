import request from '@/utils/request'

// 查询同步模板列表
export function listSyncTemplate(query) {
  return request({
    url: '/system/integration/template/list',
    method: 'get',
    params: query
  })
}

// 查询同步模板详情
export function getSyncTemplate(id) {
  return request({
    url: `/system/integration/template/${id}`,
    method: 'get'
  })
}

// 新增同步模板
export function addSyncTemplate(data) {
  return request({
    url: '/system/integration/template',
    method: 'post',
    data: data
  })
}

// 修改同步模板
export function updateSyncTemplate(data) {
  return request({
    url: '/system/integration/template',
    method: 'put',
    data: data
  })
}

// 删除同步模板
export function delSyncTemplate(ids) {
  return request({
    url: `/system/integration/template/${ids}`,
    method: 'delete'
  })
}

// 复制同步模板
export function copySyncTemplate(data) {
  return request({
    url: '/system/integration/template/copy',
    method: 'post',
    params: data
  })
}

// 根据模板类型查询模板
export function getTemplateByType(templateType) {
  return request({
    url: `/system/integration/template/byType/${templateType}`,
    method: 'get'
  })
}
