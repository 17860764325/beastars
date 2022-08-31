import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import './components/index'
import _ from 'lodash'
import { getDicts } from '@/api/dict/api'
import inputRules from '@/utils/inputRules' // 引入输入验证
import validate from '@/utils/validate' // 输入验证方法引入
import QuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.bubble.css'
import 'quill/dist/quill.snow.css'
import Vant, { Lazyload } from 'vant'
import 'vant/lib/index.css'

Vue.use(Vant)
Vue.use(Lazyload)

import Vuelazyload from 'vue-lazyload'

Vue.use(Vuelazyload, {})
Vue.use(QuillEditor)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
Vue.prototype.getDicts = getDicts

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
Vue.use(_)
Vue.use(inputRules)
Vue.prototype.GLOBAL = validate

Vue.config.productionTip = false

new Vue({
  router,
  store,
  el: '#app',
  render: h => h(App)
}).$mount('#app')
