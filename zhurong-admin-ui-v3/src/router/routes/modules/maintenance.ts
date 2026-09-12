import type { RouteRecordRaw } from 'vue-router'

/** 运维管理路由 */
export const maintenanceRoutes: RouteRecordRaw[] = [
  {
    path: '/maintenance',
    component: () => import('@/layouts/default.vue'),
    redirect: '/maintenance/inspection-plan',
    name: 'Maintenance',
    meta: { title: 'maintenance.title', icon: 'tool', orderNo: 20 },
    children: [
      {
        path: 'inspection-plan',
        name: 'InspectionPlan',
        component: () => import('@/views/maintenance/inspection-plan/index.vue'),
        meta: { title: 'maintenance.inspectionPlan.title', icon: 'schedule', permissions: ['maintenance:inspection-plan:list'] },
      },
      {
        path: 'inspection-record',
        name: 'InspectionRecord',
        component: () => import('@/views/maintenance/inspection-record/index.vue'),
        meta: { title: 'maintenance.inspectionRecord.title', icon: 'file-text', permissions: ['maintenance:inspection-record:list'] },
      },
      {
        path: 'repair-order',
        name: 'RepairOrder',
        component: () => import('@/views/maintenance/repair-order/index.vue'),
        meta: { title: 'maintenance.repairOrder.title', icon: 'wrench', permissions: ['maintenance:repair-order:list'] },
      },
      {
        path: 'schedule',
        name: 'Schedule',
        component: () => import('@/views/maintenance/schedule/index.vue'),
        meta: { title: 'maintenance.schedule.title', icon: 'calendar', permissions: ['maintenance:schedule:list'] },
      },
      {
        path: 'duty',
        name: 'Duty',
        component: () => import('@/views/maintenance/duty/index.vue'),
        meta: { title: 'maintenance.duty.title', icon: 'idcard', permissions: ['maintenance:duty:list'] },
      },
      {
        path: 'asset',
        name: 'Asset',
        component: () => import('@/views/maintenance/asset/index.vue'),
        meta: { title: 'maintenance.asset.title', icon: 'box-plot', permissions: ['maintenance:asset:list'] },
      },
    ],
  },
]