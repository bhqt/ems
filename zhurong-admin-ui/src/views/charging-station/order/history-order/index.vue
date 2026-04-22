<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('chargingModule.order.orderNo')" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          :placeholder="$t('placeholder.input') + $t('chargingModule.order.orderNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.userName')" prop="userName">
        <el-input
          v-model="queryParams.userName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.order.userName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.phone')" prop="phone">
        <el-input
          v-model="queryParams.phone"
          :placeholder="$t('placeholder.input') + $t('chargingModule.order.phone')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.merchantName')" prop="merchantName">
        <el-input
          v-model="queryParams.merchantName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.order.merchantName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.stationName')" prop="stationName">
        <el-input
          v-model="queryParams.stationName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.order.stationName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.carNo')" prop="carNo">
        <el-input
          v-model="queryParams.carNo"
          :placeholder="$t('placeholder.input') + $t('chargingModule.order.carNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.chargeMethod')" prop="chargeMethod">
        <el-select
          v-model="queryParams.chargeMethod"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.charge_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.orderSource')" prop="orderSource">
        <el-select
          v-model="queryParams.orderSource"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.order_source"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.settleType')" prop="settleType">
        <el-select
          v-model="queryParams.settleType"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.settle_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.orderStatus')" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="dict in dict.type.order_status"
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
          v-hasPermi="['system:orderInfo:edit']"
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
          v-hasPermi="['system:orderInfo:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:orderInfo:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderInfoList" @selection-change="handleSelectionChange" class="order-table">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('chargingModule.order.orderNo')" align="center" prop="orderNo" width="180" />
      <el-table-column :label="$t('chargingModule.order.createTime')" align="center" prop="createTime" width="180" />
      <el-table-column :label="$t('chargingModule.order.orderStatus')" align="center" prop="orderStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.settleType')" align="center" prop="settleType" width="120">
        <template slot-scope="scope">
          <dict-tag v-if="scope.row.settleType" :options="dict.type.settle_type" :value="scope.row.settleType"/>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.userName')" align="center" prop="userName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.phone')" align="center" prop="phone" width="120" />
      <el-table-column :label="$t('chargingModule.order.settleBalance')" align="center" prop="settleBalance" width="100"/>
      <el-table-column :label="$t('chargingModule.order.merchantName')" align="center" prop="merchantName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.stationName')" align="center" prop="stationName" width="200" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.pileName')" align="center" prop="pileName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.startTime')" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.endTime')" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.endTime || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.carNo')" align="center" prop="carNo" width="100" >
        <template slot-scope="scope">
          <span>{{ scope.row.carNo || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.carVin')" align="center" prop="carVin" width="150" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.carVin || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.chargeMethod')" align="center" prop="chargeMethod" width="100" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.charge_method" :value="scope.row.chargeMethod"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.payType')" align="center" prop="payType" width="100">
        <template slot-scope="scope">
          <dict-tag v-if="scope.row.payType" :options="dict.type.pay_type" :value="scope.row.payType"/>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.settleTime')" align="center" prop="settleTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.settleTime || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.settlePrice')" align="center" prop="settlePrice" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.settlePrice || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.paidPrice')" align="center" prop="paidPrice" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.paidPrice || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.discountAmt')" align="center" prop="discountAmt" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.discountAmt || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.elecAmt')" align="center" prop="elecAmt" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.elecAmt || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.serveAmt')" align="center" prop="serveAmt" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.serveAmt || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.chargeDuration')" align="center" prop="chargeDuration" width="100"/>
      <el-table-column :label="$t('chargingModule.order.energy')" align="center" prop="energy" width="100"/>
      <el-table-column :label="$t('chargingModule.order.orderSource')" align="center" prop="orderSource" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_source" :value="scope.row.orderSource"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.action')" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="toOrderDetail(scope.row)"
            v-hasPermi="['system:orderInfo:query']"
          >{{ $t('chargingModule.order.detail') }}</el-button>
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

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('chargingModule.order.orderNo')" prop="orderNo">
          <el-input v-model="form.orderNo" :placeholder="$t('placeholder.input') + $t('chargingModule.order.orderNo')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.userName')" prop="userName">
          <el-input v-model="form.userName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.userName')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.phone')" prop="phone">
          <el-input v-model="form.phone" :placeholder="$t('placeholder.input') + $t('chargingModule.order.phone')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.merchantName')" prop="merchantName">
          <el-input v-model="form.merchantName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.merchantName')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.stationName')" prop="stationName">
          <el-input v-model="form.stationName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.stationName')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.pileName')" prop="pileName">
          <el-input v-model="form.pileName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.pileName')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.startTime')" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('placeholder.select') + $t('chargingModule.order.startTime')">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.endTime')" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('placeholder.select') + $t('chargingModule.order.endTime')">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.carNo')" prop="carNo">
          <el-input v-model="form.carNo" :placeholder="$t('placeholder.input') + $t('chargingModule.order.carNo')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.carVin')" prop="carVin">
          <el-input v-model="form.carVin" :placeholder="$t('placeholder.input') + $t('chargingModule.order.carVin')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.chargeMethod')" prop="chargeMethod">
          <el-input v-model="form.chargeMethod" :placeholder="$t('placeholder.input') + $t('chargingModule.order.chargeMethod')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.settleTime')" prop="settleTime">
          <el-date-picker clearable
            v-model="form.settleTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('placeholder.select') + $t('chargingModule.order.settleTime')">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.settlePrice')" prop="settlePrice">
          <el-input v-model="form.settlePrice" :placeholder="$t('placeholder.input') + $t('chargingModule.order.settlePrice')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.paidPrice')" prop="paidPrice">
          <el-input v-model="form.paidPrice" :placeholder="$t('placeholder.input') + $t('chargingModule.order.paidPrice')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.discountAmt')" prop="discountAmt">
          <el-input v-model="form.discountAmt" :placeholder="$t('placeholder.input') + $t('chargingModule.order.discountAmt')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.elecAmt')" prop="elecAmt">
          <el-input v-model="form.elecAmt" :placeholder="$t('placeholder.input') + $t('chargingModule.order.elecAmt')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.serveAmt')" prop="serveAmt">
          <el-input v-model="form.serveAmt" :placeholder="$t('placeholder.input') + $t('chargingModule.order.serveAmt')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.chargeDuration')" prop="chargeDuration">
          <el-input v-model="form.chargeDuration" :placeholder="$t('placeholder.input') + $t('chargingModule.order.chargeDuration')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.energy')" prop="energy">
          <el-input v-model="form.energy" :placeholder="$t('placeholder.input') + $t('chargingModule.order.energy')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.orderSource')" prop="orderSource">
          <el-input v-model="form.orderSource" :placeholder="$t('placeholder.input') + $t('chargingModule.order.orderSource')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.order.settleBalance')" prop="settleBalance">
          <el-input v-model="form.settleBalance" :placeholder="$t('placeholder.input') + $t('chargingModule.order.settleBalance')" />
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" :placeholder="$t('placeholder.input') + $t('common.remark')" />
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
import { listOrderInfo, getOrderInfo, delOrderInfo, addOrderInfo, updateOrderInfo } from "@/api/chargingStation/orderInfo";

export default {
  name: "HistoryOrder",
  dicts: ['order_status', 'charge_method', 'order_source', 'settle_type', 'pay_type'],
  data() {
    return {
      buttonLoading: false,
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      orderInfoList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: undefined,
        userName: undefined,
        phone: undefined,
        merchantId: undefined,
        merchantName: undefined,
        stationId: undefined,
        stationName: undefined,
        pileId: undefined,
        pileName: undefined,
        startTime: undefined,
        endTime: undefined,
        carNo: undefined,
        carVin: undefined,
        chargeMethod: undefined,
        settleType: undefined,
        payType: undefined,
        settleTime: undefined,
        orderStatus: undefined,
        orderSource: undefined,
      },
      form: {},
      rules: {
        orderNo: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.orderNo') }), trigger: "blur" }],
        userName: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.userName') }), trigger: "blur" }],
        phone: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.phone') }), trigger: "blur" }],
        merchantName: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.merchantName') }), trigger: "blur" }],
        stationName: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.stationName') }), trigger: "blur" }],
        pileName: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.pileName') }), trigger: "blur" }],
        startTime: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.startTime') }), trigger: "blur" }],
        endTime: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.endTime') }), trigger: "blur" }],
        carNo: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.carNo') }), trigger: "blur" }],
        carVin: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.carVin') }), trigger: "blur" }],
        chargeMethod: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.chargeMethod') }), trigger: "blur" }],
        settleTime: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.settleTime') }), trigger: "blur" }],
        settlePrice: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.settlePrice') }), trigger: "blur" }],
        paidPrice: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.paidPrice') }), trigger: "blur" }],
        discountAmt: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.discountAmt') }), trigger: "blur" }],
        elecAmt: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.elecAmt') }), trigger: "blur" }],
        serveAmt: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.serveAmt') }), trigger: "blur" }],
        chargeDuration: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.chargeDuration') }), trigger: "blur" }],
        energy: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.energy') }), trigger: "blur" }],
        orderSource: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.orderSource') }), trigger: "blur" }],
        settleBalance: [{ required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.order.settleBalance') }), trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listOrderInfo(this.queryParams).then(response => {
        this.orderInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: undefined, orderNo: undefined, userId: undefined, userName: undefined, phone: undefined,
        merchantId: undefined, merchantName: undefined, stationId: undefined, stationName: undefined,
        pileId: undefined, pileName: undefined, startTime: undefined, endTime: undefined,
        carId: undefined, carNo: undefined, carVin: undefined, chargeMethod: undefined,
        settleType: undefined, payType: undefined, settleTime: undefined, settlePrice: undefined,
        paidPrice: undefined, discountAmt: undefined, elecAmt: undefined, serveAmt: undefined,
        orderStatus: undefined, chargeDuration: undefined, energy: undefined, orderSource: undefined,
        settleBalance: undefined, remark: undefined
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getOrderInfo(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = this.$t('chargingModule.order.editTitle');
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateOrderInfo(this.form).then(() => {
              this.$modal.msgSuccess(this.$t('common.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.buttonLoading = false; });
          } else {
            addOrderInfo(this.form).then(() => {
              this.$modal.msgSuccess(this.$t('common.addSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.buttonLoading = false; });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('chargingModule.order.deleteConfirm', { ids })).then(() => {
        this.loading = true;
        return delOrderInfo(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess(this.$t('common.deleteSuccess'));
      }).finally(() => { this.loading = false; });
    },
    handleExport() {
      this.download('system/orderInfo/export', { ...this.queryParams }, `orderInfo_${new Date().getTime()}.xlsx`)
    },
    toOrderDetail(row) {
      this.$router.push({path: '/charging-station/order-info/' + row.id})
    }
  }
};
</script>
<style scoped>
.order-table>>>.el-table__body-wrapper::-webkit-scrollbar { height: 16px; }
.order-table>>>.el-table__body-wrapper::-webkit-scrollbar-thumb { border-radius: 8px; background: #dddee0; }
.order-table>>>.el-table__body-wrapper::-webkit-scrollbar-thumb:hover { background: #c7c9cc; }
.order-table>>>.el-table__body-wrapper::-webkit-scrollbar-track { background: #f1f1f1; }
</style>
