import request from '@/utils/request'

// 查询报警规则列表
export function listAlarmRule(query) {
  return request({
    url: '/hospital/alarmRule/list',
    method: 'get',
    params: query
  })
}

// 查询报警规则详细
export function getAlarmRule(id) {
  return request({
    url: '/hospital/alarmRule/info/' + id,
    method: 'get'
  })
}

// 新增报警规则
export function addAlarmRule(data) {
  return request({
    url: '/hospital/alarmRule',
    method: 'post',
    data: data
  })
}

// 修改报警规则
export function updateAlarmRule(data) {
  return request({
    url: '/hospital/alarmRule',
    method: 'put',
    data: data
  })
}

// 删除报警规则
export function delAlarmRule(ids) {
  return request({
    url: '/hospital/alarmRule/' + ids,
    method: 'delete'
  })
}

// 查询报警记录列表
export function listAlarmRecord(query) {
  return request({
    url: '/hospital/alarmRecord/list',
    method: 'get',
    params: query
  })
}

// 报警记录操作：action=confirm / process / done
export function actionAlarmRecord(id, action, handleRemark) {
  return request({
    url: '/hospital/alarmRecord/action',
    method: 'put',
    params: { id, action, handleRemark }
  })
}
