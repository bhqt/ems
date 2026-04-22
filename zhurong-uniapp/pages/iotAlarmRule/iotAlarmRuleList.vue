<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.queryParams" ref="queryFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item class="a_query_form_item" label="设备类型" name="deviceTypeDict">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.deviceTypeDict" :localdata="pageData.dictData.a_iot_device_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="监控参数" name="alarmMonitorParam">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.alarmMonitorParam" :localdata="pageData.dictData.alarm_monitor_param" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警名称" name="alarmName">
				<uni-easyinput v-model="pageData.queryParams.alarmName" placeholder="请输入报警名称" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="值类型" name="valueType">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.valueType" :localdata="pageData.dictData.value_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警条件" name="alarmCondition">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.alarmCondition" :localdata="pageData.dictData.alarm_condition" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警阈值" name="alarmValue">
				<uni-easyinput v-model="pageData.queryParams.alarmValue" placeholder="请输入报警阈值" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="生成工单" name="createWorkOrde">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.createWorkOrde" :localdata="pageData.dictData.sys_yes_no" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警开关" name="alarmSwitch">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.alarmSwitch" :localdata="pageData.dictData.alarm_switch" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="handleQuery">查询</button>
		</view>

	  <view class="list">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list>
				<!-- 所有字段  deviceTypeDict,   alarmMonitorParam,   alarmName,   valueType,   alarmCondition,   alarmValue,   alarmSwitch,   updateTime,   -->
				<!-- direction 属性决定列表的排版方向 row,column -->
				<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
			  	<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
			  	<uni-list-item v-for="item in pageData.iotAlarmRuleList" :key="item.id"
								   :rightText="item.deviceTypeDictExtend.toString()"
								   :title="item.alarmName.toString()"
								   :note="formatNote(item)"
								   thumb="/static/biaoshi.svg" thumb-size="medium"
								   showArrow link="navigateTo" :to="'./iotAlarmRuleDetail?id='+item.id">
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
import iotAlarmRule from '@/api/autoee/iotAlarmRule';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	iotAlarmRuleList: [],
				queryParams: {
					deviceTypeDict: "",
					alarmMonitorParam: "",
					alarmName: "",
					valueType: "",
					alarmCondition: "",
					alarmSwitch: "",
				  	params: []
				},
				dictData: {
					a_iot_device_type: [],
					alarm_monitor_param: [],
					value_type: [],
					alarm_condition: [],
					alarm_switch: [],
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
				this.pageData.dictData.a_iot_device_type = await getDictData('a_iot_device_type')
				this.pageData.dictData.alarm_monitor_param = await getDictData('alarm_monitor_param')
				this.pageData.dictData.value_type = await getDictData('value_type')
				this.pageData.dictData.alarm_condition = await getDictData('alarm_condition')
				this.pageData.dictData.alarm_switch = await getDictData('alarm_switch')

			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(){
			let data = this.pageData.queryParams
			data.limitTopN = 200
        	iotAlarmRule.selectDetailListByLikeIotAlarmRule(data).then(res => {
           	 	// console.log("getAddressByCurrentUser-res=", res)
            	let list = res.rows;
            	this.pageData.iotAlarmRuleList = list;
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
				return `${item.alarmConditionExtend || ''}\n${item.alarmValue || ''}`
			} catch (e) {
				return ''
			}
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/iotAlarmRule/iotAlarmRuleList.scss';
</style>
