import request from '@/utils/request'

// 查询回调日志列表
export function listCallbackLog(query) {
  return request({
    url: '/hospital/callbackLog/list',
    method: 'get',
    params: query
  })
}
