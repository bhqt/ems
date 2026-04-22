<template>
  <div class="metering-container">
    <el-card>
      <template slot="header">
        <div class="card-header">
          <span>计量管理</span>
        </div>
      </template>
      
      <el-tabs v-model="activeTab">
        <!-- 计量器具管理 -->
        <el-tab-pane label="计量器具管理" name="meter">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-input
                v-model="meterSearch"
                placeholder="搜索计量器具"
                prefix-icon="el-icon-search"
                style="margin-bottom: 10px;"
              />
              <el-table :data="filteredMeters" style="width: 100%">
                <el-table-column prop="meterId" label="计量器具ID" width="120" />
                <el-table-column prop="meterName" label="计量器具名称" />
                <el-table-column prop="meterType" label="类型" />
                <el-table-column prop="specification" label="规格型号" />
                <el-table-column prop="installationLocation" label="安装位置" />
                <el-table-column prop="status" label="状态" width="100">
                  <template slot-scope="scope">
                    <el-tag :type="getStatusType(scope.row.status)">
                      {{ scope.row.status }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="lastCalibrationDate" label="上次校准日期" width="180" />
                <el-table-column prop="nextCalibrationDate" label="下次校准日期" width="180" />
                <el-table-column label="操作" width="150">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="small"
                      @click="viewMeterDetail(scope.row)"
                    >
                      详情
                    </el-button>
                    <el-button
                      type="success"
                      size="small"
                      @click="createCalibrationPlan(scope.row)"
                      style="margin-left: 5px;"
                    >
                      校准
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-tab-pane>
        
        <!-- 校准计划管理 -->
        <el-tab-pane label="校准计划管理" name="plan">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="planDateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-select v-model="planStatus" placeholder="选择状态">
                <el-option label="全部" value="" />
                <el-option label="待执行" value="待执行" />
                <el-option label="执行中" value="执行中" />
                <el-option label="已完成" value="已完成" />
                <el-option label="已取消" value="已取消" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="addCalibrationPlan">新增校准计划</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredPlans" style="width: 100%">
            <el-table-column prop="planId" label="计划ID" width="120" />
            <el-table-column prop="meterName" label="计量器具" />
            <el-table-column prop="planDate" label="计划校准日期" width="180" />
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="executor" label="执行人" width="120" />
            <el-table-column prop="remark" label="备注" />
            <el-table-column label="操作" width="150">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewPlanDetail(scope.row)"
                >
                  详情
                </el-button>
                <el-button
                  type="success"
                  size="small"
                  @click="executePlan(scope.row)"
                  style="margin-left: 5px;"
                  :disabled="scope.row.status !== '待执行'"
                >
                  执行
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        <!-- 校准记录管理 -->
        <el-tab-pane label="校准记录管理" name="record">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="recordDateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-input
                v-model="recordSearch"
                placeholder="搜索计量器具"
                prefix-icon="el-icon-search"
              />
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="exportRecords">导出记录</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredRecords" style="width: 100%">
            <el-table-column prop="recordId" label="记录ID" width="120" />
            <el-table-column prop="meterName" label="计量器具" />
            <el-table-column prop="calibrationDate" label="校准日期" width="180" />
            <el-table-column prop="calibrationResult" label="校准结果" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.calibrationResult === '合格' ? 'success' : 'danger'">
                  {{ scope.row.calibrationResult }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="calibrator" label="校准人" width="120" />
            <el-table-column prop="deviation" label="偏差值" width="100" />
            <el-table-column prop="remark" label="备注" />
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewRecordDetail(scope.row)"
                >
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        <!-- 校准数据分析 -->
        <el-tab-pane label="校准数据分析" name="analysis">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>校准合格率趋势</span>
                </template>
                <div id="qualificationRateChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>偏差值分布</span>
                </template>
                <div id="deviationChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 20px;">
            <el-col :span="24">
              <el-card>
                <template slot="header">
                  <span>计量器具状态统计</span>
                </template>
                <div id="statusChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    
    <!-- 校准计划对话框 -->
    <el-dialog
      title="新增校准计划"
      :visible.sync="calibrationPlanDialogVisible"
      width="500px"
    >
      <el-form :model="calibrationPlanForm" label-width="100px">
        <el-form-item label="计量器具">
          <el-select v-model="calibrationPlanForm.meterId" placeholder="选择计量器具">
            <el-option
              v-for="meter in meters"
              :key="meter.meterId"
              :label="meter.meterName"
              :value="meter.meterId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="计划校准日期">
          <el-date-picker
            v-model="calibrationPlanForm.planDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item label="执行人">
          <el-input v-model="calibrationPlanForm.executor" placeholder="输入执行人" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="calibrationPlanForm.remark"
            type="textarea"
            placeholder="输入备注"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="calibrationPlanDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCalibrationPlan">确定</el-button>
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
      this.$message.info(`查看计量器具 ${meter.meterName} 的详细信息`)
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
        this.$message.error('请填写完整的计划信息')
        return
      }
      
      const meter = this.meters.find(m => m.meterId === this.calibrationPlanForm.meterId)
      if (!meter) {
        this.$message.error('选择的计量器具不存在')
        return
      }
      
      // 模拟添加校准计划
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
      this.$message.success('校准计划已添加')
      this.calibrationPlanDialogVisible = false
    },
    viewPlanDetail(plan) {
      this.$message.info(`查看校准计划 ${plan.planId} 的详细信息`)
    },
    executePlan(plan) {
      // 模拟执行校准计划
      plan.status = '执行中'
      this.$message.success(`开始执行校准计划 ${plan.planId}`)
      
      // 模拟执行完成
      setTimeout(() => {
        plan.status = '已完成'
        this.$message.success(`校准计划 ${plan.planId} 已执行完成`)
        
        // 添加校准记录
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
        
        // 更新计量器具状态
        const meter = this.meters.find(m => m.meterId === plan.meterId)
        if (meter) {
          meter.lastCalibrationDate = newRecord.calibrationDate
          meter.status = newRecord.calibrationResult === '合格' ? '正常' : '异常'
          // 计算下次校准日期（6个月后）
          const nextDate = new Date(newRecord.calibrationDate)
          nextDate.setMonth(nextDate.getMonth() + 6)
          meter.nextCalibrationDate = nextDate.toISOString().split('T')[0]
        }
      }, 1000)
    },
    viewRecordDetail(record) {
      this.$message.info(`查看校准记录 ${record.recordId} 的详细信息`)
    },
    exportRecords() {
      this.$message.success('校准记录已导出')
    }
  },
  mounted() {
    // 这里可以初始化图表
    // 由于没有引入echarts，暂时不实现图表功能
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
