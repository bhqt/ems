<template>
  <view class="a_container">
    <!-- 加载状态 -->
    <view v-if="loading" class="loading-container">
      <uni-icons type="spinner" size="48" color="#1890ff" animation="spin"></uni-icons>
      <text class="loading-text">加载中...</text>
    </view>

    <!-- 任务详情 -->
    <view v-else-if="patrolTask" class="task-detail">
      <!-- 任务标题和状态 -->
      <view class="task-header">
        <text class="task-title">{{ patrolTask.patrolTaskName || '未命名任务' }}</text>
      </view>
      <!-- 任务详情列表 -->
      <view class="task-info">
        <view class="info-item">
								<image :src="iconReqUrl + 'tasks.svg'" class="icon-common"></image>
          <text class="info-label">任务状态：</text>
                  <view class="task-tag" :class="{
                    'tag-unstarted': patrolTask.patrolTaskStatusExtend === '未开始',
                    'tag-unfinished': patrolTask.patrolTaskStatusExtend === '未完成',
                    'tag-finished': patrolTask.patrolTaskStatusExtend === '已完成',
                    'tag-overdue': patrolTask.patrolTaskStatusExtend === '已关闭'
                  }">
                    {{ patrolTask.patrolTaskStatusExtend || '待执行' }}
                  </view>
        </view>
        <view class="info-item">
								<image :src="iconReqUrl + 'clock.svg'" class="icon-common"></image>
          <text class="info-label">任务时间：</text>
          <text class="info-value">{{ patrolTask.startTime }} - {{ patrolTask.endTime }}</text>
        </view>
        <view class="info-item">
								<image :src="iconReqUrl + 'map-location.svg'" class="icon-common"></image>
          <text class="info-label">巡更路线：</text>
          <text class="info-value">{{ patrolPath && patrolPath.routeName ? patrolPath.routeName : '未关联路线' }}</text>
        </view>

        <view class="info-item">
                            <image :src="iconReqUrl + 'user.svg'" class="icon-common"></image>
          <text class="info-label">巡更人员：</text>
          <text class="info-value">{{ patrolTask.patrolUserIdExtend || '未分配' }}</text>
        </view>


        <view class="info-item">
                            <image :src="iconReqUrl + 'bars-progress.svg'" class="icon-common"></image>
          <text class="info-label">进度：</text>
          <text class="info-value highlight">{{ finishedPointCount }}/{{ totalPointCount }} 个点位</text>
        </view>
      </view>

      <!-- 当前点位信息 -->
      <view v-if="currentPoint" class="current-point-section">
        <view class="section-title">
          <image :src="iconReqUrl + 'location-dot.svg'" class="icon-common"></image>
          <text>当前点位</text>
        </view>
        <view class="current-point-info">
          <text class="point-name">{{ currentPoint.pointOrder }}. {{ currentPoint.pointName }}</text>
          <text class="point-location">{{ currentPoint.pointLocation }}</text>
          <text v-if="currentPoint.isFinished" class="point-status finished">已完成</text>
          <text v-else class="point-status pending">待完成</text>
        </view>
      </view>

              <!-- 打卡按钮 -->
      <view class="checkin-action">
        <button v-if="currentPoint && !currentPoint.isFinished && !allPointsFinished"
                class="btn-checkin"
                @click="performCheckin"
                :disabled="isCheckingIn">
          <text v-if="isCheckingIn">打卡中...</text>
          <text v-else>
            <image :src="iconReqUrl + 'map-location.svg'" class="icon-common"></image> 立即打卡
          </text>
        </button>

        <view v-else-if="allPointsFinished" class="checked-in-success">
            <image :src="iconReqUrl + 'clipboard-check.svg'" class="icon-common"></image>
          <text class="success-text">所有点位已完成</text>
          <text class="checkin-time">完成时间：{{ checkinTime }}</text>
        </view>

        <view v-else class="already-checked-in">
            <image :src="iconReqUrl + 'clipboard-check.svg'" class="icon-common"></image>
          <text class="success-text">当前点位已完成</text>
        </view>
      </view>

      <!-- 点位列表 -->
      <view class="point-list-section" v-if="pointList.length > 0">
        <view class="section-title">
            <image :src="iconReqUrl + 'list-check.svg'" class="icon-common"></image>
          <text>点位列表</text>
        </view>
        <view class="point-list">
          <view v-for="(point, index) in pointList" :key="point.id"
                :class="['point-item', { 'current': index === currentPointIndex, 'finished': point.isFinished }]">
            <view class="point-item-header">
              <text class="point-order">{{ point.pointOrder }}</text>
              <text class="point-item-name">{{ point.pointName }}</text>
                <image v-if="point.isFinished" :src="iconReqUrl + 'clipboard-check.svg'" class="icon-common"></image>
            </view>
            <text class="point-item-location">{{ point.pointLocation }}</text>
            <text v-if="point.finishTime" class="point-finish-time">完成时间：{{ formatDate(point.finishTime) }}</text>
          </view>
        </view>
      </view>


    </view>

    <!-- 无数据状态 -->
    <view v-else class="no-data">
        <image :src="iconReqUrl + 'exclamation-circle.svg'" class="icon-common"></image>
      <text class="text">无法加载任务信息</text>
    </view>
  </view>
</template>

<script>
import patrolTaskExtend from '@/api/autoee/patrolTaskExtend';
	import config from '@/config'
	import {
		mapState
	} from 'vuex';
	import {
		getDictData
	} from '@/utils/dict'

export default {

  data() {
    return {
          iconReqUrl: config.iconReqUrl,
      taskId: '',
      patrolTask: null,
      patrolPath: null,
      pointList: [],
      currentPoint: null,
      currentPointIndex: -1,
      allPointsFinished: false,
      finishedPointCount: 0,
      totalPointCount: 0,
      loading: true,
      isCheckingIn: false,
      checkinTime: ''
    };
  },
  onLoad(options) {
    if (options.id) {
      this.taskId = options.id;
      this.loadTaskDetail();
    } else {
      this.loading = false;
    }
  },
  methods: {
    async loadTaskDetail() {
      this.loading = true;
      try {
        // 使用新的API获取巡更任务详情
        const res = await patrolTaskExtend.getPatrolTaskDetails(this.taskId);
        if (res.code === 200) {
          this.patrolTask = res.patrolTask;
          this.patrolPath = res.patrolPath;
          this.pointList = res.pointList || [];
          this.currentPoint = res.currentPoint;
          this.currentPointIndex = res.currentPointIndex || -1;
          this.allPointsFinished = res.allPointsFinished || false;

          // 计算已完成点位数量
          this.finishedPointCount = this.pointList.filter(point => point.isFinished).length;
          this.totalPointCount = this.pointList.length;

          // 如果所有点位已完成，设置完成时间
          if (this.allPointsFinished && this.pointList.length > 0) {
            const lastFinishedPoint = this.pointList[this.pointList.length - 1];
            if (lastFinishedPoint.finishTime) {
              this.checkinTime = this.formatDate(lastFinishedPoint.finishTime);
            }
          }
        } else {
          uni.showToast({
            title: res.msg || '获取任务详情失败',
            icon: 'none',
            duration: 2000
          });
        }
      } catch (error) {
        console.error('加载任务详情失败:', error);
        uni.showToast({
          title: '加载任务详情失败',
          icon: 'none',
          duration: 2000
        });
      } finally {
        this.loading = false;
      }
    },



    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    async performCheckin() {
      if (this.isCheckingIn || !this.currentPoint || this.currentPoint.isFinished) return;

      try {
        // 检查设备是否支持NFC
        const nfcAvailable = await this.checkNFCAvailability();
        if (!nfcAvailable) {
          return;
        }

        // 扫描NFC标签获取位置信息
        const nfcLocation = await this.scanNFCTag();
        if (!nfcLocation) {
          return;
        }

        // 比对NFC标签上的位置信息与当前点位位置
        if (nfcLocation !== this.currentPoint.pointLocation) {
          uni.showToast({
            title: 'NFC标签位置['+nfcLocation+']与当前点位['+this.currentPoint.pointLocation+']不匹配',
            icon: 'none',
            duration: 2000
          });
          return;
        }

        this.isCheckingIn = true;

        // 调用实际的打卡API，传入所需参数
        const checkinData = {
          planId: this.patrolTask.patrolPlanId, // 计划id
          routeId: this.patrolPath ? this.patrolPath.id : null, // 路线id
          taskId: this.taskId, // 任务id
          pointId: this.currentPoint.id // 点位id
        };

        const res = await patrolTaskExtend.performCheckin(checkinData);

        if (res.code === 200) {
          // 打卡成功后，重新加载任务详情，确保所有状态信息都是最新的
          await this.loadTaskDetail();

          // 显示成功提示
          uni.showToast({
            title: '打卡成功',
            icon: 'success',
            duration: 2000
          });

          // 如果所有点位已完成，2秒后返回上一页
          if (this.allPointsFinished) {
            setTimeout(() => {
              uni.navigateBack();
            }, 2000);
          }
        } else {
          // 打卡失败时显示错误信息
          uni.showToast({
            title: res.msg || '打卡失败',
            icon: 'none',
            duration: 2000
          });
        }

      } catch (error) {
        console.error('打卡失败:', error);
        uni.showToast({
          title: '打卡失败，请重试',
          icon: 'none',
          duration: 2000
        });
      } finally {
        this.isCheckingIn = false;
      }
    },

    // 检查设备是否支持NFC
    checkNFCAvailability() {
      return new Promise((resolve) => {
        uni.getSystemInfo({
          success: (res) => {
            // 检查设备是否支持NFC
            if (!res.nfc) {
              uni.showToast({
                title: '当前设备不支持NFC功能',
                icon: 'none',
                duration: 2000
              });
              resolve(false);
              return;
            }

            // 请求NFC权限
            uni.requestPermissions({
              permissions: ['nfc'],
              success: (permRes) => {
                if (permRes['nfc'] === 'granted') {
                  resolve(true);
                } else {
                  uni.showToast({
                    title: '请授予NFC权限以完成打卡',
                    icon: 'none',
                    duration: 2000
                  });
                  resolve(false);
                }
              },
              fail: () => {
                uni.showToast({
                  title: '获取NFC权限失败',
                  icon: 'none',
                  duration: 2000
                });
                resolve(false);
              }
            });
          },
          fail: () => {
            resolve(false);
          }
        });
      });
    },

    // 扫描NFC标签并解析NDEF数据
    scanNFCTag() {
      return new Promise((resolve) => {
        // 显示扫描提示
        uni.showLoading({
          title: '请将手机靠近NFC标签',
          mask: true
        });

        // 创建NFC监听器
        const nfcListener = {
          onShowNdefMessage: (res) => {
            uni.hideLoading();

            try {
              // 解析NDEF消息中的文本记录
              const ndefRecords = res.message.records || [];
              for (let record of ndefRecords) {
                // 检查是否为文本类型记录
                if (record.typeNameFormat === 1 && record.type.toString() === 'T') {
                  // 解析文本数据
                  const languageCodeLength = record.payload[0];
                  const textData = new TextDecoder().decode(
                    record.payload.slice(1 + languageCodeLength)
                  );
                  resolve(textData);
                  return;
                }
              }

              // 未找到文本类型记录
              uni.showToast({
                title: '未能解析NFC标签数据',
                icon: 'none',
                duration: 2000
              });
              resolve(null);
            } catch (error) {
              console.error('解析NFC数据失败:', error);
              uni.showToast({
                title: '解析NFC标签失败',
                icon: 'none',
                duration: 2000
              });
              resolve(null);
            }
          },
          onError: (error) => {
            uni.hideLoading();
            console.error('NFC扫描错误:', error);
            uni.showToast({
              title: 'NFC扫描失败',
              icon: 'none',
              duration: 2000
            });
            resolve(null);
          }
        };

        // 开始NFC监听
        uni.startHCE({
          success: () => {
            // 注册NFC消息监听
            uni.onHCEMessage(nfcListener.onShowNdefMessage);

            // 设置30秒超时
            setTimeout(() => {
              uni.hideLoading();
              uni.stopHCE();
              uni.offHCEMessage(nfcListener.onShowNdefMessage);
              uni.showToast({
                title: 'NFC扫描超时',
                icon: 'none',
                duration: 2000
              });
              resolve(null);
            }, 30000);
          },
          fail: (error) => {
            uni.hideLoading();
            console.error('启动NFC服务失败:', error);
            uni.showToast({
              title: '启动NFC服务失败',
              icon: 'none',
              duration: 2000
            });
            resolve(null);
          }
        });
      });
    }
  }
};
</script>

<style scoped>
.a_container {
  padding: 30rpx;
  box-sizing: border-box;
  height: 100vh;
  background-color: #f5f5f5;
}

/* 加载状态 */
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.loading-text {
  margin-top: 20rpx;
  font-size: 28rpx;
  color: #666;
}

/* 任务详情 */
.task-detail {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

/* 任务标题和状态 */
.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
}

.task-title {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
}

.task-tag {
  padding: 8rpx 20rpx;
  border-radius: 16rpx;
  font-size: 24rpx;
}

.tag-unstarted {
  background-color: #e6f7ff;
  color: #1890ff;
}

.tag-unfinished {
  background-color: #fff7e6;
  color: #fa8c16;
}

.tag-finished {
  background-color: #f6ffed;
  color: #52c41a;
}

.tag-overdue {
  background-color: #fff1f0;
  color: #ff4d4f;
}

/* 任务详情列表 */
.task-info {
  display: flex;
  flex-direction: column;
  gap: 24rpx;
  margin-bottom: 30rpx;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 16rpx;
}

.info-icon {
  font-size: 30rpx;
  color: #999;
}

.highlight-icon {
  color: #1890ff;
}

.info-label {
  font-size: 28rpx;
  color: #666;
  min-width: 140rpx;
}

.info-value {
  font-size: 28rpx;
  color: #333;
  flex: 1;
}

.highlight {
  color: #1890ff;
  font-weight: bold;
}

/* 当前点位信息 */
.current-point-section {
  margin: 30rpx 0;
  padding: 20rpx;
  background-color: #e6f7ff;
  border-radius: 12rpx;
}

.current-point-info {
  padding: 20rpx;
  background-color: #fff;
  border-radius: 10rpx;
  border-left: 6rpx solid #1890ff;
}

.point-name {
  display: block;
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 10rpx;
}

.point-location {
  display: block;
  font-size: 26rpx;
  color: #666;
  margin-bottom: 10rpx;
}

.point-status {
  font-size: 24rpx;
  padding: 4rpx 16rpx;
  border-radius: 12rpx;
}

.point-status.finished {
  background-color: #f6ffed;
  color: #52c41a;
}

.point-status.pending {
  background-color: #fff7e6;
  color: #fa8c16;
}

/* 点位列表 */
.point-list-section {
  margin: 30rpx 0;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 10rpx;
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
  padding-bottom: 15rpx;
  border-bottom: 2rpx solid #f0f0f0;
}

.point-list {
  display: flex;
  flex-direction: column;
  gap: 15rpx;
}

.point-item {
  padding: 20rpx;
  background-color: #fafafa;
  border-radius: 10rpx;
  border-left: 4rpx solid #d9d9d9;
}

.point-item.current {
  background-color: #e6f7ff;
  border-left-color: #1890ff;
}

.point-item.finished {
  background-color: #f6ffed;
  border-left-color: #52c41a;
}

.point-item-header {
  display: flex;
  align-items: center;
  gap: 15rpx;
  margin-bottom: 10rpx;
}

.point-order {
  width: 40rpx;
  height: 40rpx;
  line-height: 40rpx;
  text-align: center;
  background-color: #999;
  color: #fff;
  font-size: 24rpx;
  border-radius: 50%;
}

.point-item.current .point-order {
  background-color: #1890ff;
}

.point-item.finished .point-order {
  background-color: #52c41a;
}

.point-item-name {
  font-size: 28rpx;
  font-weight: bold;
  color: #333;
  flex: 1;
}

.finished-icon {
  font-size: 30rpx;
  color: #52c41a;
}

.point-item-location {
  display: block;
  font-size: 26rpx;
  color: #666;
  margin-bottom: 10rpx;
}

.point-finish-time {
  display: block;
  font-size: 24rpx;
  color: #999;
}

/* 打卡按钮 */
.checkin-action {
  margin-top: 30rpx;
  display: flex;
  justify-content: center;
}

.btn-checkin {
  width: 100%;
  height: 90rpx;
  line-height: 90rpx;
  background-color: #1890ff;
  color: #fff;
  font-size: 32rpx;
  font-weight: bold;
  border-radius: 45rpx;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10rpx;
}

.btn-checkin:disabled {
  background-color: #d9d9d9;
  color: #fff;
}

/* 打卡成功状态 */
.checked-in-success {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30rpx 0;
}

.already-checked-in {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30rpx 0;
}

.success-icon {
  font-size: 60rpx;
  color: #52c41a;
  margin-bottom: 15rpx;
}

.success-text {
  font-size: 32rpx;
  color: #52c41a;
  font-weight: bold;
  margin-bottom: 10rpx;
}

.checkin-time {
  font-size: 26rpx;
  color: #999;
}

/* 无数据状态 */
.no-data {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.no-data .icon {
  font-size: 80rpx;
  color: #d9d9d9;
  margin-bottom: 20rpx;
}

.no-data .text {
  font-size: 28rpx;
  color: #999;
}
</style>
