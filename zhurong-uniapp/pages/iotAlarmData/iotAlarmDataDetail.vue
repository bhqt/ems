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
			<uni-forms-item class="a_query_form_item" label="监控参数：" name="alarmMonitorParam">
				<text class="a_detail_text">{{pageData.detailData.alarmMonitorParamExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警名称：" name="alarmName">
				<text class="a_detail_text">{{pageData.detailData.alarmName}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警值：" name="value">
				<text class="a_detail_text">{{pageData.detailData.value}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警条件：" name="alarmCondition">
				<text class="a_detail_text">{{pageData.detailData.alarmConditionExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警阈值：" name="alarmValue">
				<text class="a_detail_text">{{pageData.detailData.alarmValue}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="负责人：" name="userId">
				<text class="a_detail_text">{{pageData.detailData.userIdExtend}}</text>
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
import iotAlarmData from '@/api/autoee/iotAlarmData';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	iotAlarmDataList: [],
				detailData: {
					areaCodeDict: "",
					areaCodeDictExtend: "",
					deviceTypeDict: "",
					deviceTypeDictExtend: "",
					deviceCodeDict: "",
					deviceCodeDictExtend: "",
					alarmMonitorParam: "",
					alarmMonitorParamExtend: "",
					alarmName: "",
					alarmNameExtend: "",
					value: "",
					valueExtend: "",
					alarmCondition: "",
					alarmConditionExtend: "",
					alarmValue: "",
					alarmValueExtend: "",
					userId: "",
					userIdExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// sys_dept: [],
					// a_iot_area: [],
					// a_iot_device_type: [],
					// a_iot_device: [],
					// alarm_monitor_param: [],
					// alarm_condition: [],
					// sys_user: [],
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
				// this.pageData.dictData.alarm_monitor_param = await getDictData('alarm_monitor_param')
				// this.pageData.dictData.alarm_condition = await getDictData('alarm_condition')
				// this.pageData.dictData.sys_user = await getDictData('sys_user')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	iotAlarmData.selectDetailByPkIotAlarmData(id).then(res => {
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
//@import '@/static/styles/autoee/iotAlarmData/iotAlarmDataDetail.scss';
</style>
