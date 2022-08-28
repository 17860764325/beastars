import router,{error404} from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

// 路由拦截器，在路由前要经过这里
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  // 设置一下，每个页面中的标题meta里面的标题和图标
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  // 获取store里面的token，如果有token的话证明是登录过的
  const hasToken = getToken()

  if (hasToken) {
    // 如果你登录了，就会判断你的目标路径是否是login是的话，那就路由到缺省的路由上去，也就是主页
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      // 如果你的目标路径并不是login的话，那么获取当前用户的角色信息，看看该用户都能访问那些菜单
      // 获取当前用户的动态路由
      const hasGetUserInfo = store.getters.permission_routes
      console.log(hasGetUserInfo, 'has')
      if (hasGetUserInfo.length !== 0) {
        // 获取到，那么就加载继续
        next()
      } else {
        try {
          // get user info
          // 没有获取的权限的话，那么就获取当前用户的权限，重新调用筛选路由的方法，最后将路由筛选出来重新放到路由里面去。
          const routerLists = await store.dispatch('permission/generateRoutes')
          console.log(routerLists, 'routerLists')
          // 将路由表动态的挂载到路由上去
          router.options.routes = router.options.routes.concat(routerLists)
          router.addRoutes(routerLists)
          // router.addRoutes('home',
          //   {
          //     path: '/404',
          //     component: () => import('@/views/404'),
          //     hidden: true
          //   })
          // router.addRoutes({ path: '*', redirect: '/404', hidden: true })
          next({ ...to, replace: true })
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    // 如果没有token的话那么就得重新登陆
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
