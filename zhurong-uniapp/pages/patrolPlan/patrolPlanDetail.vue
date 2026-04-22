<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="巡更计划名称：" name="patrolPlanName">
				<text class="a_detail_text">{{pageData.detailData.patrolPlanName}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更路线：" name="patrolPathId">
				<text class="a_detail_text">{{pageData.detailData.patrolPathIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更人员：" name="patrolUserId">
				<text class="a_detail_text">{{pageData.detailData.patrolUserIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="开始时间：" name="startTime">
				<text class="a_detail_text">{{pageData.detailData.startTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="结束时间：" name="endTime">
				<text class="a_detail_text">{{pageData.detailData.endTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更周期：" name="patrolCycleType">
				<text class="a_detail_text">{{pageData.detailData.patrolCycleTypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="计划开始日期：">
				<text class="a_detail_text">{{pageData.detailData.startDate}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="计划结束日期：">
				<text class="a_detail_text">{{pageData.detailData.endDate}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="计划状态：" name="patrolPlanStatus">
				<text class="a_detail_text">{{pageData.detailData.patrolPlanStatusExtend}}</text>
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
import patrolPlan from '@/api/autoee/patrolPlan';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	patrolPlanList: [],
				detailData: {
					patrolPlanName: "",
					patrolPlanNameExtend: "",
					patrolPathId: "",
					patrolPathIdExtend: "",
					patrolUserId: "",
					patrolUserIdExtend: "",
					startTime: "",
					startTimeExtend: "",
					endTime: "",
					endTimeExtend: "",
					patrolCycleType: "",
					patrolCycleTypeExtend: "",
					startDate: "",
					startDateExtend: "",
					endDate: "",
					endDateExtend: "",
					patrolPlanStatus: "",
					patrolPlanStatusExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// a_patrol_path: [],
					// sys_user: [],
					// patrol_cycle_type: [],
					// patrol_plan_status: [],
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
				// this.pageData.dictData.a_patrol_path = await getDictData('a_patrol_path')
				// this.pageData.dictData.sys_user = await getDictData('sys_user')
				// this.pageData.dictData.patrol_cycle_type = await getDictData('patrol_cycle_type')
				// this.pageData.dictData.patrol_plan_status = await getDictData('patrol_plan_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	patrolPlan.selectDetailByPkPatrolPlan(id).then(res => {
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
//@import '@/static/styles/autoee/patrolPlan/patrolPlanDetail.scss';
</style>
