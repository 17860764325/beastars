export default {
  install(Vue) {
    // 打开新页签
    // 结构options，pkName默认为id
    // 生成当前pageId默认为状态的首字母加当前时间
    // 如果当前pkName存在，并且传入options中存在当前pkName对应的值，根据状态的首字母加options拼接成新的pageId
    // 打开router.js中配置的路由
    Vue.prototype.$openTag = (vm, options) => {
      const { name, status, pkName = 'id' } = options
      let pageId = `${status[0]}_${Date.now()}`
      if (pkName && options && options[pkName]) {
        pageId = `${status[0]}_${options[pkName]}`
      }
      vm.$router.push({ name, params: { pageId, ...options } })
    }

    // 关闭新页签
    // 删除当前页签在Vuex和sessionStorage中visitedViews的缓存
    // 如果存在目标路由跳转到目标路由
    // 否则如果当前模块visitedViews中还有其他的的页签跳转到上一页签，如果不存在跳转到当前模块的工作台
    Vue.prototype.$closeTag = (vm, to) => {
      const view = {
        path: vm.$route.path,
        name: vm.$route.name,
        currentTab: vm.$store.getters.currentTab
      }
      vm.$store.dispatch('tagsView/delVisitedTag', view).then(views => {
        if (to) {
          vm.$router.push(to)
        } else {
          const latestView = views.slice(-1)[0]
          if (latestView) {
            vm.$router.push(latestView.path)
          } else {
            vm.$router.push(`/${vm.$store.getters.currentTab}`)
          }
        }
      })
    }

    // 关闭当前页签，打开新页签
    Vue.prototype.$closeAndOpenTag = (vm, options) => {
      const view = {
        path: vm.$route.path,
        name: vm.$route.name,
        currentTab: vm.$store.getters.currentTab
      }
      vm.$store.dispatch('tagsView/delVisitedTag', view)

      const { name, status, pkName = 'id' } = options
      let pageId = `${status[0]}_${Date.now()}`
      if (pkName && options && options[pkName]) {
        pageId = `${status[0]}_${options[pkName]}`
      }
      vm.$router.push({ name, params: { pageId, ...options } })
    }
  }
}
