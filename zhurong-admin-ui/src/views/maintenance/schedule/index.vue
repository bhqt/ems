<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <!-- <el-form-item label="计划编号" prop="number">
          <el-input
            v-model="queryParams.number"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
        <!-- <el-form-item label="计划周期" prop="cycle">
          <el-input
            v-model="queryParams.cycle"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
        <el-form-item :label="$t('maintenanceModule.station')" prop="station">
          <el-input
            v-model="queryParams.station"
            :placeholder="$t('maintenanceModule.station')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <!-- <el-form-item label="是否超时(0是，1不是)" prop="isTimeout">
          <el-input
            v-model="queryParams.isTimeout"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
        <el-form-item :label="$t('maintenanceModule.planName')" prop="name">
          <el-input
            v-model="queryParams.name"
            :placeholder="$t('maintenanceModule.planName')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <!-- <el-form-item label="计划来源" prop="source">
          <el-input
            v-model="queryParams.source"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
        <!-- <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
            v-model="queryParams.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            :placeholder="$t('common.pleaseSelect')">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="queryParams.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            :placeholder="$t('common.pleaseSelect')">
          </el-date-picker>
        </el-form-item> -->
        <!-- <el-form-item label="一级提醒" prop="firstReminder">
          <el-input
            v-model="queryParams.firstReminder"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="二级提醒" prop="secondReminder">
          <el-input
            v-model="queryParams.secondReminder"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
        <!-- <el-form-item label="开工执行人" prop="operator">
          <el-input
            v-model="queryParams.operator"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="开工时间" prop="commencementTime">
          <el-date-picker clearable
            v-model="queryParams.commencementTime"
            type="date"
            value-format="yyyy-MM-dd"
            :placeholder="$t('common.pleaseSelect')">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归结执行人" prop="terminator">
          <el-input
            v-model="queryParams.terminator"
            :placeholder="$t('common.pleaseInput')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
        <!-- <el-form-item label="归结时间" prop="resolutionTime">
          <el-date-picker clearable
            v-model="queryParams.resolutionTime"
            type="date"
            value-format="yyyy-MM-dd"
            :placeholder="$t('common.pleaseSelect')">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item :label="$t('maintenanceModule.preparedBy')" prop="preparedBy">
          <el-input
            v-model="queryParams.preparedBy"
            :placeholder="$t('maintenanceModule.preparedBy')"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item :label="$t('maintenanceModule.head')" prop="head">
          <el-input
            v-model="queryParams.head"
            :placeholder="$t('maintenanceModule.head')"
            clearable
            @keyup.enter.native="handleQuery"
          />
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
            v-hasPermi="['system:schedule:add']"
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
            v-hasPermi="['system:schedule:edit']"
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
            v-hasPermi="['system:schedule:remove']"
          >{{ $t('common.delete') }}</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:schedule:export']"
          >{{ $t('common.export') }}</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="scheduleList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!-- <el-table-column label="调度计划id" align="center" prop="scheduleId" v-if="true"/> -->
        <el-table-column label="计划编号" align="center" prop="number" />
        <el-table-column label="类型" align="center" prop="type" />
        <el-table-column label="计划周期" align="center" prop="cycle" />
        <el-table-column label="站所" align="center" prop="station" />
        <el-table-column :label="$t('common.status')" align="center" prop="status" />
        <el-table-column label="超时" align="center" prop="isTimeout" />
        <el-table-column label="计划名称" align="center" prop="name" />
        <!-- <el-table-column label="计划来源" align="center" prop="source" />
        <el-table-column label="计划内容" align="center" prop="content" /> -->
        <el-table-column label="开始时间" align="center" prop="startTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束时间" align="center" prop="endTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="一级提醒" align="center" prop="firstReminder" />
        <el-table-column label="二级提醒" align="center" prop="secondReminder" />
        <el-table-column label="开工执行人" align="center" prop="operator" /> -->
        <el-table-column label="开工时间" align="center" prop="commencementTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.commencementTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="归结执行人" align="center" prop="terminator" /> -->
        <el-table-column label="归结时间" align="center" prop="resolutionTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.resolutionTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="归结内容" align="center" prop="resolutionContent" /> -->
        <!-- <el-table-column label="编制人" align="center" prop="preparedBy" /> -->
        <el-table-column label="负责人" align="center" prop="head" />
        <el-table-column label="注意事项" align="center" prop="remark" />
        <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:schedule:edit']"
            >{{ $t('common.edit') }}</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:schedule:remove']"
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
  
      <!-- 添加或修改调度计划管理对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="计划编号" prop="number">
            <el-input v-model="form.number" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-input v-model="form.type" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="计划周期" prop="cycle">
            <el-input v-model="form.cycle" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="站所" prop="station">
            <el-input v-model="form.station" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <!-- <el-form-item label="是否超时" prop="isTimeout">
            <el-input v-model="form.isTimeout" :placeholder="$t('common.pleaseInput')" />
          </el-form-item> -->
          <el-form-item label="计划名称" prop="name">
            <el-input v-model="form.name" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="计划来源" prop="source">
            <el-input v-model="form.source" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="计划内容">
            <editor v-model="form.content" :min-height="192"/>
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker clearable
              v-model="form.startTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :placeholder="$t('common.pleaseSelect')">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker clearable
              v-model="form.endTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :placeholder="$t('common.pleaseSelect')">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="一级提醒(天)" prop="firstReminder">
            <el-input-number v-model="form.firstReminder" controls-position="right" :min="0" />
          </el-form-item>
          <el-form-item label="二级提醒(天)" prop="secondReminder">
            <el-input-number v-model="form.secondReminder" controls-position="right" :min="0" />
          </el-form-item>
          <el-form-item label="开工人" prop="operator">
            <el-input v-model="form.operator" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="开工时间" prop="commencementTime">
            <el-date-picker clearable
              v-model="form.commencementTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :placeholder="$t('common.pleaseSelect')">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="归结人" prop="terminator">
            <el-input v-model="form.terminator" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="归结时间" prop="resolutionTime">
            <el-date-picker clearable
              v-model="form.resolutionTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :placeholder="$t('common.pleaseSelect')">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="归结内容">
            <editor v-model="form.resolutionContent" :min-height="192"/>
          </el-form-item>
          <el-form-item label="编制人" prop="preparedBy">
            <el-input v-model="form.preparedBy" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="负责人" prop="head">
            <el-input v-model="form.head" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
          <el-form-item label="注意事项" prop="remark">
            <el-input v-model="form.remark" :placeholder="$t('common.pleaseInput')" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { listSchedule, getSchedule, delSchedule, addSchedule, updateSchedule } from "@/api/system/schedule";
  
  export default {
    name: "Schedule",
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
        // 显示查询条件
        showSearch: true,
        // 总条数
        total: 0,
        // 调度计划管理表格数据
        scheduleList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          number: undefined,
          type: undefined,
          cycle: undefined,
          station: undefined,
          status: undefined,
          isTimeout: undefined,
          name: undefined,
          source: undefined,
          content: undefined,
          startTime: undefined,
          endTime: undefined,
          firstReminder: undefined,
          secondReminder: undefined,
          operator: undefined,
          commencementTime: undefined,
          terminator: undefined,
          resolutionTime: undefined,
          resolutionContent: undefined,
          preparedBy: undefined,
          head: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          scheduleId: [
            { required: true, message: "调度计划id不能为空", trigger: "blur" }
          ],
          number: [
            { required: true, message: "计划编号不能为空", trigger: "blur" }
          ],
          type: [
            { required: true, message: "类型不能为空", trigger: "change" }
          ],
          cycle: [
            { required: true, message: "计划周期不能为空", trigger: "blur" }
          ],
          station: [
            { required: true, message: "站所不能为空", trigger: "blur" }
          ],
          status: [
            { required: true, message: "状态不能为空", trigger: "change" }
          ],
          isTimeout: [
            { required: true, message: "是否超时(0是，1不是)不能为空", trigger: "blur" }
          ],
          name: [
            { required: true, message: "计划名称不能为空", trigger: "blur" }
          ],
          source: [
            { required: true, message: "计划来源不能为空", trigger: "blur" }
          ],
          content: [
            { required: true, message: "计划内容不能为空", trigger: "blur" }
          ],
          startTime: [
            { required: true, message: "开始时间不能为空", trigger: "blur" }
          ],
          endTime: [
            { required: true, message: "结束时间不能为空", trigger: "blur" }
          ],
          firstReminder: [
            { required: true, message: "一级提醒不能为空", trigger: "blur" }
          ],
          secondReminder: [
            { required: true, message: "二级提醒不能为空", trigger: "blur" }
          ],
          operator: [
            { required: true, message: "开工执行人不能为空", trigger: "blur" }
          ],
          commencementTime: [
            { required: true, message: "开工时间不能为空", trigger: "blur" }
          ],
          terminator: [
            { required: true, message: "归结执行人不能为空", trigger: "blur" }
          ],
          resolutionTime: [
            { required: true, message: "归结时间不能为空", trigger: "blur" }
          ],
          resolutionContent: [
            { required: true, message: "归结内容不能为空", trigger: "blur" }
          ],
          preparedBy: [
            { required: true, message: "编制人不能为空", trigger: "blur" }
          ],
          head: [
            { required: true, message: "负责人不能为空", trigger: "blur" }
          ],
          remark: [
            { required: true, message: "注意事项不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询调度计划管理列表 */
      getList() {
        this.loading = true;
        listSchedule(this.queryParams).then(response => {
          this.scheduleList = response.rows;
          this.total = response.total;
          this.loading = false;
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
          scheduleId: undefined,
          number: undefined,
          type: undefined,
          cycle: undefined,
          station: undefined,
          status: undefined,
          isTimeout: undefined,
          name: undefined,
          source: undefined,
          content: undefined,
          startTime: undefined,
          endTime: undefined,
          firstReminder: undefined,
          secondReminder: undefined,
          operator: undefined,
          commencementTime: undefined,
          terminator: undefined,
          resolutionTime: undefined,
          resolutionContent: undefined,
          preparedBy: undefined,
          head: undefined,
          remark: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined
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
        this.ids = selection.map(item => item.scheduleId)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加调度计划管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.loading = true;
        this.reset();
        const scheduleId = row.scheduleId || this.ids
        getSchedule(scheduleId).then(response => {
          this.loading = false;
          this.form = response.data;
          this.open = true;
          this.title = "修改调度计划管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.buttonLoading = true;
            if (this.form.scheduleId != null) {
              updateSchedule(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            } else {
              addSchedule(this.form).then(response => {
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
        const scheduleIds = row.scheduleId || this.ids;
        this.$modal.confirm('是否确认删除？').then(() => {
          this.loading = true;
          return delSchedule(scheduleIds);
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
        this.download('system/schedule/export', {
          ...this.queryParams
        }, `schedule_${new Date().getTime()}.xlsx`)
      }
    }
  };
  </script>
  