<template>
  <div class="app-container">
    <!--  注册页面，填写基本信息，设置用户名称和密码 不选择背景头像和头像，背景拖片和头像在自己的个人中心自己更改，初始化是默认的-->
    <hr-form
      ref="registerForm"
      :form-disabled="false"
      :form.sync="form"
      :field-list="fieldList"
    />
    <h1>开通账号后记得让管理员添加权限哦，管理员邮箱：12587747092@qq.com</h1>
    <el-row style="display: flex; justify-content: space-between">
      <el-button @click="register()">注册</el-button>
      <el-button style="float: right" @click="login()"
        >已有账号？现在登陆</el-button
      >
    </el-row>
  </div>
</template>
<script>
import { regist } from '@/api/user'

export default {
  name: 'Register',
  data() {
    return {
      form: {},
      fieldList: [
        {
          type: 'text',
          label: '昵称',
          prop: 'name',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'text',
          label: '账号？',
          prop: 'username',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'text',
          label: '手机号：',
          prop: 'phone',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'text',
          label: '邮箱：',
          prop: 'email',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'password',
          label: '密码：',
          prop: 'password',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'password',
          label: '确认密码：',
          prop: 'rePassword',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'textarea',
          label: '个人介绍：',
          prop: 'about',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        }
      ]
    }
  },
  methods: {
    async register() {
      // 先进性，是否为空的校验
      if (this.$refs.registerForm.validate()) {
        // 再次校验，两次密码是否正确？
        if (this.form.password === this.form.rePassword) {
          //  console.log(this.form)
          // 两次密码相同
          // 向后端发送请求，存储信息
          const res = await regist(this.form)
          if (res.status === 200) {
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success'
            })
          } else {
            this.$notify({
              title: '失败  ',
              message: res.message,
              type: 'error'
            })
          }
          this.$emit('close')
        } else {
          // 密码不相同
          // 提示密码不相同，输入框变成红色
          this.$notify({
            title: '失败  ',
            message: '两次密码输入不相同',
            type: 'error'
          })
        }
      }
    },
    login() {
      //  console.log('asdfasdfa')
      // 登陆只需要将弹窗关闭即可
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
</style>
