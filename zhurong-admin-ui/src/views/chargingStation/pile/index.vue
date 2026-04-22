<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb-4">
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">充电桩总数</div>
            <div class="stat-value">{{ statistics.totalCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">正常状态</div>
            <div class="stat-value">{{ statistics.normalCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">停用状态</div>
            <div class="stat-value">{{ statistics.disabledCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">在线数量</div>
            <div class="stat-value">{{ statistics.onlineCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="hover">
      <el-form :inline="true" :model="searchForm" class="mb-4">
        <el-form-item label="充电桩名称">
          <el-input v-model="searchForm.name" placeholder="请输入充电桩名称" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="终端编码">
          <el-input v-model="searchForm.encoding" placeholder="请输入终端编码" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="品牌">
          <el-input v-model="searchForm.brand" placeholder="请输入品牌" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="型号">
          <el-input v-model="searchForm.model" placeholder="请输入型号" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable style="width: 150px;">
            <el-option label="正常" value="0"></el-option>
            <el-option label="停用" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetForm">重置</el-button>
          <el-button type="success" @click="handleAdd" icon="el-icon-plus">新增</el-button>
          <el-button type="warning" @click="handleExport">导出</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card" shadow="hover">
      <el-table :data="gridData" style="width: 100%" stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="pileId" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="充电桩名称" width="180"></el-table-column>
        <el-table-column prop="encoding" label="终端编码" width="150"></el-table-column>
        <el-table-column prop="brand" label="品牌" width="120"></el-table-column>
        <el-table-column prop="model" label="型号" width="120"></el-table-column>
        <el-table-column prop="merchantName" label="归属商户" width="150"></el-table-column>
        <el-table-column prop="stationName" label="归属电站" width="150"></el-table-column>
        <el-table-column prop="statusName" label="状态" width="100"></el-table-column>
        <el-table-column prop="workStatus" label="工作状态" width="100"></el-table-column>
        <el-table-column prop="remark" label="备注" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="info" size="small" @click="handleOpenOrClose(scope.row, scope.row.status === '0' ? '1' : '0')">{{ scope.row.status === '0' ? '停用' : '启用' }}</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.pileId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :total="total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="充电桩名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入充电桩名称" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="终端编码" prop="encoding">
          <el-input v-model="form.encoding" placeholder="请输入终端编码" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="终端类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入终端类型" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="归属商户" prop="merchantId">
          <el-input v-model="form.merchantId" placeholder="请输入归属商户ID" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="归属商户名" prop="merchantName">
          <el-input v-model="form.merchantName" placeholder="请输入归属商户名" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="归属电站" prop="stationId">
          <el-input v-model="form.stationId" placeholder="请输入归属电站ID" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="归属电站名称" prop="stationName">
          <el-input v-model="form.stationName" placeholder="请输入归属电站名称" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" style="width: 400px;">
            <el-option label="正常" value="0"></el-option>
            <el-option label="停用" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" style="width: 400px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPile, getPile, addPile, updatePile, delPile, openOrClosePile, getChargingPileStatistics, exportChargingPileList } from '@/api/chargingStation/pile'

export default {
  name: 'ChargingPile',
  data() {
    return {
      // 搜索表单
      searchForm: {
        name: '',
        encoding: '',
        brand: '',
        model: '',
        status: ''
      },
      // 表格数据
      gridData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // 统计数据
      statistics: {
        totalCount: 0,
        normalCount: 0,
        disabledCount: 0,
        onlineCount: 0
      },
      // 对话框
      dialogVisible: false,
      dialogTitle: '',
      form: {},
      rules: {
        name: [{ required: true, message: '请输入充电桩名称', trigger: 'blur' }],
        encoding: [{ required: true, message: '请输入终端编码', trigger: 'blur' }],
        type: [{ required: true, message: '请输入终端类型', trigger: 'blur' }],
        brand: [{ required: true, message: '请输入品牌', trigger: 'blur' }],
        model: [{ required: true, message: '请输入型号', trigger: 'blur' }],
        status: [{ required: true, message: '请选择状态', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
    this.getStatistics()
  },
  methods: {
    // 获取列表
    getList() {
      const params = {
        ...this.searchForm,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      listPile(params).then(response => {
        this.gridData = response.rows
        this.total = response.total
      })
    },
    // 获取统计信息
    getStatistics() {
      getChargingPileStatistics().then(response => {
        this.statistics = response.data
      })
    },
    // 搜索
    handleSearch() {
      this.pageNum = 1
      this.getList()
    },
    // 重置
    resetForm() {
      this.searchForm = {
        name: '',
        encoding: '',
        brand: '',
        model: '',
        status: ''
      }
      this.pageNum = 1
      this.getList()
    },
    // 分页
    handleSizeChange(val) {
      this.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.getList()
    },
    // 新增
    handleAdd() {
      this.dialogTitle = '新增充电桩'
      this.form = {
        status: '0'
      }
      this.dialogVisible = true
    },
    // 编辑
    handleEdit(row) {
      this.dialogTitle = '编辑充电桩'
      getPile(row.pileId).then(response => {
        this.form = response.data
        this.dialogVisible = true
      })
    },
    // 提交
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.pileId) {
            // 编辑
            updatePile(this.form).then(response => {
              this.$message.success('修改成功')
              this.dialogVisible = false
              this.getList()
              this.getStatistics()
            })
          } else {
            // 新增
            addPile(this.form).then(response => {
              this.$message.success('新增成功')
              this.dialogVisible = false
              this.getList()
              this.getStatistics()
            })
          }
        }
      })
    },
    // 启用/停用
    handleOpenOrClose(row, status) {
      this.$confirm(`确定要${status === '0' ? '启用' : '停用'}该充电桩吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        openOrClosePile({ pileId: row.pileId, status: status }).then(response => {
          this.$message.success(`${status === '0' ? '启用' : '停用'}成功`)
          this.getList()
          this.getStatistics()
        })
      })
    },
    // 删除
    handleDelete(pileId) {
      this.$confirm('确定要删除该充电桩吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delPile(pileId).then(response => {
          this.$message.success('删除成功')
          this.getList()
          this.getStatistics()
        })
      })
    },
    // 导出
    handleExport() {
      exportChargingPileList(this.searchForm).then(response => {
        const blob = new Blob([response], { type: 'application/vnd.ms-excel' })
        const url = URL.createObjectURL(blob)
        const link = document.createElement('a')
        link.href = url
        link.download = '充电桩信息.xlsx'
        link.click()
        URL.revokeObjectURL(url)
      })
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.stat-card {
  height: 100px;
}

.stat-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.stat-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.search-card {
  margin-bottom: 20px;
}

.table-card {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>
