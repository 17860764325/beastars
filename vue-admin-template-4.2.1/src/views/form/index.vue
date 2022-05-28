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
      <el-button type="primary" icon="el-icon-refresh" class="filter-item" @click="reset()">Reset</el-button>
      <el-button type="primary" icon="el-icon-plus" class="filter-item" @click="add()">Add</el-button>
      <el-button type="danger" icon="el-icon-delete" class="filter-item" @click="deletes()">Delete</el-button>
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
      :selection="true"
      @selection-change="selectionChange"
      @handle-click="handleClick"
    />
    <el-dialog
      v-if="addVisible"
      class="addDialog"
      width="80%"
      :title="'新增故事！'"
      :visible.sync="addVisible"
    >
      <story-add
        @close="addDilogClose"
      />
    </el-dialog>
    <el-dialog
      v-if="editVisible"
      class="addDialog"
      width="80%"
      :title="'修改故事！'"
      :visible.sync="editVisible"
    >
      <story-edit
        :id="id"
        @close="editDilogClose"
      />
    </el-dialog>
  </div>
</template>

<script>
import { page, deletes } from '@/api/ScheduleHeader/api.js'
export default {
  name: 'Documentation',
  components: {
    // 新增
    'story-add': () => import('./components/add'),
    // 编辑
    'story-edit': () => import('./components/edit')
  },
  data() {
    return {
      page,
      addVisible: false,
      editVisible: false,
      ids: '',
      id: undefined,
      selectedRows: [],
      tableInfo: {
        refresh: 1,
        data: [],
        fieldList: [

          // 交易形式
          {
            prop: 'name',
            showOverflowTooltip: true,
            label: '👀想做事情名称',
            minWidth: 100
          },
          {
            prop: 'date',
            showOverflowTooltip: true,
            label: '🕐准备什么时候做？',
            type: 'date',
            minWidth: 100
          },
          {
            prop: 'isOk',
            showOverflowTooltip: true,
            label: '☑️是否完成了？',
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
              label: '📝书写策划',
              event: 'detail',
              show: true
            },
            {
              type: 'text',
              label: '修改',
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
        case 'detail':
          this.detail(data)
          break
        case 'edit':
          this.edit(data)
          break
        default:
          break
      }
    },
    detail(data) {
      console.log(data)
      this.$router.push({ name: 'dateDetail', params: { id: data.id }})
    },
    // 添加数据
    add() {
      console.log('add')
      this.addVisible = true
    },
    addDilogClose() {
      this.addVisible = false
      this.getList()
    },
    // 修改数据
    edit(data) {
      this.id = data.id
      this.editVisible = true
    },
    editDilogClose() {
      this.editVisible = false
      this.getList()
    },
    // 选中数据
    selectionChange(rows) {
      this.selectedRows = rows
    },
    // 删除数据
    deletes() {
      if (this.selectedRows.length > 0) {
        this.selectedRows.forEach(element => {
          if (this.selectedRows[this.selectedRows.length - 1] === element) {
            this.ids = this.ids + element.id
          } else {
            this.ids = this.ids + element.id + ','
          }
        })
        deletes(this.ids).then(res => {
          if (res.status === 200) {
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success'
            })
          } else {
            this.$notify({
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
          this.getList()
          this.ids = ''
        })
      } else {
        this.$notify({
          title: '警告',
          message: '至少选一条数据sb',
          type: 'warning'
        })
      }
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
<style scoped>

</style>
