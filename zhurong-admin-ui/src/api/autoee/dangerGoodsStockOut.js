import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import dangerGoodsStockOut from './autoee/dangerGoodsStockOut';
export default {
  selectPageListDangerGoodsStockOut,
  selectDataListByLikeDangerGoodsStockOut,
  selectDataListByEqDangerGoodsStockOut,
  selectDetailListByLikeDangerGoodsStockOut,
  selectDetailListByEqDangerGoodsStockOut,
  selectDataByPkDangerGoodsStockOut,
  selectDetailByPkDangerGoodsStockOut,
  addDangerGoodsStockOut,
  updateNullValueByDangerGoodsStockOut,
  updateNotNullValueByDangerGoodsStockOut,
  submitTableEditDangerGoodsStockOut,
  deleteDangerGoodsStockOutByIds,
  deleteDangerGoodsStockOutOneByOne,
  deleteDangerGoodsStockOutAllData,
};

// 查询危化品出库记录分页列表
export function selectPageListDangerGoodsStockOut(query) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectPageListDangerGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 查询危化品出库记录数据列表，不分页
export function selectDataListByLikeDangerGoodsStockOut(query) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectDataListByLikeDangerGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 精确查询危化品出库记录数据列表，不分页
export function selectDataListByEqDangerGoodsStockOut(query) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectDataListByEqDangerGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 查询危化品出库记录详细列表，不分页
export function selectDetailListByLikeDangerGoodsStockOut(query) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectDetailListByLikeDangerGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 精确查询危化品出库记录详细列表，不分页
export function selectDetailListByEqDangerGoodsStockOut(query) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectDetailListByEqDangerGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 查询危化品出库记录数据信息
export function selectDataByPkDangerGoodsStockOut(id) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectDataByPkDangerGoodsStockOut/' + id,
    method: 'get'
  })
}
// 查询危化品出库记录详细信息，已转码
export function selectDetailByPkDangerGoodsStockOut(id) {
  return request({
    url: '/autoee/dangerGoodsStockOut/selectDetailByPkDangerGoodsStockOut/' + id,
    method: 'get'
  })
}

// 新增危化品出库记录
export function addDangerGoodsStockOut(data) {
  return request({
    url: '/autoee/dangerGoodsStockOut',
    method: 'post',
    data: data
  })
}

// 修改危化品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByDangerGoodsStockOut(data) {
  return request({
    url: '/autoee/dangerGoodsStockOut/updateNullValueByDangerGoodsStockOut',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByDangerGoodsStockOut(data) {
  return request({
    url: '/autoee/dangerGoodsStockOut/updateNotNullValueByDangerGoodsStockOut',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditDangerGoodsStockOut(data) {
  return request({
    url: '/autoee/dangerGoodsStockOut/submitTableEditDangerGoodsStockOut',
    method: 'put',
    data: data
  })
}

// 删除危化品出库记录
export function deleteDangerGoodsStockOutByIds(id) {
	return request({
		url: '/autoee/dangerGoodsStockOut/deleteDangerGoodsStockOutByIds/' + id,
		method: 'delete'
	})
}

// 删除危化品出库记录
export function deleteDangerGoodsStockOutOneByOne(id) {
  return request({
    url: '/autoee/dangerGoodsStockOut/deleteDangerGoodsStockOutOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deleteDangerGoodsStockOutAllData() {
  return request({
    url: '/autoee/dangerGoodsStockOut/deleteDangerGoodsStockOutAllData',
    method: 'delete'
  })
}
