<template>
  <div class="app-container">
    <!-- 新增或修改物品入库记录对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
                  <el-form-item v-show='true' label="物品名称" prop="goodsId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.goodsId" placeholder="请选择物品名称" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.a_goods_info"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="入库数量" prop="quantity" style="display: inline-block;width: 45%;">
                    <el-input v-model="addUpdateForm.quantity" placeholder="请输入整数" type="number" step="1" clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="存放地点" prop="supplier" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.supplier" placeholder="请输入存放地点" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='false' label="操作人员" prop="userId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.userId" placeholder="请选择操作人员" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="备注" prop="remark" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.remark" type="textarea" :rows="3" placeholder="请输入备注" maxlength="300" show-word-limit/>
                </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment';
import { getToken } from "@/utils/auth";
import { getDeptTreeFilterData } from "@/api/system/dept.js";
import goodsStockIn from "@/api/autoee/goodsStockIn";
import goodsStockInExtend from "@/api/autoee/goodsStockInExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['a_goods_info', 'sys_user', 'sys_dept'],
  data() {
    return {
	  datePickerOptions: {
       shortcuts: [] // 先初始化空数组,实例创建后再访问 $common 并赋值,data 函数在组件实例初始化阶段（beforeCreate 之前）执行，此时组件实例尚未完全创建，$common 这类挂载在实例上的属性还未初始化，因此无法访问
      },
	  dateTimePickerOptions: {
       shortcuts: [] // 先初始化空数组,实例创建后再访问 $common 并赋值,data 函数在组件实例初始化阶段（beforeCreate 之前）执行，此时组件实例尚未完全创建，$common 这类挂载在实例上的属性还未初始化，因此无法访问
      },
      dicts: {},
      deptTreeFilterData: null,
      open: false,
      title: "",
      loading: false,
      vueAppBaseApi: process.env.VUE_APP_BASE_API,
      addOrUpdate: "",
      addUpdateForm: {},
      addUpdateFormRules: {
        // 物品名称
        goodsId: [
          { required: true, message: "物品名称不能为空并且为整数", trigger: "change" },
        ],
        // 入库数量
        quantity: [
          { required: true, message: "入库数量不能为空并且为整数", trigger: "blur" },
          {validator: this.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
        ],
        // 存放地点
        supplier: [
        ],
        // 操作人员
        userId: [
        ],
        // 备注
        remark: [
        ],
        // 所属部门
        deptId: [
        ],
        // 创建时间
        createTime: [
        ],
        // 创建者
        createBy: [
        ],
        // 更新者
        updateBy: [
        ],
        // 更新时间
        updateTime: [
        ],
        // 删除标志
        delFlag: [
        ],
        // 删除者
        delBy: [
        ],
        // 删除时间
        delTime: [
        ]
      },
    }
  },
  created() {
    // 实例创建后再访问 $common 并赋值
    this.datePickerOptions.shortcuts = this.$common.datePickerOptionsShortcuts().map(shortcut => ({
   	  text: shortcut.text,
   	  onClick(picker) {
   	    const value = shortcut.value();
   	    picker.$emit('pick', value);
   	  }
    }));
    // 实例创建后再访问 $common 并赋值
    this.dateTimePickerOptions.shortcuts = this.$common.dateTimePickerOptionsShortcuts().map(shortcut => ({
   	  text: shortcut.text,
   	  onClick(picker) {
   	    const value = shortcut.value();
   	    picker.$emit('pick', value);
   	  }
    }));
  },
  methods: {
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.addUpdateForm = {
        id: null ,
        goodsId: null ,
        quantity: null ,
        supplier: null ,
        userId: null ,
        remark: null ,
        deptId: null ,
        createTime: null ,
        createBy: null ,
        updateBy: null ,
        updateTime: null ,
        delFlag: null ,
        delBy: null ,
        delTime: null 
      };
	  this.resetForm("addUpdateFormRef");
    },
    /** 打开新增窗口 */
    openGoodsStockInAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加物品入库记录";
      this.addOrUpdate = "add";
      goodsStockInExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openGoodsStockInUpdateDialog(id, dicts) {
      this.reset();
      goodsStockIn.selectDataByPkGoodsStockIn(id).then(response => {
        this.addUpdateForm = response.data;
        this.title = "修改物品入库记录";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          if (this.addUpdateForm.id != null) {
            goodsStockIn.updateNullValueByGoodsStockIn(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            goodsStockIn.addGoodsStockIn(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          }
        }
      });
    },
  },
  mounted() {
    // mounted扩展方法
    goodsStockInExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
