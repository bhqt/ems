import {validURL, isPhoneNo, isIp, isNumberInRange, isPositiveInteger, isIntegerInRange, isPositiveIntegerWithMinValue} from "@/utils/validate";

export default {

	//表单：校验手机号
	validatePhoneNo(rule, value, callback) {
		console.log("校验手机号：value=", value)
		if (value && !isPhoneNo(value)) {
			callback(new Error('请输入正确的手机号码'));
		} else {
			callback();
		}
	},
	//表单：校验Url
	validateUrl(rule, value, callback) {
		console.log("校验Url：value=", value)
		if (value && !validURL(value)) {
			callback(new Error('请输入正确的Url地址'));
		} else {
			callback();
		}
	},

	//表单：校验IP
	validateIP(rule, value, callback) {
		console.log("校验IP：value=", value)
		if (value && !isIp(value)) {
			callback(new Error('请输入有效的 IP 地址'));
		} else {
			callback();
		}
	},

	//表单：校验正整数
	validatePositiveInteger(rule, value, callback) {
		if (!value) {
			callback();
		} else {
			if (!isPositiveInteger(value)) {
				callback(new Error('请输入0~999999999之间的整数'));
			} else {
				callback();
			}
		}
	},

	//  表单：校验大于负一的正整数
	validatePositiveIntegerWithMinValueMinusOne(rule, value, callback) {
		if (!value) {
			callback();
		} else {
			if (!isPositiveIntegerWithMinValue(value, -1)) {
				callback(new Error('请输入-1~999999999之间的整数'));
			} else {
				callback();
			}
		}
	},
	//  表单：校验一个数字在 0 到 999999999 之间，并且可以包含最多两位小数
	validateNumberInRange(rule, value, callback) {
		if (!value) {
			callback();
		} else {
			if (!isNumberInRange(value)) {
				callback(new Error('请输入0~999999999之间的数字，可以包含两位小数'));
			} else {
				callback();
			}
		}
	},
	//  表单：校验大于0小于100的整数
	validatePositiveInteger_0_100(rule, value, callback) {
		if (!value) {
			callback();
		} else {
			if (!isIntegerInRange(value, 0, 100)) {
				callback(new Error('请输入0~100之间的整数'));
			} else {
				callback();
			}
		}
	},
}
