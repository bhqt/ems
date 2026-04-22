<template>
  <div class="app-container">
    <!-- 新增或修改维修工单对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
                <el-form-item v-show='true' label="工单编号" prop="orderNo" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.orderNo" placeholder="请输入工单编号" maxlength="32" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="问题描述" prop="description" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.description" type="textarea" :rows="3" placeholder="请输入问题描述" maxlength="250" show-word-limit/>
                </el-form-item>
                  <el-form-item v-show='true' label="故障类型" prop="orderFaultType" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.orderFaultType" placeholder="请选择故障类型" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.order_fault_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="优先级" prop="orderPriority" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.orderPriority" placeholder="请选择优先级" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.order_priority"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="工单状态" prop="repairOrderStatus" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.repairOrderStatus" placeholder="请选择工单状态" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.repair_order_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="故障位置" prop="location" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.location" placeholder="请输入故障位置" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='true' label="报修人" prop="reporterId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.reporterId" placeholder="请选择报修人" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="报修人电话" prop="reporterContact" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.reporterContact" placeholder="请输入报修人电话" maxlength="10" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="报修时间" prop="reportTime" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.reportTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="dateTimePickerOptions" placeholder="请选择报修时间" clearable style="width: 100%"/>
                </el-form-item>
                  <el-form-item v-show='true' label="维修人" prop="assigneeId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.assigneeId" placeholder="请选择维修人" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="维修结果" prop="repairResult" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.repairResult" type="textarea" :rows="3" placeholder="请输入维修结果" maxlength="100" show-word-limit/>
                </el-form-item>
                <el-form-item v-show='true' label="维修图片" prop="repairImages" style="width: 90%;">
				  <!--  ems工程：v-model绑定新上传后的值，:values绑定预览图片的值 -->
                  <image-upload v-model="addUpdateForm.repairImages" :values="addUpdateForm.repairImages" />
                  <!--  <image-upload v-model="addUpdateForm.repairImages" /> -->
                </el-form-item>
                <el-form-item v-show='true' label="完成时间" prop="completionTime" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.completionTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="dateTimePickerOptions" placeholder="请选择完成时间" clearable style="width: 100%"/>
                </el-form-item>
                <el-form-item v-show='true' label="备注" prop="remark" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.remark" type="textarea" :rows="3" placeholder="请输入备注" maxlength="300" show-word-limit/>
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
import maintainOrder from "@/api/autoee/maintainOrder";
import maintainOrderExtend from "@/api/autoee/maintainOrderExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['repair_order_status', 'order_priority', 'sys_user', 'order_fault_type', 'sys_dept'],
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
        // 工单编号
        orderNo: [
          { required: true, message: "工单编号不能为空", trigger: "blur" },
        ],
        // 问题描述
        description: [
          { required: true, message: "问题描述不能为空", trigger: "blur" },
        ],
        // 故障类型
        orderFaultType: [
          { required: true, message: "故障类型不能为空", trigger: "change" },
        ],
        // 优先级
        orderPriority: [
          { required: true, message: "优先级不能为空", trigger: "change" },
        ],
        // 工单状态
        repairOrderStatus: [
          { required: true, message: "工单状态不能为空", trigger: "change" },
        ],
        // 故障位置
        location: [
        ],
        // 报修人
        reporterId: [
        ],
        // 报修人电话
        reporterContact: [
        ],
        // 报修时间
        reportTime: [
        ],
        // 维修人
        assigneeId: [
        ],
        // 维修结果
        repairResult: [
        ],
        // 维修图片
        repairImages: [
        ],
        // 完成时间
        completionTime: [
        ],
        // 备注
        remark: [
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
        orderNo: null ,
        description: null ,
        orderFaultType: null ,
        orderPriority: null ,
        repairOrderStatus: null ,
        location: null ,
        reporterId: null ,
        reporterContact: null ,
        reportTime: null ,
        assigneeId: null ,
        repairResult: null ,
        repairImages: null ,
        completionTime: null ,
        remark: null ,
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
    openMaintainOrderAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加维修工单";
      this.addOrUpdate = "add";
      maintainOrderExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openMaintainOrderUpdateDialog(id, dicts) {
      this.reset();
      maintainOrder.selectDataByPkMaintainOrder(id).then(response => {
        this.addUpdateForm = response.data;
        this.title = "修改维修工单";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          if (this.addUpdateForm.id != null) {
            maintainOrder.updateNullValueByMaintainOrder(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            maintainOrder.addMaintainOrder(this.addUpdateForm).then(response => {
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
    maintainOrderExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
