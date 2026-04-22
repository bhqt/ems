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
			<uni-forms-item class="a_query_form_item" label="设备编号：" name="deviceCode">
				<text class="a_detail_text">{{pageData.detailData.deviceCode}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备名称：" name="deviceName">
				<text class="a_detail_text">{{pageData.detailData.deviceName}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="软件版本号：" name="softVerion">
				<text class="a_detail_text">{{pageData.detailData.softVerion}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="充电模块编号：" name="chargingModuleNo">
				<text class="a_detail_text">{{pageData.detailData.chargingModuleNo}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="tbox编号：" name="tboxNo">
				<text class="a_detail_text">{{pageData.detailData.tboxNo}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="上线状态：" name="deviceOnlineState">
				<text class="a_detail_text">{{pageData.detailData.deviceOnlineStateExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="工作状态：" name="deviceWorkStatus">
				<text class="a_detail_text">{{pageData.detailData.deviceWorkStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块状态：" name="moduleWorkStatus">
				<text class="a_detail_text">{{pageData.detailData.moduleWorkStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块故障码：" name="moduleFaultCode">
				<text class="a_detail_text">{{pageData.detailData.moduleFaultCodeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="负责人：" name="userId">
				<text class="a_detail_text">{{pageData.detailData.userIdExtend}}</text>
			</uni-forms-item>
		</uni-forms>
	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
import iotDevice from '@/api/autoee/iotDevice';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	iotDeviceList: [],
				detailData: {
					areaCodeDict: "",
					areaCodeDictExtend: "",
					deviceTypeDict: "",
					deviceTypeDictExtend: "",
					deviceCode: "",
					deviceCodeExtend: "",
					deviceName: "",
					deviceNameExtend: "",
					softVerion: "",
					softVerionExtend: "",
					chargingModuleNo: "",
					chargingModuleNoExtend: "",
					tboxNo: "",
					tboxNoExtend: "",
					deviceOnlineState: "",
					deviceOnlineStateExtend: "",
					deviceWorkStatus: "",
					deviceWorkStatusExtend: "",
					moduleWorkStatus: "",
					moduleWorkStatusExtend: "",
					moduleFaultCode: "",
					moduleFaultCodeExtend: "",
					userId: "",
					userIdExtend: "",
				},
				dictData: {
					// sys_dept: [],
					// a_iot_area: [],
					// a_iot_device_type: [],
					// device_online_state: [],
					// device_work_status: [],
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
				// this.pageData.dictData.device_online_state = await getDictData('device_online_state')
				// this.pageData.dictData.device_work_status = await getDictData('device_work_status')
				// this.pageData.dictData.module_work_status = await getDictData('module_work_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	iotDevice.selectDetailByPkIotDevice(id).then(res => {
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
//@import '@/static/styles/autoee/iotDevice/iotDeviceDetail.scss';
</style>
