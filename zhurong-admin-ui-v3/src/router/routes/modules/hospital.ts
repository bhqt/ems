import type { RouteRecordRaw } from 'vue-router'

/** 医院能源路由 */
export const hospitalRoutes: RouteRecordRaw[] = [
  {
    path: '/hospital',
    component: () => import('@/layouts/default.vue'),
    redirect: '/hospital/monitor',
    name: 'Hospital',
    meta: { title: 'hospital.title', icon: 'hospital', orderNo: 5 },
    children: [
      {
        path: 'monitor',
        name: 'HospitalMonitor',
        component: () => import('@/views/hospital/monitor/index.vue'),
        meta: { title: 'hospital.monitor.title', icon: 'desktop', permissions: ['hospital:monitor:list'] },
      },
      {
        path: 'analysis',
        name: 'HospitalAnalysis',
        component: () => import('@/views/hospital/analysis/index.vue'),
        meta: { title: 'hospital.analysis.title', icon: 'area-chart', permissions: ['hospital:analysis:list'] },
      },
      {
        path: 'efficiency',
        name: 'HospitalEfficiency',
        component: () => import('@/views/hospital/efficiency/index.vue'),
        meta: { title: 'hospital.efficiency.title', icon: 'efficiency', permissions: ['hospital:efficiency:list'] },
      },
      {
        path: 'suggestion',
        name: 'HospitalSuggestion',
        component: () => import('@/views/hospital/suggestion/index.vue'),
        meta: { title: 'hospital.suggestion.title', icon: 'lightbulb', permissions: ['hospital:suggestion:list'] },
      },
      {
        path: 'decision',
        name: 'HospitalDecision',
        component: () => import('@/views/hospital/decision/index.vue'),
        meta: { title: 'hospital.decision.title', icon: 'compass', permissions: ['hospital:decision:list'] },
      },
    ],
  },
]