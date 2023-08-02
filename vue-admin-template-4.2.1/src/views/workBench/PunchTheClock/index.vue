<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane label="打卡中心">
        <ClockCenter></ClockCenter>
      </el-tab-pane>
      <el-tab-pane label="我的计划">我的计划</el-tab-pane>
      <el-tab-pane label="计划排期">
        <plan></plan>
      </el-tab-pane>
      <el-tab-pane label="计划变更">计划变更</el-tab-pane>
      <el-tab-pane label="整体数据预览">
        <allPlan></allPlan>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import ClockCenter from '@/views/workBench/PunchTheClock/components/ClockCenter.vue'
import plan from '@/views/workBench/PunchTheClock/components/plan.vue'
import allPlan from '@/views/workBench/PunchTheClock/components/allPlan.vue'

export default {
  name: 'index',
  components: {
    ClockCenter,
    plan,
    allPlan
  }
}
</script>

<style scoped>

</style>
