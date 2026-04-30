<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="任务编码" prop="taskCode">
        <el-input
          v-model="queryParams.taskCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="源系统" prop="sourceSystem">
        <el-select v-model="queryParams.sourceSystem" :placeholder="$t('common.pleaseSelect')" clearable style="width: 120px">
          <el-option label="MES" value="MES" />
          <el-option label="ERP" value="ERP" />
          <el-option label="环保" value="ENV" />
          <el-option label="消防" value="FIRE" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="enabled">
        <el-select v-model="queryParams.enabled" :placeholder="$t('common.pleaseSelect')" clearable style="width: 100px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="2" />
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
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
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-video-play"
          size="mini"
          @click="handleExecute"
          :disabled="single"
        >立即执行</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编码" align="center" prop="taskCode" width="150" />
      <el-table-column label="任务名称" align="center" prop="taskName" :show-overflow-tooltip="true" />
      <el-table-column label="任务类型" align="center" prop="taskType" width="150" />
      <el-table-column label="源系统" align="center" prop="sourceSystem" width="100">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.sourceSystem }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="目标系统" align="center" prop="targetSystem" width="100">
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.targetSystem }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="同步类型" align="center" prop="syncType" width="100">
        <template slot-scope="scope">
          {{ scope.row.syncType === 1 ? '全量' : '增量' }}
        </template>
      </el-table-column>
      <el-table-column label="同步频率" align="center" prop="syncFrequency" width="120" />
      <el-table-column :label="$t('common.status')" align="center" prop="enabled" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.enabled === 1 ? 'success' : 'danger'">
            {{ scope.row.enabled === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >{{ $t('common.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.enabled === 1 ? 'el-icon-close' : 'el-icon-check'"
            @click="handleChangeStatus(scope.row)"
          >{{ scope.row.enabled === 1 ? '禁用' : '启用' }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            @click="handleExecute(scope.row)"
          >执行</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >{{ $t('common.delete') }}</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务编码" prop="taskCode">
              <el-input v-model="form.taskCode" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="taskName">
              <el-input v-model="form.taskName" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务类型" prop="taskType">
              <el-input v-model="form.taskType" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="同步频率" prop="syncFrequency">
              <el-select v-model="form.syncFrequency" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                <el-option label="每分钟" value="MINUTE" />
                <el-option label="每小时" value="HOURLY" />
                <el-option label="每天" value="DAILY" />
                <el-option label="每周" value="WEEKLY" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="源系统" prop="sourceSystem">
              <el-select v-model="form.sourceSystem" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                <el-option label="MES" value="MES" />
                <el-option label="ERP" value="ERP" />
                <el-option label="ENV" value="ENV" />
                <el-option label="FIRE" value="FIRE" />
                <el-option label="EMS" value="EMS" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="目标系统" prop="targetSystem">
              <el-select v-model="form.targetSystem" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                <el-option label="MES" value="MES" />
                <el-option label="ERP" value="ERP" />
                <el-option label="ENV" value="ENV" />
                <el-option label="FIRE" value="FIRE" />
                <el-option label="EMS" value="EMS" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="同步类型" prop="syncType">
              <el-radio-group v-model="form.syncType">
                <el-radio :label="1">全量同步</el-radio>
                <el-radio :label="2">增量同步</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Cron表达式" prop="cronExpression">
              <el-input v-model="form.cronExpression" placeholder="如: 0 */5 * * * ?" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="字段映射" prop="fieldMapping">
          <el-input v-model="form.fieldMapping" type="textarea" :rows="3" placeholder="JSON格式的字段映射" />
        </el-form-item>
        <el-form-item label="转换规则" prop="transformRules">
          <el-input v-model="form.transformRules" type="textarea" :rows="3" placeholder="JSON格式的转换规则" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="2">禁用</el-radio>
          </el-radio-group>
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
import { listSyncTask, getSyncTask, addSyncTask, updateSyncTask, delSyncTask, enableSyncTask, disableSyncTask } from '@/api/system/integration/sync'
import { executeSyncTask } from '@/api/system/integration/engine'

export default {
  name: 'SyncTask',
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      taskList: [],
      title: '',
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: undefined,
        taskName: undefined,
        sourceSystem: undefined,
        enabled: undefined
      },
      form: {},
      rules: {
        taskCode: [
          { required: true, message: '任务编码不能为空', trigger: 'blur' }
        ],
        taskName: [
          { required: true, message: '任务名称不能为空', trigger: 'blur' }
        ],
        sourceSystem: [
          { required: true, message: '源系统不能为空', trigger: 'change' }
        ],
        targetSystem: [
          { required: true, message: '目标系统不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listSyncTask(this.queryParams).then(response => {
        this.taskList = response.data
        this.total = this.taskList.length
        this.loading = false
      })
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加同步任务'
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSyncTask(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '编辑同步任务'
      })
    },
    handleChangeStatus(row) {
      const enabled = row.enabled === 1 ? 2 : 1
      this.$confirm('确认要' + (enabled === 1 ? '启用' : '禁用') + '该任务吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (enabled === 1) {
          return enableSyncTask(row.id)
        } else {
          return disableSyncTask(row.id)
        }
      }).then(() => {
        this.getList()
        this.$message.success('操作成功')
      })
    },
    handleExecute(row) {
      const id = row.id || this.ids[0]
      this.$confirm('确认要立即执行该同步任务吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return executeSyncTask(id)
      }).then(() => {
        this.$message.success('任务已开始执行')
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除选中的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return delSyncTask(ids)
      }).then(() => {
        this.getList()
        this.$message.success('删除成功')
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateSyncTask(this.form).then(response => {
              this.$message.success('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSyncTask(this.form).then(response => {
              this.$message.success('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: undefined,
        taskCode: undefined,
        taskName: undefined,
        taskType: undefined,
        sourceSystem: undefined,
        targetSystem: undefined,
        syncType: 1,
        syncFrequency: 'HOURLY',
        cronExpression: '0 0 * * * ?',
        fieldMapping: undefined,
        transformRules: undefined,
        description: undefined,
        enabled: 1
      }
      this.resetForm('form')
    }
  }
}
</script>
