import type { RouteRecordRaw } from 'vue-router'

/** 大屏路由 */
export const screenRoutes: RouteRecordRaw[] = [
  {
    path: '/screen',
    component: () => import('@/layouts/blank.vue'),
    meta: { hideMenu: true, layout: 'blank' },
    children: [
      {
        path: 'hospital',
        name: 'HospitalScreen',
        component: () => import('@/views/screen/hospital/index.vue'),
        meta: { title: 'common.screenHospital', hideMenu: true, layout: 'blank' },
      },
      {
        path: 'energy',
        name: 'EnergyScreen',
        component: () => import('@/views/screen/energy/index.vue'),
        meta: { title: 'common.screenEnergy', hideMenu: true, layout: 'blank' },
      },
    ],
  },
]