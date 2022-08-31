<template>
  <div class="app-container">
    <el-input
      v-model="listQuery.userName"
      style="width: 200px"
      class="filter-item"
      type="text"
    />
    <el-button
      type="primary"
      icon="el-icon-search"
      class="filter-item"
      @click="getList()"
    >Query
    </el-button>
    <el-button
      type="primary"
      icon="el-icon-refresh"
      class="filter-item"
      @click="reset()"
    >Reset
    </el-button>
    <hr-table
      ref="table"
      :refresh="tableInfo.refresh"
      :data.sync="tableInfo.data"
      :field-list="tableInfo.fieldList"
      :api="page"
      :query="listQuery"
      :handle="tableInfo.handle"
      :table-index="true"
      :selection="false"
    />
  </div>
</template>

<script>
import { page } from '@/api/system/loginIpLog.js'

export default {
  name: 'Documentation',
  components: {},
  data() {
    return {
      page,
      user: {},
      boos: false,
      addVisible: false,
      editVisible: false,
      deleteVisible: false,
      ids: '',
      id: undefined,
      selectedRows: [],
      tableInfo: {
        refresh: 1,
        data: [],
        fieldList: [
          // 交易形式
          {
            prop: 'userName',
            showOverflowTooltip: true,
            label: '登陆用户',
            minWidth: 100
          },
          {
            prop: 'userIp',
            showOverflowTooltip: true,
            label: '登陆用户的ip？',
            minWidth: 100
          }, {
            prop: 'address',
            showOverflowTooltip: true,
            label: '地址？',
            minWidth: 100
          },
          {
            prop: 'createTime',
            showOverflowTooltip: true,
            label: '登陆时间',
            type: 'date',
            minWidth: 100
          }
        ]
      },
      tableData: [],
      listQuery: {
        page: 1,
        limit: 20,
        userName: undefined
      },
      test: {
        a: '1',
        b: '2'
      }
    }
  },
  async created() {
    this.getList()
  },
  methods: {
    async getList() {
      const res = await page(this.listQuery)
      this.tableInfo.data = res.data.rows
    },
    async reset() {
      this.listQuery.date = undefined
      const res = await page({
        page: 1,
        limit: 20
      })
      this.tableInfo.data = res.data.rows
    }

  }
}
</script>

<style scoped>

</style>
