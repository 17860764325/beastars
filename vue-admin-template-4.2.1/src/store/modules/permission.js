// 路由守卫
import { constantRoutes, asyncRouters, error404 } from '../../router'
import { asyncRoutes } from '../../router/asyncRoutes'
import { getUserFatherRouterPaths, getUserSonRouterPaths } from '../../api/system/page'

/**
 * 使用meta。角色，以确定当前用户是否具有权限
 * @param roles
 * @param route
 */
// // 匹配权限
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     return roles.some(role => route.meta.roles.includes(role))
//   } else {
//     return true
//   }
// }

/**
 * 通过递归筛选异步路由表
 * @param routes asyncRoutes
 * @param roles
 */
export async function filterAsyncRoutes(routes, roles) {
  // 循环所有的路有有权限的返回true就显示，没有权限的返回false就不加入到路由里面
  // 循环所有的路由
  // routes.forEach(route => {
  //   const tmp = { ...route }
  //   // 判断该路由是否
  //   if (hasPermission(roles, tmp)) {
  //     if (tmp.children) {
  //       tmp.children = filterAsyncRoutes(tmp.children, roles)
  //     }
  //     res.push(tmp)
  //   }
  // })
  // 自己的方法，直接后端已经将这个人能访问的所有页面计算出来了
  // 现在只需要，获取到数据，并且转换成为，router的树状态结构
  // const res = await getRouterListFromMysql()
  // res.data.forEach(item => {
  //   item.children.forEach(element => {
  //     const data = `@/views/${element.component}`
  //     element.component = () => import(data)
  //   })
  // })

  // 获取所有的父页面
  const father = await getUserFatherRouterPaths()
  // 获取所有的子页面
  const son = await getUserSonRouterPaths()

  const fatherRouters = []
  // 循环父页面
  father.data.forEach(item => {
    asyncRouters.forEach(elemrnt => {
      if (item === elemrnt.path) {
        const childrenCopy = []
        son.data.forEach(son => {
          elemrnt.children.forEach(children => {
            if (son === children.path) {
              childrenCopy.push(children)
            }
          })
        })
        elemrnt.children = childrenCopy
        fatherRouters.push(elemrnt)
      }
    })
  })
  console.log(error404)
  fatherRouters.push(error404)
  console.log(fatherRouters, '筛选过后的路有列表')
  return fatherRouters
}

const state = {
  routes: [], addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    // 静态路由
    console.log(routes, 'routers')
    state.routes = constantRoutes.concat(routes) // 将过滤后的路由和constantRoutes存起来
  }
}

// 筛选
// 根据纯属进来的roles，筛选出路由
const actions = {
  generateRoutes({ commit }, roles) {
    // 压根就没有将当前人的权限传输过来
    console.log(roles, 'roles')
    return new Promise(async resolve => {
      // 路由是否有admin,有直接全部显示
      // 过滤路由（过滤出当前用户能看见的页面和菜单）
      const accessedRoutes = await filterAsyncRoutes(asyncRoutes, roles)
      console.log(accessedRoutes, 'acc')
      // accessedRoutes这个就是当前角色可见的动态路由
      // 将过滤后的路由添加到路由表中
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true, state, mutations, actions
}

