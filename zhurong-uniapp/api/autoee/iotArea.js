import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import iotArea from './autoee/iotArea';
export default {
  selectPageListIotArea,
  selectDataListByLikeIotArea,
  selectDataListByEqIotArea,
  selectDetailListByLikeIotArea,
  selectDetailListByEqIotArea,
  selectDataByPkIotArea,
  selectDetailByPkIotArea,
  addIotArea,
  updateNullValueByIotArea,
  updateNotNullValueByIotArea,
  submitTableEditIotArea,
  deleteIotAreaByIds,
  deleteIotAreaOneByOne,
};

// 查询区域管理分页列表
export function selectPageListIotArea(query) {
  return request({
    url: '/autoee/iotArea/selectPageListIotArea',
    method: 'get',
    params: query
  })
}

// 查询区域管理数据列表，不分页
export function selectDataListByLikeIotArea(query) {
  return request({
    url: '/autoee/iotArea/selectDataListByLikeIotArea',
    method: 'get',
    params: query
  })
}

// 精确查询区域管理数据列表，不分页
export function selectDataListByEqIotArea(query) {
  return request({
    url: '/autoee/iotArea/selectDataListByEqIotArea',
    method: 'get',
    params: query
  })
}

// 查询区域管理详细列表，不分页
export function selectDetailListByLikeIotArea(query) {
  return request({
    url: '/autoee/iotArea/selectDetailListByLikeIotArea',
    method: 'get',
    params: query
  })
}

// 精确查询区域管理详细列表，不分页
export function selectDetailListByEqIotArea(query) {
  return request({
    url: '/autoee/iotArea/selectDetailListByEqIotArea',
    method: 'get',
    params: query
  })
}

// 查询区域管理数据信息
export function selectDataByPkIotArea(id) {
  return request({
    url: '/autoee/iotArea/selectDataByPkIotArea/' + id,
    method: 'get'
  })
}
// 查询区域管理详细信息，已转码
export function selectDetailByPkIotArea(id) {
  return request({
    url: '/autoee/iotArea/selectDetailByPkIotArea/' + id,
    method: 'get'
  })
}

// 新增区域管理
export function addIotArea(data) {
  return request({
    url: '/autoee/iotArea',
    method: 'post',
    data: data
  })
}

// 修改区域管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIotArea(data) {
  return request({
    url: '/autoee/iotArea/updateNullValueByIotArea',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIotArea(data) {
  return request({
    url: '/autoee/iotArea/updateNotNullValueByIotArea',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIotArea(data) {
  return request({
    url: '/autoee/iotArea/submitTableEditIotArea',
    method: 'put',
    data: data
  })
}

// 删除区域管理
export function deleteIotAreaByIds(id) {
	return request({
		url: '/autoee/iotArea/deleteIotAreaByIds/' + id,
		method: 'delete'
	})
}

// 删除区域管理
export function deleteIotAreaOneByOne(id) {
  return request({
    url: '/autoee/iotArea/deleteIotAreaOneByOne/' + id,
    method: 'delete'
  })
}
