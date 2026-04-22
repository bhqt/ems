<template>
	<view>
		<hx-navbar ref="hxnb" :config="config">
			<block slot="left">
				<view class="a1">
					<text class="a2">发现</text>
				</view>
			</block>
		</hx-navbar>
		<view class="a3">
			<!-- <image src="../../static/index/faxian.png" class="a4"></image> -->
		</view>
		<l-tabs :list="tabarr" keyName="text" :lineShow="true" :lineCrude="6" :activeSize="18" linePlace="26px"
			activeTextColor="#333" bold=600 textColor="#333" lineColor="linear-gradient(to right, #98F0BC 30%, #98F0BC)"
			@choose="tabChange">
		</l-tabs>
		<swiper :current="tab" class="c6" style="height: 1500upx">
			<swiper-item v-for="(dictItem,dictindex) in tabarr" :key="dictindex">
				<!-- 报警信息  -->
				<uni-list v-if="'210' === dictItem.value">
					<!-- 所有字段  deviceTypeCode,   deviceTypeName,   remark,   updateTime,   -->
					<!-- direction 属性决定列表的排版方向 row,column -->
					<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
					<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
					<uni-list-item v-for="item in iotAlarmDataList" :key="item.id" :title="item.alarmName"
						:note="formatNote('alarm', item)" :rightText="item.createTime" thumb="/static/alarm.svg"
						thumb-size="lg" showArrow link="navigateTo"
						:to="'/pages/iotAlarmData/iotAlarmDataDetail?id='+item.id">
					</uni-list-item>
				</uni-list>
				<!-- 设备信息 -->
				<uni-list v-if="'220' === dictItem.value">
					<!-- 所有字段  deviceTypeCode,   deviceTypeName,   remark,   updateTime,   -->
					<!-- direction 属性决定列表的排版方向 row,column -->
					<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
					<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
					<uni-list-item v-for="item in iotDeviceList" :key="item.id" :title="item.deviceName"
						:note="formatNote('device', item)" :rightText="item.createTime" thumb="/static/iot-device.svg"
						thumb-size="lg" showArrow link="navigateTo"
						:to="'/pages/iotDevice/iotDeviceDetail?id='+item.id">
					</uni-list-item>
				</uni-list>
				<!-- 区域信息 -->
				<uni-list v-if="'230' === dictItem.value">
					<!-- 所有字段  deviceTypeCode,   deviceTypeName,   remark,   updateTime,   -->
					<!-- direction 属性决定列表的排版方向 row,column -->
					<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
					<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
					<uni-list-item v-for="item in iotAreaList" :key="item.id" :title="item.areaName"
						:note="formatNote('area', item)" :rightText="item.createTime" thumb="/static/iot-area.svg"
						thumb-size="lg" showArrow link="navigateTo" :to="'/pages/iotArea/iotAreaDetail?id='+item.id">
					</uni-list-item>
				</uni-list>

				<!-- 通知公告 -->
				<view class="c7" v-for="(item,index) in noticeArr" v-if="'240' === dictItem.value" :key="index"
					@click="navToNewsDetailPage(item)">
					<view class="c8">
						<view class="c9">{{ item.newsTitle }}</view>
						<view class="d1">{{ item.author }}</view>
					</view>
					<!-- <image :src="imageBaseUrl + item.newsImage" class="d2"></image> -->
				</view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	import config from '@/config'
	import {
		mapState
	} from 'vuex';
	import {
		getDictData
	} from '@/utils/dict'
	import newsManage from '@/api/autoee/newsManage';
	import iotAlarmData from '@/api/autoee/iotAlarmData';
	import iotDevice from '@/api/autoee/iotDevice';
	import iotArea from '@/api/autoee/iotArea';

	export default {
		data() {
			return {
				imageBaseUrl: config.baseUrl,
				config: {
					back: false,
					fixed: true,
					leftSlot: true,
					search: {
						placeholder: ''
					}
				},
				tabarr: [],
				tab: 0,
				iotAlarmDataList: [],
				iotDeviceList: [],
				iotAreaList: [],
				noticeArr: []
			}
		},
		onLoad() {
			this.initData()
			this.loadData()
		},
		methods: {
			async initData() {
				try {
					this.tabarr = await getDictData('news_module')
					console.log("this.tabarr=" + JSON.stringify(this.tabarr))
				} catch (error) {
					console.error('获取字典失败:', error)
				}
			},
			//请求数据
			loadData() {
				let data = {
					"newsStatus": "220"
				}
				newsManage.selectDetailListByLikeNewsManage(data).then(res => {
					// console.log("getAddressByCurrentUser-res=", res)
					let list = res.rows;
					this.noticeArr = list;
				})

				this.getAlarmData()

				data = {
					"newsStatus": "220"
				}
				newsManage.selectDetailListByLikeNewsManage(data).then(res => {
					// console.log("getAddressByCurrentUser-res=", res)
					let list = res.rows;
					this.noticeArr = list;
				})
			},
			//详情页
			navToNewsDetailPage(item) {
				let id = item.id;
				uni.navigateTo({
					url: `/pages/newsManage/newsDetail?id=${id}`
				})
			},
			//格式化note信息
			formatNote(type, item) {
				try {
					if (type === 'alarm') {
						return `${item.areaCodeDictExtend || ''} / ${item.deviceCodeDictExtend || ''}`;
					} else if (type === 'device') {
						return `${item.deviceTypeDictExtend || ''} / ${item.areaCodeDictExtend || ''}`;
					} else if (type === 'area') {
						return `${item.principal || ''} / ${item.mobile || ''}`;
					}
					return '';
				} catch (e) {
					return '';
				}
			},
			tabChange({
				index,
				item
			}) {
				this.tab = index;
				console.log("item=", JSON.stringify(item))

				if (item.value === '210') {
					this.getAlarmData();
				} else if (item.value === '220') {
					let data = {}
					// data.studentNo = this.$store.getters.name
					data.limitTopN = 50
					iotDevice.selectDetailListByLikeIotDevice(data).then(res => {
						let list = res.rows;
						// console.log("list=", JSON.stringify(list))
						this.iotDeviceList = list;
					})
				} else if (item.value === '230') {
					let data = {}
					data.limitTopN = 50
					iotArea.selectDetailListByLikeIotArea(data).then(res => {
						// console.log("getAddressByCurrentUser-res=", res)
						let list = res.rows;
						this.iotAreaList = list;
					})
				}
			},
			getAlarmData() {
				let data = {};
				// data.studentNo = this.$store.getters.name
				data.limitTopN = 50;
				iotAlarmData.selectDetailListByLikeIotAlarmData(data).then(res => {
					let list = res.rows;
					// console.log("list=", JSON.stringify(list))
					this.iotAlarmDataList = list;
				});
			}
		}
	}
</script>

<style>
	@import '@/static/styles/autoee/index/indexPage.scss';

	page {
		background-color: #FFFFFF;
	}

	.a1 {
		padding-left: 35 upx;
	}

	.a2 {
		font-size: 18px;
		font-weight: 700;
	}

	.c6 {
		height: 1500 upx;
	}

	.c7 {
		display: flex;
		flex-direction: row;
		align-items: center;
		padding-left: 35 upx;
		padding-right: 35 upx;
		margin-top: 50 upx;
	}

	.c8 {
		height: 110 upx;
		display: flex;
		flex-direction: column;
		width: 76%;
		padding-right: 30 upx;
	}

	.c9 {
		font-size: 14px;
		font-weight: 500;
		height: 90 upx;
	}

	.d1 {
		font-size: 20 upx;
		color: #999;
		height: 20 upx;
	}

	.d2 {
		height: 110 upx;
		width: 110 upx;
	}

	.a3 {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 30 upx;
		margin-bottom: 50 upx;
	}

	.a4 {
		height: 300 upx;
		width: 92%;
		border-radius: 30 upx;
	}
</style>