<template>
	<view>
		<!-- 全局加载蒙层 -->
		<view v-if="isPageLoading" class="global-loading-mask">
			<u-loading size="30" color="#1890ff"></u-loading>
			<text class="global-loading-text">加载中...</text>
		</view>

		<hx-navbar ref="hxnb" :config="config">
			<block slot="left">
				<view class="a1">
					<text class="a2">首页</text>
				</view>
			</block>
		</hx-navbar>
		<!-- 首页轮播图 -->
		<!--		<view class="a_swiper">-->
		<!--			<swiper circular autoplay interval="3000" duration="500" indicator-dots="true">-->
		<!--				<swiper-item class="a_swiper_item" v-for="(item, index) in indexPageSwiperList" :key="index"-->
		<!--					@click="navToSwiperDetailPage(item)">-->
		<!--					<view class="a_image_wrapper">-->
		<!--						<image :src="imageBaseUrl+item.mainImage" class="loaded" mode="aspectFill" @error="(e) => handleImageError(e, '轮播图')"></image>-->
		<!--					</view>-->
		<!--				</swiper-item>-->
		<!--			</swiper>-->
		<!--		</view>-->
		<view style="height: 30rpx"></view>
		<view class="patrol-plan-section">
			<view class="plan-section-header">
				<view class="plan-section-header-left">
					<!--					<i class="fa-solid fa-calendar"></i>-->
					<image :src="iconReqUrl + 'tasks.svg'" class="icon-common"></image>
					<text class="header-title">今日巡更任务</text>
				</view>
				<view class="header-more" @click="navToAllPatrolTasks">查看全部</view>
			</view>

			<!-- 有计划时显示 -->
			<uni-list v-if="todayPatrolTask" class="plan-list">
				<uni-list-item direction="column" class="plan-card" link="navigateTo"
					:to="'/pages/patrolTask/patrolCheckin?id='+todayPatrolTask.id">
					<!-- 计划标题和状态 -->
					<template v-slot:header>
						<view class="plan-header">
							<text class="plan-name">{{ todayPatrolTask.patrolTaskName || '未命名计划' }}</text>
						</view>
					</template>

					<!-- 任务详情内容 -->
					<template v-slot:body>
						<view class="plan-details">
							<!-- 任务状态 -->
							<view class="plan-info-item">
								<image :src="iconReqUrl + 'tasks.svg'" class="icon-common"></image>
								<text class="info-text">任务状态：{{ todayPatrolTask.patrolTaskStatusExtend }}</text>
							</view>
							<!-- 时间信息 -->
							<view class="plan-info-item">
								<image :src="iconReqUrl + 'clock.svg'" class="icon-common"></image>
								<text class="info-text">开始时间：{{ todayPatrolTask.startTime }}</text>
							</view>

							<!-- 路线信息 -->
							<view class="plan-info-item">
								<image :src="iconReqUrl + 'map-location.svg'" class="icon-common"></image>
								<text class="info-text">巡更路线：{{ todayPatrolTask.patrolPathIdExtend || '未关联路线' }}</text>
							</view>

						</view>
					</template>

					<!-- 右侧打卡按钮 -->
					<template v-slot:footer>
						<view class="plan-actions">
							<button class="btn-checkin" @click.stop="gotoPatrolExecute(todayPatrolTask)">
								<!--                                <image :src="iconReqUrl + 'alarm-clock.svg'" class="icon-common"></image>-->
								<text class="btn-text">去打卡</text>
							</button>
						</view>
					</template>
				</uni-list-item>
			</uni-list>
			<!-- 无计划时显示 -->
			<view class="plan-empty" v-else>
				<image :src="iconReqUrl + 'calendar-xmark.svg'" style="width: 60rpx;height: 60rpx;"></image>
				<text class="empty-title">今日暂无巡更任务</text>
			</view>
		</view>

		<!-- 4个导航图标菜单区域 -->
		<view class="nav-menu">
			<view class="nav-item" v-for="(item,index) in navarr" :key="index" @click="navClick(item.page)"
				hover-class="nav-item-active">
				<image :src="iconReqUrl + item.icon" style="width: 60rpx;height: 60rpx;"></image>
				<text>{{ item.title }}</text>
			</view>
		</view>

		<!--		<view class="b1">-->
		<!--			<view class="b2">-->
		<!--				<i class="fa-solid fa-bullhorn b3"></i>-->
		<!--				<view>关于系统上线运行的通知</view>-->
		<!--			</view>-->
		<!--			<view class="b4">2-23</view>-->
		<!--		</view>-->
		<!-- tab标签过多 可滚动排列 -->
		<l-tabs :list="tabarr" keyName="text" :lineShow="true" :lineCrude="6" :activeSize="18" linePlace="26px"
			activeTextColor="#333" bold=600 textColor="#333" lineColor="linear-gradient(to right, #98F0BC 30%, #98F0BC)"
			@choose="tabChange">
		</l-tabs>
		<!-- 切换tab显示轮播图的效果 -->
		<swiper :current="currentTab" class="c6">
			<swiper-item v-for="(dictItem,dictindex) in tabarr" :key="dictindex">

				<!-- 报警信息  -->
				<uni-list v-if="'210' === dictItem.value">
					<!-- 所有字段  deviceTypeCode,   deviceTypeName,   remark,   updateTime,   -->
					<!-- direction 属性决定列表的排版方向 row,column -->
					<!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
					<!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
					<uni-list-item v-for="item in patrolAlarmList" :key="item.id" :title="item.alarmNo"
                                   rightText="去处理"
						:note="formatNote('alarm', item)"   showArrow link="navigateTo"
						:to="'/pages/patrolAlarm/patrolAlarmDeal?id='+item.id">
						<template v-slot:header>
							<view style="display: flex; align-items: center;">
								<image :src="iconReqUrl + 'bell.svg'" class="icon-big"></image>
<!--								<text>{{ item.patrolPlanIdExtend }}</text>-->
							</view>
						</template>
					</uni-list-item>
				</uni-list>

                <!-- 工单信息 -->
                <uni-list v-if="'220' === dictItem.value">
                    <!-- 所有字段  deviceTypeCode,   deviceTypeName,   remark,   updateTime,   -->
                    <!-- direction 属性决定列表的排版方向 row,column -->
                    <!-- to 属性携带参数跳转详情页面，当前只为参考 :to="'/pages/news/newsDetail?id='+item.id" -->
                    <!-- thumbSize 可选值，lg:大图; medium:一般; sm:小图; -->
                    <uni-list-item v-for="item in maintainOrderList" :key="item.id" :title="item.orderFaultTypeExtend"
                        :note="formatNote('device', item)" rightText="去处理" showArrow link="navigateTo"
                        :to="'/pages/maintainOrder/maintainOrderDeal?id='+item.id">
                        <template v-slot:header>
                            <view style="display: flex; align-items: center;">
                                <image :src="iconReqUrl + 'tools.svg'" class="icon-big"></image>
<!--                                <text>{{ item.deviceName }}</text>-->
                            </view>
                        </template>
                    </uni-list-item>
                </uni-list>
                <!-- 通知公告 -->
                <!--                <view class="c7" v-for="(item,index) in noticeArr" v-if="'240' === dictItem.value" :key="index"-->
                <!--                      @click="navToNewsDetailPage(item)">-->
                <!--                    <view class="c8">-->
                <!--                        <view class="c9">{{ item.newsTitle }}</view>-->
                <!--                        <view class="d1">{{ item.author }}</view>-->
                <!--                    </view>-->
                <!--                    <image :src="imageBaseUrl + item.newsImage" class="d2"></image>-->
                <!--                </view>-->
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
	import indexPageSwiper from '@/api/autoee/indexPageSwiper';
	import patrolTaskExtend from '@/api/autoee/patrolTaskExtend.js';
	import patrolAlarm from '@/api/autoee/patrolAlarm.js';
	import iotDevice from '@/api/autoee/iotDevice';
	import iotArea from '@/api/autoee/iotArea';
	import maintainOrder from '@/api/autoee/maintainOrder';

	export default {
		data() {
			return {
                // 基础配置
				imageBaseUrl: config.baseUrl,
				iconReqUrl: config.iconReqUrl,
				userId: this.$store.getters.userId,
				// 当前登录人当天的巡更计划
				todayPatrolTask: null,
				// 接口请求状态
				isLoadingPlan: true,
				// 页面加载状态
				isPageLoading: true,

				config: {
					back: false,
					fixed: true,
					leftSlot: true,
					search: {
						placeholder: ''
					}
				},
				// 导航菜单数据（已优化，直接存储图标类名）
				navarr: [
                    {
						title: '巡更计划',
						icon: 'business-time.svg',
						page: `/pages/patrolPlan/patrolPlanList?patrolUserId=`+ this.$store.getters.userId
					},
                    // {
					// 	title: '巡更任务',
					// 	icon: 'map-location.svg',
					// 	page: `/pages/patrolTask/patrolTaskList?patrolUserId=`+ this.$store.getters.userId
					// },
					{
						title: '巡更记录',
						icon: 'list-1-2.svg',
						page: `/pages/patrolRecord/patrolRecordList?patrolUserId=`+ this.$store.getters.userId
					},
					{
						title: '巡更报警',
						icon: 'bell.svg',
						page: `/pages/patrolAlarm/patrolAlarmList?patrolUserId=`+ this.$store.getters.userId
					},
					{
						title: '工单信息',
						icon: 'tools.svg',
						page: `/pages/maintainOrder/maintainOrderList?assigneeId=`+ this.$store.getters.userId
					}
				],
				// 轮播图数据
				indexPageSwiperList: [],
				// Tab标签数据
				tabarr: [{
						"text": "报警信息",
						"value": "210"
					},
					{
						"text": "我的工单",
						"value": "220"
					},
				],
				// 当前激活的Tab
				currentTab: 0,
				// 列表数据
				patrolAlarmList: [],
				maintainOrderList: [],
				iotAreaList: [],
				// 通知公告数据（当前未使用）
				noticeArr: []
			}
		},
		onLoad() {
			// 页面加载时显示全局加载状态
			this.isPageLoading = true;
			// 初始化所有数据
			this.initializePage();
		},
		/**
		 * 页面初次渲染时执行
		 */
		onReady() {
			// 可以在这里添加页面渲染完成后的初始化代码
		},
		/**
		 * 下拉刷新处理函数
		 */
		onPullDownRefresh() {
			// 显示全局加载状态
			this.isPageLoading = true;
			// 重新初始化页面数据
			this.initializePage().then(() => {
				// 数据加载完成后，停止下拉刷新动画
				uni.stopPullDownRefresh();
			});
		},
		methods: {
			//格式化note信息
			formatNote(type, item) {
				try {
					if (type === 'alarm') {
						return `${item.patrolPlanIdExtend || ''} \n ${item.patrolAlarmTypeExtend || ''} `;
						// return `${item.areaCodeDictExtend || ''} / ${item.deviceCodeDictExtend || ''}`;
					} else if (type === 'device') {
						return `${item.location || ''} \n ${item.repairOrderStatusExtend||''} `;
						// return `${item.deviceTypeDictExtend || ''} / ${item.areaCodeDictExtend || ''}`;
					} else if (type === 'area') {
						return `${item.principal || ''} / ${item.mobile || ''}`;
					}
					return '';
				} catch (e) {
					return '';
				}
			},
			/**
			 * 初始化页面所有数据
			 */
			async initializePage() {
				try {
					// 并行初始化多项数据，提升加载速度
					await Promise.all([
						// this.loadSwiperData(),
						this.getTodayPatrolTask(),
						this.getAlarmData()
					]);
				} catch (error) {
					// console.error('页面数据初始化失败:', error);
					uni.showToast({
						title: '数据加载失败',
						icon: 'none'
					});
				} finally {
					// 无论成功失败，都隐藏加载状态
					this.isPageLoading = false;
				}
			},

			/**
			 * 加载轮播图数据
			 */
			async loadSwiperData() {
				try {
					const data = {
						"newsStatus": "220"
					};
					// 注释掉的代码可以根据实际需求恢复
					// const res = await indexPageSwiper.selectDetailListByLikeIndexPageSwiper(data);
					// this.indexPageSwiperList = res.rows || [];
				} catch (error) {
					// console.error('加载轮播图失败:', error);
					// 即使加载失败也不影响页面整体功能
				}
			},
			/**
			 * 获取当前登录人当天的巡更任务
			 */
			async getTodayPatrolTask() {
				try {
					this.isLoadingPlan = true;
					const queryParams = {
						patrolUserId: this.userId,
					};
					// 查询当天尚未完成的巡更任务
					const res = await patrolTaskExtend.selectUnfinishedOrUnstartedTask(queryParams);

					if (res.rows && res.rows.length > 0) {
						// 取最近的一条计划
						this.todayPatrolTask = res.rows[res.rows.length-1];
					} else {
						this.todayPatrolTask = null; // 当天无计划
					}
				} catch (error) {
					// console.error('获取当天巡更计划失败：', error);
					this.todayPatrolTask = null;
				} finally {
					this.isLoadingPlan = false;
				}
			},

			/**
			 * 图片加载失败处理
			 */
			handleImageError(e, type) {
				e.target.src = `/static/add.png`;
				// console.warn(`[图片加载失败] 类型：${type}，原路径：${e.target.src}`);
			},
			/**
			 * Tab切换事件处理
			 */
			tabChange({
				index,
				item
			}) {
				this.currentTab = index;
				// console.log("Tab切换事件处理 index=", JSON.stringify(index))
				// console.log("Tab切换事件处理 item=", JSON.stringify(item))
				// 根据选择的Tab加载对应数据
				if (item.value === '210') {
					this.getAlarmData();
				}
				if (item.value === '220') {
					let data = {}
					data.assigneeId = this.userId
					data.repairOrderStatus = "submit"
					maintainOrder.selectDetailListByLikeMaintainOrder(data).then(res => {
						// console.log("获取 工单信息 =", JSON.stringify(res))
						let list = res.rows;
						this.maintainOrderList = list;
					})
				}
			},
			getAlarmData() {
				let data = {}
				data.patrolUserId = this.userId
				data.patrolAlarmStatus = "210"
				patrolAlarm.selectDetailListByLikePatrolAlarm(data).then(res => {
					// console.log("报警信息  res=", JSON.stringify(res))
					let list = res.rows;
					this.patrolAlarmList = list;
					// // console.log("patrolAlarm  patrolAlarmList=", JSON.stringify(this.patrolAlarmList))
				})
			},
			/**
			 * 导航点击事件处理
			 */
			navClick(page) {
				// 显示加载提示，提升用户体验
				uni.showLoading({
					title: '加载中',
					mask: true
				});

				if (page === "/pages/find/find") {
					uni.switchTab({
						url: page,
						success: () => {
							uni.hideLoading();
						}
					})
				} else {
					uni.navigateTo({
						url: page,
						success: () => {
							uni.hideLoading();
						}
					})
				}
			},
			/**
			 * 跳转到轮播图详情页
			 */
			navToSwiperDetailPage(item) {
				uni.showLoading({
					title: '加载中',
					mask: true
				});

				uni.navigateTo({
					url: `/pages/index/indexPageSwiperDetail?id=${item.id}`,
					success: () => {
						uni.hideLoading();
					}
				})
			},

			/**
			 * 跳转到新闻详情页
			 */
			navToNewsDetailPage(item) {
				uni.showLoading({
					title: '加载中',
					mask: true
				});

				uni.navigateTo({
					url: `/pages/newsManage/newsDetail?id=${item.id}`,
					success: () => {
						uni.hideLoading();
					}
				})
				/**
				 * 跳转到巡更执行页面
				 */
			},
			gotoPatrolExecute(task) {
				uni.navigateTo({
					url: `/pages/patrolTask/patrolCheckin?id=${task.id}`
				});
			},
			/**
			 * 跳转到所有巡更计划页面
			 */
			navToAllPatrolTasks() {
				uni.showLoading({
					title: '加载中',
					mask: true
				});

				uni.navigateTo({
					url: '/pages/patrolTask/todayPatrolTasks',
					success: () => {
						uni.hideLoading();
					}
				});
			}
		}
	}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style>
	@import '@/static/styles/autoee/index/indexPage.scss';

	/* hx-navbar样式优化 */
	.a1 {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.a2 {
		font-size: 32rpx;
		font-weight: 600;
		color: #333;
	}

	/* 导航菜单样式 */
	.nav-menu {
		display: flex;
		flex-wrap: wrap;
		padding: 20rpx;
		background: #fff;
		margin: 20rpx;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	}

	.nav-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		width: 25%;
		padding: 24rpx 0;
	}

	.nav-item i {
		margin-bottom: 12rpx;
	}

	.nav-item text {
		font-size: 24rpx;
		color: #666;
	}

	.nav-item-active {
		background-color: #f0f9ff;
	}

	/* 轮播图样式优化 */
	.a_swiper {
		padding: 20rpx;
	}

	.a_swiper .swiper {
		border-radius: 16rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	}

	.a_image_wrapper {
		width: 100%;
		height: 300rpx;
		overflow: hidden;
	}

	.a_image_wrapper .loaded {
		width: 100%;
		height: 100%;
		transition: transform 0.3s ease;
	}

	.a_image_wrapper:active .loaded {
		transform: scale(1.05);
	}

	/* 通知区域样式优化 */
	.b1 {
		background: #fff;
		margin: 0 20rpx 20rpx;
		padding: 24rpx;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	}

	.b2 {
		flex: 1;
		overflow: hidden;
	}

	.b3 {
		width: 40rpx;
		height: 40rpx;
		color: #ff7a45;
	}

	.b4 {
		font-size: 24rpx;
		color: #999;
	}

	/* Tab内容区域样式优化 */
	.c6 {
		margin: 0 20rpx 20rpx;
		background: #fff;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
		overflow: hidden;
	}

	/* 报警信息列表样式优化 */
	.uni-list {
		padding: 0;
	}

	.uni-list-item {
		margin: 0 20rpx;
		padding: 20rpx 0;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.uni-list-item:last-child {
		border-bottom: none;
	}

	/* 全局加载蒙层 */
	.global-loading-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(255, 255, 255, 0.8);
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		z-index: 9999;
	}

	/* 计划列表样式 */
	.plan-list {
		margin: 15 rpx;
		border-radius: 12 rpx;
		background-color: #fff;
		box-shadow: 0 2 rpx 10 rpx rgba(0, 0, 0, 0.05);
	}

	.plan-card {
		padding: 20 rpx;
	}

	.plan-content {
		flex: 1;
		/* 让内容区域占据左侧空间 */
	}

	.plan-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 15 rpx;
	}

	.plan-name {
		font-size: 32 rpx;
		font-weight: 500;
		color: #333;
	}

	.plan-tag {
		padding: 4 rpx 16 rpx;
		border-radius: 20 rpx;
		font-size: 24 rpx;
	}

	.tag-unstarted {
		background-color: #f5f5f5;
		color: #666;
	}

	.tag-unfinished {
		background-color: #fff2e8;
		color: #fa8c16;
	}

	.plan-details {
		display: flex;
		flex-direction: column;
		gap: 12 rpx;
	}

	.plan-info {
		display: flex;
		align-items: center;
		font-size: 28 rpx;
		color: #666;
	}

	.info-icon {
		margin-right: 12 rpx;
		width: 24 rpx;
		height: 24 rpx;
	}

	.highlight {
		color: #1890ff;
		font-weight: 500;
	}

	/* 右侧按钮样式 */
	.btn-execute {
		background-color: #1890ff;
		color: #fff;
		border-radius: 30 rpx;
		padding: 8 rpx 20 rpx;
		font-size: 26 rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		margin-left: 10 rpx;
	}

	.btn-icon {
		margin-right: 8 rpx;
	}

	/* 巡更计划模块主样式 */
	.patrol-plan-section {
		padding: 20rpx;
		margin: 0 20rpx 20rpx;
		background: #fff;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	}

	/* 模块标题样式 */
	.plan-section-header {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 16rpx;
	}

	/* 标题左侧区域（图标和文字） */
	.plan-section-header-left {
		display: flex;
		align-items: center;
	}

	/* 标题图标样式 */
	.plan-section-header i {
		font-size: 28rpx;
		color: #1890ff;
		margin-right: 10rpx;
	}

	.header-title {
		font-size: 28rpx;
		font-weight: 600;
		color: #333;
	}

	/* 查看全部按钮样式 */
	.header-more {
		font-size: 24rpx;
		color: rgba(9, 37, 194, 0.9);
		padding: 4rpx 12rpx;
		transition: all 0.3s ease;
	}

	.header-more:active {
		color: #1890ff;
	}

	/* 加载状态样式 */
	.plan-loading {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 40rpx 0;
		color: #999;
	}

	.loading-text {
		font-size: 22rpx;
		margin-top: 16rpx;
	}

	/* 有计划卡片样式 */
	.plan-card {
		width: 100%;
		overflow: hidden;
		transition: all 0.3s ease;
	}

	.plan-card:active {
		background-color: #f8f8f8;
	}

	.plan-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 16rpx 0;
	}

	.plan-name {
		font-size: 28rpx;
		font-weight: 500;
		color: #333;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.plan-tag {
		padding: 4rpx 16rpx;
		border-radius: 20rpx;
		font-size: 20rpx;
	}

	.tag-unstarted {
		background: #e6f7ff;
		color: #1890ff;
	}

	.tag-unfinished {
		background: #fff7e6;
		color: #fa8c16;
	}

	/* 卡片内容样式 */
	.plan-details {
		padding: 12rpx 0;
	}

	.plan-info-item {
		display: flex;
		align-items: center;
		margin-bottom: 16rpx;
	}

	.plan-info-item:last-child {
		margin-bottom: 0;
	}

	/* 信息项图标样式 */
	.info-icon {
		font-size: 24rpx;
		color: #666;
		margin-right: 12 rpx;
	}

	.highlight-icon {
		color: #1890ff;
	}

	.info-text {
		color: #666;
		font-size: 24rpx;
		flex: 1;
	}

	.highlight {
		color: #1890ff;
		font-weight: 500;
	}

	/* 打卡按钮样式 */
	.plan-actions {
		display: flex;
		align-items: center;
		justify-content: flex-end;
		padding: 8rpx 0;
	}

	.btn-checkin {
		background: linear-gradient(90deg, #1890ff 0%, #40a9ff 100%);
		color: #fff;
		font-size: 26rpx;
		padding: 16rpx 56rpx;
		border-radius: 32rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		transition: all 0.3s ease;
	}

	.btn-checkin:active {
		transform: scale(0.95);
		opacity: 0.9;
	}

	.btn-icon {
		font-size: 24rpx;
		margin-right: 10rpx;
	}

	.btn-text {
		font-weight: 500;
	}

	/* 无计划状态样式 */
	.plan-empty {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 60rpx 0;
		color: #999;
		min-height: 300rpx;
	}

	.empty-icon {
		font-size: 120rpx;
		color: #ddd;
		margin-bottom: 24rpx;
	}

	.empty-title {
		font-size: 26rpx;
		margin-bottom: 12rpx;
		color: #666;
	}
</style>
