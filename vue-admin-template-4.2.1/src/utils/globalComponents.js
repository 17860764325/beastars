/**
 * 全局注册组件
 * 以下直接在template内用即可，不需要再通过import的形式在父组件的components内引用
 */

// 引入组件
import SvFormContainer from '@/components/SvFormContainer/index'
import SvCard from '@/components/SvCard'
import SvTable from '@/components/SvTable/index'
import SvSearchTable from '@/components/SvSearchTable/index'
import SvQuery from '@/components/SvQuery/index'
import SvForm from '@/components/SvForm/index'
import UploadFile from '@/components/Upload/UploadFile'
import ExcelImport from '@/components/ExcelImport'
import PassProgress from '@/components/PassProgress'
import CitySelect from '@/components/CitySelect'
import International from '@/components/International'
import SvInput from '@/components/SvInput/SvInput'
import SvButton from '@/components/SvButton/index'
import SvColumnSelect from '@/components/SvColumnSelect/index'
import SvPdfPreview from '@/components/SvPdfPreview/index'
import SvWorkflow from '@/components/SvWorkflow/index'
import SvPrint from '@/components/SvPrint/index'
import uploadFileMultiple from '@/components/Upload/uploadFileMultiple'
import SvFlowContainer from '@/components/SvWorkflow/SvFlowContainer'

// 注册组件
const plugins = {
  install(Vue) {
    // 原FormButtons，tab页形式表单中取消，提交等步骤按钮组的组件 <sv-form-container></sv-form-container>
    Vue.component('SvFormContainer', SvFormContainer)
    // 对el-card样式的二次封装，不需要重新设置el-card的title样式，直接使用<sv-card></sv-card>
    Vue.component('SvCard', SvCard)
    // 原SvTable，全局通用列表 <sv-table></sv-table>
    Vue.component('SvTable', SvTable)
    // 原SvSearchTable，带高级查询的通用列表<sv-search-table></sv-search-table>
    Vue.component('SvSearchTable', SvSearchTable)
    // 原SvQuery，带查询条件的通用列表<sv-query></sv-query>
    Vue.component('SvQuery', SvQuery)
    // 原SvForm，全局通用表单 <sv-form></sv-form>
    Vue.component('SvForm', SvForm)
    // 原UploadFile，全局通用上传组件<sv-upload-file></sv-upload-file>
    Vue.component('SvUploadFile', UploadFile)
    // 原ExcelImport，全局通用Excel数据导入组件<sv-excel-import></sv-excel-import>
    Vue.component('SvExcelImport', ExcelImport)
    // 原PassProgress，全局通用带强度条的密码输入框组件<sv-pass-progress></sv-pass-progress>
    Vue.component('SvPassProgress', PassProgress)
    // 原CitySelect，全局通用的国家/省/市/区选择组件<sv-city-select></sv-city-select>
    Vue.component('SvCitySelect', CitySelect)
    // 原International，某一字段国际化设置组件，<sv-international></sv-international>
    Vue.component('SvInternational', International)
    // 原SvInput, 查询条件多输入, <sv-input></sv-input>
    Vue.component('SvInput', SvInput)
    // 原SvButton, 点击按钮会触发确认提示框, <sv-button></sv-button>
    Vue.component('SvButton', SvButton)
    // 原SvButton, 点击按钮会触发确认提示框, <sv-column-select></sv-column-select>
    // Vue.component('SvColumnSelect', SvColumnSelect)
    Vue.component('SvPdfPreview', SvPdfPreview)
    Vue.component('SvWorkflow', SvWorkflow)
    Vue.component('SvPrint', SvPrint)
    Vue.component('SvUploadFileMultiple', uploadFileMultiple)
    Vue.component('SvFlowContainer', SvFlowContainer)
  }
}
export default plugins
