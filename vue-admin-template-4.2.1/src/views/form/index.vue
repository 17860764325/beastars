<template>
  <div class="app-container ">
    <div class="query">
      <el-input
        v-model="listQuery.date"
        style="width: 200px"
        class="filter-item"
        type="date"
      />
      <el-button type="primary" icon="el-icon-search" class="filter-item" @click="getList()">Query</el-button>
      <el-button type="primary" icon="el-icon-refresh" class="filter-item" @click="reset">Reset</el-button>
      <el-button type="primary" icon="el-icon-plus" class="filter-item" @click="add">Add</el-button>
    </div>
    <hr-table
      ref="table"
      :refresh="tableInfo.refresh"
      :data.sync="tableInfo.data"
      :field-list="tableInfo.fieldList"
      :api="page"
      :query="listQuery"
      :handle="tableInfo.handle"
      :btn-hide-set="btnHideSet"
      :table-index="true"
      :export-file-name="exportFileName"
      @handle-click="handleClick"
    />
  </div>
</template>

<script>
import { page } from '@/api/ScheduleHeader/api.js'
import { getAllDicts } from '@/api/dict/api.js'
export default {
  name: 'Documentation',
  data() {
    return {
      page,
      tableInfo: {
        refresh: 1,
        data: [],
        fieldList: [

          // 交易形式
          {
            prop: 'date',
            showOverflowTooltip: true,
            label: '日期',
            type: 'date',
            minWidth: 100
          },
          {
            prop: 'isOk',
            showOverflowTooltip: true,
            label: '是否完成',
            dictType: 'is_ok',
            minWidth: 100
          },
          {
            prop: 'isOther',
            showOverflowTooltip: true,
            label: '是否有其他事情',
            dictType: 'is_ok',
            minWidth: 100
          },
          {
            prop: 'otherIsOk',
            showOverflowTooltip: true,
            label: '其他事情是否完成',
            dictType: 'is_ok',
            minWidth: 100
          }
        ],
        handle: {
          minWidth: 200,
          fixed: 'right',
          btList: [
            {
              type: 'text',
              label: '详细编辑',
              event: 'edit',
              show: true
            }
          ]
        }
      },
      tableData: [],
      listQuery: {
        page: 1,
        limit: 20,
        date: undefined
      },
      test: {
        a: '1',
        b: '2'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      const res = await page(this.listQuery)
      this.tableInfo.data = res.data.rows
    },
    async  reset() {
      this.listQuery.date = undefined
      const res = await page({
        page: 1,
        limit: 20
      })
      this.tableInfo.data = res.data.rows
    },
    handleClick(event, data) {
      switch (event) {
        case 'edit':
          this.detail(data)
          break
        case 'add':
          this.handleAdd(data)
          break
        default:
          break
      }
    },
    detail(data) {
      console.log(data)
      this.$router.push({ name: 'dateDetail', params: { dateId: data.date }})
    },
    // 添加数据
    async add() {
      // this.$router.push({ name: 'dateAdd' })
      this.getDicts('is_ok')
      console.log(this.test['a'])
      console.log(await getAllDicts())
    }
  }
}
</script>
<style lang="scss" scoped>
.query{
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
