import type { RouteRecordRaw } from 'vue-router'

/** 数字孪生路由 */
export const digitalTwinRoutes: RouteRecordRaw[] = [
  {
    path: '/digital-twin',
    component: () => import('@/layouts/default.vue'),
    redirect: '/digital-twin/index',
    name: 'DigitalTwin',
    meta: { title: 'common.digitalTwin', icon: 'scan', orderNo: 50 },
    children: [
      {
        path: 'index',
        name: 'DigitalTwinIndex',
        component: () => import('@/views/digital-twin/index.vue'),
        meta: { title: 'common.digitalTwinScene', icon: 'scan', permissions: ['digital-twin:view'] },
      },
    ],
  },
]