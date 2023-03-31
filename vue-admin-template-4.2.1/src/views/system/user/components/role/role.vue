<template>
  <div class="app-container">
    <div class="query">
      <span style="margin-top: 20px">权限名称：</span>
      <el-input
        style="width: 200px"
        v-model="listQuery.roleName"
        placeholder="权限名称"
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
    </div>
    <el-switch v-model="value" active-color="#13ce66" inactive-color="#ff4949">
    </el-switch>
    <div
      v-show="value"
      style="width: 200px; height: 20px; background-color: bisque"
    >
      你在哪瞎几把点什么？
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
      :title="'新增权限！'"
      :visible.sync="addVisible"
    >
      <role-add @close="addDilogClose" />
    </el-dialog>
    <el-dialog
      v-if="editVisible"
      class="addDialog"
      width="40%"
      :title="'修改权限！'"
      :visible.sync="editVisible"
    >
      <role-edit :id="id" @close="editDilogClose" />
    </el-dialog>
    <el-dialog
      v-if="rolePageVisable"
      class="addDialog"
      width="75%"
      :title="title + '页面权限对应！'"
      :visible.sync="rolePageVisable"
    >
      <role-page :id="id" @close="rolePageVisable = false" />
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
import { page, deletes } from '@/api/role/api.js'
// import { getUserInfo } from '@/api/user/api.js'

export default {
  name: 'Documentation',
  components: {
    // // 新增
    'role-add': () => import('./components/roleAdd'),
    // // 编辑
    'role-edit': () => import('./components/roleEdit'),
    // 分配页面权限
    'role-page': () => import('./components/rolePage.vue')
  },
  data() {
    return {
      value: false,
      title: undefined,
      page,
      user: {},
      boos: false,
      addVisible: false,
      editVisible: false,
      deleteVisible: false,
      rolePageVisable: false,
      ids: '',
      id: undefined,
      selectedRows: [],
      tableInfo: {
        refresh: 1,
        data: [],
        fieldList: [
          // 交易形式
          {
            prop: 'roleName',
            showOverflowTooltip: true,
            label: '权限名称',
            minWidth: 100
          },
          {
            prop: 'roleAbout',
            showOverflowTooltip: true,
            label: '权限关于',
            minWidth: 150
          },
          {
            prop: 'isVoid',
            showOverflowTooltip: true,
            dictType: 'yes_no',
            label: '权限是否生效？',
            minWidth: 150
          }
        ],
        handle: {
          minWidth: 200,
          fixed: 'right',
          btList: [
            {
              type: 'text',
              label: '分配界面',
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
        roleName: undefined
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
      this.listQuery.roleName = undefined

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
      this.id = data.id
      this.title = data.roleName
      this.rolePageVisable = true
      //   this.$router.push({ name: 'dateDetail', params: { id: data.id } })
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
