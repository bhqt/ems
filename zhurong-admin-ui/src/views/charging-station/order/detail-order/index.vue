<template>
  <div class="app-container bg-container">
    <div class="detail-top">
      <el-row :gutter="12">
        <el-col :span="12">
          <div style="min-height: 300px;">
            <div class="order-title">{{ $t('chargingModule.orderDetail.orderInfo') }}</div>
            <el-descriptions :column="2" style="padding: 0 24px;">
              <el-descriptions-item :label="$t('chargingModule.order.orderNo')">{{orderInfo.orderNo}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.startTime')">{{orderInfo.startTime}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.userName')">{{orderInfo.userName}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.phone')">{{orderInfo.phone}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.settleBalance')">{{orderInfo.settleBalance}} {{ $t('chargingModule.orderDetail.yuan') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.carNo')">{{orderInfo.carNo}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.carVin')">{{orderInfo.carVin}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.brand')">--</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.model')">--</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.chargeMethod')">
                <dict-tag :options="dict.type.charge_method" :value="orderInfo.chargeMethod" size="mini"/>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.energy')">{{orderInfo.energy}} {{ $t('chargingModule.orderDetail.kwh') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.chargeDuration')">{{orderInfo.chargeDuration}} {{ $t('chargingModule.orderDetail.hour') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.settlePrice')">{{orderInfo.settlePrice}} {{ $t('chargingModule.orderDetail.yuan') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.discountAmt')">{{orderInfo.discountAmt}} {{ $t('chargingModule.orderDetail.yuan') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.paidPrice')">{{orderInfo.paidPrice}} {{ $t('chargingModule.orderDetail.yuan') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.payType')">
                <dict-tag :options="dict.type.pay_type" :value="orderInfo.payType" size="mini"/>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.orderSource')">
                <dict-tag :options="dict.type.order_source" :value="orderInfo.orderSource" size="mini"/>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.orderStatus')">
                <dict-tag :options="dict.type.order_status" :value="orderInfo.orderStatus" size="mini"/>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.order.settleType')">
                <dict-tag :options="dict.type.settle_type" :value="orderInfo.settleType" size="mini"/>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('common.remark')">{{orderInfo.remark || '--'}}</el-descriptions-item>
            </el-descriptions>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="min-height: 300px;">
            <div class="order-title">{{ $t('chargingModule.orderDetail.pileInfo') }}</div>
            <el-descriptions :column="2" style="padding: 0 24px;">
              <el-descriptions-item :label="$t('chargingModule.orderDetail.terminalCode')">34RFT54YT56Y65G543F</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.terminalName')">{{orderInfo.pileName}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.brand')">{{ $t('chargingModule.orderDetail.brand') }}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.model')">test-01</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.station')">{{orderInfo.stationName}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.merchant')">{{orderInfo.merchantName}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.contactPhone')">{{merchant.contact}}</el-descriptions-item>
              <el-descriptions-item :label="$t('chargingModule.orderDetail.stationAddress')">{{ $t('chargingModule.orderDetail.stationAddress') }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="detail-bottom">
      <el-row :gutter="12">
        <el-col :span="12">
          <div style="min-height: 350px;margin-bottom: 24px;">
            <div class="order-title">{{ $t('chargingModule.orderDetail.demandVoltage') }}</div>
            <OrderLineChartVue height="300px" :seriesName="[$t('chargingModule.orderDetail.demandVoltage'), $t('chargingModule.orderDetail.demandVoltage')]"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="min-height: 350px;margin-bottom: 24px;">
            <div class="order-title">{{ $t('chargingModule.orderDetail.demandCurrent') }}</div>
            <OrderLineChartVue height="300px" :seriesName="[$t('chargingModule.orderDetail.demandCurrent'), $t('chargingModule.orderDetail.demandCurrent')]" :yName="$t('chargingModule.orderDetail.currentUnit')" :itemColor="['#7d6fed', '#37dcfd']" :chartData="{require: [346,268, 316, 346, 248, 313, 293],real: [246,283, 316, 219, 386, 327, 276]}"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="min-height: 350px;margin-bottom: 24px;">
            <div class="order-title">{{ $t('chargingModule.orderDetail.soc') }}</div>
            <OrderLineChartVue height="300px" :seriesName="['SOC']" :yName="$t('chargingModule.orderDetail.socUnit')" :itemColor="['#77f1dc', '#37dcfd']" :chartData="{require: [36,47, 56, 58, 68, 69, 80]}"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="min-height: 350px;margin-bottom: 24px;">
            <div class="order-title">{{ $t('chargingModule.orderDetail.batteryTemp') }}</div>
            <OrderLineChartVue height="300px" :seriesName="[$t('chargingModule.orderDetail.batteryTemp')]" :yName="$t('chargingModule.orderDetail.tempUnit')" :itemColor="['#fb9959', '#37dcfd']" :chartData="{require: [53,47, 47, 52, 56, 49, 57]}"/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {getOrderInfo} from "@/api/chargingStation/orderInfo"
import OrderLineChartVue from './OrderLineChart.vue'
export default {
  dicts: ['order_status', 'charge_method', 'order_source', 'settle_type', 'pay_type'],
  components: {
    OrderLineChartVue
  },
  data() {
    return {
      orderInfo: {},
      merchant: {}
    }
  },
  created() {
    const orderId = this.$route.params && this.$route.params.orderId;
    this.getOrder(orderId)
  },
  methods: {
    getOrder(orderId) {
      getOrderInfo(orderId).then(res => {
        this.orderInfo = res.data
        this.merchant = res.data.merchant || {}
      })
    },
  }
}
</script>

<style scoped>
.app-container {
  min-height: calc(100vh - 84px);
  padding: 12px;
}
.detail-top {
  background: var(--base-item-bg);
  padding: 12px;
}
.detail-bottom {
  background: var(--base-item-bg);
  padding: 12px 12px 0 12px;
  margin-top: 12px;
}
.order-title {
  margin-bottom: 24px;
  font-size: 18px;
  color: var(--base-color-1);
  font-weight: bold;
  display: flex;
  align-items: center;
}
.order-title::before {
  content: '';
  width: 6px;
  height: 24px;
  background-color: var(--current-color);
  display: inline-block;
  margin-right: 8px;
}
</style>
