<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('chargingModule.occupancyOrder.occupancyNo')" prop="occupancyNo">
        <el-input
          v-model="queryParams.occupancyNo"
          :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.occupancyNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.occupancyOrder.orderNo')" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.orderNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.occupancyOrder.orderStatus')" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.occupancy_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.occupancyOrder.settleStatus')" prop="settleStatus">
        <el-select
          v-model="queryParams.settleStatus"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.occupancy_order_settle"
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:occupancyOrder:edit']"
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
          v-hasPermi="['system:occupancyOrder:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:occupancyOrder:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="occupancyOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('chargingModule.occupancyOrder.occupancyNo')" align="center" prop="occupancyNo" width="180"/>
      <el-table-column :label="$t('chargingModule.occupancyOrder.orderNo')" align="center" prop="orderNo" width="180" />
      <el-table-column :label="$t('chargingModule.occupancyOrder.settleStatus')" align="center" prop="settleStatus" width="100" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.occupancy_order_settle" :value="scope.row.settleStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.occupancyOrder.orderStatus')" align="center" prop="orderStatus" width="100" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.occupancy_order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.occupancyOrder.userName')" align="center" prop="orderInfo.userName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.occupancyOrder.phone')" align="center" prop="orderInfo.phone" width="120" />
      <el-table-column :label="$t('chargingModule.occupancyOrder.merchantName')" align="center" prop="orderInfo.merchantName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.occupancyOrder.stationName')" align="center" prop="orderInfo.stationName" width="200" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.occupancyOrder.pileName')" align="center" prop="orderInfo.pileName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.occupancyOrder.duration')" align="center" prop="duration" width="80" />
      <el-table-column :label="$t('chargingModule.occupancyOrder.fee')" align="center" prop="fee" width="80" />
      <el-table-column :label="$t('chargingModule.occupancyOrder.isFee')" align="center" prop="isFee" width="120" >
        <template slot-scope="scope">
          {{scope.row.isFee == '0' ? $t('common.yes') : $t('common.no')}}
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.occupancyOrder.payTime')" align="center" prop="payTime" width="180">
      </el-table-column>
      <el-table-column :label="$t('chargingModule.occupancyOrder.endReason')" align="center" prop="endReason" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          {{scope.row.endReason ? scope.row.endReason : '--'}}
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.action')" align="center" class-name="small-padding fixed-width" fixed="right" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:occupancyOrder:edit']"
          >{{ $t('common.view') }}</el-button>
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

    <!-- 添加或修改占位订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('chargingModule.occupancyOrder.occupancyNo')" prop="occupancyNo">
          <el-input v-model="form.occupancyNo" :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.occupancyNo')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.occupancyOrder.orderNo')" prop="orderNo">
          <el-input v-model="form.orderNo" :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.orderNo')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.occupancyOrder.duration')" prop="duration">
          <el-input v-model="form.duration" :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.duration')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.occupancyOrder.fee')" prop="fee">
          <el-input v-model="form.fee" :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.fee')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.occupancyOrder.payTime')" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('placeholder.select') + $t('chargingModule.occupancyOrder.payTime')">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" :placeholder="$t('placeholder.input') + $t('common.remark')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.occupancyOrder.endReason')" prop="endReason">
          <el-input v-model="form.endReason" :placeholder="$t('placeholder.input') + $t('chargingModule.occupancyOrder.endReason')" />
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
import { listOccupancyOrder, getOccupancyOrder, delOccupancyOrder, addOccupancyOrder, updateOccupancyOrder } from "@/api/chargingStation/occupancyOrder";

export default {
  name: "OccupancyOrder",
  dicts: ['occupancy_order_status', 'occupancy_order_settle'],
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
      // 占位订单信息表格数据
      occupancyOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        occupancyNo: undefined,
        orderNo: undefined,
        duration: undefined,
        fee: undefined,
        isFee: undefined,
        payTime: undefined,
        settleStatus: undefined,
        orderStatus: undefined,
        endReason: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.idRequired'), trigger: "blur" }
        ],
        occupancyNo: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.occupancyNoRequired'), trigger: "blur" }
        ],
        orderNo: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.orderNoRequired'), trigger: "blur" }
        ],
        duration: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.durationRequired'), trigger: "blur" }
        ],
        fee: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.feeRequired'), trigger: "blur" }
        ],
        isFee: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.isFeeRequired'), trigger: "blur" }
        ],
        payTime: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.payTimeRequired'), trigger: "blur" }
        ],
        settleStatus: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.settleStatusRequired'), trigger: "change" }
        ],
        orderStatus: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.orderStatusRequired'), trigger: "change" }
        ],
        remark: [
          { required: true, message: this.$t('common.validate.remarkRequired'), trigger: "blur" }
        ],
        endReason: [
          { required: true, message: this.$t('chargingModule.occupancyOrder.validate.endReasonRequired'), trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询占位订单信息列表 */
    getList() {
      this.loading = true;
      listOccupancyOrder(this.queryParams).then(response => {
        this.occupancyOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取国际化后的对话框标题 */
    getDialogTitle(isEdit) {
      return isEdit ? this.$t('chargingModule.occupancyOrder.editTitle') : this.$t('chargingModule.occupancyOrder.addTitle');
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
        occupancyNo: undefined,
        orderNo: undefined,
        duration: undefined,
        fee: undefined,
        isFee: undefined,
        payTime: undefined,
        settleStatus: undefined,
        orderStatus: undefined,
        remark: undefined,
        endReason: undefined,
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
      this.title = this.$t('chargingModule.occupancyOrder.addTitle');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getOccupancyOrder(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = this.$t('chargingModule.occupancyOrder.editTitle');
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateOccupancyOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.success'));
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addOccupancyOrder(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.success'));
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
      this.$modal.confirm(this.$t('chargingModule.occupancyOrder.deleteConfirm', { ids })).then(() => {
        this.loading = true;
        return delOccupancyOrder(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess(this.$t('common.success'));
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/occupancyOrder/export', {
        ...this.queryParams
      }, `occupancyOrder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>