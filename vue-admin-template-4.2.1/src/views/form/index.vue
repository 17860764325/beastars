<template>
  <div class="app-container">
    <div class="query">
      <span>日期：</span>
      <el-date-picker
        v-model="listQuery.startDate"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="开始日期"
      >
      </el-date-picker>
      <span>✈️✈️</span>
      <el-date-picker
        v-model="listQuery.endDate"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="结束日期"
      >
      </el-date-picker>
      <span style="margin-top: 20px">事件名称：</span>
      <el-input
        style="width: 200px"
        v-model="listQuery.name"
        placeholder="事件名称"
      >
      </el-input>
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
      <el-button
        type="primary"
        icon="el-icon-plus"
        class="filter-item"
        @click="add()"
        >Add
      </el-button>
      <el-button
        type="danger"
        icon="el-icon-delete"
        class="filter-item"
        @click="deletes()"
        >Delete
      </el-button>
      <el-button
        type="success"
        icon="el-icon-check"
        class="filter-item"
        @click="ok()"
        >OK
      </el-button>
      <el-button
        type="danger"
        icon="el-icon-close"
        class="filter-item"
        @click="no()"
        >NO
      </el-button>
    </div>
    <hr-table
      ref="table"
      :refresh="tableInfo.refresh"
      :data.sync="tableInfo.data"
      :field-list="tableInfo.fieldList"
      :api="page"
      :query="listQuery"
      :handle="tableInfo.handle"
      :table-index="true"
      :selection="true"
      @selection-change="selectionChange"
      @handle-click="handleClick"
    />
    <el-dialog
      v-if="addVisible"
      class="addDialog"
      width="40%"
      :title="'新增故事！'"
      :visible.sync="addVisible"
    >
      <story-add @close="addDilogClose" />
    </el-dialog>
    <el-dialog
      v-if="editVisible"
      class="addDialog"
      width="40%"
      :title="'修改故事！'"
      :visible.sync="editVisible"
    >
      <story-edit :id="id" @close="editDilogClose" />
    </el-dialog>
    <el-dialog
      v-if="deleteVisible"
      class="isDelete"
      width="30%"
      :title="'删除'"
      :visible.sync="deleteVisible"
    >
      <h3>是否要删除这条数据？</h3>
      <div style="text-align: center">
        <el-button @click="yesDelete()">YES!</el-button>
        <el-button @click="noDelete()">NO!</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { page, deletes, statusOk, statusNo } from '@/api/ScheduleHeader/api.js'
// import { getUserId } from '@/api/user/api.js'

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
            label: '创建人',
            minWidth: 100
          },
          {
            prop: 'name',
            showOverflowTooltip: true,
            label: '👀想做事情名称',
            minWidth: 150
          },
          {
            prop: 'createTime',
            showOverflowTooltip: true,
            label: '🕐创建时间',
            type: 'date',
            minWidth: 200
          },
          {
            prop: 'date',
            showOverflowTooltip: true,
            label: '🕐准备什么时候完成？',
            type: 'date',
            minWidth: 200
          },
          {
            prop: 'remarks',
            showOverflowTooltip: true,
            label: '备注',
            minWidth: 200
          },
          {
            prop: 'isOk',
            showOverflowTooltip: true,
            label: '☑️是否完成了？',
            dictType: 'is_ok',
            minWidth: 150
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
        startDate: undefined,
        endDate: undefined,
        name: undefined
      },
      test: {
        a: '1',
        b: '2'
      }
    }
  },
  async created() {
    await this.getList()
    // const res = await getUserId()
    // this.user = res.data
  },
  methods: {
    async getList() {
      console.log(123)
      const res = await page(this.listQuery)
      this.tableInfo.data = res.data.rows
    },
    async reset() {
      this.listQuery.startDate = undefined
      this.listQuery.endDate = undefined
      this.listQuery.name = undefined
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
      this.$router.push({ name: 'dateDetail', params: { id: data.id } })
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
      this.deleteVisible = true
    },
    async ok() {
      if (this.selectedRows.length > 0) {
        this.selectedRows.forEach((element) => {
          if (this.selectedRows[this.selectedRows.length - 1] === element) {
            this.ids = this.ids + element.id
          } else {
            this.ids = this.ids + element.id + ','
          }
        })
        await statusOk(this.ids).then((res) => {
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
    },
    async no() {
      if (this.selectedRows.length > 0) {
        this.selectedRows.forEach((element) => {
          if (this.selectedRows[this.selectedRows.length - 1] === element) {
            this.ids = this.ids + element.id
          } else {
            this.ids = this.ids + element.id + ','
          }
        })
        await statusNo(this.ids).then((res) => {
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
    },
    yesDelete() {
      if (this.selectedRows.length > 0) {
        this.selectedRows.forEach((element) => {
          if (this.selectedRows[this.selectedRows.length - 1] === element) {
            this.ids = this.ids + element.id
          } else {
            this.ids = this.ids + element.id + ','
          }
        })
        deletes(this.ids).then((res) => {
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
          this.deleteVisible = false
        })
      } else {
        this.$notify({
          title: '警告',
          message: '至少选一条数据sb',
          type: 'warning'
        })
      }
    },
    noDelete() {
      this.deleteVisible = false
      this.getList()
    }
  }
}
</script>
<style lang="scss" scoped>
.query {
  margin-top: 10px;
  margin-bottom: 20px;
}
.filter-item {
  margin: 5px;
}
</style>
