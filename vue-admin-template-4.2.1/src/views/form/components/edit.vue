<template>
  <div class="app-container">
    <el-row class="row">
      <el-col>
        <h1>Do you want to do this?</h1>
        <div class="imgDiv">
          <img class="img" src="../../../assets/images/909641.png" alt="logo" />
        </div>
      </el-col>
      <el-col>
        <hr-form
          ref="hrTable"
          :form-disabled="false"
          :form.sync="form"
          :field-list="fieldList"
        />
      </el-col>
    </el-row>

    <div style="text-align: center">
      <el-button type="danger" @click="centrl()"
        >NO I DONT WANT DO THIS!</el-button
      >
      <el-button type="success" @click="submit()">YES I WANT!</el-button>
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
          label: '事件名称：',
          prop: 'name',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          type: 'datetime',
          label: '什么时候完成：',
          prop: 'date',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'remarks',
          label: '备注',
          type: 'textarea',
          rules: { required: false },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
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
  width: 100%;
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
}
.imgDiv {
  height: 200px;
  border: 5px grey solid;
  overflow-y: auto;
}
.row {
  display: flex;
  flex-direction: column;
}
</style>
