import login from './system/login.js'
import user from './system/user'
import dictManage from './system/dictManage'
import address from './autoee/address'
import newsManage from './autoee/newsManage'

// 在main.js中引入并使用Vue.use(apis)
export default {
  install(Vue) {
	// 需要多处使用的api可以进行全局挂载
    // Vue.prototype.$api_login = login
    Vue.prototype.$http = {
      user: user,
      dictManage: dictManage,
      address: address,
      newsManage: newsManage,
    };

  }
}
