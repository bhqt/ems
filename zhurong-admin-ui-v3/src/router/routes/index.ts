import type { RouteRecordRaw } from 'vue-router'

/** 异步路由模块 */
import { dashboardRoutes } from './modules/dashboard'
import { systemRoutes } from './modules/system'
import { energyRoutes } from './modules/energy'
import { maintenanceRoutes } from './modules/maintenance'
import { chargingRoutes } from './modules/charging'
import { newenergyRoutes } from './modules/newenergy'
import { hospitalRoutes } from './modules/hospital'
import { screenRoutes } from './modules/screen'
import { digitalTwinRoutes } from './modules/digital-twin'

/** 所有路由 - 直接静态注册，避免动态 addRoute 时序问题 */
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    component: () => import('@/layouts/blank.vue'),
    meta: { title: 'common.login', hideMenu: true, layout: 'blank' },
    children: [
      {
        path: '',
        name: 'Login',
        component: () => import('@/views/login/index.vue'),
        meta: { title: 'common.login', hideMenu: true },
      },
    ],
  },
  {
    path: '/403',
    component: () => import('@/layouts/blank.vue'),
    meta: { title: 'common.forbidden', hideMenu: true, layout: 'blank' },
    children: [
      {
        path: '',
        name: '403',
        component: () => import('@/views/exception/403.vue'),
        meta: { title: 'common.forbidden', hideMenu: true },
      },
    ],
  },
  {
    path: '/404',
    component: () => import('@/layouts/blank.vue'),
    meta: { title: 'common.notFound', hideMenu: true, layout: 'blank' },
    children: [
      {
        path: '',
        name: '404',
        component: () => import('@/views/exception/404.vue'),
        meta: { title: 'common.notFound', hideMenu: true },
      },
    ],
  },
  {
    path: '/500',
    component: () => import('@/layouts/blank.vue'),
    meta: { title: 'common.serverError', hideMenu: true, layout: 'blank' },
    children: [
      {
        path: '',
        name: '500',
        component: () => import('@/views/exception/500.vue'),
        meta: { title: 'common.serverError', hideMenu: true },
      },
    ],
  },
  {
    path: '/redirect',
    component: () => import('@/layouts/default.vue'),
    meta: { hideMenu: true },
    children: [
      {
        path: '/redirect/:path(.*)',
        name: 'Redirect',
        component: () => import('@/views/redirect/index.vue'),
        meta: { title: 'common.redirect', hideMenu: true },
      },
    ],
  },
  {
    path: '/personal',
    component: () => import('@/layouts/default.vue'),
    meta: { hideMenu: true },
    children: [
      {
        path: 'center',
        name: 'PersonalCenter',
        component: () => import('@/views/personal/center.vue'),
        meta: { title: 'common.personalCenter', hideMenu: true },
      },
    ],
  },
  ...dashboardRoutes,
  ...hospitalRoutes,
  ...systemRoutes,
  ...energyRoutes,
  ...maintenanceRoutes,
  ...chargingRoutes,
  ...newenergyRoutes,
  ...digitalTwinRoutes,
  ...screenRoutes,
  {
    path: '/',
    redirect: '/dashboard/workbench',
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404',
  },
]

/** 异步路由（保留用于权限过滤） */
export const asyncRoutes: RouteRecordRaw[] = [
  ...dashboardRoutes,
  ...hospitalRoutes,
  ...systemRoutes,
  ...energyRoutes,
  ...maintenanceRoutes,
  ...chargingRoutes,
  ...newenergyRoutes,
  ...digitalTwinRoutes,
  ...screenRoutes,
]
