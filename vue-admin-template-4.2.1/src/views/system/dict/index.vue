<template>
  <div class="app-container ">
    <div class="query">
      数据字典值内容:
      <el-input
        v-model="listQuery.dictLabel"
        style="width: 200px"
        class="filter-item"
        type="text"
      />
      数据字典类型:
      <el-input
        v-model="listQuery.dictType"
        style="width: 200px"
        class="filter-item"
        type="text"
      />
      <el-button type="primary" icon="el-icon-search" class="filter-item" @click="getList()">Query</el-button>
      <el-button type="primary" icon="el-icon-refresh" class="filter-item" @click="reset()">Reset</el-button>
      <el-button type="primary" icon="el-icon-plus" class="filter-item" @click="addDilogOpen()">Add</el-button>
      <el-button type="danger" icon="el-icon-delete" class="filter-item" @click="deletes()">Delete</el-button>
    </div>
    <hr-table
      ref="table"
      :refresh="tableInfo.refresh"
      :data.sync="tableInfo.data"
      :field-list="tableInfo.fieldList"
      :api="page"
      :selection="true"
      :query="listQuery"
      :handle="tableInfo.handle"
      :table-index="true"
      @selection-change="selectionChange"
      @handle-click="handleClick"
    />
    <el-dialog
      v-if="addVisible"
      width="80%"
      append-to-body
      :title="'新增数据字典'"
      :visible.sync="addVisible"
    >
      <dict-add
        @close="addDilogClose"
      />
    </el-dialog>
    <el-dialog
      v-if="editVisible"
      width="80%"
      append-to-body
      :title="'修改数据字典'"
      :visible.sync="editVisible"
    >
      <dict-edit
        :id="id"
        @close="editClose"
      />
    </el-dialog>
    <el-dialog
      v-if="deleteVisible"
      class="isDelete"
      width="20%"
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
import { page, deletes } from '@/api/dict/api.js'
export default {
  name: 'Documentation',
  components: {
    // 新增
    'dict-add': () => import('./components/add'),
    // 编辑
    'dict-edit': () => import('./components/edit')
  },
  data() {
    return {
      page,
      id: undefined,
      addVisible: false,
      editVisible: false,
      deleteVisible: false,
      selectedRow: [],
      ids: '',
      tableInfo: {
        refresh: 1,
        data: [],
        fieldList: [
          {
            prop: 'dictType',
            showOverflowTooltip: true,
            label: '字典类型',
            minWidth: 100
          },
          {
            prop: 'dictLabel',
            showOverflowTooltip: true,
            label: '字典名称',
            minWidth: 100
          },
          {
            prop: 'dictValue',
            showOverflowTooltip: true,
            label: '字典值',
            minWidth: 100
          },
          {
            prop: 'dictSort',
            showOverflowTooltip: true,
            label: '字典排序',
            minWidth: 100
          },
          {
            prop: 'isDefault',
            showOverflowTooltip: true,
            label: '是否默认',
            dictType: 'yes_no',
            minWidth: 100
          },
          {
            prop: 'status',
            showOverflowTooltip: true,
            label: '状态',
            dictType: 'status',
            minWidth: 100
          }

        ],
        handle: {
          minWidth: 200,
          fixed: 'right',
          btList: [
            {
              type: 'text',
              label: '编辑',
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
        dictType: undefined,
        dictLabel: undefined
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
      this.listQuery.dictType = undefined
      this.listQuery.dictLabel = undefined
      const res = await page({
        page: 1,
        limit: 20
      })
      this.tableInfo.data = res.data.rows
    },
    handleClick(event, data) {
      switch (event) {
        case 'edit':
          this.editOpen(data)
          break
        default:
          break
      }
    },
    selectionChange(rows) {
      this.selectedRow = rows
      console.log(rows)
    },
    editOpen(data) {
      this.id = data.dictCode
      this.editVisible = true
    },
    editClose() {
      this.editVisible = false
      this.getList()
    },
    // 添加数据
    addDilogOpen() {
      this.addVisible = true
    },
    addDilogClose() {
      this.addVisible = false
      this.getList()
    },
    // delete
    // 删除数据
    deletes() {
      this.deleteVisible = true
    },
    yesDelete() {
      if (this.selectedRow.length > 0) {
        this.selectedRow.forEach(element => {
          if (this.selectedRow[this.selectedRow.length - 1] === element) {
            this.ids = this.ids + element.dictCode
          } else {
            this.ids = this.ids + element.dictCode + ','
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
.query{
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>
