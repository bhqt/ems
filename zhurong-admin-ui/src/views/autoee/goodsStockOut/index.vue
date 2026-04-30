<template>
  <div class="app-container">
    <el-form
      class="a_query_form"
      :model="pageData.queryParams"
      ref="queryFormRef"
      v-show="pageData.showSearchTool"
      label-width="150px"
      size="default"
      :inline="true"
    >
      <!-- 循环生成查询表单项 -->
      <el-form-item class="a_query_form_item" :label="$t('goodsModule.stockOutNo')" prop="id">
        <el-input
          clearable
          :placeholder="$t('goodsModule.stockOutNo')"
          v-model="pageData.queryParams.id"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item class="a_query_form_item" :label="$t('goodsModule.goodsName')" prop="goodsId">
        <el-select
          class="a_query_form_select"
          v-model="pageData.queryParams.goodsId"
          :placeholder="$t('common.pleaseSelect')"
          clearable
        >
          <el-option
            v-for="dict in dict.type.a_goods_info"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="a_query_form_item" :label="$t('common.operator')" prop="userId">
        <el-select
          class="a_query_form_select"
          v-model="pageData.queryParams.userId"
          :placeholder="$t('common.pleaseSelect')"
          clearable
        >
          <el-option
            v-for="dict in dict.type.sys_user"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="a_query_form_item_datetime_between" :label="$t('common.updateTime')">
        <el-date-picker
          v-model="dateRangeUpdateTime"
          type="datetimerange"
          unlink-panels
          style="width: 360px"
          :start-placeholder="$t('common.startTime')"
          :end-placeholder="$t('common.endTime')"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          range-separator="-"
          clearable
        />
      </el-form-item>
    </el-form>

    <!-- 功能按钮区 -->
    <el-row :gutter="10" class="mb8">
      <el-col v-if="true" :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['autoee:goodsStockOut:add']"
        >{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col v-if="true" :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['autoee:goodsStockOut:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <el-col :span="0.5">
        <el-divider direction="vertical" />
      </el-col>
      <el-col v-if="true" :span="1.5">
        <el-button v-if="true" icon="el-icon-refresh" size="small" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-col>
      <el-col v-if="true" :span="1.5">
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">{{ $t('common.search') }}</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="pageData.showSearchTool" @queryTable="getList()"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table
      :border="true"
      ref="mainTable"
      tooltip-effect="light"
      :row-class-name="rowClassName"
      v-loading="pageData.loadingListData"
      :data="pageData.goodsStockOutList"
      @selection-change="handleSelectionChange"
      :default-sort="pageData.defaultSort"
      @sort-change="handleSortChange"
      :show-summary="false"
      :summary-method="tableSummaryMethod"
    >
      <el-table-column type="selection" width="55" align="center"/>

      <el-table-column :label="$t('goodsModule.stockOutNo')" align="center" prop="id" />
      <el-table-column v-if="true" :label="$t('goodsModule.stockInNo')" align="center" prop="goodsStockInId" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.a_goods_stock_in" :value="scope.row.goodsStockInId" />
        </template>
      </el-table-column>
      <el-table-column v-if="true" :label="$t('goodsModule.goodsName')" align="center" prop="goodsId" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.a_goods_info" :value="scope.row.goodsId" />
        </template>
      </el-table-column>
      <el-table-column v-if="true" :label="$t('goodsModule.stockOutQty')" align="center" prop="quantity" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="true" :label="$t('goodsModule.stockOutReason')" align="center" prop="reason" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="false" :label="$t('common.remark')" align="center" prop="remark" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="true" :label="$t('common.operator')" align="center" prop="userId" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user" :value="scope.row.userId" />
        </template>
      </el-table-column>
      <el-table-column v-if="false" :label="$t('common.department')" align="center" prop="deptId" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_dept" :value="scope.row.deptId" />
        </template>
      </el-table-column>
      <el-table-column v-if="false" :label="$t('common.createTime')" align="center" prop="createTime" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ $common.formatDateTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="false" :label="$t('common.creator')" align="center" prop="createBy" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="false" :label="$t('common.updater')" align="center" prop="updateBy" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="true" :label="$t('common.updateTime')" align="center" prop="updateTime" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ $common.formatDateTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="false" :label="$t('common.deleteFlag')" align="center" prop="delFlag" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="false" :label="$t('common.deleter')" align="center" prop="delBy" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column v-if="false" :label="$t('common.deleteTime')" align="center" prop="delTime" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ $common.formatDateTime(scope.row.delTime) }}</span>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="true" size="small" link icon="el-icon-edit" type="text"
            @click="handleUpdate(scope.row)" v-hasPermi="['autoee:goodsStockOut:edit']"
          >{{ $t('common.edit') }}
          </el-button>
          <el-button v-if="true" size="small" link icon="el-icon-zoom-in" type="text"
            @click="handleShowDetail(scope.row)" v-hasPermi="['autoee:goodsStockOut:list']"
          >{{ $t('common.view') }}
          </el-button>
          <el-button v-if="true" size="small" link icon="el-icon-delete" type="text"
            @click="handleDelete(scope.row)" v-hasPermi="['autoee:goodsStockOut:remove']"
          >{{ $t('common.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="pageData.listTotal>0"
      :total="pageData.listTotal"
      :page.sync="pageData.queryParams.pageNum"
      :limit.sync="pageData.queryParams.pageSize"
      @pagination="getList()"
    />

    <!-- 子组件引用 -->
    <addUpdateDialog ref="addUpdateDialogRef" @addUpdateSubmitCallback="getList('addUpdate')"/>
    <detailDialog ref="detailDialogRef" />
    <importDialog ref="importDialogRef" @importSubmitCallback="getList('import')"/>
  </div>
</template>
<script>
import moment from 'moment';
import { getToken } from "@/utils/auth";
import { parseTime } from "@/utils/index";

// 导入API
import goodsStockOut from "@/api/autoee/goodsStockOut";
import goodsStockOutExtend from "@/api/autoee/goodsStockOutExtend";

// 导入组件
import addUpdateDialog from './addUpdateDialog.vue';
import detailDialog from './detailDialog.vue';
import importDialog from './importDialog.vue';

// 部门选择器相关（如果有）

export default {
  name: 'GoodsStockOut',
  dicts: ['a_goods_info', 'sys_user', 'a_goods_stock_in', 'sys_dept'],
  components: {
    addUpdateDialog,
    detailDialog,
    importDialog,
  },
  data() {
    // 获取当前日期
    const today = new Date();
    // 设置开始日期 默认为空
    const baseBeginDate = "";
    // const baseBeginDate = moment(today).add(-30, 'days').format('YYYY-MM-DD');
    // 设置结束日期-默认为空
    const baseEndDate = "";
    // const baseEndDate = moment(today).add(0, 'days').format('YYYY-MM-DD');
    // 设置开始时间 默认为空
    const baseBeginTime = "";
    // const baseBeginTime = moment(today).add(-30, 'days').format('YYYY-MM-DD 00:00:00');
    const baseEndTime = "";
    // const baseEndTime = moment(today).add(0, 'days').format('YYYY-MM-DD 23:59:59');

    // 初始化日期范围
    const dateRanges = {};
	dateRanges.baseBeginDate = baseBeginDate
	dateRanges.baseBeginTime = baseBeginTime
	dateRanges.baseEndDate = baseEndDate
	dateRanges.baseEndTime = baseEndTime
    dateRanges.dateRangeUpdateTime = [];

    // 初始化级联下拉框选项
    const cascadeOptions = {};

    return {
      ...dateRanges,
      ...cascadeOptions,
	  pickerOptions: {
       shortcuts: [] // 先初始化空数组,实例创建后再访问 $common 并赋值,data 函数在组件实例初始化阶段（beforeCreate 之前）执行，此时组件实例尚未完全创建，$common 这类挂载在实例上的属性还未初始化，因此无法访问
      },
      checkTableDataHasChanged: false,
      pageData: {
        dicts: {
          a_goods_info: [],
          sys_user: [],
          a_goods_stock_in: [],
          sys_dept: [],
        },
        goodsStockOutList: [],
        loadingListData: true,
        showSearchTool: true,
        selectedIds: [],
        singleSelected: true,
        multipleSelected: true,
        listTotal: 0,
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          id: null,
          goodsId: null,
          reason: null,
          userId: null,
          updateTime: null,
          params: {
            beginUpdateTime: null,
            endUpdateTime: null,
          }
        },
        // 默认排序
        defaultSort: {prop: 'updateTime', order: 'descending'},
        rules: {
          goodsStockInId: [
            { required: true, message: "入库编号不能为空", trigger: "blur" }
          ],
          goodsId: [
            { required: true, message: "物品名称不能为空", trigger: "blur" }
          ],
          quantity: [
            { required: true, message: "出库数量不能为空", trigger: "blur" }
          ],
        }
      }
    };
  },
  watch: {
  },
  methods: {
    /** 查询列表 */
    getList(operateFlag) {
      // 主页增删改、导入、其他方法处理成功扩展方法
      goodsStockOutExtend.indexOperateSuccessExtend(this, operateFlag)
      this.pageData.loadingListData = true;

      if (this.dateRangeUpdateTime !== null && this.dateRangeUpdateTime !== '') {
        this.pageData.queryParams.params["beginUpdateTime"] = this.dateRangeUpdateTime[0];
        this.pageData.queryParams.params["endUpdateTime"] = this.dateRangeUpdateTime[1];
      } else {
        this.pageData.queryParams.params["beginUpdateTime"] = null;
        this.pageData.queryParams.params["endUpdateTime"] = null;
      }

      goodsStockOut.selectPageListGoodsStockOut(this.pageData.queryParams).then(response => {
        this.pageData.goodsStockOutList = response.rows;
        this.pageData.listTotal = response.total;
        this.pageData.loadingListData = false;
      });
    },

    /** 排序触发事件 */
    handleSortChange(column) {
      this.pageData.queryParams.orderByColumn = column.prop;
      this.pageData.queryParams.isAsc = column.order;
      this.getList();
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.pageData.queryParams.pageNum = 1;
      // 触发排序查询
      this.$refs.mainTable.sort(this.pageData.defaultSort.prop, this.pageData.defaultSort.order);
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.dateRangeUpdateTime = [this.baseBeginTime, this.baseEndTime];
      this.$refs.queryFormRef.resetFields();
	  this.resetForm("queryFormRef");
      this.handleQuery();
    },

    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.pageData.selectedIds = selection.map(item => item.id);
      this.pageData.singleSelected = selection.length !== 1;
      this.pageData.multipleSelected = !selection.length;
    },

    /** 新增按钮操作 */
    async handleAdd() {
        this.$refs.addUpdateDialogRef.openGoodsStockOutAddDialog(this.pageData.queryParams);
    },

    /** 修改按钮操作 */
    async handleUpdate(row) {
        const id = row ? row.id : this.pageData.selectedIds;
        this.$refs.addUpdateDialogRef.openGoodsStockOutUpdateDialog(id);
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const idsArr = row ? [row.id] : [];
      this.$modal.confirm('确认删除当前选中的记录吗？').then(() => {
        return goodsStockOut.deleteGoodsStockOutByIds(idsArr);
      }).then(() => {
        this.getList("delete");
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 多选删除按钮操作 */
    handleDeleteMuti() {
      const idsArr = this.pageData.selectedIds;
      this.$modal.confirm('确认删除当前选中的记录吗？').then(() => {
        return goodsStockOut.deleteGoodsStockOutByIds(idsArr);
      }).then(() => {
        this.getList("delete");
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 清空全部数据按钮操作 */
    handleDeleteAllData() {
      // 第一次确认对话框
      this.$modal.confirm('警告：此操作将清空全部数据！\n清空后不可恢复！\n确定要继续吗？')
        .then(() => {
          // 第二次确认对话框，要求用户输入确认文本
          return this.$modal.prompt('请输入"确认清空"以继续操作：', {
            inputPlaceholder: '确认删除',
            confirmButtonText: '确认',
            cancelButtonText: '取消'
          });
        })
        .then(({ value }) => {
          // 验证用户输入
          if (value.trim() === '确认清空') {
            // 执行删除操作
            return goodsStockOut.deleteGoodsStockOutAllData();
          } else {
            throw new Error('确认文本不正确，操作已取消');
          }
        })
        .then(() => {
          this.getList("delete");
          this.$modal.msgSuccess("清空全部数据成功");
        })
        .catch((error) => {
          if (error === 'cancel') {
            this.$modal.msgInfo("清空全部数据操作已取消");
          } else {
            this.$modal.msgError(error.message || "清空全部数据操作失败");
          }
        });
    },

    /** 查看按钮操作 */
    handleShowDetail(row) {
      this.$refs.detailDialogRef.openGoodsStockOutDetailDialog(row);
    },

    /** 导入按钮操作 */
    handleImport() {
      this.$refs.importDialogRef.openGoodsStockOutImportDialog(this.pageData.queryParams);
    },

    /** 导出按钮操作 */
    handleExport() {
      this.$modal.confirm('确认导出当前查询条件下的所有记录吗？').then(() => {
        if (this.dateRangeUpdateTime !== null && this.dateRangeUpdateTime !== '') {
          this.pageData.queryParams.params["beginUpdateTime"] = this.dateRangeUpdateTime[0];
          this.pageData.queryParams.params["endUpdateTime"] = this.dateRangeUpdateTime[1];
        } else {
          this.pageData.queryParams.params["beginUpdateTime"] = null;
          this.pageData.queryParams.params["endUpdateTime"] = null;
        }

        const fileExt = moment().format('YYYYMMDD_HHmmss');
        return this.download('autoee/goodsStockOut/exportGoodsStockOut', {...this.pageData.queryParams},
          `物品出库记录_导出_${fileExt}.xlsx`);
      }).catch((error) => {
        console.error("导出失败：", error);
      });
    },

    /** 查询部门下拉树结构 */

    /** 自定义页面按钮方法 */

    /** 自定义表格按钮方法 */

    /** 表格合计方法 */
    tableSummaryMethod(param) {
      return goodsStockOutExtend.tableSummaryMethodExtend(this, param);
    },

    /** 行样式方法 */
    rowClassName({row, rowIndex}) {
      return '';
    },
  },
  created() {
    // 实例创建后再访问 $common 并赋值
    this.pickerOptions.shortcuts = this.$common.dateShortcuts().map(shortcut => ({
   	  text: shortcut.text,
   	  onClick(picker) {
   	    const [start, end] = shortcut.value();
   	    picker.$emit('pick', [start, end]);
   	  }
    }));
  },
  mounted() {
    // 初始化列表数据
    this.getList();


    // 执行部门树初始化（如果有）

    // mounted扩展方法
    //goodsStockOutExtend.indexMountedStartExtend(this);
  }
}
</script>
