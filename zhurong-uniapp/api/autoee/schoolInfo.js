import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import schoolInfo from './autoee/schoolInfo';
export default {
  selectPageListSchoolInfo,
  selectDataListByLikeSchoolInfo,
  selectDataListByEqSchoolInfo,
  selectDetailListByLikeSchoolInfo,
  selectDetailListByEqSchoolInfo,
  selectDataByPkSchoolInfo,
  selectDetailByPkSchoolInfo,
  addSchoolInfo,
  updateNullValueBySchoolInfo,
  updateNotNullValueBySchoolInfo,
  submitTableEditSchoolInfo,
  deleteSchoolInfoByIds,
  deleteSchoolInfoOneByOne,
};

// 查询学校信息分页列表
export function selectPageListSchoolInfo(query) {
  return request({
    url: '/autoee/schoolInfo/selectPageListSchoolInfo',
    method: 'get',
    params: query
  })
}

// 查询学校信息数据列表，不分页
export function selectDataListByLikeSchoolInfo(query) {
  return request({
    url: '/autoee/schoolInfo/selectDataListByLikeSchoolInfo',
    method: 'get',
    params: query
  })
}

// 精确查询学校信息数据列表，不分页
export function selectDataListByEqSchoolInfo(query) {
  return request({
    url: '/autoee/schoolInfo/selectDataListByEqSchoolInfo',
    method: 'get',
    params: query
  })
}

// 查询学校信息详细列表，不分页
export function selectDetailListByLikeSchoolInfo(query) {
  return request({
    url: '/autoee/schoolInfo/selectDetailListByLikeSchoolInfo',
    method: 'get',
    params: query
  })
}

// 精确查询学校信息详细列表，不分页
export function selectDetailListByEqSchoolInfo(query) {
  return request({
    url: '/autoee/schoolInfo/selectDetailListByEqSchoolInfo',
    method: 'get',
    params: query
  })
}

// 查询学校信息数据信息
export function selectDataByPkSchoolInfo(id) {
  return request({
    url: '/autoee/schoolInfo/selectDataByPkSchoolInfo/' + id,
    method: 'get'
  })
}
// 查询学校信息详细信息，已转码
export function selectDetailByPkSchoolInfo(id) {
  return request({
    url: '/autoee/schoolInfo/selectDetailByPkSchoolInfo/' + id,
    method: 'get'
  })
}

// 新增学校信息
export function addSchoolInfo(data) {
  return request({
    url: '/autoee/schoolInfo',
    method: 'post',
    data: data
  })
}

// 修改学校信息：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueBySchoolInfo(data) {
  return request({
    url: '/autoee/schoolInfo/updateNullValueBySchoolInfo',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueBySchoolInfo(data) {
  return request({
    url: '/autoee/schoolInfo/updateNotNullValueBySchoolInfo',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditSchoolInfo(data) {
  return request({
    url: '/autoee/schoolInfo/submitTableEditSchoolInfo',
    method: 'put',
    data: data
  })
}

// 删除学校信息
export function deleteSchoolInfoByIds(id) {
	return request({
		url: '/autoee/schoolInfo/deleteSchoolInfoByIds/' + id,
		method: 'delete'
	})
}

// 删除学校信息
export function deleteSchoolInfoOneByOne(id) {
  return request({
    url: '/autoee/schoolInfo/deleteSchoolInfoOneByOne/' + id,
    method: 'delete'
  })
}
