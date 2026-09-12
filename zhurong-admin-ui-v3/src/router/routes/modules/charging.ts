import type { RouteRecordRaw } from 'vue-router'

/** 充电桩路由 */
export const chargingRoutes: RouteRecordRaw[] = [
  {
    path: '/charging',
    component: () => import('@/layouts/default.vue'),
    redirect: '/charging/station',
    name: 'Charging',
    meta: { title: 'charging.title', icon: 'charge', orderNo: 30 },
    children: [
      {
        path: 'station',
        name: 'ChargingStation',
        component: () => import('@/views/charging/station/index.vue'),
        meta: { title: 'charging.station.title', icon: 'home', permissions: ['charging:station:list'] },
      },
      {
        path: 'pile',
        name: 'ChargingPile',
        component: () => import('@/views/charging/pile/index.vue'),
        meta: { title: 'charging.pile.title', icon: 'thunderbolt', permissions: ['charging:pile:list'] },
      },
      {
        path: 'order',
        name: 'ChargingOrder',
        component: () => import('@/views/charging/order/index.vue'),
        meta: { title: 'charging.order.title', icon: 'file-text', permissions: ['charging:order:list'] },
      },
      {
        path: 'price',
        name: 'ChargingPrice',
        component: () => import('@/views/charging/price/index.vue'),
        meta: { title: 'charging.price.title', icon: 'dollar', permissions: ['charging:price:list'] },
      },
    ],
  },
]