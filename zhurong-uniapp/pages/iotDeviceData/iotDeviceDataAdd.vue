<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="所属客群" name="deptId">
			  	<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deptId" :localdata="pageData.dictData.sys_dept" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="所属区域" name="areaCodeDict">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.areaCodeDict" :localdata="pageData.dictData.a_iot_area" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="设备类型" name="deviceTypeDict">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deviceTypeDict" :localdata="pageData.dictData.a_iot_device_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="设备" name="deviceCodeDict">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deviceCodeDict" :localdata="pageData.dictData.a_iot_device" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块状态" name="moduleWorkStatus">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.moduleWorkStatus" :localdata="pageData.dictData.module_work_status" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块故障码" name="moduleFaultCode">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.moduleFaultCode" :localdata="pageData.dictData.module_fault_code" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="输出电压" name="voltage">
				<uni-easyinput v-model="pageData.addUpdateForm.voltage" placeholder="请输入输出电压" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="输出电流" name="current">
				<uni-easyinput v-model="pageData.addUpdateForm.current" placeholder="请输入输出电流" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="请求电压" name="requiredVoltage">
				<uni-easyinput v-model="pageData.addUpdateForm.requiredVoltage" placeholder="请输入请求电压" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="请求电流" name="requiredCurrent">
				<uni-easyinput v-model="pageData.addUpdateForm.requiredCurrent" placeholder="请输入请求电流" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="温度" name="temperature">
				<uni-easyinput v-model="pageData.addUpdateForm.temperature" placeholder="请输入温度" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="BUS电压" name="busVoltage">
				<uni-easyinput v-model="pageData.addUpdateForm.busVoltage" placeholder="请输入BUS电压" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="采集时间">
				<uni-easyinput type="number" v-model="pageData.addUpdateForm.collectTimestamp" clearable placeholder="请输入采集时间" />
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="采集时间" name="collectTime">
				<uni-datetime-picker v-model="pageData.addUpdateForm.collectTime" type="datetime" return-type="string" placeholder="请选择" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块前级版本号" name="softPreviousVersion">
				<uni-easyinput v-model="pageData.addUpdateForm.softPreviousVersion" placeholder="请输入模块前级版本号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块后级版本号" name="softSubsequentVersion">
				<uni-easyinput v-model="pageData.addUpdateForm.softSubsequentVersion" placeholder="请输入模块后级版本号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块上锁状态" name="moduleLockStatus">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.moduleLockStatus" :localdata="pageData.dictData.module_lock_status" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块剩余运行时间" name="moduleRemainingRuntime">
				<uni-easyinput v-model="pageData.addUpdateForm.moduleRemainingRuntime" placeholder="请输入模块剩余运行时间" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="模块剩余充电次数" name="moduleRemainingChargingTimes">
				<uni-easyinput v-model="pageData.addUpdateForm.moduleRemainingChargingTimes" placeholder="请输入模块剩余充电次数" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="信号强度">
				<uni-easyinput type="number" v-model="pageData.addUpdateForm.signalStrength" clearable placeholder="请输入信号强度" />
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="电池电量">
				<uni-easyinput type="number" v-model="pageData.addUpdateForm.batteryLevel" clearable placeholder="请输入电池电量" />
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="接收时间" name="serverTime">
				<uni-datetime-picker v-model="pageData.addUpdateForm.serverTime" type="datetime" return-type="string" placeholder="请选择" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="原始报文" name="rawData">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.rawData" placeholder="请输入原始报文" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="消息标识" name="messageId">
				<uni-easyinput v-model="pageData.addUpdateForm.messageId" placeholder="请输入消息标识" clearable/>
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
import iotDeviceData from '@/api/autoee/iotDeviceData';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					id: "",
					deptId: "",
					areaCodeDict: "",
					deviceTypeDict: "",
					deviceCodeDict: "",
					moduleWorkStatus: "",
					moduleFaultCode: "",
					voltage: "",
					current: "",
					requiredVoltage: "",
					requiredCurrent: "",
					temperature: "",
					busVoltage: "",
					collectTimestamp: "",
					collectTime: "",
					softPreviousVersion: "",
					softSubsequentVersion: "",
					moduleLockStatus: "",
					moduleRemainingRuntime: "",
					moduleRemainingChargingTimes: "",
					signalStrength: "",
					batteryLevel: "",
					serverTime: "",
					rawData: "",
					messageId: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 所属客群
				deptId: {
					rules: [
					{ required: true, errorMessage: "所属客群不能为空并且为整数", trigger: "change" },
				]
				},
				// 所属区域
				areaCodeDict: {
					rules: [
					{ required: true, errorMessage: "所属区域不能为空", trigger: "change" },
				]
				},
				// 设备类型
				deviceTypeDict: {
					rules: [
					{ required: true, errorMessage: "设备类型不能为空", trigger: "change" },
				]
				},
				// 设备
				deviceCodeDict: {
					rules: [
					{ required: true, errorMessage: "设备不能为空", trigger: "change" },
				]
				},
				// 模块状态
				moduleWorkStatus: {
					rules: [
				]
				},
				// 模块故障码
				moduleFaultCode: {
					rules: [
				]
				},
				// 输出电压
				voltage: {
					rules: [
				]
				},
				// 输出电流
				current: {
					rules: [
				]
				},
				// 请求电压
				requiredVoltage: {
					rules: [
				]
				},
				// 请求电流
				requiredCurrent: {
					rules: [
				]
				},
				// 温度
				temperature: {
					rules: [
				]
				},
				// BUS电压
				busVoltage: {
					rules: [
				]
				},
				// 采集时间
				collectTimestamp: {
					rules: [
					{ required: true, errorMessage: "采集时间不能为空并且为整数", trigger: "blur" },
					{validateFunction: proxy.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
				]
				},
				// 采集时间
				collectTime: {
					rules: [
				]
				},
				// 模块前级版本号
				softPreviousVersion: {
					rules: [
				]
				},
				// 模块后级版本号
				softSubsequentVersion: {
					rules: [
				]
				},
				// 模块上锁状态
				moduleLockStatus: {
					rules: [
				]
				},
				// 模块剩余运行时间
				moduleRemainingRuntime: {
					rules: [
				]
				},
				// 模块剩余充电次数
				moduleRemainingChargingTimes: {
					rules: [
				]
				},
				// 信号强度
				signalStrength: {
					rules: [
					{validateFunction: proxy.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
				]
				},
				// 电池电量
				batteryLevel: {
					rules: [
					{validateFunction: proxy.$validateRules.validateNumberInRange, trigger: ["blur", "change"]},
				]
				},
				// 接收时间
				serverTime: {
					rules: [
					{ required: true, errorMessage: "接收时间不能为空", trigger: "blur" },
				]
				},
				// 原始报文
				rawData: {
					rules: [
				]
				},
				// 消息标识
				messageId: {
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
					a_iot_area: [],
					a_iot_device_type: [],
					a_iot_device: [],
					module_work_status: [],
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
	    let title = "新增设备数据"
	    if (options.id){
			title = "修改设备数据"
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
				this.pageData.dictData.a_iot_area = await getDictData('a_iot_area')
				this.pageData.dictData.a_iot_device_type = await getDictData('a_iot_device_type')
				this.pageData.dictData.a_iot_device = await getDictData('a_iot_device')
				this.pageData.dictData.module_work_status = await getDictData('module_work_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	iotDeviceData.selectDetailByPkIotDeviceData(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
     			if (this.pageData.addUpdateForm.id != null) {
					// 修改设备数据：只能用于前端form表单的更新操作，清空的字段回写为null
     			  iotDeviceData.updateNullValueByIotDeviceData(this.pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`修改成功`);
						this.$modal.alertCallback(`修改成功`, () => {
							uni.navigateBack()
							// this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			} else {
     			  iotDeviceData.addIotDeviceData(pageData.addUpdateForm).then(response => {
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
//@import '@/static/styles/autoee/iotDeviceData/iotDeviceDataAdd.scss';
</style>
