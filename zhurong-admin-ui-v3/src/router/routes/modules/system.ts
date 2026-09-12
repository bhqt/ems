import type { RouteRecordRaw } from 'vue-router'

/** 系统管理路由 */
export const systemRoutes: RouteRecordRaw[] = [
  {
    path: '/system',
    component: () => import('@/layouts/default.vue'),
    redirect: '/system/user',
    name: 'System',
    meta: { title: 'system.title', icon: 'setting', orderNo: 100 },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/user/index.vue'),
        meta: { title: 'system.user.title', icon: 'user', permissions: ['system:user:list'] },
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/role/index.vue'),
        meta: { title: 'system.role.title', icon: 'team', permissions: ['system:role:list'] },
      },
      {
        path: 'menu',
        name: 'Menu',
        component: () => import('@/views/system/menu/index.vue'),
        meta: { title: 'system.menu.title', icon: 'menu', permissions: ['system:menu:list'] },
      },
      {
        path: 'dept',
        name: 'Dept',
        component: () => import('@/views/system/dept/index.vue'),
        meta: { title: 'system.dept.title', icon: 'apartment', permissions: ['system:dept:list'] },
      },
      {
        path: 'dict',
        name: 'Dict',
        component: () => import('@/views/system/dict/index.vue'),
        meta: { title: 'system.dict.title', icon: 'dictionary', permissions: ['system:dict:list'] },
      },
      {
        path: 'config',
        name: 'Config',
        component: () => import('@/views/system/config/index.vue'),
        meta: { title: 'system.config.title', icon: 'tool', permissions: ['system:config:list'] },
      },
      {
        path: 'notice',
        name: 'Notice',
        component: () => import('@/views/system/notice/index.vue'),
        meta: { title: 'system.notice.title', icon: 'notification', permissions: ['system:notice:list'] },
      },
      {
        path: 'log/operlog',
        name: 'OperLog',
        component: () => import('@/views/system/log/operlog.vue'),
        meta: { title: 'system.log.operlog.title', icon: 'file-text', permissions: ['monitor:operlog:list'] },
      },
      {
        path: 'log/logininfor',
        name: 'LoginLog',
        component: () => import('@/views/system/log/logininfor.vue'),
        meta: { title: 'system.log.logininfor.title', icon: 'login', permissions: ['monitor:logininfor:list'] },
      },
      {
        path: 'monitor/online',
        name: 'OnlineUser',
        component: () => import('@/views/system/monitor/online.vue'),
        meta: { title: 'system.monitor.online.title', icon: 'usergroup-add', permissions: ['monitor:online:list'] },
      },
      {
        path: 'monitor/cache',
        name: 'CacheMonitor',
        component: () => import('@/views/system/monitor/cache.vue'),
        meta: { title: 'system.monitor.cache.title', icon: 'database', permissions: ['monitor:cache:list'] },
      },
      {
        path: 'monitor/server',
        name: 'ServerMonitor',
        component: () => import('@/views/system/monitor/server.vue'),
        meta: { title: 'system.monitor.server.title', icon: 'cloud-server', permissions: ['monitor:server:list'] },
      },
    ],
  },
]