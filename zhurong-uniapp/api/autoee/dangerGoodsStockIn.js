import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import dangerGoodsStockIn from './autoee/dangerGoodsStockIn';
export default {
  selectPageListDangerGoodsStockIn,
  selectDataListByLikeDangerGoodsStockIn,
  selectDataListByEqDangerGoodsStockIn,
  selectDetailListByLikeDangerGoodsStockIn,
  selectDetailListByEqDangerGoodsStockIn,
  selectDataByPkDangerGoodsStockIn,
  selectDetailByPkDangerGoodsStockIn,
  addDangerGoodsStockIn,
  updateNullValueByDangerGoodsStockIn,
  updateNotNullValueByDangerGoodsStockIn,
  submitTableEditDangerGoodsStockIn,
  deleteDangerGoodsStockInByIds,
  deleteDangerGoodsStockInOneByOne,
};

// 查询危化品入库记录分页列表
export function selectPageListDangerGoodsStockIn(query) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectPageListDangerGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 查询危化品入库记录数据列表，不分页
export function selectDataListByLikeDangerGoodsStockIn(query) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectDataListByLikeDangerGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 精确查询危化品入库记录数据列表，不分页
export function selectDataListByEqDangerGoodsStockIn(query) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectDataListByEqDangerGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 查询危化品入库记录详细列表，不分页
export function selectDetailListByLikeDangerGoodsStockIn(query) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectDetailListByLikeDangerGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 精确查询危化品入库记录详细列表，不分页
export function selectDetailListByEqDangerGoodsStockIn(query) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectDetailListByEqDangerGoodsStockIn',
    method: 'get',
    params: query
  })
}

// 查询危化品入库记录数据信息
export function selectDataByPkDangerGoodsStockIn(id) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectDataByPkDangerGoodsStockIn/' + id,
    method: 'get'
  })
}
// 查询危化品入库记录详细信息，已转码
export function selectDetailByPkDangerGoodsStockIn(id) {
  return request({
    url: '/autoee/dangerGoodsStockIn/selectDetailByPkDangerGoodsStockIn/' + id,
    method: 'get'
  })
}

// 新增危化品入库记录
export function addDangerGoodsStockIn(data) {
  return request({
    url: '/autoee/dangerGoodsStockIn',
    method: 'post',
    data: data
  })
}

// 修改危化品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByDangerGoodsStockIn(data) {
  return request({
    url: '/autoee/dangerGoodsStockIn/updateNullValueByDangerGoodsStockIn',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByDangerGoodsStockIn(data) {
  return request({
    url: '/autoee/dangerGoodsStockIn/updateNotNullValueByDangerGoodsStockIn',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditDangerGoodsStockIn(data) {
  return request({
    url: '/autoee/dangerGoodsStockIn/submitTableEditDangerGoodsStockIn',
    method: 'put',
    data: data
  })
}

// 删除危化品入库记录
export function deleteDangerGoodsStockInByIds(id) {
	return request({
		url: '/autoee/dangerGoodsStockIn/deleteDangerGoodsStockInByIds/' + id,
		method: 'delete'
	})
}

// 删除危化品入库记录
export function deleteDangerGoodsStockInOneByOne(id) {
  return request({
    url: '/autoee/dangerGoodsStockIn/deleteDangerGoodsStockInOneByOne/' + id,
    method: 'delete'
  })
}
