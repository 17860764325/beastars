import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import { getAllDicts } from '@/api/dict/api'
import store from '@/store'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  dicts: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  // 数据字典
  SET_DICTS: (state, dicts) => {
    state.dicts = dicts
  }
  // 添加
  //   addMenu(state, router) {
  //     // 查看是否
  //     if (!Cookie.get('menu')) {
  //       return
  //     }
  //     let menu = JSON.parse(Cookie.get('menu'))
  //     state.routerList = menu
  //     let currentMenu = [
  //       {
  //         path: '/',
  //         component: () => import(`@/views/Mine`),
  //         children: []
  //       }
  //     ]
  //     menu.forEach(item => {
  //       if (item.children) {
  //         item.children = item.children.map(item => {
  //           item.component = () => import(`@/views/${item.url}`)
  //           return item
  //         })
  //         currentMenu[0].children.push(...item.children)
  //       } else {
  //         item.component = () => import(`@/views/${item.url}`)
  //         currentMenu[0].children.push(item)
  //       }
  //     })
  //     router.addRoutes(currentMenu)
  //   },
  // }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      // 存放token
      login({ username: username.trim(), password: password }).then(async response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  async getInfo({ commit, state }) {
    // 数据字典获取值
    const res = await getAllDicts()
    const dicts = res.data
    commit('SET_DICTS', dicts)
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { name, avatar } = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

