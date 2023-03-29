<template>
  <div class="app-container">
    <hr-form
      ref="hrTableeditUserInfo"
      :form-disabled="false"
      :form.sync="form"
      :field-list="fieldList"
    />
    <div style="text-align: center">
      <el-button @click="submit()">确定</el-button>
      <el-button @click="centrl()">取消</el-button>
    </div>
  </div>
</template>

<script>
import { changeUserInfo } from '@/api/user/api.js'
export default {
  name: 'Documentation',
  props: {
    item: {
      type: Object
    }
  },
  data() {
    return {
      form: {},
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
    console.log(this.item)
    this.form = this.item
  },
  methods: {
    async submit() {
      console.log('submit')
      if (this.$refs.hrTableeditUserInfo.validate()) {
        console.log(this.form)
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
        })
      } else {
        this.$notify({
          title: '警告',
          message: '请填写完整',
          type: 'warning'
        })
      }
    },
    centrl() {
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss" scoped>
.query {
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>

