<template>
  <div class="chart-box">
    <div ref="chart" class="chart" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
import chartResize from '../mixins/chartResize'

export default {
  name: 'AreaCompareChart',
  mixins: [chartResize],
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
      const list = (this.data || []).filter(i => Number(i.kwh || 0) > 0)
      const names = list.map(i => i.dimName)
      const values = list.map(i => Number(i.kwh || 0))
      this.chart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'shadow' },
          backgroundColor: 'rgba(9, 26, 62, 0.85)',
          borderColor: 'rgba(64, 158, 255, 0.4)',
          textStyle: { color: '#eaf6ff' },
          formatter: p => p[0].name + '<br/>用电量: ' + Number(p[0].value).toFixed(1) + ' kWh'
        },
        grid: { left: '2%', right: '6%', top: 10, bottom: 8, containLabel: true },
        xAxis: {
          type: 'value',
          axisLabel: { color: '#9fc4ea' },
          splitLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.12)' }}
        },
        yAxis: {
          type: 'category',
          data: names,
          axisLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.25)' }},
          axisLabel: { color: '#cfdeee', fontSize: 13 },
          axisTick: { show: false }
        },
        series: [{
          type: 'bar',
          data: values.map(v => ({
            value: v,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                { offset: 0, color: 'rgba(99, 224, 160, 0.9)' },
                { offset: 1, color: 'rgba(89, 198, 255, 0.35)' }
              ]),
              borderRadius: [0, 4, 4, 0]
            }
          })),
          barWidth: 16,
          label: {
            show: true,
            position: 'right',
            color: '#63e0a0',
            fontSize: 12,
            formatter: p => Number(p.value).toFixed(0)
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
