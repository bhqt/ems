<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.queryParams" ref="queryFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item class="a_query_form_item" label="标题" name="title">
				<uni-easyinput v-model="pageData.queryParams.title" placeholder="请输入标题" clearable/>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="handleQuery">查询</button>
		</view>

	  <view class="list">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list>
				<!-- 所有字段  title,   mainImage,   detailImages,   newsStatus,   updateTime,   -->
				<!-- direction 属性决定列表的排版方向 row,column -->
				<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
					<uni-list-item v-for="item in pageData.indexPageSwiperList" :key="item.id"
								   thumb="/static/selected.png" thumb-size="sm"
								   showArrow link="navigateTo" :to="'./indexPageSwiperDetail?id='+item.id">
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
import indexPageSwiper from '@/api/autoee/indexPageSwiper';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	indexPageSwiperList: [],
				queryParams: {
					title: "",
					newsStatus: "",
				},
				dictData: {
					news_status: [],
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
		this.loadData();
    },
	mounted(){

	},
	computed: {
		...mapState(['user'])
	},
	methods: {
		async initData() {
		 	try {
				this.pageData.dictData.news_status = await getDictData('news_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(){
			let data = this.pageData.queryParams
        	data.studentNo = this.$store.getters.name
        	indexPageSwiper.selectDetailListByLikeIndexPageSwiper(data).then(res => {
           	 	// console.log("getAddressByCurrentUser-res=", res)
            	let list = res.rows;
            	this.pageData.indexPageSwiperList = list;
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
//@import '@/static/styles/autoee/indexPageSwiper/indexPageSwiperList.scss';
</style>
