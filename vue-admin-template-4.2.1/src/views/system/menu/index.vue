<template>
  <div class="app-container">
    <el-row class="page">
      <el-col class="left">
        <el-card class="left-card">
          <el-button type="primary" @click="append(null)">新建目录</el-button>
          <el-button type="primary" @click="getRouterList()"
          >获取路由list
          </el-button
          >
          <div class="block">
            <p>所有页面</p>
            <el-tree
                :data="treeData"
                show-checkbox
                node-key="id"
                default-expand-all
                :expand-on-click-node="false"
                @node-click="handleCheckChange"
            >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span>
                  <el-button
                      :disabled="data.parentCode !== '0'"
                      class="create"
                      type="text"
                      size="mini"
                      @click="() => append(data)"
                  >
                    新增页面
                  </el-button>
                  <el-button
                      class="delete"
                      type="text"
                      size="mini"
                      @click="() => remove(node, data)"
                  >
                    删除页面
                  </el-button>
                </span>
              </span>
            </el-tree>
          </div>
        </el-card>
      </el-col>
      <el-col class="right">
        <el-card class="right-card">
          <el-button type="primary" @click="saveMethod" :disabled="save"
          >保存！
          </el-button
          >
          <el-button type="primary" @click="updateMethod" :disabled="update"
          >更新！
          </el-button
          >
          <hr-form
              ref="pageForm"
              :form-disabled="false"
              :form.sync="form"
              :field-list="fieldList"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getPageList,
  getPageInfoByCode,
  getMaxPageCode,
  getRouterListFromMysql,
  addObj,
  editObj
} from '@/api/system/page'

let id = 1000

export default {
  data() {
    return {
      save: false,
      update: true,
      treeData: [],
      form: [],
      fieldList: [
        {
          type: 'text',
          label: '页面编码',
          prop: 'pageCode',
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: '页面名称',
          prop: 'pageName',
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: 'icon',
          prop: 'icon',
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: 'name',
          prop: 'name',
          rules: { required: false },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: 'path',
          prop: 'path',
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: '父节点',
          prop: 'pageParentCode',
          disabled: true,
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: '路由地址',
          prop: 'pagePath',
          rules: { required: false },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'text',
          label: '权限标识',
          prop: 'pagePerms',
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        },
        {
          type: 'radio',
          label: '页面类型',
          prop: 'pageType',
          options: [
            { label: '页面', value: 'C' },
            { label: '菜单', value: 'M' }
          ],
          rules: { required: true },
          sm: 24,
          md: 24,
          lg: 24,
          xl: 24
        }
      ]
    }
  },
  created() {
    this.getLsit()
  },
  methods: {
    async append(data) {
      let num = 0
      this.treeData.forEach((element) => {
        if (element.label === '子页面') {
          num++
        }
        element.children.forEach((item) => {
          if (item.label === '子页面') {
            num++
          }
        })
      })
      if (num === 0) {
        const res = await getMaxPageCode()
        id = res.data + 1
        //  console.log(id)
        if (data === null) {
          const newChild = {
            id: id++,
            label: '子页面',
            parentCode: '0',
            children: [],
            flag: 'new'
          }
          this.treeData.push(newChild)
        } else {
          const newChild = {
            id: id++,
            label: '子页面',
            parentCode: data.id,
            children: [],
            flag: 'new'
          }
          // 如果该节点没有children则加上
          if (!data.children) {
            this.$set(data, 'children', [])
          }
          data.children.push(newChild)
        }
      } else {
        this.$notify({
          title: '失败',
          message: '已经存在一个子页面，不能再进行添加！',
          type: 'error'
        })
      }
    },
    remove(node, data) {
      const parent = node.parent
      const children = parent.data.children || parent.data
      const index = children.findIndex((d) => d.id === data.id)
      children.splice(index, 1)
    },
    // 每次点击树节点，就会触发的
    async handleCheckChange(data) {
      // 每次点击刷新右边的信息
      // 判断如果为新增加节点那就不进行后端查询
      //  console.log(data)
      // 每次点击就会显示这个页面的信息
      // 要进行判断，如果是新生成的节点那么就显示空的框
      // 2023-07-26，查询后端数据库，如果咩有数据则为新加的页面，如果有数据就是修改页面
      if (data.flag === 'new') {
        // 新页面
        this.save = false
        this.update = true
        data.pageParentCode = data.parentCode
        data.pageCode = data.id
        data.pageName = data.label
        this.form = data
      } else {
        this.save = true
        this.update = false
        const res = await getPageInfoByCode(data.id)
        if (res.data.pageType === 'M') {
          res.data.pageType = '菜单'
        } else {
          res.data.pageType = '页面'
        }
        this.form = res.data
      }
    },
    async getLsit() {
      const res = await getPageList()
      this.treeData = res.data
    },
    async getRouterList() {
      const res = await getRouterListFromMysql()

      console.log(res.data)
    },
    async saveMethod() {
      //  console.log(this.form)
      // 首先校验一下必填写的项目有没填写
      if (this.$refs.pageForm.validate()) {
        if (this.form.pageType === '菜单') {
          this.form.pageType = 'M'
        } else {
          this.form.pageType = 'C'
        }
        await addObj(this.form).then((res) => {
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
    async updateMethod() {
      //  console.log(this.form)
      if (this.$refs.pageForm.validate()) {
        if (this.form.pageType === '菜单') {
          this.form.pageType = 'M'
        } else {
          this.form.pageType = 'C'
        }
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
    }
  }
}
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.page {
  display: flex;
}

.left {
  height: 700px;
  padding: 10px;
}

.right {
  padding: 10px;
}

.left-card {
  height: 700px;
  overflow-y: auto;
}

.right-card {
  height: 700px;
  overflow-y: auto;
}

.create {
  color: green;
}

.delete {
  color: red;
}
</style>
