// 中文语言包
export default {
  // 通用
  common: {
    search: '搜索',
    reset: '重置',
    submit: '提交',
    cancel: '取消',
    confirm: '确认',
    delete: '删除',
    edit: '编辑',
    add: '新增',
    view: '查看',
    export: '导出',
    import: '导入',
    download: '下载',
    upload: '上传',
    operate: '操作',
    status: '状态',
    enable: '启用',
    disable: '禁用',
    yes: '是',
    no: '否',
    save: '保存',
    close: '关闭',
    back: '返回',
    refresh: '刷新',
    loading: '加载中...',
    success: '操作成功',
    error: '操作失败',
    warning: '警告',
    info: '提示',
    pleaseSelect: '请选择',
    pleaseInput: '请输入',
    startDate: '开始日期',
    endDate: '结束日期',
    startTime: '开始时间',
    endTime: '结束时间',
    createTime: '创建时间',
    updateTime: '更新时间',
    remark: '备注',
    action: '操作',
    more: '更多',
    all: '全部',
    selectAll: '全选',
    empty: '暂无数据',
    total: '共 {total} 条',
    pageSize: '条/页',
    goTo: '前往',
    page: '页',
    index: '序号',
    operator: '操作人员',
    creator: '创建者',
    publishTime: '发布时间',
    systemBuiltIn: '系统内置',
    loginInfo: '登录信息',
    operInfo: '操作信息',
    normal: '正常',
    failed: '失败',
    standalone: '单机',
    cluster: '集群',
    refreshCache: '刷新缓存',
    selectDate: '选择日期',
    to: '至',
    pass: '合格',
    fail: '不合格',
    systemTitle: '智碳能源管理系统',
    // 导航栏
    profile: '个人中心',
    layoutSetting: '布局设置',
    logout: '退出登录',
    // 标签视图
    refreshPage: '刷新页面',
    closeCurrent: '关闭当前',
    closeOthers: '关闭其他',
    closeLeft: '关闭左侧',
    closeRight: '关闭右侧',
    closeAll: '全部关闭',
    // 设置
    saveConfig: '保存配置',
    // Cron 表达式构建器
    crontab: {
      second: '秒',
      minute: '分钟',
      hour: '小时',
      day: '日',
      month: '月',
      week: '周',
      year: '年',
      timeExpression: '时间表达式',
      cronExpression: 'Cron 表达式'
    },
    // 导航栏扩展
    moreMenu: '更多菜单',
    // 文件上传
    upload: {
      selectFile: '选取文件',
      pleaseUpload: '请上传',
      sizeLimit: '大小不超过',
      formatLimit: '格式为',
      fileSuffix: '的文件',
      uploading: '正在上传文件，请稍候...',
      uploadFailed: '上传文件失败，请重试',
      fileTypeError: '文件格式不正确',
      fileSizeError: '上传文件大小不能超过'
    },
    // 右侧工具栏
    showSearch: '显示查询',
    hideSearch: '隐藏查询',
    showHideColumns: '显隐列',
    showHideTitle: '显示/隐藏',
    show: '显示',
    hide: '隐藏'
  },

  // 登录页
  login: {
    title: '祝融能源管理系统',
    username: '用户名',
    password: '密码',
    captcha: '验证码',
    remember: '记住密码',
    login: '登录',
    logging: '登录中...',
    usernameRequired: '请输入用户名',
    passwordRequired: '请输入密码',
    captchaRequired: '请输入验证码',
    loginSuccess: '登录成功',
    loginError: '登录失败',
    logout: '退出登录',
    logoutConfirm: '确定要退出登录吗？'
  },

  // 菜单 - 与后端返回的菜单名称对应
  menu: {
    // 系统管理
    system: {
      title: '系统管理',
      user: '用户管理',
      role: '角色管理',
      menu: '菜单管理',
      dept: '部门管理',
      post: '岗位管理',
      dict: '字典管理',
      config: '参数管理',
      notice: '通知公告',
      log: {
        title: '日志管理',
        operlog: '操作日志',
        logininfor: '登录日志'
      }
    },
    // 监控
    monitor: {
      title: '系统监控',
      online: '在线用户',
      job: '定时任务',
      druid: '数据监控',
      server: '服务监控',
      cache: '缓存监控',
      redis: 'Redis监控'
    },
    // 工具
    tool: {
      title: '系统工具',
      build: '表单构建',
      gen: '代码生成',
      swagger: '系统接口',
      icon: '图标库'
    },
    // 能源管理
    energy: {
      title: '能源管理',
      overview: '能源概览',
      analysis: '能耗分析',
      report: '能源报表',
      quota: '能耗定额',
      plan: '用能计划'
    },
    // 设备管理
    equipment: {
      title: '设备管理',
      list: '设备列表',
      type: '设备类型',
      status: '设备状态',
      maintenance: '设备维护',
      repair: '维修管理'
    },
    // 告警管理
    alarm: {
      title: '告警管理',
      current: '实时告警',
      history: '历史告警',
      level: {
        general: '一般',
        urgent: '紧急',
        serious: '严重'
      },
      rule: '告警规则',
      levelLabel: '告警级别'
    },
    // 视频监控
    camera: {
      title: '视频监控',
      realtime: '实时视频',
      playback: '录像回放',
      config: '摄像头配置'
    },
    // 充电桩
    charging: {
      title: '充电桩管理',
      station: '充电站',
      pile: '充电桩',
      order: '充电订单',
      price: '计费策略'
    },
    // 巡检管理
    inspection: {
      title: '巡检管理',
      plan: '巡检计划',
      task: '巡检任务',
      record: '巡检记录',
      point: '巡检点'
    },
    // 能耗分项
    itemized: {
      title: '分项计量',
      topology: '拓扑管理',
      analysis: '分项分析'
    },
    // 数据查询
    dataQuery: {
      title: '数据查询',
      realtime: '实时数据',
      history: '历史数据',
      statistics: '统计数据'
    },
    // 个人中心
    profile: {
      title: '个人中心',
      info: '个人信息',
      password: '修改密码'
    },
    // 集控化功能
    control: {
      title: '集控化功能',
      control: '集控化功能'
    },
    // 计量管理
    metering: {
      title: '计量管理',
      metering: '计量管理'
    },
    // 碳资产管理
    carbon: {
      title: '碳资产管理',
      analysis: '碳分析'
    },
    // 能源分析
    energyAnalysis: {
      title: '能源分析',
      energyOverview: '能源概览',
      energyFlow: '能流图',
      energyTrend: '用能趋势',
      yoyAnalysis: '同比分析',
      monAnalysis: '环比分析',
      lossAnalysis: '损耗分析',
      expenseBoard: '费用看板',
      expenseReport: '费用报表'
    },
    // 数字孪生
    digitaltwin: {
      title: '数字孪生',
      digitaltwin: '数字孪生'
    },
    // 库存管理
    inventory: {
      title: '库存管理',
      attachment: '附件管理',
      purveyor: '供应商管理'
    },
    // 管理系统
    managementSystem: {
      title: '管理系统',
      prePlan: '预案管理',
      process: '流程管理',
      regulation: '制度管理',
      standard: '标准管理'
    },
    // 维护管理
    maintenance: {
      title: '维护管理',
      duty: '值班管理',
      inspectionPlan: '巡检计划',
      inspectionRecord: '巡检记录',
      repairOrder: '维修工单',
      schedule: '排班管理',
      exampleReport: '示例报告',
      myInspection: '我的巡检',
      myRepairOrder: '我的工单',
      itemTopology: '设备拓扑'
    },
    // 配额管理
    quota: {
      title: '配额管理',
      analysis: '配额分析',
      config: '配额配置',
      monitor: '配额监控'
    },
    // 报表管理
    report: {
      title: '报表管理',
      generate: '报表生成',
      template: '报表模板'
    },
    // 新能源
    newenergy: {
      title: '新能源',
      energyStorage: '储能',
      microGrid: '微电网',
      pvStation: '光伏电站',
      storageBattery: '储能电池'
    },
    // 分析报告
    analysisReport: {
      title: '分析报告',
      analysisReport: '分析报告'
    },
    // 数据查询
    dataQuery: {
      title: '数据查询',
      electricParams: '电参查询'
    },
    // 数据看板
    dataBoard: {
      title: '数据看板',
      dataBoard: '数据看板'
    },
    // 首页
    dashboard: {
      title: '首页',
      dashboard: '首页'
    },
    // 3D 可视化
    visualization3D: {
      title: '3D 可视化',
      visualization3D: '3D 可视化'
    },
    // 业务管理
    business: {
      title: '业务管理',
      business: '业务管理'
    },
    // 物品管理
    item: {
      title: '物品管理',
      item: '物品管理'
    },
    // 运维管理
    operation: {
      title: '运维管理',
      operation: '运维管理'
    },
    // 报警管理
    alarmManage: {
      title: '报警管理',
      realtime: '实时报警',
      history: '历史报警',
      rule: '报警规则',
      analysis: '报警分析'
    },
    // 新能源管理
    newEnergyManage: {
      title: '新能源管理',
      energyStorage: '储能管理',
      microGrid: '微电网管理',
      pvStation: '光伏电站管理',
      storageBattery: '储能电池管理'
    },
    // 管理体系
    managementSystem2: {
      title: '管理体系',
      prePlan: '预案管理',
      process: '流程管理',
      regulation: '制度管理',
      standard: '标准管理'
    },
    // 碳排分析
    carbonAnalysis: {
      title: '碳排分析',
      analysis: '碳排放分析'
    },
    // 电力参数查询
    powerParam: {
      title: '电力参数查询',
      query: '参数查询'
    },
    // 视频配置
    videoConfig: {
      title: '视频配置',
      config: '摄像头配置'
    },
    // 实时画面
    realTimeView: {
      title: '实时画面',
      view: '实时视频'
    },
    // 机构管理
    orgManage: {
      title: '机构管理',
      dept: '部门管理'
    },
    // 参数设置
    paramSet: {
      title: '参数设置',
      config: '系统参数'
    },
    // 文件管理
    fileManage: {
      title: '文件管理',
      files: '文件列表'
    },
    // 计费方式
    billingType: {
      title: '计费方式',
      type: '计费类型'
    },
    // Admin 监控
    adminMonitor: {
      title: 'Admin 监控',
      monitor: '系统监控'
    },
    // 任务调度中心
    jobScheduler: {
      title: '任务调度中心',
      job: '定时任务'
    },
    // 缓存列表
    cacheList: {
      title: '缓存列表',
      list: '缓存监控'
    },
    // 用量检测
    usageDetect: {
      title: '用量检测',
      detect: '用量监控'
    },
    // 能耗趋势
    energyConsumeTrend: {
      title: '能耗趋势',
      trend: '用能趋势'
    },
    // 能源管理 - 用能状况
    energyStatus: {
      title: '用能状况',
      status: '用能状况'
    },
    // 用量监测
    usageMonitor: {
      title: '用量监测',
      monitor: '用量监测'
    },
    // 用能概况
    energyOverview: {
      title: '用能概况',
      overview: '用能概况'
    },
    // 能源流向
    energyFlow: {
      title: '能源流向',
      flow: '能流图'
    },
    // 分项概览
    itemOverview: {
      title: '分项概览',
      overview: '分项计量'
    },
    // 设备信息
    deviceInfo: {
      title: '设备信息',
      info: '设备列表'
    },
    // 网关管理
    gateway: {
      title: '网关管理',
      manage: '网关列表'
    },
    // 项目拓扑
    projectTopology: {
      title: '项目拓扑',
      topology: '拓扑管理'
    },
    // 充电桩管理
    chargingManage: {
      title: '充电桩管理',
      station: '充电站管理',
      pile: '充电桩管理',
      order: '充电订单管理',
      price: '计费策略管理'
    },
    // 危化品管理
    hazardous: {
      title: '危化品管理',
      goods: '危化品信息',
      info: '危化品信息管理',
      stockIn: '危化品入库记录',
      stockOut: '危化品出库记录',
      inventory: '危化品库存',
      warning: '库存预警'
    },
    // 巡更管理
    patrol: {
      title: '巡更管理',
      point: '巡更点位',
      route: '巡更路线',
      plan: '巡更计划',
      record: '巡更记录',
      alarm: '巡更报警',
      task: '巡更任务'
    },
    // 物品管理
    itemManage: {
      title: '物品管理',
      info: '物品信息管理',
      inventory: '物品库存',
      stockIn: '物品入库记录',
      stockOut: '物品出库记录'
    },
    // 充电桩运营
    chargingOperation: {
      title: '充电桩运营',
      home: '充电桩首页',
      order: {
        title: '订单管理',
        realtime: '实时订单',
        history: '历史订单',
        abnormal: '异常订单',
        occupied: '占位订单'
      },
      merchant: {
        title: '商户管理',
        platform: '平台商户',
        interconnect: '互联商户'
      },
      station: {
        title: '电站管理',
        direct: '直连电站',
        interconnect: '互联电站'
      },
      pile: {
        title: '电桩管理',
        direct: '直连电桩',
        interconnect: '互联电桩'
      },
      brand: '品牌型号管理',
      price: '价格费用管理',
      strategy: '充电价格策略'
    },
    // 光伏管理
    pvManage: {
      title: '光伏管理',
      standard: '作业规范',
      billing: '计费方案'
    }
  },

  // 导航栏
  navbar: {
    home: '首页',
    profile: '个人中心',
    settings: '系统设置',
    fullscreen: '全屏',
    exitFullscreen: '退出全屏',
    size: '布局大小',
    sizeDefault: '默认',
    sizeMedium: '中等',
    sizeSmall: '小型',
    sizeMini: '超小',
    theme: '主题设置',
    themeLight: '浅色主题',
    themeDark: '深色主题',
    language: '切换语言',
    search: '菜单搜索'
  },

  // 标签页
  tagsView: {
    refreshPage: '刷新页面',
    closeCurrent: '关闭当前',
    closeOthers: '关闭其他',
    closeAll: '全部关闭',
    closeLeft: '关闭左侧',
    closeRight: '关闭右侧'
  },

  // 设置
  settings: {
    title: '系统配置',
    platformName: '平台名称',
    platformNamePlaceholder: '请输入平台名称',
    platformLogo: '平台logo',
    platformLogoPlaceholder: '请输入平台Logo',
    topNav: '开启 TopNav',
    tagsView: '开启 Tags-Views',
    fixedHeader: '固定 Header',
    sidebarLogo: '侧边栏 Logo'
  },

  // 输入框提示
  placeholder: {
    username: '请输入账号',
    password: '请输入密码',
    captcha: '请输入验证码',
    userName: '请输入用户名称',
    nickName: '请输入用户昵称',
    phonenumber: '请输入手机号码',
    email: '请输入邮箱',
    deptName: '请输入部门名称',
    roleName: '请输入角色名称',
    postName: '请输入岗位名称',
    dictType: '请输入字典名称',
    configName: '请输入参数名称',
    menuName: '请输入菜单名称',
    noticeTitle: '请输入公告标题',
    search: '请输入关键字',
    startDate: '开始日期',
    endDate: '结束日期',
    selectDept: '请选择归属部门',
    selectPost: '请选择岗位',
    selectRole: '请选择角色',
    selectGender: '请选择性别',
    selectStatus: '请选择状态',
    select: '请选择',
    input: '请输入'
  },

  // 表单验证提示
  validation: {
    required: '{field}不能为空',
    email: '请输入正确的邮箱地址',
    phone: '请输入正确的手机号码',
    number: '请输入数字',
    minLength: '长度不能少于{min}个字符',
    maxLength: '长度不能超过{max}个字符'
  },

  // 按钮文本
  button: {
    search: '查询',
    reset: '重置',
    add: '新增',
    edit: '编辑',
    delete: '删除',
    import: '导入',
    export: '导出',
    download: '下载',
    upload: '上传',
    submit: '确 定',
    cancel: '取 消',
    close: '关 闭',
    save: '保 存',
    more: '更多',
    refresh: '刷新',
    expand: '展开',
    collapse: '收起',
    back: '返回',
    view: '查看',
    operate: '操作',
    confirm: '确认',
    select: '选择',
    clear: '清空',
    generate: '生成',
    preview: '预览',
    copy: '复制',
    move: '移动',
    enable: '启用',
    disable: '停用',
    start: '启动',
    stop: '停止',
    run: '执行',
    detail: '详情',
    log: '日志',
    auth: '分配',
    resetPwd: '重置密码',
    authRole: '分配角色',
    authUser: '分配用户'
  },

  // 表格列标题
  table: {
    userId: '用户编号',
    userName: '用户名称',
    nickName: '用户昵称',
    deptName: '部门',
    phonenumber: '手机号码',
    status: '状态',
    createTime: '创建时间',
    updateTime: '更新时间',
    operate: '操作',
    roleId: '角色编号',
    roleName: '角色名称',
    roleKey: '权限字符',
    roleSort: '显示顺序',
    deptId: '部门编号',
    parentDept: '上级部门',
    orderNum: '显示排序',
    leader: '负责人',
    postId: '岗位编号',
    postName: '岗位名称',
    postCode: '岗位编码',
    dictId: '字典主键',
    dictName: '字典名称',
    dictType: '字典类型',
    configId: '参数主键',
    configName: '参数名称',
    configKey: '参数键名',
    configValue: '参数键值',
    menuId: '菜单编号',
    menuName: '菜单名称',
    icon: '图标',
    path: '路径',
    component: '组件',
    perms: '权限标识',
    visible: '显示状态',
    isFrame: '是否外链',
    isCache: '是否缓存',
    noticeId: '公告编号',
    noticeTitle: '公告标题',
    noticeType: '公告类型',
    noticeContent: '公告内容',
    loginTime: '登录时间',
    ipaddr: '登录IP',
    loginLocation: '登录地点',
    browser: '浏览器',
    os: '操作系统',
    operId: '日志编号',
    title: '系统模块',
    businessType: '操作类型',
    method: '请求方法',
    requestMethod: '请求方式',
    operName: '操作人员',
    operUrl: '请求地址',
    operIp: '操作地址',
    operParam: '请求参数',
    jsonResult: '返回参数',
    errorMsg: '错误消息',
    operTime: '操作时间',
    costTime: '消耗时间'
  },

  // 表单标签
  form: {
    userName: '用户名称',
    nickName: '用户昵称',
    dept: '归属部门',
    phonenumber: '手机号码',
    email: '邮箱',
    password: '用户密码',
    gender: '用户性别',
    status: '状态',
    post: '岗位',
    role: '角色',
    remark: '备注',
    roleName: '角色名称',
    roleKey: '权限字符',
    roleSort: '角色顺序',
    dataScope: '数据权限',
    menuPermissions: '菜单权限',
    deptName: '部门名称',
    parentDept: '上级部门',
    showOrder: '显示排序',
    leader: '负责人',
    contactPhone: '联系电话',
    postName: '岗位名称',
    postCode: '岗位编码',
    postSort: '岗位顺序',
    dictName: '字典名称',
    dictType: '字典类型',
    dictLabel: '数据标签',
    dictValue: '数据键值',
    dictSort: '显示排序',
    isDefault: '是否默认',
    listClass: '回显样式',
    configName: '参数名称',
    configKey: '参数键名',
    configValue: '参数键值',
    menuName: '菜单名称',
    parentMenu: '上级菜单',
    menuType: '菜单类型',
    menuIcon: '菜单图标',
    menuSort: '显示排序',
    isFrame: '是否外链',
    isCache: '是否缓存',
    visible: '显示状态',
    routePath: '路由地址',
    componentPath: '组件路径',
    perms: '权限标识',
    noticeTitle: '公告标题',
    noticeType: '公告类型',
    status: '状态',
    noticeContent: '内容',
    newPassword: '新密码'
  },

  // 提示消息
  message: {
    addSuccess: '新增成功',
    editSuccess: '修改成功',
    deleteSuccess: '删除成功',
    saveSuccess: '保存成功',
    submitSuccess: '提交成功',
    importSuccess: '导入成功',
    exportSuccess: '导出成功',
    uploadSuccess: '上传成功',
    downloadSuccess: '下载成功',
    copySuccess: '复制成功',
    operationSuccess: '操作成功',
    addFailed: '新增失败',
    editFailed: '修改失败',
    deleteFailed: '删除失败',
    saveFailed: '保存失败',
    submitFailed: '提交失败',
    importFailed: '导入失败',
    uploadFailed: '上传失败',
    operationFailed: '操作失败',
    confirmDelete: '是否确认删除',
    confirmClear: '是否确认清空',
    confirmLogout: '确定要退出登录吗',
    confirmExport: '确认要导出吗',
    selectOne: '请选择一条数据',
    selectAtLeastOne: '请至少选择一条数据',
    selectRecord: '请选择要操作的数据',
    inputRequired: '请输入',
    selectRequired: '请选择',
    deleteConfirm: '删除后无法恢复，是否确认删除',
    enableSuccess: '启用成功',
    disableSuccess: '停用成功',
    resetPwdSuccess: '重置密码成功，新密码是：',
    noData: '暂无数据',
    loading: '加载中...',
    clearingCache: '正在清除设置缓存并刷新，请稍候...',
    systemError: '系统错误',
    networkError: '网络错误',
    timeout: '请求超时',
    invalidFileType: '文件格式不正确',
    fileTooLarge: '文件大小超出限制'
  },

  // 确认对话框
  confirm: {
    title: '系统提示',
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    delete: '确认要删除选中的数据项吗？',
    clear: '确认要清空所有数据吗？',
    logout: '确定注销并退出系统吗？',
    export: '确认要导出数据吗？',
    import: '确认要导入数据吗？',
    enable: '确认要启用该数据项吗？',
    disable: '确认要停用该数据项吗？',
    resetPwd: '请输入"{username}"的新密码',
    authRole: '确认要分配角色吗？',
    authUser: '确认要分配用户吗？',
    runTask: '确认要立即执行该任务吗？',
    clean: '确认要清空缓存吗？',
    forceLogout: '确认要强退该用户吗？'
  },

  // 验证规则提示
  rules: {
    userNameRequired: '用户名称不能为空',
    userNameLength: '用户名称长度必须介于 2 和 20 之间',
    nickNameRequired: '用户昵称不能为空',
    passwordRequired: '用户密码不能为空',
    passwordLength: '用户密码长度必须介于 5 和 20 之间',
    emailInvalid: '请输入正确的邮箱地址',
    phoneInvalid: '请输入正确的手机号码',
    deptRequired: '归属部门不能为空',
    roleNameRequired: '角色名称不能为空',
    roleKeyRequired: '权限字符不能为空',
    roleSortRequired: '角色顺序不能为空',
    deptNameRequired: '部门名称不能为空',
    postNameRequired: '岗位名称不能为空',
    postCodeRequired: '岗位编码不能为空',
    dictNameRequired: '字典名称不能为空',
    dictTypeRequired: '字典类型不能为空',
    configNameRequired: '参数名称不能为空',
    configKeyRequired: '参数键名不能为空',
    menuNameRequired: '菜单名称不能为空',
    pathRequired: '路由地址不能为空',
    noticeTitleRequired: '公告标题不能为空',
    noticeContentRequired: '公告内容不能为空'
  },

  // 系统管理模块
  system: {
    user: {
      title: '用户管理',
      importTitle: '用户导入',
      importTip: '将文件拖到此处，或',
      importClick: '点击上传',
      importFormat: '仅允许导入xls、xlsx格式文件',
      updateSupport: '是否更新已经存在的用户数据',
      downloadTemplate: '下载模板',
      importResult: '导入结果',
      resetPwdTitle: '提示',
      resetPwdConfirm: '确定',
      resetPwdCancel: '取消',
      pwdLengthError: '用户密码长度必须介于 5 和 20 之间'
    },
    role: {
      title: '角色管理',
      authUserTitle: '分配用户',
      selectUser: '选择用户',
      dataScope1: '全部数据权限',
      dataScope2: '自定义数据权限',
      dataScope3: '本部门数据权限',
      dataScope4: '本部门及以下数据权限',
      dataScope5: '仅本人数据权限'
    },
    dept: {
      title: '部门管理',
      expandAll: '展开/折叠',
      selectParent: '选择上级部门'
    },
    post: {
      title: '岗位管理'
    },
    dict: {
      title: '字典管理',
      dataTitle: '字典数据',
      addData: '新增字典数据',
      editData: '修改字典数据'
    },
    config: {
      title: '参数管理'
    },
    menu: {
      title: '菜单管理',
      expandAll: '展开/折叠',
      selectIcon: '选择图标',
      directory: '目录',
      menu: '菜单',
      button: '按钮',
      parentMenu: '主类目'
    },
    notice: {
      title: '通知公告',
      addTitle: '添加公告',
      editTitle: '修改公告'
    },
    log: {
      operlog: {
        title: '操作日志',
        detailTitle: '操作日志详细',
        operId: '操作序号',
        operModule: '操作模块',
        operType: '操作类型',
        operDesc: '操作描述'
      },
      logininfor: {
        title: '登录日志',
        unlock: '解锁',
        unlockSuccess: '解锁成功'
      }
    }
  },

  // 监控模块
  monitor: {
    online: {
      title: '在线用户',
      forceLogout: '强退',
      forceLogoutConfirm: '确认要强退该用户吗？',
      forceLogoutSuccess: '强退成功',
      sessionId: '会话编号',
      loginName: '登录名称',
      deptName: '部门名称',
      host: '主机',
      loginTime: '登录时间'
    },
    job: {
      title: '定时任务',
      addTitle: '新增任务',
      editTitle: '修改任务',
      cronExpression: 'Cron表达式',
      jobName: '任务名称',
      jobGroup: '任务分组',
      invokeTarget: '调用目标字符串',
      cronExpression: 'Cron执行表达式',
      misfirePolicy: '执行策略',
      concurrent: '是否并发',
      status: '状态',
      runOnce: '执行一次',
      log: '日志',
      logTitle: '调度日志',
      logDetail: '日志详细',
      jobLogId: '日志编号',
      jobMessage: '日志信息',
      isConcurrent: '是否并发执行',
      runOnceConfirm: '确认要立即执行一次该任务吗？',
      runSuccess: '执行成功'
    },
    server: {
      title: '服务监控',
      cpu: 'CPU',
      memory: '内存',
      disk: '磁盘',
      serverInfo: '服务器信息',
      jvmInfo: 'JVM信息',
      diskInfo: '磁盘状态',
      cpuUsage: 'CPU使用率',
      memoryUsage: '内存使用率',
      runTime: '运行时长',
      startTime: '启动时间'
    },
    cache: {
      title: '缓存监控',
      cacheName: '缓存名称',
      cacheKey: '缓存键名',
      cacheValue: '缓存内容',
      clear: '清空缓存',
      clearSuccess: '清空成功',
      list: '缓存列表',
      keyList: '键名列表'
    }
  },

  // 首页/仪表盘
  dashboard: {
    title: '首页',
    projectOverview: '项目总览',
    deviceStatus: '设备状态',
    alarmInfo: '报警信息',
    energyStatistic: '今日用能统计',
    energyTrend: '今日能源趋势',
    carbonEmissions: '今日碳排放量',
    projectTotal: '项目总数',
    meterTotal: '仪表总数',
    alarmTotal: '报警记录',
    normalDevice: '正常设备',
    alarmDevice: '报警设备',
    offlineDevice: '离线设备',
    proportion: '占比',
    electricity: '电(kW·h)',
    comprehensiveEnergy: '综合能耗(kgce)',
    carbonEmission: '今日碳排放量(kg)',
    totalEnergy: '综合能耗',
    water: '水',
    time: '时间',
    more: '更多>>',
    chargingUser: '充电用户',
    chargingAmount: '充电金额',
    chargingEnergy: '充电电量',
    chargingCount: '充电次数',
    totalUser: '累计用户',
    totalAmount: '累计金额',
    totalEnergyKwh: '累计电量',
    totalCount: '累计次数',
    monthOverMonth: '环比增长',
    yearOverYear: '同比增长',
    unitUser: '户',
    unitYuan: '元',
    unitKwh: 'kW·h',
    unitCount: '次',
    chargingTerminalStatus: '充电终端状态',
    chargingOrderTrend: '充电订单趋势',
    last7DaysCount: '近7日充电次数',
    last7DaysDuration: '近7日充电时长',
    last7DaysEnergy: '近7日充电电量',
    last7DaysAmount: '近7日充电金额',
    duration: '充电时长',
    energy: '充电电量',
    amount: '充电金额',
    thisMonth: '本月',
    thisYear: '今年',
    day: '日',
    month: '月'
  },

  // 能源管理模块
  energyModule: {
    title: '能源管理',
    // 能源类型
    energyType: {
      electricity: '电力',
      water: '水',
      steam: '蒸汽',
      gas: '燃气',
      oil: '燃油'
    },
    // 批次管理
    batch: {
      title: '批次管理',
      batchId: '批次ID',
      productName: '产品名称',
      batchNumber: '批次号',
      productionDate: '生产日期',
      productionLine: '生产线',
      energyConsumption: '能耗',
      expectedOutput: '预计产量',
      unit: '单位',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchBatch: '请输入批次号/产品名称',
        selectDate: '请选择生产日期'
      }
    },
    // 能效标杆管理
    benchmark: {
      title: '能效标杆管理',
      standardId: '标准ID',
      standardName: '标准名称',
      energyType: '能源类型',
      benchmarkValue: '标杆值',
      unit: '单位',
      industry: '所属行业',
      region: '适用地区',
      effectiveDate: '生效日期',
      status: '状态',
      remark: '备注',
      placeholder: {
        searchStandard: '请输入标准名称',
        selectType: '请选择能源类型'
      }
    },
    // 能源平衡管理
    balance: {
      title: '能源平衡管理',
      balanceId: '平衡ID',
      totalSupply: '总供应量',
      totalConsumption: '总消耗量',
      balanceAmount: '平衡量',
      balanceRate: '平衡率',
      balanceDate: '平衡日期',
      calculate: '计算能源平衡'
    },
    // 能源质量管理
    quality: {
      title: '能源质量管理',
      qualityId: '记录ID',
      qualityIndex: '质量指标',
      standardValue: '标准值',
      actualValue: '实际值',
      deviation: '偏差',
      detectionTime: '检测时间'
    },
    // 能耗分析
    analysis: {
      title: '能耗分析',
      trend: '能耗趋势',
      comparison: '能耗对比',
      ranking: '能耗排名',
      report: '分析报告'
    }
  },

  // 充电桩模块
  chargingModule: {
    title: '充电桩管理',
    // 充电站管理
    station: {
      title: '充电站管理',
      stationId: '站点ID',
      stationName: '充电站名称',
      stationCode: '站点编码',
      stationType: '站点类型',
      price: '电站电价',
      merchant: '归属商户',
      merchantName: '商户名称',
      address: '电站地址',
      status: '电站状态',
      longitude: '经度',
      latitude: '纬度',
      openTime: '开放时间',
      parkFee: '停车费',
      serviceFee: '服务费',
      contactName: '联系人',
      contactPhone: '联系电话',
      createTime: '创建时间',
      updateTime: '更新时间',
      remark: '备注',
      placeholder: {
        searchStation: '请输入充电站名称',
        searchMerchant: '请输入商户名称',
        selectType: '请选择站点类型',
        selectStatus: '请选择站点状态',
        inputAddress: '请输入电站地址'
      }
    },
    // 充电桩管理
    pile: {
      title: '充电桩管理',
      pileId: '充电桩ID',
      pileName: '充电桩名称',
      pileCode: '充电桩编码',
      pileType: '充电桩类型',
      pileModel: '充电桩型号',
      station: '所属站点',
      stationName: '站点名称',
      status: '充电桩状态',
      power: '充电功率',
      voltage: '额定电压',
      current: '额定电流',
      connectorType: '接口类型',
      connectorCount: '接口数量',
      gunNumber: '枪号',
      gunStatus: '枪状态',
      manufacturer: '制造商',
      productionDate: '生产日期',
      installationDate: '安装日期',
      lastMaintainTime: '最后维护时间',
      createTime: '创建时间',
      remark: '备注',
      encoding: '终端编码',
      name: '终端名称',
      merchantName: '商户名称',
      workStatus: '工作状态',
      batchEnable: '批量启用',
      batchDisable: '批量停用',
      selectBrandFirst: '请先选择品牌',
      addTitle: '添加充电桩信息',
      editTitle: '修改充电桩信息',
      deleteConfirm: '是否确认删除充电桩信息编号为"{ids}"的数据项？',
      batchEnableConfirm: '是否确认批量启用所选终端？',
      batchDisableConfirm: '是否确认批量停用所选终端？',
      batchEnableSuccess: '批量启用成功',
      batchDisableSuccess: '批量停用成功',
      statusConfirm: '确认要{text}该充电桩吗？',
      placeholder: {
        searchPile: '请输入充电桩名称/编码',
        selectStation: '请选择所属站点',
        selectType: '请选择充电桩类型',
        selectStatus: '请选择充电桩状态'
      }
    },
    // 品牌管理
    brand: {
      title: '品牌管理',
      id: '品牌编号',
      brandName: '品牌名称',
      status: '品牌状态',
      addTitle: '添加品牌信息',
      editTitle: '修改品牌信息',
      deleteConfirm: '是否确认删除品牌信息编号为"{ids}"的数据项？',
      statusConfirm: '确认要{text}该品牌吗？',
      // 型号管理
      modelId: '型号编号',
      modelName: '型号名称',
      modelStatus: '型号状态',
      addModelTitle: '添加型号信息',
      editModelTitle: '修改型号信息',
      deleteModelConfirm: '是否确认删除型号信息编号为"{ids}"的数据项？',
      modelStatusConfirm: '确认要{text}该型号吗？'
    },
    // 商户管理
    merchant: {
      title: '商户管理',
      name: '商户名称',
      type: '商户类型',
      contact: '联系方式',
      address: '商户地址',
      status: '商户状态',
      managePile: '管理电桩',
      addTitle: '添加商户信息',
      editTitle: '修改商户信息',
      deleteConfirm: '是否确认删除商户信息编号为"{ids}"的数据项？',
      statusConfirm: '确认要{text}该商户吗？'
    },
    // 充电订单
    order: {
      title: '充电订单',
      orderNo: '订单编号',
      userName: '用户名称',
      phone: '电话',
      merchantName: '商户名称',
      stationName: '充电站名称',
      pileName: '充电桩名称',
      carNo: '车牌号',
      carVin: 'VIN码',
      chargeMethod: '充电方式',
      orderSource: '订单来源',
      settleType: '结算类型',
      orderStatus: '订单状态',
      createTime: '下单时间',
      startTime: '充电开始时间',
      endTime: '充电结束时间',
      settleTime: '结算时间',
      settlePrice: '结算金额',
      paidPrice: '实际支付金额',
      discountAmt: '优惠金额',
      elecAmt: '电费',
      serveAmt: '服务费',
      chargeDuration: '充电时长',
      energy: '总充电量',
      settleBalance: '账户余额',
      payType: '支付方式',
      abnoCause: '异常原因',
      detail: '详情',
      process: '处理',
      addTitle: '添加订单信息',
      editTitle: '修改订单信息',
      deleteConfirm: '是否确认删除订单信息编号为"{ids}"的数据项？'
    },
      title: '充电订单',
      orderId: '订单ID',
      orderNo: '订单编号',
      userId: '用户ID',
      userName: '用户名称',
      userPhone: '用户手机号',
      stationName: '充电站',
      pileName: '充电桩',
      gunNumber: '充电枪号',
      startTime: '开始时间',
      endTime: '结束时间',
      chargeDuration: '充电时长',
      chargeAmount: '充电电量',
      chargePower: '充电功率',
      totalAmount: '订单金额',
      electricityFee: '电费',
      serviceFee: '服务费',
      discountAmount: '优惠金额',
      payAmount: '实付金额',
      payStatus: '支付状态',
      payTime: '支付时间',
      payMethod: '支付方式',
      orderStatus: '订单状态',
      plateNumber: '车牌号',
      vinCode: 'VIN码',
      socStart: '开始SOC',
      socEnd: '结束SOC',
      startMeterReading: '起始电表读数',
      endMeterReading: '结束电表读数',
      createTime: '创建时间',
      placeholder: {
        searchOrder: '请输入订单编号',
        searchUser: '请输入用户名称/手机号',
        selectStation: '请选择充电站',
        selectStatus: '请选择订单状态',
        selectPayStatus: '请选择支付状态'
      }
    },
    // 计费策略
    price: {
      title: '计费策略',
      strategyId: '策略ID',
      strategyName: '策略名称',
      strategyType: '计费方式',
      timeType: '时段类型',
      startTime: '开始时间',
      endTime: '结束时间',
      electricityPrice: '电价',
      servicePrice: '服务费',
      parkPrice: '停车费',
      maxPrice: '封顶价格',
      minPrice: '最低价格',
      minDuration: '最小时长',
      maxDuration: '最大时长',
      effectiveDate: '生效日期',
      expiryDate: '失效日期',
      applicableStations: '适用站点',
      status: '状态',
      remark: '备注',
      placeholder: {
        searchStrategy: '请输入策略名称',
        selectType: '请选择计费方式',
        selectStation: '请选择适用站点'
      }
    },
    // 商户管理
    merchant: {
      title: '商户管理',
      merchantId: '商户ID',
      merchantName: '商户名称',
      merchantCode: '商户编码',
      contactPerson: '联系人',
      contactPhone: '联系电话',
      email: '邮箱',
      address: '地址',
      businessLicense: '营业执照',
      accountName: '账户名称',
      accountBank: '开户银行',
      accountNo: '银行账号',
      settlementCycle: '结算周期',
      commissionRate: '佣金比例',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchMerchant: '请输入商户名称'
      }
    },
    // 品牌管理
    brand: {
      title: '品牌管理',
      brandId: '品牌ID',
      brandName: '品牌名称',
      brandCode: '品牌编码',
      brandLogo: '品牌Logo',
      manufacturer: '制造商',
      country: '所属国家',
      website: '官网地址',
      sort: '排序',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchBrand: '请输入品牌名称'
      }
    },
    // 占位订单
    occupancyOrder: {
      title: '占位订单',
      occupancyNo: '占位订单编号',
      orderNo: '充电订单号',
      settleStatus: '结算状态',
      orderStatus: '订单状态',
      duration: '占位时长',
      fee: '占位费用',
      isFee: '是否产生占位费',
      payTime: '支付时间',
      endReason: '结束原因',
      orderInfo: '关联订单信息',
      userName: '用户名称',
      phone: '电话',
      merchantName: '商户名称',
      stationName: '充电站名称',
      pileName: '充电桩名称',
      yes: '是',
      no: '否',
      addTitle: '添加占位订单信息',
      editTitle: '修改占位订单信息',
      deleteConfirm: '是否确认删除占位订单信息编号为"{ids}"的数据项？',
      validate: {
        idRequired: '占位订单ID不能为空',
        occupancyNoRequired: '占位订单编号不能为空',
        orderNoRequired: '充电订单号不能为空',
        durationRequired: '占位时长不能为空',
        feeRequired: '占位费用不能为空',
        isFeeRequired: '是否产生占位费不能为空',
        payTimeRequired: '支付时间不能为空',
        settleStatusRequired: '结算状态不能为空',
        orderStatusRequired: '占位订单状态不能为空',
        endReasonRequired: '结束原因不能为空'
      }
    },
    // 订单详情
    orderDetail: {
      title: '订单详情',
      orderInfo: '订单信息',
      pileInfo: '充电桩信息',
      terminalCode: '终端编码',
      terminalName: '终端名称',
      brand: '品牌',
      model: '型号',
      station: '归属电站',
      merchant: '归属商户',
      contactPhone: '联系电话',
      stationAddress: '电站地址',
      demandVoltage: '需求电压/实际电压',
      demandCurrent: '需求电流/实际电流',
      soc: 'SOC',
      batteryTemp: '电池温度',
      currentUnit: '电流/A',
      socUnit: 'SOC/%',
      tempUnit: '温度/℃',
      voltageUnit: '电压/V',
      yuan: '元',
      kwh: 'kW·h',
      hour: 'h'
    },

  // 巡检管理模块
  inspectionModule: {
    title: '巡检管理',
    // 巡检任务
    task: {
      title: '巡检任务',
      taskId: '任务ID',
      taskName: '任务名称',
      taskCode: '任务编号',
      planId: '所属计划',
      planName: '巡更计划',
      pathId: '巡更路线',
      pathName: '路线名称',
      userId: '巡更人员',
      userName: '巡更人员',
      inspector: '巡检人',
      inspectDate: '巡更日期',
      startTime: '开始时间',
      endTime: '结束时间',
      deadline: '截止时间',
      taskStatus: '任务状态',
      pointCount: '巡检点数量',
      checkedCount: '已检数量',
      abnormalCount: '异常数量',
      completionRate: '完成率',
      checkResult: '巡检结果',
      remark: '备注',
      placeholder: {
        searchTask: '请输入任务名称',
        selectPlan: '请选择巡更计划',
        selectPath: '请选择巡更路线',
        selectUser: '请选择巡更人员',
        selectStatus: '请选择任务状态'
      }
    },
    // 巡检计划
    plan: {
      title: '巡检计划',
      planId: '计划ID',
      planName: '计划名称',
      planCode: '计划编号',
      frequency: '巡更频率',
      cycleType: '周期类型',
      cycleDay: '周期天数',
      startTime: '开始时间',
      endTime: '结束时间',
      pathId: '巡更路线',
      pathName: '路线名称',
      executorType: '执行人类型',
      executor: '执行人',
      remindTime: '提醒时间',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchPlan: '请输入计划名称',
        selectPath: '请选择巡更路线',
        selectFrequency: '请选择巡更频率',
        selectStatus: '请选择状态'
      }
    },
    // 巡检路线
    path: {
      title: '巡检路线',
      pathId: '路线ID',
      pathName: '路线名称',
      pathCode: '路线编号',
      pointCount: '巡检点数量',
      estimatedTime: '预计时长',
      estimatedDistance: '预计距离',
      region: '所属区域',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchPath: '请输入路线名称',
        selectRegion: '请选择所属区域'
      }
    },
    // 巡检点
    point: {
      title: '巡检点',
      pointId: '点位ID',
      pointName: '点位名称',
      pointCode: '点位编号',
      pointType: '点位类型',
      location: '点位位置',
      region: '所属区域',
      longitude: '经度',
      latitude: '纬度',
      rfidCode: 'RFID编码',
      qrCode: '二维码',
      checkItem: '检查项',
      checkStandard: '检查标准',
      normalValue: '正常值范围',
      deviceId: '关联设备',
      deviceName: '设备名称',
      photoRequired: '是否拍照',
      remarkRequired: '是否必填备注',
      sort: '排序',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchPoint: '请输入点位名称',
        selectType: '请选择点位类型',
        selectRegion: '请选择所属区域'
      }
    },
    // 巡检记录
    record: {
      title: '巡检记录',
      recordId: '记录ID',
      taskId: '任务ID',
      taskName: '任务名称',
      pointId: '巡检点',
      pointName: '点位名称',
      checkTime: '巡检时间',
      checkResult: '巡检结果',
      checkValue: '检查值',
      isNormal: '是否正常',
      normal: '正常',
      abnormal: '异常',
      abnormalDesc: '异常描述',
      abnormalType: '异常类型',
      abnormalLevel: '异常级别',
      handler: '处理人',
      handleTime: '处理时间',
      handleResult: '处理结果',
      handleStatus: '处理状态',
      photo: '现场照片',
      signPhoto: '签到照片',
      remark: '备注',
      placeholder: {
        searchRecord: '请输入点位名称',
        selectResult: '请选择巡检结果',
        selectStatus: '请选择处理状态'
      }
    },
    // 巡检告警
    alarm: {
      title: '巡检告警',
      alarmId: '告警ID',
      alarmTitle: '告警标题',
      alarmType: '告警类型',
      alarmLevel: '告警级别',
      alarmContent: '告警内容',
      alarmTime: '告警时间',
      pointId: '巡检点',
      pointName: '点位名称',
      pathName: '路线名称',
      taskName: '任务名称',
      handler: '处理人',
      handleTime: '处理时间',
      handleResult: '处理结果',
      handleStatus: '处理状态',
      status: '告警状态',
      createTime: '创建时间',
      placeholder: {
        searchAlarm: '请输入告警标题',
        selectType: '请选择告警类型',
        selectLevel: '请选择告警级别',
        selectStatus: '请选择处理状态'
      }
    }
  },

  // 危化品管理模块
  hazardousModule: {
    title: '危化品管理',
    // 危化品信息
    goods: {
      title: '危化品信息',
      goodsId: '危化品ID',
      goodsName: '危化品名称',
      goodsCode: '危化品编码',
      goodsType: '危化品类型',
      goodsCategory: '危化品类别',
      status: '危化品状态',
      specification: '规格型号',
      casNo: 'CAS号',
      unCode: 'UN编号',
      formula: '分子式',
      molecularWeight: '分子量',
      appearance: '外观性状',
      density: '密度',
      meltingPoint: '熔点',
      boilingPoint: '沸点',
      flashPoint: '闪点',
      storageTemp: '储存温度',
      hazardClass: '危险类别',
      hazardProperties: '危险特性',
      storageRequirements: '储存要求',
      handlingPrecautions: '操作注意事项',
      emergencyMeasures: '应急措施',
      fireFightingMeasures: '消防措施',
      firstAidMeasures: '急救措施',
      supplier: '供应商',
      manufacturer: '生产厂家',
      storageLocation: '存放位置',
      safetyStock: '安全库存',
      maxStock: '最大库存',
      unit: '单位',
      validityPeriod: '有效期',
      msdsFile: 'MSDS文件',
      labelFile: '标签文件',
      warningSign: '警示标志',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchGoods: '请输入危化品名称/CAS号',
        selectType: '请选择危化品类型',
        selectCategory: '请选择危化品类别',
        selectStatus: '请选择状态'
      }
    },
    // 危化品库存
    inventory: {
      title: '危化品库存',
      inventoryId: '库存ID',
      goodsId: '危化品',
      goodsName: '危化品名称',
      goodsCode: '危化品编码',
      goodsType: '危化品类型',
      batchNo: '批次号',
      quantity: '库存数量',
      availableQuantity: '可用数量',
      lockedQuantity: '锁定数量',
      unit: '单位',
      storageLocation: '存放位置',
      storageArea: '存放区域',
      storageCabinet: '存放柜号',
      safetyStock: '安全库存',
      maxStock: '最大库存',
      expiryDate: '有效期至',
      productionDate: '生产日期',
      supplier: '供应商',
      status: '状态',
      createTime: '创建时间',
      remark: '备注',
      placeholder: {
        searchInventory: '请输入危化品名称',
        selectLocation: '请选择存放位置',
        selectStatus: '请选择状态'
      }
    },
    // 入库管理
    stockIn: {
      title: '入库管理',
      inId: '入库ID',
      inNo: '入库单号',
      inType: '入库类型',
      inTime: '入库时间',
      goodsName: '危化品名称',
      goodsCode: '危化品编码',
      batchNo: '批次号',
      inQuantity: '入库数量',
      unit: '单位',
      storageLocation: '存放位置',
      supplier: '供应商',
      productionDate: '生产日期',
      expiryDate: '有效期至',
      operator: '入库人',
      operatorName: '入库人姓名',
      approver: '审批人',
      approveTime: '审批时间',
      approveStatus: '审批状态',
      remark: '备注',
      placeholder: {
        searchInNo: '请输入入库单号',
        selectType: '请选择入库类型',
        selectStatus: '请选择审批状态'
      }
    },
    // 出库管理
    stockOut: {
      title: '出库管理',
      outId: '出库ID',
      outNo: '出库单号',
      outType: '出库类型',
      outTime: '出库时间',
      goodsName: '危化品名称',
      goodsCode: '危化品编码',
      batchNo: '批次号',
      outQuantity: '出库数量',
      unit: '单位',
      recipient: '领用人',
      recipientDept: '领用部门',
      recipientPhone: '领用人电话',
      usePurpose: '用途',
      returnTime: '归还时间',
      operator: '出库人',
      approver: '审批人',
      approveTime: '审批时间',
      approveStatus: '审批状态',
      remark: '备注',
      placeholder: {
        searchOutNo: '请输入出库单号',
        selectType: '请选择出库类型',
        selectStatus: '请选择审批状态'
      }
    },
    // 库存预警
    warning: {
      title: '库存预警',
      warningId: '预警ID',
      warningType: '预警类型',
      warningLevel: '预警级别',
      goodsName: '危化品名称',
      currentQuantity: '当前库存',
      safetyStock: '安全库存',
      maxStock: '最大库存',
      warningTime: '预警时间',
      handler: '处理人',
      handleTime: '处理时间',
      handleStatus: '处理状态',
      placeholder: {
        searchGoods: '请输入危化品名称',
        selectType: '请选择预警类型',
        selectLevel: '请选择预警级别',
        selectStatus: '请选择处理状态'
      }
    }
  },
  // 个人中心模块
  profileModule: {
    title: '个人中心',
    personalInfo: '个人信息',
    myOrder: '我的工单',
    myInspection: '我的巡检',
    orderCountMonthly: '工单数量（月度）',
    inspectionCountMonthly: '巡检数量（月度）',
    userName: '用户名称',
    nickName: '用户昵称',
    sex: '性别',
    phone: '手机号码',
    email: '用户邮箱',
    dept: '所属部门',
    post: '所在岗位',
    role: '所属角色',
    createTime: '创建时间',
    editInfo: '修改信息',
    resetPwd: '重置密码',
    pending: '待处理',
    completed: '已完成',
    male: '男',
    female: '女',
    unknown: '未知',
    oldPassword: '旧密码',
    newPassword: '新密码',
    confirmPassword: '确认密码',
    enterOldPwd: '请输入旧密码',
    enterNewPwd: '请输入新密码',
    confirmNewPwd: '请确认新密码',
    pwdNotEmpty: '旧密码不能为空',
    pwdLength: '长度在 6 到 20 个字符',
    confirmPwdNotEmpty: '确认密码不能为空',
    pwdMismatch: '两次输入的密码不一致',
    modifySuccess: '修改成功',
    chart: {
      quantity: '数量',
      month: '月份',
      finished: '已完成',
      unfinished: '未完成'
    },
    placeholder: {
      inputOldPwd: '请输入旧密码',
      inputNewPwd: '请输入新密码',
      confirmNewPwd: '请确认新密码'
    }
  },
  // 集控化功能模块
  controlModule: {
    title: '集控化功能',
    deviceManagement: '设备管理',
    areaControl: '区域控制',
    remoteOperation: '远程操作',
    controlLog: '控制日志',
    deviceControl: '设备控制',
    searchDevice: '搜索设备',
    deviceId: '设备ID',
    deviceName: '设备名称',
    deviceType: '设备类型',
    location: '位置',
    status: '状态',
    lastOnlineTime: '最后在线时间',
    action: '操作',
    control: '控制',
    online: '在线',
    offline: '离线',
    waterSourceArea: '水源水区域',
    middleStationArea: '中间站区域',
    highPoolArea: '高位水池区域',
    wellArea: '水井区域',
    open: '开启',
    close: '关闭',
    areaControlSuffix: '区域控制',
    operationType: '操作类型',
    targetDevice: '目标设备',
    operationParams: '操作参数',
    securityVerify: '安全验证',
    inputSecurityCode: '输入安全验证码',
    execute: '执行远程操作',
    reset: '重置',
    logId: '日志ID',
    operationTypeCol: '操作类型',
    target: '操作目标',
    operator: '操作人',
    operationTime: '操作时间',
    result: '操作结果',
    remark: '备注',
    success: '成功',
    fail: '失败',
    start: '启动',
    stop: '停止',
    restart: '重启',
    adjust: '调整参数',
    selectOperationType: '选择操作类型',
    selectDevice: '选择设备',
    inputParams: '输入操作参数',
    inputSecurityPwd: '输入安全密码',
    deviceNameLabel: '设备名称',
    controlCommand: '控制指令',
    selectCommand: '选择控制指令',
    params: '参数',
    inputParamsLabel: '输入参数',
    securityPwd: '安全密码',
    cancel: '取消',
    confirm: '确定',
    placeholder: {
      searchDevice: '搜索设备',
      selectOperationType: '选择操作类型',
      selectDevice: '选择设备',
      inputParams: '输入操作参数',
      inputSecurityCode: '输入安全验证码',
      selectCommand: '选择控制指令',
      inputParams: '输入参数',
      inputSecurityPwd: '输入安全密码'
    }
  },
  // 计量管理模块
  meteringModule: {
    title: '计量管理',
    meterManagement: '计量器具管理',
    calibrationPlan: '校准计划管理',
    searchMeter: '搜索计量器具',
    meterId: '计量器具ID',
    meterName: '计量器具名称',
    type: '类型',
    specification: '规格型号',
    installationLocation: '安装位置',
    status: '状态',
    lastCalibrationDate: '上次校准日期',
    nextCalibrationDate: '下次校准日期',
    action: '操作',
    detail: '详情',
    calibrate: '校准',
    planId: '计划ID',
    meter: '计量器具',
    planCalibrationDate: '计划校准日期',
    executor: '执行人',
    addPlan: '新增校准计划',
    calibrationRecord: '校准记录',
    startDate: '开始日期',
    endDate: '结束日期',
    selectStatus: '选择状态',
    all: '全部',
    pending: '待执行',
    executing: '执行中',
    completed: '已完成',
    cancelled: '已取消',
    pass: '合格',
    fail: '不合格',
    analysis: '校准数据分析',
    qualificationTrend: '校准合格率趋势',
    deviationDistribution: '偏差值分布',
    meterStatusStatistics: '计量器具状态统计',
    export: '导出记录',
    execute: '执行',
    viewMeterDetail: '查看计量器具',
    viewPlanDetail: '查看校准计划',
    viewRecordDetail: '查看校准记录',
    fillCompleteInfo: '请填写完整的计划信息',
    meterNotExist: '选择的计量器具不存在',
    planAdded: '校准计划已添加',
    startExecute: '开始执行校准计划',
    planCompleted: '校准计划已执行完成',
    exportSuccess: '校准记录已导出',
    placeholder: {
      searchMeter: '搜索计量器具',
      selectStatus: '选择状态',
      startDate: '开始日期',
      endDate: '结束日期',
      selectMeter: '选择计量器具',
      selectDate: '选择日期',
      inputExecutor: '输入执行人',
      inputRemark: '输入备注'
    }
  },
  dataBoardModule: {
    title: '数据看板',
    totalEnergy: '综合能耗',
    electricity: '电',
    water: '水',
    projectOverview: '项目总览',
    projectTotal: '项目总数',
    meterTotal: '仪表总数',
    alarmRecord: '报警记录',
    equipmentStatus: '设备状态',
    alarmInfo: '报警信息',
    todayEnergyTrend: '今日能源趋势',
    todayEnergyStatistics: '今日用能统计',
    electricityKwh: '电(kW·h)',
    comprehensiveEnergy: '综合能耗(kgce)',
    todayWaterUsage: '今日用水(t)',
    dailyPowerCurve: '日用电功率曲线',
    todayWaterInfo: '今日用水信息',
    normal: '正常',
    alarm: '报警',
    offline: '离线',
    energyPlatform: '能源平台',
    carbonDataVisualizationPlatform: '智碳数据可视化平台'
  },
  dispatchModule: {
    title: '调度管理',
    loadForecast: '负荷预测',
    priceForecast: '价格预测',
    weatherForecast: '天气预测',
    modelManagement: '模型管理',
    costSaving: '成本节约',
    efficiencyImprovement: '效率提升',
    emissionReduction: '碳排放减少',
    trendAnalysis: '趋势分析'
  },
  toolModule: {
    title: '系统工具',
    basicInfo: '基本信息',
    columnInfo: '字段信息',
    genInfo: '生成信息',
    fieldProperties: '组件属性',
    formProperties: '表单属性'
  },
  equipmentModule: {
    title: '设备管理',
    equipmentInfo: '设备信息',
    equipmentAlarm: '设备报警',
    equipmentData: '设备数据',
    equipmentDocuments: '设备资料'
  },
  dataQueryModule: {
    title: '数据查询',
    dailyRawData: '日原始数据',
    dailyPeakData: '逐日极值数据'
  },
  componentsModule: {
    title: '组件',
    icons: 'Icons',
    elementIcons: 'Element-UI Icons'
  },
  autoeeModule: {
    title: '自动化',
    stockInRecord: '入库记录',
    stockOutRecord: '出库记录'
  },
  alarmModule: {
    title: '报警管理',
    alarmAnalysis: '报警分析',
    alarmHistory: '报警历史',
    alarmRule: '报警规则',
    realtimeAlarm: '实时报警',
    paramName: '参数名称',
    alarmTime: '报警时间',
    alarmInfo: '报警信息',
    alarmLevel: '报警等级',
    alarmArea: '报警区域',
    alarmEquipment: '报警设备',
    alarmVal: '报警值',
    endTime: '结束时间',
    alarmParam: '报警参数',
    alarmCount: '报警数量',
    alarmType: '报警类型',
    eventType: '事件类型',
    condition1: '条件1',
    thresholdValue1: '阈值1',
    condition2: '条件2',
    thresholdValue2: '阈值2',
    userId: '提醒人',
    createOrderSwitch: '自动创建工单',
    alarmSwitch: '报警开关',
    alarmDesc: '报警描述',
    addAlarm: '添加实时报警',
    editAlarm: '修改实时报警',
    addAlarmRule: '添加报警规则',
    editAlarmRule: '修改报警规则'
  },
  analysisReportModule: {
    title: '分析报告',
    generateReport: '生成分析报告',
    print: '打印',
    reportName: '分析报告',
    energyStatistics: '1、用能统计',
    electricityUsage: '2、用电量',
    electricityFee: '3、电费',
    waterUsage: '4、用水量',
    waterFee: '5、水费',
    recurringRate: '6、复费率',
    electricityTotal: '本周期内，共计使用电力{value}kW·h，最大用电量{max}kW·h，最大负荷发生时间{date}。',
    electricityFeeTotal: '本周期内，共计使用电费￥{value} 元',
    waterTotal: '本周期内，共计使用水{value}t，最大用水量{max}t，最大流量发生时间{date}。',
    waterFeeTotal: '本周期内，共计使用水费￥{value} 元',
    suggestion: '建议：通过在变压器下端增多监测回路，收集末端数据，判断各个监测点的能耗情况，有无电量浪费现象。或将楼宇内照明等设备更换节能产品。降低能耗，实现节能减排。'
  },
  cameraModule: {
    title: '摄像头管理',
    realtimeVideo: '实时视频',
    cameraConfig: '摄像头配置',
    cameraName: '摄像头名称',
    cameraBrand: '品牌',
    cameraSn: '序列号',
    cameraIp: '摄像头IP',
    cameraPort: '端口',
    cameraUser: '用户名',
    cameraPassword: '密码',
    cameraType: '摄像头类型',
    cameraStatus: '状态',
    cameraLocation: '安装位置',
    addCamera: '添加摄像头',
    editCamera: '修改摄像头'
  },
  carbonAssetsModule: {
    title: '碳资产管理',
    carbonAnalysis: '碳排放分析',
    monthlyCarbon: '本月碳排放',
    yearlyCarbon: '本年碳排放',
    currentMonth: '当月',
    lastMonth: '上月同期',
    currentYear: '当年',
    lastYear: '去年同期',
    trend: '趋势',
    energyType: '分类能耗',
    year: '年份'
  },
  energyAnalysisModule: {
    title: '能源分析',
    energyOverview: '能源概览',
    energyFlow: '能流图',
    energyTrend: '用能趋势',
    yoyAnalysis: '同比分析',
    monAnalysis: '环比分析',
    lossAnalysis: '损耗分析',
    expenseBoard: '费用看板',
    expenseReport: '费用报表',
    monthOnMonth: '环比',
    todayEnergy: '今日用能',
    yesterdayEnergy: '昨日同期',
    currentMonthEnergy: '当月用能',
    lastMonthEnergy: '上月同期',
    currentYearEnergy: '今年用能',
    lastYearEnergy: '去年同期',
    trend: '趋势',
    area: '区域',
    energyType: '能源类型'
  },
  itemizedAnalysisModule: {
    title: '分项分析',
    itemizedOverview: '分项概览',
    energyType: '能源类型',
    date: '日期',
    search: '查询'
  },
  maintenanceModule: {
    title: '维护管理',
    duty: '值班管理',
    inspectionPlan: '巡检计划',
    inspectionRecord: '巡检记录',
    repairOrder: '维修工单',
    schedule: '排班管理'
  },
  quotaModule: {
    title: '配额管理',
    analysis: '配额分析',
    config: '配额配置',
    monitor: '配额监控'
  },
  systemModule: {
    title: '系统管理',
    config: '参数配置',
    dept: '部门管理',
    dict: '字典管理',
    menu: '菜单管理',
    notice: '公告通知',
    post: '岗位管理',
    role: '角色管理',
    user: '用户管理'
  },
  digitaltwinModule: {
    title: '数字孪生'
  },
  inventoryModule: {
    title: '库存管理',
    attachment: '附件管理',
    purveyor: '供应商管理'
  },
  managementSystemModule: {
    title: '管理系统',
    prePlan: '预案管理',
    process: '流程管理',
    regulation: '制度管理',
    standard: '标准管理'
  },
  monitorModule: {
    title: '系统监控',
    admin: '系统管理员',
    cache: '缓存监控',
    logininfor: '登录日志',
    online: '在线用户',
    operlog: '操作日志',
    xxljob: '定时任务'
  },
  newenergyModule: {
    title: '新能源',
    energyStorage: '储能',
    microGrid: '微电网',
    pvStation: '光伏电站',
    storageBattery: '储能电池'
  },
  reportModule: {
    title: '报表管理',
    generate: '报表生成',
    template: '报表模板'
  }
}
