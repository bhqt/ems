import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import iotAlarmRule from './autoee/iotAlarmRule';
export default {
  selectPageListIotAlarmRule,
  selectDataListByLikeIotAlarmRule,
  selectDataListByEqIotAlarmRule,
  selectDetailListByLikeIotAlarmRule,
  selectDetailListByEqIotAlarmRule,
  selectDataByPkIotAlarmRule,
  selectDetailByPkIotAlarmRule,
  addIotAlarmRule,
  updateNullValueByIotAlarmRule,
  updateNotNullValueByIotAlarmRule,
  submitTableEditIotAlarmRule,
  deleteIotAlarmRuleByIds,
  deleteIotAlarmRuleOneByOne,
};

// 查询报警规则分页列表
export function selectPageListIotAlarmRule(query) {
  return request({
    url: '/autoee/iotAlarmRule/selectPageListIotAlarmRule',
    method: 'get',
    params: query
  })
}

// 查询报警规则数据列表，不分页
export function selectDataListByLikeIotAlarmRule(query) {
  return request({
    url: '/autoee/iotAlarmRule/selectDataListByLikeIotAlarmRule',
    method: 'get',
    params: query
  })
}

// 精确查询报警规则数据列表，不分页
export function selectDataListByEqIotAlarmRule(query) {
  return request({
    url: '/autoee/iotAlarmRule/selectDataListByEqIotAlarmRule',
    method: 'get',
    params: query
  })
}

// 查询报警规则详细列表，不分页
export function selectDetailListByLikeIotAlarmRule(query) {
  return request({
    url: '/autoee/iotAlarmRule/selectDetailListByLikeIotAlarmRule',
    method: 'get',
    params: query
  })
}

// 精确查询报警规则详细列表，不分页
export function selectDetailListByEqIotAlarmRule(query) {
  return request({
    url: '/autoee/iotAlarmRule/selectDetailListByEqIotAlarmRule',
    method: 'get',
    params: query
  })
}

// 查询报警规则数据信息
export function selectDataByPkIotAlarmRule(id) {
  return request({
    url: '/autoee/iotAlarmRule/selectDataByPkIotAlarmRule/' + id,
    method: 'get'
  })
}
// 查询报警规则详细信息，已转码
export function selectDetailByPkIotAlarmRule(id) {
  return request({
    url: '/autoee/iotAlarmRule/selectDetailByPkIotAlarmRule/' + id,
    method: 'get'
  })
}

// 新增报警规则
export function addIotAlarmRule(data) {
  return request({
    url: '/autoee/iotAlarmRule',
    method: 'post',
    data: data
  })
}

// 修改报警规则：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIotAlarmRule(data) {
  return request({
    url: '/autoee/iotAlarmRule/updateNullValueByIotAlarmRule',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIotAlarmRule(data) {
  return request({
    url: '/autoee/iotAlarmRule/updateNotNullValueByIotAlarmRule',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIotAlarmRule(data) {
  return request({
    url: '/autoee/iotAlarmRule/submitTableEditIotAlarmRule',
    method: 'put',
    data: data
  })
}

// 删除报警规则
export function deleteIotAlarmRuleByIds(id) {
	return request({
		url: '/autoee/iotAlarmRule/deleteIotAlarmRuleByIds/' + id,
		method: 'delete'
	})
}

// 删除报警规则
export function deleteIotAlarmRuleOneByOne(id) {
  return request({
    url: '/autoee/iotAlarmRule/deleteIotAlarmRuleOneByOne/' + id,
    method: 'delete'
  })
}
