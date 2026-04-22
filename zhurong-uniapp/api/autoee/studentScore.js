import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import studentScore from './autoee/studentScore';
export default {
  selectPageListStudentScore,
  selectDataListByLikeStudentScore,
  selectDataListByEqStudentScore,
  selectDetailListByLikeStudentScore,
  selectDetailListByEqStudentScore,
  selectDataByPkStudentScore,
  selectDetailByPkStudentScore,
  addStudentScore,
  updateNullValueByStudentScore,
  updateNotNullValueByStudentScore,
  submitTableEditStudentScore,
  deleteStudentScoreByIds,
  deleteStudentScoreOneByOne,
};

// 查询学生成绩分页列表
export function selectPageListStudentScore(query) {
  return request({
    url: '/autoee/studentScore/selectPageListStudentScore',
    method: 'get',
    params: query
  })
}

// 查询学生成绩数据列表，不分页
export function selectDataListByLikeStudentScore(query) {
  return request({
    url: '/autoee/studentScore/selectDataListByLikeStudentScore',
    method: 'get',
    params: query
  })
}

// 精确查询学生成绩数据列表，不分页
export function selectDataListByEqStudentScore(query) {
  return request({
    url: '/autoee/studentScore/selectDataListByEqStudentScore',
    method: 'get',
    params: query
  })
}

// 查询学生成绩详细列表，不分页
export function selectDetailListByLikeStudentScore(query) {
  return request({
    url: '/autoee/studentScore/selectDetailListByLikeStudentScore',
    method: 'get',
    params: query
  })
}

// 精确查询学生成绩详细列表，不分页
export function selectDetailListByEqStudentScore(query) {
  return request({
    url: '/autoee/studentScore/selectDetailListByEqStudentScore',
    method: 'get',
    params: query
  })
}

// 查询学生成绩数据信息
export function selectDataByPkStudentScore(id) {
  return request({
    url: '/autoee/studentScore/selectDataByPkStudentScore/' + id,
    method: 'get'
  })
}
// 查询学生成绩详细信息，已转码
export function selectDetailByPkStudentScore(id) {
  return request({
    url: '/autoee/studentScore/selectDetailByPkStudentScore/' + id,
    method: 'get'
  })
}

// 新增学生成绩
export function addStudentScore(data) {
  return request({
    url: '/autoee/studentScore',
    method: 'post',
    data: data
  })
}

// 修改学生成绩：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByStudentScore(data) {
  return request({
    url: '/autoee/studentScore/updateNullValueByStudentScore',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByStudentScore(data) {
  return request({
    url: '/autoee/studentScore/updateNotNullValueByStudentScore',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditStudentScore(data) {
  return request({
    url: '/autoee/studentScore/submitTableEditStudentScore',
    method: 'put',
    data: data
  })
}

// 删除学生成绩
export function deleteStudentScoreByIds(id) {
	return request({
		url: '/autoee/studentScore/deleteStudentScoreByIds/' + id,
		method: 'delete'
	})
}

// 删除学生成绩
export function deleteStudentScoreOneByOne(id) {
  return request({
    url: '/autoee/studentScore/deleteStudentScoreOneByOne/' + id,
    method: 'delete'
  })
}
