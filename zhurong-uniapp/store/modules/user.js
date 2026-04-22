import config from '@/config'
import storage from '@/utils/storage'
import constant from '@/utils/constant'
import {loginByPhoneNo, loginByUsernameAndPassword, logout, getInfo} from '@/api/system/login'
import {getToken, setToken, removeToken} from '@/utils/auth'

const baseUrl = config.baseUrl

const user = {
    // 添加新的参数，需要找storage.js中添加对应的存储节点变量名
    state: {
        token: getToken(),
        name: storage.get(constant.name),
        userId: storage.get(constant.userId),
        avatar: storage.get(constant.avatar),
        roles: storage.get(constant.roles),
        permissions: storage.get(constant.permissions),
        hasLogin: storage.get("hasLogin"),
        baseInfo: storage.get("baseInfo"),
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_NAME: (state, name) => {
            state.name = name
            storage.set(constant.name, name)
        },
        SET_USER_ID: (state, userId) => {
            state.userId = userId
            storage.set(constant.userId, userId)
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
            storage.set(constant.avatar, avatar)
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
            storage.set(constant.roles, roles)
        },
        SET_PERMISSIONS: (state, permissions) => {
            state.permissions = permissions
            storage.set(constant.permissions, permissions)
        },
        SET_USER_BASEINFO: (state, baseInfo) => {
            state.baseInfo = baseInfo
            storage.set("baseInfo", baseInfo)
        },
        SET_HAS_LOGIN: (state, hasLogin) => {
            console.log("hasLogin=", hasLogin)
            //标记当前是否已经登录，后续页面跳转时通过其进行判断，没有登录时则跳转到登录页面
            state.hasLogin = hasLogin
            storage.set("hasLogin", hasLogin)
        }
    },

    actions: {
        // 用户名密码登录
        LoginByUsernameAndPassword({commit}, userInfo) {
            const username = userInfo.username.trim()
            const password = userInfo.password
            const code = userInfo.code
            const uuid = userInfo.uuid
            return new Promise((resolve, reject) => {
                loginByUsernameAndPassword(username, password, code, uuid).then(res => {
                    console.log("res", res)
                    console.log("res.data.token=", res.data.token)
                    setToken(res.data.token)
                    commit('SET_TOKEN', res.data.token)
                    commit('SET_HAS_LOGIN', true)
                    resolve()
                }).catch(error => {
                    console.log("error=", error)
                    reject(error)
                })
            })
        },

        // 手机号码一键登录
        LoginByPhoneNo({commit}, phoneNo) {
            return new Promise((resolve, reject) => {
                loginByPhoneNo(phoneNo).then(res => {
                    // console.log("LoginByPhoneNo：res=",res)
                    console.log("LoginByPhoneNo：res.token=", res.token)
                    setToken(res.token)
                    commit('SET_TOKEN', res.token)
                    commit('SET_HAS_LOGIN', true)
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 获取用户信息
        GetInfo({commit, state}) {
            return new Promise((resolve, reject) => {
                getInfo().then(res => {
                    console.log("获取登录用户信息：res=", JSON.stringify(res))
                    const user = res.data.user
                    console.log("获取登录用户信息：user=", user)
                    const avatar = (user == null || user.avatar == "" || user.avatar == null) ? baseUrl + user.avatar : baseUrl + user.avatar
                    const username = (user == null || user.userName == "" || user.userName == null) ? "" : user.userName
					// const avatar = (user == null || user.avatar == "" || user.avatar == null) ? require("@/static/images/profile.jpg") : baseUrl + user.avatar
					const userId = (user == null || user.userId == "" || user.userId == null) ? "" : user.userId
                    if (res.roles && res.roles.length > 0) {
                        commit('SET_ROLES', res.roles)
                        commit('SET_PERMISSIONS', res.permissions)
                    } else {
                        commit('SET_ROLES', ['ROLE_DEFAULT'])
                    }
                    commit('SET_NAME', username)
                    commit('SET_USER_ID', userId)
                    commit('SET_AVATAR', avatar)
                    commit('SET_USER_BASEINFO', user)
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 退出系统
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                // logout(state.token).then(res => {
                console.log("user开始退出登录=")
                commit('SET_TOKEN', '')
                commit('SET_ROLES', [])
                commit('SET_PERMISSIONS', [])
                commit('SET_HAS_LOGIN', false)
                removeToken()
                storage.clean()
                resolve()
                console.log("user完成退出登录=")
                // }).catch(error => {
                //   reject(error)
                // })
            })
        }
    }
}

export default user
