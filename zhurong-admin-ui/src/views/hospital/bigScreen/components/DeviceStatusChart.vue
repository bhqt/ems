<template>
  <div class="chart-box">
    <div ref="chart" class="chart" />
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'DeviceStatusChart',
  props: {
    data: { type: Array, default: () => [] }
  },
  data() {
    return { chart: null }
  },
  computed: {
    stats() {
      const list = this.data || []
      const total = list.length
      const online = list.filter(i => i.online).length
      const offline = list.filter(i => !i.online && String(i.status) === '2').length
      const fault = list.filter(i => !i.online && String(i.status) !== '2').length
      return { total, online, offline, fault }
    }
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
      const s = this.stats
      this.chart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(9, 26, 62, 0.85)',
          borderColor: 'rgba(64, 158, 255, 0.4)',
          textStyle: { color: '#eaf6ff' },
          formatter: '{b}: {c}台 ({d}%)'
        },
        legend: {
          orient: 'vertical',
          right: 8,
          top: 'center',
          itemWidth: 12,
          itemHeight: 12,
          textStyle: { color: '#9fc4ea' }
        },
        graphic: [
          {
            type: 'text',
            left: '31%',
            top: '43%',
            style: {
              text: '设备总数',
              fill: '#8fb7e0',
              fontSize: 13
            }
          },
          {
            type: 'text',
            left: '31%',
            top: '52%',
            style: {
              text: '' + s.total + ' 台',
              fill: '#eaf6ff',
              fontSize: 24,
              fontWeight: 'bold'
            }
          }
        ],
        series: [{
          type: 'pie',
          radius: ['56%', '78%'],
          center: ['34%', '50%'],
          itemStyle: {
            borderColor: 'rgba(7, 20, 46, 0.9)',
            borderWidth: 2
          },
          label: { color: '#cfdeee' },
          data: [
            { name: '在线', value: s.online, itemStyle: { color: '#63e0a0', shadowBlur: 10, shadowColor: 'rgba(99,224,160,0.4)' }},
            { name: '离线', value: s.offline, itemStyle: { color: '#ff7f8f', shadowBlur: 10, shadowColor: 'rgba(255,127,143,0.4)' }},
            { name: '故障', value: s.fault, itemStyle: { color: '#ffd76b', shadowBlur: 10, shadowColor: 'rgba(255,215,107,0.4)' }}
          ]
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
