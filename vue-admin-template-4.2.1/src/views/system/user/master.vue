<template>
  <!--用户主页-->
  <div class="page">
    <!--    主页的背景图-->
    <el-row>
      <!--    背景图片和头像信息-->
      <el-card class="imgCard">
        <div class="backgroundImgDiv">
          <!--        背景-->
          <img class="backgroundImg" :src="require('../../../assets/' + userInfo.backagegroundImg)"/>
        </div>
      </el-card>
      <div class="headImgDiv">
        <!--        头像 -->
        <img class="headImg" :src="require('../../../assets/' + userInfo.userHeadImg)"/>
      </div>
    </el-row>
    <!--    下面信息-->
    <el-row>
      <el-tabs type="border-card">
        <el-tab-pane label="基本信息">
          <h2>基本信息:</h2>

          <el-button @click="openChooseImg('1')">更改背景</el-button>
          <el-button @click="openChooseImg('2')">更改头像</el-button>
        </el-tab-pane>
        <el-tab-pane label="其他">其他</el-tab-pane>
        <el-tab-pane label="其他">其他</el-tab-pane>
        <el-tab-pane label="其他">其他</el-tab-pane>
      </el-tabs>
    </el-row>
    <!--    选择头像和背景图片的弹窗-->
    <el-dialog
      v-if="chooseImg.sync"
      width="50%"
      append-to-body
      :title="'选择图片'"
      :visible.sync="chooseImg.sync"
    >
      <selectImage
        :type="chooseImg.type"
        :userLocalImg="userInfo.userHeadImg"
        :userLocalBackGroundImg="userInfo.backagegroundImg"
        @cancel="closeChangeImg()"
      ></selectImage>
    </el-dialog>
  </div>
</template>

<script>
import selectImage from '@/views/system/user/components/SelectImage'
// import store from '@/store'
import { getUserInfo } from '@/api/user/api.js'

export default {
  components: {
    selectImage
  },
  data() {
    return {
      userBackImg: undefined,
      userHeadImg: undefined,
      chooseImg: {
        sync: false,
        type: undefined
      },
      userInfo: undefined
    }
  },
  created() {
    // 初始化时候获取用户的相关信息。
    this.getLsit()
  },
  methods: {
    openChooseImg(data) {
      if (data === '1') {
        // 选择背景图片
        this.chooseImg.type = 'b'
        this.chooseImg.sync = true
      } else {
        // 选择背景图
        this.chooseImg.type = 'h'
        this.chooseImg.sync = true
      }
    },
    async getLsit() {
      const res = await getUserInfo()
      this.userInfo = res.data
    },
    closeChangeImg() {
      this.chooseImg.sync = false
      this.getLsit()
      // this.$router.go(0)
    }
  }
}
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
  margin-right: 10px;
}

.imgCard {
  width: 100%;
  height: 250px;
  display: flex;
  overflow-y: auto;
}

.backgroundImgDiv {
  width: 100%;
  height: 100px;

}

.backgroundImg {
  width: 100%;
  /*height: 300px*/
}

.headImgDiv {
  width: 130px;
  height: 100px;
  position: fixed;
  top: 200px;
  left: 250px;
  z-index: 1;
}

.headImg {
  padding: 5px;
  border: 5px solid transparent;
  border-radius: 10px;
  background: linear-gradient(white, white) padding-box, repeating-linear-gradient(-45deg, red 0, red 12.5%, transparent 0, transparent 25%, #58a 0, #58a 37.5%, transparent 0, transparent 50%) 0/5em 5em;
  width: 130px;
  height: 100px;
}
</style>
