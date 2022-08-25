<template>
  <div class="app-container ">
    <el-col style="width: 30%;margin:10px" sm="3" v-for="(item) in userList" v-bind:key="item.id">
      <el-card class="card">
        <el-row>
          <!--  头像-->
          <div class="headImgDiv">
            <img
              class="headImg"
              :src="require('../../../../assets/navbar_image/'+item.userHeadImg)"
            />
          </div>
          <!--  背景图-->
          <img class="img" :src="require('../../../../assets/weekWallPaper/'+ item.backagegroundImg)"/>
        </el-row>
        <el-row class="cardMessage">
          <el-tag>User Name:
            <el-tag type="warning">{{ item.username }}</el-tag>
          </el-tag>
          <el-tag>User About:
            <el-tag type="warning">{{ item.about }}</el-tag>
          </el-tag>
        </el-row>
        <el-row style="display: flex;flex-direction: column">
          <el-col>
            <el-tag>权限：
              <el-tag type="danger" v-for="(role) in item.role" v-bind:key="role">{{ role }}</el-tag>
            </el-tag>
          </el-col>
          <el-col>
            <el-tag type="success"><a @click="edit">查看用户信息</a></el-tag>
            <el-tag type="warning"><a @click="edit">编辑用户信息</a></el-tag>
            <el-tag type="danger"><a @click="edit">修改/密码</a></el-tag>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </div>
</template>

<script>
import { getAllUsers } from '@/api/user/api.js'

export default {
  data() {
    return {
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      userList: [{
        username: '123',
        about: 'qwerq'
      }]
    }
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
  flex-direction: column
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
