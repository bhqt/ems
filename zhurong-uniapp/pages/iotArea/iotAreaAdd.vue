<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="区域代码" name="areaCode">
				<uni-easyinput v-model="pageData.addUpdateForm.areaCode" placeholder="请输入区域代码" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="区域名称" name="areaName">
				<uni-easyinput v-model="pageData.addUpdateForm.areaName" placeholder="请输入区域名称" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="负责人" name="principal">
				<uni-easyinput v-model="pageData.addUpdateForm.principal" placeholder="请输入负责人" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="联系电话" name="mobile">
				<uni-easyinput v-model="pageData.addUpdateForm.mobile" placeholder="请输入联系电话" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="所属客群" name="deptId">
			  	<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deptId" :localdata="pageData.dictData.sys_dept" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="备注" name="remark">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.remark" placeholder="请输入备注" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="所属用户" name="userId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.userId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>
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
import iotArea from '@/api/autoee/iotArea';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					areaCode: "",
					areaName: "",
					principal: "",
					mobile: "",
					deptId: "",
					areaImages: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 区域代码
				areaCode: {
					rules: [
					{ required: true, errorMessage: "区域代码不能为空", trigger: "blur" },
				]
				},
				// 区域名称
				areaName: {
					rules: [
					{ required: true, errorMessage: "区域名称不能为空", trigger: "blur" },
				]
				},
				// 负责人
				principal: {
					rules: [
					{ required: true, errorMessage: "负责人不能为空", trigger: "blur" },
				]
				},
				// 联系电话
				mobile: {
					rules: [
					{ required: true, errorMessage: "联系电话不能为空", trigger: "blur" },
     				{validateFunction: proxy.$validateRules.validatePhoneNo, trigger: ["blur", "change"]},
				]
				},
				// 所属客群
				deptId: {
					rules: [
				]
				},
				// 区域图片
				areaImages: {
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
				// 创建者
				createBy: {
					rules: [
				]
				},
				// 创建时间
				createTime: {
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
	    let title = "新增区域管理"
	    if (options.id){
			title = "修改区域管理"
			this.pageData.addOrUpdate = "update"
	  		this.loadData(options.id);
		}
		uni.setNavigationBarTitle({
			title
		})
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
				this.pageData.dictData.sys_dept = await getDictData('sys_dept')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	iotArea.selectDetailByPkIotArea(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
     			if (this.pageData.addUpdateForm.id != null) {
					// 修改区域管理：只能用于前端form表单的更新操作，清空的字段回写为null
     			  iotArea.updateNullValueByIotArea(this.pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`修改成功`);
						this.$modal.alertCallback(`修改成功`, () => {
							uni.navigateBack()
							// this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			} else {
     			  iotArea.addIotArea(pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`新增成功`);
						this.$modal.alertCallback(`新增成功`, () => {
							uni.navigateBack()
							// this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			}
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
//@import '@/static/styles/autoee/iotArea/iotAreaAdd.scss';
</style>
