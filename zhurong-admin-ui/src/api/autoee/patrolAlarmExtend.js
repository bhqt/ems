import request from '@/utils/request'

// 提供一个默认导出的对象，它包含了所有的API方法
// 这样在别处可以直接整体引入import patrolAlarm from './autoee/patrolAlarm';
export default {
 	indexMountedStartExtend,
	indexWatchExtend,
	indexOperateSuccessExtend,
	tableSummaryMethodExtend,
	addUpdateMountedStartExtend,
	importMountedStartExtend,
	openAddDialogExtend,
	openImportDialogExtend,
};



//  indexMounted扩展方法
export function indexMountedStartExtend(instance) {

}
// indexWatch扩展方法
export function indexWatchExtend (instance, params) {
};

//  主页增删改、导入、其他方法处理成功扩展方法
export function indexOperateSuccessExtend(instance, operateFlag) {
	console.log("operateFlag=", operateFlag)
	if (operateFlag === "addUpdate") {
		//updateBatchNoDict(instance, "indexAddSuccessExtend")
	} else if (operateFlag === "import") {
	} else if (operateFlag === "delete") {
	}
}

//  addUpdateMounted扩展方法
export function addUpdateMountedStartExtend(instance) {
	//const pId = instance.proxy.$route.params && instance.proxy.$route.params.id;
	//console.log("跳转传入的pId=", pId)
	//if (pId) {
	//	instance.proxy.queryParams.realMainId = pId
	//}
}

// importMounted扩展方法
export function importMountedStartExtend(instance) {
	//const pId = instance.proxy.$route.params && instance.proxy.$route.params.id;
	//console.log("跳转传入的pId=", pId)
	//if (pId) {
	//	instance.proxy.queryParams.realMainId = pId
	//}
}

// 打开新增弹出窗口扩展方法
export function openAddDialogExtend(instance, parentQueryParams) {

}

// 打开导入弹出窗口扩展方法
export function openImportDialogExtend(instance, parentQueryParams) {
	return true;
}



// import Decimal from 'decimal.js';
// // 加法
// let c = new Decimal(a).add(new Decimal(b))
// // 减法
// let d = new Decimal(a).sub(new Decimal(b))
// // 乘法
// let e = new Decimal(a).mul(new Decimal(b))
// // 除法
// let f = new Decimal(a).div(new Decimal(b))
export function tableSummaryMethodExtend(instance, param) {
	// console.log("tableSummaryMethod=", JSON.stringify(param))
	// const {columns, data} = param;
	// const sums = [];

    // columns.forEach((column, index) => {
    //     if (index < 3) {
    //         sums[index] = '';
    //         return;
    //     }
		//
    //     if (index === 3) {
    //         sums[index] = '汇总';
    //         return;
    //     }
		//
    //     // 汇总个数、金额
    //     if (index > 3 && index <= 7) {
    //         const values = data.map(item => Number(item[column.property]));
    //         if (!values.every(value => isNaN(value))) {
    //             // 使用Decimal进行累加，初始值设为0的Decimal实例
    //             let sum = new Decimal(0);
    //             values.forEach(value => {
    //                 if (!isNaN(value)) {
    //                     sum = sum.add(new Decimal(value));
    //                 }
    //             });
    //             // 将结果转换为字符串并格式化为两位小数
    //             sums[index] = sum.toFixed(2);
    //         } else {
    //             sums[index] = ''; // 或者 'N/A'
    //         }
    //     }
	// 	// 汇总字符串，过滤重复
	// 	if (index > 7 && index <= 12) {
	// 		// 字段名称
	// 		// column.property= gaofengxianRemark
	// 		// console.log("column.property=", column.property)
	// 		const values = data.map(item => item[column.property]);
	// 		// console.log("values=", values)
	// 		let resultString = '';
	// 		values.forEach(value => {
	// 			console.log("value=", value)
	// 			if (value && value.length > 0) {
	// 				const splitValues = value.split("|"); // 使用竖线拆分字符串
	// 				splitValues.forEach(splitValue => {
	// 					if (!resultString.includes(splitValue)) { // 检查结果字符串中是否已包含该子字符串
	// 						if (resultString.length > 0) {
	// 							resultString += '|'; // 如果不为空，则添加分隔符
	// 						}
	// 						resultString += splitValue; // 添加子字符串到结果字符串
	// 					}
	// 				});
	// 			}
	// 		});
	// 		sums[index] = resultString;
	// 	}
	//
	// });
	//
	// return sums;
}
