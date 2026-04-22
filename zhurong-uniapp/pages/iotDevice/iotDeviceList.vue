<template>
	<view class="a_container">
		<uni-forms class="a_query_form" :model="pageData.queryParams" ref="queryFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item class="a_query_form_item" label="所属区域" name="areaCodeDict">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.areaCodeDict" :localdata="pageData.dictData.a_iot_area" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备类型" name="deviceTypeDict">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.deviceTypeDict" :localdata="pageData.dictData.a_iot_device_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备编号" name="deviceCode">
				<uni-easyinput v-model="pageData.queryParams.deviceCode" placeholder="请输入设备编号" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="设备名称" name="deviceName">
				<uni-easyinput v-model="pageData.queryParams.deviceName" placeholder="请输入设备名称" clearable/>
			</uni-forms-item>
			<uni-forms-item class="a_query_form_item" label="负责人" name="userId">
				<uni-data-picker class="a_query_form_select" v-model="pageData.queryParams.userId" :localdata="pageData.dictData.sys_user" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="handleQuery">查询</button>
		</view>

	  <view class="list">
			<!-- 基于 uni-list 的页面布局 -->
			<uni-list>
				<!-- 所有字段  deptId,   areaCodeDict,   deviceTypeDict,   deviceCode,   deviceName,   softVerion,   deviceOnlineState,   deviceWorkStatus,   moduleWorkStatus,   moduleFaultCode,   updateTime,   -->
				<!-- direction 属性决定列表的排版方向 row,column -->
				<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
			  	<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
			  	<uni-list-item v-for="item in pageData.iotDeviceList" :key="item.id"
								   :title="item.deviceName.toString()"
								   :rightText="item.userIdExtend.toString()"
								   :note="formatNote(item)"
								   thumb="/static/biaoshi.svg" thumb-size="medium"
								   showArrow link="navigateTo" :to="'./iotDeviceDetail?id='+item.id">
				</uni-list-item>
			</uni-list>
			<!-- 通过 loadMore 组件实现上拉加载效果，如需自定义显示内容，可参考：https://ext.dcloud.net.cn/plugin?id=29 -->
			<!--  <uni-load-more :status="loadMoreStatus" /> -->
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
			  	iotDeviceList: [],
				queryParams: {
					deptId: "",
					areaCodeDict: "",
					deviceTypeDict: "",
					deviceCode: "",
					deviceName: "",
					deviceOnlineState: "",
					deviceWorkStatus: "",
					moduleWorkStatus: "",
				  	params: []
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
	async onLoad(options) {
	  	console.log('页面参数:', options)
    	this.initData()
	  	this.loadData();
	},
	onShow() {
		console.log('页面显示')
		// this.loadData();
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
		loadData(){
			let data = this.pageData.queryParams
			data.limitTopN = 200
        	iotDevice.selectDetailListByLikeIotDevice(data).then(res => {
           	 	// console.log("getAddressByCurrentUser-res=", res)
            	let list = res.rows;
            	this.pageData.iotDeviceList = list;
        	})
	    },
		//查询
		handleQuery()
		{
			 this.loadData()
		},
		
		//格式化note信息
		formatNote(item) {
			try {
				return `${item.areaCodeDictExtend || ''}\n${item.deviceTypeDictExtend || ''}`
			} catch (e) {
				return ''
			}
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/iotDevice/iotDeviceList.scss';
</style>
