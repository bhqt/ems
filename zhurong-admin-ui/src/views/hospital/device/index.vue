<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('hospital.deviceName')" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.deviceCode')" prop="deviceCode">
        <el-input
          v-model="queryParams.deviceCode"
          :placeholder="$t('common.pleaseInput')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('hospital.deviceType')" prop="deviceType">
        <el-select v-model="queryParams.deviceType" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option
            v-for="dict in deviceTypeOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option
            v-for="dict in deviceStatusOptions"
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
          v-hasPermi="['hospital:device:add']"
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
          v-hasPermi="['hospital:device:edit']"
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
          v-hasPermi="['hospital:device:remove']"
        >{{ $t('button.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-link"
          size="mini"
          :disabled="single"
          @click="handleBind"
          v-hasPermi="['hospital:device:bind']"
        >{{ $t('hospital.bindIot') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('hospital.deviceName')" align="center" prop="deviceName" />
      <el-table-column :label="$t('hospital.deviceCode')" align="center" prop="deviceCode" />
      <el-table-column :label="$t('hospital.deviceType')" align="center" prop="deviceType" width="100">
        <template slot-scope="scope">
          <span>{{ deviceTypeLabel(scope.row.deviceType) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('hospital.iotDeviceId')" align="center" prop="iotDeviceId" width="160">
        <template slot-scope="scope">
          <span v-if="scope.row.iotDeviceId">{{ scope.row.iotDeviceId }}</span>
          <el-tag v-else type="info" size="mini">{{ $t('hospital.unbound') }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.status')" align="center" prop="status" width="90">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.status)" size="mini">{{ statusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.createTime')" align="center" prop="createTime" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewData(scope.row)"
            v-hasPermi="['hospital:device:query']"
          >{{ $t('hospital.viewData') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hospital:device:edit']"
          >{{ $t('button.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-link"
            @click="handleBind(scope.row)"
            v-hasPermi="['hospital:device:bind']"
          >{{ $t('hospital.bindIot') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hospital:device:remove']"
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

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('hospital.deviceName')" prop="deviceName">
          <el-input v-model="form.deviceName" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.deviceCode')" prop="deviceCode">
          <el-input v-model="form.deviceCode" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.deviceType')" prop="deviceType">
          <el-select v-model="form.deviceType" :placeholder="$t('common.pleaseSelect')" style="width:100%">
            <el-option
              v-for="dict in deviceTypeOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.areaName')" prop="areaId">
          <el-select v-model="form.areaId" :placeholder="$t('common.pleaseSelect')" clearable style="width:100%">
            <el-option
              v-for="area in areaOptions"
              :key="area.value"
              :label="area.label"
              :value="area.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.projectCategory')" prop="projectCategory">
          <el-select v-model="form.projectCategory" :placeholder="$t('common.pleaseSelect')" clearable style="width:100%">
            <el-option :label="$t('hospital.categoryLighting')" value="LIGHTING" />
            <el-option :label="$t('hospital.categoryAircond')" value="AIRCOND" />
            <el-option :label="$t('hospital.categoryMedical')" value="MEDICAL" />
            <el-option :label="$t('hospital.categoryPower')" value="POWER" />
            <el-option :label="$t('hospital.categoryOther')" value="OTHER" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('hospital.model')" prop="model">
          <el-input v-model="form.model" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.manufacturer')" prop="manufacturer">
          <el-input v-model="form.manufacturer" :placeholder="$t('common.pleaseInput')" />
        </el-form-item>
        <el-form-item :label="$t('hospital.iotDeviceId')" prop="iotDeviceId">
          <el-input v-model="form.iotDeviceId" :placeholder="$t('hospital.iotDeviceIdPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('common.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in deviceStatusOptions"
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

    <!-- 绑定 IOT 设备对话框 -->
    <el-dialog :title="$t('hospital.bindIot')" :visible.sync="bindOpen" width="480px" append-to-body>
      <el-form ref="bindForm" :model="bindForm" label-width="120px">
        <el-form-item :label="$t('hospital.deviceName')">
          <span>{{ bindForm.deviceName }}</span>
        </el-form-item>
        <el-form-item :label="$t('hospital.iotDeviceId')" prop="iotDeviceId">
          <el-input v-model="bindForm.iotDeviceId" :placeholder="$t('hospital.iotDeviceIdPlaceholder')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBind">{{ $t('button.submit') }}</el-button>
        <el-button @click="bindOpen = false">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 设备数据点查看对话框 -->
    <el-dialog :title="dataTitle" :visible.sync="dataOpen" width="760px" append-to-body>
      <el-form :inline="true" size="small" class="data-query-form">
        <el-form-item :label="$t('hospital.metricCode')">
          <el-input v-model="dataQuery.metricCode" :placeholder="$t('common.pleaseInput')" clearable style="width:180px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getDataList">{{ $t('button.search') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataLoading" :data="dataList" height="400">
        <el-table-column :label="$t('hospital.metricCode')" align="center" prop="metricCode" />
        <el-table-column :label="$t('hospital.metricName')" align="center" prop="metricName" />
        <el-table-column :label="$t('hospital.metricValue')" align="center" prop="metricValue" width="120" />
        <el-table-column :label="$t('hospital.unit')" align="center" prop="unit" width="80" />
        <el-table-column :label="$t('hospital.ts')" align="center" prop="ts" width="170">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.ts) }}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('hospital.quality')" align="center" prop="quality" width="90">
          <template slot-scope="scope">
            <el-tag :type="scope.row.quality === 1 ? 'danger' : 'success'" size="mini">
              {{ scope.row.quality === 1 ? $t('hospital.qualityBad') : $t('hospital.qualityGood') }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listDevice, getDevice, delDevice, addDevice, updateDevice, bindIotDevice, listDeviceData } from "@/api/hospital/device";
import { getAreaOptions } from "@/api/hospital/area";

export default {
  name: "HospitalDevice",
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
      // 显示查询条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备表格数据
      deviceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 绑定对话框
      bindOpen: false,
      bindForm: {},
      // 数据点对话框
      dataOpen: false,
      dataTitle: "",
      dataLoading: false,
      dataList: [],
      dataQuery: {
        deviceId: undefined,
        metricCode: undefined,
        limit: 100
      },
      // 设备类型选项
      deviceTypeOptions: [
        { value: 'CT', label: 'CT' },
        { value: 'MRI', label: 'MRI' },
        { value: 'DR', label: 'DR' },
        { value: 'US', label: '超声' },
        { value: 'LAB', label: '检验流水线' },
        { value: 'DSA', label: 'DSA' },
        { value: 'OTHER', label: '其他' }
      ],
      // 设备状态选项
      deviceStatusOptions: [
        { value: '0', label: this.$t('hospital.statusNormal') },
        { value: '1', label: this.$t('hospital.statusDisabled') },
        { value: '2', label: this.$t('hospital.statusOffline') }
      ],
      // 院区选项
      areaOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: undefined,
        deviceCode: undefined,
        deviceType: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deviceName: [
          { required: true, message: this.$t('hospital.deviceNameRequired'), trigger: "blur" }
        ],
        deviceCode: [
          { required: true, message: this.$t('hospital.deviceCodeRequired'), trigger: "blur" }
        ],
        deviceType: [
          { required: true, message: this.$t('hospital.deviceTypeRequired'), trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    getAreaOptions({}).then(response => {
      this.areaOptions = (response.data || []).map(a => ({ value: a.id, label: a.areaName }));
    }).catch(() => {});
  },
  methods: {
    /** 查询设备列表 */
    getList() {
      this.loading = true;
      listDevice(this.queryParams).then(response => {
        this.deviceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    deviceTypeLabel(type) {
      const item = this.deviceTypeOptions.find(o => o.value === type);
      return item ? item.label : type;
    },
    statusLabel(status) {
      const item = this.deviceStatusOptions.find(o => o.value === String(status));
      return item ? item.label : status;
    },
    statusTagType(status) {
      const map = { '0': 'success', '1': 'info', '2': 'danger' };
      return map[String(status)] || 'info';
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        deviceName: undefined,
        deviceCode: undefined,
        deviceType: undefined,
        model: undefined,
        manufacturer: undefined,
        iotDeviceId: undefined,
        areaId: undefined,
        projectCategory: undefined,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 查询按钮操作 */
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('hospital.addDevice');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDevice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('hospital.editDevice');
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDevice(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.editSuccess'));
              this.open = false;
              this.getList();
            });
          } else {
            addDevice(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.addSuccess'));
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
      this.$modal.confirm(this.$t('confirm.delete')).then(function() {
        return delDevice(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('message.deleteSuccess'));
      }).catch(() => {});
    },
    /** 绑定 IOT 设备 */
    handleBind(row) {
      const id = row.id || this.ids
      getDevice(id).then(response => {
        this.bindForm = response.data;
        this.bindOpen = true;
      });
    },
    /** 提交绑定 */
    submitBind() {
      bindIotDevice(this.bindForm.id, this.bindForm.iotDeviceId).then(response => {
        this.$modal.msgSuccess(this.$t('message.editSuccess'));
        this.bindOpen = false;
        this.getList();
      });
    },
    /** 查看设备数据点 */
    handleViewData(row) {
      this.dataQuery.deviceId = row.id;
      this.dataQuery.metricCode = undefined;
      this.dataTitle = this.$t('hospital.viewData') + ' - ' + row.deviceName;
      this.dataOpen = true;
      this.getDataList();
    },
    getDataList() {
      this.dataLoading = true;
      listDeviceData(this.dataQuery).then(response => {
        this.dataList = response.data;
        this.dataLoading = false;
      });
    }
  }
};
</script>
