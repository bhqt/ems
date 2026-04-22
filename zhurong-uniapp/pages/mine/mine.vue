<template>
    <view>
        <hx-navbar ref="hxnb" :config="config">
            <block slot="center">
                <view class="a4">
                    我的
                </view>
            </block>
        </hx-navbar>
        <view class="a2">
            <view class="avatar-wrapper">
                <view class="avatar-container" >
                    <image-show-list :images="avatarUrl" :size="160"  ></image-show-list>
                </view>
                <view class="avatar-edit-btn" @click.stop="changeAvatar" @tap.stop="changeAvatar">
                    <image :src="$iconUtil.getIconCommUrl('camera.svg')" class="avatar-upload-icon"></image>
                    <text class="edit-text">修改头像</text>
                </view>
            </view>
            <view class="a5">
                <view class="a6">{{ userInfo.nickName }}</view>
            </view>
        </view>
        <!--		<view class="a8">-->
        <!--			<image src="../../static/index/yingyong.svg" class="a9"></image>-->
        <!--			<image src="../../static/index/yingyong.svg" class="a9"></image>-->
        <!--		</view>-->
        <!--		<view class="b1">我的收藏</view>-->
        <!--		<view class="d3">-->
        <!--			<view class="d4" v-for="(item,index) in navarr" :key="index"   @click="navClick(item.page)">-->
        <!--				<image :src="item.icon" class="d5"></image>-->
        <!--				<view class="d6">{{item.title}}</view>-->
        <!--			</view>-->
        <!--		</view>-->
        <view class="b1">我的信息</view>
        <view class="d3">
            <view class="d4" v-for="(item,index) in setarr" :key="index" @click="navClick(item.page)">
                <image :src="iconReqUrl + item.icon" style="width: 60rpx;height: 60rpx;"></image>
                <view class="d6">{{ item.title }}</view>
            </view>
        </view>
        <view v-if="showSearchSvgIconButton" class="b1">查找图标</view>
        <view v-if="showSearchSvgIconButton" class="d3">
            <view class="d4" v-for="(item,index) in svgIconArr" :key="index" @click="navClick(item.page)">
                <image :src="iconReqUrl + item.icon" style="width: 60rpx;height: 60rpx;"></image>
                <view class="d6">{{ item.title }}</view>
            </view>
        </view>

        <!--		<view class="b1">我的申请</view>-->
        <!--		<view class="b3" v-for="(item,index) in messagearr" :key="index">-->
        <!--			<view class="b4">-->
        <!--				<image-->
        <!--					:src="item.icon"-->
        <!--					class="b5"></image>-->
        <!--				<view class="b6">-->
        <!--					<view class="b7">请假申请</view>-->
        <!--					<view class="b8">工作部门：系统维护 请假申请人：</view>-->
        <!--				</view>-->
        <!--			</view>-->
        <!--			<view class="b9">昨天</view>-->
        <!--		</view>-->

        <view class="a_button" style="padding: 20px 0px">
            <button type="warn" @click="toLogout">退出登录</button>
        </view>

    </view>
</template>

<script>
import config from '@/config'
import {
    mapState
} from 'vuex';
import {uploadImage} from '@/utils/commonUpload';
import IconCommon from "../../components/icon-comm/icon-comm";

export default {
    components: {IconCommon},
    data() {
        return {
            iconReqUrl: config.iconReqUrl,
            imageBaseUrl: config.baseUrl,
            showSearchSvgIconButton: config.showSearchSvgIconButton,
            userInfo: {},
            avatarUrl: [],
            config: {
                back: false,
                fixed: true,
                leftSlot: true,
                centerSlot: true,
                backgroundColor: [1, ['#CEF7E3', '#CEF7E3']],
                slideBackgroundColor: [1, ['#FFFFFF', '#FFFFFF']],
            },
            messagearr: [
                //     {
                // 	icon:'../../static/index/yingyong.svg'
                // },{
                // 	icon:'../../static/index/yingyong.svg'
                // },{
                // 	icon:'../../static/index/yingyong.svg'
                // }
            ],
            setarr: [{
                title: '设置',
                icon: 'user-cog.svg',
                page: '/pages/set/set'
            }],
            svgIconArr: [{
                title: '默认图标',
                icon: 'icons.svg',
                page: '/pages/test/commSvgImage'
            },{
                title: '系统图标',
                icon: 'icons.svg',
                page: '/pages/test/sysSvgImage'
            }
            ],
            navarr: [
                //         {
                // 	title: '区域管理',
                // 	icon: '../../static/index/schoolInfo.svg',
                // 	page: '/pages/iotArea/iotAreaList'
                // },
                // 	{
                // 		title: '设备管理',
                // 		icon: '../../static/index/score.svg',
                // 		page: '/pages/iotDevice/iotDeviceList'
                // 	},
                // 	{
                // 		title: '设备数据',
                // 		icon: '../../static/index/kecheng.svg',
                // 		page: '/pages/iotDeviceData/iotDeviceDataList'
                // 	},
                // 	{
                // 		title: '报警信息',
                // 		icon: '../../static/index/notice.svg',
                // 		page: '/pages/iotAlarmData/iotAlarmDataList'
                // 	}
            ],
        }
    },
    async onShow(options) {
        this.$http.user.getUserSimpleInfo(this.$store.getters.userId).then(res => {
            if (res && res.data && res.data.user) {
                this.userInfo = res.data.user
                // console.log("this.userInfo=" + JSON.stringify(this.userInfo))
                // console.log("this.userInfo.nickName=" + JSON.stringify(this.userInfo.nickName))
                // 设置头像URL
                if (this.userInfo.avatar) {
                    this.avatarUrl = [this.userInfo.avatar]
                }
            }
        })
    },
    onPageScroll(e) {
        // 重点，用到滑动切换必须加上
        this.$refs.hxnb.pageScroll(e);
    },
    methods: {
        //处理头像加载失败
        handleAvatarError(e) {

        },

        // 修改头像
        changeAvatar() {
            uni.showActionSheet({
                itemList: ['拍照', '从相册选择'],
                success: (res) => {
                    if (res.tapIndex === 0) {
                        // 拍照
                        this.chooseImage('camera');
                    } else if (res.tapIndex === 1) {
                        // 从相册选择
                        this.chooseImage('album');
                    }
                },
                fail: () => {
                    console.log('取消选择头像');
                }
            });
        },

        // 选择图片
        chooseImage(sourceType) {
            uni.chooseImage({
                count: 1,
                sizeType: ['compressed'],
                sourceType: [sourceType],
                success: (res) => {
                    const tempFilePath = res.tempFilePaths[0];
                    // 显示加载提示
                    uni.showLoading({
                        title: '上传中...'
                    });
                    // 上传头像
                    this.uploadAvatar(tempFilePath);
                },
                fail: (err) => {
                    console.error('选择图片失败:', err);
                    if (err.errMsg !== 'chooseImage:fail cancel') {
                        uni.showToast({
                            title: '选择图片失败',
                            icon: 'none'
                        });
                    }
                }
            });
        },

        // 上传头像
        uploadAvatar(filePath) {
            // 使用commonUpload.js中的uploadImage方法上传头像
            uploadImage(filePath, {
                name: 'file',
                formData: {
                    userId: this.$store.getters.userId
                },
                onProgress: (progress) => {
                    console.log(`上传进度: ${progress}%`);
                }
            }).then((result) => {
                if (result.code === 200 && result.data) {
                    // 上传成功，更新头像
                    // {
                    //     "code": 200,
                    //     "msg": "操作成功",
                    //     "data": {
                    //         "ossId": "1972567737590034433",
                    //         "url": "http://127.0.0.1:20080/autoee-iot-ems/profile/2025/09/29/86bf48f5f66843d5bc8d45154d750dbc/微信截图_20250307170831.png",
                    //         "fileName": "微信截图_20250307170831.png"
                    //     }
                    // }
                    if (result.data.ossId) {
                        this.avatarUrl = [result.data.ossId];
                        // 更新用户信息
                        if (result.data.ossId) {
                            // 修改个人信息
                            let updateData = {}
                            updateData.userId = this.$store.getters.userId
                            updateData.avatar = result.data.ossId
                            this.$http.user.updateUserSimpleInfo(updateData).then(res => {
                                this.$api.msg(`更新成功`);
                            }).catch(err => {
                                // 处理错误
                                // console.log("err=", err)
                                this.$api.msg(err.message);
                            });
                        }
                    }
                }
            }).catch((error) => {
                console.error('上传头像失败:', error);
            }).finally(() => {
                uni.hideLoading();
            });
        },
        //退出登录
        toLogout() {
            uni.showModal({
                content: '确定要退出登录么',
                success: (e) => {
                    if (e.confirm) {
                        this.$store.dispatch('LogOut').then(() => {
                            // console.log("退出登录=")
                            setTimeout(() => {
                                uni.reLaunch({
                                    url: "/pages/login"
                                })
                                // uni.navigateBack();
                            }, 200)
                        }).catch(() => {

                        })
                    }
                }
            });
        },
        navClick(page) {
            if (page === "/pages/find/find") {
                uni.switchTab({
                    url: page
                })
            } else {
                if (page) {
                    uni.navigateTo({
                        url: page
                    })
                } else {
                    this.$api.msg('暂未实现...');
                }
            }
        },
        set() {
            uni.navigateTo({
                url: '/pages/set/set'
            })
        }
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
		background-image: linear-gradient(#CEF7E3, #FFFFFF);
		height: 150upx;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		padding-left: 35upx;
		padding-right: 35upx;
	}

	.a3 {
		width: 50upx;
		height: 50upx;
	}

	.a4 {
		font-size: 34upx;
		font-weight: 700;
	}

	.a5 {
		display: flex;
		flex-direction: column;
	}

	.a6 {
		font-size: 40upx;
		font-weight: 700;
	}

	.a7 {
		font-size: 24upx;
		color: darkgray;
		margin-top: 10upx;
	}
.avatar-wrapper {
		display: flex;
		flex-direction: row;
		align-items: center;
		gap: 20upx;
	}

	.avatar-container >>> .image-show-list-item {
			height: 200upx;
			width: 200upx;
			border-radius: 100%;
			object-fit: contain;
			background-color: #f5f5f5;
			transition: transform 0.2s ease;
		}

			.avatar-container >>> .image-show-list {
				margin: 0;
				padding: 0;
			}

	.avatar-container {
		position: relative;
	}

	.avatar-container:active >>> .image-show-list-item {
		transform: scale(1.05);
	}

	.avatar-edit-btn {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 0;
		padding: 10upx 20upx;
		background-color: #f5f5f5;
		border-radius: 20upx;
		touch-action: manipulation;
	}

	.avatar-edit-btn:active {
		background-color: #e6e6e6;
	}

	.edit-text {
		font-size: 22upx;
		color: #333;
		margin-top: 5upx;
	}

	.avatar-upload-icon {
		width: 50upx;
		height: 50upx;
		fill: white;
	}

	.a8 {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		margin-top: 40upx;
		padding-left: 20upx;
		padding-right: 20upx;
	}

	.a9 {
		height: 160upx;
		width: 320upx;
		border-radius: 30upx;
	}

	.b3 {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		padding-left: 50upx;
		padding-right: 50upx;
		padding-top: 50upx;
	}

	.b4 {
		display: flex;
		flex-direction: row;
		align-items: center;
	}

	.b5 {
		width: 80upx;
		height: 80upx;
		margin-right: 15upx;
	}

	.b6 {
		display: flex;
		flex-direction: column;
	}

	.b7 {
		font-size: 28upx;
	}

	.b8 {
		font-size: 22upx;
		color: darkgray;
	}

	.b9 {
		font-size: 22upx;
		color: darkgray;
	}

	.b1 {
		font-size: 32upx;
		padding-left: 35upx;
		padding-top: 60upx;
		font-weight: 600;
		color: #333;
	}

	.d3 {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
	}


	.d4 {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 25%;
		margin-top: 30upx;
	}

	.d5 {
		height: 60upx;
		width: 60upx;
	}

	.d6 {
		font-size: 22upx;
		margin-top: 15upx;
	}
</style>
