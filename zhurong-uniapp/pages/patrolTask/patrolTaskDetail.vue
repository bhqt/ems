<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="任务名称：" name="patrolTaskName">
				<text class="a_detail_text">{{pageData.detailData.patrolTaskName}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更计划：" name="patrolPlanId">
				<text class="a_detail_text">{{pageData.detailData.patrolPlanIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更路线：" name="patrolPathId">
				<text class="a_detail_text">{{pageData.detailData.patrolPathIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更人员：" name="patrolUserId">
				<text class="a_detail_text">{{pageData.detailData.patrolUserIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更日期：">
				<text class="a_detail_text">{{pageData.detailData.patrolDate}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="开始时间：" name="startTime">
				<text class="a_detail_text">{{pageData.detailData.startTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="结束时间：" name="endTime">
				<text class="a_detail_text">{{pageData.detailData.endTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="任务状态：" name="patrolTaskStatus">
				<text class="a_detail_text">{{pageData.detailData.patrolTaskStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="备注：" name="remark">
				<text class="a_detail_text">{{pageData.detailData.remark}}</text>
			</uni-forms-item>
		</uni-forms>
	    <view class="indent-line"></view>
	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
import patrolTask from '@/api/autoee/patrolTask';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	patrolTaskList: [],
				detailData: {
					patrolTaskName: "",
					patrolTaskNameExtend: "",
					patrolPlanId: "",
					patrolPlanIdExtend: "",
					patrolPathId: "",
					patrolPathIdExtend: "",
					patrolUserId: "",
					patrolUserIdExtend: "",
					patrolDate: "",
					patrolDateExtend: "",
					startTime: "",
					startTimeExtend: "",
					endTime: "",
					endTimeExtend: "",
					patrolTaskStatus: "",
					patrolTaskStatusExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// a_patrol_plan: [],
					// a_patrol_path: [],
					// sys_user: [],
					// patrol_task_status: [],
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
				// this.pageData.dictData.a_patrol_path = await getDictData('a_patrol_path')
				// this.pageData.dictData.sys_user = await getDictData('sys_user')
				// this.pageData.dictData.patrol_task_status = await getDictData('patrol_task_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	patrolTask.selectDetailByPkPatrolTask(id).then(res => {
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
//@import '@/static/styles/autoee/patrolTask/patrolTaskDetail.scss';
</style>
