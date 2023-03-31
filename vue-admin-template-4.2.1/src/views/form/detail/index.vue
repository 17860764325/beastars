<template>
  <div class="app-container">
    <el-button
      type="primary"
      style="width: 15%"
      @click="cancel()"
      icon="el-icon-d-arrow-left"
      >返回</el-button
    >
    <!-- 图片上传组件辅助，组件内添加v-show=“false”属性，把该组件隐藏起来。-->
    <el-upload
      class="avatar-uploader"
      action="http://localhost:9528/api/scheduleItem/uploadImage"
      name="img"
      :show-file-list="false"
      :on-success="uploadSuccess"
      :before-upload="beforeUpload"
      :headers="headers"
    />
    <!-- 为什么是绿色的背景色，因为护眼！ -->
    <quill-editor
      ref="myQuillEditor"
      v-model="data.dataAbout"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    />
    <div style="text-align: right; margin-top: 20px">
      <el-button
        type="success"
        style="width: 15%"
        @click="save()"
        icon="el-icon-check"
        >保存</el-button
      >
    </div>
  </div>
</template>

<script>
// 富文本编辑
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import Cookies from 'js-cookie'
import imageResize from 'quill-image-resize-module' // 富文本实现图片编辑功能
import { ImageDrop } from 'quill-image-drop-module' // 图片拖动组件引用
import Quill from 'quill'
import { quillEditor } from 'vue-quill-editor'
Quill.register('modules/imageDrop', ImageDrop) // 图片拖动组件引用
Quill.register('modules/imageResize', imageResize) // 富文本实现图片编辑功能

// 保存
import { getInfo, saveInfo } from '@/api/ScheduleItem/api'
export default {
  name: 'Documentation',
  components: {
    quillEditor
  },
  props: {
    id: {
      type: Number
    }
  },
  data() {
    return {
      editorOption: {
        placeholder: '请讲述这个故事', // 提示
        readyOnly: false, // 是否只读
        theme: 'snow', // 主题 snow/bubble
        syntax: true, // 语法检测
        modules: {
          history: {
            delay: 1000,
            maxStack: 50,
            userOnly: false
          },
          imageDrop: true,
          imageResize: {
            displayStyles: {
              backgroundColor: 'black',
              border: 'none',
              color: 'white'
            },
            modules: ['Resize', 'DisplaySize', 'Toolbar']
          },
          toolbar: {
            container: [
              ['bold', 'italic', 'underline', 'strike'], // 加粗，斜体，下划线，删除线
              ['blockquote', 'code-block'], // 引用，代码块
              [{ header: 1 }, { header: 2 }], // 标题，键值对的形式；1、2表示字体大小
              [{ list: 'ordered' }, { list: 'bullet' }], // 列表
              [{ script: 'sub' }, { script: 'super' }], // 上下标
              [{ indent: '-1' }, { indent: '+1' }], // 缩进
              [{ direction: 'rtl' }], // 文本方向
              [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
              [{ header: [1, 2, 3, 4, 5, 6, false] }], // 几级标题
              [{ color: [] }, { background: [] }], // 字体颜色，字体背景颜色
              [{ font: [] }], // 字体
              [{ align: [] }], // 对齐方式
              ['clean'], // 清除字体样式
              ['image', 'video'] // 上传图片、上传视频
            ],
            handlers: {
              image: function (value) {
                if (value) {
                  // 调用element的图片上传组件
                  document.querySelector('.avatar-uploader input').click()
                } else {
                  this.quill.format('image', false)
                }
              }
            }
          }
        } // 编辑器配置项
      },
      data: {
        headerId: undefined,
        dataAbout: undefined
      },
      headers: undefined
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
    // 上传照片需要获取token携带token才能上传
    this.token = Cookies.get('Admin-Token')
    this.headers = { token: this.token }
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
    beforeUpload(file) {},
    uploadSuccess(res) {
      // 获取富文本组件实例
      const quill = this.$refs.myQuillEditor.quill
      // 如果上传成功
      if (res) {
        // 获取光标所在位置
        const length = quill.getSelection().index
        // 插入图片，res为服务器返回的图片链接地址
        console.log(res.data, '-----------')
        quill.insertEmbed(length, 'image', res.data)
        // 调整光标到最后
        quill.setSelection(length + 1)
      } else {
        // 提示信息，需引入Message
        this.$message.error('图片插入失败！')
      }
    },
    save() {
      this.data.headerId = this.id
      saveInfo(this.data).then((res) => {
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
    },
    cancel() {
      this.$router.push({ name: 'index' })
    }
  }
}
</script>
