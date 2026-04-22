<template>
	<view class="container">
		<!--		<view class="left-bottom-sign"></view>-->
		<!--		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>-->
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="left-top-sign">REGISTER</view>
			<view class="welcome">
				欢迎注册！
			</view>
			<!-- 登陆输入区域 -->
			<view style="width: 90%;margin-left:20px; margin-bottom: 30px;">
				<uni-forms ref="form" :value="user" labelWidth="0px" width="60%">
					<uni-forms-item name="username" label="">
						<view class="input-content">
							<view class="input-item">
								<uni-easyinput class="input" prefixIcon="person" v-model="user.username"
									placeholder="请输入用户名" />
							</view>
						</view>
					</uni-forms-item>
					<uni-forms-item name="password" label="">
						<view class="input-content">
							<view class="input-item">
								<uni-easyinput type="password" prefixIcon="locked" v-model="user.password"
									placeholder="请输入密码" />
							</view>
						</view>
					</uni-forms-item>
					<uni-forms-item name="confirmPassword" label="">
						<view class="input-content">
							<view class="input-item">
								<uni-easyinput type="password" prefixIcon="locked" v-model="user.confirmPassword"
									placeholder="请确认密码" />
							</view>
						</view>
					</uni-forms-item>
					<button class="confirm-btn" type="primary" @click="submit">提交</button>
					<view class="uni-flex uni-row " style="padding: 20px 40px;">
						<view class="flex-item register-section" style="width: 80%">
							<uni-breadcrumb separator="">
								<uni-breadcrumb-item>
									已有账号<text @click="toLogin">返回登陆</text>
								</uni-breadcrumb-item>
								<uni-breadcrumb-item>
									<text @click="haveLook">随便逛逛</text>
								</uni-breadcrumb-item>
							</uni-breadcrumb>
						</view>
					</view>
				</uni-forms>
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
	import {
		register
	} from "@/api/system/login"

	export default {
		data() {
			return {
				user: {
					username: "",
					password: "",
					confirmPassword: ""
				},
				rules: {
					username: {
						rules: [{
								required: true,
								errorMessage: '用户名不能为空'
							},
							{
								minLength: 2,
								maxLength: 20,
								errorMessage: '长度在 2 到 20 个字符'
							}
						]
					},
					password: {
						rules: [{
								required: true,
								errorMessage: '密码不能为空',
							},
							{
								minLength: 5,
								maxLength: 20,
								errorMessage: '长度在 5 到 20 个字符'
							}
						]
					},
					confirmPassword: {
						rules: [{
							required: true,
							errorMessage: '确认密码不能为空'
						}, {
							validateFunction: (rule, value, data) => data.password === value,
							errorMessage: '两次输入的密码不一致'
						}]
					}
				}
			}
		},
		onLoad() {

		},
		onReady() {
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			submit() {
				this.$refs.form.validate().then(res => {
					// console.log("this.user.password=", this.user.password)
					var data = {
						username: this.user.username,
						password: this.user.password
					};
					// console.log("注册-data=", JSON.stringify(data))
					register(data).then(res => {
						// 返回code==0或200时的处理
						// console.log("res=", res)
						// this.$api.msg('注册成功!请进行登陆。');
						uni.showModal({
							title: '注册完成',
							content: '注册成功!请进行登陆。',
							showCancel: false, // 不显示取消按钮
							success: (res) => {
								this.$tab.reLaunch('/pages/login')
							}
						});
					}).catch(error => {
						//  返回code!=0和200，或出现错误时的处理
						uni.showModal({
							title: '注册失败',
							content: error.message,
							showCancel: false, // 不显示取消按钮
						});
					})
				})
			},
			toLogin() {
				this.$tab.reLaunch('/pages/login')
				// this.$api.msg('去注册，暂未实现...');
			},
			haveLook() {
				this.$tab.reLaunch('/pages/index/index')
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
