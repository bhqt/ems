<!--// 图片列表展示组件 - 通过图片预览功能显示图片（兼容OSS ID和直接URL）-->
<template>
	<view class="image-list-container" :style="{ gap: gap + 'rpx' }">
		<!-- 加载状态 -->
		<view v-if="loading" class="image-loading">
			<uni-icons type="spinner" size="20" class="loading-icon"></uni-icons>
			<text>图片加载中...</text>
		</view>

		<!-- 加载完成 -->
		<template v-else>
			<!-- 正常图片 -->
			<view
				v-for="(image, index) in validImages"
				:key="index"
				class="image-item"
				@tap="previewImage(index)"
			>
				<image
					:src="image.url"
					:style="{ width: size + 'rpx', height: size + 'rpx' }"
					mode="aspectFill"
					@error="handleImageError(index)"
				></image>
			</view>

			<!-- 加载失败的提示 -->
			<view
				v-for="(image, index) in errorImages"
				:key="'error-' + index"
				class="image-error"
				:style="{ width: size + 'rpx', height: size + 'rpx' }"
			>
				<uni-icons type="close" size="24" color="#f56c6c"></uni-icons>
				<text class="error-text">加载失败</text>
			</view>
		</template>
	</view>
</template>

<script>
import {listByIds} from '@/api/system/oss';
import config from '@/config'

/**
 * 判断是否是OSS ID（假设OSS ID是纯数字）
 * @param {string} str
 * @returns {boolean}
 */
function isOssId(str) {
	return /^\d+$/.test(str);
}

export default {
	name: 'image-show-list',
	props: {
		images: {
			type: [String, Array],
			default: ''
		},
		size: {
			type: Number,
			default: 100
		},
		gap: {
			type: Number,
			default: 20
		}
	},
	data() {
		return {
			loading: false,
			loadedUrls: []
		};
	},
	computed: {
		// 有效图片（加载成功）
		validImages() {
			return this.loadedUrls.filter(item => !item.error && item.url);
		},
		// 错误图片（加载失败）
		errorImages() {
			return this.loadedUrls.filter(item => item.error);
		},
		// 预览图列表
		previewList() {
			return this.validImages.map(item => item.url);
		}
	},
	watch: {
		images: {
			immediate: true,
			handler(newVal) {
				console.log('image-show-list: images属性变化，新值:', newVal);
				this.loadImages(newVal);
			}
		}
	},
	methods: {
		async loadImages(images) {
			console.log('image-show-list: loadImages方法被调用，输入images:', images);
			try {
				this.loading = true;

				// 初始化加载结果
				this.loadedUrls = [];

				// 处理空值情况
				if (!images || images === '' || (Array.isArray(images) && images.length === 0)) {
					this.loading = false;
					return;
				}

				// 统一转为数组
				let imageList = [];

				// 增强的数据格式处理逻辑
				if (Array.isArray(images)) {
					// 如果是数组，过滤掉空值和无效值
					imageList = images.filter(item => item && item !== '' && item !== null && item !== undefined);
				} else if (typeof images === 'string') {
					// 如果是字符串，处理多种可能的格式
					const trimmedImages = images.trim();

					if (!trimmedImages) {
						this.loading = false;
						return;
					}

					try {
						// 尝试解析为JSON数组
						if (trimmedImages.startsWith('[') && trimmedImages.endsWith(']')) {
							const parsedArray = JSON.parse(trimmedImages);
							if (Array.isArray(parsedArray)) {
								imageList = parsedArray.filter(item => item && item !== '' && item !== null && item !== undefined);
							}
						} else {
							// 尝试按逗号分割
							imageList = trimmedImages.split(',').map(item => item.trim()).filter(item => item);
						}
					} catch (e) {
						console.warn('尝试解析JSON失败，将作为单个图片处理:', e);
						imageList = [trimmedImages];
					}
				} else {
					// 其他类型，转为字符串处理
					imageList = [String(images)];
				}

				// 检查是否有有效图片
				if (imageList.length === 0) {
					this.loading = false;
					return;
				}

				// 判断是否是OSS ID（假设OSS ID是纯数字）
				const isOssIdList = imageList.every(img => {
					// 确保img是字符串类型再进行正则匹配
					const imgStr = String(img);
					return isOssId(imgStr);
				});

				if (isOssIdList) {
					// 处理OSS ID情况
					console.log('image-show-list: 开始处理OSS ID列表', imageList);
					try {
						// 将imageList中的每个元素转为字符串并拼接
						const ossIds = imageList.map(id => String(id)).join(',');
						console.log('image-show-list: 准备调用listByIds接口，ossIds:', ossIds);
						const response = await listByIds(ossIds);
						console.log('image-show-list: listByIds接口调用成功，返回数据:', response);

						// 确保response.data是数组
						const dataArray = Array.isArray(response.data) ? response.data : [];

						this.loadedUrls = dataArray.map(item => ({
							url: item && item.url ? item.url : '',
							ossId: item && item.ossId ? item.ossId : null,
							error: !item || !item.url
						}));
					} catch (error) {
						console.error('OSS图片加载失败:', error);
						this.loadedUrls = imageList.map(item => ({
							url: '',
							ossId: item,
							error: true
						}));
					}
				} else {
					// 处理直接URL情况
					this.loadedUrls = imageList.map(url => {
						let requestURL = url
						if (this.$stringUtil.startsWith(requestURL, "http")) {
						} else {
							requestURL = config.baseUrl + url
						}
						console.log("requestURL=", JSON.stringify(requestURL))
						// 确保url是字符串类型
						const urlStr = String(requestURL);
						return {
							url: urlStr,
							ossId: null,
							error: !urlStr || urlStr.trim() === ''
						};
					});
				}
			} catch (error) {
				console.error('图片加载过程中发生错误:', error);
				this.loadedUrls = [];
			} finally {
				this.loading = false;
			}
		},

		// 预览图片
		previewImage(index) {
			const urls = this.previewList;
			if (urls.length > 0) {
				uni.previewImage({
					current: index,
					urls: urls,
					indicator: 'default'
				});
			}
		},

		// 处理图片加载错误
		handleImageError(index) {
			this.$set(this.loadedUrls[index], 'error', true);
		}
	}
};
</script>

<style scoped>
.image-list-container {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
}

.image-item {
	border-radius: 6 rpx;
	overflow: hidden;
	box-shadow: 0 2 rpx 8 rpx rgba(0, 0, 0, 0.1);
}

.image-loading {
	display: flex;
	align-items: center;
	color: #909399;
	font-size: 28 rpx;
}

.loading-icon {
	margin-right: 10 rpx;
	animation: rotating 1s linear infinite;
}

.image-error {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	background-color: #f5f7fa;
	border-radius: 6 rpx;
}

.error-text {
	font-size: 24 rpx;
	color: #f56c6c;
	margin-top: 8 rpx;
}

@keyframes rotating {
	from {
		transform: rotate(0deg);
	}
	to {
		transform: rotate(360deg);
	}
}
</style>
