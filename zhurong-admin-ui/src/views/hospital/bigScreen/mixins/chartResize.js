/**
 * 图表容器尺寸自适应 mixin。
 * 监听图表挂载容器（$refs.chart）的尺寸变化并调用 chart.resize()，
 * 覆盖仅监听 window resize 时容器因布局调整（如 KPI 数据加载后列高压缩）
 * 而变化、但窗口未触发 resize 导致画布尺寸滞留的场景。
 */
export default {
  mounted() {
    if (typeof ResizeObserver === 'undefined' || !this.$refs.chart) return
    this.chartRO = new ResizeObserver(() => {
      if (this.chart) this.chart.resize()
    })
    this.chartRO.observe(this.$refs.chart)
  },
  beforeDestroy() {
    if (this.chartRO) {
      this.chartRO.disconnect()
      this.chartRO = null
    }
  }
}
