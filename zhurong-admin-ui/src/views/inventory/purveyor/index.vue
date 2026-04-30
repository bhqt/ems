<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('inventoryModule.purveyor.purveyorCode')" prop="purveyorCode">
        <el-input
          v-model="queryParams.purveyorCode"
          :placeholder="$t('inventoryModule.purveyor.placeholder.inputPurveyorCode')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('inventoryModule.purveyor.purveyorName')" prop="purveyorName">
        <el-input
          v-model="queryParams.purveyorName"
          :placeholder="$t('inventoryModule.purveyor.placeholder.inputPurveyorName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('inventoryModule.purveyor.nature')" prop="nature">
        <el-select v-model="queryParams.nature" :placeholder="$t('inventoryModule.purveyor.placeholder.selectNature')">
          <el-option
            v-for="dict in dict.type.purveyor_nature"
            :key="dict.id"
            :label="dict.label"
            :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('inventoryModule.purveyor.supplyType')" prop="supplyType">
        <el-select v-model="queryParams.supplyType" :placeholder="$t('inventoryModule.purveyor.placeholder.selectSupplyType')">
          <el-option v-for="dict in dict.type.purveyor_supply_type"
            :key="dict.id"
            :label="dict.label"
            :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="联系人姓名" prop="contractName">
        <el-input
          v-model="queryParams.contractName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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
          v-hasPermi="['system:purveyor:add']"
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
          v-hasPermi="['system:purveyor:edit']"
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
          v-hasPermi="['system:purveyor:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:purveyor:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purveyorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="purveyorId" v-if="true"/> -->
      <el-table-column :label="$t('inventoryModule.purveyor.purveyorCode')" align="center" prop="purveyorCode" width="200px"/>
      <el-table-column :label="$t('inventoryModule.purveyor.purveyorName')" align="center" prop="purveyorName" />
      <el-table-column :label="$t('inventoryModule.purveyor.nature')" align="center" prop="nature" width="200">
        <template slot-scope="scope">
          <div class="table-item-tag">
            <!-- <dict-tag v-for="item in scope.row.nature" :key="item" :options="dict.type.purveyor_nature" :value="item"/> -->
            <el-tag v-for="item in scope.row.nature" :key="item" type="primary" style="margin-right:4px;">{{handleTag(dict.type.purveyor_nature, item)}}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column :label="$t('inventoryModule.purveyor.supplyType')" align="center" prop="supplyType" width="200">
        <template slot-scope="scope">
          <div class="table-item-tag">
            <el-tag v-for="item in scope.row.supplyType" :key="item" type="primary" style="margin-right:4px;">{{handleTag(dict.type.purveyor_supply_type, item)}}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column :label="$t('inventoryModule.purveyor.contractName')" align="center" prop="contractName" />
      <el-table-column :label="$t('inventoryModule.purveyor.contractPost')" align="center" prop="contractPost" />
      <el-table-column :label="$t('inventoryModule.purveyor.contractPhone')" align="center" prop="contractPhone" />
      <el-table-column :label="$t('inventoryModule.purveyor.contractEmail')" align="center" prop="contractEmail" />
      <el-table-column :label="$t('inventoryModule.purveyor.address')" align="center" prop="address" />
      <el-table-column :label="$t('common.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding" fixed="right" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:purveyor:edit']"
          >{{ $t('common.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:purveyor:remove']"
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

    <!-- 添加或修改供应商库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.purveyorCode')" prop="purveyorCode">
              <el-input v-model="form.purveyorCode" :placeholder="$t('inventoryModule.purveyor.placeholder.inputPurveyorCode')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.purveyorName')" prop="purveyorName">
              <el-input v-model="form.purveyorName" :placeholder="$t('inventoryModule.purveyor.placeholder.inputPurveyorName')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.nature')" prop="nature">
              <el-select v-model="form.nature" multiple  :placeholder="$t('inventoryModule.purveyor.placeholder.selectNature')">
                <el-option
                  v-for="dict in dict.type.purveyor_nature"
                  :key="dict.id"
                  :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.supplyType')" prop="supplyType">
              <el-select v-model="form.supplyType" multiple :placeholder="$t('inventoryModule.purveyor.placeholder.selectSupplyType')">
                <el-option v-for="dict in dict.type.purveyor_supply_type"
                  :key="dict.id"
                  :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.contractName')" prop="contractName">
              <el-input v-model="form.contractName" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.contractPost')" prop="contractPost">
              <el-input v-model="form.contractPost" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.contractPhone')" prop="contractPhone">
              <el-input v-model="form.contractPhone" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('inventoryModule.purveyor.contractEmail')" prop="contractEmail">
              <el-input v-model="form.contractEmail" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="24">
            <el-form-item :label="$t('inventoryModule.purveyor.address')" prop="address">
              <el-input v-model="form.address" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="24">
            <el-form-item :label="$t('common.remark')" prop="remark">
              <el-input type="textarea" v-model="form.remark" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPurveyor, getPurveyor, delPurveyor, addPurveyor, updatePurveyor } from "@/api/system/purveyor";

export default {
  name: "Purveyor",
  dicts: ['purveyor_nature', 'purveyor_supply_type'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 供应商库管理表格数据
      purveyorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purveyorCode: undefined,
        purveyorName: undefined,
        nature: undefined,
        supplyType: undefined,
        contractName: undefined,
        contractPost: undefined,
        contractPhone: undefined,
        contractEmail: undefined,
        address: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        purveyorCode: [
          { required: true, message: "供应商编号不能为空", trigger: "blur" }
        ],
        purveyorName: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" }
        ],
        nature: [
          { required: true, message: "供应商性质不能为空", trigger: "blur" }
        ],
        supplyType: [
          { required: true, message: "供货类型不能为空", trigger: "blur" }
        ],
        contractName: [
          { required: true, message: "联系人姓名不能为空", trigger: "blur" }
        ],
        contractPhone: [
          { required: true, message: "联系人电话不能为空", trigger: "blur" }
        ],
        address: [
          { required: true, message: "供应商地址不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商库管理列表 */
    getList() {
      this.loading = true;
      listPurveyor(this.queryParams).then(response => {
        this.purveyorList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.purveyorList.forEach(item => {
          item.nature = item.nature.split(','),
          item.supplyType = item.supplyType.split(',')
        })
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        purveyorId: undefined,
        purveyorCode: undefined,
        purveyorName: undefined,
        nature: undefined,
        supplyType: undefined,
        contractName: undefined,
        contractPost: undefined,
        contractPhone: undefined,
        contractEmail: undefined,
        address: undefined,
        remark: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.purveyorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const purveyorId = row.purveyorId || this.ids
      getPurveyor(purveyorId).then(response => {
        this.loading = false;
        this.form = {
          ...response.data,
          nature: response.data.nature.split(","),
          supplyType: response.data.supplyType.split(',')
        };
        this.open = true;
        this.title = "修改供应商库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          let data = {
            ...this.form,
            nature: this.form.nature.join(','),
            supplyType: this.form.supplyType.join(',')
          }
          if (this.form.purveyorId != null) {
            updatePurveyor(data).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addPurveyor(data).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const purveyorIds = row.purveyorId || this.ids;
      this.$modal.confirm('是否确认删除供应商库信息？').then(() => {
        this.loading = true;
        return delPurveyor(purveyorIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/purveyor/export', {
        ...this.queryParams
      }, `purveyor_${new Date().getTime()}.xlsx`)
    },
    // 处理标签
    handleTag(dicts, value) {
      let item = dicts.find(d => d.value == value)
      return item.label || '未知'
    }
  }
};
</script>
<style scoped>
.table-item-tag {
  display: flex;
  display: -webkit-box;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
</style>
