<template>
	<view>
		<hx-navbar ref="hxnb" :config="config">
			<block slot="left">
				<view class="a1">
					<text class="a2">消息</text>
				</view>
			</block>
		</hx-navbar>
		<l-tabs :list="tabarr" keyName="title" :lineShow="true" :lineCrude="6" :activeSize="18" linePlace="26px"
			activeTextColor="#333" bold=600 textColor="#333" lineColor="linear-gradient(to right, #98F0BC 30%, #98F0BC)"
			@choose="tabChange">
		</l-tabs>
		<view class="a3" v-for="(item,index) in messagearr" :key="index">
			<view class="a4">
				<image
					:src="item.icon"
					class="a5"></image>
				<view class="a6">
					<view class="a7">教职工请假申请</view>
					<view class="a8">工作部门：系统维护 请假申请人：</view>
				</view>
			</view>
			<view class="a9">昨天</view>
		</view>
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
				tabarr: [{
						title: '待办'
					},
					{
						title: '待阅'
					},
					{
						title: '已办'
					},
					{
						title: '已阅'
					}
				],
				tab: 0,
				messagearr:[{
					icon:'https://zhxy-mini.oss-cn-hangzhou.aliyuncs.com/systemicon/f413eab5c055a560c3f7b074121887e.png'
				},{
					icon:'https://zhxy-mini.oss-cn-hangzhou.aliyuncs.com/messageicon/edaefcc8b5dedc0b0f2868675820cb6.png'
				},{
					icon:'https://zhxy-mini.oss-cn-hangzhou.aliyuncs.com/messageicon/f018572d01cc6107ac389eb0a136362.png'
				},{
					icon:'https://zhxy-mini.oss-cn-hangzhou.aliyuncs.com/messageicon/0bcdecb701be04dbb4df455a1fe94ed.png'
				},{
					icon:'https://zhxy-mini.oss-cn-hangzhou.aliyuncs.com/messageicon/c24c6ecb8e0f95a4badf8289139efb7.png'
				}]
			}
		},
		
		methods: {
			tabChange({
				index,
				item
			}) {

			},
		}
	}
</script>

<style>
	page {
		background-color: #FFFFFF;
	}

	.a1 {
		padding-left: 35upx;
	}

	.a2 {
		font-size: 18px;
		font-weight: 700;
	}

	.a3 {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		padding-left: 50upx;
		padding-right: 50upx;
		padding-top: 50upx;
	}

	.a4 {
		display: flex;
		flex-direction: row;
		align-items: center;
	}

	.a5 {
		width: 80upx;
		height: 80upx;
		margin-right: 15upx;
	}

	.a6 {
		display: flex;
		flex-direction: column;
	}

	.a7 {
		font-size: 28upx;
	}

	.a8 {
		font-size: 22upx;
		color: darkgray;
	}

	.a9 {
		font-size: 22upx;
		color: darkgray;
	}
</style>
