<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="工单编号：" name="orderNo">
				<text class="a_detail_text">{{pageData.detailData.orderNo}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="问题描述：" name="description">
				<text class="a_detail_text">{{pageData.detailData.description}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="故障类型：" name="orderFaultType">
				<text class="a_detail_text">{{pageData.detailData.orderFaultTypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="优先级：" name="orderPriority">
				<text class="a_detail_text">{{pageData.detailData.orderPriorityExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="工单状态：" name="repairOrderStatus">
				<text class="a_detail_text">{{pageData.detailData.repairOrderStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="故障位置：" name="location">
				<text class="a_detail_text">{{pageData.detailData.location}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报修人：" name="reporterId">
				<text class="a_detail_text">{{pageData.detailData.reporterIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报修人电话：" name="reporterContact">
				<text class="a_detail_text">{{pageData.detailData.reporterContact}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报修时间：">
				<text class="a_detail_text">{{pageData.detailData.reportTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="维修人：" name="assigneeId">
				<text class="a_detail_text">{{pageData.detailData.assigneeIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="维修结果：" name="repairResult">
				<text class="a_detail_text">{{pageData.detailData.repairResult}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="维修图片：">
                <image-show-list :images="pageData.detailData.repairImages" :size="100" :gap="20" />
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="完成时间：">
				<text class="a_detail_text">{{pageData.detailData.completionTime}}</text>
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
// import ImageShowList from '@/components/image-show-list';// 在页面引入组件，通过main.js全局引入时小程序中不好使
import maintainOrder from '@/api/autoee/maintainOrder';

export default {
    components: {
		// ImageShowList
	},
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	maintainOrderList: [],
				detailData: {
					orderNo: "",
					orderNoExtend: "",
					description: "",
					descriptionExtend: "",
					orderFaultType: "",
					orderFaultTypeExtend: "",
					orderPriority: "",
					orderPriorityExtend: "",
					repairOrderStatus: "",
					repairOrderStatusExtend: "",
					location: "",
					locationExtend: "",
					reporterId: "",
					reporterIdExtend: "",
					reporterContact: "",
					reporterContactExtend: "",
					reportTime: "",
					reportTimeExtend: "",
					assigneeId: "",
					assigneeIdExtend: "",
					repairResult: "",
					repairResultExtend: "",
					repairImages: "",
					repairImagesExtend: "",
					completionTime: "",
					completionTimeExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// order_fault_type: [],
					// order_priority: [],
					// repair_order_status: [],
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
				// this.pageData.dictData.order_fault_type = await getDictData('order_fault_type')
				// this.pageData.dictData.order_priority = await getDictData('order_priority')
				// this.pageData.dictData.repair_order_status = await getDictData('repair_order_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	maintainOrder.selectDetailByPkMaintainOrder(id).then(res => {
           	 	console.log('maintainOrderDetail: 后端返回数据:', res.data);
           	 	console.log('maintainOrderDetail: 后端返回repairImages:', res.data.repairImages, typeof res.data.repairImages);
           	 	// 处理数据中的null值，转换为空字符串
           	 	this.pageData.detailData = res.data;
           	 	console.log('maintainOrderDetail: 赋值后pageData.detailData.repairImages:', this.pageData.detailData.repairImages);
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
//@import '@/static/styles/autoee/maintainOrder/maintainOrderDetail.scss';
</style>
