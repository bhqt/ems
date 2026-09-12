/** Token 存储键名 */
export const TOKEN_KEY = 'zhurong_token'
export const REFRESH_TOKEN_KEY = 'zhurong_refresh_token'

/** 用户信息存储键名 */
export const USER_INFO_KEY = 'zhurong_user_info'

/** 主题存储键名 */
export const THEME_KEY = 'zhurong_theme'

/** 语言存储键名 */
export const LOCALE_KEY = 'zhurong_locale'

/** 侧边栏状态存储键名 */
export const SIDEBAR_KEY = 'zhurong_sidebar'

/** 标签页存储键名 */
export const TAGS_VIEW_KEY = 'zhurong_tags_view'

/** 设置存储键名 */
export const SETTINGS_KEY = 'zhurong_settings'

/** 医院模块存储键名 */
export const HOSPITAL_KEY = 'zhurong_hospital'

/** 默认分页大小 */
export const DEFAULT_PAGE_SIZE = 20

/** 最大分页大小 */
export const MAX_PAGE_SIZE = 100

/** 请求超时时间(ms) */
export const REQUEST_TIMEOUT = 30000

/** Token 刷新提前时间(秒) */
export const TOKEN_REFRESH_THRESHOLD = 300

/** 密码最小长度 */
export const PASSWORD_MIN_LENGTH = 6

/** 密码最大长度 */
export const PASSWORD_MAX_LENGTH = 20

/** 用户名最小长度 */
export const USERNAME_MIN_LENGTH = 3

/** 用户名最大长度 */
export const USERNAME_MAX_LENGTH = 20

/** 手机号正则 */
export const PHONE_REGEX = /^1[3-9]\d{9}$/

/** 邮箱正则 */
export const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/

/** 身份证正则 */
export const ID_CARD_REGEX = /^[1-9]\d{5}(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/

/** URL 正则 */
export const URL_REGEX = /^https?:\/\/[^\s/$.?#].[^\s]*$/

/** IP 地址正则 */
export const IP_REGEX = /^((25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(25[0-5]|2[0-4]\d|[01]?\d\d?)$/

/** 强密码正则 (至少包含大小写字母、数字、特殊字符，8位以上) */
export const STRONG_PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/

/** 中文字符正则 */
export const CHINESE_REGEX = /^[\u4e00-\u9fa5]+$/

/** 英文字符正则 */
export const ENGLISH_REGEX = /^[a-zA-Z]+$/

/** 数字正则 */
export const NUMBER_REGEX = /^\d+$/

/** 正整数正则 */
export const POSITIVE_INTEGER_REGEX = /^[1-9]\d*$/

/** 非负整数正则 */
export const NON_NEGATIVE_INTEGER_REGEX = /^\d+$/

/** 小数正则 (最多2位小数) */
export const DECIMAL_REGEX = /^\d+(\.\d{1,2})?$/

/** 颜色十六进制正则 */
export const HEX_COLOR_REGEX = /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/

/** Base64 正则 */
export const BASE64_REGEX = /^[A-Za-z0-9+/]+={0,2}$/

/** UUID 正则 */
export const UUID_REGEX = /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i

/** 日期格式化 */
export const DATE_FORMATS = {
  DATE: 'YYYY-MM-DD',
  DATETIME: 'YYYY-MM-DD HH:mm:ss',
  TIME: 'HH:mm:ss',
  MONTH: 'YYYY-MM',
  YEAR: 'YYYY',
  CHINESE_DATE: 'YYYY年MM月DD日',
  CHINESE_DATETIME: 'YYYY年MM月DD日 HH时mm分',
}

/** 能源类型 */
export const ENERGY_TYPES = [
  { value: 'electricity', label: '电力', unit: 'kWh', color: '#1890ff' },
  { value: 'water', label: '水', unit: '吨', color: '#13c2c2' },
  { value: 'gas', label: '燃气', unit: 'm³', color: '#faad14' },
  { value: 'steam', label: '蒸汽', unit: '吨', color: '#722ed1' },
  { value: 'cooling', label: '冷量', unit: 'kWh', color: '#13c2c2' },
  { value: 'heating', label: '热量', unit: 'kWh', color: '#fa8c16' },
]

/** 设备状态 */
export const DEVICE_STATUS = [
  { value: 'online', label: '在线', color: '#52c41a' },
  { value: 'running', label: '运行中', color: '#1890ff' },
  { value: 'standby', label: '待机', color: '#faad14' },
  { value: 'offline', label: '离线', color: '#bfbfbf' },
  { value: 'fault', label: '故障', color: '#ff4d4f' },
  { value: 'maintenance', label: '维护中', color: '#722ed1' },
]

/** 报警级别 */
export const ALARM_LEVELS = [
  { value: 'critical', label: '紧急', color: '#ff4d4f', priority: 1 },
  { value: 'major', label: '主要', color: '#ff7875', priority: 2 },
  { value: 'minor', label: '次要', color: '#faad14', priority: 3 },
  { value: 'warning', label: '警告', color: '#ffc53d', priority: 4 },
  { value: 'info', label: '提示', color: '#1890ff', priority: 5 },
]

/** 报警类型 */
export const ALARM_TYPES = [
  { value: 'threshold', label: '阈值报警' },
  { value: 'trend', label: '趋势报警' },
  { value: 'device', label: '设备故障' },
  { value: 'communication', label: '通讯故障' },
  { value: 'quality', label: '质量异常' },
]

/** 工单状态 */
export const WORK_ORDER_STATUS = [
  { value: 'pending', label: '待派单', color: '#bfbfbf' },
  { value: 'assigned', label: '已派单', color: '#1890ff' },
  { value: 'processing', label: '处理中', color: '#faad14' },
  { value: 'pending_verify', label: '待验收', color: '#722ed1' },
  { value: 'completed', label: '已完成', color: '#52c41a' },
  { value: 'closed', label: '已关闭', color: '#bfbfbf' },
  { value: 'cancelled', label: '已取消', color: '#ff4d4f' },
]

/** 工单优先级 */
export const WORK_ORDER_PRIORITY = [
  { value: 'low', label: '低', color: '#52c41a' },
  { value: 'medium', label: '中', color: '#faad14' },
  { value: 'high', label: '高', color: '#fa8c16' },
  { value: 'urgent', label: '紧急', color: '#ff4d4f' },
]

/** 菜单类型 */
export const MENU_TYPES = [
  { value: 'directory', label: '目录' },
  { value: 'menu', label: '菜单' },
  { value: 'button', label: '按钮' },
]

/** 数据范围 */
export const DATA_SCOPES = [
  { value: '1', label: '全部数据权限' },
  { value: '2', label: '自定义数据权限' },
  { value: '3', label: '本部门数据权限' },
  { value: '4', label: '本部门及以下数据权限' },
  { value: '5', label: '仅本人数据权限' },
]

/** 字典类型 */
export const DICT_TYPES = [
  { value: 'sys_user_sex', label: '用户性别' },
  { value: 'sys_show_hide', label: '显示隐藏' },
  { value: 'sys_normal_disable', label: '正常停用' },
  { value: 'sys_job_status', label: '任务状态' },
  { value: 'sys_job_group', label: '任务分组' },
]

/** 操作日志业务类型 */
export const BUSINESS_TYPES = [
  { value: 0, label: '其他' },
  { value: 1, label: '新增' },
  { value: 2, label: '修改' },
  { value: 3, label: '删除' },
  { value: 4, label: '授权' },
  { value: 5, label: '导出' },
  { value: 6, label: '导入' },
  { value: 7, label: '强退' },
  { value: 8, label: '生成代码' },
  { value: 9, label: '清空数据' },
]

/** 操作类型 */
export const OPER_TYPES = [
  { value: 'login', label: '登录' },
  { value: 'logout', label: '注销' },
  { value: 'insert', label: '新增' },
  { value: 'update', label: '修改' },
  { value: 'delete', label: '删除' },
  { value: 'grant', label: '授权' },
  { value: 'export', label: '导出' },
  { value: 'import', label: '导入' },
  { value: 'force', label: '强退' },
  { value: 'genCode', label: '生成代码' },
  { value: 'clean', label: '清空数据' },
]

/** HTTP 状态码 */
export const HTTP_STATUS = {
  SUCCESS: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  METHOD_NOT_ALLOWED: 405,
  CONFLICT: 409,
  UNPROCESSABLE_ENTITY: 422,
  INTERNAL_SERVER_ERROR: 500,
  BAD_GATEWAY: 502,
  SERVICE_UNAVAILABLE: 503,
  GATEWAY_TIMEOUT: 504,
}

/** 默认头像 */
export const DEFAULT_AVATAR = 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png'

/** 空数据提示 */
export const EMPTY_TEXTS = {
  DEFAULT: '暂无数据',
  TABLE: '暂无数据',
  LIST: '暂无列表数据',
  TREE: '暂无树形数据',
  SEARCH: '无匹配结果',
  CHART: '暂无图表数据',
}

/** 文件上传限制 */
export const UPLOAD_LIMITS = {
  IMAGE: {
    MAX_SIZE: 10 * 1024 * 1024, // 10MB
    ACCEPT: 'image/*',
  },
  DOCUMENT: {
    MAX_SIZE: 50 * 1024 * 1024, // 50MB
    ACCEPT: '.pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt',
  },
  VIDEO: {
    MAX_SIZE: 500 * 1024 * 1024, // 500MB
    ACCEPT: 'video/*',
  },
  ALL: {
    MAX_SIZE: 100 * 1024 * 1024, // 100MB
    ACCEPT: '*/*',
  },
}

/** 导出文件名最大长度 */
export const EXPORT_FILENAME_MAX_LENGTH = 100

/** 树形选择最大层级 */
export const TREE_MAX_LEVEL = 10

/** 表单验证消息 */
export const VALIDATION_MESSAGES = {
  REQUIRED: '请输入{field}',
  SELECT_REQUIRED: '请选择{field}',
  MIN_LENGTH: '{field}长度不能少于{min}个字符',
  MAX_LENGTH: '{field}长度不能超过{max}个字符',
  MIN_VALUE: '{field}不能小于{min}',
  MAX_VALUE: '{field}不能大于{max}',
  EMAIL: '请输入正确的邮箱格式',
  PHONE: '请输入正确的手机号格式',
  ID_CARD: '请输入正确的身份证号格式',
  URL: '请输入正确的网址格式',
  IP: '请输入正确的IP地址格式',
  PASSWORD: '密码必须包含大小写字母、数字和特殊字符，且长度不少于8位',
  PASSWORD_MISMATCH: '两次密码不一致',
  NUMBER: '请输入正确的数字格式',
  INTEGER: '请输入正确的整数格式',
  POSITIVE: '请输入正数',
  DECIMAL: '请输入正确的小数格式(最多2位小数)',
  DATE: '请输入正确的日期格式',
  TIME: '请输入正确的时间格式',
  DATETIME: '请输入正确的日期时间格式',
}