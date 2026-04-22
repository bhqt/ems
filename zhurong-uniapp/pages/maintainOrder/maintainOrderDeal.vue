<template>
	<view class="a_container">
        <uni-forms class="a_query_form" :model="pageData.addUpdateForm" ref="detailFormRef"
		           label-position="left" label-width="100px" label-align="right" border>
			<uni-forms-item class="a_query_form_item" label="工单编号：" name="orderNo">
				<text class="a_detail_text">{{pageData.addUpdateForm.orderNo}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="问题描述：" name="description">
				<text class="a_detail_text">{{pageData.addUpdateForm.description}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="故障类型：" name="orderFaultType">
				<text class="a_detail_text">{{pageData.addUpdateForm.orderFaultTypeExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="优先级：" name="orderPriority">
				<text class="a_detail_text">{{pageData.addUpdateForm.orderPriorityExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="工单状态：" name="repairOrderStatus">
				<text class="a_detail_text">{{pageData.addUpdateForm.repairOrderStatusExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="故障位置：" name="location">
				<text class="a_detail_text">{{pageData.addUpdateForm.location}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报修人：" name="reporterId">
				<text class="a_detail_text">{{pageData.addUpdateForm.reporterIdExtend}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报修人电话：" name="reporterContact">
				<text class="a_detail_text">{{pageData.addUpdateForm.reporterContact}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="报修时间：">
				<text class="a_detail_text">{{pageData.addUpdateForm.reportTime}}</text>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="维修人：" name="assigneeId">
				<text class="a_detail_text">{{pageData.addUpdateForm.assigneeIdExtend}}</text>
			</uni-forms-item>
		</uni-forms>
        <view class="indent-line"></view>
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef" :rules="pageData.addUpdateFormRules"
		           label-position="left" label-width="100px" label-align="right">

			<uni-forms-item v-show="true" class="a_add_form_item" label="维修结果" name="repairResult">
				<uni-easyinput type="textarea" v-model="pageData.addUpdateForm.repairResult" placeholder="请输入维修结果" clearable/>
			</uni-forms-item>
            <uni-forms-item v-show="true" class="a_add_form_item" label="维修图片" name="repairImages">
				<common-upload
					v-model="pageData.addUpdateForm.repairImages"
					ref="commonUpload"
					file-type="image"
					:limit="9"
				/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="完成时间" name="completionTime">
				<uni-datetime-picker v-model="pageData.addUpdateForm.completionTime" type="datetime" return-type="string" placeholder="请选择" clearable/>
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
// import CommonUpload from '@/components/common-upload/common-upload.vue';
import maintainOrder from '@/api/autoee/maintainOrder';

export default {
    components: {
		// CommonUpload
	},
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				iconReqUrl: config.iconReqUrl,
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					orderNo: "",
					description: "",
					orderFaultType: "",
					orderPriority: "",
					repairOrderStatus: "",
					location: "",
					reporterId: "",
					reporterContact: "",
					reportTime: "",
					assigneeId: "",
					repairResult: "",
					repairImages: "",
					completionTime: "",
					remark: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 工单编号
				orderNo: {
					rules: [
					{ required: true, errorMessage: "工单编号不能为空", trigger: "blur" },
				]
				},
				// 问题描述
				description: {
					rules: [
					{ required: true, errorMessage: "问题描述不能为空", trigger: "blur" },
				]
				},
				// 故障类型
				orderFaultType: {
					rules: [
					{ required: true, errorMessage: "故障类型不能为空", trigger: "change" },
				]
				},
				// 优先级
				orderPriority: {
					rules: [
					{ required: true, errorMessage: "优先级不能为空", trigger: "change" },
				]
				},
				// 工单状态
				repairOrderStatus: {
					rules: [
					{ required: true, errorMessage: "工单状态不能为空", trigger: "change" },
				]
				},
				// 故障位置
				location: {
					rules: [
				]
				},
				// 报修人
				reporterId: {
					rules: [
				]
				},
				// 报修人电话
				reporterContact: {
					rules: [
				]
				},
				// 报修时间
				reportTime: {
					rules: [
				]
				},
				// 维修人
				assigneeId: {
					rules: [
				]
				},
				// 维修结果
				repairResult: {
					rules: [
                        { required: true, errorMessage: "维修结果不能为空", trigger: "change" },
				]
				},
				// 维修图片
				repairImages: {
					rules: [
                        { required: true, errorMessage: "维修图片不能为空", trigger: "change" },
				]
				},
				// 完成时间
				completionTime: {
					rules: [
                          { required: true, errorMessage: "完成时间不能为空", trigger: "change" },
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
					order_fault_type: [],
					order_priority: [],
					repair_order_status: [],
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
	    let title = "新增维修工单"
	    if (options.id){
			title = "处理维修工单"
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
				this.pageData.dictData.order_fault_type = await getDictData('order_fault_type')
				this.pageData.dictData.order_priority = await getDictData('order_priority')
				this.pageData.dictData.repair_order_status = await getDictData('repair_order_status')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	maintainOrder.selectDetailByPkMaintainOrder(id).then(res => {
            	this.pageData.addUpdateForm= res.data;

                    console.log(" this.$dateTime.getCurrentDateTime()=", JSON.stringify( this.$dateTime.getCurrentDateTime()))
            	if (!this.pageData.addUpdateForm.completionTime){
                    this.pageData.addUpdateForm.completionTime =this.$dateTime.getCurrentDateTime()
                }
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
                this.$modal.confirm('确认提交当前工单？').then(() => {
                    if (this.pageData.addUpdateForm.id != null) {

                        this.pageData.addUpdateForm.repairOrderStatus="completed"
                        // 修改维修工单：只能用于前端form表单的更新操作，清空的字段回写为null
                        maintainOrder.updateNullValueByMaintainOrder(this.pageData.addUpdateForm).then(response => {
                            // this.$api.msg(`修改成功`);
                            this.$modal.alertCallback(`提交成功`, () => {
                                // uni.navigateBack()
                                this.$tab.reLaunch('/pages/index/index');
                            });
                        });
                    } else {
                        maintainOrder.addMaintainOrder(pageData.addUpdateForm).then(response => {
                            // this.$api.msg(`新增成功`);
                            this.$modal.alertCallback(`新增成功`, () => {
                                uni.navigateBack()
                                // this.$tab.reLaunch('/pages/index/index');
                            });
                        });
                    }
                })
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
//@import '@/static/styles/autoee/maintainOrder/maintainOrderAdd.scss';
</style>
