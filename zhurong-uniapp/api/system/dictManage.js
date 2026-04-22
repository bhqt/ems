import upload from '@/utils/upload'
import request from '@/utils/request'

	// 通过字典类型获取字典信息
export  function getDictDataByType(data) {
	return request({
		url: '/common/getDictDataByTypeWithParams',
		method: 'post',
		data: data
	})
}
// export  function getDictDataByType(data) {
// 	return request({
// 		url: '/common/getDictDataByType',
// 		method: 'post',
// 		data: data
// 	})
// }
