<template>
  <div class="chart-box">
    <div ref="chart" class="chart" />
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'PowerLineChart',
  props: {
    data: { type: Array, default: () => [] }
  },
  data() {
    return { chart: null }
  },
  watch: {
    data() {
      this.render()
    }
  },
  mounted() {
    this.chart = echarts.init(this.$refs.chart)
    this.render()
    this.resizeHandler = () => this.chart && this.chart.resize()
    window.addEventListener('resize', this.resizeHandler)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeHandler)
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },
  methods: {
    render() {
      if (!this.chart) return
      const list = this.data || []
      const labels = list.map(i => i.label)
      const power = list.map(i => Number(i.avgPower || 0))
      this.chart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(9, 26, 62, 0.85)',
          borderColor: 'rgba(64, 158, 255, 0.4)',
          textStyle: { color: '#eaf6ff' },
          formatter: p => p[0].name + '<br/>平均功率: ' + Number(p[0].value).toFixed(2) + ' kW'
        },
        grid: { left: '4%', right: '4%', top: '12%', bottom: '8%', containLabel: true },
        xAxis: {
          type: 'category',
          data: labels,
          boundaryGap: false,
          axisLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.3)' }},
          axisLabel: { color: '#9fc4ea', fontSize: 12 }
        },
        yAxis: {
          type: 'value',
          name: '功率(kW)',
          nameTextStyle: { color: '#8fb7e0' },
          axisLabel: { color: '#9fc4ea' },
          splitLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.12)' }}
        },
        series: [{
          type: 'line',
          data: power,
          smooth: true,
          symbol: 'none',
          lineStyle: {
            width: 2.5,
            color: '#59c6ff',
            shadowColor: 'rgba(89, 198, 255, 0.5)',
            shadowBlur: 10
          },
          itemStyle: { color: '#59c6ff' },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(89, 198, 255, 0.28)' },
              { offset: 1, color: 'rgba(89, 198, 255, 0)' }
            ])
          }
        }]
      }, true)
    }
  }
}
</script>

<style lang="scss" scoped>
.chart-box, .chart {
  width: 100%;
  height: 100%;
}
</style>
