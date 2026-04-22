<template>
	<view class="container">
		<!--		<view class="left-bottom-sign"></view>-->
		<!--		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>-->
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="left-top-sign">LOGIN</view>
			<view class="welcome">
				欢迎回来！
			</view>
			<!-- 登陆输入区域 -->
			<!--	账号登陆 -->
			<!--	 分段器	-->
			<uni-segmented-control :current="currentTabIndex" :values="tabNameitems" @clickItem="onClickItem"
				styleType="text" activeColor="#007aff"
				style="width: 50%;margin-left:30px; margin-bottom: 30px;"></uni-segmented-control>
			<view v-if="currentTabIndex === 0">
				<view class="input-content">
					<view class="input-item">
						<uni-easyinput class="input" prefixIcon="person" v-model="loginForm.username"
							placeholder="请输入用户名 "></uni-easyinput>
					</view>
                    <view style="height: 20px;"></view>
					<view class="input-item">
						<uni-easyinput class="input" type="password" prefixIcon="locked" v-model="loginForm.password"
							placeholder="请输入密码"></uni-easyinput>
					</view>
				</view>
				<button class="confirm-btn" @click="toLogin" :disabled="logining">登录</button>
<!--				<view class="uni-flex uni-row " style="padding-top: 20px;padding-left: 44px;padding-right: 40px;">-->
<!--					<view class="flex-item register-section">-->
<!--						<uni-breadcrumb separator="">-->
<!--							<uni-breadcrumb-item>-->
<!--								还没有账号?<text @click="toRegist">立即注册</text>-->
<!--							</uni-breadcrumb-item>-->
<!--							<uni-breadcrumb-item>-->
<!--								<text @click="forgetPassword">忘记密码?</text>-->
<!--							</uni-breadcrumb-item>-->
<!--						</uni-breadcrumb>-->
<!--					</view>-->
<!--				</view>-->
<!--				<view class="uni-flex uni-row " style="padding-top: 20px;padding-left: 40px;padding-right: 40px;">-->
<!--					<view class="flex-item register-section">-->
<!--						<uni-breadcrumb separator="">-->
<!--							<uni-breadcrumb-item>-->
<!--								<text @click="haveLook">随便逛逛</text>-->
<!--							</uni-breadcrumb-item>-->
<!--						</uni-breadcrumb>-->
<!--					</view>-->
<!--				</view>-->
			</view>

			<view v-if="currentTabIndex === 1">
				<view class="input-content">
					<view class="input-item">
						<uni-easyinput class="input" prefixIcon="phone" v-model="loginForm.phoneNo"
							placeholder="请输入手机号码 "></uni-easyinput>
					</view>
				</view>
				<button class="confirm-btn" @click="toLogin" :disabled="logining">登录</button>
					<view class="uni-flex uni-row " style="padding-top: 20px;padding-left: 44px;padding-right: 40px;">
					<view class="flex-item register-section">
						<uni-breadcrumb separator="">
							<uni-breadcrumb-item>
								还没有账号?<text @click="toRegist">立即注册</text>
							</uni-breadcrumb-item>
							<uni-breadcrumb-item>
								<text @click="forgetPassword">忘记密码?</text>
							</uni-breadcrumb-item>
						</uni-breadcrumb>
					</view>
				</view>
				<view class="uni-flex uni-row " style="padding-top: 20px;padding-left: 40px;padding-right: 40px;">
					<view class="flex-item register-section">
						<uni-breadcrumb separator="">
							<uni-breadcrumb-item>
								<text @click="haveLook">随便逛逛</text>
							</uni-breadcrumb-item>
						</uni-breadcrumb>
					</view>
				</view>
			</view>


			<view class="login-bottom-box">
<!--				<view style="margin-bottom: 10px">-->
<!--					<text style="width: 100%;color: #07a7a7;font-size: x-small">-&#45;&#45; 更多登录方式 -&#45;&#45;</text>-->
<!--				</view>-->
<!--				<view style="margin-bottom: 20px">-->
<!--					<text class="icon yticon icon-weixin" style="font-size: xx-large;color:green;margin-right: 30px"-->
<!--						@click="loginByWecat"></text>-->
<!--					<text class="icon yticon icon-dianhua-copy" style="font-size: xx-large;color:green"-->
<!--						@click="loginByPhoneNo"></text>-->
<!--				</view>-->

				<!--			显示图标的可用方法-->
				<!--			<uni-icons type="contact" size="30" color="#0089FF"></uni-icons>-->
				<!--			<uni-icons custom-prefix="yticon" type="icon-yiguoqi1" size="30" color="#0089FF"></uni-icons>-->
				<!--			<text class="icon yticon icon-alipay"></text>-->
			</view>
		</view>


	</view>


</template>

<script>
	import {
		mapMutations
	} from 'vuex';
	import config from '@/config';
	// import { log } from 'console';

	export default {
		data() {
			return {
				tabNameitems: ['用户名'],
				// tabNameitems: ['用户名', '手机号'],
				currentTabIndex: 0,

				mobile: '',
				password: '',
				logining: false,
				loginForm: {
					username: "",
					password: "",
					phoneNo: "",
					code: "",
					uuid: ''
				}
			}
		},
		onLoad() {
			// console.log("开始-onload")
			/* #ifdef APP-PLUS */
			// 默认使用手机号码一键登录
			// this.toLoginByPhoneNo();
			// console.log("完成-toLoginByPhoneNo")
			/* #endif */
		},
		methods: {
			onClickItem(e) {
				if (this.currentTabIndex != e.currentIndex) {
					this.currentTabIndex = e.currentIndex;
				}
			},

			inputChange(e) {
				const key = e.currentTarget.dataset.key;
				this[key] = e.detail.value;
			},
			navBack() {
				uni.navigateBack();
			},
			toRegist() {
				this.$tab.reLaunch('/pages/register')
				// this.$api.msg('去注册，暂未实现...');
			},
			haveLook() {
				this.$tab.reLaunch('/pages/index/index')
			},
			forgetPassword() {
				this.$api.msg('忘记密码，暂未实现...');
			},
			changeLoginType() {
				this.usePhoneNoLoginFlag = false;
			},
			// 登录方法
			async toLogin() {
				if (this.loginForm.username === "") {
					this.$modal.msgError("请输入您的账号")
				} else if (this.loginForm.password === "") {
					this.$modal.msgError("请输入您的密码")
				} else if (this.loginForm.code === "" && this.captchaEnabled) {
					this.$modal.msgError("请输入验证码")
				} else {
					this.$modal.loading("登录中，请耐心等待...")
					this.pwdLogin()
				}
			},
			// 用户名密码密码登录
			async pwdLogin() {
				this.$store.dispatch('LoginByUsernameAndPassword', this.loginForm).then(() => {
					this.$modal.closeLoading()
					this.loginSuccess()
				}).catch((e) => {
					console.log("e=", e)
					this.$modal.msgError(e.message)
					if (this.captchaEnabled) {
						// this.getCode()
					}
				})
			},

			// 登录成功后，处理函数
			loginSuccess(result) {
				// 设置用户信息
				// console.log("开始通过GetInfo获取用户信息")
				this.$store.dispatch('GetInfo').then(res => {
					this.$tab.reLaunch('/pages/index/index')
					uni.closeAuthView();
				})
			},
			loginByWecat() {
				this.$api.msg('微信登陆，暂未实现...');
			},
			// 手机号码一键登录
			async loginByPhoneNo(phoneNo) {
				this.$store.dispatch('LoginByPhoneNo', phoneNo).then(() => {
					this.$modal.closeLoading()
					this.loginSuccess()
				}).catch(() => {
					if (this.captchaEnabled) {
						// this.getCode()
					}
				})
			},
			async toLoginByPhoneNo() {
				// 调用手机号码一键登录
				const phoneNo = await new Promise((resolve, reject) => {
					// uni.login非异步，所以用Promise包装
					uni.login({
						provider: 'univerify',
						univerifyStyle: {
							fullScreen: false
						},
						success(res) { // 登录成功
							// console.log("一键登录成功：", res.authResult); // {openid:'登录授权唯一标识',access_token:'接口返回的 token'}
							// 在得到access_token后，通过callfunction调用云函数获取
							uniCloud.callFunction({
								name: 'getPhoneNumber', // 你的云函数名称
								data: {
									'access_token': res.authResult
										.access_token, // 客户端一键登录接口返回的access_token
									'openid': res.authResult.openid // 客户端一键登录接口返回的openid
								}
							}).then(res2 => {
								// res.result = {
								//   code: '',
								//   message: ''
								// }
								// 登录成功，可以关闭一键登录授权界面了
								// console.log("获取手机号码成功：", res2);
								// console.log("获取手机号码成功：phoneNumber", res2.result.phoneNumber);
								resolve(res2.result.phoneNumber);
							}).catch(err => {
								// 处理错误
								uni.showModal({
									title: '手机号码一键登录失败，请使用其他方式进行登录',
									content: '具体错误信息：' + err,
									showCancel: false, // 不显示取消按钮
									success: function(res) {
										uni.closeAuthView();
									}
								});
								// console.log("调用云函数getPhoneNumber失败",err); // {openid:'登录授权唯一标识',access_token:'接口返回的 token'}
								reject(err);
							})


						},
						fail(res) { // 登录失败
							if (res.errCode !== 30003 && res.errCode !== 30002) { // 用户手动关闭或切换登陆方式时不提示
								uni.showModal({
									title: '提示',
									content: '手机号码一键登录失败，请使用其他方式进行登录。具体错误信息：' + res.errMsg,
									showCancel: false, // 不显示取消按钮
									complete: function(res) {
										uni.closeAuthView();
									}
								});

							}
							// console.log("一键登录失败：" + res.errCode + ":" + res.errMsg)
						}
					});
				});

				// console.log("phoneNo=", phoneNo)
				// console.log("开始-调用后台手机号码一键登录接口")
				this.$store.dispatch('LoginByPhoneNo', phoneNo).then((res) => {
					// // console.log("res=", res)
					// console.log("res.token=", res.token)
					// console.log("手机号码一键登录成功")
					this.loginSuccess()
					// uni.closeAuthView();
				}).catch((err) => {
					// console.log("err=", err)
					uni.showModal({
						title: '提示',
						content: err,
						showCancel: false,
						success: function(res) {
							uni.closeAuthView();
						}
					});
				})



			},



		},

	}
</script>

<style lang='scss'>
	page {
		background: #fff;
	}

	.login-bottom-box {
		position: fixed;
		bottom: 40rpx;
		text-align: center;
		width: 100%;
	}

	.container {
		padding-top: 115px;
		position: relative;
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #fff;
	}

	.wrapper {
		position: relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;
	}

	.back-btn {
		position: absolute;
		left: 40upx;
		z-index: 9999;
		padding-top: var(--status-bar-height);
		top: 40upx;
		font-size: 40upx;
		color: $font-color-dark;
	}

	.left-top-sign {
		font-size: 120upx;
		color: $page-color-base;
		position: relative;
		left: -16upx;
	}

	.right-top-sign {
		position: absolute;
		top: 80upx;
		right: -30upx;
		z-index: 95;

		&:before,
		&:after {
			display: block;
			content: "";
			width: 400upx;
			height: 80upx;
			background: #b4f3e2;
		}

		&:before {
			transform: rotate(50deg);
			border-radius: 0 50px 0 0;
		}

		&:after {
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}

	.left-bottom-sign {
		position: absolute;
		left: -270upx;
		bottom: -320upx;
		border: 100upx solid #d0d1fd;
		border-radius: 50%;
		padding: 180upx;
	}

	.welcome {
		position: relative;
		left: 50upx;
		top: -90upx;
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0, 0, 0, .3);
	}

	.input-content {
		padding: 0 60upx;
	}

	.input-item {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: center;
		padding: 0 30upx;
		//background:$page-color-light;
		height: 80upx;
		border-radius: 4px;
		margin-bottom: 50upx;

		&:last-child {
			margin-bottom: 0;
		}

		.tit {
			height: 50upx;
			line-height: 56upx;
			font-size: $font-sm+2upx;
			color: $font-color-base;
		}

		input {
			height: 60upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			width: 100%;
		}
	}

	.confirm-btn {
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		background: $uni-color-primary;
		color: #fff;
		font-size: $font-lg;

		&:after {
			border-radius: 100px;
		}
	}

	.default-btn {
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		//background: $uni-color-success;
		//color: #fff;
		font-size: $font-lg;

		&:after {
			border-radius: 100px;
		}
	}

	.forget-section {
		font-size: $font-sm+2upx;
		color: $font-color-spec;
		text-align: center;
		margin-top: 40upx;
	}

	.register-section {
		//position: absolute;
		left: 0;
		bottom: 50upx;
		//width: 100%;
		font-size: $font-sm+2upx;
		color: $font-color-base;
		text-align: left;

		text {
			color: $font-color-spec;
			margin-left: 10upx;
		}
	}
</style>
