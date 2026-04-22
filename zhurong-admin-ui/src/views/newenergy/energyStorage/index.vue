<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">系统总数</div>
          <div class="stat-value">{{ statistics.totalStorage || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">正常系统</div>
          <div class="stat-value text-success">{{ statistics.normalStorage || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">故障系统</div>
          <div class="stat-value text-danger">{{ statistics.faultStorage || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">维护系统</div>
          <div class="stat-value text-warning">{{ statistics.maintenanceStorage || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">总装机容量</div>
          <div class="stat-value">{{ statistics.totalCapacity || 0 }} kWh</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">当前功率</div>
          <div class="stat-value">{{ statistics.currentPower || 0 }} kW</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="系统名称" prop="storageName">
        <el-input
          v-model="queryParams.storageName"
          placeholder="请输入系统名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统编号" prop="storageCode">
        <el-input
          v-model="queryParams.storageCode"
          placeholder="请输入系统编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统类型" prop="storageType">
        <el-select v-model="queryParams.storageType" placeholder="请选择系统类型" clearable size="small">
          <el-option
            v-for="dict in storageTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属区域" prop="areaId">
        <treeselect
          v-model="queryParams.areaId"
          :options="deptOptions"
          :show-count="true"
          placeholder="请选择所属区域"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="系统状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择系统状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['newenergy:energyStorage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['newenergy:energyStorage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['newenergy:energyStorage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['newenergy:energyStorage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="energyStorageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" v-if="false" />
      <el-table-column label="系统名称" align="center" prop="storageName" :show-overflow-tooltip="true" />
      <el-table-column label="系统编号" align="center" prop="storageCode" />
      <el-table-column label="系统类型" align="center" prop="storageType">
        <template slot-scope="scope">
          <dict-tag :options="storageTypeOptions" :value="scope.row.storageType" />
        </template>
      </el-table-column>
      <el-table-column label="装机容量(kWh)" align="center" prop="capacity" />
      <el-table-column label="额定功率(kW)" align="center" prop="power" />
      <el-table-column label="电池类型" align="center" prop="batteryType" />
      <el-table-column label="所属区域" align="center" prop="areaName" />
      <el-table-column label="电池组数量" align="center" prop="batteryCount" />
      <el-table-column label="系统状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['newenergy:energyStorage:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['newenergy:energyStorage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['newenergy:energyStorage:remove']"
          >删除</el-button>
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

    <!-- 添加或修改储能系统对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="系统名称" prop="storageName">
              <el-input v-model="form.storageName" placeholder="请输入系统名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系统编号" prop="storageCode">
              <el-input v-model="form.storageCode" placeholder="请输入系统编号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="系统类型" prop="storageType">
              <el-select v-model="form.storageType" placeholder="请选择系统类型" style="width: 100%">
                <el-option
                  v-for="dict in storageTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="装机容量" prop="capacity">
              <el-input-number v-model="form.capacity" :min="0" :precision="2" style="width: 100%" placeholder="请输入装机容量(kWh)" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="额定功率" prop="power">
              <el-input-number v-model="form.power" :min="0" :precision="2" style="width: 100%" placeholder="请输入额定功率(kW)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电压等级" prop="voltageLevel">
              <el-input v-model="form.voltageLevel" placeholder="请输入电压等级" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电池类型" prop="batteryType">
              <el-input v-model="form.batteryType" placeholder="请输入电池类型" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属区域" prop="areaId">
              <treeselect
                v-model="form.areaId"
                :options="deptOptions"
                :show-count="true"
                placeholder="请选择所属区域"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经度" prop="longitude">
              <el-input-number v-model="form.longitude" :precision="6" style="width: 100%" placeholder="请输入经度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="latitude">
              <el-input-number v-model="form.latitude" :precision="6" style="width: 100%" placeholder="请输入纬度" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="投运日期" prop="commissioningDate">
              <el-date-picker
                v-model="form.commissioningDate"
                type="date"
                placeholder="请选择投运日期"
                style="width: 100%"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系统状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择系统状态" style="width: 100%">
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
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="系统详情" :visible.sync="detailOpen" width="900px" append-to-body>
      <el-descriptions :column="3" border v-if="detailData">
        <el-descriptions-item label="系统名称">{{ detailData.storageName }}</el-descriptions-item>
        <el-descriptions-item label="系统编号">{{ detailData.storageCode }}</el-descriptions-item>
        <el-descriptions-item label="系统类型">
          <dict-tag :options="storageTypeOptions" :value="detailData.storageType" />
        </el-descriptions-item>
        <el-descriptions-item label="装机容量">{{ detailData.capacity }} kWh</el-descriptions-item>
        <el-descriptions-item label="额定功率">{{ detailData.power }} kW</el-descriptions-item>
        <el-descriptions-item label="电压等级">{{ detailData.voltageLevel }}</el-descriptions-item>
        <el-descriptions-item label="电池类型">{{ detailData.batteryType }}</el-descriptions-item>
        <el-descriptions-item label="所属区域">{{ detailData.areaName }}</el-descriptions-item>
        <el-descriptions-item label="系统状态">
          <el-tag :type="getStatusType(detailData.status)">{{ getStatusLabel(detailData.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="经度">{{ detailData.longitude }}</el-descriptions-item>
        <el-descriptions-item label="纬度">{{ detailData.latitude }}</el-descriptions-item>
        <el-descriptions-item label="投运日期">{{ detailData.commissioningDate }}</el-descriptions-item>
        <el-descriptions-item label="地址">{{ detailData.address }}</el-descriptions-item>
        <el-descriptions-item label="电池组数量">{{ detailData.batteryCount }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(detailData.createTime) }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEnergyStorage, getEnergyStorage, delEnergyStorage, addEnergyStorage, updateEnergyStorage, exportEnergyStorage, getStorageStatistics } from "@/api/newenergy/energyStorage";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "EnergyStorage",
  components: { Treeselect },
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
      // 储能系统表格数据
      energyStorageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      detailOpen: false,
      // 详情数据
      detailData: null,
      // 统计数据
      statistics: {},
      // 部门树选项
      deptOptions: undefined,
      // 系统类型字典
      storageTypeOptions: [],
      // 系统状态选项
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
        storageName: null,
        storageCode: null,
        storageType: null,
        areaId: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        storageName: [
          { required: true, message: "系统名称不能为空", trigger: "blur" }
        ],
        storageCode: [
          { required: true, message: "系统编号不能为空", trigger: "blur" }
        ],
        storageType: [
          { required: true, message: "系统类型不能为空", trigger: "change" }
        ],
        capacity: [
          { required: true, message: "装机容量不能为空", trigger: "blur" }
        ],
        power: [
          { required: true, message: "额定功率不能为空", trigger: "blur" }
        ],
        areaId: [
          { required: true, message: "所属区域不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStorageStatistics();
    this.getTreeselect();
    this.getDicts("storage_type").then(response => {
      this.storageTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询储能系统列表 */
    getList() {
      this.loading = true;
      listEnergyStorage(this.queryParams).then(response => {
        this.energyStorageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取统计数据 */
    getStorageStatistics() {
      getStorageStatistics().then(response => {
        this.statistics = response.data;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
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
        storageName: null,
        storageCode: null,
        storageType: null,
        capacity: null,
        power: null,
        voltageLevel: null,
        batteryType: null,
        areaId: null,
        address: null,
        longitude: null,
        latitude: null,
        commissioningDate: null,
        status: "1",
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
      this.title = "添加储能系统";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getEnergyStorage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改储能系统";
      });
    },
    /** 详情按钮操作 */
    handleView(row) {
      const id = row.id;
      getEnergyStorage(id).then(response => {
        this.detailData = response.data;
        this.detailOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEnergyStorage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getStorageStatistics();
            });
          } else {
            addEnergyStorage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getStorageStatistics();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除储能系统编号为"' + ids + '"的数据项？').then(function() {
        return delEnergyStorage(ids);
      }).then(() => {
        this.getList();
        this.getStorageStatistics();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/newenergy/energyStorage/export', {
        ...this.queryParams
      }, `energyStorage_${new Date().getTime()}.xlsx`);
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
