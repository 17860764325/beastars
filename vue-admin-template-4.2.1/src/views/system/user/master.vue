<template>
  <!--用户主页-->
  <div class="page">
    <!--    主页的背景图-->
    <el-row>
      <!--    背景图片和头像信息-->
      <div>
<!--        <el-img :src="require('../../../../assets/navbar_image/'+)"></el-img>-->
      </div>
      <el-img></el-img>
    </el-row>
    <!--    下面信息-->
    <el-row>

    </el-row>
  </div>
</template>

<script>
// import { getUserInfoByUsername } from '../../../api/user.js'
import store from '@/store'
export default {
  data() {
    return {
      userBackImg: undefined,
      userHeadImg: undefined
    }
  },
  created() {
    // 初始化时候获取用户的相关信息。
    console.log("!2312")
    console.log(store.getters.token)
  }
}
</script>

<style scoped>
.page {
  margin-left: 10px;
  margin-right: 10px;
}
</style>
