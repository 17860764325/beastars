import dialogCloseMixin from './dialogCloseMixin'
export default {
  data() {
    return {
      tableHeight: document.documentElement.clientHeight * 0.6
    }
  },
  mixins: [dialogCloseMixin],
  watch: {
    $route() {
      this.initTableHeight()
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initTableHeight()
    })
    window.addEventListener('resize', () => {
      this.initTableHeight()
    })
  },
  methods: {
    initTableHeight() {
      const fullHeight = document.documentElement.clientHeight
      const fullWidth = document.body.clientWidth
      const filterContainer = document.querySelector('.filter-container')
      if (filterContainer && fullWidth > 768) {
        const tableHeight = fullHeight - filterContainer.clientHeight - 190
        this.tableHeight = tableHeight > 200 ? tableHeight : 200
      } else {
        this.tableHeight = fullHeight * 0.6
      }
    },
    saveHeaderWidth(newWidth, oldWidth, column) {
      localStorage.setItem(this.componentName + '-' + column.property, newWidth)
    },
    columnWidth(column, defaultWidth) {
      const width = localStorage.getItem(this.componentName + '-' + column)
      if (!width) return defaultWidth
      return width
    }
  }
}
