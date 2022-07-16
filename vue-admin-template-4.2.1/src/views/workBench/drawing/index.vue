<template>
  <div class="draw">
    <el-row class="row">
      <el-col class="col">
        <el-card>
          <h1>人体🧬🩻</h1>
          <el-button type="success" round>新增图纸</el-button>
        </el-card>
      </el-col>
      <el-col class="col">
        <el-card>
          <h1>化学⚗️🧪🥼</h1>
          <el-button type="success" round>新增图纸</el-button>

        </el-card>
      </el-col>
      <el-col class="col">
        <el-card>
          <h1>物理⚛️🔭</h1>
          <el-button type="success" round>新增图纸</el-button>

        </el-card>
      </el-col>
      <el-col class="col">
        <el-card>
          <h1>计算机💻</h1>
          <el-button type="success" round>新增图纸</el-button>

        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>

export default ({
  data() {
    return {
      property: 'value'
    }
  }
})
</script>
<style>
.row{
display: flex;
}
.col{
  padding: 10px;
  margin: 10px;
}
.draw{
  background-image: url(../../../assets/draw/tuzhi3.webp);
}
</style>
