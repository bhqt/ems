<template>
  <div class="energy-container">
    <el-card>
      <template slot="header">
        <div class="card-header">
          <span>{{ $t('energyModule.title') }}</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <!-- 批次管理 -->
        <el-tab-pane :label="$t('energyModule.batch.title')" name="batch">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="batchDateRange"
                type="daterange"
                :range-separator="$t('common.to')"
                :start-placeholder="$t('common.startDate')"
                :end-placeholder="$t('common.endDate')"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-input
                v-model="batchSearch"
                :placeholder="$t('energyModule.batch.placeholder.searchBatch')"
                prefix-icon="el-icon-search"
              />
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="addBatchRecord">{{ $t('button.add') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredBatches" style="width: 100%">
            <el-table-column prop="batchId" :label="$t('energyModule.batch.batchId')" width="120" />
            <el-table-column prop="productName" :label="$t('energyModule.batch.productName')" />
            <el-table-column prop="batchNumber" :label="$t('energyModule.batch.batchNumber')" />
            <el-table-column prop="productionDate" :label="$t('energyModule.batch.productionDate')" width="180" />
            <el-table-column prop="productionLine" :label="$t('energyModule.batch.productionLine')" />
            <el-table-column prop="energyConsumption" :label="$t('energyModule.batch.energyConsumption')" width="100" />
            <el-table-column prop="status" :label="$t('common.status')" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.status === $t('message.completed') ? 'success' : 'warning'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column :label="$t('common.operate')" width="150">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewBatchDetail(scope.row)"
                >
                  {{ $t('button.view') }}
                </el-button>
                <el-button
                  type="success"
                  size="small"
                  @click="calculateEnergyConsumption(scope.row)"
                  style="margin-left: 5px;"
                >
                  {{ $t('energyModule.batch.energyConsumption') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        <!-- 能效标杆管理 -->
        <el-tab-pane :label="$t('energyModule.benchmark.title')" name="benchmark">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="12">
              <el-input
                v-model="benchmarkSearch"
                :placeholder="$t('energyModule.benchmark.placeholder.searchStandard')"
                prefix-icon="el-icon-search"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="12">
              <el-button type="primary" @click="addBenchmarkStandard">{{ $t('button.add') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredBenchmarks" style="width: 100%">
            <el-table-column prop="standardId" :label="$t('energyModule.benchmark.standardId')" width="120" />
            <el-table-column prop="standardName" :label="$t('energyModule.benchmark.standardName')" />
            <el-table-column prop="energyType" :label="$t('energyModule.benchmark.energyType')" />
            <el-table-column prop="benchmarkValue" :label="$t('energyModule.benchmark.benchmarkValue')" width="100" />
            <el-table-column prop="unit" :label="$t('energyModule.benchmark.unit')" width="80" />
            <el-table-column prop="applicableRange" :label="$t('energyModule.benchmark.applicableRange') || '适用范围'" />
            <el-table-column prop="status" :label="$t('common.status')" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.status === $t('common.enable') ? 'success' : 'danger'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column :label="$t('common.operate')" width="150">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewBenchmarkDetail(scope.row)"
                >
                  {{ $t('button.view') }}
                </el-button>
                <el-button
                  type="success"
                  size="small"
                  @click="toggleBenchmarkStatus(scope.row)"
                  style="margin-left: 5px;"
                >
                  {{ scope.row.status === $t('common.enable') ? $t('button.disable') : $t('button.enable') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        <!-- 能源平衡管理 -->
        <el-tab-pane :label="$t('energyModule.balance.title')" name="balance">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="balanceDate"
                type="date"
                :placeholder="$t('common.selectDate')"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-select v-model="balanceEnergyType" :placeholder="$t('energyModule.benchmark.placeholder.selectType')">
                <el-option :label="$t('common.all')" value="" />
                <el-option :label="$t('energyModule.energyType.electricity')" value="电力" />
                <el-option :label="$t('energyModule.energyType.water')" value="水" />
                <el-option :label="$t('energyModule.energyType.steam')" value="蒸汽" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="calculateEnergyBalance">{{ $t('energyModule.balance.calculate') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="energyBalances" style="width: 100%">
            <el-table-column prop="balanceId" :label="$t('energyModule.balance.balanceId')" width="120" />
            <el-table-column prop="energyType" :label="$t('energyModule.benchmark.energyType')" />
            <el-table-column prop="totalSupply" :label="$t('energyModule.balance.totalSupply')" width="100" />
            <el-table-column prop="totalConsumption" :label="$t('energyModule.balance.totalConsumption')" width="100" />
            <el-table-column prop="balanceAmount" :label="$t('energyModule.balance.balanceAmount')" width="100">
              <template slot-scope="scope">
                <el-tag :type="Math.abs(scope.row.balanceAmount) < 0.01 ? 'success' : 'danger'">
                  {{ scope.row.balanceAmount.toFixed(2) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="balanceRate" :label="$t('energyModule.balance.balanceRate')" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.balanceRate >= 99 ? 'success' : 'warning'">
                  {{ scope.row.balanceRate.toFixed(2) }}%
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="balanceDate" :label="$t('energyModule.balance.balanceDate')" width="180" />
            <el-table-column :label="$t('common.operate')" width="120">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewBalanceDetail(scope.row)"
                >
                  {{ $t('button.view') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        <!-- 能源质量管理 -->
        <el-tab-pane :label="$t('energyModule.quality.title')" name="quality">
          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8">
              <el-date-picker
                v-model="qualityDateRange"
                type="daterange"
                :range-separator="$t('common.to')"
                :start-placeholder="$t('common.startDate')"
                :end-placeholder="$t('common.endDate')"
                style="width: 100%;"
              />
            </el-col>
            <el-col :span="8">
              <el-select v-model="qualityEnergyType" :placeholder="$t('energyModule.benchmark.placeholder.selectType')">
                <el-option :label="$t('common.all')" value="" />
                <el-option :label="$t('energyModule.energyType.electricity')" value="电力" />
                <el-option :label="$t('energyModule.energyType.water')" value="水" />
                <el-option :label="$t('energyModule.energyType.steam')" value="蒸汽" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" @click="addEnergyQuality">{{ $t('button.add') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="filteredQualityRecords" style="width: 100%">
            <el-table-column prop="qualityId" :label="$t('energyModule.quality.qualityId')" width="120" />
            <el-table-column prop="energyType" :label="$t('energyModule.benchmark.energyType')" />
            <el-table-column prop="qualityIndex" :label="$t('energyModule.quality.qualityIndex')" />
            <el-table-column prop="standardValue" :label="$t('energyModule.quality.standardValue')" width="100" />
            <el-table-column prop="actualValue" :label="$t('energyModule.quality.actualValue')" width="100" />
            <el-table-column prop="deviation" :label="$t('energyModule.quality.deviation')" width="100">
              <template slot-scope="scope">
                <el-tag :type="Math.abs(scope.row.deviation) < 0.01 ? 'success' : 'warning'">
                  {{ scope.row.deviation.toFixed(2) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" :label="$t('common.status')" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.status === $t('common.pass') ? 'success' : 'danger'">
                  {{ scope.row.status === '合格' ? $t('common.pass') : $t('common.fail') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="detectionTime" :label="$t('energyModule.quality.detectionTime')" width="180" />
            <el-table-column :label="$t('common.operate')" width="120">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size="small"
                  @click="viewQualityDetail(scope.row)"
                >
                  {{ $t('button.view') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    
    <!-- 新增批次记录对话框 -->
    <el-dialog
      :title="$t('button.add') + $t('energyModule.batch.title')"
      :visible.sync="batchDialogVisible"
      width="500px"
    >
      <el-form :model="batchForm" label-width="100px">
        <el-form-item :label="$t('energyModule.batch.productName')">
          <el-input v-model="batchForm.productName" :placeholder="$t('energyModule.batch.placeholder.searchBatch')" />
        </el-form-item>
        <el-form-item :label="$t('energyModule.batch.batchNumber')">
          <el-input v-model="batchForm.batchNumber" :placeholder="$t('placeholder.input') + $t('energyModule.batch.batchNumber')" />
        </el-form-item>
        <el-form-item :label="$t('energyModule.batch.productionDate')">
          <el-date-picker
            v-model="batchForm.productionDate"
            type="date"
            :placeholder="$t('common.selectDate')"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item :label="$t('energyModule.batch.productionLine')">
          <el-input v-model="batchForm.productionLine" :placeholder="$t('placeholder.input') + $t('energyModule.batch.productionLine')" />
        </el-form-item>
        <el-form-item :label="$t('energyModule.batch.expectedOutput')">
          <el-input v-model="batchForm.expectedOutput" :placeholder="$t('placeholder.input') + $t('energyModule.batch.expectedOutput')" />
        </el-form-item>
        <el-form-item :label="$t('energyModule.batch.remark')">
          <el-input
            v-model="batchForm.remark"
            type="textarea"
            :placeholder="$t('placeholder.input') + $t('energyModule.batch.remark')"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="batchDialogVisible = false">{{ $t('button.cancel') }}</el-button>
        <el-button type="primary" @click="submitBatchRecord">{{ $t('button.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EnergyManagement',
  data() {
    return {
      activeTab: 'batch',
      batchDateRange: null,
      batchSearch: '',
      batches: [
        {
          batchId: 'BATCH001',
          productName: '钢材A',
          batchNumber: '20260328001',
          productionDate: '2026-03-28',
          productionLine: '生产线1',
          energyConsumption: 1200,
          status: '已完成',
          remark: '正常生产'
        },
        {
          batchId: 'BATCH002',
          productName: '钢材B',
          batchNumber: '20260328002',
          productionDate: '2026-03-28',
          productionLine: '生产线2',
          energyConsumption: 1500,
          status: '已完成',
          remark: '正常生产'
        },
        {
          batchId: 'BATCH003',
          productName: '钢材C',
          batchNumber: '20260329001',
          productionDate: '2026-03-29',
          productionLine: '生产线1',
          energyConsumption: 0,
          status: '生产中',
          remark: '正在生产'
        }
      ],
      benchmarkSearch: '',
      benchmarks: [
        {
          standardId: 'STD001',
          standardName: '钢材A能耗标杆',
          energyType: '电力',
          benchmarkValue: 1000,
          unit: 'kWh/t',
          applicableRange: '生产线1',
          status: '启用'
        },
        {
          standardId: 'STD002',
          standardName: '钢材B能耗标杆',
          energyType: '电力',
          benchmarkValue: 1200,
          unit: 'kWh/t',
          applicableRange: '生产线2',
          status: '启用'
        },
        {
          standardId: 'STD003',
          standardName: '钢材C能耗标杆',
          energyType: '电力',
          benchmarkValue: 1100,
          unit: 'kWh/t',
          applicableRange: '生产线1',
          status: '禁用'
        }
      ],
      balanceDate: '',
      balanceEnergyType: '',
      energyBalances: [
        {
          balanceId: 'BAL001',
          energyType: '电力',
          totalSupply: 10000,
          totalConsumption: 9950,
          balanceAmount: 50,
          balanceRate: 99.5,
          balanceDate: '2026-03-27'
        },
        {
          balanceId: 'BAL002',
          energyType: '水',
          totalSupply: 5000,
          totalConsumption: 4980,
          balanceAmount: 20,
          balanceRate: 99.6,
          balanceDate: '2026-03-27'
        },
        {
          balanceId: 'BAL003',
          energyType: '蒸汽',
          totalSupply: 3000,
          totalConsumption: 2950,
          balanceAmount: 50,
          balanceRate: 98.3,
          balanceDate: '2026-03-27'
        }
      ],
      qualityDateRange: null,
      qualityEnergyType: '',
      qualityRecords: [
        {
          qualityId: 'QUAL001',
          energyType: '电力',
          qualityIndex: '电压',
          standardValue: 380,
          actualValue: 382,
          deviation: 2,
          status: '合格',
          detectionTime: '2026-03-28 10:00:00',
          detectionLocation: '车间A'
        },
        {
          qualityId: 'QUAL002',
          energyType: '电力',
          qualityIndex: '频率',
          standardValue: 50,
          actualValue: 50.1,
          deviation: 0.1,
          status: '合格',
          detectionTime: '2026-03-28 10:00:00',
          detectionLocation: '车间A'
        },
        {
          qualityId: 'QUAL003',
          energyType: '水',
          qualityIndex: 'pH值',
          standardValue: 7.0,
          actualValue: 6.5,
          deviation: -0.5,
          status: '不合格',
          detectionTime: '2026-03-28 11:00:00',
          detectionLocation: '水源水区域'
        }
      ],
      batchDialogVisible: false,
      batchForm: {
        productName: '',
        batchNumber: '',
        productionDate: '',
        productionLine: '',
        expectedOutput: '',
        remark: ''
      }
    }
  },
  computed: {
    filteredBatches() {
      let result = this.batches
      if (this.batchSearch) {
        result = result.filter(batch => 
          batch.productName.includes(this.batchSearch) ||
          batch.batchNumber.includes(this.batchSearch)
        )
      }
      if (this.batchDateRange) {
        const startDate = new Date(this.batchDateRange[0])
        const endDate = new Date(this.batchDateRange[1])
        result = result.filter(batch => {
          const batchDate = new Date(batch.productionDate)
          return batchDate >= startDate && batchDate <= endDate
        })
      }
      return result
    },
    filteredBenchmarks() {
      if (!this.benchmarkSearch) {
        return this.benchmarks
      }
      return this.benchmarks.filter(benchmark => 
        benchmark.standardName.includes(this.benchmarkSearch) ||
        benchmark.energyType.includes(this.benchmarkSearch)
      )
    },
    filteredQualityRecords() {
      let result = this.qualityRecords
      if (this.qualityEnergyType) {
        result = result.filter(record => record.energyType === this.qualityEnergyType)
      }
      if (this.qualityDateRange) {
        const startDate = new Date(this.qualityDateRange[0])
        const endDate = new Date(this.qualityDateRange[1])
        result = result.filter(record => {
          const recordDate = new Date(record.detectionTime)
          return recordDate >= startDate && recordDate <= endDate
        })
      }
      return result
    }
  },
  methods: {
    viewBatchDetail(batch) {
      this.$message.info(`查看批次 ${batch.batchNumber} 的详细信息`)
    },
    calculateEnergyConsumption(batch) {
      // 模拟计算能耗
      batch.energyConsumption = Math.floor(Math.random() * 500) + 1000
      batch.status = '已完成'
      this.$message.success(`批次 ${batch.batchNumber} 的能耗已计算完成：${batch.energyConsumption} kWh`)
    },
    addBatchRecord() {
      this.batchForm = {
        productName: '',
        batchNumber: '',
        productionDate: '',
        productionLine: '',
        expectedOutput: '',
        remark: ''
      }
      this.batchDialogVisible = true
    },
    submitBatchRecord() {
      if (!this.batchForm.productName || !this.batchForm.batchNumber || !this.batchForm.productionDate || !this.batchForm.productionLine) {
        this.$message.error('请填写完整的批次信息')
        return
      }
      
      // 模拟添加批次记录
      const newBatch = {
        batchId: 'BATCH' + (this.batches.length + 1).toString().padStart(3, '0'),
        productName: this.batchForm.productName,
        batchNumber: this.batchForm.batchNumber,
        productionDate: this.batchForm.productionDate,
        productionLine: this.batchForm.productionLine,
        energyConsumption: 0,
        status: '生产中',
        remark: this.batchForm.remark
      }
      this.batches.push(newBatch)
      this.$message.success('批次记录已添加')
      this.batchDialogVisible = false
    },
    viewBenchmarkDetail(benchmark) {
      this.$message.info(`查看标杆标准 ${benchmark.standardName} 的详细信息`)
    },
    toggleBenchmarkStatus(benchmark) {
      benchmark.status = benchmark.status === '启用' ? '禁用' : '启用'
      this.$message.success(`标杆标准 ${benchmark.standardName} 已${benchmark.status === '启用' ? '启用' : '禁用'}`)
    },
    addBenchmarkStandard() {
      this.$message.info('新增标杆标准功能开发中')
    },
    calculateEnergyBalance() {
      // 模拟计算能源平衡
      this.$message.success('能源平衡计算已完成')
      
      // 模拟添加平衡记录
      const newBalance = {
        balanceId: 'BAL' + (this.energyBalances.length + 1).toString().padStart(3, '0'),
        energyType: this.balanceEnergyType || '电力',
        totalSupply: Math.floor(Math.random() * 1000) + 9000,
        totalConsumption: Math.floor(Math.random() * 900) + 8900,
        balanceAmount: 0,
        balanceRate: 0,
        balanceDate: this.balanceDate || new Date().toISOString().split('T')[0]
      }
      newBalance.balanceAmount = newBalance.totalSupply - newBalance.totalConsumption
      newBalance.balanceRate = (newBalance.totalConsumption / newBalance.totalSupply) * 100
      this.energyBalances.push(newBalance)
    },
    viewBalanceDetail(balance) {
      this.$message.info(`查看能源平衡 ${balance.balanceId} 的详细信息`)
    },
    addEnergyQuality() {
      this.$message.info('新增能源质量记录功能开发中')
    },
    viewQualityDetail(record) {
      this.$message.info(`查看能源质量记录 ${record.qualityId} 的详细信息`)
    }
  }
}
</script>

<style scoped>
.energy-container {
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
