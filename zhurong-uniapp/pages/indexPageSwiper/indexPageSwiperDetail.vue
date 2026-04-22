<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="标题：" name="title">
				<text class="a_detail_text">{{pageData.detailData.title}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="状态：" name="newsStatus">
				<text class="a_detail_text">{{pageData.detailData.newsStatusExtend}}</text>
			</uni-forms-item>
		</uni-forms>
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
				detailData: {
					title: "",
					titleExtend: "",
					mainImage: "",
					mainImageExtend: "",
					detailImages: "",
					detailImagesExtend: "",
					detailContent: "",
					detailContentExtend: "",
					newsStatus: "",
					newsStatusExtend: "",
				},
				dictData: {
					// news_status: [],
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
				// this.pageData.dictData.news_status = await getDictData('news_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	indexPageSwiper.selectDetailByPkIndexPageSwiper(id).then(res => {
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
//@import '@/static/styles/autoee/indexPageSwiper/indexPageSwiperDetail.scss';
</style>
