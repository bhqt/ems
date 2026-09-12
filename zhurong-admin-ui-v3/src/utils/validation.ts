import { 
  PHONE_REGEX, 
  EMAIL_REGEX, 
  ID_CARD_REGEX, 
  URL_REGEX, 
  IP_REGEX,
  STRONG_PASSWORD_REGEX,
  NUMBER_REGEX,
  POSITIVE_INTEGER_REGEX,
  DECIMAL_REGEX,
} from '@/constants'

/** 创建必填验证规则 */
export function required(message = '不能为空', trigger: 'blur' | 'change' | 'blur,change' = 'blur') {
  return { required: true, message, trigger }
}

/** 创建长度验证规则 */
export function length(min: number, max: number, message?: string, trigger: 'blur' | 'change' = 'blur') {
  return {
    min,
    max,
    message: message || `长度在 ${min} 到 ${max} 个字符之间`,
    trigger,
  }
}

/** 创建最小长度验证规则 */
export function minLength(min: number, message?: string, trigger: 'blur' | 'change' = 'blur') {
  return {
    min,
    message: message || `长度不能少于 ${min} 个字符`,
    trigger,
  }
}

/** 创建最大长度验证规则 */
export function maxLength(max: number, message?: string, trigger: 'blur' | 'change' = 'blur') {
  return {
    max,
    message: message || `长度不能超过 ${max} 个字符`,
    trigger,
  }
}

/** 手机号验证 */
export function phone(message = '请输入正确的手机号格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: PHONE_REGEX,
    message,
    trigger,
  }
}

/** 邮箱验证 */
export function email(message = '请输入正确的邮箱格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: EMAIL_REGEX,
    message,
    trigger,
  }
}

/** 身份证验证 */
export function idCard(message = '请输入正确的身份证号格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      if (ID_CARD_REGEX.test(value)) {
        callback()
      } else {
        callback(new Error(message))
      }
    },
    trigger,
  }
}

/** URL 验证 */
export function url(message = '请输入正确的网址格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: URL_REGEX,
    message,
    trigger,
  }
}

/** IP 地址验证 */
export function ip(message = '请输入正确的IP地址格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: IP_REGEX,
    message,
    trigger,
  }
}

/** 强密码验证 */
export function strongPassword(message = '密码必须包含大小写字母、数字和特殊字符，且长度不少于8位', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: STRONG_PASSWORD_REGEX,
    message,
    trigger,
  }
}

/** 数字验证 */
export function number(message = '请输入正确的数字格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: NUMBER_REGEX,
    message,
    trigger,
  }
}

/** 整数验证 */
export function integer(message = '请输入正确的整数格式', trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      if (/^-?\d+$/.test(value)) {
        callback()
      } else {
        callback(new Error(message))
      }
    },
    trigger,
  }
}

/** 正整数验证 */
export function positiveInteger(message = '请输入正整数', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: POSITIVE_INTEGER_REGEX,
    message,
    trigger,
  }
}

/** 非负整数验证 */
export function nonNegativeInteger(message = '请输入非负整数', trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      if (/^\d+$/.test(value)) {
        callback()
      } else {
        callback(new Error(message))
      }
    },
    trigger,
  }
}

/** 小数验证 */
export function decimal(precision = 2, message?: string, trigger: 'blur' | 'change' = 'blur') {
  const regex = new RegExp(`^\\d+(\\.\\d{1,${precision}})?$`)
  return {
    pattern: regex,
    message: message || `请输入正确的小数格式(最多${precision}位小数)`,
    trigger,
  }
}

/** 正数验证 */
export function positive(message = '请输入正数', trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      const num = parseFloat(value)
      if (!isNaN(num) && num > 0) {
        callback()
      } else {
        callback(new Error(message))
      }
    },
    trigger,
  }
}

/** 负数验证 */
export function negative(message = '请输入负数', trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      const num = parseFloat(value)
      if (!isNaN(num) && num < 0) {
        callback()
      } else {
        callback(new Error(message))
      }
    },
    trigger,
  }
}

/** 范围验证 */
export function range(min: number, max: number, message?: string, trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      const num = parseFloat(value)
      if (!isNaN(num) && num >= min && num <= max) {
        callback()
      } else {
        callback(new Error(message || `请输入 ${min} 到 ${max} 之间的数值`))
      }
    },
    trigger,
  }
}

/** 中文验证 */
export function chinese(message = '请输入中文字符', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: /^[\u4e00-\u9fa5]+$/,
    message,
    trigger,
  }
}

/** 英文字符验证 */
export function english(message = '请输入英文字符', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: /^[a-zA-Z]+$/,
    message,
    trigger,
  }
}

/** 用户名验证 (字母开头，允许字母数字下划线，3-20位) */
export function username(message = '用户名必须以字母开头，只能包含字母、数字和下划线，长度3-20位', trigger: 'blur' | 'change' = 'blur') {
  return {
    pattern: /^[a-zA-Z][a-zA-Z0-9_]{2,19}$/,
    message,
    trigger,
  }
}

/** 密码确认验证 */
export function confirmPassword(passwordField: string, message = '两次密码不一致', trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: string, callback: any) => {
      if (!value) {
        callback()
        return
      }
      const form = (rule as any).form
      if (form && form[passwordField] !== value) {
        callback(new Error(message))
      } else {
        callback()
      }
    },
    trigger,
  }
}

/** 自定义验证 */
export function custom(validator: (value: any, callback: (error?: Error) => void) => void, trigger: 'blur' | 'change' = 'blur') {
  return {
    validator: (rule: any, value: any, callback: any) => {
      validator(value, callback)
    },
    trigger,
  }
}

/** 组合验证规则 */
export function compose(...rules: any[]): any[] {
  return rules.flat()
}

/** 常用验证规则组合 */
export const commonRules = {
  /** 用户名 */
  username: [required(), minLength(3), maxLength(20), username()],
  /** 密码 */
  password: [required(), minLength(8), maxLength(20), strongPassword()],
  /** 确认密码 */
  confirmPassword: (passwordField = 'password') => [required(), confirmPassword(passwordField)],
  /** 手机号 */
  phone: [required(), phone()],
  /** 邮箱 */
  email: [required(), email()],
  /** 身份证 */
  idCard: [required(), idCard()],
  /** 真实姓名 */
  realName: [required(), minLength(2), maxLength(20), chinese()],
  /** 部门名称 */
  deptName: [required(), minLength(2), maxLength(50)],
  /** 角色名称 */
  roleName: [required(), minLength(2), maxLength(30)],
  /** 菜单名称 */
  menuName: [required(), minLength(2), maxLength(50)],
  /** 字典类型 */
  dictType: [required(), minLength(2), maxLength(50), english()],
  /** 字典标签 */
  dictLabel: [required(), minLength(1), maxLength(100)],
  /** 字典值 */
  dictValue: [required(), minLength(1), maxLength(100)],
  /** 参数名称 */
  configName: [required(), minLength(2), maxLength(100)],
  /** 参数键名 */
  configKey: [required(), minLength(2), maxLength(100), english()],
  /** 公告标题 */
  noticeTitle: [required(), minLength(2), maxLength(200)],
  /** 公告内容 */
  noticeContent: [required(), minLength(10)],
}