<!--
  数据看板中间上部区域：山东省虚拟 3D 地图
  - 基于 ECharts 原生 map + effectScatter + lines，无 echarts-gl 依赖
  - 用「省份错位分层 + 渐变发光描边」营造 3D 立体感
  - 城市热点脉冲 + 飞线连接
-->
<template>
  <div ref="chartRef" class="map3d-container"></div>
</template>

<script>
import * as echarts from 'echarts'
import resize from '../dashboard/mixins/resize'
import shandongGeo from './shandong.json'

export default {
  name: 'ShandongMap3D',
  mixins: [resize],
  data() {
    return {
      chart: null,
      // 浪潮山东园区五大城市点位（经度, 纬度）
      cityPoints: [
        { name: '济南总部', value: [117.000923, 36.675807], level: 4, info: '研发 + 数据中心' },
        { name: '青岛研发', value: [120.355173, 36.082982], level: 3, info: '研发中心' },
        { name: '烟台分公司', value: [121.391379, 37.539297], level: 2, info: '区域分公司' },
        { name: '威海数据中心', value: [122.116189, 37.509691], level: 3, info: '异地容灾' },
        { name: '潍坊生产基地', value: [119.097023, 36.716629], level: 2, info: '生产基地' }
      ]
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },
  methods: {
    initChart() {
      // 注册山东省地图
      echarts.registerMap('shandong', shandongGeo)
      this.chart = echarts.init(this.$el, null, { renderer: 'canvas' })
      this.setOption()
    },
    setOption() {
      // 中心点（济南）作为能量流出中心
      const center = this.cityPoints[0].value
      // 飞线：从济南出发到其他四个城市
      const flyLines = this.cityPoints.slice(1).map(c => ({
        fromName: '济南总部',
        toName: c.name,
        coords: [center, c.value]
      }))

      this.chart.setOption({
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(28,37,80,0.92)',
          borderColor: '#2969e8',
          textStyle: { color: '#fff' },
          formatter: (p) => {
            if (p.componentType === 'effectScatter') {
              const data = p.data
              return `<div style="font-weight:bold;color:#00d0fe;margin-bottom:4px;">${data.name}</div>
                <div>类型：${data.info}</div>
                <div>接入设备：<b style="color:#1be5e7;">${data.devices || '-'}</b> 台</div>
                <div>当前功率：<b style="color:#1be5e7;">${data.power || '-'} kW</b></div>`
            }
            return p.name
          }
        },
        // 视觉映射：3D 立体感 = 错位多层
        visualMap: {
          show: false,
          min: 0,
          max: 1000,
          inRange: { color: ['#0a1a4a', '#1545b3', '#00d0fe'] }
        },
        geo: [
          // 底层阴影（深色作为背景）
          {
            map: 'shandong',
            aspectScale: 0.85,
            layoutCenter: ['50%', '52%'],
            layoutSize: '108%',
            zoom: 1.05,
            roam: false,
            silent: true,
            z: 0,
            itemStyle: {
              areaColor: 'rgba(8, 18, 60, 0.0)',
              borderColor: 'rgba(0, 208, 254, 0.0)',
              borderWidth: 0,
              shadowColor: 'rgba(0, 208, 254, 0.55)',
              shadowBlur: 22
            }
          },
          // 错位第二层（下方 6px，营造 3D 厚度）
          {
            map: 'shandong',
            aspectScale: 0.85,
            layoutCenter: ['50%', '55%'],
            layoutSize: '108%',
            zoom: 1.05,
            roam: false,
            silent: true,
            z: 1,
            itemStyle: {
              areaColor: 'rgba(10, 32, 80, 0.85)',
              borderColor: 'rgba(0, 208, 254, 0.5)',
              borderWidth: 1
            }
          },
          // 主图层：山东省主体（发光描边 + 渐变填充）
          {
            map: 'shandong',
            aspectScale: 0.85,
            layoutCenter: ['50%', '50%'],
            layoutSize: '108%',
            zoom: 1.05,
            roam: false,
            z: 2,
            label: {
              show: true,
              color: '#9fdcff',
              fontSize: 11,
              textBorderColor: '#003366',
              textBorderWidth: 1.5
            },
            itemStyle: {
              areaColor: {
                type: 'radial',
                x: 0.5, y: 0.5, r: 0.7,
                colorStops: [
                  { offset: 0, color: 'rgba(0, 110, 200, 0.55)' },
                  { offset: 1, color: 'rgba(0, 50, 130, 0.35)' }
                ]
              },
              borderColor: '#00d0fe',
              borderWidth: 1.2,
              shadowColor: 'rgba(0, 208, 254, 0.8)',
              shadowBlur: 12
            },
            emphasis: {
              itemStyle: {
                areaColor: 'rgba(0, 208, 254, 0.45)',
                borderColor: '#1be5e7',
                borderWidth: 1.5
              },
              label: { color: '#fff' }
            }
          },
          // 顶层高光（最亮的一条线）
          {
            map: 'shandong',
            aspectScale: 0.85,
            layoutCenter: ['50%', '50%'],
            layoutSize: '108%',
            zoom: 1.05,
            roam: false,
            z: 3,
            silent: true,
            label: { show: false },
            itemStyle: {
              areaColor: 'rgba(0,0,0,0)',
              borderColor: 'rgba(178, 240, 255, 0.9)',
              borderWidth: 0.6,
              shadowColor: 'rgba(0, 240, 255, 0.6)',
              shadowBlur: 6
            }
          }
        ],
        series: [
          // 飞线
          {
            name: 'energy-flow',
            type: 'lines',
            coordinateSystem: 'geo',
            zlevel: 4,
            effect: {
              show: true,
              period: 4,
              trailLength: 0.5,
              symbol: 'arrow',
              symbolSize: 6,
              color: '#1be5e7'
            },
            lineStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: 'rgba(0, 208, 254, 0.9)' },
                { offset: 1, color: 'rgba(27, 229, 231, 0.4)' }
              ]),
              width: 1.4,
              opacity: 0.7,
              curveness: 0.3
            },
            data: flyLines
          },
          // 城市点位：脉冲动画
          {
            name: 'cities',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            zlevel: 5,
            rippleEffect: { brushType: 'stroke', scale: 4.5, period: 3.5 },
            showEffectOn: 'render',
            symbolSize: (val) => 8 + val[2] * 2,
            itemStyle: {
              color: (params) => {
                const lvl = params.data.level || 1
                if (lvl >= 4) return '#ff7e3c'
                if (lvl >= 3) return '#00d0fe'
                return '#1be5e7'
              },
              shadowBlur: 18,
              shadowColor: 'rgba(0, 208, 254, 0.8)'
            },
            label: {
              show: true,
              position: 'top',
              color: '#ffffff',
              fontSize: 12,
              fontWeight: 'bold',
              textBorderColor: '#003366',
              textBorderWidth: 2,
              formatter: '{b}'
            },
            data: this.cityPoints.map(p => ({
              ...p,
              // 演示数据：模拟每个接入点位的设备数与当前功率
              devices: 18 - p.level * 2,
              power: (1800 - p.level * 240).toFixed(0)
            }))
          }
        ]
      })
    }
  }
}
</script>

<style scoped>
.map3d-container {
  width: 100%;
  height: 100%;
  position: relative;
}
</style>
