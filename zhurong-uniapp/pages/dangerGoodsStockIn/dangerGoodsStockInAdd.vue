<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="危化品名称" name="dangerGoodsId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.dangerGoodsId" :localdata="pageData.dictData.a_danger_goods_info" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="入库数量">
				<uni-easyinput type="number" v-model="pageData.addUpdateForm.quantity" clearable placeholder="请输入入库数量" />
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="存放地点" name="supplier">
				<uni-easyinput v-model="pageData.addUpdateForm.supplier" placeholder="请输入存放地点" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="操作人员" name="userId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.userId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="备注" name="remark">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.remark" placeholder="请输入备注" clearable/>
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
import dangerGoodsStockIn from '@/api/autoee/dangerGoodsStockIn';

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
					dangerGoodsId: "",
					quantity: "",
					supplier: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,
                addOrUpdate: "add",
                addUpdateFormRules: {
				// 危化品名称
				dangerGoodsId: {
					rules: [
					{ required: true, errorMessage: "危化品名称不能为空并且为整数", trigger: "change" },
				]
				},
				// 入库数量
				quantity: {
					rules: [
					{ required: true, errorMessage: "入库数量不能为空并且为整数", trigger: "blur" },
					{validateFunction: proxy.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
				]
				},
				// 存放地点
				supplier: {
					rules: [
				]
				},
				// 操作人员
				userId: {
					rules: [
				]
				},
				// 备注
				remark: {
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
					a_danger_goods_info: [],
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
	    let title = "新增危化品入库记录"
	    if (options.id){
			title = "修改危化品入库记录"
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
				this.pageData.dictData.a_danger_goods_info = await getDictData('a_danger_goods_info')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.sys_dept = await getDictData('sys_dept')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	dangerGoodsStockIn.selectDetailByPkDangerGoodsStockIn(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
				// this.$modal.confirm('确认提交当前操作？').then(() => {
					if (this.pageData.addUpdateForm.id != null) {
						// 修改危化品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
					  dangerGoodsStockIn.updateNullValueByDangerGoodsStockIn(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`修改成功`);
							this.$modal.alertCallback(`修改成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./dangerGoodsStockInList`
								// })
							});
					  });
					} else {
					  dangerGoodsStockIn.addDangerGoodsStockIn(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`新增成功`);
							this.$modal.alertCallback(`新增成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./dangerGoodsStockInList`
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
//@import '@/static/styles/autoee/dangerGoodsStockIn/dangerGoodsStockInAdd.scss';
</style>
