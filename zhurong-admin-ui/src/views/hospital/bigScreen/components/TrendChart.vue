<template>
  <div class="chart-box">
    <div ref="chart" class="chart" />
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'TrendChart',
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
      const kwh = list.map(i => Number(i.kwh || 0))
      const avgPower = list.map(i => Number(i.avgPower || 0))
      this.chart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(9, 26, 62, 0.85)',
          borderColor: 'rgba(64, 158, 255, 0.4)',
          textStyle: { color: '#eaf6ff' }
        },
        legend: {
          textStyle: { color: '#9fc4ea' },
          top: 6
        },
        grid: { left: '4%', right: '5%', top: '16%', bottom: '8%', containLabel: true },
        xAxis: {
          type: 'category',
          data: labels,
          boundaryGap: true,
          axisLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.3)' }},
          axisLabel: { color: '#9fc4ea', fontSize: 13 }
        },
        yAxis: [
          {
            type: 'value',
            name: '用电量(kWh)',
            nameTextStyle: { color: '#8fb7e0' },
            axisLabel: { color: '#9fc4ea' },
            splitLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.12)' }}
          },
          {
            type: 'value',
            name: '平均功率(kW)',
            nameTextStyle: { color: '#8fb7e0' },
            axisLabel: { color: '#9fc4ea' },
            splitLine: { show: false }
          }
        ],
        series: [
          {
            name: '用电量(kWh)',
            type: 'bar',
            data: kwh,
            barWidth: '45%',
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(89, 198, 255, 0.85)' },
                { offset: 1, color: 'rgba(47, 127, 224, 0.35)' }
              ]),
              borderRadius: [4, 4, 0, 0]
            }
          },
          {
            name: '平均功率(kW)',
            type: 'line',
            data: avgPower,
            yAxisIndex: 1,
            smooth: true,
            symbol: 'circle',
            symbolSize: 6,
            itemStyle: { color: '#ffd76b' },
            lineStyle: { width: 2.5, shadowColor: 'rgba(255, 215, 107, 0.5)', shadowBlur: 8 },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(255, 215, 107, 0.25)' },
                { offset: 1, color: 'rgba(255, 215, 107, 0)' }
              ])
            }
          }
        ]
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
