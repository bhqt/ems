<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">微电网总数</div>
          <div class="stat-value">{{ statistics.totalCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">并网数量</div>
          <div class="stat-value text-success">{{ statistics.gridConnectedCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">离网数量</div>
          <div class="stat-value text-warning">{{ statistics.offGridCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">总容量(kW)</div>
          <div class="stat-value">{{ statistics.totalCapacity || 0 }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="微电网名称" prop="gridName">
        <el-input
          v-model="queryParams.gridName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微电网编码" prop="gridCode">
        <el-input
          v-model="queryParams.gridCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运行模式" prop="runMode">
        <el-select v-model="queryParams.runMode" :placeholder="$t('common.pleaseSelect')" clearable size="small">
          <el-option
            v-for="dict in runModeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="系统状态" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="gridList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="80" />
      <el-table-column label="微电网名称" align="center" prop="gridName" :show-overflow-tooltip="true" />
      <el-table-column label="微电网编码" align="center" prop="gridCode" />
      <el-table-column label="微电网类型" align="center" prop="gridType">
        <template slot-scope="scope">
          <el-tag>{{ getGridTypeLabel(scope.row.gridType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="所属区域ID" align="center" prop="areaId" />
      <el-table-column label="光伏容量(kW)" align="center" prop="pvCapacity" />
      <el-table-column label="储能容量(kWh)" align="center" prop="storageCapacity" />
      <el-table-column label="负荷容量(kW)" align="center" prop="loadCapacity" />
      <el-table-column label="运行模式" align="center" prop="runMode">
        <template slot-scope="scope">
          <el-tag :type="getRunModeType(scope.row.runMode)">{{ getRunModeLabel(scope.row.runMode) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="系统状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="manager" />
      <el-table-column label="联系电话" align="center" prop="contactPhone" />
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">{{ $t('common.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改微电网对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="微电网名称" prop="gridName">
              <el-input v-model="form.gridName" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="微电网编码" prop="gridCode">
              <el-input v-model="form.gridCode" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="微电网类型" prop="gridType">
              <el-select v-model="form.gridType" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in gridTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属区域ID" prop="areaId">
              <el-input-number v-model="form.areaId" :min="0" style="width: 100%" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="光伏装机容量" prop="pvCapacity">
              <el-input-number v-model="form.pvCapacity" :min="0" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="储能容量(kWh)" prop="storageCapacity">
              <el-input-number v-model="form.storageCapacity" :min="0" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负荷容量(kW)" prop="loadCapacity">
              <el-input-number v-model="form.loadCapacity" :min="0" :precision="2" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="运行模式" prop="runMode">
              <el-select v-model="form.runMode" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in runModeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="系统状态" prop="status">
              <el-select v-model="form.status" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装日期" prop="installDate">
              <el-date-picker v-model="form.installDate" type="date" :placeholder="$t('common.pleaseSelect')" style="width: 100%" value-format="yyyy-MM-dd" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="并网日期" prop="gridDate">
              <el-date-picker v-model="form.gridDate" type="date" :placeholder="$t('common.pleaseSelect')" style="width: 100%" value-format="yyyy-MM-dd" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="manager">
              <el-input v-model="form.manager" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经度" prop="longitude">
              <el-input-number v-model="form.longitude" :precision="6" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="纬度" prop="latitude">
              <el-input-number v-model="form.latitude" :precision="6" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMicroGrid, getMicroGrid, addMicroGrid, updateMicroGrid, deleteMicroGrid, exportMicroGrid, getMicroGridStatistics } from '@/api/newenergy/microGrid'

export default {
  name: 'MicroGrid',
  data() {
    return {
      loading: true,
      ids: [],
      showSearch: true,
      total: 0,
      gridList: [],
      title: '',
      open: false,
      statistics: {},
      gridTypeOptions: [
        { dictValue: '1', dictLabel: '独立型' },
        { dictValue: '2', dictLabel: '并网型' }
      ],
      runModeOptions: [
        { dictValue: '1', dictLabel: '并网' },
        { dictValue: '2', dictLabel: '离网' },
        { dictValue: '3', dictLabel: '并离网切换' }
      ],
      statusOptions: [
        { dictValue: '0', dictLabel: '停用' },
        { dictValue: '1', dictLabel: '正常' },
        { dictValue: '2', dictLabel: '故障' },
        { dictValue: '3', dictLabel: '维护' }
      ],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gridName: null,
        gridCode: null,
        runMode: null,
        status: null
      },
      form: {},
      rules: {
        gridName: [{ required: true, message: '微电网名称不能为空', trigger: 'blur' }],
        gridCode: [{ required: true, message: '微电网编码不能为空', trigger: 'blur' }],
        gridType: [{ required: true, message: '微电网类型不能为空', trigger: 'change' }],
        status: [{ required: true, message: '系统状态不能为空', trigger: 'change' }],
        runMode: [{ required: true, message: '运行模式不能为空', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
    this.getStatistics()
  },
  methods: {
    getList() {
      this.loading = true
      listMicroGrid(this.queryParams).then(response => {
        this.gridList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getStatistics() {
      getMicroGridStatistics().then(response => {
        this.statistics = response.data
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        gridName: null,
        gridCode: null,
        gridType: '2',
        areaId: null,
        longitude: null,
        latitude: null,
        pvCapacity: null,
        storageCapacity: null,
        loadCapacity: null,
        installDate: null,
        gridDate: null,
        status: '1',
        runMode: '1',
        manager: null,
        contactPhone: null,
        remark: null
      }
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加微电网'
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMicroGrid(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改微电网'
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMicroGrid(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
              this.getStatistics()
            })
          } else {
            addMicroGrid(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
              this.getStatistics()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除微电网编号为"' + ids + '"的数据项？').then(() => {
        return deleteMicroGrid(ids)
      }).then(() => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    handleExport() {
      this.download('/system/newenergy/microgrid/export', {
        ...this.queryParams
      }, `microGrid_${new Date().getTime()}.xlsx`)
    },
    getGridTypeLabel(type) {
      const item = this.gridTypeOptions.find(s => s.dictValue === type)
      return item ? item.dictLabel : type
    },
    getRunModeLabel(runMode) {
      const item = this.runModeOptions.find(s => s.dictValue === runMode)
      return item ? item.dictLabel : runMode
    },
    getRunModeType(runMode) {
      switch (runMode) {
        case '1': return 'success'
        case '2': return 'warning'
        case '3': return 'info'
        default: return 'info'
      }
    },
    getStatusType(status) {
      switch (status) {
        case '0': return 'info'
        case '1': return 'success'
        case '2': return 'danger'
        case '3': return 'warning'
        default: return 'info'
      }
    },
    getStatusLabel(status) {
      const item = this.statusOptions.find(s => s.dictValue === status)
      return item ? item.dictLabel : status
    }
  }
}
</script>

<style scoped>
.stat-card {
  text-align: center;
  padding: 10px;
}
.stat-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}
.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}
.text-success {
  color: #67c23a;
}
.text-warning {
  color: #e6a23c;
}
.mb20 {
  margin-bottom: 20px;
}
.mb8 {
  margin-bottom: 8px;
}
</style>
