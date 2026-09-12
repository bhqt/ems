<template>
  <div class="alarm-scroll">
    <transition-group name="alarm" tag="div" class="alarm-list">
      <div v-for="a in visibleList" :key="a.id" class="alarm-item">
        <span class="alarm-dot" :style="{ background: levelColor(a).color }" />
        <span class="alarm-desc">
          <span class="alarm-name">{{ a.deviceName || '-' }}</span>
          <span class="alarm-type" :style="{ color: levelColor(a).color }">{{ typeLabel(a.alarmType) }}</span>
        </span>
        <span class="alarm-time">{{ fmtTime(a.startTime) }}</span>
      </div>
    </transition-group>
  </div>
</template>

<script>
export default {
  name: 'AlarmScroll',
  props: {
    data: { type: Array, default: () => [] }
  },
  computed: {
    visibleList() {
      return (this.data || []).slice(0, 8)
    }
  },
  methods: {
    levelColor(a) {
      const lvl = String(a.escalateLevel != null ? a.escalateLevel : (a.alarmLevel != null ? a.alarmLevel : a.level))
      if (lvl === '2') return { color: '#ff4d6d' }
      if (lvl === '1') return { color: '#ffd76b' }
      return { color: '#59c6ff' }
    },
    typeLabel(t) {
      if (t === 'OVERLOAD') return '过载'
      if (t === 'OFFLINE') return '离线'
      if (t === 'HEATING') return '过热'
      if (t === 'LEAKAGE') return '泄漏'
      return t || '-'
    },
    fmtTime(t) {
      if (!t) return ''
      const d = new Date(String(t).replace(/-/g, '/'))
      const pad = n => (n < 10 ? '0' + n : '' + n)
      return d.getMonth() + 1 + '-' + pad(d.getDate()) + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes())
    }
  }
}
</script>

<style lang="scss" scoped>
.alarm-scroll {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.alarm-list {
  display: flex;
  flex-direction: column;
}

.alarm-item {
  display: flex;
  align-items: center;
  padding: 6px 4px;
  border-bottom: 1px dashed rgba(64, 158, 255, 0.14);
  animation: alarmMove 6s linear infinite;

  &:hover {
    background: rgba(64, 158, 255, 0.08);
  }
}

.alarm-dot {
  flex: 0 0 8px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 10px;
  box-shadow: 0 0 8px currentColor;
}

.alarm-desc {
  flex: 1;
  display: flex;
  align-items: center;
  min-width: 0;
}

.alarm-name {
  color: #eaf6ff;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-right: 8px;
}

.alarm-type {
  font-size: 12px;
  flex: 0 0 auto;
}

.alarm-time {
  flex: 0 0 auto;
  margin-left: 10px;
  font-size: 12px;
  color: #8fb7e0;
  font-family: 'Consolas', monospace;
}

@keyframes alarmMove {
  0%, 15% { opacity: 0.7; transform: translateY(0); }
  50% { opacity: 1; transform: translateY(0); }
  100% { opacity: 0.75; transform: translateY(-2px); }
}
</style>
