import { loginByUsername, logout, getUserInfo,autoLogin } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { encrypt, decrypt } from '@/utils/encrypt'

const user = {
  state: {
    user: '',    
    token: getToken(),
    name: '',
    email: '',
    introduction: '',
    roles: [],
    setting: {
      articlePlatform: []
    },
    rtoken:'',
    exitime:0
  },

  mutations: {  
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_RTOKEN: (state, rtoken) => {
      state.rtoken = rtoken
    },
    SET_EXTIME: (state, extime) => {
      state.extime = extime
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },   
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_EMAIL: (state, email) => {
      state.email = email
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      const username = userInfo.username.trim();
      const password = encrypt(userInfo.password.trim());//userInfo.password.trim();//
      //console.log(password);
      //console.log(decrypt(password));

      return new Promise((resolve, reject) => {
        loginByUsername(username, password).then(response => {             
          const data = response
          commit('SET_TOKEN', data.access_token)
          commit('SET_RTOKEN', data.refresh_token)         
          commit('SET_EXTIME', Date.now()+data.expires_in*1000)
          setToken(data.access_token,data.refresh_token,Date.now()+data.expires_in*1000,userInfo.iskeeplogin)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    AutoLogin({ commit, state }) {     
      return new Promise((resolve, reject) => {
        autoLogin(state.rtoken).then(response => {             
          const data = response        
          commit('SET_TOKEN', data.access_token)
          commit('SET_RTOKEN', data.refresh_token)         
          commit('SET_EXTIME', Date.now()+data.expires_in*1000)
          setToken(data.access_token,data.refresh_token,Date.now()+data.expires_in*1000,true)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getUserInfo(state.token).then(response => {
          if (!response.data) { // 由于mockjs 不支持自定义状态码只能这样hack
            reject('error')
          }
          const data = response.data
          commit('SET_ROLES', data.roles)
          commit('SET_NAME', data.username)         
          commit('SET_INTRODUCTION', data.introduction)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 第三方验证登录
    // LoginByThirdparty({ commit, state }, code) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_CODE', code)
    //     loginByThirdparty(state.status, state.email, state.code).then(response => {
    //       commit('SET_TOKEN', response.data.token)
    //       setToken(response.data.token)
    //       resolve()
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_RTOKEN', '')
          commit('SET_EXTIME', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_TOKEN', '')
        commit('SET_RTOKEN', '')
        commit('SET_EXTIME', '')
        removeToken()
        resolve()
      })
    },

    // 动态修改权限
    ChangeRole({ commit }, role) {
      return new Promise(resolve => {
        commit('SET_TOKEN', role)
        setToken(role)
        getUserInfo(role).then(response => {
          const data = response.data
          commit('SET_ROLES', data.role)
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          commit('SET_INTRODUCTION', data.introduction)
          resolve()
        })
      })
    }
  }
}

export default user
