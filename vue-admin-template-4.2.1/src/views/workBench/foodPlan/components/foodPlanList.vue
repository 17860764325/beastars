<template>
  <div class="app-container">
    <div class="query">
      <span style="margin-top: 20px">食物名称：</span>
      <el-input
          style="width: 200px"
          v-model="listQuery.foodName"
          placeholder="食物名称"
      >
      </el-input>
      <span style="margin-top: 20px">食物类型：</span>
      <el-select
          style="width: 200px"
          v-model="listQuery.foodType"
          placeholder="食物名称"
      >
        <el-option
            v-for="item in options"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue">
        </el-option>
      </el-select>
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
import { page, deletes } from '@/api/food/api.js'
import { getUserInfo } from '@/api/user/api.js'
import { getDicts } from '@/api/dict/api'

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
            prop: 'foodName',
            showOverflowTooltip: true,
            label: '食物名称',
            minWidth: 150
          },
          {
            prop: 'foodCode',
            showOverflowTooltip: true,
            label: '食物编码',
            minWidth: 150
          },
          {
            prop: 'foodType',
            showOverflowTooltip: true,
            label: '食物类型',
            dictType: 'feed_type',
            minWidth: 150
          },
          {
            prop: 'number',
            showOverflowTooltip: true,
            label: '单位数量(g)',
            minWidth: 150
          },{
            prop: 'quantityOfHeat',
            showOverflowTooltip: true,
            label: '热量',
            minWidth: 100
          },
          {
            prop: 'protein',
            showOverflowTooltip: true,
            label: '蛋白质',
            minWidth: 150
          },
          {
            prop: 'fat',
            showOverflowTooltip: true,
            label: '脂肪',
            minWidth: 100
          },
          {
            prop: 'carbonWater',
            showOverflowTooltip: true,
            label: '碳水',
            minWidth: 100
          },
          {
            prop: 'dietaryFiber',
            showOverflowTooltip: true,
            label: '膳食纤维',
            minWidth: 100
          },
          {
            prop: 'starch',
            showOverflowTooltip: true,
            label: '维生素',
            minWidth: 100
          }
        ],
        handle: {
          minWidth: 200,
          fixed: 'right',
          btList: [
            {
              type: 'text',
              label: '修改',
              event: 'edit',
              show: true
            }
          ]
        }
      },
      options: [], // 食物类型
      tableData: [],
      listQuery: {
        page: 1,
        limit: 20,
        foodName: undefined,
        foodType: undefined,
      },
      test: {
        a: '1',
        b: '2'
      }
    }
  },
  async created() {
    await getUserInfo().then((res) => {
      this.listQuery.userName = res.data.username
    })
    await this.getList()
    // const res = await getUserId()
    // this.user = res.data
    // 根据字典值获取list
    await getDicts('feed_type').then((res) => {
      this.options = res.data
    })
  },
  methods: {
    getList() {
      this.tableInfo.refresh = Math.random()
    },
    async reset() {
      this.listQuery.foodName = undefined
      this.listQuery.foodType = undefined
      this.getList()
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
      //  console.log(data)
      this.$router.push({ name: 'dateDetail', params: { id: data.id } })
    },
    // 添加数据
    add() {
      //  console.log('add')
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
