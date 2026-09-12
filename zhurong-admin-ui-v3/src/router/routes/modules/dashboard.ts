import type { RouteRecordRaw } from 'vue-router'

/** 仪表盘路由 */
export const dashboardRoutes: RouteRecordRaw[] = [
  {
    path: '/dashboard',
    component: () => import('@/layouts/default.vue'),
    redirect: '/dashboard/workbench',
    name: 'Dashboard',
    meta: { title: 'dashboard.title', icon: 'home', orderNo: 1 },
    children: [
      {
        path: 'workbench',
        name: 'Workbench',
        component: () => import('@/views/dashboard/workbench/index.vue'),
        meta: { title: 'dashboard.workbench.title', icon: 'dashboard', affix: true },
      },
      {
        path: 'analysis',
        name: 'Analysis',
        component: () => import('@/views/dashboard/analysis/index.vue'),
        meta: { title: 'dashboard.analysis.title', icon: 'line-chart' },
      },
    ],
  },
]