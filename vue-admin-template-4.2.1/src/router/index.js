import Vue from 'vue'
// import store from '../store'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

// 静态路由
export const constantRoutes = [

  {
    path: '/login', component: () => import('@/views/login/index'), hidden: true
  }, {
    path: '/', component: Layout, redirect: '/dashboard', children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'HOME☘️', icon: 'dashboard' }
    }]
  }]

// 动态路由
export const asyncRouters = [{
  path: '/form', component: Layout, meta: { title: '日程日志', icon: 'table' }, children: [{
    path: 'index',
    name: 'index',
    component: () => import('@/views/form/index'),
    meta: { title: '想做的事🦾', icon: 'table' }
  }, {
    path: 'dateDetail/:dateId',
    name: 'dateDetail',
    component: () => import('@/views/form/detail/index'),
    meta: { title: '事情详情计划🛎', icon: 'form' },
    props: true,
    hidden: true
  }, {
    path: 'test',
    name: 'test',
    component: () => import('@/views/form/index'),
    meta: { title: 'test测试页面', icon: 'table' }
  }]
}, {
  path: '/system', component: Layout, meta: { title: '系统管理⚙️', icon: 'eye-open' }, children: [{
    path: 'dict',
    name: 'dict',
    component: () => import('@/views/system/dict/index'),
    meta: { title: '字典📚', icon: 'table' }
  }, {
    path: 'user',
    name: 'user',
    component: () => import('@/views/system/user/master'),
    meta: { title: '个人中心🫠', icon: 'user' }
  }, {
    path: 'userManagement',
    name: 'userManagement',
    component: () => import('@/views/system/user/index'),
    meta: { title: 'master', icon: 'user' }
  }, {
    path: 'page',
    name: 'page',
    component: () => import('@/views/system/menu/index'),
    meta: { title: '页面管理', icon: 'eye-open' }
  }, {
    path: 'loginIpLog',
    name: 'loginIpLog',
    component: () => import('@/views/system/loginIpLog/index'),
    meta: { title: '登陆系统记录', icon: 'eye-open' }
  }]
}, {
  path: '/workBench', component: Layout, meta: { title: '工具台🛠', icon: 'nested' }, children: [{
    path: 'ebbinghaus',
    name: 'ebbinghaus',
    component: () => import('@/views/workBench/ebbinghaus/index'),
    meta: { title: '艾宾浩斯以往记忆曲线表📈', icon: 'dashboard' }
  }, {
    path: 'code',
    name: 'code',
    component: () => import('@/views/workBench/code/index'),
    meta: { title: '代码生成👨🏻‍🔧', icon: 'dashboard' }
  }, {
    path: 'drawing',
    name: 'drawing',
    component: () => import('@/views/workBench/drawing/index'),
    meta: { title: '你的图纸世界👨🏻‍🔬', icon: 'dashboard' }
  }, {
    path: 'study',
    name: 'study',
    component: () => import('@/views/workBench/study/index'),
    meta: { title: '成人高考资料📝', icon: 'dashboard' }
  }, {
    path: 'PunchTheClock',
    name: 'PunchTheClock',
    component: () => import('@/views/workBench/PunchTheClock/index'),
    meta: { title: 'Keeping Life', icon: 'dashboard' }
  }
    // 考研，自媒体、模块
    // {
    //   path: 'study',
    //   name: 'study',
    //   component: () => import('@/views/workBench/study/index'),
    //   meta: { title: '成人高考资料📝', icon: 'dashboard' }
    // }
  ]
}, {
  path: '/otherWorld', component: Layout, meta: { title: '异世界👨🏻‍🚀', icon: 'link' }, children: [{
    path: 'gongqijun',
    name: 'gongqijun',
    component: () => import('@/views/otherWorld/gongqijun/index'),
    meta: { title: '宫崎骏的动漫世界🎑', icon: 'link' }
  }, {
    path: 'beastars',
    name: 'beastars',
    component: () => import('@/views/otherWorld/beastars/index'),
    meta: { title: 'beastars🐺🐰🐼🐑  ', icon: 'link' }
  }, {
    path: 'otherWorld',
    name: 'otherWorld',
    component: () => import('@/views/otherWorld/otherWorld/index'),
    meta: { title: '其他的世界🌃', icon: 'link' }
  }]
}]
// 404 page must be placed at the end !!!
export const error404 = { path: '*', component: () => import('@/views/404'), hidden: true }
export const createRouter = () => new Router({
  // mode: 'history', // require service support
  mode: 'hash', base: '/vue/app/', scrollBehavior: () => ({ y: 0 }), routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
