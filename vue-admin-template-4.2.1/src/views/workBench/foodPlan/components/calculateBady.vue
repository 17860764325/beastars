<template>
  <div>
    <el-button type="primary" @click="clean()">清空</el-button>
    <p>基本信息：</p>
    <span style="margin-top: 20px">年龄：</span>
    <el-input-number
        style="width: 200px;margin:10px"
        v-model="basicInfo.age"
        placeholder="年龄"
    >
    </el-input-number>
    <br/>
    <span style="margin-top: 20px">身高(CM)：</span>
    <el-input-number
        style="width: 200px;margin:10px"
        v-model="basicInfo.height"
        placeholder="身高"
    >
    </el-input-number>
    <br/>
    <span style="margin-top: 20px">体重(KG)：</span>
    <el-input-number
        style="width: 200px;margin:10px"
        v-model="basicInfo.heavy"
        placeholder="体重"
    >
    </el-input-number>
    <br/>
    <div style="margin-top: 10px;margin-bottom: 10px">
      <span style="margin-top: 20px">性别：</span>
      <el-radio v-model="basicInfo.sex" label="1">男</el-radio>
      <el-radio v-model="basicInfo.sex" label="0">女</el-radio>
    </div>

    <br/>
    <span style="margin-top: 20px">运动频率：</span>
    <el-select v-model="basicInfo.sportFrequency">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      >
      </el-option>
    </el-select>
    <br/>
    <span style="margin-top: 20px">每天摄入总热量：</span>
    <el-input-number
        style="width: 200px;margin:10px"
        v-model="basicInfo.in"
        placeholder="每天摄入总热量"
    >
    </el-input-number>

    <div>
      <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
      <el-descriptions title="计算数值：" :column="3" border>
        <template slot="extra">
          <el-button type="primary" size="large" @click="calculate('1')">计算</el-button>
        </template>
        <el-descriptions-item label="体质指数：" label-class-name="my-label" content-class-name="my-content">{{
            constitutionNumber
          }}
        </el-descriptions-item>
        <el-descriptions-item label="体脂率：" label-class-name="my-label" content-class-name="my-content">{{ constitution }}%</el-descriptions-item>
      </el-descriptions>
      <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
      <el-descriptions title="每日总热量摄入：" :column="3" border>
        <template slot="extra">
          <el-button type="primary" size="large" @click="calculate('2')">计算</el-button>
        </template>
        <el-descriptions-item label="每日消耗总热量：" label-class-name="my-label" content-class-name="my-content">
          {{ calorieIntake }}
        </el-descriptions-item>
        <el-descriptions-item label="基础代谢：" label-class-name="my-label" content-class-name="my-content">
          {{ basalMetabolism }}
        </el-descriptions-item>
        <el-descriptions-item label="活动消耗热量：" label-class-name="my-label" content-class-name="my-content">{{ activityCalorieConsumption }}
        </el-descriptions-item>
        <el-descriptions-item label="最大心率：" label-class-name="my-label" content-class-name="my-content">{{ maximumHeartRate }}
        </el-descriptions-item>
        <el-descriptions-item label="燃脂心率：" label-class-name="my-label" content-class-name="my-content">{{ fatBurningHeartRate }}
        </el-descriptions-item>
        <el-descriptions-item label="食物热效能：" label-class-name="my-label" content-class-name="my-content">{{ thermalEfficiencyFood }}
        </el-descriptions-item>
      </el-descriptions>
      <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
      <el-descriptions title="食物所需摄入量：" :column="3" border>
        <template slot="extra">
          <el-button type="primary" size="large" @click="calculate('3')">计算</el-button>
        </template>
        <el-descriptions-item label="碳水：" label-class-name="my-label" content-class-name="my-content"
        >{{ carbonWater }}
        </el-descriptions-item>
        <el-descriptions-item label="蛋白：" label-class-name="my-label" content-class-name="my-content">{{ eggWhite }}</el-descriptions-item>
        <el-descriptions-item label="脂肪：" label-class-name="my-label" content-class-name="my-content">{{ fat }}</el-descriptions-item>
      </el-descriptions>
    </div>
    <div>
      <p>每公斤脂肪的热量是7700大卡</p>
      <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="体脂计算公式" name="1">
          <div>体质系数(BMI) = 体重(kg) / 身高平方(m^2)(单位为米)</div>
          <div>体脂率=1.2×BMI+0.23×年龄-5.4-10.8×性别（男为1，女为0）</div>
        </el-collapse-item>
        <el-collapse-item title="每日消耗总热量" name="2">
          <div>每日消耗总热量=基础代谢（占总的60-65%）+活动（运动健身，消耗热量25-35%）+食物热效应（占5-10%）</div>
        </el-collapse-item>
        <el-collapse-item title="基础代谢计算公式(BMR)" name="3">
          <div>男：66+（13.7×体重KG）+（5×身高cm）-（6.8×年龄）</div>
          <div>女：655+（9.6×体重K）+（1.8×身高cm）-（4.7×年龄）</div>
        </el-collapse-item>
        <el-collapse-item title="活动消耗量" name="4">
          <div> 基础代谢 * 运动频率系数</div>
        </el-collapse-item>
        <el-collapse-item title="食物热效能" name="5">
          <div>食物热效应=每日总摄入热量的10%</div>
        </el-collapse-item>
        <el-collapse-item title="最大心率" name="6">
          <div>男生：220-年龄</div>
          <div>女生：226-年龄</div>
        </el-collapse-item>
        <el-collapse-item title="燃脂心率" name="7">
          <div>男生：（220-年龄）×60%和80%</div>
          <div>女生：（226-年龄）×60%和80%</div>
        </el-collapse-item>
        <el-collapse-item title="食物摄入量计算" name="8">
          <div> 碳水：BMR * 60% / 4</div>
          <div> 蛋白：BMR * 20% / 4</div>
          <div> 脂肪：BMR * 20% / 9</div>
        </el-collapse-item>
      </el-collapse>

    </div>
  </div>
</template>

<script>

export default {
  components: {},
  data() {
    return {
      basicInfo: {
        height: 0,
        heavy: 0,
        sex: '',
        age: 0,
        sportFrequency: '',
        in: 0
      },
      options: [
        {
          label: '几乎不运动',
          value: '0.2'
        }, {
          label: '每周1-3次，强度1h之内',
          value: '0.375'
        }, {
          label: '每周3-5次，强度1h左右',
          value: '0.55'
        }, {
          label: '每周5-7次，中高强度1.5h',
          value: '0.7'
        }, {
          label: '每周6-7次，高强度2h',
          value: '0.8'
        }
      ],
      activeNames: [],
      // 体质系数
      constitutionNumber: 0,
      // 体质率
      constitution: 0,
      // 每日热量摄入
      calorieIntake: 0,
      // 基础代谢
      basalMetabolism: 0,
      // 活动消耗热量
      activityCalorieConsumption: 0,
      // 最大心率
      maximumHeartRate: 0,
      // 燃脂心率
      fatBurningHeartRate: 0,
      // 食物热效能
      thermalEfficiencyFood: 0,
      // 碳水
      carbonWater: 0,
      // 蛋白
      eggWhite: 0,
      // 脂肪
      fat: 0
    }
  },
  created() {
  },
  methods: {
    handleChange(val) {
      console.log(val)
    },
    calculate(type) {
      if(this.calibrate()) {
        switch (type) {
          case '1':
            // 体质系数计算
            // eslint-disable-next-line no-case-declarations
            const heightDeal = Number(this.basicInfo.height) / 100
            const value = Number(this.basicInfo.heavy) / (Number(heightDeal) * Number(heightDeal))
            this.constitutionNumber = parseFloat(value).toFixed(2)
            // 体脂率计算 1.2×BMI+0.23×年龄-5.4-10.8×性别（男为1，女为0)
            const otherValue = (1.2 * Number(this.constitutionNumber)) + (0.23 * Number(this.basicInfo.age) - 5.4 - (10.8 * Number(this.basicInfo.sex)))
            this.constitution = parseFloat(otherValue).toFixed(2)
            break
          case '2':
            // 计算基础代谢
            const value1 =
                this.basicInfo.sex === '1' ? 66 + (13.7 * Number(this.basicInfo.heavy)) + (5 * Number(this.basicInfo.height)) - (6.8 * Number(this.basicInfo.age)) : 655 + (9.6 * Number(this.basicInfo.heavy)) + (1.8 * Number(this.basicInfo.height)) - (4.7 * Number(this.basicInfo.age))
            this.basalMetabolism = parseFloat(value1).toFixed(2)
            // 计算活动消耗
            this.activityCalorieConsumption = parseFloat(Number(this.basalMetabolism) * Number(this.basicInfo.sportFrequency)).toFixed(2)
            // 计算食物热效能
            this.thermalEfficiencyFood = parseFloat(Number(this.basicInfo.in) * 0.1).toFixed(2)
            // 计算每日消耗总热量
            this.calorieIntake = parseFloat(Number(this.basalMetabolism) + Number(this.activityCalorieConsumption) + Number(this.thermalEfficiencyFood)).toFixed(2)
            // 计算最大心率
            this.basicInfo.sex === '1' ? this.maximumHeartRate = 220 - Number(this.basicInfo.age) : this.maximumHeartRate = 226 - Number(this.basicInfo.age)
            // 计算燃脂心率
            this.fatBurningHeartRate = `${parseFloat(Number(this.maximumHeartRate) * 0.6).toFixed(2)}~${parseFloat(Number(this.maximumHeartRate) * 0.8).toFixed(2)}`
            break
          case '3':
            // 计算应该摄入碳水
            this.carbonWater = parseFloat(Number(this.basalMetabolism) * 0.6 / 4).toFixed(2)
            // 计算应该摄入蛋白质
            this.eggWhite = parseFloat(Number(this.basalMetabolism) * 0.2 / 4).toFixed(2)
            // 计算应该摄入脂肪
            this.fat = parseFloat(Number(this.basalMetabolism) * 0.2 / 9).toFixed(2)
            break
        }
      }
    },
    clean() {
      this.basicInfo.in = 0
      this.basicInfo.age = 0
      this.basicInfo.height = 0
      this.basicInfo.sex = ''
      this.basicInfo.heavy = 0
      this.basicInfo.sportFrequency = ''
    },
    // 校验基础信息是否都填写了
    calibrate() {
      if (this.basicInfo.sex === '' || this.basicInfo.sportFrequency === '' || this.basicInfo.age === 0 || this.basicInfo.height === 0 || this.basicInfo.heavy === 0 || this.basicInfo.in === 0) {
        this.$message.error('请填写完整信息,才可以计算数据')
        return false
      }
      return true
    }
  }
}
</script>
<style>
.my-label {
  background: green;
  font-size: large;
  color: black;
}

.my-content {
  background: #66c2d5;
  font-size: large;
}
</style>

