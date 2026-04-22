<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更计划" name="patrolPlanId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolPlanId" :localdata="pageData.dictData.a_patrol_plan" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更路线" name="patrolPathId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolPathId" :localdata="pageData.dictData.a_patrol_path" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更点位" name="patrolPointId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolPointId" :localdata="pageData.dictData.a_patrol_point" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更人员" name="patrolUserId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolUserId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更任务" name="patrolTaskId">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolTaskId" :localdata="pageData.dictData.a_patrol_task" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="点位顺序">
				<uni-easyinput type="number" v-model="pageData.addUpdateForm.pointOrder" clearable placeholder="请输入点位顺序" />
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="点位巡更时间" name="arriveTime">
				<uni-datetime-picker v-model="pageData.addUpdateForm.arriveTime" type="datetime" return-type="string" placeholder="请选择" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="巡更结果" name="patrolResult">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.patrolResult" :localdata="pageData.dictData.patrol_result" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="结果描述" name="resultDesc">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.resultDesc" placeholder="请输入结果描述" clearable/>
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
import patrolRecord from '@/api/autoee/patrolRecord';

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
					patrolPlanId: "",
					patrolPathId: "",
					patrolPointId: "",
					patrolUserId: "",
					patrolTaskId: "",
					pointOrder: "",
					arriveTime: "",
					patrolResult: "",
					resultDesc: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 巡更计划
				patrolPlanId: {
					rules: [
					{ required: true, errorMessage: "巡更计划不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更路线
				patrolPathId: {
					rules: [
					{ required: true, errorMessage: "巡更路线不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更点位
				patrolPointId: {
					rules: [
					{ required: true, errorMessage: "巡更点位不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更人员
				patrolUserId: {
					rules: [
					{ required: true, errorMessage: "巡更人员不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更任务
				patrolTaskId: {
					rules: [
					{ required: true, errorMessage: "巡更任务不能为空并且为整数", trigger: "change" },
				]
				},
				// 点位顺序
				pointOrder: {
					rules: [
					{ required: true, errorMessage: "点位顺序不能为空并且为整数", trigger: "blur" },
					{validateFunction: proxy.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
				]
				},
				// 点位巡更时间
				arriveTime: {
					rules: [
					{ required: true, errorMessage: "点位巡更时间不能为空", trigger: "blur" },
				]
				},
				// 巡更结果
				patrolResult: {
					rules: [
					{ required: true, errorMessage: "巡更结果不能为空", trigger: "change" },
				]
				},
				// 结果描述
				resultDesc: {
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
					a_patrol_plan: [],
					a_patrol_path: [],
					sys_user: [],
					a_patrol_task: [],
					patrol_result: [],
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
	    let title = "新增巡更记录"
	    if (options.id){
			title = "修改巡更记录"
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
				this.pageData.dictData.a_patrol_plan = await getDictData('a_patrol_plan')
				this.pageData.dictData.a_patrol_path = await getDictData('a_patrol_path')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.a_patrol_task = await getDictData('a_patrol_task')
				this.pageData.dictData.patrol_result = await getDictData('patrol_result')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	patrolRecord.selectDetailByPkPatrolRecord(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
				// this.$modal.confirm('确认提交当前操作？').then(() => {
					if (this.pageData.addUpdateForm.id != null) {
						// 修改巡更记录：只能用于前端form表单的更新操作，清空的字段回写为null
					  patrolRecord.updateNullValueByPatrolRecord(this.pageData.addUpdateForm).then(response => {
							// this.$api.msg(`修改成功`);
							this.$modal.alertCallback(`修改成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./patrolRecordList`
								// })
							});
					  });
					} else {
					  patrolRecord.addPatrolRecord(pageData.addUpdateForm).then(response => {
							// this.$api.msg(`新增成功`);
							this.$modal.alertCallback(`新增成功`, () => {
								uni.navigateBack()
								// this.$tab.reLaunch('/pages/index/index');
								// uni.navigateTo({
								  //     url: `./patrolRecordList`
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
//@import '@/static/styles/autoee/patrolRecord/patrolRecordAdd.scss';
</style>
