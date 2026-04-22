<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.detailData" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="合同编号(新)：" name="contractNoNew">
				<text class="a_detail_text">{{pageData.detailData.contractNoNew ? pageData.detailData.contractNoNew : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="续签编号(老)：" name="contractNoOld">
				<text class="a_detail_text">{{pageData.detailData.contractNoOld ? pageData.detailData.contractNoOld : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="所属客户：" name="belongCustomer">
				<text class="a_detail_text">{{pageData.detailData.belongCustomer ? pageData.detailData.belongCustomer : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="客户方联系人：" name="customerContact">
				<text class="a_detail_text">{{pageData.detailData.customerContact ? pageData.detailData.customerContact : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="合同类型：" name="contractType">
				<text class="a_detail_text">{{pageData.detailData.contractTypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="合同子类型：" name="contractSubtype">
				<text class="a_detail_text">{{pageData.detailData.contractSubtypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="签约公司：" name="signCompany">
				<text class="a_detail_text">{{pageData.detailData.signCompany ? pageData.detailData.signCompany : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="业务员：" name="salesmanId">
				<text class="a_detail_text">{{pageData.detailData.salesmanId ? pageData.detailData.salesmanId : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="技术支持：" name="techSupport">
				<text class="a_detail_text">{{pageData.detailData.techSupport ? pageData.detailData.techSupport : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报价单号：" name="quoteNo">
				<text class="a_detail_text">{{pageData.detailData.quoteNo ? pageData.detailData.quoteNo : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="合同总价：" name="contractTotal">
				<text class="a_detail_text">{{pageData.detailData.contractTotal ? pageData.detailData.contractTotal : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="已收金额：" name="receivedAmount">
				<text class="a_detail_text">{{pageData.detailData.receivedAmount ? pageData.detailData.receivedAmount : '' }}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="签约日期：">
				<text class="a_detail_text">{{pageData.detailData.signDate}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="附件：">
			  	<div v-for="(file, index) in pageData.detailData.attachmentFiles ?  pageData.detailData.attachmentFiles.split(',') : []" :key="index">
					<el-link type="primary" :href="file" download>{{ file.split('/').pop() }}</el-link>
				</div>
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
import contractInfo from '@/api/autoee/contractInfo';

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
			  	contractInfoList: [],
				detailData: {
					contractNoNew: "",
					contractNoNewExtend: "",
					contractNoOld: "",
					contractNoOldExtend: "",
					belongCustomer: "",
					belongCustomerExtend: "",
					customerContact: "",
					customerContactExtend: "",
					contractType: "",
					contractTypeExtend: "",
					contractSubtype: "",
					contractSubtypeExtend: "",
					signCompany: "",
					signCompanyExtend: "",
					salesmanId: "",
					salesmanIdExtend: "",
					techSupport: "",
					techSupportExtend: "",
					quoteNo: "",
					quoteNoExtend: "",
					contractTotal: "",
					contractTotalExtend: "",
					receivedAmount: "",
					receivedAmountExtend: "",
					signDate: "",
					signDateExtend: "",
					attachmentFiles: "",
					attachmentFilesExtend: "",
					remark: "",
					remarkExtend: "",
				},
				dictData: {
					// contract_type: [],
					// contract_subtype: [],
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
				// this.pageData.dictData.contract_type = await getDictData('contract_type')
				// this.pageData.dictData.contract_subtype = await getDictData('contract_subtype')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
        	contractInfo.selectDetailByPkContractInfo(id).then(res => {
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
//@import '@/static/styles/autoee/contractInfo/contractInfoDetail.scss';
</style>
