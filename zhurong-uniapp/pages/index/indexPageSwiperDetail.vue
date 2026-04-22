<template>
	<view class="container">
		<view class="a_swiper">
			<!--	 图片轮播图		-->
			<swiper circular autoplay interval="3000" duration="500" indicator-dots="true">
				<swiper-item class="a_swiper_item" v-for="(item,index) in imgList" :key="index">
					<view class="a_image_wrapper">
						<image :src="item" class="loaded" mode="aspectFill"></image>
					</view>
				</swiper-item>
			</swiper>
		</view>
		<!-- 基本信息	-->
		<view class="">
			<view style="padding: 0 30upx;">
				<text class=""
				      style="font-size:  large;font-weight: bold;color: black">{{ indexPageSwiper.title }}
				</text>
			</view>
			<view style="padding: 0 30upx;">
				<uni-icons type="contact-filled" size="16" color="#999"></uni-icons>
				<text class="uni-footer-text" style="font-weight: bold; font-size: 14">{{ indexPageSwiper.createBy }}</text>
				<uni-icons style="padding-left: 10px" type="cloud-upload-filled" size="16" color="#999"></uni-icons>
				<text class="uni-footer-text" style="font-weight: bold; font-size: 14"> {{ indexPageSwiper.createTime }}</text>
			</view>
		</view>
		<view style="padding: 10upx 30upx;">

			<!--#ifdef APP-PLUS || H5-->
			<u-parse :content="indexPageSwiper.detailContent" @preview="preview" @navigate="navigate"></u-parse>
			<!--#endif-->
			<!--#ifdef MP-->
			<rich-text :nodes="indexPageSwiper.detailContent"></rich-text>
			<!--#endif-->
		</view>


	</view>
</template>

<script>
import config from '@/config'
import indexPageSwiper from '@/api/autoee/indexPageSwiper';

export default {
	data() {
		return {
			imageBaseUrl: config.baseUrl,
			indexPageSwiper: {},
			imgList: [],
		};
	},
	async onLoad(options) {
		this.imageBaseUrl = config.baseUrl
		//接收传值,id里面放的是标题，因为测试数据并没写id
		let id = options.id;
		this.imgList = [];
		indexPageSwiper.selectDataByPkIndexPageSwiper(id).then(res => {
			this.indexPageSwiper = res.data;
			let productImagesArr = this.indexPageSwiper.detailImages.split(",")
			console.log("config.baseUrl=", config.baseUrl)
			for (let i = 0; i < productImagesArr.length; i++) {
				this.imgList[i] = config.baseUrl + productImagesArr[i]
				console.log("this.imgList[i] =", this.imgList[i])
			}
		})
	},
	methods: {},

}
</script>

<style lang='scss'>
/* 标题简介 */
	.introduce-section {
		background: #fff;
		padding: 20 upx 30 upx;

		.title {
			font-size: 32 upx;
			color: $font-color-dark;
			height: 50 upx;
			line-height: 50 upx;
		}

		.price-box {
			display: flex;
			align-items: baseline;
			height: 64 upx;
			padding: 10 upx 0;
			font-size: 26 upx;
			color: $uni-color-primary;
		}
	}

</style>
