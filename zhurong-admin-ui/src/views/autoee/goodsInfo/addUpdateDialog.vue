<template>
  <div class="app-container">
    <!-- 新增或修改物品信息管理对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
                <el-form-item v-show='true' label="物品名称" prop="goodsName" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.goodsName" placeholder="请输入物品名称" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='true' label="物品类型" prop="goodsType" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.goodsType" placeholder="请选择物品类型" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.goods_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="物品状态" prop="goodsStatus" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.goodsStatus" placeholder="请选择物品状态" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.goods_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="规格型号" prop="specification" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.specification" placeholder="请输入规格型号" maxlength="100" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='true' label="单位" prop="goodsUnit" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.goodsUnit" placeholder="请选择单位" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.goods_unit"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="存储位置" prop="storageLocation" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.storageLocation" placeholder="请输入存储位置" maxlength="200" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='false' label="所属用户" prop="userId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.userId" placeholder="请选择所属用户" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
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
import goodsInfo from "@/api/autoee/goodsInfo";
import goodsInfoExtend from "@/api/autoee/goodsInfoExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['goods_unit', 'sys_user', 'goods_status', 'goods_type', 'sys_dept'],
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
        goodsName: [
          { required: true, message: "物品名称不能为空", trigger: "blur" },
        ],
        // 物品类型
        goodsType: [
          { required: true, message: "物品类型不能为空", trigger: "change" },
        ],
        // 物品状态
        goodsStatus: [
          { required: true, message: "物品状态不能为空", trigger: "change" },
        ],
        // 规格型号
        specification: [
          { required: true, message: "规格型号不能为空", trigger: "blur" },
        ],
        // 单位
        goodsUnit: [
          { required: true, message: "单位不能为空", trigger: "change" },
        ],
        // 存储位置
        storageLocation: [
        ],
        // 所属用户
        userId: [
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
        goodsName: null ,
        goodsType: null ,
        goodsStatus: null ,
        specification: null ,
        goodsUnit: null ,
        storageLocation: null ,
        userId: null ,
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
    openGoodsInfoAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加物品信息管理";
      this.addOrUpdate = "add";
      goodsInfoExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openGoodsInfoUpdateDialog(id, dicts) {
      this.reset();
      goodsInfo.selectDataByPkGoodsInfo(id).then(response => {
        this.addUpdateForm = response.data;
        this.title = "修改物品信息管理";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          if (this.addUpdateForm.id != null) {
            goodsInfo.updateNullValueByGoodsInfo(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            goodsInfo.addGoodsInfo(this.addUpdateForm).then(response => {
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
    goodsInfoExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
