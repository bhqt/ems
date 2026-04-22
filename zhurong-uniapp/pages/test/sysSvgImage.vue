<template>
	<div class="icon-browser">
		<h1 class="page-title">系统图标浏览与搜索</h1>

        <div class="search-container">
            <uni-easyinput v-model="searchQuery" placeholder="输入图标名称或中文描述搜索..."
                           @input="handleSearch" clearable/>
            <div class="search-result-count">
                找到 {{ totalFilteredCount }} 个匹配图标，已显示 {{ visibleIcons.length }} 个
            </div>
        </div>

		<div class="icons-grid">
			<div v-for="(icon, index) in visibleIcons" :key="index" class="icon-item" @click="handleIconClick(icon)">
				<img :src="`${iconReqUrl}${icon.name}`" :alt="icon.name" class="icon-common">
				<div class="icon-name">{{ icon.name }}</div>
			</div>
		</div>

		<!-- 加载指示器 -->
		<div v-if="isLoading" class="loading-indicator">
			<div class="spinner"></div>
			<p>加载更多图标...</p>
		</div>

		<!-- 没有更多数据提示 -->
		<div v-if="!isLoading && visibleIcons.length >= totalFilteredCount && totalFilteredCount > 0"
			class="end-message">
			已显示全部图标
		</div>

			<!-- 图标详情弹窗 -->
		<div v-if="selectedIcon" class="modal-overlay">
			<div class="modal-content">
				<div class="modal-header">
					<h3>图标详情</h3>
					<button class="close-btn" @click="selectedIcon = null">×</button>
				</div>
				<div class="modal-body">
					<div class="detail-icon-container">
						<img :src="`${iconReqUrl}${selectedIcon.name}`" :alt="selectedIcon.cnName || selectedIcon.name"
							class="detail-icon">
					</div>
					<div class="detail-info">
						<p><strong>英文名称:</strong> {{ selectedIcon.name }}</p>
						<p><strong>中文名称:</strong> {{ selectedIcon.cnName }}</p>
<!--						<p><strong>引用路径:</strong> {{ iconReqUrl }}{{ selectedIcon.name }}</p>-->

						<view class="a_button">
							<button type="primary" @click="copyIconPath(selectedIcon)">复制名称</button>
						</view>

					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'SysIconBrowserWithInfiniteScroll',
		data() {
			return {
				iconReqUrl: this.$iconUtil.getIconSysUrl('')
                ,
				searchQuery: '',
				selectedIcon: null,
				// 完整图标列表
				allIcons: [
					{ name: 'add.png' },
					{ name: 'alarm.svg' },
					{ name: 'arc.png' },
					{ name: 'biaoshi.svg' },
					{ name: 'book.svg' },
					{ name: 'ditu.svg' },
					{ name: 'faxian.svg' },
					{ name: 'find.png' },
					{ name: 'find_select.png' },
					{ name: 'huojiang.svg' },
					{ name: 'index.png' },
					{ name: 'index_select.png' },
					{ name: 'iot(物联网)设备.svg' },
					{ name: 'iot-area-2.svg' },
					{ name: 'iot-area.svg' },
					{ name: 'iot-device-2.svg' },
					{ name: 'iot-device.svg' },
					{ name: 'keche.svg' },
					{ name: 'kecheng.svg' },
					{ name: 'message.png' },
					{ name: 'message_select.png' },
					{ name: 'my.png' },
					{ name: 'my_select.png' },
					{ name: 'notice.svg' },
					{ name: 'school.svg' },
					{ name: 'schoolInfo.svg' },
					{ name: 'score.svg' },
					{ name: 'select.png' },
					{ name: 'selected.png' },
					{ name: 'set.svg' },
					{ name: 'shengpi.svg' },
					{ name: 'system.png' },
					{ name: 'system_select.png' },
					{ name: 'tab-cart-current.png' },
					{ name: 'tab-cart.png' },
					{ name: 'tab-cate-current.png' },
					{ name: 'tab-cate.png' },
					{ name: 'tab-home-current.png' },
					{ name: 'tab-home.png' },
					{ name: 'tab-my-current.png' },
					{ name: 'tab-my.png' },
					{ name: 'tab-zhixun.png' },
					{ name: 'tab-zhixun_current.png' },
					{ name: 'todo.png' },
					{ name: 'xiaofei.svg' },
					{ name: 'yingyong.svg' },
					{ name: 'iot-device3.svg' },
					{ name: 'iot-device4.svg' }
				],
				// 筛选后的图标列表
				filteredIcons: [],
				// 已加载的数量
				loadedCount: 0,
				// 每批加载的数量
				batchSize: 20,
				// 加载状态
				isLoading: false
			};
		},
		computed: {
			// 可见的图标（已加载的部分）
			visibleIcons() {
				return this.filteredIcons.slice(0, this.loadedCount);
			},
			// 筛选后的总数
			totalFilteredCount() {
				return this.filteredIcons.length;
			}
		},
		mounted() {
			// 初始化筛选
			this.filteredIcons = [...this.allIcons];
			// 初始加载
			this.loadMore();

			// 监听滚动事件
			window.addEventListener('scroll', this.handleScroll);
		},
		beforeUnmount() {
			// 移除滚动监听
			window.removeEventListener('scroll', this.handleScroll);
		},
		methods: {
			// 处理搜索
			handleSearch() {
				const query = this.searchQuery.toLowerCase().trim();

				if (!query) {
					this.filteredIcons = [...this.allIcons];
				} else {
					this.filteredIcons = this.allIcons.filter(icon =>
						icon.name.toLowerCase().includes(query)
					);
				}

				// 重置加载状态
				this.loadedCount = 0;
				this.loadMore();

			},

			// 加载更多图标
			loadMore() {
				if (this.isLoading || this.loadedCount >= this.totalFilteredCount) {
					return;
				}

				this.isLoading = true;

				// 模拟网络请求延迟
				setTimeout(() => {
					// 计算本次加载的数量
					const newCount = Math.min(
						this.loadedCount + this.batchSize,
						this.totalFilteredCount
					);

					this.loadedCount = newCount;
					this.isLoading = false;
				}, 500);
			},

			// 处理滚动事件
			handleScroll() {
				// 当滚动到页面底部附近时加载更多
				if (
					window.innerHeight + window.scrollY >=
					document.body.offsetHeight - 500 && // 距离底部500px时触发
					!this.isLoading &&
					this.loadedCount < this.totalFilteredCount
				) {
					this.loadMore();
				}
			},

			// 处理图标点击
			handleIconClick(icon) {
				this.selectedIcon = icon;
			},

			// 复制图标路径
			copyIconPath(icon) {
				const iconPath = `${icon.name}`;
				navigator.clipboard.writeText(iconPath).then(() => {
					this.selectedIcon = null
				 	this.$api.msg('图标名称已复制到剪贴板');
				}).catch(err => {
					console.error('无法复制名称: ', err);
					uni.showToast({
						title: '复制失败，请手动复制',
						icon: 'none'
					});
				});
			}
		}
	};
</script>

<style scoped>
	.icon-browser {
		max-width: 1200px;
		margin: 0 auto;
		padding: 20px;
	}

	.page-title {
		text-align: center;
		color: #2c3e50;
		margin-bottom: 20px;
		font-size: 2rem;
	}

	/* 搜索容器 */
	.search-container {
		margin-bottom: 20px;
	}

	.search-input {
		width: 100%;
		padding: 12px 16px;
		border: 1px solid #ddd;
		border-radius: 8px;
		font-size: 16px;
		box-sizing: border-box;
		transition: border-color 0.3s;
	}

	.search-input:focus {
		outline: none;
		border-color: #3498db;
	}

	.search-result-count {
		margin-top: 8px;
		color: #666;
		font-size: 14px;
	}

	/* 图标网格 */
	.icons-grid {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
		gap: 20px;
	}

	/* 图标项 */
	.icon-item {
		text-align: center;
		padding: 15px;
		border: 1px solid #eee;
		border-radius: 10px;
		cursor: pointer;
		transition: all 0.3s;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.icon-item:hover {
		background-color: #f9f9f9;
		border-color: #3498db;
		transform: translateY(-3px);
		box-shadow: 0 4px 12px rgba(52, 152, 219, 0.1);
	}

	/* 图标样式 */
	.icon-common {
		width: 64px;
		height: 64px;
		margin-bottom: 10px;
		object-fit: contain;
	}

	/* 图标名称 */
	.icon-name {
		font-size: 14px;
		color: #333;
		word-break: break-all;
	}

	.icon-cn-name {
		font-size: 12px;
		color: #666;
	}

	/* 加载指示器 */
	.loading-indicator {
		text-align: center;
		padding: 30px 0;
	}

	.spinner {
		width: 40px;
		height: 40px;
		border: 4px solid rgba(52, 152, 219, 0.1);
		border-left-color: #3498db;
		border-radius: 50%;
		animation: spin 1s linear infinite;
		margin: 0 auto 10px;
	}

	@keyframes spin {
		to {
			transform: rotate(360deg);
		}
	}

	/* 结束消息 */
	.end-message {
		text-align: center;
		padding: 20px 0;
		color: #666;
		font-style: italic;
	}

	/* 弹窗样式 */
	.modal-overlay {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 1000;
		animation: fadeIn 0.3s;
	}

	.modal-content {
		background-color: white;
		border-radius: 10px;
		width: 90%;
		max-width: 500px;
		box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
		animation: slideIn 0.3s;
	}

	.modal-header {
		padding: 15px 20px;
		border-bottom: 1px solid #eee;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.modal-header h3 {
		margin: 0;
		color: #2c3e50;
	}

	.close-btn {
		background: none;
		border: none;
		font-size: 20px;
		cursor: pointer;
		color: #666;
		transition: color 0.2s;
	}

	.close-btn:hover {
		color: #e74c3c;
	}

	.modal-body {
		padding: 20px;
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 20px;
	}

	.detail-icon-container {
		background-color: #f9f9f9;
		padding: 20px;
		border-radius: 8px;
	}

	.detail-icon {
		width: 98px;
		height: 98px;
		object-fit: contain;
	}

	.detail-info {
		width: 100%;
		font-size: 16px;
	}

	.detail-info p {
		margin: 10px 0;
		line-height: 1.5;
	}

	.detail-info strong {
		color: #2c3e50;
	}

	.copy-btn {
		margin-top: 15px;
		padding: 8px 16px;
		background-color: #3498db;
		color: white;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		transition: background-color 0.2s;
		font-size: 14px;
	}

	.copy-btn:hover {
		background-color: #2980b9;
	}

	/* 动画 */
	@keyframes fadeIn {
		from {
			opacity: 0;
		}

		to {
			opacity: 1;
		}
	}

	@keyframes slideIn {
		from {
			transform: translateY(-50px);
			opacity: 0;
		}

		to {
			transform: translateY(0);
			opacity: 1;
		}
	}

	@media (max-width: 768px) {
		.icons-grid {
			grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
		}

		.icon-common {
			width: 48px;
			height: 48px;
		}

		.page-title {
			font-size: 1.5rem;
		}
	}
</style>
