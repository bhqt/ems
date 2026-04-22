/**
 * 日期时间工具类
 * 提供常用的日期时间格式化、计算等功能
 * 优先使用moment.js，如果不可用则使用原生JavaScript实现
 */

let moment = null;
// 尝试导入moment.js
try {
  // 这里使用动态导入，避免编译错误
  // moment = require('moment');
  // console.warn('moment.js is available');
} catch (error) {
  console.warn('moment.js is not available, using native JavaScript Date instead');
}

/**
 * 原生JavaScript日期格式化函数
 * @param {Date} date - 日期对象
 * @param {string} format - 格式化字符串
 * @returns {string} 格式化后的日期字符串
 */
function formatDate(date, format) {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');

  return format
    .replace('YYYY', year)
    .replace('MM', month)
    .replace('DD', day)
    .replace('HH', hours)
    .replace('mm', minutes)
    .replace('ss', seconds);
}

const dateTime = {
  /**
   * 获取当前日期时间
   * @param {string} format - 日期时间格式，默认为 'YYYY-MM-DD HH:mm:ss'
   * @returns {string} 格式化后的当前日期时间
   */
  getCurrentDateTime(format = 'YYYY-MM-DD HH:mm:ss') {
    if (moment) {
      return moment().format(format);
    } else {
      return formatDate(new Date(), format);
    }
  },

  /**
   * 获取当前日期
   * @param {string} format - 日期格式，默认为 'YYYY-MM-DD'
   * @returns {string} 格式化后的当前日期
   */
  getCurrentDate(format = 'YYYY-MM-DD') {
    if (moment) {
      return moment().format(format);
    } else {
      return formatDate(new Date(), format);
    }
  },

  /**
   * 获取当前时间
   * @param {string} format - 时间格式，默认为 'HH:mm:ss'
   * @returns {string} 格式化后的当前时间
   */
  getCurrentTime(format = 'HH:mm:ss') {
    if (moment) {
      return moment().format(format);
    } else {
      return formatDate(new Date(), format);
    }
  },

  /**
   * 格式化日期时间
   * @param {string|Date} date - 要格式化的日期时间
   * @param {string} format - 日期时间格式，默认为 'YYYY-MM-DD HH:mm:ss'
   * @returns {string} 格式化后的日期时间
   */
  formatDateTime(date, format = 'YYYY-MM-DD HH:mm:ss') {
    if (moment) {
      return moment(date).format(format);
    } else {
      return formatDate(new Date(date), format);
    }
  },

  /**
   * 格式化日期
   * @param {string|Date} date - 要格式化的日期
   * @param {string} format - 日期格式，默认为 'YYYY-MM-DD'
   * @returns {string} 格式化后的日期
   */
  formatDate(date, format = 'YYYY-MM-DD') {
    if (moment) {
      return moment(date).format(format);
    } else {
      return formatDate(new Date(date), format);
    }
  },

  /**
   * 格式化时间
   * @param {string|Date} date - 要格式化的时间
   * @param {string} format - 时间格式，默认为 'HH:mm:ss'
   * @returns {string} 格式化后的时间
   */
  formatTime(date, format = 'HH:mm:ss') {
    if (moment) {
      return moment(date).format(format);
    } else {
      return formatDate(new Date(date), format);
    }
  },

  /**
   * 获取指定日期的前n天
   * @param {string|Date} date - 基准日期
   * @param {number} days - 天数，默认为1
   * @param {string} format - 日期格式，默认为 'YYYY-MM-DD'
   * @returns {string} 计算后的日期
   */
  getBeforeDays(date, days = 1, format = 'YYYY-MM-DD') {
    if (moment) {
      return moment(date).subtract(days, 'days').format(format);
    } else {
      const d = new Date(date);
      d.setDate(d.getDate() - days);
      return formatDate(d, format);
    }
  },

  /**
   * 获取指定日期的后n天
   * @param {string|Date} date - 基准日期
   * @param {number} days - 天数，默认为1
   * @param {string} format - 日期格式，默认为 'YYYY-MM-DD'
   * @returns {string} 计算后的日期
   */
  getAfterDays(date, days = 1, format = 'YYYY-MM-DD') {
    if (moment) {
      return moment(date).add(days, 'days').format(format);
    } else {
      const d = new Date(date);
      d.setDate(d.getDate() + days);
      return formatDate(d, format);
    }
  },

  /**
   * 获取两个日期之间的天数差
   * @param {string|Date} startDate - 开始日期
   * @param {string|Date} endDate - 结束日期
   * @returns {number} 天数差
   */
  getDaysBetween(startDate, endDate) {
    if (moment) {
      return moment(endDate).diff(moment(startDate), 'days');
    } else {
      const start = new Date(startDate);
      const end = new Date(endDate);
      const diffTime = Math.abs(end - start);
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays;
    }
  },

  /**
   * 判断是否为今天
   * @param {string|Date} date - 要判断的日期
   * @returns {boolean} 是否为今天
   */
  isToday(date) {
    if (moment) {
      return moment(date).isSame(moment(), 'day');
    } else {
      const today = new Date();
      const target = new Date(date);
      return today.getDate() === target.getDate() &&
             today.getMonth() === target.getMonth() &&
             today.getFullYear() === target.getFullYear();
    }
  },

  /**
   * 判断是否为昨天
   * @param {string|Date} date - 要判断的日期
   * @returns {boolean} 是否为昨天
   */
  isYesterday(date) {
    if (moment) {
      return moment(date).isSame(moment().subtract(1, 'days'), 'day');
    } else {
      const yesterday = new Date();
      yesterday.setDate(yesterday.getDate() - 1);
      const target = new Date(date);
      return yesterday.getDate() === target.getDate() &&
             yesterday.getMonth() === target.getMonth() &&
             yesterday.getFullYear() === target.getFullYear();
    }
  },

  /**
   * 判断是否为今年
   * @param {string|Date} date - 要判断的日期
   * @returns {boolean} 是否为今年
   */
  isThisYear(date) {
    if (moment) {
      return moment(date).isSame(moment(), 'year');
    } else {
      const thisYear = new Date().getFullYear();
      const targetYear = new Date(date).getFullYear();
      return thisYear === targetYear;
    }
  },

  /**
   * 获取日期的年份
   * @param {string|Date} date - 日期
   * @returns {number} 年份
   */
  getYear(date) {
    if (moment) {
      return moment(date).year();
    } else {
      return new Date(date).getFullYear();
    }
  },

  /**
   * 获取日期的月份
   * @param {string|Date} date - 日期
   * @returns {number} 月份（1-12）
   */
  getMonth(date) {
    if (moment) {
      return moment(date).month() + 1;
    } else {
      return new Date(date).getMonth() + 1;
    }
  },

  /**
   * 获取日期的日
   * @param {string|Date} date - 日期
   * @returns {number} 日（1-31）
   */
  getDay(date) {
    if (moment) {
      return moment(date).date();
    } else {
      return new Date(date).getDate();
    }
  },

  /**
   * 获取日期的星期几
   * @param {string|Date} date - 日期
   * @param {boolean} isChinese - 是否返回中文，默认为true
   * @returns {string|number} 星期几
   */
  getWeekDay(date, isChinese = true) {
    if (moment) {
      if (isChinese) {
        const weekDays = ['日', '一', '二', '三', '四', '五', '六'];
        return `星期${weekDays[moment(date).day()]}`;
      }
      return moment(date).day();
    } else {
      const day = new Date(date).getDay();
      if (isChinese) {
        const weekDays = ['日', '一', '二', '三', '四', '五', '六'];
        return `星期${weekDays[day]}`;
      }
      return day;
    }
  },

  /**
   * 获取日期所在月份的第一天
   * @param {string|Date} date - 日期
   * @param {string} format - 日期格式，默认为 'YYYY-MM-DD'
   * @returns {string} 月份第一天
   */
  getFirstDayOfMonth(date, format = 'YYYY-MM-DD') {
    if (moment) {
      return moment(date).startOf('month').format(format);
    } else {
      const d = new Date(date);
      d.setDate(1);
      return formatDate(d, format);
    }
  },

  /**
   * 获取日期所在月份的最后一天
   * @param {string|Date} date - 日期
   * @param {string} format - 日期格式，默认为 'YYYY-MM-DD'
   * @returns {string} 月份最后一天
   */
  getLastDayOfMonth(date, format = 'YYYY-MM-DD') {
    if (moment) {
      return moment(date).endOf('month').format(format);
    } else {
      const d = new Date(date);
      d.setMonth(d.getMonth() + 1);
      d.setDate(0);
      return formatDate(d, format);
    }
  },

  /**
   * 将时间戳转换为日期时间
   * @param {number} timestamp - 时间戳（毫秒）
   * @param {string} format - 日期时间格式，默认为 'YYYY-MM-DD HH:mm:ss'
   * @returns {string} 格式化后的日期时间
   */
  timestampToDateTime(timestamp, format = 'YYYY-MM-DD HH:mm:ss') {
    if (moment) {
      return moment(timestamp).format(format);
    } else {
      return formatDate(new Date(timestamp), format);
    }
  },

  /**
   * 将日期时间转换为时间戳
   * @param {string|Date} date - 日期时间
   * @returns {number} 时间戳（毫秒）
   */
  dateTimeToTimestamp(date) {
    if (moment) {
      return moment(date).valueOf();
    } else {
      return new Date(date).getTime();
    }
  },

  /**
   * 获取友好的时间显示（如：刚刚、几分钟前、几小时前、几天前）
   * @param {string|Date} date - 日期时间
   * @returns {string} 友好的时间显示
   */
  getFriendlyTime(date) {
    const now = moment ? moment() : new Date();
    const target = moment ? moment(date) : new Date(date);

    let diffInSeconds, diffInMinutes, diffInHours, diffInDays;

    if (moment) {
      diffInSeconds = now.diff(target, 'seconds');
      diffInMinutes = now.diff(target, 'minutes');
      diffInHours = now.diff(target, 'hours');
      diffInDays = now.diff(target, 'days');
    } else {
      const diff = now - target;
      diffInSeconds = Math.floor(diff / 1000);
      diffInMinutes = Math.floor(diffInSeconds / 60);
      diffInHours = Math.floor(diffInMinutes / 60);
      diffInDays = Math.floor(diffInHours / 24);
    }

    if (diffInSeconds < 60) {
      return '刚刚';
    } else if (diffInMinutes < 60) {
      return `${diffInMinutes}分钟前`;
    } else if (diffInHours < 24) {
      return `${diffInHours}小时前`;
    } else if (diffInDays < 7) {
      return `${diffInDays}天前`;
    } else if (diffInDays < 30) {
      const weeks = Math.floor(diffInDays / 7);
      return `${weeks}周前`;
    } else if (diffInDays < 365) {
      const months = Math.floor(diffInDays / 30);
      return `${months}个月前`;
    } else {
      const years = Math.floor(diffInDays / 365);
      return `${years}年前`;
    }
  }
};

export default dateTime;
