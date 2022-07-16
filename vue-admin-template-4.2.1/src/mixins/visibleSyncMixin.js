export default {
  data() {
    return {
      dialogVisible: false
    }
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    visible(value) {
      this.dialogVisible = value
    },
    dialogVisible(value) {
      this.$emit('update:visible', value)
    }
  }
}
