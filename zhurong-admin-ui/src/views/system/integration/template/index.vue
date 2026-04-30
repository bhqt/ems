<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板编码" prop="templateCode">
        <el-input
          v-model="queryParams.templateCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板类型" prop="templateType">
        <el-select v-model="queryParams.templateType" :placeholder="$t('common.pleaseSelect')" clearable style="width: 150px">
          <el-option label="MES同步" value="MES_SYNC" />
          <el-option label="ERP同步" value="ERP_SYNC" />
          <el-option label="环保系统同步" value="ENV_SYNC" />
          <el-option label="消防系统同步" value="FIRE_SYNC" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable style="width: 120px">
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
          icon="el-icon-document-copy"
          size="mini"
          :disabled="single"
          @click="handleCopy"
        >复制</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模板名称" align="center" prop="templateName" :show-overflow-tooltip="true" />
      <el-table-column label="模板编码" align="center" prop="templateCode" width="120" />
      <el-table-column label="模板类型" align="center" prop="templateType" width="120">
        <template slot-scope="scope">
          <el-tag :type="getTemplateTypeTag(scope.row.templateType)">
            {{ getTemplateTypeName(scope.row.templateType) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="源系统" align="center" prop="sourceSystem" width="100" />
      <el-table-column label="目标系统" align="center" prop="targetSystem" width="100" />
      <el-table-column :label="$t('common.status')" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
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
            :icon="scope.row.status === 1 ? 'el-icon-close' : 'el-icon-check'"
            @click="handleChangeStatus(scope.row)"
          >{{ scope.row.status === 1 ? '禁用' : '启用' }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            @click="handleCopy(scope.row)"
          >复制</el-button>
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
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="form.templateName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item label="模板编码" prop="templateCode">
          <el-input v-model="form.templateCode" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item label="模板类型" prop="templateType">
          <el-select v-model="form.templateType" :placeholder="$t('common.pleaseSelect')">
            <el-option label="MES同步" value="MES_SYNC" />
            <el-option label="ERP同步" value="ERP_SYNC" />
            <el-option label="环保系统同步" value="ENV_SYNC" />
            <el-option label="消防系统同步" value="FIRE_SYNC" />
          </el-select>
        </el-form-item>
        <el-form-item label="源系统" prop="sourceSystem">
          <el-select v-model="form.sourceSystem" :placeholder="$t('common.pleaseSelect')">
            <el-option label="MES" value="MES" />
            <el-option label="ERP" value="ERP" />
            <el-option label="环保系统" value="ENV" />
            <el-option label="消防系统" value="FIRE" />
          </el-select>
        </el-form-item>
        <el-form-item label="目标系统" prop="targetSystem">
          <el-select v-model="form.targetSystem" :placeholder="$t('common.pleaseSelect')">
            <el-option label="MES" value="MES" />
            <el-option label="ERP" value="ERP" />
            <el-option label="环保系统" value="ENV" />
            <el-option label="消防系统" value="FIRE" />
            <el-option label="本系统" value="LOCAL" />
          </el-select>
        </el-form-item>
        <el-form-item label="字段映射" prop="fieldMapping">
          <el-input v-model="form.fieldMapping" type="textarea" :placeholder="$t('common.pleaseInput')" :rows="4" />
        </el-form-item>
        <el-form-item label="转换规则" prop="transformRules">
          <el-input v-model="form.transformRules" type="textarea" :placeholder="$t('common.pleaseInput')" :rows="4" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
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

    <el-dialog title="复制模板" :visible.sync="copyOpen" width="500px" append-to-body>
      <el-form ref="copyForm" :model="copyForm" :rules="copyRules" label-width="100px">
        <el-form-item label="原模板名称" disabled>
          <el-input v-model="copyForm.originalName" disabled />
        </el-form-item>
        <el-form-item label="新模板名称" prop="newTemplateName">
          <el-input v-model="copyForm.newTemplateName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item label="新模板编码" prop="newTemplateCode">
          <el-input v-model="copyForm.newTemplateCode" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCopyForm">确 定</el-button>
        <el-button @click="copyCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSyncTemplate, getSyncTemplate, addSyncTemplate, updateSyncTemplate, delSyncTemplate, copySyncTemplate } from '@/api/system/integration/template'

export default {
  name: 'SyncTemplate',
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      templateList: [],
      title: '',
      open: false,
      copyOpen: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateName: undefined,
        templateCode: undefined,
        templateType: undefined,
        status: undefined
      },
      form: {},
      copyForm: {
        originalName: '',
        newTemplateName: '',
        newTemplateCode: ''
      },
      rules: {
        templateName: [
          { required: true, message: '模板名称不能为空', trigger: 'blur' }
        ],
        templateCode: [
          { required: true, message: '模板编码不能为空', trigger: 'blur' }
        ],
        templateType: [
          { required: true, message: '模板类型不能为空', trigger: 'blur' }
        ],
        sourceSystem: [
          { required: true, message: '源系统不能为空', trigger: 'blur' }
        ],
        targetSystem: [
          { required: true, message: '目标系统不能为空', trigger: 'blur' }
        ]
      },
      copyRules: {
        newTemplateName: [
          { required: true, message: '新模板名称不能为空', trigger: 'blur' }
        ],
        newTemplateCode: [
          { required: true, message: '新模板编码不能为空', trigger: 'blur' }
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
      listSyncTemplate(this.queryParams).then(response => {
        this.templateList = response.data
        this.total = this.templateList.length
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
      this.title = '添加同步模板'
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSyncTemplate(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '编辑同步模板'
      })
    },
    handleChangeStatus(row) {
      const status = row.status === 1 ? 2 : 1
      this.$confirm('确认要' + (status === 1 ? '启用' : '禁用') + '该模板吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        row.status = status
        return updateSyncTemplate(row)
      }).then(() => {
        this.getList()
        this.$message.success('操作成功')
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除选中的模板?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return delSyncTemplate(ids)
      }).then(() => {
        this.getList()
        this.$message.success('删除成功')
      })
    },
    handleCopy(row) {
      const id = row.id || this.ids
      getSyncTemplate(id).then(response => {
        this.copyForm.originalName = response.data.templateName
        this.copyForm.copyId = response.data.id
        this.copyForm.newTemplateName = ''
        this.copyForm.newTemplateCode = ''
        this.copyOpen = true
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateSyncTemplate(this.form).then(response => {
              this.$message.success('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSyncTemplate(this.form).then(response => {
              this.$message.success('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    submitCopyForm() {
      this.$refs['copyForm'].validate(valid => {
        if (valid) {
          copySyncTemplate({
            id: this.copyForm.copyId,
            newTemplateName: this.copyForm.newTemplateName,
            newTemplateCode: this.copyForm.newTemplateCode
          }).then(response => {
            this.$message.success('复制成功')
            this.copyOpen = false
            this.getList()
          })
        }
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    copyCancel() {
      this.copyOpen = false
      this.copyForm = {
        originalName: '',
        newTemplateName: '',
        newTemplateCode: ''
      }
    },
    reset() {
      this.form = {
        id: undefined,
        templateName: undefined,
        templateCode: undefined,
        templateType: undefined,
        sourceSystem: undefined,
        targetSystem: undefined,
        fieldMapping: undefined,
        transformRules: undefined,
        description: undefined,
        status: 1
      }
      this.resetForm('form')
    },
    getTemplateTypeTag(type) {
      const tagMap = {
        'MES_SYNC': 'primary',
        'ERP_SYNC': 'success',
        'ENV_SYNC': 'warning',
        'FIRE_SYNC': 'danger'
      }
      return tagMap[type] || 'info'
    },
    getTemplateTypeName(type) {
      const nameMap = {
        'MES_SYNC': 'MES同步',
        'ERP_SYNC': 'ERP同步',
        'ENV_SYNC': '环保系统同步',
        'FIRE_SYNC': '消防系统同步'
      }
      return nameMap[type] || type
    }
  }
}
</script>
