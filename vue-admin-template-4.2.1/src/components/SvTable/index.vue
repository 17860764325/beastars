<template>
  <div class="sv-table" :class="className">
    <!-- 首先声明一个el-table data是传进来的-->
    <el-table
      ref="table"
      :data="data"
      :max-height="customTableHeight || tableHeight"
      :height="customTableHeight || tableHeight"
      border
      :style="getTableStyle"
      :stripe="stripe"
      v-bind="$attrs"
      v-on="$listeners"
      @header-dragend="changeColumnWidth"
      @row-dblclick="handleRowDbclick"
      @row-click="handleRowClick"
    >
      <!-- 复选框（可以选择多个） -->
      <el-table-column v-if="selection" key="selection" align="center" type="selection" width="55" fixed="left" />
      <!-- 单选框（只能选择单个） -->
      <el-table-column v-if="singleSelect" key="singleSelect" align="center" width="55" fixed="left">
        <template #default="scope">
          <el-radio v-model="singleSelectValue" :label="scope.row[pk]" @change="singleSelectionChange(scope.row)" />
        </template>
      </el-table-column>
      <!-- 序号 -->
      <el-table-column
        v-if="tableIndex"
        key="index"
        type="index"
        align="center"
        :width="fieldList.length === 0 ? '' : 55"
      >
        <template #default="scope">
          <span>{{ scope.$index + 1 + (listInfo.query.page - 1) * listInfo.query.limit }}</span>
        </template>
      </el-table-column>
      <!-- 这个是正常传进来的列，进行循环 -->
      <el-table-column
        v-for="(item, index) in newFieldList.filter(item => item.show)"
        :key="`${index}-${item.prop}`"
        :label="item.label"
        :prop="item.prop"
        :align="item.align || 'center'"
        :width="item.width"
        :min-width="item.minWidth || '100px'"
        :show-overflow-tooltip="item.showOverflowTooltip"
        :sortable="item.sortable === false ? false : true"
        :fixed="item.fixed"
      >
        <!-- 传进来的列，会定义type这个属性，从这里来判断，从而显示不同的插槽 -->
        <template #default="scope">
          <!-- slot自定义列 -->
          <template v-if="item.type === 'slot'">
            <slot :name="`col-${item.prop}`" :row="scope.row" />
          </template>
          <!-- 日期 -->
          <template v-else-if="item.type === 'date'">
            <span v-if="scope.row[item.prop]">{{ formatter(scope.row[item.prop],'yyyy-MM-dd hh:mm:ss') }}</span>
          </template>
          <!-- 链接 -->
          <template v-else-if="item.type === 'link'">
            <a style="color: #409eff" @click="openLink(item, scope.row)">{{ scope.row[item.prop] }}</a>
          </template>
          <!-- 复杂标签 -->
          <div v-else-if="item.type === 'tag' && item.tagOptions">
            <el-tag :type="getTagType(scope.row, item)">{{ getTagValue(scope.row, item) }}</el-tag>
          </div>
          <!-- 简单标签 -->
          <el-tag v-else-if="item.type === 'tag' && !item.tagOptions">
            {{ scope.row[item.prop] }}
          </el-tag>
          <!-- 图片 -->
          <img v-else-if="item.type === 'image' && scope.row[item.prop]" height="50px" :src="scope.row[item.prop]">
          <!-- 文件 -->
          <sv-upload-file
            v-else-if="item.type === 'file' && scope.row[item.prop]"
            v-model="scope.row[item.prop]"
            :storage-type="item.storageType ? item.storageType : 'minio'"
            :biz-dir="item.bizDir"
            button-style="icon"
            :disabled="true"
            :multiple="true"
          />
          <!-- 其他 -->
          <span v-else>{{ dictFilter(scope.row[item.prop],dicts[item.dictType]) }}</span>
        </template>
        <!-- 嵌套表格 -->
        <template v-if="item.children">
          <el-table-column
            v-for="(childItem, childIndex) in item.children"
            :key="childIndex"
            :prop="childItem.prop"
            :label="childItem.label"
            :align="childItem.align || 'center'"
            :width="childItem.width"
            :min-width="childItem.minWidth || '85px'"
            :show-overflow-tooltip="childItem.showOverflowTooltip"
            :sortable="item.sortable"
            :fixed="childItem.fixed"
          >
            <template #default="scope">
              <span>{{ dictFilter(scope.row[childItem.prop],dicts[childItem.dictType]) }}</span>
            </template>
          </el-table-column>
        </template>
      </el-table-column>
      <!-- 操作栏 -->
      <el-table-column
        v-if="handle"
        key="handle"
        :fixed="handle.fixed"
        :align="handle.align || 'center'"

        :width="handle.width"
        :min-width="handle.minWidth"
      >
        <template #default="scope">
          <template v-for="(item, index) in handle.btList">
            <!-- 自定义操作类型 -->
            <slot
              v-if="item.slot && item.show && !btnHideSet(item.event, scope.row)"
              :name="`bt-${item.event}`"
              :data="{ item, row: scope.row }"
            />
            <!-- 操作按钮 -->
            <el-button
              v-if="!item.slot && item.show && !btnHideSet(item.event, scope.row)"
              :key="index"
              size="small"
              :type="item.type"
              :icon="item.icon"
              :disabled="item.disabled || btnDisableSet(item.event, scope.row)"
              @click.stop="handleClick(item.event, scope.row, scope.$index)"
            >
              {{ item.label }}
            </el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <template v-if="pager">
      <div class="table-pagination-container">
        <el-pagination
          :current-page.sync="listInfo.query.page"
          :page-sizes="listInfo.pageSizes"
          :page-size="listInfo.query.limit"
          layout="total, sizes, prev, pager, next, jumper"
          :total="listInfo.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </template>
    <!-- 一个关于选择的东西 -->
    <sv-column-select
      ref="columnSelector"
      :module="module"
      :data="newFieldList"
      :field-list="fieldList"
      :visible.sync="columnSelectorVisible"
      @change-column-set="changeColumnSet"
    />
  </div>
</template>
<script>
import _ from 'lodash'
import listHeightMixin from '@/mixins/listHeightMixin'
import { getColumnSetting, saveColumnSetting } from '@/components/SvColumnSelect/api'
export default {
  name: 'SvTable',
  mixins: [listHeightMixin],
  props: {
    // 斑马线
    stripe: {
      type: Boolean,
      default: true
    },
    // 是否显示序号
    tableIndex: {
      type: Boolean,
      default: false
    },
    // 是否有单选框
    singleSelect: {
      type: Boolean,
      default: false
    },
    // 是否有复选框
    selection: {
      type: Boolean,
      default: false
    },
    // 选中行数据
    checkedList: {
      type: Array,
      default: () => []
    },
    // 获取数据的接口
    api: {
      type: Function,
      required: true
    },
    // 列表数据
    data: {
      type: Array
    },
    // 表格字段配置
    fieldList: {
      type: Array,
      required: true,
      default: () => []
    },
    // 主键
    pk: {
      type: String,
      default: 'id'
    },
    // 是否分页
    pager: {
      type: Boolean,
      default: true
    },
    // 普通列表查询条件
    query: {
      type: Object,
      default: () => ({})
    },
    // SvSearchTable的查询条件
    queryList: {
      type: Array,
      default: () => []
    },
    // 刷新
    refresh: {
      type: Number
    },
    // 重置当前页
    initCurrentPage: {
      type: Number
    },
    // 如果需要配置隐藏列功能需要传入module，module需要保证唯一性
    // 如果重新设置了列表的列，例如新增列，修改列，需要删除掉admin库里面的base_user_columns_setting表里面对应module的列设置
    module: {
      type: String
    },
    // 操作栏配置
    handle: {
      type: Object
    },
    // 操作按钮隐藏功能
    btnHideSet: {
      type: Function,
      default: () => false
    },
    // 操作按钮disabled
    btnDisableSet: {
      type: Function,
      default: () => false
    },
    // 导出接口
    exportApi: {
      type: Function
    },
    // 导出最大行数
    maxExportRows: {
      type: Number,
      default: 10000
    },
    // 导出文件名
    exportFileName: {
      type: String
    },
    // 自定义table高度
    customTableHeight: {
      type: [String, Number]
    },
    // 表格宽度，默认100%
    tabelWidth: {
      type: String,
      default: '100%'
    },
    // 自定义类名
    className: {
      type: String
    }
  },
  data() {
    return {
      // 单选行对应的value
      singleSelectValue: undefined,
      // 列表信息
      listInfo: {
        // 总条数
        total: 0,
        // 分页数量列表
        pageSizes: [10, 20, 50, 100],
        // 查询条件
        query: {
          page: 1,
          // 每页条数
          limit: 20
        }
      },
      newFieldList: [],
      columnSelectorVisible: false
    }
  },
  computed: {
    // 数据字典
    dicts() {
      return this.$store.getters.dicts
    },
    // 列表样式
    getTableStyle() {
      return { width: this.tabelWidth }
    }
  },
  watch: {
    // 把当前页重置到第一页
    initCurrentPage() {
      this.listInfo.query.page = 1
    },
    // 刷新列表
    refresh() {
      if (!this.api) return
      this.getList()
    },
    // 默认选中某一行数据
    checkedList() {
      if (this.checkedList && this.checkedList.length && this.rows) {
        // 设置当前选中项
        this.checkedList.forEach(selected => {
          const row = this.rows.find(item => item[this.pk] === selected)
          this.$nextTick(() => {
            if (!row) return
            this.$refs.table.toggleRowSelection(row, true)
          })
        })
      }
    }
  },
  // 更新列表
  updated() {
    this.$refs.table.doLayout()
  },
  // 初始化列设置
  created() {
    this.initColumns()
  },
  methods: {
    // 初始化列设置
    // 1. 如果没有传入module，即列设置未保存到数据库，直接通过调用geneNewFieldList生成列表的当前列
    // 2. 如果传入了module，即列设置可能已经保存到数据库
    // 2.1 请求数据库保存的列设置的数据
    // 2.2 判断后台是否请求回来数据
    // 2.2.1 如果请求回来数据，调用geneNewFieldListForModule对请求后台的数据做处理，取出每一列的设置with和show，以及列的顺序
    // 2.2.2 如果未请求回来数据，调用geneNewFieldList生成列表的当前列
    async initColumns() {
      if (!this.module) {
        this.newFieldList = this.geneNewFieldList()
      } else {
        const response = await getColumnSetting(this.module)
        this.newFieldList =
          response && response.length ? this.geneNewFieldListForModule(JSON.parse(response)) : this.geneNewFieldList()
      }
    },
    // 根据前台定义生成列设置，过滤掉hidden隐藏的列，并且把显示的列设置show为true
    geneNewFieldList() {
      const newFieldList = this.fieldList
        .filter(el => !el.hidden)
        .map(item => {
          this.$set(item, 'show', true)
          return item
        })
      return _.cloneDeep(newFieldList)
    },
    // 根据数据库种保存的列设置做处理
    geneNewFieldListForModule(oldFieldList) {
      // 前台设置的列设置为fieldList过滤掉隐藏列
      const newFieldList = this.fieldList.filter(el => !el.hidden)
      let storedInDbFields = []
      const newAddFields = []
      // 遍历前台列设置
      for (let index = 0; index < newFieldList.length; index++) {
        // 前台列对象item
        const item = { ...newFieldList[index] }
        // 获得当前列在数据库中存储的数据
        const oldIndex = oldFieldList.findIndex(el => item.prop === el.prop)
        // 判断，如果后台中没有oldIndex不为-1，在item中记录在后台存存储的顺序，获得是否展示show，获取列设置width或者minWidth，把数据存储到storedInDbFields中
        // 如果oldIndex为false说明为前台新增列，设置show属性为true，找到其前一列的属性prop，将其存储到新增表单属性数组newAddFields中
        if (oldIndex !== -1) {
          item.orderNum = oldIndex
          item.show = oldFieldList[oldIndex].show
          if (item.width) {
            item.width = oldFieldList[oldIndex].width ? oldFieldList[oldIndex].width : item.width
          }
          if (item.minWidth) {
            item.minWidth = oldFieldList[oldIndex].minWidth ? oldFieldList[oldIndex].minWidth : item.minWidth
          }
          storedInDbFields.push(item)
        } else {
          this.$set(item, 'show', true)
          item.preProp = index > 0 ? newFieldList[index - 1].prop : undefined
          newAddFields.push(item)
        }
      }
      // 对之前已经存在的列表配置项storedInDbFields进行排序
      storedInDbFields.sort(this.compareArray('orderNum'))
      // 遍历newAddFields
      // 如果不存在前一列的属性，将其放到第一列
      // 否则将其插入到前一列的下一列
      newAddFields.forEach(item => {
        if (!item.preProp) {
          storedInDbFields = [item, ...storedInDbFields]
        } else {
          const preIndex = storedInDbFields.findIndex(el => el.prop === item.preProp)
          storedInDbFields.splice(preIndex + 1, 0, item)
        }
      })
      return storedInDbFields
    },
    // 对数组进行排序
    compareArray(prop) {
      return function(a, b) {
        const value1 = a[prop]
        const value2 = b[prop]
        return value1 - value2
      }
    },
    // 拖动列对列宽进行保存
    changeColumnWidth(newWidth, oldWidth, column) {
      if (!this.module) return
      this.newFieldList.forEach(el => {
        if (el.prop === column.property) {
          if (el.minWidth) {
            el.minWidth = newWidth
          } else {
            el.width = newWidth
          }
        }
      })
      saveColumnSetting({ module: this.module, columns: JSON.stringify(this.newFieldList) })
      this.$refs.table.doLayout()
    },
    // 重置列设置
    async resetColumnSettings() {
      const newFieldList = this.fieldList
        .filter(el => !el.hidden)
        .map(item => {
          this.$set(item, 'show', true)
          return item
        })
      this.newFieldList = _.cloneDeep(newFieldList)
      await saveColumnSetting({ module: this.module, columns: JSON.stringify(newFieldList) })
    },
    // 打开列设置弹出框
    columnsSettings() {
      this.columnSelectorVisible = true
    },
    // 更新列设置
    changeColumnSet(columns) {
      this.newFieldList = columns
    },
    // 处理查询条件
    handleParams() {
      const obj = {}
      for (const key in this.query) {
        const queryValue = this.query[key]
        if (this.queryValueIsTrue(queryValue)) {
          obj[key] = Array.isArray(queryValue) ? queryValue.join(',') : queryValue
        }
      }
      // 如果不需要分页，则无分页相关参数
      return this.pager ? { ...obj, ...this.listInfo.query } : obj
    },
    queryValueIsTrue(value) {
      if (value === 0 || value === false) return true
      if (Array.isArray(value) && value.length) return true
      if (!Array.isArray(value) && value) return true
      return false
    },
    // 获取数据
    getList() {
      if (!this.api) return
      this.$store.dispatch('app/loadingStart')
      this.api(this.handleParams(), this.queryList)
        .then(res => {
          if (res.status === 200) {
            this.rows = []
            if (this.pager) {
              this.rows = res.data.rows
              this.listInfo.total = Number(res.data.total)
            } else {
              this.rows = res.data
            }
            this.$emit('update:data', this.rows)
            // 如果是单选singleSelect，清空选中条件
            if (this.singleSelect) {
              this.singleSelectValue = undefined
              this.$emit('selection-change', undefined)
            }
            // 设置当前选中项
            this.checkedList.forEach(selected => {
              const row = this.rows.find(item => item[this.pk] === selected)
              this.$nextTick(() => this.$refs.table.toggleRowSelection(row, true))
            })
          } else {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error',
              duration: 2000
            })
          }
        })
        .finally(() => {
          this.$store.dispatch('app/loadingEnd')
        })
    },
    // 改变每页显示数量
    handleSizeChange(val) {
      this.listInfo.query.limit = val
      this.listInfo.query.page = 1
      this.getList()
    },
    // 换页
    handleCurrentChange(val) {
      this.listInfo.query.page = val
      this.getList()
    },
    // 获取标签类型
    getTagType(row, item) {
      if (row[item.prop] !== undefined && row[item.prop] !== null) {
        return item.tagOptions[row[item.prop]] ? item.tagOptions[row[item.prop]].type : 'primary'
      }
      return 'primary'
    },
    // 获取标签值
    getTagValue(row, item) {
      if (row[item.prop] !== undefined && row[item.prop] !== null) {
        return item.tagOptions[row[item.prop]] ? item.tagOptions[row[item.prop]].name : ''
      }
    },
    // 单选选中行数据
    singleSelectionChange(row) {
      this.$emit('selection-change', _.cloneDeep(row))
    },
    // 单击事件
    handleRowClick(row) {
      if (this.singleSelect) {
        this.singleSelectValue = row[this.pk]
        this.$emit('selection-change', _.cloneDeep(row))
      }
      if (this.selection) {
        this.$refs.table.toggleRowSelection(row)
      }
    },
    // 双击行事件
    handleRowDbclick(row) {
      this.$emit('row-dbclick', row)
    },
    // 派发按钮点击事件
    handleClick(event, data, index) {
      this.$emit('handle-click', event, data, index)
    },
    // 打开链接
    openLink(item, row) {
      // 构造url参数
      let query = {}
      // 行数据上获取的参数
      if (item.params && item.params.length) {
        item.params.forEach(el => {
          query[el] = row[el]
        })
      }
      if (item.tagInfo) {
        let tagInfo = { ...item.tagInfo }
        tagInfo[tagInfo.pkName] = row[tagInfo.pkName]
        if (item.titleParams) {
          item.titleParams.forEach((el, i) => {
            tagInfo.title = tagInfo.title.replace(`{${i + 1}}`, row[el])
          })
        }
        tagInfo = { ...tagInfo, ...query }
        this.$openTag(this, tagInfo)
      } else {
        if (item.customParams) {
          query = { ...query, ...item.customParams }
        }
        // 构造路由
        let path = item.path
        // 动态路由参数
        if (item.dynamicRouteParams) {
          item.dynamicRouteParams.forEach(el => {
            path += `/${row[el] || el}`
          })
        }
        const routeUrl = this.$router.resolve({
          path,
          query
        })
        window.open(routeUrl.href, '_blank')
      }
    },
    // 导出
    async exportExcel() {
      if (!this.api && !this.exportApi) {
        return this.$notify.warning({
          message: '请求错误',
          duration: 2000
        })
      }
      const queryCondition = this.handleParams()
      // 用分页的单页行数设置导出最大行数
      queryCondition.page = 1
      queryCondition.limit = this.maxExportRows
      await this.handleExport(queryCondition)
    },
    // 格式化日期
    formatter(thistime, fmt) {
      const $this = new Date(thistime)
      const o = {
        'M+': $this.getMonth() + 1,
        'd+': $this.getDate(),
        'h+': $this.getHours(),
        'm+': $this.getMinutes(),
        's+': $this.getSeconds(),
        'q+': Math.floor(($this.getMonth() + 3) / 3),
        'S': $this.getMilliseconds()
      }
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ($this.getFullYear() + '').substr(4 - RegExp.$1.length))
      }
      for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
      }
      return fmt
    },
    // 导出当前页
    exportCurrentPage() {
      if (!this.api && !this.exportApi) {
        return this.$notify.warning({
          message: '请求错误',
          duration: 2000
        })
      }
      this.handleExport(this.handleParams())
    },
    // // 后台请求导出数据，处理数据
    // async handleExport(query) {
    //   const result = this.exportApi ? await this.exportApi(query) : await this.api(query, this.queryList)
    //   const tHeader = []
    //   this.$refs.table.columns.forEach(el => {
    //     // 排除操作列
    //     if (el.type !== 'selection' && el.label) {
    //       tHeader.push(el.label)
    //     }
    //   })
    //   const data = []
    //   debugger
    //   result.data.rows.forEach((item, i) => {
    //     // 返回的数据进行，每行都循环
    //     const row = []
    //     // 如果要求显示序号的话。就忘row里main放进一个
    //     if (this.tableIndex) row.push(i + 1)
    //     // 再根据每列进行循环
    //     this.$refs.table.columns.forEach(el => {
    //       // 在所有定义的列中，寻找这条列的数据，（看数据中是否包含字典值什么的），也就是找到对应的fieldList
    //       const field = this.fieldList.find(f => f.prop === el.property)
    //       // 判断首先找到的fieldList不为空，并且当列的名称啥的不为空的时候，才进行下面的操作
    //       if (el.property && field) {
    //         // 如果该列的type类型是tag的时候，就进行下面的操作
    //         if (field.type === 'tag' && field.tagOptions) {
    //           // 标签列展示信息转换
    //           row.push(field.tagOptions[item[el.property] ? item[el.property] : ''].name)
    //         } else if (field.dictType) {
    //           // 当存在字段值的时候执行下面的操作
    //           // 字典列展示信息转换
    //           // row.push(this.dictFilter(item[el.property], this.dicts[field.dictType]))
    //           // 首先根据传进来的字典值名称，查询一下相关的信息
    //           this.getDicts(field.dictType).then(res => {
    //             row.push(this.dictFilter(item[el.property], res))
    //           })
    //         } else {
    //           row.push(item[el.property])
    //         }
    //       }
    //     })
    //     data.push(row)
    //   })
    //   export_json_to_excel({
    //     header: tHeader,
    //     data,
    //     filename: this.exportFileName
    //   })
    // },
    dictFilter(val, dictList) {
      if (!dictList) return val
      // 会出现一种情况，那就是，如果字典值有数字也有字符串怎么办
      const dict = dictList.find(el => el.dictValue === val)
      if (dict) {
        return dict.dictLabel
      } else {
        return val
      }
    },

    async dictFilter1(val, dictType) {
      if (!dictType) return val
      await this.getDicts(dictType).then(res => {
        res.data.forEach(el => {
          if (Number(el.dictValue) === val) {
            return el.dictLabel
          }
        })
        return val
      })
      // const res = this.getDicts(dictType)
      // const dict = res.data.find(el => el.dictValue === val)
      // if (dict) {
      //   return dict.dictLabel
      // } else {
      //   return val
      // }
    }
  }
}
</script>
<style lang="scss" scoped>
.sv-table {
  .table-pagination-container {
    padding: 15px 0;
  }
}
::v-deep.el-table--mini td,
.el-table--mini th {
  padding: 2px 0 !important;
}
::v-deep.el-radio .el-radio__label {
  display: none;
}
</style>
