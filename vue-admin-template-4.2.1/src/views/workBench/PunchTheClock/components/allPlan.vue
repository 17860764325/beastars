<template>
  <div>
    <div class="calendar-box">
      <!-- 日历表头 -->
      <div class="calendarTop">
        <div class="calendar-tag" v-for="ite in top" :key="ite">
          <span>{{ `星期` + ite }}</span>
        </div>
      </div>
      <!-- 具体内容 -->
      <div class="calendarBottom">
        <div class="calendar-content">
          <div
            class="calendar-item"
            :class="ite.isToday"
            v-for="(ite, indx) in visibleCalendar"
            :key="indx"
          >
            <!-- 号数 -->
            <div class="calendar-item-number">{{ ite.day }}</div>
            <!-- 号数对应的需要展示的内容 -->
            <div class="calendar-item-content">....</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      top: ['一', '二', '三', '四', '五', '六', '日'],
      time: {
        year: new Date().getFullYear(),
        month: new Date().getMonth()
      }
    }
  },
  // vue computed中需要定义的方法
  computed: {
    visibleCalendar() {
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
        calendarArr.push({
          date: new Date(startDay + i * 24 * 3600 * 1000),
          year: date.getFullYear(),
          month: date.getMonth(),
          day: date.getDate(),
          // 是否在当月
          thisMonth:
            date.getFullYear() === today.getFullYear() &&
            date.getMonth() === today.getMonth()
              ? 'thisMonth'
              : '',
          // 是否是今天
          isToday:
            date.getFullYear() === today.getFullYear() &&
            date.getMonth() === today.getMonth() &&
            date.getDate() === today.getDate()
              ? 'isToday'
              : 'notToday',
          // 是否在今天之后
          afterToday: date.getTime() >= today.getTime() ? 'afterToday' : '',
          // 得到日期字符串，格式 yyyy-MM-dd 00:00:00
          timeStr: date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' 00:00:00',
          // 得到date类型日期
          timeDate: date
        })
      }
      return calendarArr
    }
  },
  created() {
  },
  methods: {}
}
</script>
<style>
// vue style中的样式
.calendar-box {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.calendarTop {
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: space-evenly;
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
}

.calendarBottom {
  width: 100%;
  height: calc(100% - 30px);
}

.calendar-content {
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
}

.calendar-item {
  width: 14%;
  height: 16%;
  display: flex;
}

.calendar-item-number {
  width: 100%;
  height: 20px;
}

.calendar-item-content {
  width: 100%;
  height: calc(100% - 20px);
}

.isToday {
  border: 1px solid #50bfff;
}

</style>

