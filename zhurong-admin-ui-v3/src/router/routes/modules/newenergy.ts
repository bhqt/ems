import type { RouteRecordRaw } from 'vue-router'

/** 新能源路由 */
export const newenergyRoutes: RouteRecordRaw[] = [
  {
    path: '/newenergy',
    component: () => import('@/layouts/default.vue'),
    redirect: '/newenergy/pv',
    name: 'NewEnergy',
    meta: { title: 'newenergy.title', icon: 'solar-power', orderNo: 40 },
    children: [
      {
        path: 'pv',
        name: 'PVStation',
        component: () => import('@/views/newenergy/pv/index.vue'),
        meta: { title: 'newenergy.pv.title', icon: 'sun', permissions: ['newenergy:pv:list'] },
      },
      {
        path: 'storage',
        name: 'EnergyStorage',
        component: () => import('@/views/newenergy/storage/index.vue'),
        meta: { title: 'newenergy.storage.title', icon: 'battery', permissions: ['newenergy:storage:list'] },
      },
      {
        path: 'microgrid',
        name: 'MicroGrid',
        component: () => import('@/views/newenergy/microgrid/index.vue'),
        meta: { title: 'newenergy.microgrid.title', icon: 'global', permissions: ['newenergy:microgrid:list'] },
      },
      {
        path: 'storageBattery',
        name: 'StorageBattery',
        component: () => import('@/views/newenergy/storageBattery/index.vue'),
        meta: { title: 'newenergy.storageBattery.title', icon: 'battery', permissions: ['newenergy:storageBattery:list'] },
      },
    ],
  },
]