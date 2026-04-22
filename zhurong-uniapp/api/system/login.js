import request from '@/utils/request'

// login_for_ruoyi_autoee

// 用户名密码密码登录
export function loginByUsernameAndPassword(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    'url': '/login',
    headers: {
      isToken: false
    },
    'method': 'post',
    'data': data
  })
}

// 手机号码一键登录
export function loginByPhoneNo(phoneNo) {
  const data = {
	  phoneNo
  }
  return request({
    'url': '/jwtapi/loginByPhoneNo',
    headers: {
      isToken: false
    },
    'method': 'post',
    'data': data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    'url': '/getInfo',
    'method': 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    'url': '/jwtapi/logoutByJwt',
    'method': 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    'url': '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

export function register(data) {
	return request({
		url: '/register',
		method: 'post',
		data: data
	})
}
