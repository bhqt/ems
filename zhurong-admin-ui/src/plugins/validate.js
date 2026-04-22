import {
  isInteger,
  isIp,
	isPhoneNo,
  isNumberInRange,
  isPositiveInteger,
  isIntegerInRange,
  isPositiveIntegerWithDigitsRange, isPositiveIntegerWithFixedDigits,
  isPositiveIntegerWithMaxDigits,
  isPositiveIntegerWithMinValue
} from "@/utils/validate";

export default {
  // IP
  ipAddress(value) {
    return isIp(value)
  },
  // 手机号
  phoneNo(value) {
    return isPhoneNo(value)
  },

  // 验一个数字在 0 到 999999999 之间，并且可以包含最多两位小数
  numberInRange(value) {
    return isNumberInRange(value);
  },

  // 整数：整数包括所有的正整数、负整数以及 0
  integer(value) {
    return isInteger(value);
  },

  // 正整数，包含0
  positiveInteger(value) {
    return isPositiveInteger(value);
  },

  // 校验一个整数大于等于某个值
  positiveIntegerWithMinValue(value, minValue) {
    return isPositiveIntegerWithMinValue(value, minValue);
  },

  // 校验一个正整数是否在指定的最小和最大数字之间
  positiveIntegerInRange(value, minValue, maxValue) {
    return isIntegerInRange(value, minValue, maxValue);
  },

  // 校验最大和最小位数的正整数
  //  示例
  //   console.log(positiveIntegerWithDigitsRange("123", 2, 4)); // true
  //   console.log(positiveIntegerWithDigitsRange("1", 2, 4));   // false (不足 2 位)
  //   console.log(positiveIntegerWithDigitsRange("12345", 2, 4)); // false (超过 4 位)
  //   console.log(positiveIntegerWithDigitsRange("012", 2, 4)); // false (前导零)
  positiveIntegerWithDigitsRange(value, minDigits, maxDigits) {
    return isPositiveIntegerWithDigitsRange(value, minDigits, maxDigits) ;
  },

  // 校验最大为几位的正整数：digits-位数；如：1,12,123
  positiveIntegerWithMaxDigits(value, maxDigits) {
    return isPositiveIntegerWithMaxDigits(value, maxDigits);
  },

  // 校验固定位数的正整数：digits-位数；
  positiveIntegerWithFixedDigits(value, digits) {
    return isPositiveIntegerWithFixedDigits(value, digits);
  },

}

