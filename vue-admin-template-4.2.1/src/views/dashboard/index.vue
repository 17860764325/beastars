<template>
  <div class="dashboard-container">
    <div class="dashboard-text">Dear {{ name }} master, I'm glad to serve you! </div>
    <img
      :src="emptyGif"
      class="emptyGif"
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
      emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3'
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
.emptyGif {
  display: block;
  width: 65%;
  margin: 0 auto;
}
.dashboard-container{
 width: 100%;
 height: 100%;
  background-color: #e3e3e3;
}
</style>
