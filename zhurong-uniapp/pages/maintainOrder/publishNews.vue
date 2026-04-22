<template>
	<view class="container">
		<view class="example">
			<uni-forms ref="baseForm" :rules="rules" :model="newsInfo" labelWidth="80px">
				<uni-forms-item label="板块" required name="newsModule">
					<!-- 	<uni-data-select v-model="newsInfo.newsModule" :localdata="range"
						@change="change"></uni-data-select> -->

					<uni-data-picker v-model="newsInfo.newsModule" :localdata="range" popup-title="选择板块">
					</uni-data-picker>
				</uni-forms-item>
				<uni-forms-item label="标题" required name="newsTitle">
					<uni-easyinput class="input" type="text" v-model="newsInfo.newsTitle" placeholder="请输入标题"
						placeholder-class="placeholder" />
				</uni-forms-item>
				<uni-forms-item label="内容" required name="newsContent">
					<uni-easyinput type="textarea" v-model="newsInfo.newsContent" placeholder="请输入内容"></uni-easyinput>
				</uni-forms-item>
				<uni-forms-item label="图片">
					<uni-file-picker v-model="newsInfo.filePickerImageArr" fileMediatype="image" file-extname="png,jpg"
						mode="grid" :auto-upload="true" @select="select" @delete="deleteFlie" @progress="progress"
						@success="success" @fail="fail" />
					<!-- <button @click="upload">上传文件</button> -->
				</uni-forms-item>
				<!-- <uni-forms-item label="日期时间">
					<uni-datetime-picker type="datetime" return-type="timestamp" v-model="newsInfo.datetimesingle" />
				</uni-forms-item>
				<uni-forms-item label="选择技能">
					<uni-data-select v-model="newsInfo.skills" :localdata="range">
					</uni-data-select>
				</uni-forms-item> -->
				<button type="primary" @click="submit('baseForm')">提交</button>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	import upload from '@/utils/upload'
	import {
		mapState
	} from 'vuex';
	export default {
		data() {
			return {
				newsInfo: {
					newsModule: '',
					newsTitle: '',
					newsContent: '',
					filePickerImageArr: [],
					newsImageArr: [],
					newsImage: '',
					newsType: '210',
					newsStatus: '220',
				},
				// 校验规则
				rules: {
					newsModule: {
						rules: [{
							required: true,
							errorMessage: '板块不能为空'
						}]
					},
					newsTitle: {
						rules: [{
							required: true,
							errorMessage: '标题不能为空'
						}]
					},
					newsContent: {
						rules: [{
							required: true,
							errorMessage: '内容不能为空'
						}]
					},
				},
				range: [],
			}
		},
		onLoad(option) {
			let title = '发布文章';
			uni.setNavigationBarTitle({
				title
			})
			this.loadNewsModule()
		},
		mounted() {},
		computed: {
			...mapState(['user'])
		},
		methods: {
			// 获取新闻版块
			loadNewsModule() {
				let data2 = {
					dictType: "ems_news_module"
				}
				this.$http.cart.getDictDataByType(data2).then(res2 => {
					let datalist = res2.data
					this.range = []
					for (var j = 0; j < datalist.length; j++) {
						if (datalist[j].dictLabel === '推荐' || datalist[j].dictLabel === '关注' || datalist[j]
							.dictLabel === '热榜') {
							// 推荐等几个选项不在下拉框中显示
						} else {
							var item = {}
							item.value = datalist[j].dictValue
							item.text = datalist[j].dictLabel
							item.selected = true
							item.selected = false
							this.range.push(item)
						}
					}
				})
			},
			// 获取选择的文件信息，并上传到后端
			select(e) {
				console.log('选择文件：', e)
				var tempFilePaths = e.tempFilePaths
				for (var i = 0; i < tempFilePaths.length; i++) {
					this.uploadFile(tempFilePaths[i])
				}
			},
			// 删除选择的文件
			deleteFlie(e) {
				console.log('删除选择的文件：', e)
				var tempFilePath = e.tempFilePath
				console.log('删除选择的文件临时路径：', tempFilePath)
				// 将删除的文件从数组中移除
				for (var i = 0; i < this.newsInfo.newsImageArr.length; i++) {
					if (this.newsInfo.newsImageArr[i].tempFilePath === tempFilePath) {
						this.newsInfo.newsImageArr.splice(i, 1); // 从索引 i 处移除一个元素
						break; // 如果只想移除一个匹配项，可以在这里退出循环
					}
				}
				console.log("当前选择的文件信息=" + JSON.stringify(this.newsInfo.newsImageArr));
			},
			// 获取上传进度
			progress(e) {
				console.log('上传进度：', e)
			},
			// 上传成功
			success(e) {
				console.log('上传成功')
			},
			// 上传失败
			fail(e) {
				console.log('上传失败：', e)
			},

			uploadFile(tempFilePath) {
				console.log(this.newsInfo.filePickerImageArr);
				// 用于传递其他参数信息
				const formData = {
					// 其他需要传递的参数，如 token 等
					// user: 'admin',
				};
				// 调用封装的公共方法
				upload({
					url: '/common/upload',
					name: 'file', // 后端接收文件的字段名
					filePath: tempFilePath,
					// header: {
					// 	'Content-Type': 'multipart/form-data'
					// },
					// formData: formData
				}).then(res => {
					var fileName = res.fileName;
					// 将返回的文件名放入自己的数组中，待提交时使用
					var fileObj = {
						fileName: fileName,
						tempFilePath: tempFilePath // 用于删除时进行查找
					}
					this.newsInfo.newsImageArr.push(fileObj)
					console.log("当前选择的文件信息=" + JSON.stringify(this.newsInfo.newsImageArr));
				}).catch(err => {
					// 处理错误
					// console.log("err=", err)
					this.$api.msg(err.message);
				});
			},

			//提交
			submit(ref) {
				// 将自己设置的文件名称数组转为字符串放入对象，保证到后台的对应字段中
				for (var i = 0; i < this.newsInfo.newsImageArr.length; i++) {
					if (i == 0) {
						this.newsInfo.newsImage = this.newsInfo.newsImageArr[i].fileName
					} else {
						this.newsInfo.newsImage = this.newsInfo.newsImage + "," + this.newsInfo.newsImageArr[i].fileName
					}
				}

				console.log(this.newsInfo);
				this.$refs[ref].validate().then(res => {
					let data = this.newsInfo;

					this.$http.cart.addNewsInfo(data).then(res => {
						// console.log("res=", res); 
						this.$api.msg(`发布成功`);
						this.newsInfo = {
							newsModule: '',
							newsTitle: '',
							newsContent: '',
							filePickerImageArr: [],
							newsImageArr: [],
							newsImage: '',
							newsType: '210',
							newsStatus: '220',
						}
					}).catch(err => {
						// 处理错误
						// console.log("err=", err)
						this.$api.msg(err.message);
					});


				}).catch(err => {
					console.log('err', err);
				})


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

	.example {
		padding: 15px;
		background-color: #fff;
	}

	.segmented-control {
		margin-bottom: 15px;
	}

	.button-group {
		margin-top: 15px;
		display: flex;
		justify-content: space-around;
	}

	.form-item {
		display: flex;
		align-items: center;
		flex: 1;
	}

	.button {
		display: flex;
		align-items: center;
		height: 35px;
		line-height: 35px;
		margin-left: 10px;
	}
</style>