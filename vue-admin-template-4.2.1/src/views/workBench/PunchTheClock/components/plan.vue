<template>
  <div>
    <div>
      <el-select v-model:value="type">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button type="primary" @click="visibleCalendar()">生成默认计划</el-button>
      <el-button type="primary" @click="init()">刷新</el-button>
    </div>
    <div style="display: flex;flex-wrap: wrap;margin-top: 20px">
      <div class="calendarTop">
        <div class="calendar-tag" v-for="ite in top" :key="ite">
          <span>{{ `星期` + ite }}</span>
        </div>
      </div>
      <!--  写一个日历的展示和编辑-->
      <div class="DateTop">
        <div style="  width: 14%;" v-for="(item, index) in list">
          <div :class="modalStyle(item)">
            <p class="font">日期：{{ item.currentDateToday }}</p>
            <p class="font">{{ item.isFinishTrain === '1' ? '✅完成' : '❌未完成' }}</p>
            <p class="font">{{ item.isPunchClock === '1' ? '✅打卡' : '❌未打卡' }}</p>
            <p class="font">{{ item.isTrain === '1' ? '需要训练' : '不需要训练' }}</p>
            <a class="fontButton" type="text" @click="detail(item,'detail')">详情</a>
            <a class="fontButton" type="text" @click="detail(item,'edit')">编辑</a>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import { addObj, getList } from '@/api/punchTheClock/api.js'

export default {
  data() {
    return {
      time: {
        year: new Date().getFullYear(),
        month: new Date().getMonth()
      },
      top: ['一', '二', '三', '四', '五', '六', '日'],
      // 坚持类型选项
      options: [
        {
          label: '背单词',
          value: '3'
        },
        {
          label: '运动',
          value: '1'
        },
        {
          label: '学习',
          value: '2'
        }
      ],
      // 选择类型值
      type: '',
      // 日历列表
      list: [],
      classChange: true
    }
  },
  computed: {
    // visibleCalendar() {
    //   console.log(this.time.year, this.time.month)
    //   // 获取今天的日期并将时间设置到 0分0秒0点
    //   const today = new Date()
    //   today.setHours(0)
    //   today.setMinutes(0)
    //   today.setSeconds(0)
    //   today.setMilliseconds(0)
    //
    //   const calendarArr = []
    //   // 获取当前月份第一天
    //   const currentFirstDay = new Date(this.time.year, this.time.month, 1)
    //   // 获取第一天是周几，注意周日的时候getDay()返回的是0，要做特殊处理
    //   const weekDay =
    //     currentFirstDay.getDay() === 0 ? 7 : currentFirstDay.getDay()
    //
    //   // 用当前月份第一天减去周几前面几天，就是看见的日历的第一天
    //   const startDay = currentFirstDay - (weekDay - 1) * 24 * 3600 * 1000
    //   // 我们统一用42天来显示当前显示日历
    //   for (let i = 0; i < 42; i++) {
    //     let flag = true
    //     if (i === 2) {
    //       flag = false
    //     }
    //     const date = new Date(startDay + i * 24 * 3600 * 1000)
    //     calendarArr.push({
    //       date: new Date(startDay + i * 24 * 3600 * 1000),
    //       year: date.getFullYear(),
    //       month: date.getMonth(),
    //       flag: flag,
    //       day: date.getDate(),
    //       // 是否在当月
    //       thisMonth:
    //         date.getFullYear() === today.getFullYear() &&
    //         date.getMonth() === today.getMonth()
    //           ? 'thisMonth'
    //           : '',
    //       // 是否是今天
    //       isToday:
    //         date.getFullYear() === today.getFullYear() &&
    //         date.getMonth() === today.getMonth() &&
    //         date.getDate() === today.getDate()
    //           ? 'isToday'
    //           : 'notToday',
    //       // 是否在今天之后
    //       afterToday:
    //         date.getTime() >= today.getTime() ? 'afterToday' : '',
    //       // 得到日期字符串，格式 yyyy-MM-dd 00:00:00
    //       timeStr:
    //         date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' 00:00:00',
    //       // 得到date类型日期
    //       timeDate:
    //       date
    //     })
    //   }
    //   // 每个月月初存储在数据库一份然后根据数据库的数据进行渲染，完成日期的请持久化
    //   return calendarArr
    // }
  },
  created() {
   this.init()
  },
  methods: {
    init()
    {
      console.log(this.time.month)
      const dateS = this.getWeekDataList(this.getCurMonthFirstOrLast(0))
      const dateE = this.getWeekDataList(this.getCurMonthFirstOrLast(1))
      console.log(dateS)
      console.log(dateE)
      const query = {
        currentDateStart: dateS[0] + ' 00:00:00',
        currentDateEnd: dateE[6] + ' 23:59:59'
      }

      getList(query).then(res => {
        this.list = res.data
      })
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
    },
    getCurMonthFirstOrLast(type) {
      let date = new Date
      let y = date.getFullYear().toString()
      let m = (date.getMonth() + 1).toString().padStart(2, 0)
      let d = (['1', new Date(y, m, 0).getDate(), date.getDate()][type]).toString().padStart(2, 0)
      return [y, m, d].join('-')
    },
    getWeekDataList(data) {
      let oneDayTime = 1000 * 60 * 60 * 24
      let today = new Date(data)
      let todayDay = today.getDay() || 7
      let startDate = new Date(
          today.getTime() - oneDayTime * (todayDay - 1)
      )
      let dateList = []
      for (let i = 0; i < 7; i++) {
        let temp = new Date(startDate.getTime() + i * oneDayTime)
        let year = temp.getFullYear()
        let month = temp.getMonth() + 1 < 10 ? '0' + (temp.getMonth() + 1) : temp.getMonth() + 1
        let day = temp.getDate() < 10 ? '0' + temp.getDate() : temp.getDate()
        dateList[i] = `${year}-${month}-${day}`
      }
      return dateList
    },
    visibleCalendar() {
      // 首先判断改曰生成了没有？
      if (this.list.length !== 0 && ((new Date().getMonth() + 1 ) + '' === this.list[12].month + '')){
        this.$message({
          message: '该月已经生成了',
          type: 'error'
        })
        return
      }
      console.log(this.time.year, this.time.month)
      // 获取今天的日期并将时间设置到 0分0秒0点
      const today = new Date()
      today.setHours(0)
      today.setMinutes(0)
      today.setSeconds(0)
      today.setMilliseconds(0)

      const calendarArr = []
      // 获取当前月份第一天
      const currentFirstDay = new Date(this.time.year, this.time.month, 1)
      // 获取第一天是周几，注意周日的时候getDay()返回的是0，要做特殊处理
      const weekDay =
          currentFirstDay.getDay() === 0 ? 7 : currentFirstDay.getDay()

      // 用当前月份第一天减去周几前面几天，就是看见的日历的第一天
      const startDay = currentFirstDay - (weekDay - 1) * 24 * 3600 * 1000
      // 我们统一用42天来显示当前显示日历
      for (let i = 0; i < 42; i++) {
        const date = new Date(startDay + i * 24 * 3600 * 1000)
        const intii = i % 2 === 0 ? '1' : '0'
        calendarArr.push({
          currentDateToday: date.getFullYear()+ '-' + (date.getMonth() + 1) + '-' + date.getDate(),
          year: date.getFullYear(),
          month: date.getMonth() + 1,
          day: date.getDate() ,
          isTrain: intii,
          isPunchClock: '0',
          isFinishTrain: i % 2 !== 0 ? '1' : '0',
          // 是否在当月
          thisMonth:
              date.getFullYear() === today.getFullYear() &&
              date.getMonth() +1  === today.getMonth() +1 ? 'thisMonth' : '',
          // 是否是今天
          isToday:
              date.getFullYear() === today.getFullYear() &&
              date.getMonth() === today.getMonth() &&
              date.getDate()  === today.getDate() ? 'isToday' : 'notToday',
          // 是否在今天之后
          afterToday:
              date.getTime() >= today.getTime() ? 'afterToday' : '',
          // 得到日期字符串，格式 yyyy-MM-dd 00:00:00
          timeStr:
              date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' 00:00:00',
          // 得到date类型日期
          timeDate:
          date
        })
      }
      // 每个月月初存储在数据库一份然后根据数据库的数据进行渲染，完成日期的请持久化

      addObj(calendarArr).then(res => {
        this.$message({
          message: res.data,
          type: res.success ? 'success' : 'error'
        })
        this.init()
      })
    },
    detail(item, type) {
      switch (type) {
        case 'detail':
          this.$message({
            message: '详情' + item.day + '日',
            type: 'success'
          })
          break
        case 'edit':
          this.$message({
            message: '编辑' + item.day + '日',
            type: 'success'
          })
          break
      }
    },
    modalStyle(data) {
      // 先判断是否是当前日期
      console.log(new Date().getDate())
      console.log(new Date().getMonth())
      if (data.day + '' === new Date().getDate() + '' && data.month + '' === (new Date().getMonth() + 1) + '') {
        if (data.isFinishTrain === '0' && data.isTrain === '1') {
          return 'itemNoCurrent'
        } else {
          return 'itemOkCurrent'
        }
      } else {
        if (data.month + '' !== (new Date().getMonth() + 1) + '') {
          return 'NotThisMonth'
        } else {
          if (data.isFinishTrain === '0' && data.isTrain === '1') {
            return 'itemNo'
          } else {
            return 'itemOk'

          }
        }
      }
    }
  }
}
</script>

<style scoped>
.itemNo {
  background-color: #EE3B3B;
  width: 80%;
  height: 80%;
  border: 1px solid #000;
  border-radius: 5px;

}

.itemOk {
  background-color: #9AFF9A;
  width: 80%;
  height: 80%;
  border: 1px solid #000;
  border-radius: 5px;

}

.itemNoCurrent {
  background-color: #EE3B3B;
  width: 80%;
  height: 80%;
  border: 1px solid #000;
  border-radius: 5px;

}

.itemOkCurrent {
  background-color: #9AFF9A;
  width: 80%;
  height: 80%;
  border: 4px solid orange;
  border-radius: 5px;
}

.NotThisMonth {
  background-color: gray;
  width: 80%;
  height: 80%;
  display: none;
  border: 1px solid #000;
  border-radius: 5px;
}

.font {
  font-size: 8px;
  text-align: center;
}

.fontButton {
  font-size: 8px;
  text-align: center;
  color: #20a0ff;
}

.calendarTop {
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: space-evenly;
}

.DateTop {
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly
}

.calendar-tag {
  width: 14%;
  height: 30px;
}

.calendar-tag span {
  background-color: rgb(205, 237, 248);
  width: 100%;
  height: 30px;
  font-weight: bold;
  border: pink 3px solid;
  border-radius: 5px;
}
</style>
