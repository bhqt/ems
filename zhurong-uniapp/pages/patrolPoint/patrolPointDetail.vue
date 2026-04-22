<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="点位名称：" name="pointName">
				<text class="a_detail_text">{{pageData.detailData.pointName}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="点位地点：" name="pointLocation">
				<text class="a_detail_text">{{pageData.detailData.pointLocation}}</text>
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
import patrolPoint from '@/api/autoee/patrolPoint';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	patrolPointList: [],
				detailData: {
					pointName: "",
					pointNameExtend: "",
					pointLocation: "",
					pointLocationExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
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
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	patrolPoint.selectDetailByPkPatrolPoint(id).then(res => {
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
//@import '@/static/styles/autoee/patrolPoint/patrolPointDetail.scss';
</style>
