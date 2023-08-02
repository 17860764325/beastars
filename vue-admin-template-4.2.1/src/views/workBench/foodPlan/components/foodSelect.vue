<template>
  <div style="display: flex;flex-wrap: wrap;justify-content: center">
    <div class="mainPage">
      <div class="foodTypeTitle">
        <i class="el-icon-chicken">蛋白质</i>
        <el-select placeholder="选择食物" v-model="protein" @change="calculateNumber()">
          <el-option
              v-for="item in proteinList"
              :key="item.foodCode"
              :label="item.foodName"
              :value="item.foodCode"
          >
          </el-option>
        </el-select>
        <el-input-number placeholder="数量(g)" v-model="proteinNumber" @change="calculateNumber()"></el-input-number>
      </div>
      <div v-if="mealsType !== 'dinner'" class="foodTypeTitle">
        <i class="el-icon-food">碳水</i>
        <el-select placeholder="选择食物" v-model="carbonWater" @change="calculateNumber()">
          <el-option
              v-for="item in carbonWaterList"
              :key="item.foodCode"
              :label="item.foodName"
              :value="item.foodCode"
          >
          </el-option>
        </el-select>
        <el-input-number placeholder="数量(g)" v-model="carbonWaterNumber" @change="calculateNumber()"
        ></el-input-number>
      </div>
      <div class="foodTypeTitle">
        <i class="el-icon-grape">维生素</i>
        <el-select placeholder="选择食物" v-model="starch" @change="calculateNumber()">
          <el-option
              v-for="item in starchList"
              :key="item.foodCode"
              :label="item.foodName"
              :value="item.foodCode"
          >
          </el-option>
        </el-select>
        <el-input-number placeholder="数量(g)" v-model="starchNumber" @change="calculateNumber()"></el-input-number>
      </div>
      <div class="foodTypeTitle">
        <i class="el-icon-apple">膳食纤维</i>
        <el-select placeholder="选择食物" v-model="dietaryFiber" @change="calculateNumber()">
          <el-option
              v-for="item in dietaryFiberList"
              :key="item.foodCode"
              :label="item.foodName"
              :value="item.foodCode"
          >
          </el-option>
        </el-select>
        <el-input-number placeholder="数量(g)" v-model="dietaryFiberNumber" @change="calculateNumber()"
        ></el-input-number>
      </div>
    </div>
    <div style="display: flex;align-items: center;font-size: large">
      <span>蛋白质：<span style="color: #d00707">{{ calculate.protein }}</span>- -</span>
      <span>脂肪：<span style="color: #d00707">{{ calculate.fat }}</span>- -</span>
      <span>碳水：<span style="color: #d00707">{{ calculate.carbonWater }}</span>- -</span>
      <span>膳食纤维：<span style="color: #d00707">{{ calculate.dietaryFiber }}</span></span>
      <span>维生素：<span style="color: #d00707">{{ calculate.starch }}</span></span>
      <span>热量：<span style="color: #d00707">{{ calculate.quantityOfHeat }}</span></span>
    </div>
  </div>
</template>
<script>
import { getList } from '@/api/food/api'
import item from '@/layout/components/Sidebar/Item.vue'

export default {
  computed: {
    item() {
      return item
    }
  },
  props: {
    mealsType: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      listAll: [],
      starchList: [],
      proteinList: [],
      dietaryFiberList: [],
      carbonWaterList: [],
      starch: '',
      starchNumber: 0,
      protein: '',
      proteinNumber: 0,
      dietaryFiber: '',
      dietaryFiberNumber: 0,
      carbonWater: '',
      carbonWaterNumber: 0,
      calculate: {
        protein: 0,
        fat: 0,
        carbonWater: 0,
        dietaryFiber: 0,
        quantityOfHeat: 0,
        starch: 0,
      }
    }
  },
  async created() {
    // 查询出所有类型的食物放进不同的option
    const res = await getList({})
    console.log(res.data)
    this.listAll = res.data
    // starch 维生素
    // this.starchList = res.data.filter(item => item.foodType === 'starch')
    this.starchList = res.data
    // protein 蛋白质
    // this.proteinList = res.data.filter(item => item.foodType === 'protein')
    this.proteinList = res.data
    // dietary_fiber 膳食纤维
    // this.dietaryFiberList = res.data.filter(item => item.foodType === 'dietary_fiber')
    this.dietaryFiberList = res.data
    // carbon_water 碳水
    // this.carbonWaterList = res.data.filter(item => item.foodType === 'carbon_water')
    this.carbonWaterList = res.data
  },
  methods: {
    calculateNumber() {
      // 判断是否是晚餐，是晚餐的话不计算碳水
      this.calculate.protein = this.deal('protein').toFixed(2)
      this.calculate.starch = this.deal('starch').toFixed(2)
      this.calculate.dietaryFiber = this.deal('dietaryFiber').toFixed(2)
      this.calculate.carbonWater = this.deal('carbonWater').toFixed(2)
      this.calculate.fat = this.deal('fat').toFixed(2)
      this.calculate.quantityOfHeat = this.deal('quantityOfHeat').toFixed(2)
      this.calculate.type = this.type
      this.$emit('total',this.calculate)
    },
    dealBasic(foodCode, type) {
      const data = this.listAll.find(item => item.foodCode === foodCode)
      if (!data) return 0
      switch (type) {
        case 'protein':
          return parseFloat(Number(data.protein) / Number(data.number)).toFixed(2)
        case 'starch':
          return parseFloat(Number(data.starch) / Number(data.number)).toFixed(2)
        case 'fat':
          return parseFloat(Number(data.fat) / Number(data.number)).toFixed(2)
        case 'dietaryFiber':
          return parseFloat(Number(data.dietaryFiber) / Number(data.number)).toFixed(2)
        case 'carbonWater':
          return parseFloat(Number(data.carbonWater) / Number(data.number)).toFixed(2)
        case 'quantityOfHeat':
          return parseFloat(Number(data.quantityOfHeat) / Number(data.number)).toFixed(2)
      }
    },
    // 蛋白质计算
    calculateProtein() {
      const data = (this.proteinNumber * this.dealBasic(this.protein, 'protein') + this.starchNumber * this.dealBasic(this.starch, 'protein') + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'protein') + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'protein'))
      console.log((this.proteinNumber * this.dealBasic(this.protein, 'protein') + '+' + this.starchNumber * this.dealBasic(this.starch, 'protein') + '+' + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'protein') + '+' + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'protein')), '蛋白质计算')
      return data
    },
    // 维生素
    calculateStarch() {
      const data = (this.proteinNumber * this.dealBasic(this.protein, 'starch') + this.starchNumber * this.dealBasic(this.starch, 'starch') + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'starch') + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'starch'))
      console.log(this.proteinNumber * this.dealBasic(this.protein, 'starch') + '+' + this.starchNumber * this.dealBasic(this.starch, 'starch') + '+' + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'starch') + '+' + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'starch'), '维生素计算')
      return data
    },
    // 脂肪计算
    calculateFat() {
      const data = (this.proteinNumber * this.dealBasic(this.protein, 'fat') + this.starchNumber * this.dealBasic(this.starch, 'fat') + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'fat') + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'fat'))
      console.log(this.proteinNumber * this.dealBasic(this.protein, 'fat') + '+' + this.starchNumber * this.dealBasic(this.starch, 'fat') + '+' + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'fat') + '+' + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'fat'), '脂肪计算')
      return data
    },
    // 膳食纤维
    calculateDietaryFiber() {
      const data = (this.proteinNumber * this.dealBasic(this.protein, 'dietaryFiber') + this.starchNumber * this.dealBasic(this.starch, 'dietaryFiber') + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'dietaryFiber') + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'dietaryFiber'))
      console.log(this.proteinNumber * this.dealBasic(this.protein, 'dietaryFiber') + '+' + this.starchNumber * this.dealBasic(this.starch, 'dietaryFiber') + '+' + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'dietaryFiber') + '+' + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'dietaryFiber'), '膳食纤维计算')
      return data
    },
    // 碳水计算
    calculateCarbonWater() {
      const data = (this.proteinNumber * this.dealBasic(this.protein, 'carbonWater') + this.starchNumber * this.dealBasic(this.starch, 'carbonWater') + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'carbonWater') + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'carbonWater'))
      console.log(this.proteinNumber * this.dealBasic(this.protein, 'carbonWater') + '+' + this.starchNumber * this.dealBasic(this.starch, 'carbonWater') + '+' + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'carbonWater') + '+' + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'carbonWater'), '碳水计算')
      return data
    },
    // 热量
    calculateQuantityOfHeat() {
      const data = (this.proteinNumber * this.dealBasic(this.protein, 'quantityOfHeat') + this.starchNumber * this.dealBasic(this.starch, 'quantityOfHeat') + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'quantityOfHeat') + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'quantityOfHeat'))
      console.log(this.proteinNumber * this.dealBasic(this.protein, 'quantityOfHeat') + '+' + this.starchNumber * this.dealBasic(this.starch, 'quantityOfHeat') + '+' + this.dietaryFiberNumber * this.dealBasic(this.dietaryFiber, 'quantityOfHeat') + '+' + this.carbonWaterNumber * this.dealBasic(this.carbonWater, 'quantityOfHeat'), '热量计算')
      return data
    },

    deal(type) {
      switch (type) {
        case 'protein':
          return this.calculateProtein()
        case 'starch':
          return this.calculateStarch()
        case 'fat':
          return this.calculateFat()
        case 'dietaryFiber':
          return this.calculateDietaryFiber()
        case 'carbonWater':
          return this.calculateCarbonWater()
        case 'quantityOfHeat':
          return this.calculateQuantityOfHeat()
      }
    }

  }
}
</script>
<style>
.mainPage {
  display: flex;
}

.foodTypeTitle {
  background-color: #20a0ff;
  border: #039a03 solid 3px;
  border-radius: 10px;
  width: 15%;
  margin-left: 20px;
}
</style>
