<template>
	<view>
		<view style="height: 45px;" v-if="fixed"></view>
		<scroll-view
			:scroll-x="layout === 'scroll'"
			:scroll-left="scrollLeft"
			scroll-with-animation="true"
			class="tabs"
			:style="{
				background: bk,
				width: tabWidth / 3.75 + 'vw',
				position: fixed ? 'fixed' : 'relative'
			}"
		>
			<view
				:class="[
					{
						'equal-layout': layout === 'equal'
					}
				]"
			>
				<view
					v-for="(item, index) in list"
					:key="index"
					:class="[{ value: select == index }, 'label']"
					:style="{
						fontSize: (select == index ? activeSize : size) + 'px',
						padding: '0 ' + labelPadding + 'px',
						color: select == index ? activeTextColor : textColor,
						fontWeight: select == index ? bold : '400'
					}"
					@click="selectTab(index, item)"
				>
					<text class="label-text">{{ keyName ? item[keyName] : item }}</text>
					<!-- 竖分割线 -->
					<view
						class="split-line"
						:style="{ background: slineColor }"
						v-if="splitLine && index != list.length - 1"
					></view>
				</view>

				<!-- 下划线 -->
				<view
					class="line"
					v-if="lineShow"
					:style="{
						top: linePlace,
						height: lineCrude + 'px',
						width: lineWidth + 'px',
						background: lineColor,
						transform: `translateX(${lineLeft}px)`
					}"
				></view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			select: -1,
			containerWidth: 0,
			lineWidth: 0,
			lineLeft: 0,
			labelWidth: [],
			labelTextWidth: [],
			tabBoxLeftMargin: 0, // 动态获取
			tabBoxLeftPadding: 10 // tab盒子左边padding
		};
	},
	props: {
		list: {
			type: Array,
			default() {
				return [];
			}
		},
		value: {
			type: Number,
			default: 0
		},
		keyName: {
			type: String, // 键名
			default: ''
		},
		layout: {
			type: String, // 布局
			default: 'equal' //  ’left‘|'equal'|'scroll' 靠左  均分  滚动
		},

		tabWidth: {
			type: Number, // tab 宽度
			default: 375
		},
		activeSize: {
			type: Number, // 选中字体大小
			default: 14
		},
		size: {
			type: Number, // 默认字体大小
			default: 14
		},
		textColor: {
			type: String, // tab字体颜色
			default: '#999'
		},
		activeTextColor: {
			type: String, // 选中tab字体颜色
			default: '#E9392F'
		},
		lineColor: {
			type: String, // 下划线颜色
			default: '#E9392F'
		},
		bk: {
			type: String, // 背景
			default: '#fff'
		},
		bold: {
			type: Number | String, // 字体粗细
			default: 400
		},
		labelPadding: {
			type: Number, // label 的 pandding
			default: 5
		},

		isLoading: {
			type: Boolean, // 加载中禁止点击
			default: false
		},
		fixed: {
			type: Boolean, // 是否固定
			default: false
		},
		lineShow: {
			type: Boolean, // 下划线
			default: false
		},

		lineScale: {
			type: Number, // 下划线伸缩比例
			default: 1
		},
		linePlace: {
			type: String, // 下划线的位置
			default: '35px'
		},
		lineCrude: {
			type: Number, // 下划线的粗细
			default: 2
		},

		splitLine: {
			type: Boolean, // 每两个中间加分割线
			default: false
		},
		slineColor: {
			type: String, // 每两个中间加分割线
			default: '#eee'
		}
	},
	computed: {
		scrollLeft() {
			let normalWidth = 0,
				fontScale = this.activeSize / this.size; // 选中后字体放大比例
			for (let i = 0; i < this.labelWidth.length; i++) {
				let dom = this.labelWidth[i];
				if (i === this.select) {
					//考虑 文字超出 tab 的情况
					let textWidth = Math.min(this.labelTextWidth[i].width, dom.width - this.labelPadding * 2);
					console.log(textWidth);
					//tab选项的文字 选中之后的宽度
					normalWidth = textWidth * fontScale;
					// tab选项 选中之后 宽度变化的差值
					let _diff = (fontScale - 1) * textWidth;
					this.lineLeft = this.labelTextWidth[i].left - this.tabBoxLeftMargin - this.tabBoxLeftPadding;
					// 均分布局的时候  再减去一个差值
					if (this.layout === 'equal') {
						this.lineLeft -= _diff / 2;
					}
					break;
				}
			}
			// 下划线左边距离 10 itemTab padding
			this.lineLeft = this.lineLeft;

			// 下划线长度默认和字体宽度一样， lineScale控制伸缩；
			if (this.lineScale !== 1) {
				this.lineWidth = normalWidth * this.lineScale;
				// 伸缩后 左边的距离  加上 差值的一半
				this.lineLeft += (normalWidth - this.lineWidth) / 2;
			} else {
				this.lineWidth = normalWidth;
			}

			// 超过1/3的距离
			if (this.lineLeft > this.containerWidth / 3) {
				return this.lineLeft - this.containerWidth / 3;
			} else {
				return 0;
			}
		}
	},
	watch: {
		value: {
			handler(val) {
				this.select = val;
			}
		},

		list: {
			handler(val) {},
			immediate: true
		}
	},
	mounted() {
		this.$nextTick(() => {
			this.countDomWidth();
		});
	},
	methods: {
		countDomWidth() {
			let query = uni
				.createSelectorQuery()
				// #ifndef MP-ALIPAY
				.in(this);
			// #endif
			query
				.select('.tabs')
				.boundingClientRect(res => {
					if (res) {
						this.containerWidth = res.width;
						this.tabBoxLeftMargin = res.left;
					}
				})
				// 初始化计算每个选项的宽度
				.selectAll('.label-text')
				.boundingClientRect(res => {
					this.labelTextWidth = res;
				})
				.selectAll('.label')
				.boundingClientRect(res => {
					this.labelWidth = res;
					this.select = this.value;
				})
				.exec();
		},
		selectTab(index, item) {
			// 加载中禁止点击
			if (this.isLoading) return;
			if (this.select !== index) {
				this.$emit('choose', {
					index,
					item
				});
				this.select = index;
				// this.$emit('input', index)
			}
		}
	}
};
</script>

<style lang="scss" scoped>
.tabs {
	width: 100%;
	height: 45px;
	line-height: 45px;
	padding: 0 10px;
	box-sizing: border-box;
	white-space: nowrap;
	
	::-webkit-scrollbar {
		display: none;
	}
	.equal-layout {
		display: flex;
		justify-content: space-between;
	}
	// border-top: vw(1) solid #f8f8f8;
	// border-bottom: vw(1) solid #f8f8f8;

	.line {
		border-radius: 10px;
		position: absolute;
		z-index: 0;
		transition: all 0.3s ease;
	}

	.label {
		flex-shrink: 0;
		flex: 1;
		text-align: center;
		display: inline-block;
		transition: all 0.2s ease;
		position: relative;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;

		.split-line {
			width: 1px;
			height: 20px;
			position: absolute;
			right: 0;
			transform: translateY(-50%);
			top: 50%;
		}

		.label-text {
			position: relative;
			z-index: 1;
		}
	}
}
</style>
