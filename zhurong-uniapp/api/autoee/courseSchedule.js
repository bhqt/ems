import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import courseSchedule from './autoee/courseSchedule';
export default {
  selectPageListCourseSchedule,
  selectDataListByLikeCourseSchedule,
  selectDataListByEqCourseSchedule,
  selectDetailListByLikeCourseSchedule,
  selectDetailListByEqCourseSchedule,
  selectDataByPkCourseSchedule,
  selectDetailByPkCourseSchedule,
  addCourseSchedule,
  updateNullValueByCourseSchedule,
  updateNotNullValueByCourseSchedule,
  submitTableEditCourseSchedule,
  deleteCourseScheduleByIds,
  deleteCourseScheduleOneByOne,
};

// 查询课查询分页列表
export function selectPageListCourseSchedule(query) {
  return request({
    url: '/autoee/courseSchedule/selectPageListCourseSchedule',
    method: 'get',
    params: query
  })
}

// 查询课查询数据列表，不分页
export function selectDataListByLikeCourseSchedule(query) {
  return request({
    url: '/autoee/courseSchedule/selectDataListByLikeCourseSchedule',
    method: 'get',
    params: query
  })
}

// 精确查询课查询数据列表，不分页
export function selectDataListByEqCourseSchedule(query) {
  return request({
    url: '/autoee/courseSchedule/selectDataListByEqCourseSchedule',
    method: 'get',
    params: query
  })
}

// 查询课查询详细列表，不分页
export function selectDetailListByLikeCourseSchedule(query) {
  return request({
    url: '/autoee/courseSchedule/selectDetailListByLikeCourseSchedule',
    method: 'get',
    params: query
  })
}

// 精确查询课查询详细列表，不分页
export function selectDetailListByEqCourseSchedule(query) {
  return request({
    url: '/autoee/courseSchedule/selectDetailListByEqCourseSchedule',
    method: 'get',
    params: query
  })
}

// 查询课查询数据信息
export function selectDataByPkCourseSchedule(id) {
  return request({
    url: '/autoee/courseSchedule/selectDataByPkCourseSchedule/' + id,
    method: 'get'
  })
}
// 查询课查询详细信息，已转码
export function selectDetailByPkCourseSchedule(id) {
  return request({
    url: '/autoee/courseSchedule/selectDetailByPkCourseSchedule/' + id,
    method: 'get'
  })
}

// 新增课查询
export function addCourseSchedule(data) {
  return request({
    url: '/autoee/courseSchedule',
    method: 'post',
    data: data
  })
}

// 修改课查询：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByCourseSchedule(data) {
  return request({
    url: '/autoee/courseSchedule/updateNullValueByCourseSchedule',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByCourseSchedule(data) {
  return request({
    url: '/autoee/courseSchedule/updateNotNullValueByCourseSchedule',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditCourseSchedule(data) {
  return request({
    url: '/autoee/courseSchedule/submitTableEditCourseSchedule',
    method: 'put',
    data: data
  })
}

// 删除课查询
export function deleteCourseScheduleByIds(id) {
	return request({
		url: '/autoee/courseSchedule/deleteCourseScheduleByIds/' + id,
		method: 'delete'
	})
}

// 删除课查询
export function deleteCourseScheduleOneByOne(id) {
  return request({
    url: '/autoee/courseSchedule/deleteCourseScheduleOneByOne/' + id,
    method: 'delete'
  })
}
