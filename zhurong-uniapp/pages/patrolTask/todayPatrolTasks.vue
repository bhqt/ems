<template>
	<view class="a_container">
		<!-- 加载状态 -->
		<view v-if="loading" class="plan-loading">
			<uni-icons type="spinner" size="36" color="#007aff" animation="spin"></uni-icons>
			<text class="loading-text">加载中...</text>
		</view>

		<!-- 无计划状态 -->
		<view v-else-if="patrolTasks.length === 0" class="plan-empty">
			<i class="empty-icon fa-solid fa-file-circle-xmark"></i>
			<text class="empty-title">今日暂无巡更任务</text>
		</view>

		<!-- 巡更任务列表 -->
		<uni-list v-else class="plan-list">
			<uni-list-item v-for="task in patrolTasks" :key="task.id" direction="column" class="plan-card"
				link="navigateTo" :to="'/pages/patrolTask/patrolCheckin?id='+task.id">
				<!-- 计划标题和状态 -->
				<template v-slot:header>
					<view class="plan-header">
						<text class="plan-name">{{ task.patrolTaskName || '未命名计划' }}</text>
					</view>
				</template>

				<!-- 计划详情内容 -->
				<template v-slot:body>
					<view class="plan-details">
						<!-- 任务状态 -->
						<view class="plan-info-item">
							<image :src="iconReqUrl + 'tasks.svg'" class="icon-common"></image>
							<text class="info-text">任务状态：{{ task.patrolTaskStatusExtend }}</text>
						</view>
						<!-- 时间信息 -->
						<view class="plan-info-item">
							<image :src="iconReqUrl + 'clock.svg'" class="icon-common"></image>
							<text class="info-text">计划时间：{{ task.startTime }} - {{ task.endTime }}</text>
						</view>

						<!-- 路线信息 -->
						<view class="plan-info-item">
							<image :src="iconReqUrl + 'map-location.svg'" class="icon-common"></image>
							<text class="info-text">巡更路线：{{ task.patrolPathIdExtend || '未关联路线' }}</text>
						</view>

						<!-- 人员信息 -->
						<view class="plan-info-item">
							<image :src="iconReqUrl + 'user.svg'" class="icon-common"></image>
							<text class="info-text">巡更人员：{{ task.patrolUserIdExtend || '未分配' }}</text>
						</view>
					</view>
				</template>

				<!-- 右侧打卡按钮 -->
				<template v-slot:footer>
					<view class="plan-actions">
						<button class="btn-checkin" @click.stop="gotoPatrolExecute(task.id)">
							<text class="btn-text">去打卡</text>
						</button>
					</view>
				</template>
			</uni-list-item>
		</uni-list>
	</view>
</template>

<script>
	import patrolTaskExtend from '@/api/autoee/patrolTaskExtend';
	// hx-navbar 组件已通过 uni_modules 自动注册，无需手动导入
	import config from '@/config'
	import {
		mapState
	} from 'vuex';
	import {
		getDictData
	} from '@/utils/dict'

	export default {
		// hx-navbar 组件已通过 uni_modules 自动注册，无需手动注册组件
		data() {
			return {
				iconReqUrl: config.iconReqUrl,
				userId: this.$store.getters.userId,
				patrolTasks: [],
				loading: true,
				queryParams: {
					// 可以添加查询参数，如果API需要的话
				}
			};
		},
		onLoad() {
			this.loadTodayPatrolTasks();
		},
		onShow() {
			// 页面显示时重新加载数据，以便刷新任务状态
			this.loadTodayPatrolTasks();
		},
		methods: {
			async loadTodayPatrolTasks() {
				this.loading = true;
				try {
					this.queryParams = {
						patrolUserId: this.userId,
					};
					const res = await patrolTaskExtend.selectUnfinishedOrUnstartedTask(this.queryParams);
					if (res.code === 200 && res.rows) {
						this.patrolTasks = res.rows;
					} else {
						this.patrolTasks = [];
					}
				} catch (error) {
					console.error('加载今日巡更任务失败:', error);
					this.patrolTasks = [];
				} finally {
					this.loading = false;
				}
			},

			getStatusText(status) {
				const statusMap = {
					'未开始': '未开始',
					'未完成': '未完成',
					'3': '已完成',
					'4': '已逾期'
				};
				return statusMap[status] || '未知状态';
			},

			getTagClass(status) {
				switch (status) {
					case '未开始':
						return 'tag-unstarted';
					case '未完成':
						return 'tag-unfinished';
					case '3':
					case '已完成':
						return 'tag-finished';
					case '4':
					case '已逾期':
						return 'tag-overdue';
					default:
						return '';
				}
			},

			gotoPatrolExecute(taskId) {
				// 跳转到打卡页面，传递任务ID
				uni.navigateTo({
					url: `/pages/patrolTask/patrolCheckin?id=${taskId}`
				});
			}
		}
	};
</script>

<style lang="scss" scoped>
	.a_container {
		padding-bottom: 20rpx;
		background-color: #f5f5f5;
	}

	.plan-loading {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 60rpx 0;
		background-color: #fff;
		margin: 20rpx;
		border-radius: 12rpx;
	}

	.loading-text {
		margin-top: 20rpx;
		font-size: 24rpx;
		color: #999;
	}

	.plan-empty {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 100rpx 0;
		color: #999;
		min-height: 400rpx;
		background-color: #fff;
		margin: 20rpx;
		border-radius: 12rpx;
	}

	.empty-icon {
		font-size: 120rpx;
		margin-bottom: 30rpx;
		color: #ccc;
	}

	.empty-title {
		font-size: 28rpx;
		margin-bottom: 10rpx;
	}

	.plan-list {
		padding: 20rpx;
	}

	.plan-card {
		background-color: #fff;
		border-radius: 12rpx;
		margin-bottom: 20rpx;
		overflow: hidden;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
	}

	.plan-card:active {
		background-color: #f5f5f5;
	}

	.plan-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 20rpx;
	}

	.plan-name {
		font-size: 28rpx;
		font-weight: bold;
		color: #333;
	}

	.plan-tag {
		padding: 4rpx 16rpx;
		border-radius: 16rpx;
		font-size: 20rpx;
	}

	.tag-unstarted {
		background-color: #f0f0f0;
		color: #666;
	}

	.tag-unfinished {
		background-color: #e6f7ff;
		color: #1890ff;
	}

	.tag-finished {
		background-color: #f6ffed;
		color: #52c41a;
	}

	.tag-overdue {
		background-color: #fff1f0;
		color: #ff4d4f;
	}

	.plan-details {
		padding: 0 20rpx 20rpx;
	}

	.plan-info-item {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.info-icon {
		width: 32rpx;
		height: 32rpx;
		font-size: 28rpx;
		color: #999;
		margin-right: 16rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.highlight-icon {
		color: #1890ff;
	}

	.info-text {
		font-size: 24rpx;
		color: #666;
		flex: 1;
	}

	.highlight {
		color: #1890ff;
		font-weight: 500;
	}

	.plan-actions {
		display: flex;
		justify-content: flex-end;
		padding: 16rpx 20rpx;
		border-top: 1rpx solid #f0f0f0;
	}

	.btn-checkin {
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: #1890ff;
		color: #fff;
		font-size: 24rpx;
		padding: 12rpx 32rpx;
		border-radius: 8rpx;
		border: none;
	}

	.btn-checkin:active {
		transform: scale(0.95);
		opacity: 0.8;
	}

	.btn-icon {
		font-size: 24rpx;
		margin-right: 8rpx;
	}

	.btn-text {
		font-size: 24rpx;
	}
</style>
