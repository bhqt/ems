import { getToken } from '@/utils/auth'

// 登录页面
const loginPage = "/pages/login"

// 页面白名单
const whiteList = [
  '/','/pages/login','/pages/register','/pages/index/index'
]

// 检查地址白名单
function checkWhite(url) {
  const path = url.split('?')[0]
  return whiteList.indexOf(path) !== -1
}

// 页面跳转验证拦截器
let list = ["navigateTo", "redirectTo", "reLaunch", "switchTab"]
list.forEach(item => {
  uni.addInterceptor(item, {
    invoke(to) {
	  console.log("页面跳转验证拦截器-检查跳转路径："+to.url)
      if (getToken()) {
		  console.log("已经登录-getToken可以获取到token信息")
        if (to.url === loginPage) {
          uni.reLaunch({ url: "/" })
        }
        return true
      } else {
        if (checkWhite(to.url)) {
          return true
        }
        uni.reLaunch({ url: loginPage })
        return false
      }
    },
    fail(err) {
      console.log(err)
    }
  })
})
