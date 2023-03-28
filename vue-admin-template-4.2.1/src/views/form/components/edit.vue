<template>
  <div class="app-container">
    <el-row class="row">
      <el-col>
        <h1>Do what you want to do!</h1>
      </el-col>
      <el-col>
        <hr-form
          ref="hrTable"
          :form-disabled="false"
          :form.sync="form"
          :field-list="fieldList"
        />
      </el-col>
      <el-col>
        <img class="img" src="../../../assets/images/909641.png" alt="logo" />
      </el-col>
    </el-row>

    <div style="text-align: center">
      <el-button @click="submit()">DO IT!</el-button>
      <el-button @click="centrl()">NO I DONT WANT DO THIS!</el-button>
    </div>
  </div>
</template>

<script>
import { editObj, getDictInfoById } from '@/api/ScheduleHeader/api'
export default {
  name: 'Documentation',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    id: Number
  },
  data() {
    return {
      form: {},
      fieldList: [
        {
          type: 'text',
          label: '想做的事情？是什么？',
          prop: 'name',
          rules: { required: true }
        },
        {
          type: 'date',
          label: '什么时候做？',
          prop: 'date',
          rules: { required: true }
        }
      ]
    }
  },
  async created() {
    const res = await getDictInfoById(this.id)
    this.form = res.data
  },
  methods: {
    async submit() {
      console.log('submit')
      if (this.$refs.hrTable.validate()) {
        console.log(this.form)
        await editObj(this.form).then((res) => {
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
.img {
  width: 50%;
}
.row {
  display: flex;
  flex-direction: column;
}
</style>
