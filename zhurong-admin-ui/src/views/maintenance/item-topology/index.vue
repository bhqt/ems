<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('maintenanceModule.projectName')" prop="itemName">
        <el-input v-model="queryParams.itemName" :placeholder="$t('maintenanceModule.projectName')" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('common.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('common.status')" clearable>
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('maintenanceModule.rootNodeName')" prop="itemType">
        <el-select v-model="queryParams.itemType" :placeholder="$t('common.pleaseSelect')" clearable>
          <el-option v-for="dict in dict.type.root_node_type" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:itemTopology:add']">{{ $t('common.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">{{ $t('maintenanceModule.expandCollapse') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="itemTopologyList" row-key="itemId"
      :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column prop="itemName" :label="$t('maintenanceModule.projectName')" width="260"></el-table-column>
      <el-table-column prop="orderNum" :label="$t('maintenanceModule.orderNum')" width="200"></el-table-column>
      <el-table-column prop="itemType" :label="$t('maintenanceModule.rootNode')" width="200">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.root_node_type" :value="scope.row.itemType" />
        </template>
      </el-table-column>
      <el-table-column prop="status" :label="$t('common.status')" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:itemTopology:edit']">{{ $t('common.edit') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
            v-hasPermi="['system:itemTopology:add']">{{ $t('common.add') }}</el-button>
          <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['system:itemTopology:remove']">{{ $t('common.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item :label="$t('maintenanceModule.parentProject')" prop="parentId">
              <treeselect v-model="form.parentId" :options="itemTopologyOptions" :normalizer="normalizer"
                :placeholder="$t('maintenanceModule.parentProject')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('maintenanceModule.projectName')" prop="itemName">
              <el-input v-model="form.itemName" :placeholder="$t('maintenanceModule.projectName')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('maintenanceModule.displayOrder')" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('maintenanceModule.projectType')" prop="itemType">
              <el-select v-model="form.itemType" :placeholder="$t('common.pleaseSelect')" clearable>
                <el-option v-for="dict in dict.type.root_node_type" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('maintenanceModule.relatedEquipment')" prop="deviceIds">
              <el-select v-model="form.deviceIds" multiple :placeholder="$t('maintenanceModule.relatedEquipment')" style="width:100%" @remove-tag="removeTag">
                <template #empty>
                  <EquipmentTable :selectIds="form.deviceIds || []" @selectChange="selectChange"/>
                </template>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="form.leader" :placeholder="$t('common.pleaseInput')" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" :placeholder="$t('common.pleaseInput')" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row> -->
        <el-row>
          <!-- <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" :placeholder="$t('common.pleaseInput')" maxlength="50" />
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item :label="$t('maintenanceModule.projectStatus')">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label
                }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('common.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listItemTopology,
  getItemTopology,
  delItemTopology,
  addItemTopology,
  updateItemTopology,
  listItemTopologyExcludeChild,
} from "@/api/system/itemTopology";
import {
  listEquipmentInfo,
} from "@/api/system/equipmentInfo";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import EquipmentTable from "@/views/maintenance/item-topology/equipmentTable"

export default {
  name: "ItemTopology",
  dicts: ["sys_normal_disable", 'root_node_type'],
  components: { Treeselect, EquipmentTable },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示查询条件
      showSearch: true,
      // 表格树数据
      itemTopologyList: [],
      // 项目树选项
      itemTopologyOptions: [],
      // 设备信息表格数据
      deviceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        itemName: undefined,
        status: undefined,
        itemType: undefined
      },
      queryDeviceParams: {
        itemName: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "上级项目不能为空", trigger: "blur" },
        ],
        itemName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        orderNum: [
          { required: true, message: "显示排序不能为空", trigger: "blur" },
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDevices();
  },
  methods: {
    /** 查询设备*/
    getDevices() {
      listEquipmentInfo(this.queryDeviceParams).then((response) => {
        this.deviceList = response.rows;
      });
    },
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listItemTopology(this.queryParams).then((response) => {
        this.itemTopologyList = this.handleTree(response.data, "itemId");
        this.loading = false;
      });
    },
    /** 转换项目数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.itemId,
        label: node.itemName,
        children: node.children,
      };
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        itemId: undefined,
        parentId: undefined,
        itemName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        deviceId: undefined,
        status: "0",
        deviceIds: [],
      };
      this.resetForm("form");
    },
    /** 查询按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.itemId;
      }
      this.open = true;
      this.title = "添加项目";
      listItemTopology().then((response) => {
        this.itemTopologyOptions = this.handleTree(response.data, "itemId");
      });
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getItemTopology(row.itemId).then((response) => {
        this.form = response.data;
        this.$set(this.form, "deviceId", response.data.deviceId);
        this.open = true;
        this.title = "修改项目";
        listItemTopologyExcludeChild(row.itemId).then((response) => {
          this.itemTopologyOptions = this.handleTree(response.data, "itemId");
          if (this.itemTopologyOptions.length == 0) {
            const noResultsOptions = {
              itemId: this.form.parentId,
              itemName: this.form.parentName,
              children: [],
            };
            this.itemTopologyOptions.push(noResultsOptions);
          }
        });
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.itemId != undefined) {
            updateItemTopology(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItemTopology(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除？')
        .then(function () {
          return delItemTopology(row.itemId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    // 设备选择
    selectChange(data) {
      this.form.deviceIds = data
    },
    // 设备多选移除tag
    removeTag(data) {
      this.form.deviceIds = this.form.deviceIds.filter(d => d !== data)
    }
  },
};
</script>
