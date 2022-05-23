<template>
  <div class="app-container ">
    <quill-editor
      ref="myQuillEditor"
      v-model="this.data.dataAbout"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    />
    <div style="text-align: center">
      <el-button @click="save()">保存</el-button>
    </div>
  </div>
</template>

<script>
// 富文本编辑
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import { quillEditor } from 'vue-quill-editor'
// 保存
import { getInfo, saveInfo } from '@/api/ScheduleItem/api'

export default {
  name: 'Documentation',
  components: {
    quillEditor
  },
  props: {
    id: String
  },
  data() {
    return {
      editorOption: {
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // 加粗，斜体，下划线，删除线
            ['blockquote', 'code-block'], // 引用，代码块
            [{ 'header': 1 }, { 'header': 2 }], // 标题，键值对的形式；1、2表示字体大小
            [{ 'list': 'ordered' }, { 'list': 'bullet' }], // 列表
            [{ 'script': 'sub' }, { 'script': 'super' }], // 上下标
            [{ 'indent': '-1' }, { 'indent': '+1' }], // 缩进
            [{ 'direction': 'rtl' }], // 文本方向
            [{ 'size': ['small', false, 'large', 'huge'] }], // 字体大小
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }], // 几级标题
            [{ 'color': [] }, { 'background': [] }], // 字体颜色，字体背景颜色
            [{ 'font': [] }], // 字体
            [{ 'align': [] }], // 对齐方式
            ['clean'], // 清除字体样式
            ['image', 'video'] // 上传图片、上传视频
          ]
        },
        placeholder: '请讲述这个故事', // 提示
        readyOnly: false, // 是否只读
        theme: 'snow', // 主题 snow/bubble
        syntax: true // 语法检测
      }, // 编辑器配置项
      data: {
        headerId: undefined,
        dataAbout: undefined
      }

    }
  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill
    }
  },
  async created() {
    const res = await getInfo(this.id)
    this.data.dataAbout = res.data.dataAbout
  },
  methods: {
    // 失去焦点
    onEditorBlur(date) {
      console.log(date, 'onEditorBlur')
    },
    // 获得焦点
    onEditorFocus(date) {
      console.log(date, 'onEditorFocus')
    },
    // 值发生变化
    onEditorChange(date) {
      console.log(date.html, 'onEditorChange')
      this.data.dataAbout = date.html
    },
    save() {
      this.data.headerId = this.id
      saveInfo(this.data).then(res => {
        if (res.success) {
          this.$notify({
            title: '成功',
            message: '🥳故事保存成功！',
            type: 'success'
          })
        } else {
          this.$notify({
            title: '失败',
            message: '😵保存失败，未知原因！',
            type: 'error'
          })
        }
      })
    }

  }

}
</script>
