/**
 * 字符串工具类
 * 提供常用的字符串操作方法
 */

const stringUtil = {
  /**
   * 校验字符串是否为空
   * @param {string} str - 待校验的字符串
   * @returns {boolean} 为空返回true，不为空返回false
   */
  isEmpty(str) {
    return str === undefined || str === null || str === '' || str === 'undefined' || (typeof str === 'string' && str.trim() === '');
  },

  /**
   * 校验字符串是否不为空
   * @param {string} str - 待校验的字符串
   * @returns {boolean} 不为空返回true，为空返回false
   */
  isNotEmpty(str) {
    return !this.isEmpty(str);
  },

  /**
   * 格式化字符串，替换占位符 {0}, {1}...
   * @param {string} format - 格式化模板
   * @param {...*} args - 替换参数
   * @returns {string} 格式化后的字符串
   */
  format(format, ...args) {
    if (this.isEmpty(format)) return format;
    
    return format.replace(/{(\d+)}/g, (match, index) => {
      return args[index] !== undefined ? args[index] : match;
    });
  },

  /**
   * 裁剪字符串，超出长度部分用省略号替换
   * @param {string} str - 待裁剪的字符串
   * @param {number} length - 最大长度
   * @param {string} suffix - 省略符号，默认为 '...'
   * @returns {string} 裁剪后的字符串
   */
  truncate(str, length, suffix = '...') {
    if (this.isEmpty(str) || str.length <= length) return str;
    return str.slice(0, length) + suffix;
  },

  /**
   * 去除字符串两端的空白字符
   * @param {string} str - 待处理的字符串
   * @returns {string} 处理后的字符串
   */
  trim(str) {
    if (this.isEmpty(str)) return '';
    return typeof str === 'string' ? str.trim() : String(str).trim();
  },

  /**
   * 转换为大写
   * @param {string} str - 待处理的字符串
   * @returns {string} 大写字符串
   */
  toUpperCase(str) {
    if (this.isEmpty(str)) return '';
    return typeof str === 'string' ? str.toUpperCase() : String(str).toUpperCase();
  },

  /**
   * 转换为小写
   * @param {string} str - 待处理的字符串
   * @returns {string} 小写字符串
   */
  toLowerCase(str) {
    if (this.isEmpty(str)) return '';
    return typeof str === 'string' ? str.toLowerCase() : String(str).toLowerCase();
  },

  /**
   * 首字母大写
   * @param {string} str - 待处理的字符串
   * @returns {string} 首字母大写的字符串
   */
  capitalize(str) {
    if (this.isEmpty(str)) return '';
    const trimmed = this.trim(str);
    return trimmed.charAt(0).toUpperCase() + trimmed.slice(1);
  },

  /**
   * 检查字符串是否包含指定子串
   * @param {string} str - 原字符串
   * @param {string} substr - 子字符串
   * @returns {boolean} 是否包含
   */
  contains(str, substr) {
    if (this.isEmpty(str) || this.isEmpty(substr)) return false;
    return str.includes(substr);
  },

  /**
   * 检查字符串是否以指定子串开头
   * @param {string} str - 原字符串
   * @param {string} substr - 子字符串
   * @returns {boolean} 是否以子串开头
   */
  startsWith(str, substr) {
    if (this.isEmpty(str)) return false;
    if (this.isEmpty(substr)) return true;
    return str.startsWith(substr);
  },

  /**
   * 检查字符串是否以指定子串结尾
   * @param {string} str - 原字符串
   * @param {string} substr - 子字符串
   * @returns {boolean} 是否以子串结尾
   */
  endsWith(str, substr) {
    if (this.isEmpty(str)) return false;
    if (this.isEmpty(substr)) return true;
    return str.endsWith(substr);
  },

  /**
   * 生成指定长度的随机字符串
   * @param {number} length - 字符串长度
   * @param {string} chars - 可用字符集，默认为大小写字母和数字
   * @returns {string} 随机字符串
   */
  random(length = 8, chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789') {
    let result = '';
    const charsLength = chars.length;
    for (let i = 0; i < length; i++) {
      result += chars.charAt(Math.floor(Math.random() * charsLength));
    }
    return result;
  },

  /**
   * 替换所有匹配的子串
   * @param {string} str - 原字符串
   * @param {string|RegExp} find - 要查找的内容
   * @param {string} replace - 替换的内容
   * @returns {string} 替换后的字符串
   */
  replaceAll(str, find, replace) {
    if (this.isEmpty(str)) return '';
    if (typeof find === 'string') {
      return str.split(find).join(replace);
    }
    return str.replace(find, replace);
  },

  /**
   * 计算字符串长度，中文算2个字符
   * @param {string} str - 待计算的字符串
   * @returns {number} 字符串长度
   */
  getRealLength(str) {
    if (this.isEmpty(str)) return 0;
    let len = 0;
    for (let i = 0; i < str.length; i++) {
      const c = str.charCodeAt(i);
      // 中文字符范围：0x4e00-0x9fa5
      len += (c >= 0x4e00 && c <= 0x9fa5) ? 2 : 1;
    }
    return len;
  },

  /**
   * 转义HTML特殊字符
   * @param {string} str - 待转义的字符串
   * @returns {string} 转义后的字符串
   */
  escapeHtml(str) {
    if (this.isEmpty(str)) return '';
    const map = {
      '&': '&amp;',
      '<': '&lt;',
      '>': '&gt;',
      '"': '&quot;',
      "'": '&#039;'
    };
    return str.replace(/[&<>"']/g, m => map[m]);
  },

  /**
   * 移除HTML标签
   * @param {string} str - 包含HTML的字符串
   * @returns {string} 移除标签后的字符串
   */
  removeHtmlTags(str) {
    if (this.isEmpty(str)) return '';
    return str.replace(/<[^>]*>/g, '');
  },

  /**
   * 手机号格式化（中间4位用*代替）
   * @param {string} phone - 手机号
   * @returns {string} 格式化后的手机号
   */
  formatPhone(phone) {
    if (this.isEmpty(phone)) return '';
    const phoneStr = String(phone);
    if (/^1[3-9]\d{9}$/.test(phoneStr)) {
      return phoneStr.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
    }
    return phoneStr;
  },

  /**
   * 身份证号格式化（中间8位用*代替）
   * @param {string} idCard - 身份证号
   * @returns {string} 格式化后的身份证号
   */
  formatIdCard(idCard) {
    if (this.isEmpty(idCard)) return '';
    const idCardStr = String(idCard);
    if (idCardStr.length === 15 || idCardStr.length === 18) {
      return idCardStr.replace(/(\d{6})\d{8}(\d{4})/, '$1********$2');
    }
    return idCardStr;
  },

  /**
   * 格式化金额，添加千位分隔符
   * @param {number|string} amount - 金额
   * @param {number} decimals - 小数位数，默认2位
   * @returns {string} 格式化后的金额
   */
  formatMoney(amount, decimals = 2) {
    if (amount === undefined || amount === null) return '0.00';
    const num = parseFloat(amount);
    if (isNaN(num)) return '0.00';
    
    return num.toFixed(decimals).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  }
};

export default stringUtil;