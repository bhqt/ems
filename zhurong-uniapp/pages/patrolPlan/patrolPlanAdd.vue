<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更计划名称" name="patrolPlanName">
				<uni-easyinput v-model="pageData.addUpdateForm.patrolPlanName" placeholder="请输入巡更计划名称" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更路线" name="patrolPathId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolPathId" :localdata="pageData.dictData.a_patrol_path" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更人员" name="patrolUserId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolUserId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更周期" name="patrolCycleType">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolCycleType" :localdata="pageData.dictData.patrol_cycle_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="计划开始日期" name="startDate">
				<uni-datetime-picker v-model="pageData.addUpdateForm.startDate" type="date" return-type="string" clearable placeholder="请选择"/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="计划结束日期" name="endDate">
				<uni-datetime-picker v-model="pageData.addUpdateForm.endDate" type="date" return-type="string" clearable placeholder="请选择"/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="计划状态" name="patrolPlanStatus">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolPlanStatus" :localdata="pageData.dictData.patrol_plan_status" placeholder="请选择" clearable>
				</uni-data-picker>
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
import CommonUpload from '@/components/common-upload/common-upload.vue';
import patrolPlan from '@/api/autoee/patrolPlan';

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
					patrolPlanName: "",
					patrolPathId: "",
					patrolUserId: "",
					startTime: "",
					endTime: "",
					patrolCycleType: "",
					startDate: "",
					endDate: "",
					patrolPlanStatus: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 巡更计划名称
				patrolPlanName: {
					rules: [
					{ required: true, errorMessage: "巡更计划名称不能为空", trigger: "blur" },
				]
				},
				// 巡更路线
				patrolPathId: {
					rules: [
					{ required: true, errorMessage: "巡更路线不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更人员
				patrolUserId: {
					rules: [
					{ required: true, errorMessage: "巡更人员不能为空并且为整数", trigger: "change" },
				]
				},
				// 开始时间
				startTime: {
					rules: [
					{ required: true, errorMessage: "开始时间不能为空", trigger: "blur" },
				]
				},
				// 结束时间
				endTime: {
					rules: [
					{ required: true, errorMessage: "结束时间不能为空", trigger: "blur" },
				]
				},
				// 巡更周期
				patrolCycleType: {
					rules: [
					{ required: true, errorMessage: "巡更周期不能为空", trigger: "change" },
				]
				},
				// 计划开始日期
				startDate: {
					rules: [
					{ required: true, errorMessage: "计划开始日期不能为空", trigger: "blur" },
				]
				},
				// 计划结束日期
				endDate: {
					rules: [
					{ required: true, errorMessage: "计划结束日期不能为空", trigger: "blur" },
				]
				},
				// 计划状态
				patrolPlanStatus: {
					rules: [
					{ required: true, errorMessage: "计划状态不能为空", trigger: "change" },
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
					a_patrol_path: [],
					sys_user: [],
					patrol_cycle_type: [],
					patrol_plan_status: [],
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
	    let title = "新增巡更计划"
	    if (options.id){
			title = "修改巡更计划"
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
				this.pageData.dictData.a_patrol_path = await getDictData('a_patrol_path')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.patrol_cycle_type = await getDictData('patrol_cycle_type')
				this.pageData.dictData.patrol_plan_status = await getDictData('patrol_plan_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	patrolPlan.selectDetailByPkPatrolPlan(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
				// this.$modal.confirm('确认提交当前操作？').then(() => {
					if (this.pageData.addUpdateForm.id != null) {
						// 修改巡更计划：只能用于前端form表单的更新操作，清空的字段回写为null
					  patrolPlan.updateNullValueByPatrolPlan(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`修改成功`);
							this.$modal.alertCallback(`修改成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./patrolPlanList`
								// })
							});
					  });
					} else {
					  patrolPlan.addPatrolPlan(pageData.addUpdateForm).then(response => {
							// this.$api.msg(`新增成功`);
							this.$modal.alertCallback(`新增成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./patrolPlanList`
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
//@import '@/static/styles/autoee/patrolPlan/patrolPlanAdd.scss';
</style>
