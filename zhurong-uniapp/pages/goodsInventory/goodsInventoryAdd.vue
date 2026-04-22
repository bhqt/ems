<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="false" class="a_add_form_item" label="物品名称" name="goodsId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.goodsId" :localdata="pageData.dictData.a_goods_info" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="物品类型" name="goodsType">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.goodsType" :localdata="pageData.dictData.goods_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="规格型号" name="specification">
				<uni-easyinput v-model="pageData.addUpdateForm.specification" placeholder="请输入规格型号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="当前库存数量">
				<uni-easyinput type="number" v-model="pageData.addUpdateForm.currentStock" clearable placeholder="请输入当前库存数量" />
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="单位" name="goodsUnit">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.goodsUnit" :localdata="pageData.dictData.goods_unit" placeholder="请选择" clearable>
				</uni-data-picker>
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
import goodsInventory from '@/api/autoee/goodsInventory';

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
					currentStock: "",
				},
                open: false,
                title: "",
                loading: false,
                addOrUpdate: "add",
                addUpdateFormRules: {
				// 物品名称
				goodsId: {
					rules: [
					{ required: true, errorMessage: "物品名称不能为空并且为整数", trigger: "change" },
				]
				},
				// 物品类型
				goodsType: {
					rules: [
					{ required: true, errorMessage: "物品类型不能为空", trigger: "change" },
				]
				},
				// 规格型号
				specification: {
					rules: [
					{ required: true, errorMessage: "规格型号不能为空", trigger: "blur" },
				]
				},
				// 当前库存数量
				currentStock: {
					rules: [
					{ required: true, errorMessage: "当前库存数量不能为空并且为整数", trigger: "blur" },
					{validateFunction: proxy.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
				]
				},
				// 单位
				goodsUnit: {
					rules: [
					{ required: true, errorMessage: "单位不能为空", trigger: "change" },
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
					a_goods_info: [],
					goods_type: [],
					goods_unit: [],
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
	    let title = "新增物品库存"
	    if (options.id){
			title = "修改物品库存"
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
				this.pageData.dictData.a_goods_info = await getDictData('a_goods_info')
				this.pageData.dictData.goods_type = await getDictData('goods_type')
				this.pageData.dictData.goods_unit = await getDictData('goods_unit')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.sys_dept = await getDictData('sys_dept')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	goodsInventory.selectDetailByPkGoodsInventory(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
				// this.$modal.confirm('确认提交当前操作？').then(() => {
					if (this.pageData.addUpdateForm.id != null) {
						// 修改物品库存：只能用于前端form表单的更新操作，清空的字段回写为null
					  goodsInventory.updateNullValueByGoodsInventory(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`修改成功`);
							this.$modal.alertCallback(`修改成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./goodsInventoryList`
								// })
							});
					  });
					} else {
					  goodsInventory.addGoodsInventory(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`新增成功`);
							this.$modal.alertCallback(`新增成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./goodsInventoryList`
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
//@import '@/static/styles/autoee/goodsInventory/goodsInventoryAdd.scss';
</style>
