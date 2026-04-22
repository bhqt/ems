<template>
  <div class="app-container">
    <!-- 新增或修改巡更报警对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
                <el-form-item v-show='true' label="报警编号" prop="alarmNo" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.alarmNo" placeholder="请输入报警编号" maxlength="25" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='true' label="巡更计划" prop="patrolPlanId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolPlanId" placeholder="请选择巡更计划" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.a_patrol_plan"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="巡更任务" prop="patrolTaskId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolTaskId" placeholder="请选择巡更任务" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.a_patrol_task"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="巡更人员" prop="patrolUserId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolUserId" placeholder="请选择巡更人员" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="报警类型" prop="patrolAlarmType" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolAlarmType" placeholder="请选择报警类型" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.patrol_alarm_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="报警时间" prop="patrolAlarmTime" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.patrolAlarmTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="dateTimePickerOptions" placeholder="请选择报警时间" clearable style="width: 100%"/>
                </el-form-item>
                <el-form-item v-show='true' label="报警内容" prop="patrolAlarmContent" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.patrolAlarmContent" type="textarea" :rows="3" placeholder="请输入报警内容" maxlength="300" show-word-limit/>
                </el-form-item>
                  <el-form-item v-show='true' label="报警状态" prop="patrolAlarmStatus" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolAlarmStatus" placeholder="请选择报警状态" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.patrol_alarm_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="处理人" prop="handleUserId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.handleUserId" placeholder="请选择处理人" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="处理时间" prop="handleTime" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.handleTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="dateTimePickerOptions" placeholder="请选择处理时间" clearable style="width: 100%"/>
                </el-form-item>
                <el-form-item v-show='true' label="处理描述" prop="handleDesc" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.handleDesc" type="textarea" :rows="3" placeholder="请输入处理描述" maxlength="300" show-word-limit/>
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
import patrolAlarm from "@/api/autoee/patrolAlarm";
import patrolAlarmExtend from "@/api/autoee/patrolAlarmExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['patrol_alarm_status', 'a_patrol_plan', 'a_patrol_task', 'patrol_alarm_type', 'sys_user', 'sys_dept'],
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
        // 报警编号
        alarmNo: [
          { required: true, message: "报警编号不能为空", trigger: "blur" },
        ],
        // 巡更计划
        patrolPlanId: [
          { required: true, message: "巡更计划不能为空并且为整数", trigger: "change" },
        ],
        // 巡更任务
        patrolTaskId: [
          { required: true, message: "巡更任务不能为空并且为整数", trigger: "change" },
        ],
        // 巡更人员
        patrolUserId: [
          { required: true, message: "巡更人员不能为空并且为整数", trigger: "change" },
        ],
        // 报警类型
        patrolAlarmType: [
          { required: true, message: "报警类型不能为空", trigger: "change" },
        ],
        // 报警时间
        patrolAlarmTime: [
          { required: true, message: "报警时间不能为空", trigger: "blur" },
        ],
        // 报警内容
        patrolAlarmContent: [
          { required: true, message: "报警内容不能为空", trigger: "blur" },
        ],
        // 报警状态
        patrolAlarmStatus: [
          { required: true, message: "报警状态不能为空", trigger: "change" },
        ],
        // 处理人
        handleUserId: [
        ],
        // 处理时间
        handleTime: [
        ],
        // 处理描述
        handleDesc: [
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
        // 创建者
        createBy: [
        ],
        // 创建时间
        createTime: [
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
        alarmNo: null ,
        patrolPlanId: null ,
        patrolTaskId: null ,
        patrolUserId: null ,
        patrolAlarmType: null ,
        patrolAlarmTime: null ,
        patrolAlarmContent: null ,
        patrolAlarmStatus: null ,
        handleUserId: null ,
        handleTime: null ,
        handleDesc: null ,
        remark: null ,
        userId: null ,
        deptId: null ,
        createBy: null ,
        createTime: null ,
        updateBy: null ,
        updateTime: null ,
        delFlag: null ,
        delBy: null ,
        delTime: null 
      };
	  this.resetForm("addUpdateFormRef");
    },
    /** 打开新增窗口 */
    openPatrolAlarmAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加巡更报警";
      this.addOrUpdate = "add";
      patrolAlarmExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openPatrolAlarmUpdateDialog(id, dicts) {
      this.reset();
      patrolAlarm.selectDataByPkPatrolAlarm(id).then(response => {
        this.addUpdateForm = response.data;
        this.title = "修改巡更报警";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          if (this.addUpdateForm.id != null) {
            patrolAlarm.updateNullValueByPatrolAlarm(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            patrolAlarm.addPatrolAlarm(this.addUpdateForm).then(response => {
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
    patrolAlarmExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
