import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import goodsInfo from './autoee/goodsInfo';
export default {
  selectPageListGoodsInfo,
  selectDataListByLikeGoodsInfo,
  selectDataListByEqGoodsInfo,
  selectDetailListByLikeGoodsInfo,
  selectDetailListByEqGoodsInfo,
  selectDataByPkGoodsInfo,
  selectDetailByPkGoodsInfo,
  addGoodsInfo,
  updateNullValueByGoodsInfo,
  updateNotNullValueByGoodsInfo,
  submitTableEditGoodsInfo,
  deleteGoodsInfoByIds,
  deleteGoodsInfoOneByOne,
  deleteGoodsInfoAllData,
};

// 查询物品信息管理分页列表
export function selectPageListGoodsInfo(query) {
  return request({
    url: '/autoee/goodsInfo/selectPageListGoodsInfo',
    method: 'get',
    params: query
  })
}

// 查询物品信息管理数据列表，不分页
export function selectDataListByLikeGoodsInfo(query) {
  return request({
    url: '/autoee/goodsInfo/selectDataListByLikeGoodsInfo',
    method: 'get',
    params: query
  })
}

// 精确查询物品信息管理数据列表，不分页
export function selectDataListByEqGoodsInfo(query) {
  return request({
    url: '/autoee/goodsInfo/selectDataListByEqGoodsInfo',
    method: 'get',
    params: query
  })
}

// 查询物品信息管理详细列表，不分页
export function selectDetailListByLikeGoodsInfo(query) {
  return request({
    url: '/autoee/goodsInfo/selectDetailListByLikeGoodsInfo',
    method: 'get',
    params: query
  })
}

// 精确查询物品信息管理详细列表，不分页
export function selectDetailListByEqGoodsInfo(query) {
  return request({
    url: '/autoee/goodsInfo/selectDetailListByEqGoodsInfo',
    method: 'get',
    params: query
  })
}

// 查询物品信息管理数据信息
export function selectDataByPkGoodsInfo(id) {
  return request({
    url: '/autoee/goodsInfo/selectDataByPkGoodsInfo/' + id,
    method: 'get'
  })
}
// 查询物品信息管理详细信息，已转码
export function selectDetailByPkGoodsInfo(id) {
  return request({
    url: '/autoee/goodsInfo/selectDetailByPkGoodsInfo/' + id,
    method: 'get'
  })
}

// 新增物品信息管理
export function addGoodsInfo(data) {
  return request({
    url: '/autoee/goodsInfo',
    method: 'post',
    data: data
  })
}

// 修改物品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByGoodsInfo(data) {
  return request({
    url: '/autoee/goodsInfo/updateNullValueByGoodsInfo',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByGoodsInfo(data) {
  return request({
    url: '/autoee/goodsInfo/updateNotNullValueByGoodsInfo',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditGoodsInfo(data) {
  return request({
    url: '/autoee/goodsInfo/submitTableEditGoodsInfo',
    method: 'put',
    data: data
  })
}

// 删除物品信息管理
export function deleteGoodsInfoByIds(id) {
	return request({
		url: '/autoee/goodsInfo/deleteGoodsInfoByIds/' + id,
		method: 'delete'
	})
}

// 删除物品信息管理
export function deleteGoodsInfoOneByOne(id) {
  return request({
    url: '/autoee/goodsInfo/deleteGoodsInfoOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deleteGoodsInfoAllData() {
  return request({
    url: '/autoee/goodsInfo/deleteGoodsInfoAllData',
    method: 'delete'
  })
}
