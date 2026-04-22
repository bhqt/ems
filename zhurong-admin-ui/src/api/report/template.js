import request from '@/utils/request'

// 查询报表模板列表
export function listTemplate(query) {
  return request({
    url: '/system/report/template/list',
    method: 'get',
    params: query
  })
}

// 查询报表模板详细信息
export function getTemplate(templateId) {
  return request({
    url: `/system/report/template/info/${templateId}`,
    method: 'get'
  })
}

// 新增报表模板
export function addTemplate(data) {
  return request({
    url: '/system/report/template/add',
    method: 'post',
    data: data
  })
}

// 修改报表模板
export function updateTemplate(data) {
  return request({
    url: '/system/report/template/edit',
    method: 'put',
    data: data
  })
}

// 删除报表模板
export function deleteTemplate(templateId) {
  return request({
    url: `/system/report/template/remove/${templateId}`,
    method: 'delete'
  })
}

// 批量删除报表模板
export function deleteTemplateByIds(templateIds) {
  return request({
    url: '/system/report/template/remove',
    method: 'delete',
    data: templateIds
  })
}

// 修改报表模板状态
export function changeStatus(templateId, status) {
  const data = {
    templateId,
    status
  }
  return request({
    url: '/system/report/template/changeStatus',
    method: 'put',
    data: data
  })
}

// 导出报表模板
export function exportTemplate(query) {
  return request({
    url: '/system/report/template/export',
    method: 'get',
    params: query
  })
}
