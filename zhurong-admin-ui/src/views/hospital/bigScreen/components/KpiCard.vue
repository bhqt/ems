<template>
  <div class="kpi-card">
    <div class="kpi-icon" :style="{ color: icon }">
      <i :class="iconClass" />
    </div>
    <div class="kpi-info">
      <div class="kpi-label">{{ label }}</div>
      <div class="kpi-value">
        <span class="kpi-num">{{ displayValue }}</span>
        <span class="kpi-unit">{{ unit }}</span>
      </div>
      <div class="kpi-extra" :class="{ danger: danger }">
        <i :class="danger ? 'el-icon-caret-bottom' : 'el-icon-caret-top'" />
        {{ extra }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'KpiCard',
  props: {
    label: { type: String, default: '' },
    value: { type: [String, Number], default: 0 },
    unit: { type: String, default: '' },
    iconClass: { type: String, default: 'el-icon-data-line' },
    icon: { type: String, default: '#59c6ff' },
    extra: { type: String, default: '' },
    danger: { type: Boolean, default: false },
    precision: { type: Number, default: 0 }
  },
  computed: {
    displayValue() {
      if (this.value === null || this.value === undefined || this.value === '') return '0'
      if (typeof this.value === 'number') {
        return this.value.toLocaleString('en-US', { minimumFractionDigits: this.precision, maximumFractionDigits: this.precision })
      }
      return this.value
    }
  }
}
</script>

<style lang="scss" scoped>
.kpi-card {
  position: relative;
  display: flex;
  align-items: center;
  height: 100%;
  padding: 10px 14px;
  box-sizing: border-box;
  background: linear-gradient(135deg, rgba(14, 32, 64, 0.8), rgba(20, 52, 108, 0.55));
  border: 1px solid rgba(64, 158, 255, 0.25);
  box-shadow: 0 0 12px rgba(21, 64, 128, 0.25), inset 0 0 18px rgba(32, 96, 160, 0.08);
  overflow: hidden;

  &::after {
    content: '';
    position: absolute;
    right: -40px;
    top: -40px;
    width: 110px;
    height: 110px;
    border-radius: 50%;
    background: radial-gradient(circle, rgba(89, 198, 255, 0.14), transparent 70%);
    pointer-events: none;
  }
}

.kpi-icon {
  flex: 0 0 46px;
  height: 46px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 26px;
  border: 1px solid currentColor;
  border-radius: 8px;
  margin-right: 14px;
  background: rgba(255, 255, 255, 0.03);

  i {
    filter: drop-shadow(0 0 6px currentColor);
  }
}

.kpi-info {
  flex: 1;
  min-width: 0;
}

.kpi-label {
  font-size: 13px;
  color: #9fc4ea;
  letter-spacing: 1px;
  white-space: nowrap;
}

.kpi-value {
  margin-top: 4px;
  line-height: 1;
}

.kpi-num {
  font-size: 30px;
  font-weight: 700;
  color: #eaf6ff;
  font-family: 'Consolas', 'Courier New', monospace;
  text-shadow: 0 0 10px rgba(89, 198, 255, 0.45);
}

.kpi-unit {
  margin-left: 4px;
  font-size: 13px;
  color: #8fb7e0;
}

.kpi-extra {
  margin-top: 4px;
  font-size: 12px;
  color: #63e0a0;
  white-space: nowrap;

  &.danger {
    color: #f56c6c;
  }
}
</style>
