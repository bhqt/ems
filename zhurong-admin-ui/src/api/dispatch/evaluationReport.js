import request from '@/utils/request'

export function listEvaluationReport(query) {
  return request({
    url: '/system/dispatch/evaluation/list',
    method: 'get',
    params: query
  })
}

export function getEvaluationReport(reportId) {
  return request({
    url: '/system/dispatch/evaluation/' + reportId,
    method: 'get'
  })
}

export function addEvaluationReport(data) {
  return request({
    url: '/system/dispatch/evaluation',
    method: 'post',
    data: data
  })
}

export function updateEvaluationReport(data) {
  return request({
    url: '/system/dispatch/evaluation',
    method: 'put',
    data: data
  })
}

export function delEvaluationReport(reportIds) {
  return request({
    url: '/system/dispatch/evaluation/' + reportIds,
    method: 'delete'
  })
}

export function exportEvaluationReport(query) {
  return request({
    url: '/system/dispatch/evaluation/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
