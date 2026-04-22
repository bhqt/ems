import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import goodsStockIn from './autoee/goodsStockIn';
export default {
  selectPageListGoodsStockIn,
  selectDataListByLikeGoodsStockIn,
  selectDataListByEqGoodsStockIn,
  selectDetailListByLikeGoodsStockIn,
  selectDetailListByEqGoodsStockIn,
  selectDataByPkGoodsStockIn,
  selectDetailByPkGoodsStockIn,
  addGoodsStockIn,
  updateNullValueByGoodsStockIn,
  updateNotNullValueByGoodsStockIn,
  submitTableEditGoodsStockIn,
  deleteGoodsStockInByIds,
  deleteGoodsStockInOneByOne,
  deleteGoodsStockInAllData,
};

// 查询物品入库记录分页列表
export function selectPageListGoodsStockIn(query) {
  return request({
    url: '/autoee/goodsStockIn/selectPageListGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 查询物品入库记录数据列表，不分页
export function selectDataListByLikeGoodsStockIn(query) {
  return request({
    url: '/autoee/goodsStockIn/selectDataListByLikeGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 精确查询物品入库记录数据列表，不分页
export function selectDataListByEqGoodsStockIn(query) {
  return request({
    url: '/autoee/goodsStockIn/selectDataListByEqGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 查询物品入库记录详细列表，不分页
export function selectDetailListByLikeGoodsStockIn(query) {
  return request({
    url: '/autoee/goodsStockIn/selectDetailListByLikeGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 精确查询物品入库记录详细列表，不分页
export function selectDetailListByEqGoodsStockIn(query) {
  return request({
    url: '/autoee/goodsStockIn/selectDetailListByEqGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 查询物品入库记录数据信息
export function selectDataByPkGoodsStockIn(id) {
  return request({
    url: '/autoee/goodsStockIn/selectDataByPkGoodsStockIn/' + id,
    method: 'get'
  })
}
// 查询物品入库记录详细信息，已转码
export function selectDetailByPkGoodsStockIn(id) {
  return request({
    url: '/autoee/goodsStockIn/selectDetailByPkGoodsStockIn/' + id,
    method: 'get'
  })
}

// 新增物品入库记录
export function addGoodsStockIn(data) {
  return request({
    url: '/autoee/goodsStockIn',
    method: 'post',
    data: data
  })
}

// 修改物品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByGoodsStockIn(data) {
  return request({
    url: '/autoee/goodsStockIn/updateNullValueByGoodsStockIn',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByGoodsStockIn(data) {
  return request({
    url: '/autoee/goodsStockIn/updateNotNullValueByGoodsStockIn',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditGoodsStockIn(data) {
  return request({
    url: '/autoee/goodsStockIn/submitTableEditGoodsStockIn',
    method: 'put',
    data: data
  })
}

// 删除物品入库记录
export function deleteGoodsStockInByIds(id) {
	return request({
		url: '/autoee/goodsStockIn/deleteGoodsStockInByIds/' + id,
		method: 'delete'
	})
}

// 删除物品入库记录
export function deleteGoodsStockInOneByOne(id) {
  return request({
    url: '/autoee/goodsStockIn/deleteGoodsStockInOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deleteGoodsStockInAllData() {
  return request({
    url: '/autoee/goodsStockIn/deleteGoodsStockInAllData',
    method: 'delete'
  })
}
