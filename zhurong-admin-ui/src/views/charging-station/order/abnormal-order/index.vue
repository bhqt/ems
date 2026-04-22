<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('chargingModule.order.orderNo')" prop="orderNo">
        <el-input v-model="queryParams.orderNo" :placeholder="$t('placeholder.input') + $t('chargingModule.order.orderNo')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.userName')" prop="userName">
        <el-input v-model="queryParams.userName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.userName')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.phone')" prop="phone">
        <el-input v-model="queryParams.phone" :placeholder="$t('placeholder.input') + $t('chargingModule.order.phone')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.merchantName')" prop="merchantName">
        <el-input v-model="queryParams.merchantName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.merchantName')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.stationName')" prop="stationName">
        <el-input v-model="queryParams.stationName" :placeholder="$t('placeholder.input') + $t('chargingModule.order.stationName')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.carNo')" prop="carNo">
        <el-input v-model="queryParams.carNo" :placeholder="$t('placeholder.input') + $t('chargingModule.order.carNo')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.chargeMethod')" prop="chargeMethod">
        <el-select v-model="queryParams.chargeMethod" :placeholder="$t('placeholder.select')" clearable style="width: 215px">
          <el-option v-for="dict in dict.type.charge_method" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.order.orderSource')" prop="orderSource">
        <el-select v-model="queryParams.orderSource" :placeholder="$t('placeholder.select')" clearable style="width: 215px">
          <el-option v-for="dict in dict.type.order_source" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:orderInfo:edit']">{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:orderInfo:remove']">{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['system:orderInfo:export']">{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('chargingModule.order.orderNo')" align="center" prop="orderNo" width="180" />
      <el-table-column :label="$t('chargingModule.order.createTime')" align="center" prop="createTime" width="180" />
      <el-table-column :label="$t('chargingModule.order.orderStatus')" align="center" prop="orderStatus" width="100">
        <template slot-scope="scope"><dict-tag :options="dict.type.order_status" :value="scope.row.orderStatus"/></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.settleType')" align="center" prop="settleType" width="120">
        <template slot-scope="scope">
          <dict-tag v-if="scope.row.settleType" :options="dict.type.settle_type" :value="scope.row.settleType"/>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.abnoCause')" align="center" prop="abnoCause" width="180" show-overflow-tooltip>
        <template slot-scope="scope"><span>{{ scope.row.abnoCause || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.userName')" align="center" prop="userName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.phone')" align="center" prop="phone" width="120" />
      <el-table-column :label="$t('chargingModule.order.settleBalance')" align="center" prop="settleBalance" width="100"/>
      <el-table-column :label="$t('chargingModule.order.merchantName')" align="center" prop="merchantName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.stationName')" align="center" prop="stationName" width="200" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.pileName')" align="center" prop="pileName" width="120" show-overflow-tooltip/>
      <el-table-column :label="$t('chargingModule.order.startTime')" align="center" prop="startTime" width="180">
        <template slot-scope="scope"><span>{{ scope.row.startTime || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.endTime')" align="center" prop="endTime" width="180">
        <template slot-scope="scope"><span>{{ scope.row.endTime || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.carNo')" align="center" prop="carNo" width="100" >
        <template slot-scope="scope"><span>{{ scope.row.carNo || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.carVin')" align="center" prop="carVin" width="150" show-overflow-tooltip>
        <template slot-scope="scope"><span>{{ scope.row.carVin || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.chargeMethod')" align="center" prop="chargeMethod" width="100" >
        <template slot-scope="scope"><dict-tag :options="dict.type.charge_method" :value="scope.row.chargeMethod"/></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.payType')" align="center" prop="payType" width="100">
        <template slot-scope="scope">
          <dict-tag v-if="scope.row.payType" :options="dict.type.pay_type" :value="scope.row.payType"/>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.settleTime')" align="center" prop="settleTime" width="180">
        <template slot-scope="scope"><span>{{ scope.row.settleTime || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.settlePrice')" align="center" prop="settlePrice" width="100">
        <template slot-scope="scope"><span>{{ scope.row.settlePrice || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.paidPrice')" align="center" prop="paidPrice" width="100">
        <template slot-scope="scope"><span>{{ scope.row.paidPrice || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.discountAmt')" align="center" prop="discountAmt" width="100">
        <template slot-scope="scope"><span>{{ scope.row.discountAmt || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.elecAmt')" align="center" prop="elecAmt" width="100">
        <template slot-scope="scope"><span>{{ scope.row.elecAmt || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.serveAmt')" align="center" prop="serveAmt" width="100">
        <template slot-scope="scope"><span>{{ scope.row.serveAmt || '--' }}</span></template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.order.chargeDuration')" align="center" prop="chargeDuration" width="100"/>
      <el-table-column :label="$t('chargingModule.order.energy')" align="center" prop="energy" width="100"/>
      <el-table-column :label="$t('chargingModule.order.orderSource')" align="center" prop="orderSource" width="100">
        <template slot-scope="scope"><dict-tag :options="dict.type.order_source" :value="scope.row.orderSource"/></template>
      </el-table-column>
      <el-table-column :label="$t('common.action')" align="center" class-name="small-padding fixed-width" fixed="right" width="160">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="toOrderDetail(scope.row)" v-hasPermi="['system:orderInfo:query']">{{ $t('chargingModule.order.detail') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:orderInfo:edit']">{{ $t('chargingModule.order.process') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listOrderInfo, getOrderInfo, delOrderInfo, updateOrderInfo } from "@/api/chargingStation/orderInfo";

export default {
  name: "AbnormalOrder",
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
      queryParams: {
        pageNum: 1, pageSize: 10, orderNo: undefined, userName: undefined, phone: undefined,
        merchantName: undefined, stationName: undefined, carNo: undefined, carVin: undefined,
        chargeMethod: undefined, settleType: undefined, payType: undefined,
        settleTime: undefined, orderStatus: undefined, orderSource: undefined,
      },
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listOrderInfo(this.queryParams).then(response => {
        this.orderInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleUpdate(row) {
      this.loading = true;
      const id = row.id || this.ids
      getOrderInfo(id).then(response => {
        this.loading = false;
        this.$router.push({path: '/charging-station/order-info/' + id})
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('chargingModule.order.deleteConfirm', { ids })).then(() => {
        this.loading = true;
        return delOrderInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('common.deleteSuccess'));
      }).finally(() => { this.loading = false; });
    },
    handleExport() {
      this.download('system/orderInfo/export', { ...this.queryParams }, `orderInfo_${new Date().getTime()}.xlsx`)
    },
    toOrderDetail(row) { this.$router.push({path: '/charging-station/order-info/' + row.id}) }
  }
};
</script>
