import store from '@/store'
import config from '@/config'
import {
	getToken
} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {
	toast,
	showConfirm,
	tansParams
} from '@/utils/common'

let timeout = 10000
const baseUrl = config.baseUrl

const request = config => {
	// 是否需要设置 token
	const isToken = (config.headers || {}).isToken === false
	config.header = config.header || {}
	if (getToken() && !isToken) {
		config.header['Authorization'] = 'Bearer ' + getToken()
	}
	// get请求映射params参数
	if (config.params) {
		let url = config.url + '?' + tansParams(config.params)
		url = url.slice(0, -1)
		config.url = url
	}
	console.log("request.js - config.url=", config.url)
	return new Promise((resolve, reject) => {
		// https://uniapp.dcloud.net.cn/api/request/request.html#
		// 请求的 header 中 content-type 默认为 application/json。
		let url = config.baseUrl || baseUrl + config.url
		uni.request({
				method: config.method || 'get',
				timeout: config.timeout || timeout,
				url: url,
				data: config.data,
				header: config.header,
				dataType: 'json'
			}).then(response => {
				let [error, res] = response
				// console.log("request.js - error=" + JSON.stringify(error))
				// console.log("request.js - res=" + JSON.stringify(res))
				if (error) {
					console.log("【异常】-请求后端服务地址[" + url + "]", error)
					toast('请求后端服务接口出现异常！')
					// 将 Promise 对象的状态设置为  rejected（拒绝），并传递错误信息
					const error = new Error('请求后端服务接口出现异常！');
					reject(error)
					return
				}
				//  后端处理成功返回的code为0
				const code = res.data.code || 200
				// console.log("request.js - code=" + code)
				const message = errorCode[code] || res.data.msg || errorCode['default']
				if (code === 401) {
					showConfirm('当前功能需登陆后访问，确认登陆吗？').then(res => {
						if (res.confirm) {
							store.dispatch('LogOut').then(res => {
								uni.reLaunch({
									url: '/pages/login'
								})
							})
						}
					})
					// 将 Promise 对象的状态设置为  rejected（拒绝），并传递错误信息
					const error = new Error('登陆状态已过期，请重新登录。');
					reject(error)
				} else if (code === 500) {
					// console.log("code=500")
					// 将 Promise 对象的状态设置为  rejected（拒绝），并传递错误信息
					const error = new Error(message);
					reject(error)
				} else if (code !== 200 && code != 0) {
					console.log("code !== 200 && code != 0---code=" + code)
					// 将 Promise 对象的状态设置为  rejected（拒绝），并传递错误信息
					const error = new Error(message);
					reject(error)
				}
				// 【后台处理成功】将 Promise 对象的状态设置为resolved（已解决），并传递数据作为解决结果
				resolve(res.data)
			})
			.catch(error => {
				console.log("【异常】-请求后端服务地址[" + url + "]", error)
				// 使用了解构赋值语法，将 error 对象中的 message 属性提取出来并赋值给了一个名为 message 的变量。
				let { message } = error
				if (message === 'Network Error') {
					message = '请求后端服务接口出现异常！连接异常'
				} else if (message.includes('timeout')) {
					message = '请求后端服务接口出现异常！系统接口请求超时'
				} else if (message.includes('Request failed with status code')) {
					message = '请求后端服务接口出现异常！系统接口' + message.substr(message.length - 3) + '异常'
				}
				toast(message)
				// 将 Promise 对象的状态设置为  rejected（拒绝），并传递错误信息
				reject(error)
			})
	})
}

export default request
