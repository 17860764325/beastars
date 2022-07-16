<template>
  <el-dialog :visible.sync="dialogVisible" close-on-click-modal width="700px" append-to-body>
    <el-transfer
      v-model="values"
      style="text-align: left; display: block"
      :props="{ key: 'prop' }"
      :titles="['显示列', '隐藏列']"
      :data="columns"
      ref="transfer"
    >
      <div slot="left-footer">
        <el-button class="transfer-footer" @click="up">
          <i class="el-icon-arrow-up" title="上移"></i>
        </el-button>
        <el-button class="transfer-footer" @click="down">
          <i class="el-icon-arrow-down" title="下移"></i>
        </el-button>
        <el-button class="transfer-footer" @click="reset">
          <i class="el-icon-refresh" title="重置列设置"></i>
        </el-button>
      </div>
      <div slot="right-footer" style="visibility：hidden;"></div>
    </el-transfer>
  </el-dialog>
</template>
<script>
import visibleSyncMixin from '@/mixins/visibleSyncMixin'
import { saveColumnSetting } from './api'
export default {
  name: 'SvColumnSelect',
  mixins: [visibleSyncMixin],
  data() {
    return {
      values: [],
      columns: []
    }
  },
  props: {
    data: Array,
    module: String,
    getShowFieldList: Function
  },
  watch: {
    visible: {
      immediate: true,
      handler() {
        if (this.visible) {
          this.columns = _.cloneDeep(this.data)
          this.data.forEach(el => {
            // 列设置为隐藏，并且没有被添加过，设置为隐藏列
            if (el.show === false && !this.values.includes(el.prop)) {
              this.values.push(el.prop)
            }
          })
        }
      }
    },
    values: {
      deep: true,
      handler() {
        this.columns.forEach(el => {
          el.show = !this.values.includes(el.prop)
        })
      }
    },
    columns: {
      deep: true,
      handler() {
        this.$emit('change-column-set', _.cloneDeep(this.columns))
        if (!this.module) return
        saveColumnSetting({ module: this.module, columns: JSON.stringify(_.cloneDeep(this.columns)) })
      }
    }
  },
  methods: {
    up() {
      const leftChecked = this.$refs.transfer.leftChecked
      if (!leftChecked || !leftChecked.length) {
        return this.$notify({
          title: '提示',
          message: '请在左侧选择排序列字段',
          type: 'warning',
          duration: 2000
        })
      }
      if (leftChecked.length > 1) {
        return this.$notify({
          title: '提示',
          message: '只能选择一项',
          type: 'warning',
          duration: 2000
        })
      }
      const moveElement = leftChecked[0]
      const fromIndex = this.columns.findIndex(el => el.prop === moveElement)
      const fromColumn = this.columns[fromIndex]
      for (let i = fromIndex - 1; i >= 0; i--) {
        const column = this.columns[i]
        if (column.show) {
          this.columns.splice(fromIndex, 1, column)
          this.columns.splice(i, 1, fromColumn)
          break
        }
      }
    },
    down() {
      const leftChecked = this.$refs.transfer.leftChecked
      if (!leftChecked || !leftChecked.length) {
        return this.$notify({
          title: '提示',
          message: '请在左侧选择排序列字段',
          type: 'warning',
          duration: 2000
        })
      }
      const moveElement = leftChecked[0]
      if (leftChecked.length > 1) {
        return this.$notify({
          title: '提示',
          message: '只能选择一项',
          type: 'warning',
          duration: 2000
        })
      }
      const fromIndex = this.columns.findIndex(el => el.prop === moveElement)
      const fromColumn = this.columns[fromIndex]
      for (let i = fromIndex + 1; i < this.columns.length; i++) {
        const column = this.columns[i]
        if (column.show) {
          this.columns.splice(fromIndex, 1, column)
          this.columns.splice(i, 1, fromColumn)
          break
        }
      }
    },
    reset() {
      this.columns = this.getShowFieldList()
      this.resetValues()
    },
    resetValues() {
      this.values = []
    }
  }
}
</script>
<style lang="scss" scoped>
.transfer-footer {
  margin: 8px 0 0 10px;
  padding: 5px 10px;
}
::v-deep .el-transfer-panel__list {
  height: 206px;
}
</style>
