<template>
  <div style="display: flex">
    <el-col style="display: flex;justify-content: center;align-items: center;" :span="6">
      <el-card style="height: 100%">
        <p>流程：</p>
        <el-timeline>
          <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :icon="activity.icon"
              :type="activity.type"
              :color="activity.color"
              :size="activity.size"
              :timestamp="activity.timestamp"
          >
            {{ activity.content }}
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-col>
    <el-col style="display: flex;flex-direction: column">
      <el-card>
        <p>打卡：</p>
        <el-button style="height: 200px;width: 200px;font-size: 30px;" :type="type" :disabled="flag"
                   @click="buttonChange" circle
        >{{ flag ? '完成打卡✅' : '立即打卡' }}
        </el-button>
        <h3 style="color: red">检测您已经打卡但是还没有提交证明，请尽快提交证明！</h3>
      </el-card>
      <el-card>
        <p>上传证明：</p>
        <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img
                class="el-upload-list__item-thumbnail"
                :src="file.url" alt=""
            >
            <span class="el-upload-list__item-actions">
        <span
            class="el-upload-list__item-preview"
            @click="handlePictureCardPreview(file)"
        >
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleDownload(file)"
        >
          <i class="el-icon-download"></i>
        </span>
        <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleRemove(file)"
        >
          <i class="el-icon-delete"></i>
        </span>
      </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-card>

    </el-col>
  </div>
</template>

<script>
import { getList, editObj } from '@/api/punchTheClock/api.js'
import moment from 'moment'

export default {
  data() {
    return {
      type: 'primary',
      flag: false,
      reverse: true,
      activities: [{
        content: '打卡',
        timestamp: '未打卡',
        ize: 'large',
        type: 'danger',
        icon: 'el-icon-circle-close'
      },
        {
          content: '上传证明',
          timestamp: '未上传',
          size: 'large',
          type: 'danger',
          icon: 'el-icon-circle-close'
        }],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false
    }
  },
  created() {
    this.init()
  },
  methods: {
    init(){
      // 默认加载当天的打卡情况
      const query = {
        currentDateStart: this.dateFormat() + ' 00:00:00',
        currentDateEnd: this.dateFormat() + ' 23:59:59'
      }
      getList(query).then(res => {
        // 判断流程到第几步骤了
        // 判断是否打卡
        if (res.data[0].isPunchClock === '1') {
          this.flag = true
          this.type = 'success'
          if (res.data[0].pictureAddressId !== '' && res.data[0].pictureAddressId !== null && res.data[0].pictureAddressId !== undefined) {
            this.activities = []
            this.activities.push({
                  content: '打卡',
                  timestamp: res.data[0].punchClockTime,
                  size: 'large', // normal / large
                  type: 'success', // primary / success / warning / danger / info
                  icon: 'el-icon-circle-check',
                  color: '#0bbd87'
                },
                {
                  content: '上传证明',
                  timestamp: res.data[0].punchClockTime,
                  ize: 'large',
                  type: 'success',
                  icon: 'el-icon-circle-check'
                })
          } else {
            this.activities = []
            this.activities.push({
                  content: '打卡',
                  timestamp: res.data[0].punchClockTime,
                  size: 'large', // normal / large
                  type: 'success', // primary / success / warning / danger / info
                  icon: 'el-icon-circle-check',
                  color: '#0bbd87'
                },
                {
                  content: '上传证明',
                  timestamp: '未上传',
                  ize: 'large',
                  type: 'danger',
                  icon: 'el-icon-circle-close'
                })
          }
        } else {
          this.flag = false
          this.type = 'primary'
          this.activities = []
          this.activities.push({
                content: '打卡',
                timestamp: '未打卡',
                ize: 'large',
                type: 'danger',
                icon: 'el-icon-circle-close'
              },
              {
                content: '上传证明',
                timestamp: '未上传',
                size: 'large',
                type: 'danger',
                icon: 'el-icon-circle-close'
              })
        }

      })
    },
    buttonChange() {
      // 将打卡状态传输给后端
      editObj({
        isPunchClock: '1',
        isFinishTrain: '1',
        punchClockTime: moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
        currentDateToday: this.dateFormat()
      }).then(res => {
        if (res.success) {
          this.flag = true
          this.type = 'success'
          this.activities = [{
            content: '打卡',
            timestamp: res.data.punchClockTime,
            size: 'large', // normal / large
            type: 'success', // primary / success / warning / danger / info
            icon: 'el-icon-circle-check',
            color: '#0bbd87'
          }, {
            content: '上传证明',
            timestamp: '2018-04-13',
            ize: 'large',
            type: 'danger',
            icon: 'el-icon-circle-close'
          }]
        }else{
          this.$message({
            message: res.message,
            type: 'error'
          })
        }
        this.init()
      })
    },
    handleRemove(file) {
      console.log(file)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleDownload(file) {
      console.log(file)
    },
    dateFormat() {
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      // var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      // var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      console.log(year + '-' + month + '-' + day)
      return year + '-' + month + '-' + day
    }
  }
}
</script>

<style scoped>

</style>
