<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('chargingModule.brand.brandName')" prop="modelName">
        <el-input
          v-model="queryParams.brandName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.brand.brandName')"
          disabled
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.brand.modelName')" prop="modelName">
        <el-input
          v-model="queryParams.modelName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.brand.modelName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select
          v-model="queryParams.status"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:model:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:model:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:model:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:model:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >{{ $t('common.close') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('chargingModule.brand.modelId')" align="center" prop="id" v-if="true"/>
      <el-table-column :label="$t('chargingModule.brand.modelName')" align="center" prop="modelName" />
      <el-table-column :label="$t('chargingModule.brand.modelStatus')" align="center" prop="status" >
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
            @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.creator')" align="center" prop="createBy" />
      <el-table-column :label="$t('common.createTime')" align="center" prop="createTime" width="180"/>
      <el-table-column :label="$t('common.action')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:model:edit']"
          >{{ $t('common.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:model:remove']"
          >{{ $t('common.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改型号信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="$t('chargingModule.brand.brandName')">
          <el-input v-model="queryParams.brandName" :placeholder="$t('placeholder.input') + $t('chargingModule.brand.brandName')" disabled/>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.brand.modelName')" prop="modelName">
          <el-input v-model="form.modelName" :placeholder="$t('placeholder.input') + $t('chargingModule.brand.modelName')" />
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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
import { listModel, getModel, delModel, addModel, updateModel } from "@/api/chargingStation/model";
import { getBrand } from "@/api/chargingStation/brand";
export default {
  dicts: ['sys_normal_disable'],
  name: "Model",
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
      // 型号信息表格数据
      modelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        modelName: undefined,
        brandId: undefined,
        status: undefined,
        brandName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: () => this.$t('validation.required', { field: 'ID' }), trigger: "blur" }
        ],
        modelName: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.brand.modelName') }), trigger: "blur" }
        ],
        brandId: [
          { required: true, message: () => this.$t('validation.required', { field: 'Brand ID' }), trigger: "blur" }
        ],
        status: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.brand.modelStatus') }), trigger: "change" }
        ],
      }
    };
  },
  created() {
    const brandId = this.$route.params && this.$route.params.brandId;
    console.log(brandId);
    this.getBrandInfo(brandId);
  },
  methods: {
    // 获取品牌详情
    getBrandInfo(brandId) {
      getBrand(brandId).then(res => {
        this.queryParams.brandId = res.data.id,
        this.queryParams.brandName = res.data.brandName
        this.getList()
      })
    },
    /** 查询型号信息列表 */
    getList() {
      this.loading = true;
      listModel(this.queryParams).then(response => {
        this.modelList = response.rows;
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
        id: undefined,
        modelName: undefined,
        brandId: undefined,
        status: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('chargingModule.brand.addModelTitle');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getModel(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = this.$t('chargingModule.brand.editModelTitle');
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateModel(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.brandId = this.queryParams.brandId
            addModel(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('chargingModule.brand.deleteModelConfirm', { ids })).then(() => {
        this.loading = true;
        return delModel(ids);
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
      this.download('system/model/export', {
        ...this.queryParams
      }, `model_${new Date().getTime()}.xlsx`)
    },
    /** 返回按钮操作 */
    handleClose() {
      const obj = { path: "/charging-station/brand" };
      this.$tab.closeOpenPage(obj);
    },
    // 型号状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? this.$t('common.enable') : this.$t('common.disable');
      this.$modal.confirm(this.$t('chargingModule.brand.modelStatusConfirm', { text })).then(() => {
        return updateModel(row);
      }).then(() => {
        this.$modal.msgSuccess(text + this.$t('common.success'));
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>