<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.queryParams" ref="queryFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item class="a_query_form_item" label="所属区域" name="areaCodeDict">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.areaCodeDict" :localdata="pageData.dictData.a_iot_area" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备类型" name="deviceTypeDict">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.deviceTypeDict" :localdata="pageData.dictData.a_iot_device_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备" name="deviceCodeDict">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.deviceCodeDict" :localdata="pageData.dictData.a_iot_device" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="模块状态" name="moduleWorkStatus">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.moduleWorkStatus" :localdata="pageData.dictData.module_work_status" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="输出电压" name="voltage">
				<uni-easyinput v-model="pageData.queryParams.voltage" placeholder="请输入输出电压" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="输出电流" name="current">
				<uni-easyinput v-model="pageData.queryParams.current" placeholder="请输入输出电流" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item_datetime_between" label="采集时间">
				<uni-datetime-picker v-model="pageData.dateRangeCollectTime" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期" return-type="string" rangeSeparator="-" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item_datetime_between" label="接收时间">
				<uni-datetime-picker v-model="pageData.dateRangeServerTime" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期" return-type="string" rangeSeparator="-" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="消息标识" name="messageId">
				<uni-easyinput v-model="pageData.queryParams.messageId" placeholder="请输入消息标识" clearable/>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="handleQuery">查询</button>
		</view>

	  <view class="list">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list>
				<!-- 所有字段  deptId,   areaCodeDict,   deviceTypeDict,   deviceCodeDict,   moduleWorkStatus,   voltage,   current,   requiredVoltage,   requiredCurrent,   temperature,   busVoltage,   collectTime,   updateTime,   -->
				<!-- direction 属性决定列表的排版方向 row,column -->
				<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
			  	<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
			  	<uni-list-item v-for="item in pageData.iotDeviceDataList" :key="item.id"
								   :title="item.deviceCodeDictExtend.toString()"
								   :note="formatNote(item)"
								   thumb="/static/biaoshi.svg" thumb-size="medium"
								   showArrow link="navigateTo" :to="'./iotDeviceDataDetail?id='+item.id">
				</uni-list-item>
			</uni-list>
			<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
			<!--  <uni-load-more :status="loadMoreStatus" /> -->
		</view>
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
				queryParams: {
					deptId: "",
					areaCodeDict: "",
					deviceTypeDict: "",
					deviceCodeDict: "",
					moduleWorkStatus: "",
					collectTime: "",
					updateTime: "",
				  	params: []
				},
				dateRangeCollectTime: [],
				dateRangeUpdateTime: [],
				dictData: {
					sys_dept: [],
					a_iot_area: [],
					a_iot_device_type: [],
					a_iot_device: [],
					module_work_status: [],
				}
			}
		}
	},
	async onLoad(options) {
	  	console.log('页面参数:', options)
    	this.initData()
	  	this.loadData();
	},
	onShow() {
		console.log('页面显示')
		// this.loadData();
    },
	mounted(){

	},
	computed: {
		...mapState(['user'])
	},
	methods: {
		async initData() {
		 	try {
				this.pageData.dictData.sys_dept = await getDictData('sys_dept')
				this.pageData.dictData.a_iot_area = await getDictData('a_iot_area')
				this.pageData.dictData.a_iot_device_type = await getDictData('a_iot_device_type')
				this.pageData.dictData.a_iot_device = await getDictData('a_iot_device')
				this.pageData.dictData.module_work_status = await getDictData('module_work_status')
				// 初始化 dateRangeCollectTime
				this.pageData.dateRangeCollectTime = [];
				// 初始化 dateRangeUpdateTime
				this.pageData.dateRangeUpdateTime = [];

			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(){
  			if (this.pageData.dateRangeCollectTime && this.pageData.dateRangeCollectTime.length > 0) {
  				this.pageData.queryParams.params["beginCollectTime"] = this.pageData.dateRangeCollectTime[0];
  				this.pageData.queryParams.params["endCollectTime"] = this.pageData.dateRangeCollectTime[1];
  			} else {
  				this.pageData.queryParams.params["beginCollectTime"] = null;
  				this.pageData.queryParams.params["endCollectTime"] = null;
  			}
  			if (this.pageData.dateRangeUpdateTime && this.pageData.dateRangeUpdateTime.length > 0) {
  				this.pageData.queryParams.params["beginUpdateTime"] = this.pageData.dateRangeUpdateTime[0];
  				this.pageData.queryParams.params["endUpdateTime"] = this.pageData.dateRangeUpdateTime[1];
  			} else {
  				this.pageData.queryParams.params["beginUpdateTime"] = null;
  				this.pageData.queryParams.params["endUpdateTime"] = null;
  			}
			let data = this.pageData.queryParams
			data.limitTopN = 200
        	iotDeviceData.selectDetailListByLikeIotDeviceData(data).then(res => {
           	 	// console.log("getAddressByCurrentUser-res=", res)
            	let list = res.rows;
            	this.pageData.iotDeviceDataList = list;
        	})
	    },
		//查询
		handleQuery()
		{
			 this.loadData()
		},
		
		//格式化note信息
		formatNote(item) {
			try {
				return `${item.areaCodeDictExtend || ''}\n${item.deviceTypeDictExtend || ''}\n${item.collectTime || ''}`
			} catch (e) {
				return ''
			}
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/iotDeviceData/iotDeviceDataList.scss';
</style>
