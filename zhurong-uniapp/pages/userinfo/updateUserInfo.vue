<template>
	<view class="content">
		<view class="row b-b">
			<text class="tit">登录账号：</text>
			<uni-easyinput class="input" type="text" v-model="userInfo.userName" placeholder="请输入姓名"
				placeholder-class="placeholder"  disabled/>
		</view>
		<view class="row b-b">
			<text class="tit">昵称：</text>
			<uni-easyinput class="input" type="text" v-model="userInfo.nickName" placeholder="请输入姓名"
				placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">手机号码：</text>
			<uni-easyinput class="input" type="number" v-model="userInfo.phonenumber" placeholder="请输入手机号码"
				placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">邮箱：</text>
			<uni-easyinput class="input" type="text" v-model="userInfo.email" placeholder="请输入手机邮箱"
				placeholder-class="placeholder" />
			<!-- <text class="yticon icon-shouhuodizhi"></text> -->
		</view>
		<view class="row b-b">
			<text class="tit">性别：</text>
			<uni-data-select style="width: 100%;" v-model="userInfo.sex" :localdata="range" @change="change"></uni-data-select>
		</view>

		<button class="add-btn" @click="confirm">提交</button>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex';
	export default {
		data() {
			return {
				userInfo: {},
				range: [{
						value: "0",
						text: "男"
					},
					{
						value: "1",
						text: "女"
					}
				],
			}
		},
		onLoad(option) {
			let title = '修改个人资料';
			uni.setNavigationBarTitle({
				title
			})

            let userId = this.$store.getters.userId;
            if (userId){
                this.$http.user.getUserSimpleInfo(userId).then(res => {
                    console.log("getUserInfo res=" + JSON.stringify(res))
                    this.userInfo = res.data.user
                    // console.log("this.userInfo.sex=" + this.userInfo.sex)
                })
            } else{
                this.$modal.msgError("获取当前登录人信息失败！")
                // showToast("获取当前登录人信息失败！")
            }
		},
		mounted() {},
		computed: {
			...mapState(['user'])
		},
		methods: {
			change(e) {
				// console.log("e:", e);
			},
			switchChange(e) {
				// console.log("e.detail=", e.detail)
				this.userInfo.defaultFlag = e.detail.value
			},

			//地图选择地址
			chooseLocation() {
				uni.chooseLocation({
					success: (data) => {
						this.userInfo.address = data.name;
						this.userInfo.address = data.name;
					}
				})
			},

			//提交
			confirm() {
				let data = this.userInfo;
				if (!data.userName) {
					this.$api.msg('请填写姓名');
					return;
				}
				if (!/(^1[3|4|5|7|8][0-9]{9}$)/.test(data.phonenumber)) {
					this.$api.msg('请输入正确的手机号码');
					return;
				}
				// console.log("this.$store.getters.userId=", this.$store.getters.userId)
				data.userId = this.$store.getters.userId
				// console.log("data=", data)

				// 修改个人信息
				this.$http.user.updateUserSimpleInfo(data).then(res => {
					// console.log("res=", res);
					//this.$api.prePage()获取上一页实例，可直接调用上页所有数据和方法，在App.vue定义
					// this.$api.prePage().refreshList(data, this.manageType);
					this.$api.msg(`更新成功`);
					// setTimeout(()=>{
					// uni.navigateBack()
					// }, 800)
				}).catch(err => {
					// 处理错误
					// console.log("err=", err)
					this.$api.msg(err.message);
				});


			},
		}
	}
</script>

<style lang="scss">
	page {
		background: $page-color-base;
		padding-top: 16upx;
	}

	.row {
		display: flex;
		align-items: center;
		position: relative;
		padding: 0 30upx;
		height: 110upx;
		background: #fff;

		.tit {
			flex-shrink: 0;
			width: 160upx;
			font-size: 30upx;
			color: $font-color-dark;
		}

		.input {
			flex: 1;
			font-size: 30upx;
			color: $font-color-dark;
		}

		.icon-shouhuodizhi {
			font-size: 36upx;
			color: $font-color-light;
		}
	}

	.default-row {
		margin-top: 16upx;

		.tit {
			flex: 1;
		}

		switch {
			transform: translateX(16upx) scale(.9);
		}
	}

	.add-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		margin: 60upx auto;
		font-size: $font-lg;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}
</style>
