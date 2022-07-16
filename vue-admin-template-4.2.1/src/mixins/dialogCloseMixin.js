export default {
  methods: {
    closeDialog(that) {
      if (!that) return
      if (that.$data) {
        Object.keys(that.$data).forEach(key => {
          if (key.toLowerCase().match('visible') && typeof that.$data[key] === 'boolean') {
            that.$data[key] = false
          }
        })
      }
      if (that.$refs) {
        Object.keys(that.$refs).forEach(el => {
          if (el && that.$refs[el]) {
            // 如果是v-for循环生成的子组件实例，ref是数组
            if (that.$refs[el].length) {
              that.$refs[el].forEach(e => {
                this.closeDialog(e)
              })
            } else {
              this.closeDialog(that.$refs[el])
            }
          }
        })
      }
    }
  },
  // 浏览器前进后退
  beforeRouteLeave(to, from, next) {
    this.closeDialog(this)
    next()
  }
}
