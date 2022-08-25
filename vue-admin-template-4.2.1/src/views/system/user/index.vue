<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="用户管理" name="userManagement">
        <userManagement></userManagement>
      </el-tab-pane>
      <el-tab-pane label="权限分配" name="roleManagement">权限分配</el-tab-pane>
      <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
      <el-tab-pane label="组织架构图" name="organizationalStructure">组织架构图</el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import userManagement from '@/views/system/user/components/userManagement'
export default {
  components: {
    userManagement
  },
  data() {
    return {
      activeName: 'second'
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
      switch (tab.name) {
        case 'userManagement':
          console.log(tab.name)
          break
        case 'roleManagement':
          console.log(tab.name)
          break
        case 'third':
          console.log(tab.name)
          break
        case 'organizationalStructure':
          console.log(tab.name)
          break
        default:
          break
      }
    }
  }
}
</script>
