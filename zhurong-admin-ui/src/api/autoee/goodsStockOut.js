import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import goodsStockOut from './autoee/goodsStockOut';
export default {
  selectPageListGoodsStockOut,
  selectDataListByLikeGoodsStockOut,
  selectDataListByEqGoodsStockOut,
  selectDetailListByLikeGoodsStockOut,
  selectDetailListByEqGoodsStockOut,
  selectDataByPkGoodsStockOut,
  selectDetailByPkGoodsStockOut,
  addGoodsStockOut,
  updateNullValueByGoodsStockOut,
  updateNotNullValueByGoodsStockOut,
  submitTableEditGoodsStockOut,
  deleteGoodsStockOutByIds,
  deleteGoodsStockOutOneByOne,
  deleteGoodsStockOutAllData,
};

// 查询物品出库记录分页列表
export function selectPageListGoodsStockOut(query) {
  return request({
    url: '/autoee/goodsStockOut/selectPageListGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 查询物品出库记录数据列表，不分页
export function selectDataListByLikeGoodsStockOut(query) {
  return request({
    url: '/autoee/goodsStockOut/selectDataListByLikeGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 精确查询物品出库记录数据列表，不分页
export function selectDataListByEqGoodsStockOut(query) {
  return request({
    url: '/autoee/goodsStockOut/selectDataListByEqGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 查询物品出库记录详细列表，不分页
export function selectDetailListByLikeGoodsStockOut(query) {
  return request({
    url: '/autoee/goodsStockOut/selectDetailListByLikeGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 精确查询物品出库记录详细列表，不分页
export function selectDetailListByEqGoodsStockOut(query) {
  return request({
    url: '/autoee/goodsStockOut/selectDetailListByEqGoodsStockOut',
    method: 'get',
    params: query
  })
}

// 查询物品出库记录数据信息
export function selectDataByPkGoodsStockOut(id) {
  return request({
    url: '/autoee/goodsStockOut/selectDataByPkGoodsStockOut/' + id,
    method: 'get'
  })
}
// 查询物品出库记录详细信息，已转码
export function selectDetailByPkGoodsStockOut(id) {
  return request({
    url: '/autoee/goodsStockOut/selectDetailByPkGoodsStockOut/' + id,
    method: 'get'
  })
}

// 新增物品出库记录
export function addGoodsStockOut(data) {
  return request({
    url: '/autoee/goodsStockOut',
    method: 'post',
    data: data
  })
}

// 修改物品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByGoodsStockOut(data) {
  return request({
    url: '/autoee/goodsStockOut/updateNullValueByGoodsStockOut',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByGoodsStockOut(data) {
  return request({
    url: '/autoee/goodsStockOut/updateNotNullValueByGoodsStockOut',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditGoodsStockOut(data) {
  return request({
    url: '/autoee/goodsStockOut/submitTableEditGoodsStockOut',
    method: 'put',
    data: data
  })
}

// 删除物品出库记录
export function deleteGoodsStockOutByIds(id) {
	return request({
		url: '/autoee/goodsStockOut/deleteGoodsStockOutByIds/' + id,
		method: 'delete'
	})
}

// 删除物品出库记录
export function deleteGoodsStockOutOneByOne(id) {
  return request({
    url: '/autoee/goodsStockOut/deleteGoodsStockOutOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deleteGoodsStockOutAllData() {
  return request({
    url: '/autoee/goodsStockOut/deleteGoodsStockOutAllData',
    method: 'delete'
  })
}
