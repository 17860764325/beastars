<template>
  <div>
    <el-transfer
      v-model="value"
      :data="data"
      :titles="['所有角色', '已分配角色']"
    ></el-transfer>
    <div style="text-align: center; margin-top: 20px">
      <el-button type="primary" style="width: 45%" @click="cancel()"
        >取消</el-button
      >
      <el-button type="primary" style="width: 45%" @click="savecharacter()"
        >保存</el-button
      >
    </div>
  </div>
</template>
<script>
import {
  getAllCharacter,
  getCharacterByUserId,
  saveCharacter
} from '@/api/character/api.js'
export default {
  props: {
    item: Object
  },
  data() {
    return {
      // 左边数据
      data: [],
      // 右边数据
      value: []
    }
  },
  async created() {
    // 写两个接口，一个是所有的角色
    await getAllCharacter().then((res) => {
      res.data.forEach((element) => {
        this.data.push({
          key: element.id,
          label: element.roleName
        })
      })
    })
    // 一个是该用户所拥有的角色
    await getCharacterByUserId(this.item.userid).then((res) => {
      res.data.forEach((element) => {
        this.value.push(element.id)
      })
    })
  },
  methods: {
    async savecharacter() {
      console.log(this.value)
      // 循环选中的权限，有几个角色就生成几个对象
      await saveCharacter({ userId: this.item.userid, roles: this.value }).then(
        (res) => {
          if (res.status === 200) {
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success'
            })
          } else {
            this.$notify({
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
          this.cancel()
        }
      )
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>