<template>
  <div class="chart-box">
    <div ref="chart" class="chart" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
import chartResize from '../mixins/chartResize'

export default {
  name: 'CategoryChart',
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
      const palette = ['#59c6ff', '#ffd76b', '#63e0a0', '#ff7f8f', '#b07fff']
      this.chart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(9, 26, 62, 0.85)',
          borderColor: 'rgba(64, 158, 255, 0.4)',
          textStyle: { color: '#eaf6ff' },
          formatter: '{b}: {c} kWh ({d}%)'
        },
        legend: {
          type: 'scroll',
          orient: 'vertical',
          right: 4,
          top: 'center',
          itemWidth: 12,
          itemHeight: 12,
          textStyle: { color: '#9fc4ea', fontSize: 12 },
          formatter: name => {
            const item = list.find(i => i.categoryName === name)
            return item ? name : name
          }
        },
        series: [{
          type: 'pie',
          radius: ['52%', '75%'],
          center: ['36%', '50%'],
          avoidLabelOverlap: true,
          itemStyle: {
            borderColor: 'rgba(7, 20, 46, 0.9)',
            borderWidth: 2,
            shadowBlur: 12,
            shadowColor: 'rgba(89, 198, 255, 0.35)'
          },
          label: {
            color: '#cfdeee',
            fontSize: 12,
            formatter: p => p.name + '\n' + p.value.toFixed(0) + 'kWh'
          },
          labelLine: { lineStyle: { color: 'rgba(159, 196, 234, 0.5)' }},
          emphasis: { scaleSize: 6 },
          data: list.map((i, idx) => ({
            name: i.categoryName,
            value: Number(i.kwh || 0),
            itemStyle: { color: palette[idx % palette.length] }
          }))
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
