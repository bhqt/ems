<template>
	<view class="a_container">
		<uni-forms class="a_add_form" :model="pageData.addUpdateForm" ref="addUpdateFormRef"
		           label-position="left" label-width="100px" label-align="right">
			<uni-forms-item v-show="true" class="a_add_form_item" label="新闻版块" name="newsModule">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.newsModule" :localdata="pageData.dictData.news_module" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="新闻类型" name="newsType">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.newsType" :localdata="pageData.dictData.news_type" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="作者" name="author">
				<uni-easyinput v-model="pageData.addUpdateForm.author" placeholder="请输入作者" clearable/>
			</uni-forms-item>
			<uni-forms-item v-show="true" class="a_add_form_item" label="新闻状态" name="newsStatus">
				<uni-data-picker class="a_add_form_select" v-model="pageData.addUpdateForm.newsStatus" :localdata="pageData.dictData.news_status" placeholder="请选择" clearable>
				</uni-data-picker>
			</uni-forms-item>
		</uni-forms>
		<view class="a_button">
			<button type="primary" @click="submitForm">提交</button>
		</view>

	</view>
</template>

<script>
import config from '@/config'
import {mapState} from 'vuex';
import {getDictData} from '@/utils/dict'
import newsManage from '@/api/autoee/newsManage';

export default {
	data() {
		return {
			// 所有需要响应式的参数都要初始化，否则Vue 无法追踪这个属性的变化
			pageData: {
				imageBaseUrl: config.baseUrl,
				userId: this.$store.getters.userId,
				addUpdateForm: {
					id: "",
					newsModule: "",
					newsType: "",
					newsTitle: "",
					newsContent: "",
					newsImage: "",
					author: "",
					newsStatus: "",
				},
                open: false,
                title: "",
                loading: false,

                addOrUpdate: "add",
                addUpdateFormRules: {
				// 新闻版块
				newsModule: {
					rules: [
					{ required: true, errorMessage: "新闻版块不能为空", trigger: "change" },
				]
				},
				// 新闻类型
				newsType: {
					rules: [
					{ required: true, errorMessage: "新闻类型不能为空", trigger: "change" },
				]
				},
				// 新闻标题
				newsTitle: {
					rules: [
					{ required: true, errorMessage: "新闻标题不能为空", trigger: "blur" },
				]
				},
				// 新闻内容
				newsContent: {
					rules: [
				]
				},
				// 新闻图片
				newsImage: {
					rules: [
				]
				},
				// 作者
				author: {
					rules: [
				]
				},
				// 新闻状态
				newsStatus: {
					rules: [
					{ required: true, errorMessage: "新闻状态不能为空", trigger: "change" },
				]
				},
				// 创建者
				createBy: {
					rules: [
				]
				},
				// 创建时间
				createTime: {
					rules: [
				]
				},
				// 更新者
				updateBy: {
					rules: [
				]
				},
				// 更新时间
				updateTime: {
					rules: [
				]
				},
				// 删除标志
				delFlag: {
					rules: [
				]
				},
				// 删除者
				delBy: {
					rules: [
				]
				},
				// 删除时间
				delTime: {
					rules: [
				]
				}
                },
				dictData: {
					news_module: [],
					news_type: [],
				}
			}
		}
	},
	onReady() {
		// 需要在onReady中设置规则
		this.$refs.addUpdateForm.setRules(this.pageData.addUpdateFormRules)
	},
  	onLoad(options) {
	  	console.log('页面参数:', options)
    	this.initData()
	    let title = "新增内容管理"
	    if (options.id){
			title = "修改内容管理"
			this.pageData.addOrUpdate = "update"
	  		this.loadData(options.id);
		}
		uni.setNavigationBarTitle({
			title
		})
	},
	onShow() {
		console.log('页面显示')
    },
	mounted(){

	},
	computed: {
		...mapState(['user'])
	},
	methods: {
		async initData() {
		 	try {
				this.pageData.dictData.news_module = await getDictData('news_module')
				this.pageData.dictData.news_type = await getDictData('news_type')
			} catch (error) {
				console.error('获取字典失败:', error)
			}
		},
	  	//请求数据
		loadData(id){
			let data = this.pageData.addUpdateForm
        	newsManage.selectDetailByPkNewsManage(id).then(res => {
            	this.pageData.addUpdateForm= res.data;
        	})
	    },
		/** 提交按钮 */
		submitForm() {
			uni.showLoading()
			this.$refs.addUpdateFormRef.validate().then(res => {
     			if (this.pageData.addUpdateForm.id != null) {
					// 修改内容管理：只能用于前端form表单的更新操作，清空的字段回写为null
     			  newsManage.updateNullValueByNewsManage(this.pageData.addUpdateForm).then(response => {
     			    	this.$api.msg(`修改成功`);
						uni.navigateBack()
     			  });
     			} else {
     			  newsManage.addNewsManage(pageData.addUpdateForm).then(response => {
     			    	this.$api.msg(`新增成功`);
						uni.navigateBack()
     			  });
     			}
			}).catch(err => {
				uni.hideLoading()
				console.log('表单错误信息：', err);
			})
		}
	}
}
</script>

// style中样式放入单独文件中，避免格式化代码后导致样式失效问题
<style lang="scss">
//@import '@/static/styles/autoee/newsManage/newsManageAdd.scss';
</style>
