<template>
	<view class="a_container">
        <uni-forms class="a_query_form" :model="pageData.addUpdateForm" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="报警编号：" name="alarmNo">
				<text class="a_detail_text">{{pageData.addUpdateForm.alarmNo}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更计划：" name="patrolPlanId">
				<text class="a_detail_text">{{pageData.addUpdateForm.patrolPlanIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="巡更任务：" name="patrolTaskId">
				<text class="a_detail_text">{{pageData.addUpdateForm.patrolTaskIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警类型：" name="patrolAlarmType">
				<text class="a_detail_text">{{pageData.addUpdateForm.patrolAlarmTypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警时间：">
				<text class="a_detail_text">{{pageData.addUpdateForm.patrolAlarmTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警内容：" name="patrolAlarmContent">
				<text class="a_detail_text">{{pageData.addUpdateForm.patrolAlarmContent}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报警状态：" name="patrolAlarmStatus">
				<text class="a_detail_text">{{pageData.addUpdateForm.patrolAlarmStatusExtend}}</text>
			</uni-forms-item>
		</uni-forms>
        <view class="indent-line"></view>
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">

<!--			<uni-forms-item v-show="true" class="a_add_form_item" label="处理人" name="handleUserId">-->
<!--				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.handleUserId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>-->
<!--				</uni-data-picker>-->
<!--			</uni-forms-item>-->
			<uni-forms-item v-show="true" class="a_add_form_item" label="处理时间" name="handleTime">
				<uni-datetime-picker v-model="pageData.addUpdateForm.handleTime" type="datetime" return-type="string" placeholder="请选择" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="处理描述" name="handleDesc">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.handleDesc" placeholder="请输入处理描述" clearable/>
			</uni-forms-item>
<!--			<uni-forms-item v-show="true" class="a_add_form_item" label="备注" name="remark">-->
<!--				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.remark" placeholder="请输入备注" clearable/>-->
<!--			</uni-forms-item>-->

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
import patrolAlarm from '@/api/autoee/patrolAlarm';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					alarmNo: "",
					patrolPlanId: "",
					patrolTaskId: "",
					patrolUserId: "",
					patrolAlarmType: "",
					patrolAlarmTime: "",
					patrolAlarmContent: "",
					patrolAlarmStatus: "",
					handleUserId: "",
					handleTime: "",
					handleDesc: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 报警编号
				alarmNo: {
					rules: [
					{ required: true, errorMessage: "报警编号不能为空", trigger: "blur" },
				]
				},
				// 巡更计划
				patrolPlanId: {
					rules: [
					{ required: true, errorMessage: "巡更计划不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更任务
				patrolTaskId: {
					rules: [
					{ required: true, errorMessage: "巡更任务不能为空并且为整数", trigger: "change" },
				]
				},
				// 巡更人员
				patrolUserId: {
					rules: [
					{ required: true, errorMessage: "巡更人员不能为空并且为整数", trigger: "change" },
				]
				},
				// 报警类型
				patrolAlarmType: {
					rules: [
					{ required: true, errorMessage: "报警类型不能为空", trigger: "change" },
				]
				},
				// 报警时间
				patrolAlarmTime: {
					rules: [
					{ required: true, errorMessage: "报警时间不能为空", trigger: "blur" },
				]
				},
				// 报警内容
				patrolAlarmContent: {
					rules: [
					{ required: true, errorMessage: "报警内容不能为空", trigger: "blur" },
				]
				},
				// 报警状态
				patrolAlarmStatus: {
					rules: [
					{ required: true, errorMessage: "报警状态不能为空", trigger: "change" },
				]
				},
				// 处理人
				handleUserId: {
					rules: [
				]
				},
				// 处理时间
				handleTime: {
					rules: [
                        { required: true, errorMessage: "处理时间不能为空", trigger: "blur" },
				]
				},
				// 处理描述
				handleDesc: {
					rules: [
                        { required: true, errorMessage: "处理描述不能为空", trigger: "blur" },
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
					a_patrol_task: [],
					sys_user: [],
					patrol_alarm_type: [],
					patrol_alarm_status: [],
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
	    let title = "新增巡更报警"
	    if (options.id){
			title = "修改巡更报警"
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
				this.pageData.dictData.a_patrol_task = await getDictData('a_patrol_task')
				this.pageData.dictData.sys_user = await getDictData('sys_user')
				this.pageData.dictData.patrol_alarm_type = await getDictData('patrol_alarm_type')
				this.pageData.dictData.patrol_alarm_status = await getDictData('patrol_alarm_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	patrolAlarm.selectDetailByPkPatrolAlarm(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			// uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
                this.$modal.confirm('确认提交当前操作？').then(() => {
     			if (this.pageData.addUpdateForm.id != null) {
                     this.pageData.addUpdateForm.handleUserId = this.pageData.userId
                     this.pageData.addUpdateForm.patrolAlarmStatus = "220"
					// 修改巡更报警：只能用于前端form表单的更新操作，清空的字段回写为null
     			  patrolAlarm.updateNullValueByPatrolAlarm(this.pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`修改成功`);
						this.$modal.alertCallback(`提交成功`, () => {
							// uni.navigateBack()
							this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			} else {
     			  patrolAlarm.addPatrolAlarm(pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`新增成功`);
						this.$modal.alertCallback(`新增成功`, () => {
							uni.navigateBack()
							// this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			}
                 })
			}).catch(err => {
				// uni.hideLoading()
				console.log('表单错误信息：', err);
			})
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/patrolAlarm/patrolAlarmAdd.scss';
</style>
