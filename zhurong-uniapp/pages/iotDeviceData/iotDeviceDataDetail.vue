<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="所属区域：" name="areaCodeDict">
				<text class="a_detail_text">{{pageData.detailData.areaCodeDictExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备类型：" name="deviceTypeDict">
				<text class="a_detail_text">{{pageData.detailData.deviceTypeDictExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备：" name="deviceCodeDict">
				<text class="a_detail_text">{{pageData.detailData.deviceCodeDictExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块状态：" name="moduleWorkStatus">
				<text class="a_detail_text">{{pageData.detailData.moduleWorkStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块故障码：" name="moduleFaultCode">
				<text class="a_detail_text">{{pageData.detailData.moduleFaultCodeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="输出电压：" name="voltage">
				<text class="a_detail_text">{{pageData.detailData.voltage}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="输出电流：" name="current">
				<text class="a_detail_text">{{pageData.detailData.current}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="请求电压：" name="requiredVoltage">
				<text class="a_detail_text">{{pageData.detailData.requiredVoltage}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="请求电流：" name="requiredCurrent">
				<text class="a_detail_text">{{pageData.detailData.requiredCurrent}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="温度：" name="temperature">
				<text class="a_detail_text">{{pageData.detailData.temperature}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="BUS电压：" name="busVoltage">
				<text class="a_detail_text">{{pageData.detailData.busVoltage}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="采集时间：">
				<text class="a_detail_text">{{pageData.detailData.collectTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块前级版本号：" name="softPreviousVersion">
				<text class="a_detail_text">{{pageData.detailData.softPreviousVersion}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块后级版本号：" name="softSubsequentVersion">
				<text class="a_detail_text">{{pageData.detailData.softSubsequentVersion}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块上锁状态：" name="moduleLockStatus">
				<text class="a_detail_text">{{pageData.detailData.moduleLockStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块剩余运行时间：" name="moduleRemainingRuntime">
				<text class="a_detail_text">{{pageData.detailData.moduleRemainingRuntime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块剩余充电次数：" name="moduleRemainingChargingTimes">
				<text class="a_detail_text">{{pageData.detailData.moduleRemainingChargingTimes}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="信号强度：">
				<text class="a_detail_text">{{pageData.detailData.signalStrength}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="电池电量：">
				<text class="a_detail_text">{{pageData.detailData.batteryLevel}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="接收时间：">
				<text class="a_detail_text">{{pageData.detailData.serverTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="原始报文：" name="rawData">
				<text class="a_detail_text">{{pageData.detailData.rawData}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="消息标识：" name="messageId">
				<text class="a_detail_text">{{pageData.detailData.messageId}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="备注：" name="remark">
				<text class="a_detail_text">{{pageData.detailData.remark}}</text>
			</uni-forms-item>
		</uni-forms>
	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
import iotDeviceData from '@/api/autoee/iotDeviceData';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	iotDeviceDataList: [],
				detailData: {
					areaCodeDict: "",
					areaCodeDictExtend: "",
					deviceTypeDict: "",
					deviceTypeDictExtend: "",
					deviceCodeDict: "",
					deviceCodeDictExtend: "",
					moduleWorkStatus: "",
					moduleWorkStatusExtend: "",
					moduleFaultCode: "",
					moduleFaultCodeExtend: "",
					voltage: "",
					voltageExtend: "",
					current: "",
					currentExtend: "",
					requiredVoltage: "",
					requiredVoltageExtend: "",
					requiredCurrent: "",
					requiredCurrentExtend: "",
					temperature: "",
					temperatureExtend: "",
					busVoltage: "",
					busVoltageExtend: "",
					collectTime: "",
					collectTimeExtend: "",
					softPreviousVersion: "",
					softPreviousVersionExtend: "",
					softSubsequentVersion: "",
					softSubsequentVersionExtend: "",
					moduleLockStatus: "",
					moduleLockStatusExtend: "",
					moduleRemainingRuntime: "",
					moduleRemainingRuntimeExtend: "",
					moduleRemainingChargingTimes: "",
					moduleRemainingChargingTimesExtend: "",
					signalStrength: "",
					signalStrengthExtend: "",
					batteryLevel: "",
					batteryLevelExtend: "",
					serverTime: "",
					serverTimeExtend: "",
					rawData: "",
					rawDataExtend: "",
					messageId: "",
					messageIdExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// sys_dept: [],
					// a_iot_area: [],
					// a_iot_device_type: [],
					// a_iot_device: [],
					// module_work_status: [],
				}
			}
		}
	},
	async onLoad(options) {
	  	console.log('页面参数:', options)
	  	let id = options.id;
    	this.initData(id)
	  	this.loadData(id);
	},
	onShow() {
		console.log('页面显示')
    },
	mounted(){

	},
	computed: {
		...mapState(['user'])
	},
	methods: {
		async initData(id) {
		 	try {
				// this.pageData.dictData.sys_dept = await getDictData('sys_dept')
				// this.pageData.dictData.a_iot_area = await getDictData('a_iot_area')
				// this.pageData.dictData.a_iot_device_type = await getDictData('a_iot_device_type')
				// this.pageData.dictData.a_iot_device = await getDictData('a_iot_device')
				// this.pageData.dictData.module_work_status = await getDictData('module_work_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	iotDeviceData.selectDetailByPkIotDeviceData(id).then(res => {
           	 	// console.log("getAddressByCurrentUser-res=", res)
           	 	this.pageData.detailData= res.data;
        	})
	    },
		//查询
		handleQuery()
		{
			 this.loadData()
		},
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/iotDeviceData/iotDeviceDataDetail.scss';
</style>
