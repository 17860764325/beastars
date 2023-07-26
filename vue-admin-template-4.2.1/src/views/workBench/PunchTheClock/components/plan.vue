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
    </div>
    <div style="display: flex;flex-wrap: wrap;margin-top: 20px">
      <!--  写一个日历的展示和编辑-->
      <div :class="modalStyle(item)" v-for="(item, index) in list">
        <p class="font">日期：{{ item.data }}</p>
        <p class="font">{{ item.flag ? '✅完成' : '❌未完成' }}</p>
        <p class="font">{{ item.flag ? '需要训练' : '不需要训练' }}</p>
        <a class="fontButton" type="text" @click="detail(item)">详情</a>
        <a class="fontButton" type="text" @click="detail(item)">编辑</a>
      </div>
    </div>
  </div>

</template>

<script>

export default {
  data() {
    return {
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
  created() {
    for (let i = 0; i < 31; i++) {
      // if (i % 2 === 0) {
      //   this.list.push({
      //     data: i + 1,
      //     flag: true
      //   })
      // } else {
      //   this.list.push({
      //     data: i + 1,
      //     flag: false
      //   })
      // }
      if (i === 2) {
        this.list.push({
          data: i + 1,
          flag: false
        })
      } else {
        this.list.push({
          data: i + 1,
          flag: true
        })
      }
    }
  },
  methods: {
    detail(item) {
      console.log(item)
    },
    modalStyle(data) {
      // 先判断是否是当前日期
      if (data.data === new Date().getDate()) {
        if (data.flag) {
          return 'itemOkCurrent'
        } else {
          return 'itemNoCurrent'
        }
      } else {
        if (data.flag) {
          return 'itemOk'
        } else {
          return 'itemNo'
        }
      }
    }
  }
}
</script>

<style scoped>
.itemNo {
  background-color: #EE3B3B;
  width: 100px;
  height: 100px;
  border: 1px solid #000;
  border-radius: 5px;
  margin: 3px;
}

.itemOk {
  background-color: #9AFF9A;
  width: 100px;
  height: 100px;
  border: 1px solid #000;
  border-radius: 5px;
  margin: 3px;
}

.itemNoCurrent {
  background-color: #EE3B3B;
  width: 100px;
  height: 100px;
  border: 1px solid #000;
  border-radius: 5px;
  margin: 3px;
}

.itemOkCurrent {
  background-color: #9AFF9A;
  width: 100px;
  height: 100px;
  border: 4px solid orange;
  border-radius: 5px;
  margin: 3px;
}

.font {
  font-size: 8px;
  text-align: center;
}

.fontButton {
  font-size: 8px;
  text-align: center;
  color: #20a0ff;
  margin-left: 5px;
}
</style>
