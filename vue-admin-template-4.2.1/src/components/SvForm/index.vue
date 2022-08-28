<!--  功能：CommonForm表单
      作者：刘玉
      日期：2020-03-29
-->
<template>
  <el-form ref="form" :model="form" :label-position="labelPosition" :label-width="labelWidth" :disabled="formDisabled">
    <el-row>
      <el-col
        v-for="item in fieldList"
        :key="item.label"
        :xs="24"
        :sm="getSm(item)"
        :md="getMd(item)"
        :lg="getLg(item)"
        :xl="getXl(item)"
      >
        <el-form-item
          v-if="!item.hasOwnProperty('show') || (item.hasOwnProperty('show') && item.show)"
          :prop="item.prop"
          :rules="item.rules ? filter_rules(item.rules) : item.rules"
          :style="getFormItemStyle(item)"
        >
          <!-- 标签名 -->
          <template v-slot:label>
            <span>{{ item.label }}</span>
            <el-tooltip v-if="item.tooltip" placement="top">
              <div slot="content" style="line-height: 18px" v-html="item.tooltip" />
              <svg-icon icon-class="help" />
            </el-tooltip>
          </template>
          <!-- text类型 -->
          <el-input
            v-if="item.type === 'text'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `${'请填写'}${item.label}`"
            :disabled="!!item.disabled"
            :clearable="!!item.clearable"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- multipleInput类型 -->
          <sv-input
            v-if="item.type === 'multipleInput'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `${'请填写'}${item.label}`"
            :disabled="!!item.disabled"
            :clearable="!!item.clearable"
            style="width: 100%"
            :class="item.class"
            :style="item.style"
            @handleSave="handleEvent(item.event, form[item.prop])"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- button类型 -->
          <el-button
            v-if="item.type === 'button'"
            :type="item.buttonType"
            :icon="item.icon"
            :disabled="item.disabled"
            @click="handleClick(item.event)"
          >{{ item.name }}</el-button>
          <!-- textarea类型 -->
          <el-input
            v-if="item.type === 'textarea'"
            :ref="item.prop"
            v-model="form[item.prop]"
            type="textarea"
            :placeholder="item.placeholder || `${'请填写'}${item.label}`"
            :disabled="!!item.disabled"
            :autosize="{ minRows: item.minRows || 1, maxRows: item.maxRows || 4 }"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- password类型 -->
          <el-input
            v-if="item.type === 'password'"
            :ref="item.prop"
            v-model="form[item.prop]"
            type="password"
            :placeholder="item.placeholder || `${'请填写'}${item.label}`"
            :disabled="!!item.disabled"
            :show-password="!!item.showPassword"
            :clearable="!!item.clearable"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- passProgress类型，密码输入框带密码强度条 -->
          <sv-pass-progress
            v-if="item.type === 'passProgress'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :event="item.event"
            :placeholder="item.placeholder"
            :disabled="item.disabled"
            :show-password="item.showPassword"
            :class="item.class"
            :style="item.style"
            @change="handleEvent"
          />
          <!-- singlePopup类型，input框通过点击按钮选择输入 -->
          <el-input
            v-if="item.type === 'singlePopup'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `请选择${item.label}`"
            readonly
            :class="item.class"
            :style="item.style"
          >
            <i
              v-if="form[item.prop] && !!item.clearable && !item.disabled"
              slot="prefix"
              class="el-icon-circle-close el-input__icon"
              style="cursor: pointer"
              @click="handleClear(item.event, item.prop)"
            />
            <el-button
              slot="append"
              icon="el-icon-search"
              :disabled="!!item.disabled"
              @click="handleClick(item.event, item.prop)"
            />
          </el-input>
          <!-- number类型 -->
          <el-input-number
            v-if="item.type === 'number'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `${'请填写'}${item.label}`"
            :disabled="!!item.disabled"
            :min="item.min"
            :max="item.max"
            :precision="item.precision || item.precision === 0 ? item.precision : 2"
            :step="item.step"
            :controls="item.controls === true ? item.controls : false"
            style="width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- select类型 -->
          <el-select
            v-if="item.type === 'select'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `${'请选择'}${item.label}`"
            :disabled="!!item.disabled"
            :filterable="!!item.filterable"
            :clearable="!!item.clearable"
            style="display: inline-block; width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          >
            <el-option
              v-for="el in getOptions(item) ? getOptions(item) : options[item.prop]"
              :key="el[item.optSet && item.optSet.dictValue ? item.optSet.dictValue : 'dictValue']"
              :label="el[item.optSet && item.optSet.dictLabel ? item.optSet.dictLabel : 'dictLabel']"
              :value="el[item.optSet && item.optSet.dictValue ? item.optSet.dictValue : 'dictValue']"
              :disabled="el.disabled"
            />
          </el-select>
          <!-- multipleSelect类型，多选类型 -->
          <el-select
            v-if="item.type === 'multipleSelect'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `${'请选择'}${item.label}`"
            :disabled="!!item.disabled"
            multiple
            :collapse-tags="!!item.collapseTags"
            :filterable="!!item.filterable"
            :clearable="!!item.clearable"
            style="display: inline-block; width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          >
            <el-option
              v-for="el in getOptions(item) ? getOptions(item) : options[item.prop]"
              :key="el[item.optSet && item.optSet.value ? item.optSet.value : 'value']"
              :label="el[item.optSet && item.optSet.label ? item.optSet.label : 'labelDefault']"
              :value="el[item.optSet && item.optSet.value ? item.optSet.value : 'value']"
              :disabled="el.disabled"
            />
          </el-select>
          <!-- cascader类型 -->
          <el-cascader
            v-if="item.type === 'cascader'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :placeholder="item.placeholder || `${'请选择'}${item.label}`"
            :disabled="!!item.disabled"
            :filterable="!!item.filterable"
            :clearable="!!item.clearable"
            :props="item.optSet"
            :options="getOptions(item) ? getOptions(item) : options[item.prop]"
            style="display: inline-block; width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- citySelect类型，省市区选择组件 -->
          <sv-city-select
            v-if="item.type === 'citySelect'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :event="item.event"
            :prop="item.prop"
            :placeholder="item.placeholder"
            :disabled="item.disabled"
            :region="item.region"
            :level="item.level"
            :class="item.class"
            :style="item.style"
            @change="handleCitySelect"
          />
          <!-- date类型 -->
          <el-date-picker
            v-if="item.type === 'date'"
            :ref="item.prop"
            v-model="form[item.prop]"
            type="date"
            :placeholder="item.placeholder || `${'请选择'}${item.label}`"
            :disabled="!!item.disabled"
            :picker-options="item.pickerOptions"
            value-format="yyyy-MM-dd"
            style="width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- datetime类型 -->
          <el-date-picker
            v-if="item.type === 'datetime'"
            :ref="item.prop"
            v-model="form[item.prop]"
            type="datetime"
            :placeholder="item.placeholder || `${'请选择'}${item.label}`"
            :disabled="!!item.disabled"
            :picker-options="item.pickerOptions"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- daterange类型 -->
          <el-date-picker
            v-if="item.type === 'daterange'"
            :ref="item.prop"
            v-model="form[item.prop]"
            type="daterange"
            :disabled="!!item.disabled"
            :picker-options="item.pickerOptions"
            :range-separator="item.rangeSeparator || '至'"
            :start-placeholder="item.startPlaceholder || '开始时间'"
            :end-placeholder="item.endPlaceholder || '结束时间'"
            value-format="yyyy-MM-dd"
            style="width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- datetimerange类型 -->
          <el-date-picker
            v-if="item.type === 'datetimerange'"
            :ref="item.prop"
            v-model="form[item.prop]"
            type="datetimerange"
            :disabled="!!item.disabled"
            :picker-options="item.pickerOptions"
            :range-separator="item.rangeSeparator || '至'"
            :start-placeholder="item.startPlaceholder || '开始时间'"
            :end-placeholder="item.endPlaceholder || '结束时间'"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <!-- upload类型 -->
          <sv-upload-file
            v-if="item.type === 'upload'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :storage-type="item.storageType"
            :multiple="item.multiple"
            :file-type="item.fileType"
            :limit="item.limit"
            :disabled="item.disabled"
            :list-type="item.listType"
            :dir="item.dir"
            :button-style="item.buttonStyle"
            :value="item.value"
            :badge-hidden="item.badgeHidden"
            :biz-dir="item.bizDir"
            @change="handleChange(item)"
          />
          <!-- radio类型 -->
          <el-radio-group
            v-if="item.type === 'radio'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :disabled="!!item.disabled"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          >
            <el-radio
              v-for="el in getOptions(item) ? getOptions(item) : options[item.prop]"
              :key="el[item.optSet && item.optSet.label ? item.optSet.label : 'label']"
              :label="el[item.optSet && item.optSet.label ? item.optSet.label : 'label']"
              :disabled="!!item.disabled"
            >
              {{ el[item.optSet && item.optSet.name ? item.optSet.name : 'label'] }}
            </el-radio>
          </el-radio-group>
          <!-- checkbox类型 -->
          <el-checkbox-group
            v-if="item.type === 'checkbox'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          >
            <el-checkbox
              v-for="el in getOptions(item) ? getOptions(item) : options[item.prop]"
              :key="el.label"
              :label="el[item.optSet && item.optSet.label ? item.optSet.label : 'label']"
              :disabled="!!item.disabled"
            >
              {{ el[item.optSet && item.optSet.name ? item.optSet.name : 'label'] }}
            </el-checkbox>
          </el-checkbox-group>
          <!-- switch类型 -->
          <el-switch
            v-if="item.type === 'switch'"
            :ref="item.prop"
            v-model="form[item.prop]"
            :disabled="!!item.disabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-text="item.activeText"
            :inactive-text="item.inactiveText"
            :active-value="item.activeValue || 1"
            :inactive-value="item.inactiveValue || item.inactiveValue === false ? item.inactiveValue : 0"
            :class="item.class"
            :style="item.style"
            @change="handleEvent(item.event, form[item.prop])"
          />
          <template v-if="item.type === 'slot'">
            <slot :name="`form-item-${item.prop}`" />
          </template>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
import { getAllDicts } from '@/api/dict/api'
export default {
  name: 'SvForm',
  props: {
    // form表单
    // eslint-disable-next-line vue/require-default-prop
    form: {
      type: Object
    },
    // form表单是否禁用
    formDisabled: {
      type: Boolean,
      default: false
    },
    // 表单项列表
    // eslint-disable-next-line vue/require-default-prop
    fieldList: {
      type: Array
    },
    // 左右布局时标签宽度
    labelWidth: {
      type: String,
      default: '110px'
    }
  },
  data() {
    return {
      dicts: undefined,
      // 表单布局，默认上下布局，如果想修改为左右只需要把labelPosition改为right，开发根据labelWidth调整每个表单的宽度
      labelPosition: 'top',
      options: {}
    }
  },
  watch: {
    form: {
      handler() {
        // 初始化遍历feildList列表，为form对象添加属性
        this.fieldList
          .filter(item => item.prop)
          .forEach(el => {
            if (!this.form.hasOwnProperty(el.prop)) {
              if (this.isType(el.type)) {
                this.$set(this.form, el.prop, [])
              } else if (el.type === 'switch') {
                this.$set(this.form, el.prop, el.inactiveValue || 0)
              } else {
                this.$set(this.form, el.prop, undefined)
              }
            }
          })
      },
      immediate: true
    }
  },
  async created() {
    const res = await getAllDicts()
    this.dicts = res.data
    this.setOptions()
  },
  methods: {
    // 判断输入框的类型
    isType(type) {
      return type === 'checkbox' || type === 'multipleSelect' || type === 'daterange' || type === 'datetimerange'
    },
    // 根据不同的类型和布局设置表单项的padding，margin，height
    getFormItemStyle(item) {
      if (item.type === 'upload' || item.type === 'textarea' || item.type === 'passProgress') {
        return this.labelPosition === 'top' ? { padding: '0 10px', marginBottom: '10px' } : {}
      } else {
        // 每个form表单上面的label
        const topStyle = { height: '41px', padding: '0 10px', marginBottom: '50px' }
        const rightStyle = { height: '28px' }
        return this.labelPosition === 'top' ? topStyle : rightStyle
      }
    },
    // 屏幕分辨率 >=768px && <992px
    getSm(item) {
      return item.sm ? Number(item.sm) : this.labelPosition === 'top' ? 12 : 12
    },
    // 屏幕分辨率 >=992px && <1200px
    getMd(item) {
      return item.md ? Number(item.md) : this.labelPosition === 'top' ? 8 : 12
    },
    // 屏幕分辨率 >=1200px && <1920px
    getLg(item) {
      return item.lg ? Number(item.lg) : this.labelPosition === 'top' ? 6 : 8
    },
    // 屏幕分辨率 >=1920px
    getXl(item) {
      return item.xl ? Number(item.xl) : 6
    },
    // 下拉框选项，后台获取
    setOptions() {
      const promise = this.fieldList
        .filter(item => item.optApi)
        .map(el => {
          const obj = {}
          obj[el.prop] = el.optApi(el.optQuery).then(response => (response ? response.data : []))
          return obj
        })
      Promise.all(promise).then(res => {
        res.forEach(el => {
          Object.keys(el).forEach(key => {
            el[key].then(data => {
              this.$set(this.options, key, data)
            })
          })
        })
      })
    },
    // 上传组件清空或者上传后调用表单必填校验
    handleChange(item) {
      this.$refs[item.prop][0].$parent.validate('change')
    },
    // 下拉框选项，数据字典获取，或者直接通过feildList传入
    getOptions(item) {
      if (item.dict) {
        return this.dicts[item.dict]
      } else {
        return item.options
      }
    },
    // 省/市/区选择change事件
    handleCitySelect(event, data, prop) {
      // 省/市/区选择必填校验
      this.$nextTick(() => {
        this.$refs.form.validateField([prop])
      })
      // 触发父类中handleEvent方法
      this.$emit('handle-event', event, data)
    },
    // 按钮点击事件
    handleClick(event, prop) {
      this.$emit('handle-click', event, prop)
    },
    // singlePop清空回调事件
    handleClear(event, prop) {
      this.form[prop] = undefined
      this.$emit('handle-clear', event, prop)
    },
    // 表单项change事件
    handleEvent(event, data) {
      this.$emit('handle-event', event, data)
    },
    clearValidate() {
      this.$refs.form.clearValidate()
    },
    resetFields() {
      this.$refs.form.resetFields()
    },
    // 表单校验，由父组件调用
    validate() {
      let validFlag
      this.$refs.form.validate((valid, errorFields) => {
        if (!valid) {
          const field = Object.keys(errorFields)[0]
          if (this.$refs[field]) {
            if (this.$refs[field][0].hasOwnProperty('focus')) {
              this.$refs[field][0].focus()
            } else {
              this.$refs[field][0].$el.scrollIntoView()
            }
            document.documentElement.scrollTop = 0
          }
          validFlag = false
          this.$emit('handle-failed')
        } else {
          validFlag = true
          this.$emit('handle-submit')
        }
      })
      return validFlag
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left !important;
}
</style>
