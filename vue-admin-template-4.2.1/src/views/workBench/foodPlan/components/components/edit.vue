<template>
  <div class="app-container">
    <el-row class="row">
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
      <el-button style="margin-top: 20px" type="danger" @click="centrl()"
        >cancel</el-button
      >
      <el-button style="margin-top: 20px" type="success" @click="submit()">ok</el-button>
    </div>
  </div>
</template>

<script>
import { editObj, page } from '@/api/food/api'
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
          prop: 'foodName',
          label: '食物名称：',
          type: 'text',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'foodCode',
          label: '食物编码：',
          type: 'text',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'foodType',
          label: '食物类型',
          type: 'select',
          dict: 'feed_type',
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          prop: 'number',
          label: '单位数量(单位:g)：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'quantityOfHeat',
          label: '单位数量下的热量：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'protein',
          label: '单位数量下的蛋白质：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'fat',
          label: '单位数量下的脂肪：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'carbonWater',
          label: '单位数量下的碳水：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'dietaryFiber',
          label: '单位数量下的膳食纤维：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        },
        {
          prop: 'starch',
          label: '单位数量下的维生素：',
          type: 'number',
          rules: { required: true },
          sm: 12,
          md: 12,
          lg: 12,
          xl: 12
        }
      ]
    }
  },
  async created() {
    const query = {
      id: this.id
    }
    const res = await page(query)
    this.form = res.data.rows[0]
  },
  methods: {
    async submit() {
      //  console.log('submit')
      if (this.$refs.hrTable.validate()) {
        //  console.log(this.form)
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
