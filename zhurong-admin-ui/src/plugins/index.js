import tab from './tab'
import auth from './auth'
import cache from './cache'
import modal from './modal'
import download from './download'

import common from './common'
import validate from './validate'
import validateRules from "./validateRules";

export default {
  install(Vue) {
    // 页签操作
    Vue.prototype.$tab = tab
    // 认证对象
    Vue.prototype.$auth = auth
    // 缓存对象
    Vue.prototype.$cache = cache
    // 模态框对象
    Vue.prototype.$modal = modal
    // 下载文件
    Vue.prototype.$download = download


    // 公共方法：将 common 对象添加到 Vue 实例的原型上，从而使其在所有 Vue 组件中可用。
    // 这意味着你可以在任何组件中通过 this.$common 访问 common 中定义的方法和属性。
    // 如：if (this.$common.isEmpty(this.someValue)) {
    Vue.prototype.$common = common
    // 校验
    Vue.prototype.$validate = validate
    // 校验Form表单的rules
    Vue.prototype.$validateRules = validateRules

  }
}
