import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import dangerGoodsInventory from './autoee/dangerGoodsInventory';
export default {
  selectPageListDangerGoodsInventory,
  selectDataListByLikeDangerGoodsInventory,
  selectDataListByEqDangerGoodsInventory,
  selectDetailListByLikeDangerGoodsInventory,
  selectDetailListByEqDangerGoodsInventory,
  selectDataByPkDangerGoodsInventory,
  selectDetailByPkDangerGoodsInventory,
  addDangerGoodsInventory,
  updateNullValueByDangerGoodsInventory,
  updateNotNullValueByDangerGoodsInventory,
  submitTableEditDangerGoodsInventory,
  deleteDangerGoodsInventoryByIds,
  deleteDangerGoodsInventoryOneByOne,
  deleteDangerGoodsInventoryAllData,
};

// 查询危化品库存分页列表
export function selectPageListDangerGoodsInventory(query) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectPageListDangerGoodsInventory',
    method: 'get',
    params: query
  })
}

// 查询危化品库存数据列表，不分页
export function selectDataListByLikeDangerGoodsInventory(query) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectDataListByLikeDangerGoodsInventory',
    method: 'get',
    params: query
  })
}

// 精确查询危化品库存数据列表，不分页
export function selectDataListByEqDangerGoodsInventory(query) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectDataListByEqDangerGoodsInventory',
    method: 'get',
    params: query
  })
}

// 查询危化品库存详细列表，不分页
export function selectDetailListByLikeDangerGoodsInventory(query) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectDetailListByLikeDangerGoodsInventory',
    method: 'get',
    params: query
  })
}

// 精确查询危化品库存详细列表，不分页
export function selectDetailListByEqDangerGoodsInventory(query) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectDetailListByEqDangerGoodsInventory',
    method: 'get',
    params: query
  })
}

// 查询危化品库存数据信息
export function selectDataByPkDangerGoodsInventory(id) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectDataByPkDangerGoodsInventory/' + id,
    method: 'get'
  })
}
// 查询危化品库存详细信息，已转码
export function selectDetailByPkDangerGoodsInventory(id) {
  return request({
    url: '/autoee/dangerGoodsInventory/selectDetailByPkDangerGoodsInventory/' + id,
    method: 'get'
  })
}

// 新增危化品库存
export function addDangerGoodsInventory(data) {
  return request({
    url: '/autoee/dangerGoodsInventory',
    method: 'post',
    data: data
  })
}

// 修改危化品库存：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByDangerGoodsInventory(data) {
  return request({
    url: '/autoee/dangerGoodsInventory/updateNullValueByDangerGoodsInventory',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByDangerGoodsInventory(data) {
  return request({
    url: '/autoee/dangerGoodsInventory/updateNotNullValueByDangerGoodsInventory',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditDangerGoodsInventory(data) {
  return request({
    url: '/autoee/dangerGoodsInventory/submitTableEditDangerGoodsInventory',
    method: 'put',
    data: data
  })
}

// 删除危化品库存
export function deleteDangerGoodsInventoryByIds(id) {
	return request({
		url: '/autoee/dangerGoodsInventory/deleteDangerGoodsInventoryByIds/' + id,
		method: 'delete'
	})
}

// 删除危化品库存
export function deleteDangerGoodsInventoryOneByOne(id) {
  return request({
    url: '/autoee/dangerGoodsInventory/deleteDangerGoodsInventoryOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deleteDangerGoodsInventoryAllData() {
  return request({
    url: '/autoee/dangerGoodsInventory/deleteDangerGoodsInventoryAllData',
    method: 'delete'
  })
}
