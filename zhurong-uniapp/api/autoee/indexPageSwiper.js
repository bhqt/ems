import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import indexPageSwiper from './autoee/indexPageSwiper';
export default {
  selectPageListIndexPageSwiper,
  selectDataListByLikeIndexPageSwiper,
  selectDataListByEqIndexPageSwiper,
  selectDetailListByLikeIndexPageSwiper,
  selectDetailListByEqIndexPageSwiper,
  selectDataByPkIndexPageSwiper,
  selectDetailByPkIndexPageSwiper,
  addIndexPageSwiper,
  updateNullValueByIndexPageSwiper,
  updateNotNullValueByIndexPageSwiper,
  submitTableEditIndexPageSwiper,
  deleteIndexPageSwiperByIds,
  deleteIndexPageSwiperOneByOne,
};

// 查询首页轮播图分页列表
export function selectPageListIndexPageSwiper(query) {
  return request({
    url: '/autoee/indexPageSwiper/selectPageListIndexPageSwiper',
    method: 'get',
    params: query
  })
}

// 查询首页轮播图数据列表，不分页
export function selectDataListByLikeIndexPageSwiper(query) {
  return request({
    url: '/autoee/indexPageSwiper/selectDataListByLikeIndexPageSwiper',
    method: 'get',
    params: query
  })
}

// 精确查询首页轮播图数据列表，不分页
export function selectDataListByEqIndexPageSwiper(query) {
  return request({
    url: '/autoee/indexPageSwiper/selectDataListByEqIndexPageSwiper',
    method: 'get',
    params: query
  })
}

// 查询首页轮播图详细列表，不分页
export function selectDetailListByLikeIndexPageSwiper(query) {
  return request({
    url: '/autoee/indexPageSwiper/selectDetailListByLikeIndexPageSwiper',
    method: 'get',
    params: query
  })
}

// 精确查询首页轮播图详细列表，不分页
export function selectDetailListByEqIndexPageSwiper(query) {
  return request({
    url: '/autoee/indexPageSwiper/selectDetailListByEqIndexPageSwiper',
    method: 'get',
    params: query
  })
}

// 查询首页轮播图数据信息
export function selectDataByPkIndexPageSwiper(id) {
  return request({
    url: '/autoee/indexPageSwiper/selectDataByPkIndexPageSwiper/' + id,
    method: 'get'
  })
}
// 查询首页轮播图详细信息，已转码
export function selectDetailByPkIndexPageSwiper(id) {
  return request({
    url: '/autoee/indexPageSwiper/selectDetailByPkIndexPageSwiper/' + id,
    method: 'get'
  })
}

// 新增首页轮播图
export function addIndexPageSwiper(data) {
  return request({
    url: '/autoee/indexPageSwiper',
    method: 'post',
    data: data
  })
}

// 修改首页轮播图：只能用于前端form表单的更新操作，清空的字段回写为null
export function updateNullValueByIndexPageSwiper(data) {
  return request({
    url: '/autoee/indexPageSwiper/updateNullValueByIndexPageSwiper',
    method: 'put',
    data: data
  })
}

// 更新设置值的字段，未设置值的字段不进行更新
export function updateNotNullValueByIndexPageSwiper(data) {
  return request({
    url: '/autoee/indexPageSwiper/updateNotNullValueByIndexPageSwiper',
    method: 'put',
    data: data
  })
}

// 提交列表编辑
export function submitTableEditIndexPageSwiper(data) {
  return request({
    url: '/autoee/indexPageSwiper/submitTableEditIndexPageSwiper',
    method: 'put',
    data: data
  })
}

// 删除首页轮播图
export function deleteIndexPageSwiperByIds(id) {
	return request({
		url: '/autoee/indexPageSwiper/deleteIndexPageSwiperByIds/' + id,
		method: 'delete'
	})
}

// 删除首页轮播图
export function deleteIndexPageSwiperOneByOne(id) {
  return request({
    url: '/autoee/indexPageSwiper/deleteIndexPageSwiperOneByOne/' + id,
    method: 'delete'
  })
}
