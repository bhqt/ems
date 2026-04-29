import i18n from './index'

/**
 * 国际化辅助函数
 * 主要用于处理动态菜单的翻译
 */

/**
 * 中文菜单名称到国际化 key 的映射表
 * 用于后端返回中文菜单标题时，能够正确映射到国际化 key
 */
const chineseToKeyMap = {
  // 系统管理
  '系统管理': 'menu.system.title',
  '用户管理': 'menu.system.user',
  '角色管理': 'menu.system.role',
  '菜单管理': 'menu.system.menu',
  '部门管理': 'menu.system.dept',
  '岗位管理': 'menu.system.post',
  '字典管理': 'menu.system.dict',
  '参数管理': 'menu.system.config',
  '通知公告': 'menu.system.notice',
  '日志管理': 'menu.system.log.title',
  '操作日志': 'menu.system.log.operlog',
  '登录日志': 'menu.system.log.logininfor',
  
  // 系统监控
  '系统监控': 'menu.monitor.title',
  '在线用户': 'menu.monitor.online',
  '定时任务': 'menu.monitor.job',
  '数据监控': 'menu.monitor.druid',
  '服务监控': 'menu.monitor.server',
  '缓存监控': 'menu.monitor.cache',
  'Redis监控': 'menu.monitor.redis',
  
  // 系统工具
  '系统工具': 'menu.tool.title',
  '表单构建': 'menu.tool.build',
  '代码生成': 'menu.tool.gen',
  '系统接口': 'menu.tool.swagger',
  '图标库': 'menu.tool.icon',
  
  // 能源管理
  '能源管理': 'menu.energy.title',
  '能源概览': 'menu.energy.overview',
  '能耗分析': 'menu.energy.analysis',
  '能源报表': 'menu.energy.report',
  '能耗定额': 'menu.energy.quota',
  '用能计划': 'menu.energy.plan',
  
  // 设备管理
  '设备管理': 'menu.equipment.title',
  '设备列表': 'menu.equipment.list',
  '设备类型': 'menu.equipment.type',
  '设备状态': 'menu.equipment.status',
  '设备维护': 'menu.equipment.maintenance',
  '维修管理': 'menu.equipment.repair',
  
  // 告警管理
  '告警管理': 'menu.alarm.title',
  '实时告警': 'menu.alarm.current',
  '历史告警': 'menu.alarm.history',
  '告警规则': 'menu.alarm.rule',
  '告警级别': 'menu.alarm.levelLabel',
  
  // 视频监控
  '视频监控': 'menu.camera.title',
  '实时视频': 'menu.camera.realtime',
  '录像回放': 'menu.camera.playback',
  '摄像头配置': 'menu.camera.config',
  
  // 充电桩管理
  '充电桩管理': 'menu.charging.title',
  '充电站': 'menu.charging.station',
  '充电桩': 'menu.charging.pile',
  '充电订单': 'menu.charging.order',
  '计费策略': 'menu.charging.price',
  
  // 巡检管理
  '巡检管理': 'menu.inspection.title',
  '巡检计划': 'menu.inspection.plan',
  '巡检任务': 'menu.inspection.task',
  '巡检记录': 'menu.inspection.record',
  '巡检点': 'menu.inspection.point',
  
  // 分项计量
  '分项计量': 'menu.itemized.title',
  '拓扑管理': 'menu.itemized.topology',
  '分项分析': 'menu.itemized.analysis',
  
  // 数据查询
  '数据查询': 'menu.dataQuery.title',
  '实时数据': 'menu.dataQuery.realtime',
  '历史数据': 'menu.dataQuery.history',
  '统计数据': 'menu.dataQuery.statistics',
  
  // 个人中心
  '个人中心': 'menu.profile.title',
  '个人信息': 'menu.profile.info',
  '修改密码': 'menu.profile.password',
  
  // 集控化功能
  '集控化功能': 'menu.control.title',
  
  // 计量管理
  '计量管理': 'menu.metering.title',
  
  // 碳资产管理
  '碳资产管理': 'menu.carbon.title',
  '碳分析': 'menu.carbon.analysis',
  
  // 能源分析
  '能源分析': 'menu.energyAnalysis.title',
  '能流图': 'menu.energyAnalysis.energyFlow',
  '用能趋势': 'menu.energyAnalysis.energyTrend',
  '同比分析': 'menu.energyAnalysis.yoyAnalysis',
  '环比分析': 'menu.energyAnalysis.monAnalysis',
  '损耗分析': 'menu.energyAnalysis.lossAnalysis',
  '费用看板': 'menu.energyAnalysis.expenseBoard',
  '费用报表': 'menu.energyAnalysis.expenseReport',
  
  // 数字孪生
  '数字孪生': 'menu.digitaltwin.title',
  
  // 库存管理
  '库存管理': 'menu.inventory.title',
  '附件管理': 'menu.inventory.attachment',
  '供应商管理': 'menu.inventory.purveyor',
  
  // 管理系统
  '管理系统': 'menu.managementSystem.title',
  '预案管理': 'menu.managementSystem.prePlan',
  '流程管理': 'menu.managementSystem.process',
  '制度管理': 'menu.managementSystem.regulation',
  '标准管理': 'menu.managementSystem.standard',
  
  // 维护管理
  '维护管理': 'menu.maintenance.title',
  '值班管理': 'menu.maintenance.duty',
  '维修工单': 'menu.maintenance.repairOrder',
  '排班管理': 'menu.maintenance.schedule',
  '示例报告': 'menu.maintenance.exampleReport',
  '我的巡检': 'menu.maintenance.myInspection',
  '我的工单': 'menu.maintenance.myRepairOrder',
  '设备拓扑': 'menu.maintenance.itemTopology',
  
  // 定额管理
  '定额管理': 'menu.quota.title',
  '定额分析': 'menu.quota.analysis',
  '定额配置': 'menu.quota.config',
  '定额监控': 'menu.quota.monitor',
  
  // 报表管理
  '报表管理': 'menu.report.title',
  '报表生成': 'menu.report.generate',
  '报表模板': 'menu.report.template',
  
  // 新能源
  '新能源': 'menu.newenergy.title',
  '储能': 'menu.newenergy.energyStorage',
  '微电网': 'menu.newenergy.microGrid',
  '光伏电站': 'menu.newenergy.pvStation',
  '储能电池': 'menu.newenergy.storageBattery',
  
  // 分析报告
  '分析报告': 'menu.analysisReport.title',
  
  // 数据看板
  '数据看板': 'menu.dataBoard.title',
  
  // 仪表盘
  '首页': 'menu.dashboard.title',
  'Dashboard': 'menu.dashboard.title',
  '首页(dashboard)': 'menu.dashboard.title',
  
  // 3D 可视化
  '3D可视化': 'menu.visualization3D.title',
  '3D 可视化': 'menu.visualization3D.title',
  
  // 业务管理
  '业务管理': 'menu.business.title',
  
  // 物品管理
  '物品管理': 'menu.item.title',
  
  // 运维管理
  '运维管理': 'menu.operation.title',
  
  // 报警管理
  '报警管理': 'menu.alarmManage.title',
  
  // 新能源管理
  '新能源管理': 'menu.newEnergyManage.title',
  '储能管理': 'menu.newEnergyManage.energyStorage',
  '微电网管理': 'menu.newEnergyManage.microGrid',
  '光伏电站管理': 'menu.newEnergyManage.pvStation',
  '储能电池管理': 'menu.newEnergyManage.storageBattery',
  
  // 管理体系
  '管理体系': 'menu.managementSystem2.title',
  '预案管理': 'menu.managementSystem2.prePlan',
  '流程管理': 'menu.managementSystem2.process',
  '制度管理': 'menu.managementSystem2.regulation',
  '标准管理': 'menu.managementSystem2.standard',
  
  // 碳排分析
  '碳排分析': 'menu.carbonAnalysis.title',
  '碳排放分析': 'menu.carbonAnalysis.analysis',
  
  // 电力参数查询
  '电力参数查询': 'menu.powerParam.title',
  '参数查询': 'menu.powerParam.query',
  
  // 视频配置
  '视频配置': 'menu.videoConfig.title',
  '摄像头配置': 'menu.videoConfig.config',
  
  // 实时画面
  '实时画面': 'menu.realTimeView.title',
  '实时视频': 'menu.realTimeView.view',
  
  // 机构管理
  '机构管理': 'menu.orgManage.title',
  '部门管理': 'menu.orgManage.dept',
  
  // 参数设置
  '参数设置': 'menu.paramSet.title',
  '系统参数': 'menu.paramSet.config',
  
  // 文件管理
  '文件管理': 'menu.fileManage.title',
  '文件列表': 'menu.fileManage.files',
  
  // 计费方式
  '计费方式': 'menu.billingType.title',
  '计费类型': 'menu.billingType.type',
  
  // Admin 监控
  'Admin监控': 'menu.adminMonitor.title',
  'Admin 监控': 'menu.adminMonitor.title',
  '系统监控': 'menu.adminMonitor.monitor',
  
  // 任务调度中心
  '任务调度中心': 'menu.jobScheduler.title',
  '定时任务': 'menu.jobScheduler.job',
  
  // 缓存列表
  '缓存列表': 'menu.cacheList.title',
  '缓存监控': 'menu.cacheList.list',
  
  // 用量检测
  '用量检测': 'menu.usageDetect.title',
  '用量监控': 'menu.usageDetect.detect',
  
  // 能耗趋势
  '能耗趋势': 'menu.energyConsumeTrend.title',
  '用能趋势': 'menu.energyConsumeTrend.trend',
  
  // 能源流向
  '能源流向': 'menu.energyFlow.title',
  '能流图': 'menu.energyFlow.flow',
  
  // 分项概览
  '分项概览': 'menu.itemOverview.title',
  '分项计量': 'menu.itemOverview.overview',
  
  // 设备信息
  '设备信息': 'menu.deviceInfo.title',
  '设备列表': 'menu.deviceInfo.info',
  
  // 网关管理
  '网关管理': 'menu.gateway.title',
  '网关列表': 'menu.gateway.manage',
  
  // 项目拓扑
  '项目拓扑': 'menu.projectTopology.title',
  '拓扑管理': 'menu.projectTopology.topology',
  
  // 充电桩管理
  '充电桩管理': 'menu.chargingManage.title',
  '充电站管理': 'menu.chargingManage.station',
  '充电桩管理': 'menu.chargingManage.pile',
  '充电订单管理': 'menu.chargingManage.order',
  '计费策略管理': 'menu.chargingManage.price',
  
  // 危化品管理
  '危化品管理': 'menu.hazardous.title',
  '危化品信息': 'menu.hazardous.goods',
  '危化品信息管理': 'menu.hazardous.info',
  '危化品入库记录': 'menu.hazardous.stockIn',
  '危化品出库记录': 'menu.hazardous.stockOut',
  '危化品库存': 'menu.hazardous.inventory',
  '库存预警': 'menu.hazardous.warning',
  
  // 巡更管理
  '巡更管理': 'menu.patrol.title',
  '巡更点位': 'menu.patrol.point',
  '巡更路线': 'menu.patrol.route',
  '巡更计划': 'menu.patrol.plan',
  '巡更记录': 'menu.patrol.record',
  '巡更报警': 'menu.patrol.alarm',
  '巡更任务': 'menu.patrol.task',
  
  // 物品管理
  '物品管理': 'menu.itemManage.title',
  '物品信息管理': 'menu.itemManage.info',
  '物品库存': 'menu.itemManage.inventory',
  '物品入库记录': 'menu.itemManage.stockIn',
  '物品出库记录': 'menu.itemManage.stockOut',
  
  // 充电桩运营
  '充电桩运营': 'menu.chargingOperation.title',
  '充电桩首页': 'menu.chargingOperation.home',
  '订单管理': 'menu.chargingOperation.order.title',
  '实时订单': 'menu.chargingOperation.order.realtime',
  '历史订单': 'menu.chargingOperation.order.history',
  '异常订单': 'menu.chargingOperation.order.abnormal',
  '占位订单': 'menu.chargingOperation.order.occupied',
  '商户管理': 'menu.chargingOperation.merchant.title',
  '平台商户': 'menu.chargingOperation.merchant.platform',
  '互联商户': 'menu.chargingOperation.merchant.interconnect',
  '电站管理': 'menu.chargingOperation.station.title',
  '直连电站': 'menu.chargingOperation.station.direct',
  '互联电站': 'menu.chargingOperation.station.interconnect',
  '电桩管理': 'menu.chargingOperation.pile.title',
  '直连电桩': 'menu.chargingOperation.pile.direct',
  '互联电桩': 'menu.chargingOperation.pile.interconnect',
  '品牌型号管理': 'menu.chargingOperation.brand',
  '价格费用管理': 'menu.chargingOperation.price',
  '充电价格策略': 'menu.chargingOperation.strategy',
  
  // 光伏管理
  '光伏管理': 'menu.pvManage.title',
  '作业规范': 'menu.pvManage.standard',
  '计费方案': 'menu.pvManage.billing',
  
  // 报警管理
  '报警管理': 'menu.alarmManage.title',
  '实时报警': 'menu.alarmManage.realtime',
  '历史报警': 'menu.alarmManage.history',
  '报警规则': 'menu.alarmManage.rule',
  '报警分析': 'menu.alarmManage.analysis',
  
  // 能源管理
  '用能状况': 'menu.energyStatus.title',
  '用量监测': 'menu.usageMonitor.title',
  '用能概况': 'menu.energyOverview.title'
}

/**
 * 翻译菜单标题
 * @param {string} title - 菜单标题（可能是国际化 key 或原始文本）
 * @param {string} fallback - 回退文本
 * @returns {string} 翻译后的文本
 *
 * 使用说明：
 * 1. 如果 title 已经是国际化 key 格式（包含点号），直接翻译
 * 2. 如果后端返回的是中文文本，通过映射表查找对应的国际化 key
 * 3. 如果映射表中没有，尝试在 menu 配置中查找
 * 4. 如果都找不到，返回原始文本
 */
export function translateMenuTitle(title, fallback = '') {
  if (!title) return ''

  // 1. 如果 title 已经是国际化 key 格式（包含点号）
  if (title.includes('.')) {
    const translated = i18n.t(title)
    if (translated !== title) {
      return translated
    }
  }

  // 2. 通过中文映射表查找对应的国际化 key
  if (chineseToKeyMap[title]) {
    const translated = i18n.t(chineseToKeyMap[title])
    if (translated !== chineseToKeyMap[title]) {
      return translated
    }
  }

  // 3. 尝试在 menu 配置中查找（用于后端返回中文的情况）
  const possibleKeys = [
    `menu.${title}`,
    `menu.system.${title}`,
    `menu.monitor.${title}`,
    `menu.tool.${title}`,
    `menu.energy.${title}`,
    `menu.equipment.${title}`,
    `menu.alarm.${title}`,
    `menu.camera.${title}`,
    `menu.charging.${title}`,
    `menu.inspection.${title}`,
    `menu.itemized.${title}`,
    `menu.dataQuery.${title}`,
    `menu.profile.${title}`,
    `menu.control.${title}`,
    `menu.metering.${title}`,
    `menu.carbon.${title}`,
    `menu.energyAnalysis.${title}`,
    `menu.digitaltwin.${title}`,
    `menu.inventory.${title}`,
    `menu.managementSystem.${title}`,
    `menu.maintenance.${title}`,
    `menu.quota.${title}`,
    `menu.report.${title}`,
    `menu.newenergy.${title}`,
    `menu.analysisReport.${title}`,
    `menu.dataBoard.${title}`,
    `menu.dashboard.${title}`,
    `menu.visualization3D.${title}`,
    `menu.business.${title}`,
    `menu.item.${title}`,
    `menu.operation.${title}`,
    `menu.alarmManage.${title}`,
    `menu.newEnergyManage.${title}`,
    `menu.managementSystem2.${title}`,
    `menu.carbonAnalysis.${title}`,
    `menu.powerParam.${title}`,
    `menu.videoConfig.${title}`,
    `menu.realTimeView.${title}`,
    `menu.orgManage.${title}`,
    `menu.paramSet.${title}`,
    `menu.fileManage.${title}`,
    `menu.billingType.${title}`,
    `menu.adminMonitor.${title}`,
    `menu.jobScheduler.${title}`,
    `menu.cacheList.${title}`,
    `menu.usageDetect.${title}`,
    `menu.energyConsumeTrend.${title}`,
    `menu.energyFlow.${title}`,
    `menu.itemOverview.${title}`,
    `menu.deviceInfo.${title}`,
    `menu.gateway.${title}`,
    `menu.projectTopology.${title}`,
    `menu.chargingManage.${title}`,
    `menu.hazardous.${title}`,
    `menu.patrol.${title}`,
    `menu.itemManage.${title}`,
    `menu.chargingOperation.${title}`,
    `menu.pvManage.${title}`,
    `menu.alarmManage.${title}`,
    `menu.energyStatus.${title}`,
    `menu.usageMonitor.${title}`,
    `menu.energyOverview.${title}`
  ]

  for (const key of possibleKeys) {
    const translated = i18n.t(key)
    if (translated !== key) {
      return translated
    }
  }

  // 4. 尝试使用 common 中的翻译
  const commonKey = `common.${title}`
  const commonTranslated = i18n.t(commonKey)
  if (commonTranslated !== commonKey) {
    return commonTranslated
  }

  // 5. 返回原始文本或回退文本
  return fallback || title
}

/**
 * 批量翻译菜单列表
 * @param {Array} menus - 菜单列表
 * @returns {Array} 翻译后的菜单列表
 */
export function translateMenus(menus) {
  if (!Array.isArray(menus)) return []

  return menus.map(menu => {
    const translatedMenu = { ...menu }

    // 翻译当前菜单标题
    if (menu.meta && menu.meta.title) {
      translatedMenu.meta = {
        ...menu.meta,
        title: translateMenuTitle(menu.meta.title)
      }
    }

    // 递归翻译子菜单
    if (menu.children && menu.children.length > 0) {
      translatedMenu.children = translateMenus(menu.children)
    }

    return translatedMenu
  })
}

/**
 * 生成菜单的国际化 key
 * 用于前端静态路由配置
 * @param {string} module - 模块名
 * @param {string} name - 菜单名
 * @returns {string} 国际化 key
 */
export function generateMenuKey(module, name) {
  return `menu.${module}.${name}`
}

/**
 * 从路由路径推断菜单 key
 * @param {string} path - 路由路径
 * @returns {string} 可能的国际化 key
 */
export function inferMenuKeyFromPath(path) {
  if (!path) return ''

  // 移除开头的斜杠并按斜杠分割
  const parts = path.replace(/^\//, '').split('/')

  if (parts.length >= 2) {
    return `menu.${parts[0]}.${parts[1]}`
  }

  if (parts.length === 1) {
    return `menu.${parts[0]}.title`
  }

  return ''
}

/**
 * 获取 Element UI 语言包
 * 用于动态切换 Element UI 的语言
 */
export function getElementLocale() {
  const langMap = {
    'zh-CN': () => import('element-ui/lib/locale/lang/zh-CN'),
    'en': () => import('element-ui/lib/locale/lang/en'),
    'id': () => import('element-ui/lib/locale/lang/id'), // Element UI 支持印尼语
    'ru': () => import('element-ui/lib/locale/lang/ru-RU') // Element UI 支持俄语
  }

  const currentLang = i18n.locale
  const loader = langMap[currentLang] || langMap['zh-CN']

  return loader()
}

export default {
  translateMenuTitle,
  translateMenus,
  generateMenuKey,
  inferMenuKeyFromPath,
  getElementLocale
}
