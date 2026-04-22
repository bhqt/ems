import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import iotAlarmData from './autoee/iotAlarmData';
export default {
  selectPageListIotAlarmData,
  selectDataListByLikeIotAlarmData,
  selectDataListByEqIotAlarmData,
  selectDetailListByLikeIotAlarmData,
  selectDetailListByEqIotAlarmData,
  selectDataByPkIotAlarmData,
  selectDetailByPkIotAlarmData,
  addIotAlarmData,
  updateNullValueByIotAlarmData,
  updateNotNullValueByIotAlarmData,
  submitTableEditIotAlarmData,
  deleteIotAlarmDataByIds,
  deleteIotAlarmDataOneByOne,
};

// 查询实时报警分页列表
export function selectPageListIotAlarmData(query) {
  return request({
    url: '/autoee/iotAlarmData/selectPageListIotAlarmData',
    method: 'get',
    params: query
  })
}

// 查询实时报警数据列表，不分页
export function selectDataListByLikeIotAlarmData(query) {
  return request({
    url: '/autoee/iotAlarmData/selectDataListByLikeIotAlarmData',
    method: 'get',
    params: query
  })
}

// 精确查询实时报警数据列表，不分页
export function selectDataListByEqIotAlarmData(query) {
  return request({
    url: '/autoee/iotAlarmData/selectDataListByEqIotAlarmData',
    method: 'get',
    params: query
  })
}

// 查询实时报警详细列表，不分页
export function selectDetailListByLikeIotAlarmData(query) {
  return request({
    url: '/autoee/iotAlarmData/selectDetailListByLikeIotAlarmData',
    method: 'get',
    params: query
  })
}

// 精确查询实时报警详细列表，不分页
export function selectDetailListByEqIotAlarmData(query) {
  return request({
    url: '/autoee/iotAlarmData/selectDetailListByEqIotAlarmData',
    method: 'get',
    params: query
  })
}

// 查询实时报警数据信息
export function selectDataByPkIotAlarmData(id) {
  return request({
    url: '/autoee/iotAlarmData/selectDataByPkIotAlarmData/' + id,
    method: 'get'
  })
}
// 查询实时报警详细信息，已转码
export function selectDetailByPkIotAlarmData(id) {
  return request({
    url: '/autoee/iotAlarmData/selectDetailByPkIotAlarmData/' + id,
    method: 'get'
  })
}

// 新增实时报警
export function addIotAlarmData(data) {
  return request({
    url: '/autoee/iotAlarmData',
    method: 'post',
    data: data
  })
}

// 修改实时报警：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIotAlarmData(data) {
  return request({
    url: '/autoee/iotAlarmData/updateNullValueByIotAlarmData',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIotAlarmData(data) {
  return request({
    url: '/autoee/iotAlarmData/updateNotNullValueByIotAlarmData',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIotAlarmData(data) {
  return request({
    url: '/autoee/iotAlarmData/submitTableEditIotAlarmData',
    method: 'put',
    data: data
  })
}

// 删除实时报警
export function deleteIotAlarmDataByIds(id) {
	return request({
		url: '/autoee/iotAlarmData/deleteIotAlarmDataByIds/' + id,
		method: 'delete'
	})
}

// 删除实时报警
export function deleteIotAlarmDataOneByOne(id) {
  return request({
    url: '/autoee/iotAlarmData/deleteIotAlarmDataOneByOne/' + id,
    method: 'delete'
  })
}
