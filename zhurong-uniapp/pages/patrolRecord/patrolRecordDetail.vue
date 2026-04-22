<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="巡更计划：" name="patrolPlanId">
				<text class="a_detail_text">{{pageData.detailData.patrolPlanIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更路线：" name="patrolPathId">
				<text class="a_detail_text">{{pageData.detailData.patrolPathIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更点位：" name="patrolPointId">
				<text class="a_detail_text">{{pageData.detailData.patrolPointIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更人员：" name="patrolUserId">
				<text class="a_detail_text">{{pageData.detailData.patrolUserIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更任务：" name="patrolTaskId">
				<text class="a_detail_text">{{pageData.detailData.patrolTaskIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="点位顺序：">
				<text class="a_detail_text">{{pageData.detailData.pointOrder}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="点位巡更时间：">
				<text class="a_detail_text">{{pageData.detailData.arriveTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更结果：" name="patrolResult">
				<text class="a_detail_text">{{pageData.detailData.patrolResultExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="结果描述：" name="resultDesc">
				<text class="a_detail_text">{{pageData.detailData.resultDesc}}</text>
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
import patrolRecord from '@/api/autoee/patrolRecord';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	patrolRecordList: [],
				detailData: {
					patrolPlanId: "",
					patrolPlanIdExtend: "",
					patrolPathId: "",
					patrolPathIdExtend: "",
					patrolPointId: "",
					patrolPointIdExtend: "",
					patrolUserId: "",
					patrolUserIdExtend: "",
					patrolTaskId: "",
					patrolTaskIdExtend: "",
					pointOrder: "",
					pointOrderExtend: "",
					arriveTime: "",
					arriveTimeExtend: "",
					patrolResult: "",
					patrolResultExtend: "",
					resultDesc: "",
					resultDescExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// a_patrol_plan: [],
					// a_patrol_path: [],
					// sys_user: [],
					// a_patrol_task: [],
					// patrol_result: [],
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
				// this.pageData.dictData.a_patrol_task = await getDictData('a_patrol_task')
				// this.pageData.dictData.patrol_result = await getDictData('patrol_result')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	patrolRecord.selectDetailByPkPatrolRecord(id).then(res => {
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
//@import '@/static/styles/autoee/patrolRecord/patrolRecordDetail.scss';
</style>
