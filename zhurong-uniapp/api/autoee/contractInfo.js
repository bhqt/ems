import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import contractInfo from './autoee/contractInfo';
export default {
  selectPageListContractInfo,
  selectDataListByLikeContractInfo,
  selectDataListByEqContractInfo,
  selectDetailListByLikeContractInfo,
  selectDetailListByEqContractInfo,
  selectDataByPkContractInfo,
  selectDetailByPkContractInfo,
  addContractInfo,
  updateNullValueByContractInfo,
  updateNotNullValueByContractInfo,
  submitTableEditContractInfo,
  deleteContractInfoByIds,
  deleteContractInfoOneByOne,
};

// 查询合同信息管理分页列表
export function selectPageListContractInfo(query) {
  return request({
    url: '/autoee/contractInfo/selectPageListContractInfo',
    method: 'get',
    params: query
  })
}

// 查询合同信息管理数据列表，不分页
export function selectDataListByLikeContractInfo(query) {
  return request({
    url: '/autoee/contractInfo/selectDataListByLikeContractInfo',
    method: 'get',
    params: query
  })
}

// 精确查询合同信息管理数据列表，不分页
export function selectDataListByEqContractInfo(query) {
  return request({
    url: '/autoee/contractInfo/selectDataListByEqContractInfo',
    method: 'get',
    params: query
  })
}

// 查询合同信息管理详细列表，不分页
export function selectDetailListByLikeContractInfo(query) {
  return request({
    url: '/autoee/contractInfo/selectDetailListByLikeContractInfo',
    method: 'get',
    params: query
  })
}

// 精确查询合同信息管理详细列表，不分页
export function selectDetailListByEqContractInfo(query) {
  return request({
    url: '/autoee/contractInfo/selectDetailListByEqContractInfo',
    method: 'get',
    params: query
  })
}

// 查询合同信息管理数据信息
export function selectDataByPkContractInfo(id) {
  return request({
    url: '/autoee/contractInfo/selectDataByPkContractInfo/' + id,
    method: 'get'
  })
}
// 查询合同信息管理详细信息，已转码
export function selectDetailByPkContractInfo(id) {
  return request({
    url: '/autoee/contractInfo/selectDetailByPkContractInfo/' + id,
    method: 'get'
  })
}

// 新增合同信息管理
export function addContractInfo(data) {
  return request({
    url: '/autoee/contractInfo',
    method: 'post',
    data: data
  })
}

// 修改合同信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByContractInfo(data) {
  return request({
    url: '/autoee/contractInfo/updateNullValueByContractInfo',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByContractInfo(data) {
  return request({
    url: '/autoee/contractInfo/updateNotNullValueByContractInfo',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditContractInfo(data) {
  return request({
    url: '/autoee/contractInfo/submitTableEditContractInfo',
    method: 'put',
    data: data
  })
}

// 删除合同信息管理
export function deleteContractInfoByIds(id) {
	return request({
		url: '/autoee/contractInfo/deleteContractInfoByIds/' + id,
		method: 'delete'
	})
}

// 删除合同信息管理
export function deleteContractInfoOneByOne(id) {
  return request({
    url: '/autoee/contractInfo/deleteContractInfoOneByOne/' + id,
    method: 'delete'
  })
}
