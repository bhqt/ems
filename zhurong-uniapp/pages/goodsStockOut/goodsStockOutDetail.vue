<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="入库编号：" name="goodsStockInId">
				<text class="a_detail_text">{{pageData.detailData.goodsStockInIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="物品名称：" name="goodsId">
				<text class="a_detail_text">{{pageData.detailData.goodsIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="出库数量：">
				<text class="a_detail_text">{{pageData.detailData.quantity ? pageData.detailData.quantity : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="出库原因：" name="reason">
				<text class="a_detail_text">{{pageData.detailData.reason ? pageData.detailData.reason : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="备注：" name="remark">
				<text class="a_detail_text">{{pageData.detailData.remark ? pageData.detailData.remark : '' }}</text>
			</uni-forms-item>
		</uni-forms>
	    <view class="indent-line"></view>
	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
// 手动注册组件，或者通过easycom 模式自动扫描项目中 components 目录下的组件，加一层目录，目录名称和文件名一致
import ImageShowList from '@/components/image-show-list/image-show-list';
import goodsStockOut from '@/api/autoee/goodsStockOut';

export default {
    components: {
		ImageShowList
	},
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
			  	goodsStockOutList: [],
				detailData: {
					goodsStockInId: "",
					goodsStockInIdExtend: "",
					goodsId: "",
					goodsIdExtend: "",
					quantity: "",
					quantityExtend: "",
					reason: "",
					reasonExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// a_goods_info: [],
					// sys_user: [],
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
				// this.pageData.dictData.a_goods_info = await getDictData('a_goods_info')
				// this.pageData.dictData.sys_user = await getDictData('sys_user')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	goodsStockOut.selectDetailByPkGoodsStockOut(id).then(res => {
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
//@import '@/static/styles/autoee/goodsStockOut/goodsStockOutDetail.scss';
</style>
