<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.queryParams" ref="queryFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item class="a_query_form_item" label="任务名称" name="patrolTaskName">
				<uni-easyinput v-model="pageData.queryParams.patrolTaskName" placeholder="请输入任务名称" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更计划" name="patrolPlanId">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.patrolPlanId" :localdata="pageData.dictData.a_patrol_plan" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更路线" name="patrolPathId">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.patrolPathId" :localdata="pageData.dictData.a_patrol_path" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更日期">
				<uni-datetime-picker clearable start-placeholder="开始日期" end-placeholder="结束日期"
				                     v-model="pageData.dateRangePatrolDate" type="daterange" return-type="string" rangeSeparator="-"/>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="handleQuery">查询</button>
		</view>

	  <view class="list">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list>
				<!-- 所有字段  patrolTaskName,   patrolPlanId,   patrolPathId,   patrolUserId,   patrolDate,   startTime,   endTime,   patrolTaskStatus,   updateTime,   -->
				<!-- direction 属性决定列表的排版方向 row,column -->
				<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
			  	<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
			  	<uni-list-item v-for="item in pageData.patrolTaskList" :key="item.id"
								   :title="item.patrolTaskName ? item.patrolTaskName.toString() : ''"
								   :rightText="item.patrolTaskStatusExtend ? item.patrolTaskStatusExtend.toString() : ''"
								   :note="formatNote(item)"
								   :thumb="pageData.iconReqUrl + 'circle-arrow-right.svg'" thumb-size="medium"
								   showArrow link="navigateTo" :to="'./patrolTaskDetail?id='+item.id">
				</uni-list-item>
			</uni-list>
			<!-- 点击加载更多按钮 -->
			<view v-if="pageData.showLoadMoreButton && loadMoreStatus === 'more' && !isLoadingMore" class="load-more-btn-container">
				<button @click="loadMore">点击加载更多</button>
			</view>
			<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
			<uni-load-more :status="loadMoreStatus" />
		</view>
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
			// 上拉加载相关状态
		    loadMoreStatus: 'more', // 'more':可以加载更多, 'loading':加载中, 'noMore':没有更多数据
		    isLoadingMore: false,
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
			    showLoadMoreButton: config.showLoadMoreButton,
				userId: this.$store.getters.userId,
			  	patrolTaskList: [],
				queryParams: {
					patrolTaskName: "",
					patrolPlanId: "",
					patrolPathId: "",
					patrolUserId: "",
					patrolDate: "",
					patrolTaskStatus: "",
				  	params: [],
				    pageNum: 1,
				    pageSize: 10
				},
				dateRangePatrolDate: [],
				dictData: {
					a_patrol_plan: [],
					a_patrol_path: [],
					sys_user: [],
					patrol_task_status: [],
				}
			}
		}
	},
	async onLoad(options) {
	  	console.log('页面参数:', options)
    	this.initData()
	    // 允许传入参数进行查询列表时的过滤：目前支持下拉框字段
  		if (this.$stringUtil.isNotEmpty(options.patrolPlanId)) {
			this.pageData.queryParams.patrolPlanId = options.patrolPlanId
		}
  		if (this.$stringUtil.isNotEmpty(options.patrolPathId)) {
			this.pageData.queryParams.patrolPathId = options.patrolPathId
		}
  		if (this.$stringUtil.isNotEmpty(options.patrolUserId)) {
			this.pageData.queryParams.patrolUserId = options.patrolUserId
		}
  		if (this.$stringUtil.isNotEmpty(options.patrolTaskStatus)) {
			this.pageData.queryParams.patrolTaskStatus = options.patrolTaskStatus
		}
  		if (this.$stringUtil.isNotEmpty(options.userId)) {
			this.pageData.queryParams.userId = options.userId
		}
  		if (this.$stringUtil.isNotEmpty(options.deptId)) {
			this.pageData.queryParams.deptId = options.deptId
		}
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
  	/**
	 * 下拉刷新处理函数
	 */
	onPullDownRefresh() {
	     // 重置页码，重新加载第一页数据
		this.pageData.queryParams.pageNum = 1;
		this.loadMoreStatus = 'more';
		// 重新加载数据
		this.loadData().then(() => {
			// 数据加载完成后，停止下拉刷新动画
			uni.stopPullDownRefresh();
		});
	},
	/**
	 * 上拉触底事件，用于加载更多数据
	 */
	onReachBottom() {
		// 如果当前没有正在加载更多，且还有更多数据，则加载更多
		if (!this.isLoadingMore && this.loadMoreStatus === 'more') {
			this.loadMore();
		}
	},
	methods: {
		async initData() {
		 	try {
				this.pageData.dictData.a_patrol_plan = await getDictData('a_patrol_plan')
				this.pageData.dictData.a_patrol_path = await getDictData('a_patrol_path')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.patrol_task_status = await getDictData('patrol_task_status')
				// 初始化 dateRangePatrolDate
				this.pageData.dateRangePatrolDate = [];

			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(){
			return new Promise((resolve, reject) => {
				if (this.pageData.dateRangePatrolDate && this.pageData.dateRangePatrolDate.length > 0) {
					this.pageData.queryParams.params["beginPatrolDate"] = this.pageData.dateRangePatrolDate[0];
					this.pageData.queryParams.params["endPatrolDate"] = this.pageData.dateRangePatrolDate[1];
				} else {
					this.pageData.queryParams.params["beginPatrolDate"] = null;
					this.pageData.queryParams.params["endPatrolDate"] = null;
				}
				let data = this.pageData.queryParams
				//data.limitTopN = 200
        		patrolTask.selectPageListPatrolTask(data).then(res => {
           		 	// console.log("getAddressByCurrentUser-res=", res)
            		let list = res.rows;
	                // 如果是第一页，则直接替换数据，否则追加数据
	                if (this.pageData.queryParams.pageNum === 1) {
					  this.pageData.patrolTaskList = list;
					} else {
	              	  this.pageData.patrolTaskList = [...this.pageData.patrolTaskList, ...list];
					}
	            	// 设置加载状态
	            	if (list.length < this.pageData.queryParams.pageSize) {
	            	 	this.loadMoreStatus = 'noMore';
	            	} else {
	            	 	this.loadMoreStatus = 'more';
	            	}
	            	resolve(res);
	        	}).catch(error => {
	        		console.error('加载数据失败:', error);
	        		reject(error);
	        	})
	        });
	    },
		/**
		 * 加载更多数据
		 */
		loadMore() {
			this.isLoadingMore = true;
			this.loadMoreStatus = 'loading';
			// 增加页码
			this.pageData.queryParams.pageNum += 1;
			this.loadData().catch(error => {
				console.error('加载更多数据失败:', error);
				this.loadMoreStatus = 'more';
			}).finally(() => {
				this.isLoadingMore = false;
			});
		},
		//查询
		handleQuery()
		{
			// 重置页码，重新加载第一页数据
			this.pageData.queryParams.pageNum = 1;
			this.loadMoreStatus = 'more';
		    this.loadData()
		},
	    //格式化note信息
		formatNote(item) {
			try {
				return ` ${item.patrolPlanIdExtend.toString()} \n  ${item.patrolPathIdExtend.toString()} \n  ${item.patrolDate.toString()} \n  ${item.startTime.toString()} \n  ${item.endTime.toString()}`
			} catch (e) {
				return ''
			}
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/patrolTask/patrolTaskList.scss';
</style>
