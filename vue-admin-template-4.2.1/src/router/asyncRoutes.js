import Layout from '@/layout'
export const asyncRoutes = [
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        // 重点：role
        meta: { title: 'External Link', icon: 'link', role: ['admin'] }
      }
    ]
  }
]
