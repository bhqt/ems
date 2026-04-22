import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import maintainOrder from './autoee/maintainOrder';
export default {
  selectPageListMaintainOrder,
  selectDataListByLikeMaintainOrder,
  selectDataListByEqMaintainOrder,
  selectDetailListByLikeMaintainOrder,
  selectDetailListByEqMaintainOrder,
  selectDataByPkMaintainOrder,
  selectDetailByPkMaintainOrder,
  addMaintainOrder,
  updateNullValueByMaintainOrder,
  updateNotNullValueByMaintainOrder,
  submitTableEditMaintainOrder,
  deleteMaintainOrderByIds,
  deleteMaintainOrderOneByOne,
  deleteMaintainOrderAllData,
};

// 查询维修工单分页列表
export function selectPageListMaintainOrder(query) {
  return request({
    url: '/autoee/maintainOrder/selectPageListMaintainOrder',
    method: 'get',
    params: query
  })
}

// 查询维修工单数据列表，不分页
export function selectDataListByLikeMaintainOrder(query) {
  return request({
    url: '/autoee/maintainOrder/selectDataListByLikeMaintainOrder',
    method: 'get',
    params: query
  })
}

// 精确查询维修工单数据列表，不分页
export function selectDataListByEqMaintainOrder(query) {
  return request({
    url: '/autoee/maintainOrder/selectDataListByEqMaintainOrder',
    method: 'get',
    params: query
  })
}

// 查询维修工单详细列表，不分页
export function selectDetailListByLikeMaintainOrder(query) {
  return request({
    url: '/autoee/maintainOrder/selectDetailListByLikeMaintainOrder',
    method: 'get',
    params: query
  })
}

// 精确查询维修工单详细列表，不分页
export function selectDetailListByEqMaintainOrder(query) {
  return request({
    url: '/autoee/maintainOrder/selectDetailListByEqMaintainOrder',
    method: 'get',
    params: query
  })
}

// 查询维修工单数据信息
export function selectDataByPkMaintainOrder(id) {
  return request({
    url: '/autoee/maintainOrder/selectDataByPkMaintainOrder/' + id,
    method: 'get'
  })
}
// 查询维修工单详细信息，已转码
export function selectDetailByPkMaintainOrder(id) {
  return request({
    url: '/autoee/maintainOrder/selectDetailByPkMaintainOrder/' + id,
    method: 'get'
  })
}

// 新增维修工单
export function addMaintainOrder(data) {
  return request({
    url: '/autoee/maintainOrder',
    method: 'post',
    data: data
  })
}

// 修改维修工单：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByMaintainOrder(data) {
  return request({
    url: '/autoee/maintainOrder/updateNullValueByMaintainOrder',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByMaintainOrder(data) {
  return request({
    url: '/autoee/maintainOrder/updateNotNullValueByMaintainOrder',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditMaintainOrder(data) {
  return request({
    url: '/autoee/maintainOrder/submitTableEditMaintainOrder',
    method: 'put',
    data: data
  })
}

// 删除维修工单
export function deleteMaintainOrderByIds(id) {
	return request({
		url: '/autoee/maintainOrder/deleteMaintainOrderByIds/' + id,
		method: 'delete'
	})
}

// 删除维修工单
export function deleteMaintainOrderOneByOne(id) {
  return request({
    url: '/autoee/maintainOrder/deleteMaintainOrderOneByOne/' + id,
    method: 'delete'
  })
}

// 删除全部数据
export function deleteMaintainOrderAllData() {
  return request({
    url: '/autoee/maintainOrder/deleteMaintainOrderAllData',
    method: 'delete'
  })
}
