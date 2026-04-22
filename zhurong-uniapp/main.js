import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
import apis from './api/apiIndex' // apis
// 【注意】小程序（如微信小程序）采用局部组件注册优先的原则，且对全局组件的支持有限。全局组件在微信小程序中不能正常使用。
// import components from './components' // 全局组件
import './permission' // permission

// 引入全局样式
import '@/static/styles/index.scss'

// 注册全局组件
// 【注意】小程序（如微信小程序）采用局部组件注册优先的原则，且对全局组件的支持有限。全局组件在微信小程序中不能正常使用。
// Vue.use(components)

/**
 *  直接在Vue实例挂载几个常用的函数
 *  所有测试用数据均存放于根目录json.js
 *
 *  css部分使用了App.vue下的全局样式和iconfont图标。
 *  示例使用了uni.scss下的变量, 除变量外已尽量移除特有语法,可直接替换为其他预处理器使用
 */
const msg = (title, duration=1500, mask=false, icon='none')=>{
	//统一提示方便全局修改
	if(Boolean(title) === false){
		return;
	}
	uni.showToast({
		title,
		duration,
		mask,
		icon
	});
}
const json = type=>{
	//模拟异步请求数据
	return new Promise(resolve=>{
		setTimeout(()=>{
			resolve(Json[type]);
		}, 500)
	})
}

const prePage = ()=>{
	let pages = getCurrentPages();
	let prePage = pages[pages.length - 2];
	// #ifdef H5
	return prePage;
	// #endif
	return prePage.$vm;
}

Vue.use(plugins)
Vue.use(apis)

Vue.config.productionTip = false
Vue.prototype.$store = store;

Vue.prototype.$fire = new Vue();
Vue.prototype.$api = {msg, json, prePage};

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
