<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="报警编号：" name="alarmNo">
				<text class="a_detail_text">{{pageData.detailData.alarmNo}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更计划：" name="patrolPlanId">
				<text class="a_detail_text">{{pageData.detailData.patrolPlanIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更任务：" name="patrolTaskId">
				<text class="a_detail_text">{{pageData.detailData.patrolTaskIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警类型：" name="patrolAlarmType">
				<text class="a_detail_text">{{pageData.detailData.patrolAlarmTypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警时间：">
				<text class="a_detail_text">{{pageData.detailData.patrolAlarmTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警内容：" name="patrolAlarmContent">
				<text class="a_detail_text">{{pageData.detailData.patrolAlarmContent}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警状态：" name="patrolAlarmStatus">
				<text class="a_detail_text">{{pageData.detailData.patrolAlarmStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="处理人：" name="handleUserId">
				<text class="a_detail_text">{{pageData.detailData.handleUserIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="处理时间：">
				<text class="a_detail_text">{{pageData.detailData.handleTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="处理描述：" name="handleDesc">
				<text class="a_detail_text">{{pageData.detailData.handleDesc}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="备注：" name="remark">
				<text class="a_detail_text">{{pageData.detailData.remark?pageData.detailData.remark:''}}</text>
			</uni-forms-item>
		</uni-forms>
	    <view class="indent-line"></view>
	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
import patrolAlarm from '@/api/autoee/patrolAlarm';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	patrolAlarmList: [],
				detailData: {
					alarmNo: "",
					alarmNoExtend: "",
					patrolPlanId: "",
					patrolPlanIdExtend: "",
					patrolTaskId: "",
					patrolTaskIdExtend: "",
					patrolAlarmType: "",
					patrolAlarmTypeExtend: "",
					patrolAlarmTime: "",
					patrolAlarmTimeExtend: "",
					patrolAlarmContent: "",
					patrolAlarmContentExtend: "",
					patrolAlarmStatus: "",
					patrolAlarmStatusExtend: "",
					handleUserId: "",
					handleUserIdExtend: "",
					handleTime: "",
					handleTimeExtend: "",
					handleDesc: "",
					handleDescExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// a_patrol_plan: [],
					// a_patrol_task: [],
					// sys_user: [],
					// patrol_alarm_type: [],
					// patrol_alarm_status: [],
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
				// this.pageData.dictData.a_patrol_plan = await getDictData('a_patrol_plan')
				// this.pageData.dictData.a_patrol_task = await getDictData('a_patrol_task')
				// this.pageData.dictData.sys_user = await getDictData('sys_user')
				// this.pageData.dictData.patrol_alarm_type = await getDictData('patrol_alarm_type')
				// this.pageData.dictData.patrol_alarm_status = await getDictData('patrol_alarm_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	patrolAlarm.selectDetailByPkPatrolAlarm(id).then(res => {
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
//@import '@/static/styles/autoee/patrolAlarm/patrolAlarmDetail.scss';
</style>
