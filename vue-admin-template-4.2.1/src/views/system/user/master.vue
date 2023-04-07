<template>
  <!--用户主页-->
  <div class="page">
    <!--    主页的背景图-->
    <el-row>
      <!--    背景图片和头像信息-->
      <el-card class="imgCard">
        <div class="backgroundImgDiv">
          <!--        背景-->
          <img
            class="backgroundImg"
            :src="require('../../../assets/' + userInfo.backagegroundImg)"
          />
        </div>
      </el-card>
      <div class="headImgDiv">
        <!--        头像 -->
        <img
          class="headImg"
          :src="require('../../../assets/' + userInfo.userHeadImg)"
        />
      </div>
    </el-row>
    <!--    下面信息-->
    <el-row>
      <el-tabs type="border-card">
        <el-tab-pane label="基本信息">
          <h2>基本信息:</h2>
          <el-button @click="edit()"><i class="el-icon-edit"></i> </el-button>
          <hr-form
            ref="hrTableChangeUsreInfo"
            :formDisabled="!this.isEdit"
            :form-disabled="false"
            :form.sync="form"
            :field-list="fieldList"
          />
          <div>
            <el-button class="button" type="primary" @click="save"
              >保存</el-button
            >
          </div>
        </el-tab-pane>
        <el-tab-pane label="头像以及背景">
          <h2>头像以及背景</h2>
          <div>
            <el-button type="primary" @click="openChooseImg('1')"
              >更改背景</el-button
            >
            <el-button type="success" @click="openChooseImg('2')"
              >更改头像</el-button
            >
          </div>
        </el-tab-pane>
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
import { getUserInfo, changeUserInfo } from '@/api/user/api.js'

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
      userInfo: undefined,
      isEdit: false,
      form: [],
      fieldList: [
        {
          type: 'text',
          label: '昵称',
          prop: 'name',
          rules: {
            required: true
          }
        },
        {
          type: 'text',
          label: '账户名称',
          prop: 'username',
          rules: { required: true }
        },
        {
          type: 'text',
          label: '密码',
          prop: 'password',
          rules: { required: true }
        },
        {
          type: 'text',
          label: '电话',
          prop: 'phone',
          rules: { required: true }
        },
        {
          type: 'text',
          label: '邮箱',
          prop: 'email',
          rules: { required: true }
        },
        {
          type: 'text',
          label: '简介',
          prop: 'about',
          rules: { required: true }
        }
      ]
    }
  },
  created() {
    // 初始化时候获取用户的相关信息。
    this.getLsit()
  },
  methods: {
    edit() {
      this.isEdit = !this.isEdit
    },
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
      this.form = res.data
    },
    closeChangeImg() {
      this.chooseImg.sync = false
      this.getLsit()
      // this.$router.go(0)
    },
    async save() {
      //  console.log(this.form)
      if (!this.$refs.hrTableChangeUsreInfo.validate()) {
        this.$notify({
          title: '失败',
          message: '请填写完整信息!',
          type: 'error'
        })
      } else {
        await changeUserInfo(this.form).then((res) => {
          if (res.status === 200) {
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success'
            })
            this.$emit('close')
          } else {
            this.$notify({
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
          this.isEdit = !this.isEdit
        })
      }
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
  background: linear-gradient(white, white) padding-box,
    repeating-linear-gradient(
        -45deg,
        red 0,
        red 12.5%,
        transparent 0,
        transparent 25%,
        #58a 0,
        #58a 37.5%,
        transparent 0,
        transparent 50%
      )
      0/5em 5em;
  width: 130px;
  height: 100px;
}

.button {
  text-align: center;
  margin: 10px;
}
</style>
