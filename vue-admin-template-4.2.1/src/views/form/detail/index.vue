<template>
  <div class="app-container documentation-container">

    <el-form>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="日期">
          <el-input v-model="formInline.user" type="date" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </el-form>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        fixed
        prop="date"
        label="日期"
        width="auto"
      />
      <el-table-column
        prop="name"
        label="事件描述"
        width="120"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>住址: {{ scope.row.address }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="province"
        label="时间区间"
        width="120"
      />
      <el-table-column
        prop="city"
        label="是否完成"
        width="120"
      />
      <el-table-column
        prop="address"
        label="是否临时有事"
        width="300"
      />
      <el-table-column
        prop="zip"
        label="临时事情描述"
        width="120"
      />
      <el-table-column
        prop="zip"
        label="临时事情是否完成"
        width="120"
      />
      <el-table-column
        prop="zip"
        label="临时事情时间区间"
        width="120"
      />
      <el-table-column
        prop="zip"
        label="备注"
        width="120"
      />
      <el-table-column
        fixed="right"
        label="操作"
        width="100"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleClick(scope.row)">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>

export default {
  name: 'Documentation',
  props: {
    dateId: String
  },
  data() {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-04',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1517 弄',
        zip: 200333
      }, {
        date: '2016-05-01',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1519 弄',
        zip: 200333
      }, {
        date: '2016-05-03',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1516 弄',
        zip: 200333
      }],
      formInline: {
        user: '',
        region: ''
      }
    }
  },
  created() {
    console.log(this.dateId)
  },
  methods: {
    handleClick(row) {
      console.log(row)
    },
    onSubmit() {
      console.log('submit!')
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
