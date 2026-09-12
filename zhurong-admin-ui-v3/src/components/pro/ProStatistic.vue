<template>
  <BaseCard :bordered="bordered" :hoverable="hoverable" :size="size">
    <div class="pro-statistic">
      <div class="pro-statistic-content">
        <div v-if="prefix" class="pro-statistic-prefix">{{ prefix }}</div>
        <div class="pro-statistic-main">
          <div v-if="title" class="pro-statistic-title">{{ title }}</div>
          <div class="pro-statistic-value">
            <a-typography.text :strong="true" :copyable="copyable" :ellipsis="ellipsis">
              {{ formattedValue }}
            </a-typography.text>
            <a-typography.text v-if="unit" class="pro-statistic-unit">{{ unit }}</a-typography.text>
          </div>
        </div>
        <div v-if="suffix" class="pro-statistic-suffix">{{ suffix }}</div>
      </div>

      <div v-if="trend !== undefined" class="pro-statistic-trend">
        <a-trend
          :flag="trend >= 0 ? 'up' : 'down'"
          :color="trend >= 0 ? 'success' : 'error'"
          :reverse-color="reverseColor"
        >
          {{ trend >= 0 ? '+' : '' }}{{ trend.toFixed(2) }}%
        </a-trend>
        <span class="pro-statistic-trend-label">{{ trendLabel }}</span>
      </div>

      <div v-if="compare !== undefined" class="pro-statistic-compare">
        <span :class="['pro-statistic-compare-value', compare >= 0 ? 'positive' : 'negative']">
          {{ compare >= 0 ? '+' : '' }}{{ compare }}
        </span>
        <span class="pro-statistic-compare-label">{{ compareLabel }}</span>
      </div>

      <div v-if="loading" class="pro-statistic-loading">
        <a-skeleton :active="true" :paragraph="{ rows: 2 }" />
      </div>
    </div>
  </BaseCard>
</template>

<script setup lang="ts>
import { computed } from 'vue'
import BaseCard from '@/components/common/BaseCard.vue'

const props = defineProps<{
  /** 标题 */
  title?: string
  /** 数值 */
  value?: number | string
  /** 单位 */
  unit?: string
  /** 前缀 */
  prefix?: string
  /** 后缀 */
  suffix?: string
  /** 趋势百分比 */
  trend?: number
  /** 趋势标签 */
  trendLabel?: string
  /** 反转颜色 */
  reverseColor?: boolean
  /** 对比值 */
  compare?: number
  /** 对比标签 */
  compareLabel?: string
  /** 小数位 */
  precision?: number
  /** 格式化函数 */
  formatter?: (value: number | string) => string
  /** 是否可复制 */
  copyable?: boolean
  /** 省略号 */
  ellipsis?: boolean | { rows: number; expandable: boolean }
  /** 加载状态 */
  loading?: boolean
  /** 是否有边框 */
  bordered?: boolean
  /** 悬浮浮起 */
  hoverable?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
}>()

const formattedValue = computed(() => {
  if (props.loading) return '---'
  if (props.value === undefined || props.value === null) return '---'
  if (props.formatter) return props.formatter(props.value)
  const num = typeof props.value === 'string' ? parseFloat(props.value) : props.value
  if (isNaN(num)) return props.value
  if (props.precision !== undefined) return num.toFixed(props.precision)
  return num.toLocaleString()
})
</script>

<style scoped>
.pro-statistic {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.pro-statistic-content {
  display: flex;
  align-items: flex-end;
  gap: 8px;
}

.pro-statistic-prefix {
  color: var(--zhurong-color-text-secondary);
  font-size: 14px;
}

.pro-statistic-main {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.pro-statistic-title {
  font-size: 14px;
  color: var(--zhurong-color-text-secondary);
  font-weight: 500;
}

.pro-statistic-value {
  display: flex;
  align-items: baseline;
  gap: 4px;
  font-size: 28px;
  font-weight: 600;
  color: var(--zhurong-color-text-heading);
  line-height: 1.2;
}

.pro-statistic-unit {
  font-size: 14px;
  font-weight: 400;
  color: var(--zhurong-color-text-secondary);
}

.pro-statistic-suffix {
  color: var(--zhurong-color-text-secondary);
  font-size: 14px;
}

.pro-statistic-trend {
  display: flex;
  align-items: center;
  gap: 8px;
  padding-top: 8px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
}

.pro-statistic-trend-label {
  font-size: 12px;
  color: var(--zhurong-color-text-tertiary);
}

.pro-statistic-compare {
  display: flex;
  align-items: center;
  gap: 8px;
  padding-top: 8px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
}

.pro-statistic-compare-value {
  font-size: 14px;
  font-weight: 500;
}

.pro-statistic-compare-value.positive {
  color: var(--zhurong-color-success);
}

.pro-statistic-compare-value.negative {
  color: var(--zhurong-color-error);
}

.pro-statistic-compare-label {
  font-size: 12px;
  color: var(--zhurong-color-text-tertiary);
}

.pro-statistic-loading {
  padding: 16px 0;
}
</style>