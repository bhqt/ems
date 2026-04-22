<template>
  <div class="app-container">
    <!--查看危化品信息管理详细对话框 去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="pageData.title" :visible.sync="pageData.open" width="1000px" style="" append-to-body>
      <!-- 使用Element UI栅格系统优化布局 -->
      <el-form ref="detailFormRef" :model="pageData.detailForm" label-width="150px" style="padding-right: 30px">
        <el-row :gutter="20">
		        <el-col v-if="true" :span="12">
          <el-form-item label="危化品名称：" prop="dangerGoodsName">
              <div>{{ pageData.detailForm.dangerGoodsName }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="危化品类型：" prop="dangerGoodsType">
            <div>{{ pageData.detailForm.dangerGoodsTypeExtend }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="危化品状态：" prop="dangerGoodsStatus">
            <div>{{ pageData.detailForm.dangerGoodsStatusExtend }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="规格型号：" prop="specification">
              <div>{{ pageData.detailForm.specification }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="12">
          <el-form-item label="单位：" prop="goodsUnit">
            <div>{{ pageData.detailForm.goodsUnitExtend }}</div>
          </el-form-item>
        </el-col>
		        <el-col v-if="true" :span="24">
          <el-form-item label="存储位置：" prop="storageLocation">
              <div>{{ pageData.detailForm.storageLocation }}</div>
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
import dangerGoodsInfo from "@/api/autoee/dangerGoodsInfo";
import moment from 'moment';

export default {
  name: "detailDialog",
  dicts: ['danger_goods_type', 'goods_unit', 'sys_user', 'danger_goods_status', 'sys_dept'],

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
    openDangerGoodsInfoDetailDialog(row) {
      dangerGoodsInfo.selectDetailByPkDangerGoodsInfo(row.id).then(response => {
        this.pageData.detailForm = response.data;
        this.pageData.open = true;
        this.pageData.title = "查看危化品信息管理";
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
