import tab from './tab'
import auth from './auth'
import modal from './modal'
import loadMoreData from './loadMoreData'
import dateTime from './dateTime'
import stringUtil from './stringUtil'
import iconUtil from './iconUtil'

// 在main.js中引入并使用Vue.use(plugins)
// 使用方法：
// js中：this.$modal.alert({
// 模版中可以省略this.直接：$modal.alert({
export default {
	install(Vue) {
		// 页签操作
		Vue.prototype.$tab = tab
		// 认证对象
		Vue.prototype.$auth = auth
		// 模态框对象
		Vue.prototype.$modal = modal
        // 加载更多数据
		Vue.prototype.$loadMoreData = loadMoreData
		// 日期时间工具
		Vue.prototype.$dateTime = dateTime
		// 字符串工具
		Vue.prototype.$stringUtil = stringUtil
		// icon工具
		Vue.prototype.$iconUtil = iconUtil
	}
}
