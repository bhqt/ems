<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.areaName')" prop="areaName">
        <el-input
          v-model="queryParams.areaName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.areaCode')" prop="areaCode">
        <el-input
          v-model="queryParams.areaCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
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
          v-hasPermi="['hospital:area:add']"
        >{{ $t('button.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['hospital:area:edit']"
        >{{ $t('button.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hospital:area:remove']"
        >{{ $t('button.delete') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="areaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('hospital.areaName')" align="center" prop="areaName" />
      <el-table-column :label="$t('hospital.areaCode')" align="center" prop="areaCode" width="120" />
      <el-table-column :label="$t('hospital.areaType')" align="center" prop="areaType" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.areaType === '0' ? 'primary' : 'info'" size="mini">
            {{ scope.row.areaType === '0' ? $t('hospital.areaTypeCampus') : $t('hospital.areaTypeBuilding') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.sort')" align="center" prop="sort" width="80" />
      <el-table-column :label="$t('common.status')" align="center" prop="status" width="90">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.remark')" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:area:edit']"
          >{{ $t('button.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:area:remove']"
          >{{ $t('button.delete') }}</el-button>
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

    <!-- 添加或修改院区对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item :label="$t('hospital.areaName')" prop="areaName">
          <el-input v-model="form.areaName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.areaCode')" prop="areaCode">
          <el-input v-model="form.areaCode" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.areaType')" prop="areaType">
          <el-radio-group v-model="form.areaType">
            <el-radio :label="'0'">{{ $t('hospital.areaTypeCampus') }}</el-radio>
            <el-radio :label="'1'">{{ $t('hospital.areaTypeBuilding') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('hospital.sort')" prop="sort">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArea, getArea, delArea, addArea, updateArea } from "@/api/hospital/area";

export default {
  name: "HospitalArea",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      areaList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        areaName: undefined,
        areaCode: undefined,
        status: undefined
      },
      form: {},
      rules: {
        areaName: [
          { required: true, message: this.$t('hospital.areaNameRequired'), trigger: "blur" }
        ],
        areaCode: [
          { required: true, message: this.$t('hospital.areaCodeRequired'), trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listArea(this.queryParams).then(response => {
        this.areaList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: undefined,
        areaName: undefined,
        areaCode: undefined,
        areaType: "0",
        parentId: 0,
        status: "0",
        sort: 0,
        remark: undefined
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('hospital.addArea');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getArea(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('hospital.editArea');
      });
    },
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateArea(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addArea(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.addSuccess'));
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('confirm.delete')).then(function() {
        return delArea(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('message.deleteSuccess'));
      }).catch(() => {});
    }
  }
};
</script>
