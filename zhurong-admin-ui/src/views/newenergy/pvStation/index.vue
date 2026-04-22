<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">电站总数</div>
          <div class="stat-value">{{ statistics.totalStation || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">正常电站</div>
          <div class="stat-value text-success">{{ statistics.normalStation || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">故障电站</div>
          <div class="stat-value text-danger">{{ statistics.faultStation || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">维护电站</div>
          <div class="stat-value text-warning">{{ statistics.maintenanceStation || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">总装机容量</div>
          <div class="stat-value">{{ statistics.totalCapacity || 0 }} kW</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stat-card">
          <div class="stat-title">今日发电量</div>
          <div class="stat-value">{{ statistics.todayEnergy || 0 }} kWh</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="电站名称" prop="stationName">
        <el-input
          v-model="queryParams.stationName"
          placeholder="请输入电站名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电站编号" prop="stationCode">
        <el-input
          v-model="queryParams.stationCode"
          placeholder="请输入电站编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电站类型" prop="stationType">
        <el-select v-model="queryParams.stationType" placeholder="请选择电站类型" clearable size="small">
          <el-option
            v-for="dict in stationTypeOptions"
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
      <el-form-item label="电站状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择电站状态" clearable size="small">
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
          v-hasPermi="['newenergy:pvStation:add']"
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
          v-hasPermi="['newenergy:pvStation:edit']"
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
          v-hasPermi="['newenergy:pvStation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['newenergy:pvStation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="pvStationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" v-if="false" />
      <el-table-column label="电站名称" align="center" prop="stationName" :show-overflow-tooltip="true" />
      <el-table-column label="电站编号" align="center" prop="stationCode" />
      <el-table-column label="电站类型" align="center" prop="stationType">
        <template slot-scope="scope">
          <dict-tag :options="stationTypeOptions" :value="scope.row.stationType" />
        </template>
      </el-table-column>
      <el-table-column label="装机容量(kW)" align="center" prop="capacity" />
      <el-table-column label="所属区域" align="center" prop="areaName" />
      <el-table-column label="逆变器数量" align="center" prop="inverterCount" />
      <el-table-column label="组件数量" align="center" prop="componentCount" />
      <el-table-column label="电站状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="manager" />
      <el-table-column label="联系电话" align="center" prop="contactPhone" />
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
            v-hasPermi="['newenergy:pvStation:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['newenergy:pvStation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['newenergy:pvStation:remove']"
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

    <!-- 添加或修改光伏电站信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="电站名称" prop="stationName">
              <el-input v-model="form.stationName" placeholder="请输入电站名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电站编号" prop="stationCode">
              <el-input v-model="form.stationCode" placeholder="请输入电站编号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电站类型" prop="stationType">
              <el-select v-model="form.stationType" placeholder="请选择电站类型" style="width: 100%">
                <el-option
                  v-for="dict in stationTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="装机容量" prop="capacity">
              <el-input-number v-model="form.capacity" :min="0" :precision="2" style="width: 100%" placeholder="请输入装机容量(kW)" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
          <el-col :span="12">
            <el-form-item label="电站状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择电站状态" style="width: 100%">
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
            <el-form-item label="安装日期" prop="installDate">
              <el-date-picker
                v-model="form.installDate"
                type="date"
                placeholder="请选择安装日期"
                style="width: 100%"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="并网日期" prop="gridDate">
              <el-date-picker
                v-model="form.gridDate"
                type="date"
                placeholder="请选择并网日期"
                style="width: 100%"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设计寿命" prop="designLife">
              <el-input-number v-model="form.designLife" :min="0" style="width: 100%" placeholder="请输入设计寿命(年)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组件类型" prop="componentType">
              <el-select v-model="form.componentType" placeholder="请选择组件类型" style="width: 100%">
                <el-option
                  v-for="dict in componentTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="逆变器型号" prop="inverterModel">
              <el-input v-model="form.inverterModel" placeholder="请输入逆变器型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="逆变器数量" prop="inverterCount">
              <el-input-number v-model="form.inverterCount" :min="0" style="width: 100%" placeholder="请输入逆变器数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="组件数量" prop="componentCount">
              <el-input-number v-model="form.componentCount" :min="0" style="width: 100%" placeholder="请输入组件数量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="manager">
              <el-input v-model="form.manager" placeholder="请输入负责人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
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
    <el-dialog title="电站详情" :visible.sync="detailOpen" width="900px" append-to-body>
      <el-descriptions :column="3" border v-if="detailData">
        <el-descriptions-item label="电站名称">{{ detailData.stationName }}</el-descriptions-item>
        <el-descriptions-item label="电站编号">{{ detailData.stationCode }}</el-descriptions-item>
        <el-descriptions-item label="电站类型">
          <dict-tag :options="stationTypeOptions" :value="detailData.stationType" />
        </el-descriptions-item>
        <el-descriptions-item label="装机容量">{{ detailData.capacity }} kW</el-descriptions-item>
        <el-descriptions-item label="所属区域">{{ detailData.areaName }}</el-descriptions-item>
        <el-descriptions-item label="电站状态">
          <el-tag :type="getStatusType(detailData.status)">{{ getStatusLabel(detailData.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="经度">{{ detailData.longitude }}</el-descriptions-item>
        <el-descriptions-item label="纬度">{{ detailData.latitude }}</el-descriptions-item>
        <el-descriptions-item label="安装日期">{{ detailData.installDate }}</el-descriptions-item>
        <el-descriptions-item label="并网日期">{{ detailData.gridDate }}</el-descriptions-item>
        <el-descriptions-item label="设计寿命">{{ detailData.designLife }} 年</el-descriptions-item>
        <el-descriptions-item label="组件类型">
          <dict-tag :options="componentTypeOptions" :value="detailData.componentType" />
        </el-descriptions-item>
        <el-descriptions-item label="逆变器型号">{{ detailData.inverterModel }}</el-descriptions-item>
        <el-descriptions-item label="逆变器数量">{{ detailData.inverterCount }}</el-descriptions-item>
        <el-descriptions-item label="组件数量">{{ detailData.componentCount }}</el-descriptions-item>
        <el-descriptions-item label="负责人">{{ detailData.manager }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detailData.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(detailData.createTime) }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPvStation, getPvStation, delPvStation, addPvStation, updatePvStation, exportPvStation, getStatistics } from "@/api/newenergy/pvStation";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "PvStation",
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
      // 光伏电站信息表格数据
      pvStationList: [],
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
      // 电站类型字典
      stationTypeOptions: [],
      // 组件类型字典
      componentTypeOptions: [],
      // 电站状态选项
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
        stationName: null,
        stationCode: null,
        stationType: null,
        areaId: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stationName: [
          { required: true, message: "电站名称不能为空", trigger: "blur" }
        ],
        stationCode: [
          { required: true, message: "电站编号不能为空", trigger: "blur" }
        ],
        stationType: [
          { required: true, message: "电站类型不能为空", trigger: "change" }
        ],
        capacity: [
          { required: true, message: "装机容量不能为空", trigger: "blur" }
        ],
        areaId: [
          { required: true, message: "所属区域不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStatistics();
    this.getTreeselect();
    this.getDicts("pv_station_type").then(response => {
      this.stationTypeOptions = response.data;
    });
    this.getDicts("pv_component_type").then(response => {
      this.componentTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询光伏电站信息列表 */
    getList() {
      this.loading = true;
      listPvStation(this.queryParams).then(response => {
        this.pvStationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取统计数据 */
    getStatistics() {
      getStatistics().then(response => {
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
        stationName: null,
        stationCode: null,
        stationType: null,
        capacity: null,
        areaId: null,
        longitude: null,
        latitude: null,
        installDate: null,
        gridDate: null,
        designLife: null,
        componentType: null,
        inverterModel: null,
        inverterCount: null,
        componentCount: null,
        status: "1",
        manager: null,
        contactPhone: null,
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
      this.title = "添加光伏电站信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPvStation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改光伏电站信息";
      });
    },
    /** 详情按钮操作 */
    handleView(row) {
      const id = row.id;
      getPvStation(id).then(response => {
        this.detailData = response.data;
        this.detailOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePvStation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getStatistics();
            });
          } else {
            addPvStation(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getStatistics();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除光伏电站信息编号为"' + ids + '"的数据项？').then(function() {
        return delPvStation(ids);
      }).then(() => {
        this.getList();
        this.getStatistics();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/newenergy/pvStation/export', {
        ...this.queryParams
      }, `pvStation_${new Date().getTime()}.xlsx`);
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
