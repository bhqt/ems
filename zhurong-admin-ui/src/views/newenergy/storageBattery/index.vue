<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('newenergyModule.storageBattery.batteryName')" prop="batteryName">
        <el-input
          v-model="queryParams.batteryName"
          :placeholder="$t('newenergyModule.storageBattery.placeholder.inputBatteryName')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('newenergyModule.storageBattery.batteryCode')" prop="batteryCode">
        <el-input
          v-model="queryParams.batteryCode"
          :placeholder="$t('newenergyModule.storageBattery.placeholder.inputBatteryCode')"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('newenergyModule.storageBattery.storageSystem')" prop="storageId">
        <el-select v-model="queryParams.storageId" :placeholder="$t('newenergyModule.storageBattery.placeholder.selectStorageSystem')" clearable size="small">
          <el-option
            v-for="storage in storageOptions"
            :key="storage.id"
            :label="storage.storageName"
            :value="storage.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('newenergyModule.storageBattery.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('newenergyModule.storageBattery.placeholder.selectStatus')" clearable size="small">
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
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['newenergy:storageBattery:add']"
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
          v-hasPermi="['newenergy:storageBattery:edit']"
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
          v-hasPermi="['newenergy:storageBattery:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['newenergy:storageBattery:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="storageBatteryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('newenergyModule.storageBattery.id')" align="center" prop="id" v-if="false" />
      <el-table-column :label="$t('newenergyModule.storageBattery.batteryName')" align="center" prop="batteryName" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('newenergyModule.storageBattery.batteryCode')" align="center" prop="batteryCode" />
      <el-table-column :label="$t('newenergyModule.storageBattery.storageSystem')" align="center" prop="storageName" />
      <el-table-column :label="$t('newenergyModule.storageBattery.batteryModel')" align="center" prop="batteryModel" />
      <el-table-column :label="$t('newenergyModule.storageBattery.ratedCapacity')" align="center" prop="ratedCapacity" />
      <el-table-column :label="$t('newenergyModule.storageBattery.ratedVoltage')" align="center" prop="ratedVoltage" />
      <el-table-column :label="$t('newenergyModule.storageBattery.totalCapacity')" align="center" prop="totalCapacity" />
      <el-table-column :label="$t('newenergyModule.storageBattery.seriesCount')" align="center" prop="seriesCount" />
      <el-table-column :label="$t('newenergyModule.storageBattery.parallelCount')" align="center" prop="parallelCount" />
      <el-table-column :label="$t('newenergyModule.storageBattery.status')" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['newenergy:storageBattery:query']"
          >{{ $t('common.detail') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['newenergy:storageBattery:edit']"
          >{{ $t('common.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['newenergy:storageBattery:remove']"
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

    <!-- 添加或修改储能电池组对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.storageSystem')" prop="storageId">
              <el-select v-model="form.storageId" :placeholder="$t('newenergyModule.storageBattery.placeholder.selectStorageSystem')" style="width: 100%">
                <el-option
                  v-for="storage in storageOptions"
                  :key="storage.id"
                  :label="storage.storageName"
                  :value="storage.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.batteryName')" prop="batteryName">
              <el-input v-model="form.batteryName" :placeholder="$t('newenergyModule.storageBattery.placeholder.inputBatteryName')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.batteryCode')" prop="batteryCode">
              <el-input v-model="form.batteryCode" :placeholder="$t('newenergyModule.storageBattery.placeholder.inputBatteryCode')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.batteryModel')" prop="batteryModel">
              <el-input v-model="form.batteryModel" :placeholder="$t('newenergyModule.storageBattery.placeholder.inputBatteryModel')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.ratedCapacity')" prop="ratedCapacity">
              <el-input-number v-model="form.ratedCapacity" :min="0" :precision="2" style="width: 100%" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.ratedVoltage')" prop="ratedVoltage">
              <el-input-number v-model="form.ratedVoltage" :min="0" :precision="2" style="width: 100%" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.seriesCount')" prop="seriesCount">
              <el-input-number v-model="form.seriesCount" :min="1" style="width: 100%" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.parallelCount')" prop="parallelCount">
              <el-input-number v-model="form.parallelCount" :min="1" style="width: 100%" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.totalCapacity')" prop="totalCapacity">
              <el-input-number v-model="form.totalCapacity" :min="0" :precision="2" style="width: 100%" :placeholder="$t('common.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.commissioningDate')" prop="commissioningDate">
              <el-date-picker
                v-model="form.commissioningDate"
                type="date"
                :placeholder="$t('common.pleaseSelect')"
                style="width: 100%"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('newenergyModule.storageBattery.status')" prop="status">
              <el-select v-model="form.status" :placeholder="$t('newenergyModule.storageBattery.placeholder.selectStatus')" style="width: 100%">
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog :title="$t('newenergyModule.storageBattery.title') + $t('common.detail')" :visible.sync="detailOpen" width="900px" append-to-body>
      <el-descriptions :column="3" border v-if="detailData">
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.batteryName')">{{ detailData.batteryName }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.batteryCode')">{{ detailData.batteryCode }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.storageSystem')">{{ detailData.storageName }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.batteryModel')">{{ detailData.batteryModel }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.ratedCapacity')">{{ detailData.ratedCapacity }} Ah</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.ratedVoltage')">{{ detailData.ratedVoltage }} V</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.totalCapacity')">{{ detailData.totalCapacity }} kWh</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.seriesCount')">{{ detailData.seriesCount }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.parallelCount')">{{ detailData.parallelCount }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.commissioningDate')">{{ detailData.commissioningDate }}</el-descriptions-item>
        <el-descriptions-item :label="$t('newenergyModule.storageBattery.status')">
          <el-tag :type="getStatusType(detailData.status)">{{ getStatusLabel(detailData.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item :label="$t('common.createTime')">{{ parseTime(detailData.createTime) }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">{{ $t('button.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStorageBattery, getStorageBattery, delStorageBattery, addStorageBattery, updateStorageBattery, exportStorageBattery } from "@/api/newenergy/storageBattery";
import { listEnergyStorage } from "@/api/newenergy/energyStorage";

export default {
  name: "StorageBattery",
  data() {
    return {
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
      // 储能电池组表格数据
      storageBatteryList: [],
      // 储能系统选项
      storageOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      detailOpen: false,
      // 详情数据
      detailData: null,
      // 电池状态选项
      statusOptions: [
        { dictValue: '0', dictLabel: '停用' },
        { dictValue: '1', dictLabel: '正常' },
        { dictValue: '2', dictLabel: '故障' },
        { dictValue: '3', dictLabel: '维护' }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        batteryName: null,
        batteryCode: null,
        storageId: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        storageId: [
          { required: true, message: "储能系统不能为空", trigger: "change" }
        ],
        batteryName: [
          { required: true, message: "电池组名称不能为空", trigger: "blur" }
        ],
        batteryCode: [
          { required: true, message: "电池组编号不能为空", trigger: "blur" }
        ],
        ratedCapacity: [
          { required: true, message: "额定容量不能为空", trigger: "blur" }
        ],
        ratedVoltage: [
          { required: true, message: "额定电压不能为空", trigger: "blur" }
        ],
        seriesCount: [
          { required: true, message: "串数不能为空", trigger: "blur" }
        ],
        parallelCount: [
          { required: true, message: "并数不能为空", trigger: "blur" }
        ],
        totalCapacity: [
          { required: true, message: "总容量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStorageOptions();
  },
  methods: {
    /** 查询储能电池组列表 */
    getList() {
      this.loading = true;
      listStorageBattery(this.queryParams).then(response => {
        this.storageBatteryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取储能系统选项 */
    getStorageOptions() {
      listEnergyStorage({ pageSize: 1000 }).then(response => {
        this.storageOptions = response.rows;
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
        id: null,
        storageId: null,
        batteryName: null,
        batteryCode: null,
        batteryModel: null,
        ratedCapacity: null,
        ratedVoltage: null,
        seriesCount: null,
        parallelCount: null,
        totalCapacity: null,
        status: "1",
        commissioningDate: null,
        remark: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加储能电池组";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getStorageBattery(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改储能电池组";
      });
    },
    /** 详情按钮操作 */
    handleView(row) {
      const id = row.id;
      getStorageBattery(id).then(response => {
        this.detailData = response.data;
        this.detailOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStorageBattery(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStorageBattery(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除储能电池组编号为"' + ids + '"的数据项？').then(function() {
        return delStorageBattery(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/newenergy/storageBattery/export', {
        ...this.queryParams
      }, `storageBattery_${new Date().getTime()}.xlsx`);
    },
    /** 获取状态类型 */
    getStatusType(status) {
      switch (status) {
        case '0': return 'info';
        case '1': return 'success';
        case '2': return 'danger';
        case '3': return 'warning';
        default: return 'info';
      }
    },
    /** 获取状态标签 */
    getStatusLabel(status) {
      const item = this.statusOptions.find(s => s.dictValue === status);
      return item ? item.dictLabel : status;
    }
  }
};
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
.text-danger {
  color: #f56c6c;
}
.text-warning {
  color: #e6a23c;
}
</style>
