<template>
  <div class="app-container">
    <!--查看物品入库记录详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- 使用Element UI栅格系统优化布局 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-row :gutter="20">
		        <el-col v-if="true" :span="12">
          <el-form-item label="入库编号：" prop="id">
              <div>{{ pageData.detailForm.id }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="物品名称：" prop="goodsId">
            <div >
              <dict-tag :options="dict.type.a_goods_info" :value="pageData.detailForm.goodsId" :show-css="false"/>
            </div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="入库数量：" prop="quantity">
              <div>{{ pageData.detailForm.quantity }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="存放地点：" prop="supplier">
              <div>{{ pageData.detailForm.supplier }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="操作人员：" prop="userId">
            <div >
              <dict-tag :options="dict.type.sys_user" :value="pageData.detailForm.userId" :show-css="false"/>
            </div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="24">
          <el-form-item label="备注：" prop="remark">
            <div>{{ pageData.detailForm.remark }}</div>
          </el-form-item>
        </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import goodsStockIn from "@/api/autoee/goodsStockIn";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['a_goods_info', 'sys_user', 'sys_dept'],

  data() {
    return {
      pageData: {
        open: false,
        title: "",
        vueAppBaseApi: process.env.VUE_APP_BASE_API,
        detailForm: {}
      },
    };
  },

  methods: {
    /** 打开详细窗口 */
    openGoodsStockInDetailDialog(row) {
      goodsStockIn.selectDetailByPkGoodsStockIn(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看物品入库记录";
      });
    },

    // 关闭按钮
    cancel() {
      this.pageData.open = false;
    }
  }
};
</script>

<style>
</style>
