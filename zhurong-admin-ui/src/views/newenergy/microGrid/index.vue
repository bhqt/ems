<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb-4">
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">微电网总数</div>
            <div class="stat-value">{{ statistics.totalCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">并网数量</div>
            <div class="stat-value">{{ statistics.gridConnectedCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">离网数量</div>
            <div class="stat-value">{{ statistics.offGridCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-label">总容量(kW)</div>
            <div class="stat-value">{{ statistics.totalCapacity || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="hover">
      <el-form :inline="true" :model="searchForm" class="mb-4">
        <el-form-item label="微电网名称">
          <el-input v-model="searchForm.name" placeholder="请输入微电网名称" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="微电网编码">
          <el-input v-model="searchForm.code" placeholder="请输入微电网编码" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="所在区域">
          <el-input v-model="searchForm.region" placeholder="请输入所在区域" clearable style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="并网状态">
          <el-select v-model="searchForm.gridStatus" placeholder="请选择并网状态" clearable style="width: 150px;">
            <el-option label="并网" value="1"></el-option>
            <el-option label="离网" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运行状态">
          <el-select v-model="searchForm.status" placeholder="请选择运行状态" clearable style="width: 150px;">
            <el-option label="运行" value="1"></el-option>
            <el-option label="停用" value="0"></el-option>
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
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="微电网名称" width="180"></el-table-column>
        <el-table-column prop="code" label="微电网编码" width="150"></el-table-column>
        <el-table-column prop="region" label="所在区域" width="150"></el-table-column>
        <el-table-column prop="voltageLevel" label="电压等级(kV)" width="120"></el-table-column>
        <el-table-column prop="totalCapacity" label="总容量(kW)" width="120"></el-table-column>
        <el-table-column prop="maxLoad" label="最大负荷(kW)" width="120"></el-table-column>
        <el-table-column prop="gridStatusName" label="并网状态" width="100"></el-table-column>
        <el-table-column prop="statusName" label="运行状态" width="100"></el-table-column>
        <el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
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
        <el-form-item label="微电网名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入微电网名称" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="微电网编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入微电网编码" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="所在区域" prop="region">
          <el-input v-model="form.region" placeholder="请输入所在区域" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="电压等级(kV)" prop="voltageLevel">
          <el-input v-model="form.voltageLevel" type="number" placeholder="请输入电压等级" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="总容量(kW)" prop="totalCapacity">
          <el-input v-model="form.totalCapacity" type="number" placeholder="请输入总容量" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="最大负荷(kW)" prop="maxLoad">
          <el-input v-model="form.maxLoad" type="number" placeholder="请输入最大负荷" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="并网状态" prop="gridStatus">
          <el-select v-model="form.gridStatus" placeholder="请选择并网状态" style="width: 400px;">
            <el-option label="并网" value="1"></el-option>
            <el-option label="离网" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运行状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择运行状态" style="width: 400px;">
            <el-option label="运行" value="1"></el-option>
            <el-option label="停用" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" style="width: 400px;"></el-input>
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
import { listMicroGrid, getMicroGrid, addMicroGrid, updateMicroGrid, deleteMicroGrid, exportMicroGrid, getMicroGridStatistics } from '@/api/newenergy/microGrid'

export default {
  name: 'MicroGrid',
  data() {
    return {
      // 搜索表单
      searchForm: {
        name: '',
        code: '',
        region: '',
        gridStatus: '',
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
        gridConnectedCount: 0,
        offGridCount: 0,
        totalCapacity: 0
      },
      // 对话框
      dialogVisible: false,
      dialogTitle: '',
      form: {},
      rules: {
        name: [{ required: true, message: '请输入微电网名称', trigger: 'blur' }],
        code: [{ required: true, message: '请输入微电网编码', trigger: 'blur' }],
        region: [{ required: true, message: '请输入所在区域', trigger: 'blur' }],
        voltageLevel: [{ required: true, message: '请输入电压等级', trigger: 'blur' }],
        totalCapacity: [{ required: true, message: '请输入总容量', trigger: 'blur' }],
        maxLoad: [{ required: true, message: '请输入最大负荷', trigger: 'blur' }],
        gridStatus: [{ required: true, message: '请选择并网状态', trigger: 'blur' }],
        status: [{ required: true, message: '请选择运行状态', trigger: 'blur' }]
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
      listMicroGrid(params).then(response => {
        this.gridData = response.rows
        this.total = response.total
      })
    },
    // 获取统计信息
    getStatistics() {
      getMicroGridStatistics().then(response => {
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
        code: '',
        region: '',
        gridStatus: '',
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
      this.dialogTitle = '新增微电网'
      this.form = {
        gridStatus: 1,
        status: 1
      }
      this.dialogVisible = true
    },
    // 编辑
    handleEdit(row) {
      this.dialogTitle = '编辑微电网'
      getMicroGrid(row.id).then(response => {
        this.form = response.data
        this.dialogVisible = true
      })
    },
    // 提交
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            updateMicroGrid(this.form).then(response => {
              this.$message.success('修改成功')
              this.dialogVisible = false
              this.getList()
              this.getStatistics()
            })
          } else {
            // 新增
            addMicroGrid(this.form).then(response => {
              this.$message.success('新增成功')
              this.dialogVisible = false
              this.getList()
              this.getStatistics()
            })
          }
        }
      })
    },
    // 删除
    handleDelete(id) {
      this.$confirm('确定要删除该微电网吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteMicroGrid([id]).then(response => {
          this.$message.success('删除成功')
          this.getList()
          this.getStatistics()
        })
      })
    },
    // 导出
    handleExport() {
      exportMicroGrid(this.searchForm).then(response => {
        const blob = new Blob([response], { type: 'application/vnd.ms-excel' })
        const url = URL.createObjectURL(blob)
        const link = document.createElement('a')
        link.href = url
        link.download = '微电网信息.xlsx'
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
