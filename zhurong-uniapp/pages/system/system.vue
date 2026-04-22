<template>
	<view>
		<hx-navbar ref="hxnb" :config="config">
			<block slot="left">
				<view class="a1">
					<text class="a2">应用</text>
				</view>
			</block>
		</hx-navbar>
		<!--		<view class="a8">-->
		<!--			<image src="../../static/index/yingyong.svg" class="a9">-->
		<!--			</image>-->
		<!--			<image src="../../static/index/yingyong.svg" class="a9">-->
		<!--			</image>-->
		<!--		</view>-->
		<view class="b5">
			<text class="b6">推荐应用</text>
			<text class="b7">根据您最近浏览和办理的应用为您推荐</text>
		</view>
<!--		<view class="b8">-->
<!--			<view class="b9" v-for="(item,index) in recomarr" :key="index">-->
<!--				<image :src="item.icon" class="c1"></image>-->
<!--				<text class="c2">{{ item.title }}</text>-->
<!--				<text class="c3">{{ item.value }}</text>-->
<!--				<text class="c4">{{ item.view }}浏览</text>-->
<!--				<image src="../../static/add.png" class="c5"></image>-->
<!--			</view>-->
<!--		</view>-->
		<l-tabs :list="tabarr" keyName="title" :lineShow="true" :lineCrude="6" :activeSize="18" linePlace="26px"
		        activeTextColor="#333" bold=600 textColor="#333" lineColor="linear-gradient(to right, #98F0BC 30%, #98F0BC)"
		        @choose="tabChange">
		</l-tabs>
		<swiper :current="tab" class="c6">
			<swiper-item v-for="(dictItem, dictindex) in tabarr" :key="dictindex">
				<!-- 计算行数：Math.ceil(navarr[dictindex].length / 4) -->
				<view
					v-for="rowIndex in Math.ceil(navarr[dictindex].length / 4)"
					:key="rowIndex"
					class="a5"
				>
					<!-- 计算每行的4个项目 -->
					<view
						class="a6"
						v-for="item in navarr[dictindex].slice((rowIndex-1)*4, rowIndex*4)"
						:key="item.page"
						@click="navClick(item.page)"
					>
						<image :src="item.icon" class="a7"></image>
						<text>{{ item.title }}</text>
					</view>
				</view>
			</swiper-item>
		</swiper>
		<!--		<view class="d1">-->
		<!--			<view class="d2" id="a1">最新上线</view>-->
		<!--			<view class="d3">-->
		<!--				<view class="d4" v-for="(item,index) in navarr" :key="index"  @click="navClick(item.page)">-->
		<!--					<image :src="item.icon" class="d5"></image>-->
		<!--					<view class="d6">{{item.title}}</view>-->
		<!--				</view>-->
		<!--			</view>-->
		<!--			<view class="d7" id="a2">电子教务</view>-->
		<!--			<view class="d3">-->
		<!--				<view class="d4" v-for="(item,index) in arr2" :key="index">-->
		<!--					<image :src="item.icon" class="d5"></image>-->
		<!--					<view class="d6">{{item.title}}</view>-->
		<!--				</view>-->
		<!--			</view>-->
		<!--			<view class="d7" id="a3">教学服务</view>-->
		<!--			<view class="d3">-->
		<!--				<view class="d4" v-for="(item,index) in arr3" :key="index">-->
		<!--					<image :src="item.icon" class="d5"></image>-->
		<!--					<view class="d6">{{item.title}}</view>-->
		<!--				</view>-->
		<!--			</view>-->
		<!--			<view class="d7" id="a4">科研服务</view>-->
		<!--			<view class="d3">-->
		<!--				<view class="d4" v-for="(item,index) in arr4" :key="index">-->
		<!--					<image :src="item.icon" class="d5"></image>-->
		<!--					<view class="d6">{{item.title}}</view>-->
		<!--				</view>-->
		<!--			</view>-->
		<!--		</view>-->
	</view>
</template>

<script>
export default {
	data() {
		return {
			config: {
				back: false,
				fixed: true,
				leftSlot: true,
				search: {
					placeholder: ''
				}
			},
			recomarr: [{
				title: '成绩查询',
				icon: '../../static/index/score.svg',
				value: '查询当前及历史成绩',
				view: '200'
			},
				{
					title: '课程查询',
					icon: '../../static/index/kecheng.svg',
					value: '查询课程信息',
					view: '200'
				},
				{
					title: '图书借阅',
					icon: '../../static/index/yingyong.svg',
					value: '查看图书借阅记录',
					view: '200'
				},
				// {
				// 	title: '网上缴费',
				// 	icon: '../../static/index/yingyong.svg',
				// 	value: '自助网上缴费',
				// 	view: '200'
				// }
			],
			tabarr: [{
				title: '最新上线'
			},
				{
					title: '电子教务'
				},
				{
					title: '教学服务'
				},
				{
					title: '科研服务'
				}
			],
			tab: 0,
			navarr: [
				[{
					title: '区域管理',
					icon: '../../static/index/schoolInfo.svg',
					page: '/pages/iotArea/iotAreaList'
				},
					{
						title: '设备管理',
						icon: '../../static/index/score.svg',
						page: '/pages/iotDevice/iotDeviceList'
					},
					{
						title: '设备数据',
						icon: '../../static/index/kecheng.svg',
						page: '/pages/iotDeviceData/iotDeviceDataList'
					},
					{
						title: '报警信息',
						icon: '../../static/index/notice.svg',
						page: '/pages/iotAlarmData/iotAlarmDataList'
					},
					{
						title: '通知消息',
						icon: '../../static/index/notice.svg',
						page: '/pages/find/find'
					},
					// {
					// 	title: '校园地图',
					// 	icon: '../../static/index/ditu.svg'
					// },
					// {
					// 	title: '我的消费',
					// 	icon: '../../static/index/xiaofei.svg'
					// },
					{
						title: '电子邮件',
						icon: '../../static/index/yingyong.svg'
					},
					// {
					// 	title: '图书借阅',
					// 	icon: '../../static/index/book.svg'
					// },
					{
						title: '获奖成果',
						icon: '../../static/index/huojiang.svg'
					},
					{
					title: '我的党建',
					icon: '../../static/index/yingyong.svg'
				},
					{
					title: '网上审批',
					icon: '../../static/index/shengpi.svg'
				}],
				[{
					title: '网上审批',
					icon: '../../static/index/shengpi.svg'
				},
					{
						title: '网上缴费',
						icon: '../../static/index/xiaofei.svg'
					},
					{
						title: '设备预警',
						icon: '../../static/index/yingyong.svg'
					}, {
					title: '办事指南',
					icon: '../../static/index/yingyong.svg'
				}],
				[
				// 	{
				// 	title: '图书借阅',
				// 	icon: '../../static/index/yingyong.svg'
				// },
				// 	{
				// 		title: '课堂考勤',
				// 		icon: '../../static/index/yingyong.svg'
				// 	},
					{
						title: '练习中心',
						icon: '../../static/index/yingyong.svg'
					},
					{
						title: '综合实践',
						icon: '../../static/index/yingyong.svg'
					}
				],
				[{
					title: '获奖成果',
					icon: '../../static/index/yingyong.svg'
				},
					{
						title: '研究课题',
						icon: '../../static/index/yingyong.svg'
					}]

			],

		}
	},
	methods: {
		tabChange({
			index,
			item
		}) {
			this.tab = index;
			// this.getElementScollTop("a"+(index+1))
		},
		//获取元素离页面顶部的距离
		getElementScollTop(element) {
			const query = uni.createSelectorQuery()
			console.log(element)
			query.select('#' + element).boundingClientRect((data) => {
				let pageScrollTop = Math.round(data.top)
				uni.pageScrollTo({
					scrollTop: pageScrollTop - 400, //滚动的距离
					duration: 0, //过渡时间
				})
			}).exec()
		},
		navClick(page) {
			if (page === "/pages/find/find") {
				uni.switchTab({
					url: page
				})
			} else {
				if (page) {
					uni.navigateTo({
						url: page
					})
				} else {
					this.$api.msg('暂未实现...');
				}
			}
		},
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

.a8 {
	display: flex;
	flex-direction: row;
	justify-content: space-around;
	margin-top: 40 upx;
	padding-left: 20 upx;
	padding-right: 20 upx;
}

.a9 {
	height: 160 upx;
	width: 320 upx;
	border-radius: 30 upx;
}

.b5 {
	display: flex;
	flex-direction: column;
	padding-left: 30 upx;
	margin-top: 30 upx;
}

.b6 {
	font-size: 18px;
	font-weight: 600;
}

.b7 {
	font-size: 20 upx;
	color: darkgray;
	margin-top: 10 upx;
	letter-spacing: 1 upx;
}

.b8 {
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-between;
	padding-top: 70 upx;
	padding-left: 30 upx;
	padding-right: 30 upx;
	margin-bottom: 30 upx;
}

.b9 {
	display: flex;
	flex-direction: column;
	border-radius: 30 upx;
	padding-top: 30 upx;
	align-items: center;
	height: 300 upx;
	width: 220 upx;
	box-shadow: 0px -5px 10px 0px #F3F2F8,
	-6px 0px 6px 0px #F3F2F8,
	6px 0px 6px 0px #F3F2F8,
	0px 5px 19px 0px #F3F2F8;
}

.c1 {
	width: 70 upx;
	height: 70 upx;
}

.c2 {
	font-size: 26 upx;
	margin-top: 10 upx;
}

.c3 {
	font-size: 18 upx;
	color: darkgray;
}

.c4 {
	font-size: 20 upx;
	color: #666666;
	margin-top: 20 upx;
}

.c5 {
	height: 50 upx;
	width: 50 upx;
	margin-top: 30 upx;
}

.d1 {

	margin-top: 30 upx;

}

.d2 {
	padding-right: 40 upx;
	padding-left: 40 upx;
	font-weight: 600;
	font-size: 26 upx;
	color: #333;
	margin-top: 30 upx;
}

.d3 {
	display: flex;
	flex-direction: row;
	flex-wrap: wrap;
}


.d4 {
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 25%;
	margin-top: 30 upx;
}

.d5 {
	height: 60 upx;
	width: 60 upx;
}

.d6 {
	font-size: 22 upx;
	margin-top: 15 upx;
}

.d7 {

	padding-right: 40 upx;
	padding-left: 40 upx;
	font-weight: 600;
	font-size: 26 upx;
	margin-top: 50 upx;
	color: #333;

}
</style>
