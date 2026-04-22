<script>
import config from './config'
import store from '@/store'
import { getToken } from '@/utils/auth'

export default {
	onLaunch: function () {
		this.initApp()
	},
	methods: {
		// 初始化应用
		initApp() {
			// 初始化应用配置
			this.initConfig()
			// 检查用户登录状态
			//#ifdef H5
			// this.checkLogin()
			//#endif
		},
		initConfig() {
			this.globalData.config = config
		},
		checkLogin() {
			if (!getToken()) {
				this.$tab.reLaunch('/pages/login')
			}
		}
	}
}
</script>


<style lang='scss'>
@import '@/common/uni-ui.scss';

view,
scroll-view,
swiper,
swiper-item,
cover-view,
cover-image,
icon,
text,
rich-text,
progress,
button,
checkbox,
form,
input,
label,
radio,
slider,
switch,
textarea,
navigator,
audio,
camera,
image,
video {
	box-sizing: border-box;
}

/* 骨架屏替代方案 */
.Skeleton {
	background: #f3f3f3;
	padding: 20upx 0;
	border-radius: 8upx;
}



.clamp {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	display: block;
}

.common-hover {
	background: #f5f5f5;
}

/*边框*/
.b-b:after,
.b-t:after {
	position: absolute;
	z-index: 3;
	left: 0;
	right: 0;
	height: 0;
	content: '';
	transform: scaleY(.5);
	border-bottom: 1px solid $border-color-base;
}

.b-b:after {
	bottom: 0;
}

.b-t:after {
	top: 0;
}

/* button样式改写 */
uni-button,
button {
	height: 80upx;
	line-height: 80upx;
	font-size: $font-lg + 2upx;
	font-weight: normal;

	&.no-border:before,
	&.no-border:after {
		border: 0;
	}
}

uni-button[type=default],
button[type=default] {
	color: $font-color-dark;
}

/* input 样式 */
.input-placeholder {
	color: #999999;
}

.placeholder {
	color: #999999;
}
</style>
