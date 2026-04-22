import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import dangerGoodsInfo from './autoee/dangerGoodsInfo';
export default {
  selectPageListDangerGoodsInfo,
  selectDataListByLikeDangerGoodsInfo,
  selectDataListByEqDangerGoodsInfo,
  selectDetailListByLikeDangerGoodsInfo,
  selectDetailListByEqDangerGoodsInfo,
  selectDataByPkDangerGoodsInfo,
  selectDetailByPkDangerGoodsInfo,
  addDangerGoodsInfo,
  updateNullValueByDangerGoodsInfo,
  updateNotNullValueByDangerGoodsInfo,
  submitTableEditDangerGoodsInfo,
  deleteDangerGoodsInfoByIds,
  deleteDangerGoodsInfoOneByOne,
};

// 查询危化品信息管理分页列表
export function selectPageListDangerGoodsInfo(query) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectPageListDangerGoodsInfo',
    method: 'get',
    params: query
  })
}

// 查询危化品信息管理数据列表，不分页
export function selectDataListByLikeDangerGoodsInfo(query) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectDataListByLikeDangerGoodsInfo',
    method: 'get',
    params: query
  })
}

// 精确查询危化品信息管理数据列表，不分页
export function selectDataListByEqDangerGoodsInfo(query) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectDataListByEqDangerGoodsInfo',
    method: 'get',
    params: query
  })
}

// 查询危化品信息管理详细列表，不分页
export function selectDetailListByLikeDangerGoodsInfo(query) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectDetailListByLikeDangerGoodsInfo',
    method: 'get',
    params: query
  })
}

// 精确查询危化品信息管理详细列表，不分页
export function selectDetailListByEqDangerGoodsInfo(query) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectDetailListByEqDangerGoodsInfo',
    method: 'get',
    params: query
  })
}

// 查询危化品信息管理数据信息
export function selectDataByPkDangerGoodsInfo(id) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectDataByPkDangerGoodsInfo/' + id,
    method: 'get'
  })
}
// 查询危化品信息管理详细信息，已转码
export function selectDetailByPkDangerGoodsInfo(id) {
  return request({
    url: '/autoee/dangerGoodsInfo/selectDetailByPkDangerGoodsInfo/' + id,
    method: 'get'
  })
}

// 新增危化品信息管理
export function addDangerGoodsInfo(data) {
  return request({
    url: '/autoee/dangerGoodsInfo',
    method: 'post',
    data: data
  })
}

// 修改危化品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByDangerGoodsInfo(data) {
  return request({
    url: '/autoee/dangerGoodsInfo/updateNullValueByDangerGoodsInfo',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByDangerGoodsInfo(data) {
  return request({
    url: '/autoee/dangerGoodsInfo/updateNotNullValueByDangerGoodsInfo',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditDangerGoodsInfo(data) {
  return request({
    url: '/autoee/dangerGoodsInfo/submitTableEditDangerGoodsInfo',
    method: 'put',
    data: data
  })
}

// 删除危化品信息管理
export function deleteDangerGoodsInfoByIds(id) {
	return request({
		url: '/autoee/dangerGoodsInfo/deleteDangerGoodsInfoByIds/' + id,
		method: 'delete'
	})
}

// 删除危化品信息管理
export function deleteDangerGoodsInfoOneByOne(id) {
  return request({
    url: '/autoee/dangerGoodsInfo/deleteDangerGoodsInfoOneByOne/' + id,
    method: 'delete'
  })
}
