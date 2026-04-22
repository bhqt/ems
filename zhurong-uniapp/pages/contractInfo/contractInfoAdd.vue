<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="合同编号(新)" name="contractNoNew">
				<uni-easyinput v-model="pageData.addUpdateForm.contractNoNew" placeholder="请输入合同编号(新)" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="续签编号(老)" name="contractNoOld">
				<uni-easyinput v-model="pageData.addUpdateForm.contractNoOld" placeholder="请输入续签编号(老)" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="所属客户" name="belongCustomer">
				<uni-easyinput v-model="pageData.addUpdateForm.belongCustomer" placeholder="请输入所属客户" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="客户方联系人" name="customerContact">
				<uni-easyinput v-model="pageData.addUpdateForm.customerContact" placeholder="请输入客户方联系人" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="合同类型" name="contractType">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.contractType" :localdata="pageData.dictData.contract_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="合同子类型" name="contractSubtype">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.contractSubtype" :localdata="pageData.dictData.contract_subtype" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="签约公司" name="signCompany">
				<uni-easyinput v-model="pageData.addUpdateForm.signCompany" placeholder="请输入签约公司" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="业务员" name="salesmanId">
				<uni-easyinput v-model="pageData.addUpdateForm.salesmanId" placeholder="请输入业务员" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="技术支持" name="techSupport">
				<uni-easyinput v-model="pageData.addUpdateForm.techSupport" placeholder="请输入技术支持" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="报价单号" name="quoteNo">
				<uni-easyinput v-model="pageData.addUpdateForm.quoteNo" placeholder="请输入报价单号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="合同总价" name="contractTotal">
				<uni-easyinput v-model="pageData.addUpdateForm.contractTotal" placeholder="请输入合同总价" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="已收金额" name="receivedAmount">
				<uni-easyinput v-model="pageData.addUpdateForm.receivedAmount" placeholder="请输入已收金额" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="签约日期" name="signDate">
				<uni-datetime-picker v-model="pageData.addUpdateForm.signDate" type="date" return-type="string" clearable placeholder="请选择"/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="附件" name="attachmentFiles">
				<common-upload
					v-model="pageData.addUpdateForm.attachmentFiles"
					ref="commonUpload"
					file-type="file"
					:limit="9"
				/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="备注" name="remark">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.remark" placeholder="请输入备注" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="所属用户" name="userId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.userId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="所属部门" name="deptId">
			  	<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deptId" :localdata="pageData.dictData.sys_dept" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="submitForm">提交</button>
		</view>

	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
// 手动注册组件，或者通过easycom 模式自动扫描项目中 components 目录下的组件，加一层目录，名称和文件名一致
import CommonUpload from '@/components/common-upload/common-upload.vue';
import contractInfo from '@/api/autoee/contractInfo';

export default {
	components: {
		CommonUpload
	},
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					contractNoNew: "",
					contractNoOld: "",
					belongCustomer: "",
					customerContact: "",
					contractType: "",
					contractSubtype: "",
					signCompany: "",
					salesmanId: "",
					techSupport: "",
					quoteNo: "",
					contractTotal: "",
					receivedAmount: "",
					signDate: "",
					attachmentFiles: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,
                addOrUpdate: "add",
                addUpdateFormRules: {
				// 合同编号(新)
				contractNoNew: {
					rules: [
					{ required: true, errorMessage: "合同编号(新)不能为空", trigger: "blur" },
				]
				},
				// 续签编号(老)
				contractNoOld: {
					rules: [
				]
				},
				// 所属客户
				belongCustomer: {
					rules: [
					{ required: true, errorMessage: "所属客户不能为空", trigger: "blur" },
				]
				},
				// 客户方联系人
				customerContact: {
					rules: [
					{ required: true, errorMessage: "客户方联系人不能为空", trigger: "blur" },
				]
				},
				// 合同类型
				contractType: {
					rules: [
					{ required: true, errorMessage: "合同类型不能为空", trigger: "change" },
				]
				},
				// 合同子类型
				contractSubtype: {
					rules: [
				]
				},
				// 签约公司
				signCompany: {
					rules: [
				]
				},
				// 业务员
				salesmanId: {
					rules: [
				]
				},
				// 技术支持
				techSupport: {
					rules: [
				]
				},
				// 报价单号
				quoteNo: {
					rules: [
				]
				},
				// 合同总价
				contractTotal: {
					rules: [
				]
				},
				// 已收金额
				receivedAmount: {
					rules: [
				]
				},
				// 签约日期
				signDate: {
					rules: [
				]
				},
				// 附件
				attachmentFiles: {
					rules: [
				]
				},
				// 备注
				remark: {
					rules: [
				]
				},
				// 所属用户
				userId: {
					rules: [
				]
				},
				// 所属部门
				deptId: {
					rules: [
				]
				},
				// 创建时间
				createTime: {
					rules: [
				]
				},
				// 创建者
				createBy: {
					rules: [
				]
				},
				// 更新者
				updateBy: {
					rules: [
				]
				},
				// 更新时间
				updateTime: {
					rules: [
				]
				},
				// 删除标志
				delFlag: {
					rules: [
				]
				},
				// 删除者
				delBy: {
					rules: [
				]
				},
				// 删除时间
				delTime: {
					rules: [
				]
				}
                },
				dictData: {
					contract_type: [],
					contract_subtype: [],
					sys_user: [],
					sys_dept: [],
				}
			}
		}
	},
	onReady() {
		// 需要在onReady中设置规则
		// this.$refs.addUpdateForm.setRules(this.pageData.addUpdateFormRules)
	},
  	onLoad(options) {
	  	console.log('页面参数:', options)
    	this.initData()
	    let title = "新增合同信息管理"
	    if (options.id){
			title = "修改合同信息管理"
			this.pageData.addOrUpdate = "update"
	  		this.loadData(options.id);
		}
		// 页面名称放到pages.json中，方便修改管理
		// uni.setNavigationBarTitle({
		// 	title
		// })
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
		async initData() {
		 	try {
				this.pageData.dictData.contract_type = await getDictData('contract_type')
				this.pageData.dictData.contract_subtype = await getDictData('contract_subtype')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.sys_dept = await getDictData('sys_dept')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	contractInfo.selectDetailByPkContractInfo(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
				// this.$modal.confirm('确认提交当前操作？').then(() => {
					if (this.pageData.addUpdateForm.id != null) {
						// 修改合同信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
					  contractInfo.updateNullValueByContractInfo(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`修改成功`);
							this.$modal.alertCallback(`修改成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./contractInfoList`
								// })
							});
					  });
					} else {
					  contractInfo.addContractInfo(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`新增成功`);
							this.$modal.alertCallback(`新增成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./contractInfoList`
								// })
							});
					  });
					}
				// })
			}).catch(err => {
				uni.hideLoading()
				console.log('表单错误信息：', err);
			})
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/contractInfo/contractInfoAdd.scss';
</style>
