<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="设备类型" name="deviceTypeDict">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deviceTypeDict" :localdata="pageData.dictData.a_iot_device_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="监控参数" name="alarmMonitorParam">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.alarmMonitorParam" :localdata="pageData.dictData.alarm_monitor_param" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="报警名称" name="alarmName">
				<uni-easyinput v-model="pageData.addUpdateForm.alarmName" placeholder="请输入报警名称" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="值类型" name="valueType">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.valueType" :localdata="pageData.dictData.value_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="报警条件" name="alarmCondition">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.alarmCondition" :localdata="pageData.dictData.alarm_condition" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="报警阈值" name="alarmValue">
				<uni-easyinput v-model="pageData.addUpdateForm.alarmValue" placeholder="请输入报警阈值" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="生成工单" name="createWorkOrde">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.createWorkOrde" :localdata="pageData.dictData.sys_yes_no" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="报警开关" name="alarmSwitch">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.alarmSwitch" :localdata="pageData.dictData.alarm_switch" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="备注" name="remark">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.remark" placeholder="请输入备注" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="用户id" name="userId">
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
import iotAlarmRule from '@/api/autoee/iotAlarmRule';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					deviceTypeDict: "",
					alarmMonitorParam: "",
					alarmName: "",
					valueType: "",
					alarmCondition: "",
					alarmValue: "",
					alarmSwitch: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 设备类型
				deviceTypeDict: {
					rules: [
					{ required: true, errorMessage: "设备类型不能为空", trigger: "change" },
				]
				},
				// 监控参数
				alarmMonitorParam: {
					rules: [
				]
				},
				// 报警名称
				alarmName: {
					rules: [
					{ required: true, errorMessage: "报警名称不能为空", trigger: "blur" },
				]
				},
				// 值类型
				valueType: {
					rules: [
					{ required: true, errorMessage: "值类型不能为空", trigger: "change" },
				]
				},
				// 报警条件
				alarmCondition: {
					rules: [
					{ required: true, errorMessage: "报警条件不能为空", trigger: "change" },
				]
				},
				// 报警阈值
				alarmValue: {
					rules: [
					{ required: true, errorMessage: "报警阈值不能为空", trigger: "blur" },
				]
				},
				// 生成工单
				createWorkOrde: {
					rules: [
					{ required: true, errorMessage: "生成工单不能为空", trigger: "change" },
				]
				},
				// 报警开关
				alarmSwitch: {
					rules: [
					{ required: true, errorMessage: "报警开关不能为空", trigger: "change" },
				]
				},
				// 备注
				remark: {
					rules: [
				]
				},
				// 用户id
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
					a_iot_device_type: [],
					alarm_monitor_param: [],
					value_type: [],
					alarm_condition: [],
					alarm_switch: [],
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
	    let title = "新增报警规则"
	    if (options.id){
			title = "修改报警规则"
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
				this.pageData.dictData.a_iot_device_type = await getDictData('a_iot_device_type')
				this.pageData.dictData.alarm_monitor_param = await getDictData('alarm_monitor_param')
				this.pageData.dictData.value_type = await getDictData('value_type')
				this.pageData.dictData.alarm_condition = await getDictData('alarm_condition')
				this.pageData.dictData.alarm_switch = await getDictData('alarm_switch')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	iotAlarmRule.selectDetailByPkIotAlarmRule(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
     			if (this.pageData.addUpdateForm.id != null) {
					// 修改报警规则：只能用于前端form表单的更新操作，清空的字段回写为null
     			  iotAlarmRule.updateNullValueByIotAlarmRule(this.pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`修改成功`);
						this.$modal.alertCallback(`修改成功`, () => {
							uni.navigateBack()
							// this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			} else {
     			  iotAlarmRule.addIotAlarmRule(pageData.addUpdateForm).then(response => {
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
//@import '@/static/styles/autoee/iotAlarmRule/iotAlarmRuleAdd.scss';
</style>
