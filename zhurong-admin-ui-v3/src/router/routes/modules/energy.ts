import type { RouteRecordRaw } from 'vue-router'

/** 能耗管理路由 */
export const energyRoutes: RouteRecordRaw[] = [
  {
    path: '/energy',
    component: () => import('@/layouts/default.vue'),
    redirect: '/energy/analysis',
    name: 'Energy',
    meta: { title: 'energy.title', icon: 'dashboard', orderNo: 10 },
    children: [
      {
        path: 'analysis',
        name: 'EnergyAnalysis',
        component: () => import('@/views/energy/analysis/index.vue'),
        meta: { title: 'energy.analysis.title', icon: 'line-chart', permissions: ['energy:analysis:list'] },
      },
      {
        path: 'quota',
        name: 'EnergyQuota',
        component: () => import('@/views/energy/quota/index.vue'),
        meta: { title: 'energy.quota.title', icon: 'calculator', permissions: ['energy:quota:list'] },
      },
      {
        path: 'alarm',
        name: 'EnergyAlarm',
        component: () => import('@/views/energy/alarm/index.vue'),
        meta: { title: 'energy.alarm.title', icon: 'warning', permissions: ['energy:alarm:list'] },
      },
      {
        path: 'report',
        name: 'EnergyReport',
        component: () => import('@/views/energy/report/index.vue'),
        meta: { title: 'energy.report.title', icon: 'file-excel', permissions: ['energy:report:list'] },
      },
      {
        path: 'carbon',
        name: 'CarbonAsset',
        component: () => import('@/views/energy/carbon/index.vue'),
        meta: { title: 'energy.carbon.title', icon: 'environment', permissions: ['energy:carbon:list'] },
      },
    ],
  },
]