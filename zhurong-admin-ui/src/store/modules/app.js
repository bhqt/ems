import Cookies from 'js-cookie'
import {getLogoInfo, getConfigKey} from "@/api/system/config"
import { getDefaultLang } from '@/i18n'

// 平台默认标题（与 vue.config.js 中的 name 保持一致）
// 这里写死为「智慧能源监控平台」，避免后端 sys_config.sys.title
// 被误改/历史数据残留（如"祝融能源管理系统"、"智碳能源管理系统"）
// 时，导致 Tab 标题不符合项目统一规范。
const DEFAULT_SYS_TITLE = '智慧能源监控平台'

const state = {
  sidebar: {
    opened: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    withoutAnimation: false,
    hide: false
  },
  device: 'desktop',
  size: Cookies.get('size') || 'medium',
  logoInfo: {},
  language: getDefaultLang()
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    if (state.sidebar.hide) {
      return false;
    }
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1)
    } else {
      Cookies.set('sidebarStatus', 0)
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  },
  SET_SIZE: (state, size) => {
    state.size = size
    Cookies.set('size', size)
  },
  SET_SIDEBAR_HIDE: (state, status) => {
    state.sidebar.hide = status
  },
  SET_LOGOINFO: (state, logoInfo) => {
    state.logoInfo = logoInfo
  },
  SET_THEME: (state, theme) => {
    state.logoInfo.theme = theme
  },
  SET_LANGUAGE: (state, language) => {
    state.language = language
    Cookies.set('language', language)
  }
}

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setSize({ commit }, size) {
    commit('SET_SIZE', size)
  },
  toggleSideBarHide({ commit }, status) {
    commit('SET_SIDEBAR_HIDE', status)
  },
  getLogoInfo({commit}) {
    document.getElementsByTagName('body')[0].className = 'theme-light'
    getLogoInfo().then(res => {
      // 设置浏览器icon、标题
      // 注：sysTitle 来自后端 sys_config.sys.title 配置项。
      // 若后端未配置 / 返回为空 / 返回非项目规范的旧值（如「祝融能源管理系统」/「智碳能源管理系统」），
      // 一律兜底使用前端写死的 DEFAULT_SYS_TITLE，避免 Tab title 不符合规范。
      const backendSysTitle = res.data?.sysTitle
      const sysTitle = (backendSysTitle && backendSysTitle !== '祝融能源管理系统' && backendSysTitle !== '智碳能源管理系统')
        ? backendSysTitle
        : DEFAULT_SYS_TITLE
      if (res.data?.browserLogo) {
        document.querySelector("link[rel*='icon']").href = res.data.browserLogo
      }
      document.title = sysTitle
      // 设置主题
      let localTheme = localStorage.getItem('theme')
      let theme = res.data.theme || 'theme-light'
      document.getElementsByTagName('body')[0].className = localTheme || theme
      if(!localTheme) localStorage.setItem('theme', localTheme || theme)
      let result = {
        ...res.data,
        theme: localTheme || theme
      }
      commit('SET_LOGOINFO', result)
    })
  },
  setTheme({commit}, theme) {
    commit("SET_THEME", theme)
  },
  setLanguage({ commit }, language) {
    commit('SET_LANGUAGE', language)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
