<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('chargingModule.merchant.name')" prop="name">
        <el-input v-model="queryParams.name" :placeholder="$t('placeholder.input') + $t('chargingModule.merchant.name')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.merchant.contact')" prop="contact">
        <el-input v-model="queryParams.contact" :placeholder="$t('placeholder.input') + $t('chargingModule.merchant.contact')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:merchant:add']">{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:merchant:edit']">{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:merchant:remove']">{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:merchant:export']">{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="merchantList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('chargingModule.merchant.name')" align="center" prop="name" />
      <el-table-column :label="$t('chargingModule.merchant.type')" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.merchant_type" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.merchant.contact')" align="center" prop="contact" />
      <el-table-column :label="$t('chargingModule.merchant.address')" align="center" prop="avatar" />
      <el-table-column :label="$t('chargingModule.merchant.status')" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
            @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('common.action')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-setting" @click="handleManage(scope.row)"
            v-hasPermi="['system:merchant:edit']">{{ $t('chargingModule.merchant.managePile') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:merchant:edit']">{{ $t('common.edit') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:merchant:remove']">{{ $t('common.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改商户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="$t('chargingModule.merchant.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('placeholder.input') + $t('chargingModule.merchant.name')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.merchant.contact')" prop="contact">
          <el-input v-model="form.contact" :placeholder="$t('placeholder.input') + $t('chargingModule.merchant.contact')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.merchant.address')" prop="avatar">
          <el-input v-model="form.avatar" :placeholder="$t('placeholder.input') + $t('chargingModule.merchant.address')" />
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('placeholder.input') + $t('common.remark')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('common.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
<script>
import { listMerchant, getMerchant, delMerchant, addMerchant, updateMerchant } from "@/api/chargingStation/merchant";

export default {
  name: "Merchant",
  dicts: ['merchant_type'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示查询条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商户信息表格数据
      merchantList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        contact: undefined,
        avatar: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        merchantId: [
          { required: true, message: () => this.$t('validation.required', { field: 'ID' }), trigger: "blur" }
        ],
        name: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.merchant.name') }), trigger: "blur" }
        ],
        type: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.merchant.type') }), trigger: "change" }
        ],
        contact: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: () => this.$t('validation.phone'),
            trigger: "blur"
          }
        ],
        avatar: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.merchant.address') }), trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 商户状态开关状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? this.$t('common.enable') : this.$t('common.disable');
      this.$modal.confirm(this.$t('chargingModule.merchant.statusConfirm', { text })).then(() => {
        return updateMerchant(row);
      }).then(() => {
        this.$modal.msgSuccess(this.$t('common.success'));
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 查询商户信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.type = '1';
      listMerchant(this.queryParams).then(response => {
        this.merchantList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        merchantId: undefined,
        name: undefined,
        type: undefined,
        contact: undefined,
        avatar: undefined,
        status: undefined,
        delFlag: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 查询按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.merchantId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('chargingModule.merchant.addTitle');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const merchantId = row.merchantId || this.ids
      getMerchant(merchantId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = this.$t('chargingModule.merchant.editTitle');
      });
    },
        /** 管理电桩按钮操作 */
        handleManage(row){
      const merchantId = row.merchantId;
      this.$router.push("/charging-station/pile/connected/" + merchantId);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.merchantId != null) {
            updateMerchant(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.type = '1';
            addMerchant(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.addSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const merchantIds = row.merchantId || this.ids;
      this.$modal.confirm(this.$t('chargingModule.merchant.deleteConfirm', { ids: merchantIds })).then(() => {
        this.loading = true;
        return delMerchant(merchantIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess(this.$t('common.deleteSuccess'));
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/merchant/export', {
        ...this.queryParams
      }, `merchant_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
  