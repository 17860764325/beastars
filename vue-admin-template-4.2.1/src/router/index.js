import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard☘️', icon: 'dashboard' }
    }]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: '想做的事🦾', icon: 'table' }
      },
      {
        path: 'dateDetail/:dateId',
        name: 'dateDetail',
        component: () => import('@/views/form/detail/index'),
        meta: { title: '事情详情计划🛎', icon: 'form' },
        props: true,
        hidden: true

      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    meta: { title: '系统管理⚙️', icon: 'eye-open' },
    children: [
      {
        path: 'dict',
        name: 'dict',
        component: () => import('@/views/system/dict/index'),
        meta: { title: '字典', icon: 'table' }
      },
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/system/user/index'),
        meta: { title: 'master', icon: 'user' }

      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
