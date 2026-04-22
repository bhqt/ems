<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item :label="$t('chargingModule.pile.encoding')" prop="encoding">
        <el-input
          v-model="queryParams.encoding"
          :placeholder="$t('placeholder.input') + $t('chargingModule.pile.encoding')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.pile.name')" prop="name">
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('placeholder.input') + $t('chargingModule.pile.name')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.pile.merchantName')" prop="merchantName" >
        <el-input
          v-model="queryParams.merchantName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.pile.merchantName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.pile.stationName')" prop="stationName">
        <el-input
          v-model="queryParams.stationName"
          :placeholder="$t('placeholder.input') + $t('chargingModule.pile.stationName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('chargingModule.brand.brandName')" prop="brand">
        <el-select v-model="queryParams.brand" :placeholder="$t('placeholder.select')" clearable>
          <el-option v-for="brand in brandList"
            :key="brand.id"
            :label="brand.brandName"
            :value="brand.brandName"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.brand.modelName')" prop="model">
        <el-select v-model="queryParams.model" :placeholder="$t('placeholder.select')">
            <el-option
              v-for="model in modelList"
              :key="model.id"
              :label="model.modelName"
              :value="model.modelName"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.pile.merchant')" prop="merchantId">
        <el-select
          v-model="queryParams.merchantId"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
          :disabled="merchantId"
        >
          <el-option
            v-for="merchant in merchantList"
            :key="merchant.merchantId"
            :label="merchant.name"
            :value="merchant.merchantId"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('chargingModule.pile.station')" prop="stationId">
        <el-select
          v-model="queryParams.stationId"
          :placeholder="$t('placeholder.select')"
          clearable
          style="width: 215px"
        >
          <el-option
            v-for="station in stationList"
            :key="station.stationId"
            :label="station.name"
            :value="station.stationId"
          />
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
          v-hasPermi="['system:pile:add']"
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
          v-hasPermi="['system:pile:edit']"
        >{{ $t('common.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-open"
          size="mini"
          :disabled="multiple"
          @click="handleOpen"
          v-hasPermi="['system:pile:edit']"
        >{{ $t('chargingModule.pile.batchEnable') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-turn-off"
          size="mini"
          :disabled="multiple"
          @click="handleClose"
          v-hasPermi="['system:pile:edit']"
        >{{ $t('chargingModule.pile.batchDisable') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:pile:remove']"
        >{{ $t('common.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:pile:export']"
        >{{ $t('common.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('chargingModule.pile.encoding')" align="center" prop="encoding" />
      <el-table-column :label="$t('chargingModule.pile.name')" align="center" prop="name" />
      <el-table-column :label="$t('chargingModule.pile.merchantName')" align="center" prop="merchantName" />
      <el-table-column :label="$t('chargingModule.pile.stationName')" align="center" prop="stationName" />
      <el-table-column :label="$t('chargingModule.brand.brandName')" align="center" prop="brand" />
      <el-table-column :label="$t('chargingModule.brand.modelName')" align="center" prop="model" />
      <el-table-column :label="$t('chargingModule.pile.status')" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
            @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column :label="$t('chargingModule.pile.workStatus')" align="center" prop="workStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pile_status" :value="scope.row.workStatus" />
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('common.action')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:pile:edit']"
          >{{ $t('common.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:pile:remove']"
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

    <!-- 添加或修改充电桩信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="$t('chargingModule.pile.encoding')" prop="encoding">
          <el-input v-model="form.encoding" :placeholder="$t('placeholder.input') + $t('chargingModule.pile.encoding')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.pile.name')" prop="name">
          <el-input v-model="form.name" :placeholder="$t('placeholder.input') + $t('chargingModule.pile.name')" />
        </el-form-item>
        <el-form-item :label="$t('chargingModule.pile.merchant')" prop="merchantId">
          <el-select v-model="form.merchantId" :placeholder="$t('placeholder.select')" @change="merchantChange">
            <el-option v-for="item in merchantList" :key="item.merchantId" :label="item.name"
              :value="item.merchantId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.pile.station')" prop="stationId">
          <el-select v-model="form.stationId" :placeholder="$t('placeholder.select')" @change="stationChange">
            <el-option v-for="item in stationList" :key="item.stationId" :label="item.name"
              :value="item.stationId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.brand.brandName')" prop="brand">
          <el-select v-model="form.brand" :placeholder="$t('placeholder.select')" @change="changeBrand">
            <el-option
              v-for="brand in brandList"
              :key="brand.id"
              :label="brand.brandName"
              :value="brand.brandName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('chargingModule.brand.modelName')" prop="model">
          <el-select v-model="form.model" :placeholder="form.brand ? $t('placeholder.select') : $t('chargingModule.pile.selectBrandFirst')" :disabled="form.brand ? false : true">
            <el-option
              v-for="model in selectModel"
              :key="model.id"
              :label="model.modelName"
              :value="model.modelName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('common.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('placeholder.input') + $t('common.remark')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('common.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPile, getPile, delPile, addPile, updatePile, openOrClosePile } from "@/api/chargingStation/pile";
import { listStation } from "@/api/chargingStation/station";
import { listMerchant } from "@/api/chargingStation/merchant";
import {listBrand} from "@/api/chargingStation/brand"
import {listModel} from "@/api/chargingStation/model"
export default {
  name: "Pile",
  dicts: ['pile_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中电桩
      piles: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示查询条件
      showSearch: true,
      // 总条数
      total: 0,
      // 充电桩信息表格数据
      pileList: [],
      // 充电站信息表格数据
      stationList: [],
      // 商户信息表格数据
      merchantList: [],
      //页面跳转商户id
      merchantId: undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        encoding: undefined,
        type: undefined,
        name: undefined,
        merchantId: undefined,
        merchantName: undefined,
        stationId: undefined,
        stationName: undefined,
        brand: undefined,
        model: undefined,
        status: undefined,
        workStatus: undefined,
      },
      queryStationParams: {
        type:'1',
      },
      queryMerchantParams: {
        type:'1',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pileId: [
          { required: true, message: () => this.$t('validation.required', { field: 'ID' }), trigger: "blur" }
        ],
        encoding: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.encoding') }), trigger: "blur" }
        ],
        type: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.pileType') }), trigger: "change" }
        ],
        name: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.name') }), trigger: "blur" }
        ],
        merchantId: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.merchant') }), trigger: "blur" }
        ],
        merchantName: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.merchantName') }), trigger: "blur" }
        ],
        stationId: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.station') }), trigger: "blur" }
        ],
        stationName: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.pile.stationName') }), trigger: "blur" }
        ],
        brand: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.brand.brandName') }), trigger: "blur" }
        ],
        model: [
          { required: true, message: () => this.$t('validation.required', { field: this.$t('chargingModule.brand.modelName') }), trigger: "blur" }
        ],
      },
      brandList: [],
      modelList: [], // 所有型号列表
      selectModel: [], // 选中品牌的型号列表
    };
  },
  async created() {
    try {
      this.merchantId = this.$route.params?.merchantId;
      if (this.merchantId) {
        this.queryParams.merchantId = this.merchantId;
      }
      
      // P2优化：使用Promise.all控制并行请求，避免浏览器并发限制
      await Promise.all([
        this.getBrandList(),
        this.getModelList(),
        this.getStationList(),
        this.getMerchantList()
      ]);
      
      // 依赖基础数据的主列表查询
      await this.getList();
    } catch (error) {
      console.error('充电桩列表初始化失败:', error);
      this.$modal?.msgError?.('页面加载失败，请刷新重试') || alert('页面加载失败，请刷新重试');
    }
  },
  methods: {
    // 获取型号列表
    getModelList() {
      listModel({status: '0'}).then(res => {
        this.modelList = res.rows
      })
    },
    // 获取品牌列表
    getBrandList() {
      listBrand({status: '0'}).then(res => {
        this.brandList = res.rows
      })
    },
    // 电站选择
    stationChange(data) {
      var station = this.stationList.find(u => u.stationId == data)
      if (station) {
        this.form.stationName = station.name
      }
    },
    // 商户选择
    merchantChange(data) {
      var merchant = this.merchantList.find(u => u.merchantId == data)
      if (merchant) {
        this.form.merchantName = merchant.name
      }
    },
    
    /** 查询充电桩信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.type = '1';
      listPile(this.queryParams).then(response => {
        this.pileList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询充电站信息列表 */
    getStationList() {
      this.loading = true;
      listStation(this.queryStationParams).then(response => {
        this.stationList = response.rows;
        this.loading = false;
      });
    },
    /** 查询商户信息列表 */
    getMerchantList() {
      this.loading = true;
      listMerchant(this.queryMerchantParams).then(response => {
        this.merchantList = response.rows;
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
        pileId: undefined,
        encoding: undefined,
        type: undefined,
        name: undefined,
        merchantId: undefined,
        merchantName: undefined,
        stationId: undefined,
        stationName: undefined,
        brand: undefined,
        model: undefined,
        status: undefined,
        workStatus: undefined,
        delFlag: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
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
      this.ids = selection.map(item => item.pileId)
      this.piles = selection
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('chargingModule.pile.addTitle');
      this.selectModel = []
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const pileId = row.pileId || this.ids
      getPile(pileId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = this.$t('chargingModule.pile.editTitle');
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.pileId != null) {
            updatePile(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            this.form.type = '1';
            addPile(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('common.addSuccess'));
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
      const pileIds = row.pileId || this.ids;
      this.$modal.confirm(this.$t('chargingModule.pile.deleteConfirm', { ids: pileIds })).then(() => {
        this.loading = true;
        return delPile(pileIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess(this.$t('common.deleteSuccess'));
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 批量启用按钮操作 */
    handleOpen() {
      const piles = this.piles
      this.$modal.confirm(this.$t('chargingModule.pile.batchEnableConfirm')).then(() => {
        this.loading = true;
        piles.forEach(element => {
        element.status = '0'
      });
        return openOrClosePile(piles);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess(this.$t('chargingModule.pile.batchEnableSuccess'));
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 批量停用按钮操作 */
    handleClose() {
      const piles = this.piles
      this.$modal.confirm(this.$t('chargingModule.pile.batchDisableConfirm')).then(() => {
        this.loading = true;
        piles.forEach(element => {
        element.status = '1'
      });
        return openOrClosePile(piles);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess(this.$t('chargingModule.pile.batchDisableSuccess'));
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/pile/export', {
        ...this.queryParams
      }, `pile_${new Date().getTime()}.xlsx`)
    },
    // 电站状态开关状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? this.$t('common.enable') : this.$t('common.disable');
      this.$modal.confirm(this.$t('chargingModule.pile.statusConfirm', { text })).then(() => {
        return updatePile(row);
      }).then(() => {
        this.$modal.msgSuccess(this.$t('common.success'));
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // 修改品牌
    changeBrand(value) {
      this.form.model = undefined
      let brand = this.brandList.find(b => b.brandName == value)
      this.selectModel = this.modelList.filter(m => m.brandId == brand.id)
    }
  }
};
</script>
