<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.queryParams" ref="queryFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item class="a_query_form_item" label="区域代码" name="areaCode">
				<uni-easyinput v-model="pageData.queryParams.areaCode" placeholder="请输入区域代码" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="区域名称" name="areaName">
				<uni-easyinput v-model="pageData.queryParams.areaName" placeholder="请输入区域名称" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="负责人" name="principal">
				<uni-easyinput v-model="pageData.queryParams.principal" placeholder="请输入负责人" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="联系电话" name="mobile">
				<uni-easyinput v-model="pageData.queryParams.mobile" placeholder="请输入联系电话" clearable/>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="handleQuery">查询</button>
		</view>

	  <view class="list">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list>
				<!-- 所有字段  areaCode,   areaName,   principal,   mobile,   deptId,   areaImages,   updateTime,   -->
				<!-- direction 属性决定列表的排版方向 row,column -->
				<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
			  	<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
			  	<uni-list-item v-for="item in pageData.iotAreaList" :key="item.id"
								   :title="item.areaName.toString()"
								   :rightText="item.mobile.toString()"
								   :note="formatNote(item)"
								   thumb="/static/biaoshi.svg" thumb-size="medium"
								   showArrow link="navigateTo" :to="'./iotAreaDetail?id='+item.id">
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
import iotArea from '@/api/autoee/iotArea';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	iotAreaList: [],
				queryParams: {
					areaCode: "",
					areaName: "",
					principal: "",
					mobile: "",
					deptId: "",
				  	params: []
				},
				dictData: {
					sys_dept: [],
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

			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(){
			let data = this.pageData.queryParams
			data.limitTopN = 200
        	iotArea.selectDetailListByLikeIotArea(data).then(res => {
           	 	// console.log("getAddressByCurrentUser-res=", res)
            	let list = res.rows;
            	this.pageData.iotAreaList = list;
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
				return `${item.areaCode || ''}\n${item.principal || ''}`
			} catch (e) {
				return ''
			}
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/iotArea/iotAreaList.scss';
</style>
