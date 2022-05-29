<template>
  <div class="dashboard-container">
    <!-- <div class="dashboard-text">Dear {{ name }} master, I'm glad to serve you! </div> -->
    <img
      :src="require('../../assets/weekWallPaper/'+emptyImage)"
      class="emptyImage"
      alt="logoHome"
    >
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  // eslint-disable-next-line vue/order-in-components
  data() {
    return {
      emptyImage: ''
    }
  },
  created() {
    // 每天随机主页壁纸
    this.wallPaperRandem(new Date().getDay())
  },
  methods: {
    wallPaperRandem(day) {
      switch (day) {
        case 0:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '1.jpg'
          } else {
            this.emptyImage = '14.jpg'
          }
          break
        case 1:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '2.jpg'
          } else {
            this.emptyImage = '13.png'
          }
          break
        case 2:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '3.png'
          } else {
            this.emptyImage = '12.jpg'
          }
          break
        case 3:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '4.png'
          } else {
            this.emptyImage = '11.jpg'
          }
          break
        case 4:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '5.jpg'
          } else {
            this.emptyImage = '10.jpg'
          }
          break
        case 5:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '6.jpg'
          } else {
            this.emptyImage = '9.jpg'
          }
          break
        case 6:
          if ((Math.round(Math.random() * 6)) % 2 === 0) {
            this.emptyImage = '7.jpg'
          } else {
            this.emptyImage = '8.jpg'
          }
          break
        default:
          break
      }
    }

  }
}
</script>

<style lang="scss" scoped>
.dashboard-text{
  border: black solid 5px;
}
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.emptyImage {
  display: block;
  width: 100%;
  margin: 0 auto;
}
.dashboard-container{
 width: 100%;
 height: 100%;
  background-color: #e3e3e3;
}
</style>
