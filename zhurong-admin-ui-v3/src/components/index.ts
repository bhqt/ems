// Common Components
export { default as BaseButton } from './common/BaseButton.vue'
export { default as BaseInput } from './common/BaseInput.vue'
export { default as BaseSelect } from './common/BaseSelect.vue'
export { default as BaseTable } from './common/BaseTable.vue'
export { default as BaseForm } from './common/BaseForm.vue'
export { default as BaseModal } from './common/BaseModal.vue'
export { default as BaseDrawer } from './common/BaseDrawer.vue'
export { default as BaseCard } from './common/BaseCard.vue'
export { default as BaseTabs } from './common/BaseTabs.vue'
export { default as BaseTree } from './common/BaseTree.vue'
export { default as BaseUpload } from './common/BaseUpload.vue'
export { default as BaseBreadcrumb } from './common/BaseBreadcrumb.vue'
export { default as BasePagination } from './common/BasePagination.vue'
export { default as BaseTag } from './common/BaseTag.vue'
export { default as BaseDescriptions } from './common/BaseDescriptions.vue'
export { default as BaseSteps } from './common/BaseSteps.vue'
export { default as BaseTransfer } from './common/BaseTransfer.vue'
export { default as BaseCascader } from './common/BaseCascader.vue'
export { default as LoadingWrapper } from './common/LoadingWrapper.vue'

// Pro Components
export { default as ProLayout } from './pro/ProLayout.vue'
export { default as ProTable } from './pro/ProTable.vue'
export { default as ProForm } from './pro/ProForm.vue'
export { default as ProCard } from './pro/ProCard.vue'
export { default as ProChart } from './pro/ProChart.vue'
export { default as ProStatistic } from './pro/ProStatistic.vue'
export { default as ProSearchForm } from './pro/ProSearchForm.vue'
export { default as ProDetail } from './pro/ProDetail.vue'

// Layout Components
export { default as BasicLayout } from './layout/BasicLayout.vue'
export { default as BlankLayout } from './layout/BlankLayout.vue'
export { default as IframeLayout } from './layout/IframeLayout.vue'
export { default as SiderMenu } from './layout/components/SiderMenu.vue'
export { default as TopNav } from './layout/components/TopNav.vue'
export { default as TagsView } from './layout/components/TagsView.vue'
export { default as PageHeader } from './layout/components/PageHeader.vue'
export { default as Breadcrumb } from './layout/components/Breadcrumb.vue'
export { default as Logo } from './layout/components/Logo.vue'
export { default as Footer } from './layout/components/Footer.vue'
export { default as ThemeSwitcher } from './layout/components/ThemeSwitcher.vue'
export { default as LanguageSwitcher } from './layout/components/LanguageSwitcher.vue'

// Charts
export { default as ECharts } from './charts/ECharts.vue'
export { default as G2Chart } from './charts/G2Chart.vue'

// Hospital Components
export { default as HospitalDashboard } from './hospital/HospitalDashboard.vue'
export { default as DeviceWall } from './hospital/DeviceWall.vue'
export { default as EnergyFlow } from './hospital/EnergyFlow.vue'
export { default as WardEnergyCard } from './hospital/WardEnergyCard.vue'

// Auto register components for unplugin-vue-components
import type { App } from 'vue'

export function registerComponents(app: App) {
  // 组件会通过 unplugin-vue-components 自动注册
  // 这里可以添加全局注册逻辑（如果需要）
}