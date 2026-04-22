import moment from 'moment';

export default {
  // 判断是否为空
  isBlank(value) {
    if (value === undefined || value === null || value.trim() === '') {
      return true;
    }
    return false;
  },
  // 判断是否不为空
  isNotBlank(value) {
    return !this.isBlank(value);
  },
  // 转码
  exchangeCodeToName(code, options) {
    const matchedOption = options.find(option => option.code === code)
    if (matchedOption) {
      return matchedOption.name
    } else {
      return code
    }
  },

  formatDate(date, format = 'YYYY-MM-DD') {
    console.log("date=", JSON.stringify(date))
    if (!date) {
      return '';
    }
    return moment(date).format(format);
  },

  formatTime(date, format = 'HH:mm:ss') {
    if (!date) {
      return '';
    }
 // 明确指定输入格式为时间格式
  return moment(date, 'HH:mm:ss').format(format);
  },

  formatDateTime(date, format = 'YYYY-MM-DD HH:mm:ss') {
    if (!date) {
      return '';
    }
    return moment(date).format(format);
  },

  // 通用工具函数：根据时间单位和数量计算日期范围
  getTimeRange(unit, amount) {  // 修复此处的语法错误，移除=号
    const end = new Date();
    const start = new Date();
    const millisecondsPerHour = 3600 * 1000;

    // 根据单位计算时间差（毫秒）
    let timeDiff;
    switch (unit) {
      case 'hour':
        timeDiff = millisecondsPerHour * amount;
        break;
      case 'day':
        timeDiff = millisecondsPerHour * 24 * amount;
        break;
      default:
        timeDiff = 0;
    }

    // 只有当时间差大于0时才调整开始时间
    if (timeDiff > 0) {
      start.setTime(start.getTime() - timeDiff);
    }

    return [start, end];
  },

  // 通用工具函数：根据时间单位和数量计算日期
  getTime(unit, amount) {
    const start = new Date();
    const millisecondsPerHour = 3600 * 1000;

    // 根据单位计算时间差（毫秒）
    let timeDiff;
    switch (unit) {
      case 'hour':
        timeDiff = millisecondsPerHour * amount;
        break;
      case 'day':
        timeDiff = millisecondsPerHour * 24 * amount;
        break;
      default:
        timeDiff = 0;
    }

    start.setTime(start.getTime() + timeDiff);

    return start;
  },

  // 日期范围快捷选项（按天/月/年）
  dateShortcuts() {
    const shortcutConfigs = [
      {text: '1天内', unit: 'day', amount: 0},    // 当天
      {text: '3天内', unit: 'day', amount: 3},
      {text: '1周内', unit: 'day', amount: 7},
      {text: '1月内', unit: 'day', amount: 30},
      {text: '3月内', unit: 'day', amount: 90},
      {text: '1年内', unit: 'day', amount: 365},
      {text: '3年内', unit: 'day', amount: 365 * 3}
    ];

    return shortcutConfigs.map(config => ({
      text: config.text,
      value: () => this.getTimeRange(config.unit, config.amount)  // 注意这里要加this
    }));
  },

  // 日期时间范围快捷选项（包含小时级）
  dateTimeShortcuts() {
    const shortcutConfigs = [
      {text: '1小时内', unit: 'hour', amount: 1},
      {text: '3小时内', unit: 'hour', amount: 3},
      {text: '6小时内', unit: 'hour', amount: 6},
      {text: '12小时内', unit: 'hour', amount: 12},
      {text: '1天内', unit: 'day', amount: 0},      // 当天
      {text: '3天内', unit: 'day', amount: 3},
      {text: '1周内', unit: 'day', amount: 7},
      {text: '1月内', unit: 'day', amount: 30},
      {text: '3月内', unit: 'day', amount: 90},
      {text: '1年内', unit: 'day', amount: 365},
      {text: '3年内', unit: 'day', amount: 365 * 3}  // 修复原3年内的计算错误
    ];

    return shortcutConfigs.map(config => ({
      text: config.text,
      value: () => this.getTimeRange(config.unit, config.amount)  // 注意这里要加this
    }));
  },

  // 日期快捷选项（当天、前N天、后N天）
  datePickerOptionsShortcuts() {
    const shortcutConfigs = [
      {text: '当天', type: 'current', amount: 0},
      {text: '前1天', type: 'past', unit: 'day', amount: -1},
      {text: '前3天', type: 'past', unit: 'day', amount: -3},
      {text: '前7天', type: 'past', unit: 'day', amount: -7},
      {text: '前30天', type: 'past', unit: 'day', amount: -30},
      {text: '前90天', type: 'past', unit: 'day', amount: -90},
      {text: '前365天', type: 'past', unit: 'day', amount: -365},
      {text: '后1天', type: 'future', unit: 'day', amount: 1},
      {text: '后3天', type: 'future', unit: 'day', amount: 3},
      {text: '后7天', type: 'future', unit: 'day', amount: 7},
      {text: '后30天', type: 'future', unit: 'day', amount: 30},
      {text: '后90天', type: 'future', unit: 'day', amount: 90},
      {text: '后365天', type: 'future', unit: 'day', amount: 365}
    ];

    return shortcutConfigs.map(config => ({
      text: config.text,
      value: () => this.getTime(config.unit, config.amount)
    }))
  },

  // 日期时间快捷选项（当天、前N天、后N天）
  dateTimePickerOptionsShortcuts() {
    const shortcutConfigs = [
      {text: '当天', type: 'current', amount: 0},
      {text: '前1天', type: 'past', unit: 'day', amount: -1},
      {text: '前3天', type: 'past', unit: 'day', amount: -3},
      {text: '前7天', type: 'past', unit: 'day', amount: -7},
      {text: '前30天', type: 'past', unit: 'day', amount: -30},
      {text: '前90天', type: 'past', unit: 'day', amount: -90},
      {text: '前365天', type: 'past', unit: 'day', amount: -365},
      {text: '后1天', type: 'future', unit: 'day', amount: 1},
      {text: '后3天', type: 'future', unit: 'day', amount: 3},
      {text: '后7天', type: 'future', unit: 'day', amount: 7},
      {text: '后30天', type: 'future', unit: 'day', amount: 30},
      {text: '后90天', type: 'future', unit: 'day', amount: 90},
      {text: '后365天', type: 'future', unit: 'day', amount: 365}
    ];

    return shortcutConfigs.map(config => ({
      text: config.text,
      value: () => this.getTime(config.unit, config.amount)
    }))
  },


}
