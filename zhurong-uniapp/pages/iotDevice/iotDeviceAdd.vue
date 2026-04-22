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
			<uni-forms-item v-show="true" class="a_add_form_item" label="设备编号" name="deviceCode">
				<uni-easyinput v-model="pageData.addUpdateForm.deviceCode" placeholder="请输入设备编号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="设备名称" name="deviceName">
				<uni-easyinput v-model="pageData.addUpdateForm.deviceName" placeholder="请输入设备名称" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="软件版本号" name="softVerion">
				<uni-easyinput v-model="pageData.addUpdateForm.softVerion" placeholder="请输入软件版本号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="充电模块编号" name="chargingModuleNo">
				<uni-easyinput v-model="pageData.addUpdateForm.chargingModuleNo" placeholder="请输入充电模块编号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="tbox编号" name="tboxNo">
				<uni-easyinput v-model="pageData.addUpdateForm.tboxNo" placeholder="请输入tbox编号" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="上线状态" name="deviceOnlineState">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deviceOnlineState" :localdata="pageData.dictData.device_online_state" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="工作状态" name="deviceWorkStatus">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.deviceWorkStatus" :localdata="pageData.dictData.device_work_status" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="模块状态" name="moduleWorkStatus">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.moduleWorkStatus" :localdata="pageData.dictData.module_work_status" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="false" class="a_add_form_item" label="模块故障码" name="moduleFaultCode">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.moduleFaultCode" :localdata="pageData.dictData.module_fault_code" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="备注" name="remark">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.remark" placeholder="请输入备注" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="负责人" name="userId">
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
import iotDevice from '@/api/autoee/iotDevice';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					deptId: "",
					areaCodeDict: "",
					deviceTypeDict: "",
					deviceCode: "",
					deviceName: "",
					softVerion: "",
					chargingModuleNo: "",
					tboxNo: "",
					deviceOnlineState: "",
					deviceImages: "",
					remark: "",
					userId: "",
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
				// 设备编号
				deviceCode: {
					rules: [
					{ required: true, errorMessage: "设备编号不能为空", trigger: "blur" },
				]
				},
				// 设备名称
				deviceName: {
					rules: [
					{ required: true, errorMessage: "设备名称不能为空", trigger: "blur" },
				]
				},
				// 软件版本号
				softVerion: {
					rules: [
				]
				},
				// 充电模块编号
				chargingModuleNo: {
					rules: [
				]
				},
				// tbox编号
				tboxNo: {
					rules: [
				]
				},
				// 上线状态
				deviceOnlineState: {
					rules: [
					{ required: true, errorMessage: "上线状态不能为空", trigger: "change" },
				]
				},
				// 工作状态
				deviceWorkStatus: {
					rules: [
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
				// 设备图片
				deviceImages: {
					rules: [
				]
				},
				// 备注
				remark: {
					rules: [
				]
				},
				// 负责人
				userId: {
					rules: [
					{ required: true, errorMessage: "负责人不能为空并且为整数", trigger: "change" },
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
					device_online_state: [],
					device_work_status: [],
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
	    let title = "新增设备管理"
	    if (options.id){
			title = "修改设备管理"
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
				this.pageData.dictData.device_online_state = await getDictData('device_online_state')
				this.pageData.dictData.device_work_status = await getDictData('device_work_status')
				this.pageData.dictData.module_work_status = await getDictData('module_work_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	iotDevice.selectDetailByPkIotDevice(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
     			if (this.pageData.addUpdateForm.id != null) {
					// 修改设备管理：只能用于前端form表单的更新操作，清空的字段回写为null
     			  iotDevice.updateNullValueByIotDevice(this.pageData.addUpdateForm).then(response => {
     			    	// this.$api.msg(`修改成功`);
						this.$modal.alertCallback(`修改成功`, () => {
							uni.navigateBack()
							// this.$tab.reLaunch('/pages/index/index');
						});
     			  });
     			} else {
     			  iotDevice.addIotDevice(pageData.addUpdateForm).then(response => {
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
//@import '@/static/styles/autoee/iotDevice/iotDeviceAdd.scss';
</style>
