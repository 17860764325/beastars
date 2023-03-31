<template>
  <div class="app-container">
    <hr-form
      ref="roleaddForm"
      :form-disabled="false"
      :form.sync="form"
      :field-list="fieldList"
    >
    </hr-form>
    <div style="text-align: center">
      <el-button @click="submit()">确定</el-button>
      <el-button @click="centrl()">取消</el-button>
    </div>
  </div>
</template>

<script>
import { addObj } from '@/api/role/api'
export default {
  name: 'Documentation',
  data() {
    return {
      form: {},
      fieldList: [
        {
          type: 'text',
          label: '权限名称',
          prop: 'roleName',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'text',
          label: '权限描述',
          prop: 'roleAbout',
          rules: { required: false },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        // {
        //   type: 'slot',
        //   label: '是否启用',
        //   prop: 'isVoid',
        //   rules: { required: false },
        //   sm: 12,
        //   md: 12,
        //   lg: 12,
        //   xl: 12
        // }
        {
          type: 'select',
          label: '是否启用',
          prop: 'isVoid',
          dict: 'yes_no',
          tooltip:
            'type: select，选择框类型，绑定事件，下拉框的数据直接获取数据字典', // 帮助文本，用小问号显示，可以方html或者字符串
          event: 'selectTypeEvent',
          rules: { required: true },
          // filterable: true, // 是否可以过滤条件
          clearable: true, // 文本框内容是否可以清空，对应回调函数handle-clear(event, prop)，非必选
          // disabled: true, // 是否禁用，非必选
          // class: 'text-type-class', // 在<style></style>中定义class，加到输入框el-input上，非必选
          // style: 'width: 60%' // 设置输入框el-input的样式，非必选
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        }
      ]
    }
  },
  methods: {
    async submit() {
      console.log('submit')
      if (this.$refs.roleaddForm.validate()) {
        console.log(this.form)
        await addObj(this.form).then((res) => {
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

