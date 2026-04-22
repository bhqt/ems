/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true
  }
  return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}




// IP
export function isIp(value) {
  // 简单的 IP 地址校验正则表达式
  const ipRegex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
  return ipRegex.test(value)
}

// 手机号码
export function isPhoneNo(value) {
  // 简单的 IP 地址校验正则表达式
  const ipRegex = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
  return ipRegex.test(value)
}
export function isUrl(value) {
  // 简单的 IP 地址校验正则表达式
  const ipRegex = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
  return ipRegex.test(value)
}

// 校验一个数字在 0 到 999999999 之间，并且可以包含最多两位小数
export function isNumberInRange(value) {
  const regex = /^(\d{1,9})(\.\d{1,2})?$/;
  const num = parseFloat(value);
  return regex.test(value) && num >= 0 && num <= 999999999;
}

// 整数：整数包括所有的正整数、负整数以及 0
export function isInteger(value) {
  return isIntegerInRange(value, -999999999, 999999999);
}

// 正整数，包含0
export function isPositiveInteger(value) {
  return isIntegerInRange(value, 0, 999999999);
}


// 校验一个整数大于等于某个值
export function isPositiveIntegerWithMinValue(value, minValue) {
  // return Number.isInteger(value) && value >= minValue && value <= 999999999;
  return isIntegerInRange(value, minValue, 999999999);
}

// 校验一个正整数是否在指定的最小和最大数字之间
export function isIntegerInRange(value, minValue, maxValue) {
  // 先检查是否是数字
  if (typeof value === 'number') {
    console.log("value typeof= number")
    // 检查是否是整数且在范围内
    return Number.isInteger(value) && value >= minValue && value <= maxValue;
  }

  // 如果是字符串
  if (typeof value === 'string') {
    console.log("value typeof= string")
    // 使用正则表达式检查是否为有效的整数且不为科学计数法
    const integerRegex = /^[+-]?\d+$/;
    if (integerRegex.test(value)) {
      const num = parseInt(value);
      // 检查转换后的数字是否为整数且在范围内
      return num >= minValue && num <= maxValue;
    } else {
		console.log("mmmmm")
    }
  }

  // 其他类型直接返回 false
  return false;
}

// 校验最大和最小位数的正整数
//  示例
//   console.log(positiveIntegerWithDigitsRange("123", 2, 4)); // true
//   console.log(positiveIntegerWithDigitsRange("1", 2, 4));   // false (不足 2 位)
//   console.log(positiveIntegerWithDigitsRange("12345", 2, 4)); // false (超过 4 位)
//   console.log(positiveIntegerWithDigitsRange("012", 2, 4)); // false (前导零)
export function isPositiveIntegerWithDigitsRange(value, minDigits, maxDigits) {
  const length = value.length;
  const isPositiveInteger = /^\d+$/.test(value) && parseInt(value, 10) > 0;
  return isPositiveInteger && length >= minDigits && length <= maxDigits;
}

// 校验最大为几位的正整数：digits-位数；如：1,12,123
export function isPositiveIntegerWithMaxDigits(value, maxDigits) {
  const regex = new RegExp(`^\\d{1,${maxDigits}}$`);
  return regex.test(value) && parseInt(value, 10) > 0;
}

// 校验固定位数的正整数：digits-位数；
export function isPositiveIntegerWithFixedDigits(value, digits) {
  const regex = new RegExp(`^\\d{${digits}}$`);
  return regex.test(value) && parseInt(value, 10) > 0;
}
