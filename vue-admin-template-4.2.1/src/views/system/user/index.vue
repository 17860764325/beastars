<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="用户管理" name="userManagement">
        <userManagement></userManagement>
      </el-tab-pane>
      <!-- <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane> -->
      <el-tab-pane label="权限分配" name="roleManagement">
        <role></role>
      </el-tab-pane>
      <!-- <el-tab-pane label="权限页面关联" name="organizationalStructure"
        >权限页面关联</el-tab-pane -->
      <!-- > -->
    </el-tabs>
    <div v-show="visable">asdfasdfasdfasdfasdfasdfasdfasdfasd</div>
  </div>
</template>
<script>
import userManagement from '@/views/system/user/components/userManagement'
import role from './components/role/role.vue'
export default {
  components: {
    userManagement,
    role
  },
  data() {
    return {
      visable: true,
      activeName: 'second'
    }
  },
  methods: {
    handleClick(tab, event) {
      //  console.log(tab, event)
      switch (tab.name) {
        case 'userManagement':
          this.visable = false
          //  console.log(tab.name)
          break
        case 'roleManagement':
          this.visable = false
          //  console.log(tab.name)
          break
        case 'third':
          this.visable = false
          //  console.log(tab.name)
          break
        case 'organizationalStructure':
          this.visable = false
          //  console.log(tab.name)
          break
        default:
          break
      }
    }
  }
}
</script>
