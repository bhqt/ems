import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import goodsInventory from './autoee/goodsInventory';
export default {
  selectPageListGoodsInventory,
  selectDataListByLikeGoodsInventory,
  selectDataListByEqGoodsInventory,
  selectDetailListByLikeGoodsInventory,
  selectDetailListByEqGoodsInventory,
  selectDataByPkGoodsInventory,
  selectDetailByPkGoodsInventory,
  addGoodsInventory,
  updateNullValueByGoodsInventory,
  updateNotNullValueByGoodsInventory,
  submitTableEditGoodsInventory,
  deleteGoodsInventoryByIds,
  deleteGoodsInventoryOneByOne,
};

// 查询物品库存分页列表
export function selectPageListGoodsInventory(query) {
  return request({
    url: '/autoee/goodsInventory/selectPageListGoodsInventory',
    method: 'get',
    params: query
  })
}

// 查询物品库存数据列表，不分页
export function selectDataListByLikeGoodsInventory(query) {
  return request({
    url: '/autoee/goodsInventory/selectDataListByLikeGoodsInventory',
    method: 'get',
    params: query
  })
}

// 精确查询物品库存数据列表，不分页
export function selectDataListByEqGoodsInventory(query) {
  return request({
    url: '/autoee/goodsInventory/selectDataListByEqGoodsInventory',
    method: 'get',
    params: query
  })
}

// 查询物品库存详细列表，不分页
export function selectDetailListByLikeGoodsInventory(query) {
  return request({
    url: '/autoee/goodsInventory/selectDetailListByLikeGoodsInventory',
    method: 'get',
    params: query
  })
}

// 精确查询物品库存详细列表，不分页
export function selectDetailListByEqGoodsInventory(query) {
  return request({
    url: '/autoee/goodsInventory/selectDetailListByEqGoodsInventory',
    method: 'get',
    params: query
  })
}

// 查询物品库存数据信息
export function selectDataByPkGoodsInventory(id) {
  return request({
    url: '/autoee/goodsInventory/selectDataByPkGoodsInventory/' + id,
    method: 'get'
  })
}
// 查询物品库存详细信息，已转码
export function selectDetailByPkGoodsInventory(id) {
  return request({
    url: '/autoee/goodsInventory/selectDetailByPkGoodsInventory/' + id,
    method: 'get'
  })
}

// 新增物品库存
export function addGoodsInventory(data) {
  return request({
    url: '/autoee/goodsInventory',
    method: 'post',
    data: data
  })
}

// 修改物品库存：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByGoodsInventory(data) {
  return request({
    url: '/autoee/goodsInventory/updateNullValueByGoodsInventory',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByGoodsInventory(data) {
  return request({
    url: '/autoee/goodsInventory/updateNotNullValueByGoodsInventory',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditGoodsInventory(data) {
  return request({
    url: '/autoee/goodsInventory/submitTableEditGoodsInventory',
    method: 'put',
    data: data
  })
}

// 删除物品库存
export function deleteGoodsInventoryByIds(id) {
	return request({
		url: '/autoee/goodsInventory/deleteGoodsInventoryByIds/' + id,
		method: 'delete'
	})
}

// 删除物品库存
export function deleteGoodsInventoryOneByOne(id) {
  return request({
    url: '/autoee/goodsInventory/deleteGoodsInventoryOneByOne/' + id,
    method: 'delete'
  })
}
