<template>
  <div class="app-container">
    <el-col
      style="width: 30%; margin: 10px"
      v-for="item in userList"
      v-bind:key="item.userid"
    >
      <el-card class="card">
        <el-row>
          <!--  头像-->
          <div class="headImgDiv">
            <img
              class="headImg"
              :src="require('../../../../assets/' + item.userHeadImg)"
            />
          </div>
          <!--  背景图-->
          <img
            class="img"
            :src="require('../../../../assets/' + item.backagegroundImg)"
          />
        </el-row>
        <el-row class="cardMessage">
          <el-tag
            >名称:
            <el-tag type="warning">{{ item.name }}</el-tag>
          </el-tag>
          <el-tag
            >关于:
            <el-tag type="warning">{{ item.about }}</el-tag>
          </el-tag>
        </el-row>
        <el-row style="display: flex; flex-direction: column">
          <el-col>
            <el-tag
              >权限：
              <el-tag
                type="danger"
                v-for="role in item.role"
                v-bind:key="role"
                >{{ role }}</el-tag
              >
            </el-tag>
          </el-col>
          <el-col>
            <el-tag type="warning"
              ><a @click="editUserInfofunction(item)">编辑用户信息</a></el-tag
            >
            <el-tag type="danger"
              ><a @click="changePasword(item.userid)">修改密码</a></el-tag
            >
            <el-tag type="danger"
              ><a @click="userCharacter(item)">角色</a></el-tag
            >
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-dialog
      v-if="changePasswordVisable"
      class="isDelete"
      width="40%"
      :title="'密码修改'"
      :visible.sync="changePasswordVisable"
    >
      <h3>请输入您想修改的密码</h3>
      新密码:<el-input type="password" v-model="newPassword"></el-input>
      重新输入新密码:<el-input
        type="password"
        v-model="newPasswordTow"
      ></el-input>
      <div style="text-align: center">
        <el-button @click="yesChangePassword()">YES!</el-button>
        <el-button @click="noChangePassword()">NO!</el-button>
      </div>
    </el-dialog>

    <el-dialog
      v-if="editUserInfoVisable"
      width="80%"
      :title="'用户信息修改'"
      :visible.sync="editUserInfoVisable"
    >
      <editUserInfo
        @close="editUserInfoVisable = false"
        :item="editUserInfo"
      ></editUserInfo>
    </el-dialog>

    <el-dialog
      v-if="userCharactervisable"
      width="38%"
      :title="'用户角色修改'"
      :visible.sync="userCharactervisable"
    >
      <characterUser
        :item="editUserInfo"
        @close="userCharactervisable = false"
      ></characterUser>
    </el-dialog>
  </div>
</template>

<script>
import { getAllUsers, changePassWord } from '@/api/user/api.js'
import editUserInfo from './editUserInfo.vue'
import characterUser from './character/characterUser.vue'

export default {
  data() {
    return {
      // 修改密码弹窗是否弹出
      changePasswordVisable: false,
      // 第一遍输入的新密码
      newPassword: undefined,
      // 第二遍输入的密码
      newPasswordTow: undefined,
      // 修改信息弹窗是否弹出
      editUserInfoVisable: false,
      // 要修改的人员的信息
      editUserInfo: undefined,
      // 当前用户的id
      userId: undefined,
      // 用户角色的弹唱是否弹出
      userCharactervisable: false,
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      userList: [
        {
          username: '123',
          about: 'qwerq'
        }
      ]
    }
  },
  components: {
    characterUser,
    editUserInfo
  },
  created() {
    // 初始化查询出权限如果是管理员可以管理所有用户
    this.getAllUsers()
    // 如果是其他部门的就可以管理他自己部门的相关的人员
  },
  methods: {
    // 获取用户列表
    async getAllUsers() {
      const res = await getAllUsers()
      this.userList = res.data
    },
    // 点击操作按钮触发事件
    edit() {
      console.log('123123')
    },
    changePasword(id) {
      this.userId = id
      this.changePasswordVisable = true
    },
    noChangePassword() {
      this.changePasswordVisable = false
    },
    async yesChangePassword() {
      if (this.newPassword !== this.newPasswordTow) {
        this.$notify({
          title: '失败',
          message: '两次密码输入的不一样!',
          type: 'error'
        })
        return
      }
      const param = { userid: this.userId, password: this.newPassword }
      await changePassWord(param).then((res) => {
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
        this.changePasswordVisable = false
      })
    },
    async editUserInfofunction(item) {
      this.editUserInfo = item
      this.editUserInfoVisable = true
    },
    userCharacter(item) {
      this.editUserInfo = item
      this.userCharactervisable = true
    }
  }
}
</script>
<style>
.card {
  width: 100%;
}

.img {
  width: 100%;
  height: 190px;
}

.cardMessage {
  margin-top: 30px;
  display: flex;
  flex-direction: column;
}

.headImg {
  width: 100px;
  height: 80px;
  border-radius: 10px;
}

.headImgDiv {
  z-index: 1;
  position: absolute;
  top: 125px;
  left: 10px;
}
</style>
