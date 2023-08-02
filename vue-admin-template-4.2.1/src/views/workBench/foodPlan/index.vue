<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane label="基础代谢计算">
        <calculateBady></calculateBady>
      </el-tab-pane>
      <el-tab-pane label="计算用餐数据">
        <calculateFood></calculateFood>
      </el-tab-pane>
      <el-tab-pane label="食物列表">
        <foodPlanList></foodPlanList>
      </el-tab-pane>
      <el-tab-pane label="排产计划">
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import calculateFood from './components/calculateFood.vue'
import calculateBady from './components/calculateBady.vue'
import foodPlanList from './components/foodPlanList.vue'

export default {
  components: {
    calculateFood,
    foodPlanList,
    calculateBady
  },
  data() {
    return {
      breakfast: 'breakfast',
      launch: 'launch',
      dinner: 'dinner'
    }
  },
  created() {
  },
  methods: {}
}
</script>
<style>

</style>

