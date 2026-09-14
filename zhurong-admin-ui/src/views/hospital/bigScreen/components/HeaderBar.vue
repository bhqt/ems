<template>
  <div class="hdr">
    <div class="hdr-left">
      <div class="logo">
        <i class="el-icon-first-aid-kit" />
      </div>
      <div class="title">
        <span class="title-top">医院智慧能源监控平台</span>
        <span class="title-sub">{{ subtitle }}</span>
      </div>
    </div>
    <div class="hdr-center">
      <span class="cn">智</span><span class="cn">碳</span><span class="cn">示</span>
    </div>
    <div class="hdr-right">
      <div class="clock">
        <div class="clock-day">{{ dayText }}</div>
        <div class="clock-time">{{ timeText }}</div>
      </div>
      <el-button type="text" icon="el-icon-back" class="back-btn" @click="$router.go(-1)">
        返回
      </el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeaderBar',
  props: {
    title: { type: String, default: '智慧能源监控平台' },
    subtitle: { type: String, default: '智慧能源监控平台 · Smart Hospital Energy Management' }
  },
  data() {
    return {
      dayText: '',
      timeText: ''
    }
  },
  created() {
    this.tick()
    this.timer = setInterval(this.tick, 1000)
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  methods: {
    tick() {
      const d = new Date()
      const pad = n => (n < 10 ? '0' + n : '' + n)
      const weeks = ['日', '一', '二', '三', '四', '五', '六']
      this.dayText = `${d.getFullYear()}年${pad(d.getMonth() + 1)}月${pad(d.getDate())}日 星期${weeks[d.getDay()]}`
      this.timeText = `${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`
    }
  }
}
</script>

<style lang="scss" scoped>
.hdr {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 88px;
  padding: 0 30px;
  box-sizing: border-box;
  background: linear-gradient(90deg, rgba(9, 26, 62, 0.1), rgba(17, 74, 152, 0.35), rgba(9, 26, 62, 0.1));
  border-bottom: 1px solid rgba(64, 158, 255, 0.18);

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 2px;
    background: linear-gradient(90deg, transparent, #2f7fe0, #59c6ff, #2f7fe0, transparent);
  }
}

.hdr-left {
  display: flex;
  align-items: center;
  flex: 0 0 400px;
}

.logo {
  width: 52px;
  height: 52px;
  margin-right: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #59c6ff;
  border: 1px solid rgba(89, 198, 255, 0.5);
  border-radius: 50%;
  box-shadow: 0 0 18px rgba(89, 198, 255, 0.3), inset 0 0 18px rgba(89, 198, 255, 0.12);
  background: radial-gradient(circle at 35% 30%, rgba(89, 198, 255, 0.22), transparent 60%);
}

.title {
  display: flex;
  flex-direction: column;
}

.title-top {
  font-size: 26px;
  font-weight: 700;
  letter-spacing: 4px;
  color: #eaf6ff;
  text-shadow: 0 0 12px rgba(89, 198, 255, 0.55);
}

.title-sub {
  margin-top: 4px;
  font-size: 11px;
  letter-spacing: 2px;
  color: rgba(159, 212, 255, 0.7);
}

.hdr-center {
  display: flex;
  gap: 14px;
  align-items: baseline;
  flex: 1;
  justify-content: center;

  .cn {
    font-size: 30px;
    font-weight: 700;
    color: rgba(234, 246, 255, 0.92);
    text-shadow: 0 0 16px rgba(89, 198, 255, 0.6);
    letter-spacing: 6px;
  }
}

.hdr-right {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  flex: 0 0 400px;
  gap: 20px;
}

.clock {
  text-align: right;
}

.clock-day {
  font-size: 14px;
  color: #9fc4ea;
  letter-spacing: 1px;
}

.clock-time {
  font-size: 30px;
  font-weight: 700;
  color: #eaf6ff;
  font-family: 'Consolas', monospace;
  text-shadow: 0 0 12px rgba(89, 198, 255, 0.6);
}

.back-btn {
  color: #9fc4ea !important;
  font-size: 15px;

  &:hover {
    color: #59c6ff !important;
  }
}
</style>
