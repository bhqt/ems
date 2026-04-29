<template>
  <div class="metering-container">
    <el-card>
      <template slot="header">
        <div class="card-header">
          <span>{{ $t('meteringModule.title') }}</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane :label="$t('meteringModule.meterManagement')" name="meter">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-input
                v-model="meterSearch"
                :placeholder="$t('meteringModule.placeholder.searchMeter')"
                prefix-icon="el-icon-search"
                style="margin-bottom: 10px;"
              />
              <el-table :data="filteredMeters" style="width: 100%">
                <el-table-column prop="meterId" :label="$t('meteringModule.meterId')" width="120" />
                <el-table-column prop="meterName" :label="$t('meteringModule.meterName')" />
                <el-table-column prop="meterType" :label="$t('meteringModule.type')" />
                <el-table-column prop="specification" :label="$t('meteringModule.specification')" />
                <el-table-column prop="installationLocation" :label="$t('meteringModule.installationLocation')" />
                <el-table-column prop="status" :label="$t('meteringModule.status')" width="100">
                  <template slot-scope="scope">
                    <el-tag :type="getStatusType(scope.row.status)">
                      {{ scope.row.status }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="lastCalibrationDate" :label="$t('meteringModule.lastCalibrationDate')" width="180" />
                <el-table-column prop="nextCalibrationDate" :label="$t('meteringModule.nextCalibrationDate')" width="180" />
                <el-table-column :label="$t('meteringModule.action')" width="150">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="small"
                      @click="viewMeterDetail(scope.row)"
                    >
                      {{ $t('meteringModule.detail') }}
                    </el-button>
                    <el-button
                      type="success"
                      size="small"
                      @click="createCalibrationPlan(scope.row)"
                      style="margin-left: 5px;"
                    >
                      {{ $t('meteringModule.calibrate') }}
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane :label="$t('meteringModule.calibrationPlan')" name="plan">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="planDateRange"
                type="daterange"
                :range-separator="$t('meteringModule.placeholder.endDate')"
                :start-placeholder="$t('meteringModule.placeholder.startDate')"
                :end-placeholder="$t('meteringModule.placeholder.endDate')"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-select v-model="planStatus" :placeholder="$t('meteringModule.placeholder.selectStatus')">
                <el-option :label="$t('meteringModule.all')" value="" />
                <el-option :label="$t('meteringModule.pending')" value="待执行" />
                <el-option :label="$t('meteringModule.executing')" value="执行中" />
                <el-option :label="$t('meteringModule.completed')" value="已完成" />
                <el-option :label="$t('meteringModule.cancelled')" value="已取消" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="addCalibrationPlan">{{ $t('meteringModule.addPlan') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredPlans" style="width: 100%">
            <el-table-column prop="planId" :label="$t('meteringModule.planId')" width="120" />
            <el-table-column prop="meterName" :label="$t('meteringModule.meter')" />
            <el-table-column prop="planDate" :label="$t('meteringModule.planCalibrationDate')" width="180" />
            <el-table-column prop="status" :label="$t('meteringModule.status')" width="100">
              <template slot-scope="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="executor" :label="$t('meteringModule.executor')" width="120" />
            <el-table-column prop="remark" :label="$t('meteringModule.remark')" />
            <el-table-column :label="$t('meteringModule.action')" width="150">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewPlanDetail(scope.row)"
                >
                  {{ $t('meteringModule.detail') }}
                </el-button>
                <el-button
                  type="success"
                  size="small"
                  @click="executePlan(scope.row)"
                  style="margin-left: 5px;"
                  :disabled="scope.row.status !== '待执行'"
                >
                  {{ $t('meteringModule.execute') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane :label="$t('meteringModule.calibrationRecord')" name="record">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="recordDateRange"
                type="daterange"
                :range-separator="$t('meteringModule.placeholder.endDate')"
                :start-placeholder="$t('meteringModule.placeholder.startDate')"
                :end-placeholder="$t('meteringModule.placeholder.endDate')"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-input
                v-model="recordSearch"
                :placeholder="$t('meteringModule.placeholder.searchMeter')"
                prefix-icon="el-icon-search"
              />
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="exportRecords">{{ $t('meteringModule.export') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredRecords" style="width: 100%">
            <el-table-column prop="recordId" label="记录ID" width="120" />
            <el-table-column prop="meterName" :label="$t('meteringModule.meter')" />
            <el-table-column prop="calibrationDate" label="校准日期" width="180" />
            <el-table-column prop="calibrationResult" label="校准结果" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.calibrationResult === $t('meteringModule.pass') ? 'success' : 'danger'">
                  {{ scope.row.calibrationResult === $t('meteringModule.pass') ? $t('meteringModule.pass') : $t('meteringModule.fail') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="calibrator" label="校准人" width="120" />
            <el-table-column prop="deviation" label="偏差值" width="100" />
            <el-table-column prop="remark" :label="$t('meteringModule.remark')" />
            <el-table-column :label="$t('meteringModule.action')" width="120">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewRecordDetail(scope.row)"
                >
                  {{ $t('meteringModule.detail') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane :label="$t('meteringModule.analysis')" name="analysis">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>{{ $t('meteringModule.qualificationTrend') }}</span>
                </template>
                <div id="qualificationRateChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>{{ $t('meteringModule.deviationDistribution') }}</span>
                </template>
                <div id="deviationChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 20px;">
            <el-col :span="24">
              <el-card>
                <template slot="header">
                  <span>{{ $t('meteringModule.meterStatusStatistics') }}</span>
                </template>
                <div id="statusChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-dialog
      :title="$t('meteringModule.addPlan')"
      :visible.sync="calibrationPlanDialogVisible"
      width="500px"
    >
      <el-form :model="calibrationPlanForm" label-width="100px">
        <el-form-item :label="$t('meteringModule.meter')">
          <el-select v-model="calibrationPlanForm.meterId" :placeholder="$t('meteringModule.placeholder.selectMeter')">
            <el-option
              v-for="meter in meters"
              :key="meter.meterId"
              :label="meter.meterName"
              :value="meter.meterId"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('meteringModule.planCalibrationDate')">
          <el-date-picker
            v-model="calibrationPlanForm.planDate"
            type="date"
            :placeholder="$t('meteringModule.placeholder.selectDate')"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item :label="$t('meteringModule.executor')">
          <el-input v-model="calibrationPlanForm.executor" :placeholder="$t('meteringModule.placeholder.inputExecutor')" />
        </el-form-item>
        <el-form-item :label="$t('meteringModule.remark')">
          <el-input
            v-model="calibrationPlanForm.remark"
            type="textarea"
            :placeholder="$t('meteringModule.placeholder.inputRemark')"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="calibrationPlanDialogVisible = false">{{ $t('common.cancel') }}</el-button>
        <el-button type="primary" @click="submitCalibrationPlan">{{ $t('common.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'MeteringManagement',
  data() {
    return {
      activeTab: 'meter',
      meterSearch: '',
      meters: [
        {
          meterId: 'MTR001',
          meterName: '电能表1',
          meterType: '电能表',
          specification: 'DDS1000',
          installationLocation: '车间A',
          status: '正常',
          lastCalibrationDate: '2026-01-15',
          nextCalibrationDate: '2026-07-15'
        },
        {
          meterId: 'MTR002',
          meterName: '水表1',
          meterType: '水表',
          specification: 'LXSG-15',
          installationLocation: '水源水区域',
          status: '正常',
          lastCalibrationDate: '2026-02-20',
          nextCalibrationDate: '2026-08-20'
        },
        {
          meterId: 'MTR003',
          meterName: '流量计1',
          meterType: '流量计',
          specification: 'DN50',
          installationLocation: '中间站区域',
          status: '待校准',
          lastCalibrationDate: '2026-01-10',
          nextCalibrationDate: '2026-03-30'
        },
        {
          meterId: 'MTR004',
          meterName: '电能表2',
          meterType: '电能表',
          specification: 'DDS2000',
          installationLocation: '车间B',
          status: '异常',
          lastCalibrationDate: '2026-01-05',
          nextCalibrationDate: '2026-07-05'
        }
      ],
      plans: [
        {
          planId: 'PLN001',
          meterId: 'MTR003',
          meterName: '流量计1',
          planDate: '2026-03-30',
          status: '待执行',
          executor: '张三',
          remark: '定期校准'
        },
        {
          planId: 'PLN002',
          meterId: 'MTR001',
          meterName: '电能表1',
          planDate: '2026-07-15',
          status: '待执行',
          executor: '李四',
          remark: '定期校准'
        }
      ],
      records: [
        {
          recordId: 'REC001',
          meterId: 'MTR001',
          meterName: '电能表1',
          calibrationDate: '2026-01-15',
          calibrationResult: '合格',
          calibrator: '张三',
          deviation: '0.5%',
          remark: '正常校准'
        },
        {
          recordId: 'REC002',
          meterId: 'MTR002',
          meterName: '水表1',
          calibrationDate: '2026-02-20',
          calibrationResult: '合格',
          calibrator: '李四',
          deviation: '0.3%',
          remark: '正常校准'
        },
        {
          recordId: 'REC003',
          meterId: 'MTR004',
          meterName: '电能表2',
          calibrationDate: '2026-03-10',
          calibrationResult: '不合格',
          calibrator: '张三',
          deviation: '2.5%',
          remark: '需要更换'
        }
      ],
      planDateRange: null,
      planStatus: '',
      recordDateRange: null,
      recordSearch: '',
      calibrationPlanDialogVisible: false,
      calibrationPlanForm: {
        meterId: '',
        planDate: '',
        executor: '',
        remark: ''
      }
    }
  },
  computed: {
    filteredMeters() {
      if (!this.meterSearch) {
        return this.meters
      }
      return this.meters.filter(meter =>
        meter.meterName.includes(this.meterSearch) ||
        meter.meterId.includes(this.meterSearch) ||
        meter.installationLocation.includes(this.meterSearch)
      )
    },
    filteredPlans() {
      let result = this.plans
      if (this.planStatus) {
        result = result.filter(plan => plan.status === this.planStatus)
      }
      if (this.planDateRange) {
        const startDate = new Date(this.planDateRange[0])
        const endDate = new Date(this.planDateRange[1])
        result = result.filter(plan => {
          const planDate = new Date(plan.planDate)
          return planDate >= startDate && planDate <= endDate
        })
      }
      return result
    },
    filteredRecords() {
      let result = this.records
      if (this.recordSearch) {
        result = result.filter(record => record.meterName.includes(this.recordSearch))
      }
      if (this.recordDateRange) {
        const startDate = new Date(this.recordDateRange[0])
        const endDate = new Date(this.recordDateRange[1])
        result = result.filter(record => {
          const recordDate = new Date(record.calibrationDate)
          return recordDate >= startDate && recordDate <= endDate
        })
      }
      return result
    }
  },
  methods: {
    getStatusType(status) {
      switch (status) {
        case '正常':
        case '已完成':
          return 'success'
        case '待校准':
        case '待执行':
          return 'warning'
        case '异常':
        case '已取消':
          return 'danger'
        case '执行中':
          return 'primary'
        default:
          return ''
      }
    },
    viewMeterDetail(meter) {
      this.$message.info(this.$t('meteringModule.viewMeterDetail') + meter.meterName)
    },
    createCalibrationPlan(meter) {
      this.calibrationPlanForm.meterId = meter.meterId
      this.calibrationPlanForm.planDate = ''
      this.calibrationPlanForm.executor = ''
      this.calibrationPlanForm.remark = ''
      this.calibrationPlanDialogVisible = true
    },
    addCalibrationPlan() {
      this.calibrationPlanForm = {
        meterId: '',
        planDate: '',
        executor: '',
        remark: ''
      }
      this.calibrationPlanDialogVisible = true
    },
    submitCalibrationPlan() {
      if (!this.calibrationPlanForm.meterId || !this.calibrationPlanForm.planDate || !this.calibrationPlanForm.executor) {
        this.$message.error(this.$t('meteringModule.fillCompleteInfo'))
        return
      }

      const meter = this.meters.find(m => m.meterId === this.calibrationPlanForm.meterId)
      if (!meter) {
        this.$message.error(this.$t('meteringModule.meterNotExist'))
        return
      }

      const newPlan = {
        planId: 'PLN' + (this.plans.length + 1).toString().padStart(3, '0'),
        meterId: this.calibrationPlanForm.meterId,
        meterName: meter.meterName,
        planDate: this.calibrationPlanForm.planDate,
        status: '待执行',
        executor: this.calibrationPlanForm.executor,
        remark: this.calibrationPlanForm.remark
      }
      this.plans.push(newPlan)
      this.$message.success(this.$t('meteringModule.planAdded'))
      this.calibrationPlanDialogVisible = false
    },
    viewPlanDetail(plan) {
      this.$message.info(this.$t('meteringModule.viewPlanDetail') + plan.planId)
    },
    executePlan(plan) {
      plan.status = '执行中'
      this.$message.success(this.$t('meteringModule.startExecute') + plan.planId)

      setTimeout(() => {
        plan.status = '已完成'
        this.$message.success(this.$t('meteringModule.planCompleted') + plan.planId)

        const newRecord = {
          recordId: 'REC' + (this.records.length + 1).toString().padStart(3, '0'),
          meterId: plan.meterId,
          meterName: plan.meterName,
          calibrationDate: new Date().toISOString().split('T')[0],
          calibrationResult: Math.random() > 0.1 ? '合格' : '不合格',
          calibrator: plan.executor,
          deviation: (Math.random() * 2).toFixed(1) + '%',
          remark: '计划执行'
        }
        this.records.push(newRecord)

        const meter = this.meters.find(m => m.meterId === plan.meterId)
        if (meter) {
          meter.lastCalibrationDate = newRecord.calibrationDate
          meter.status = newRecord.calibrationResult === '合格' ? '正常' : '异常'
          const nextDate = new Date(newRecord.calibrationDate)
          nextDate.setMonth(nextDate.getMonth() + 6)
          meter.nextCalibrationDate = nextDate.toISOString().split('T')[0]
        }
      }, 1000)
    },
    viewRecordDetail(record) {
      this.$message.info(this.$t('meteringModule.viewRecordDetail') + record.recordId)
    },
    exportRecords() {
      this.$message.success(this.$t('meteringModule.exportSuccess'))
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.metering-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  text-align: right;
}
</style>
