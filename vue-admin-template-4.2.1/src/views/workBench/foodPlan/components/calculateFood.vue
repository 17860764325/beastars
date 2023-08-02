<template>
  <div>
    <div>日期：{{ dateFormat() }}</div>
    <div class="breakfast">
      <p>早餐：</p>
      <foodSelect
          :meals-type="breakfast"
          :type="'1'"
          @total="total"
      ></foodSelect>
    </div>
    <div class="launch">
      <p>午餐：</p>
      <food-select
          :meals-type="launch"
          :type="'2'"
          @total="total"
      ></food-select>
    </div>
    <div class="dinner">
      <p>晚餐：</p>
      <foodSelect
          @total="total"
          :meals-type="dinner"
          :type="'3'"
      >
      </foodSelect>
    </div>
    <p>总计：</p>
    <div style="display: flex;align-items: center;font-size: large">
      <span>蛋白质：<el-tag style="color: #d00707;font-size: large">{{ calculate.protein }}</el-tag>- -</span>
      <span>脂肪：<el-tag style="color: #d00707;font-size: large">{{ calculate.fat }}</el-tag>- -</span>
      <span>碳水：<el-tag style="color: #d00707;font-size: large">{{ calculate.carbonWater }}</el-tag>- -</span>
      <span>膳食纤维：<el-tag style="color: #d00707;font-size: large">{{ calculate.dietaryFiber }}</el-tag></span>
      <span>维生素：<el-tag style="color: #d00707;font-size: large">{{ calculate.starch }}</el-tag></span>
      <span>热量：<el-tag style="color: #d00707;font-size: large">{{ calculate.quantityOfHeat }}</el-tag></span>
    </div>
  </div>
</template>

<script>
import foodSelect from './foodSelect.vue'
import { data } from 'autoprefixer'

export default {
  components: {
    foodSelect
  },
  data() {
    return {
      breakfast: 'breakfast',
      launch: 'launch',
      dinner: 'dinner',
      calculate: {
        protein: 0,
        fat: 0,
        carbonWater: 0,
        dietaryFiber: 0,
        quantityOfHeat: 0,
        starch: 0
      }, calculate1: {
        protein: 0,
        fat: 0,
        carbonWater: 0,
        dietaryFiber: 0,
        quantityOfHeat: 0,
        starch: 0
      }, calculate2: {
        protein: 0,
        fat: 0,
        carbonWater: 0,
        dietaryFiber: 0,
        quantityOfHeat: 0,
        starch: 0
      }, calculate3: {
        protein: 0,
        fat: 0,
        carbonWater: 0,
        dietaryFiber: 0,
        quantityOfHeat: 0,
        starch: 0
      }
    }
  },
  created() {
  },
  methods: {
    data,
    dateFormat() {
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      // var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      // var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      // var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      console.log(year + '-' + month + '-' + day)
      return year + '年' + month + '月' + day + '日'
    },
    total(data) {
      console.log(data)
      if (data.type === '1') {
        this.calculate1 = data
      } else if (data.type === '2') {
        this.calculate2 = data
      } else if (data.type === '3') {
        this.calculate3 = data
      }
      this.calculate.protein = (Number(this.calculate1.protein) + Number(this.calculate2.protein) + Number(this.calculate3.protein)).toFixed(2)
      this.calculate.fat = (Number(this.calculate1.fat) + Number(this.calculate2.fat) + Number(this.calculate3.fat)).toFixed(2)
      this.calculate.carbonWater = (Number(this.calculate1.carbonWater) + Number(this.calculate2.carbonWater) + Number(this.calculate3.carbonWater)).toFixed(2)
      this.calculate.dietaryFiber = (Number(this.calculate1.dietaryFiber) + Number(this.calculate2.dietaryFiber) + Number(this.calculate3.dietaryFiber)).toFixed(2)
      this.calculate.quantityOfHeat = (Number(this.calculate1.quantityOfHeat) + Number(this.calculate2.quantityOfHeat) + Number(this.calculate3.quantityOfHeat)).toFixed(2)
      this.calculate.starch = (Number(this.calculate1.starch) + Number(this.calculate2.starch) + Number(this.calculate3.starch)).toFixed(2)
    }
  }
}
</script>
<style>

</style>

