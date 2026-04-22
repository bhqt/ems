<template>
  <div class="app-container">
    <!-- 新增或修改巡更记录对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
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
                  <el-form-item v-show='true' label="巡更路线" prop="patrolPathId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolPathId" placeholder="请选择巡更路线" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.a_patrol_path"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="巡更点位" prop="patrolPointId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolPointId" placeholder="请选择巡更点位" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.a_patrol_point"
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
                <el-form-item v-show='true' label="点位顺序" prop="pointOrder" style="display: inline-block;width: 45%;">
                    <el-input v-model="addUpdateForm.pointOrder" placeholder="请输入整数" type="number" step="1" clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="点位巡更时间" prop="arriveTime" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.arriveTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="dateTimePickerOptions" placeholder="请选择点位巡更时间" clearable style="width: 100%"/>
                </el-form-item>
                  <el-form-item v-show='true' label="巡更结果" prop="patrolResult" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolResult" placeholder="请选择巡更结果" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.patrol_result"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="结果描述" prop="resultDesc" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.resultDesc" type="textarea" :rows="3" placeholder="请输入结果描述" maxlength="300" show-word-limit/>
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
import patrolRecord from "@/api/autoee/patrolRecord";
import patrolRecordExtend from "@/api/autoee/patrolRecordExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['a_patrol_plan', 'a_patrol_task', 'sys_user', 'a_patrol_path', 'a_patrol_point', 'patrol_result', 'sys_dept'],
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
        // 巡更计划
        patrolPlanId: [
          { required: true, message: "巡更计划不能为空并且为整数", trigger: "change" },
        ],
        // 巡更路线
        patrolPathId: [
          { required: true, message: "巡更路线不能为空并且为整数", trigger: "change" },
        ],
        // 巡更点位
        patrolPointId: [
          { required: true, message: "巡更点位不能为空并且为整数", trigger: "change" },
        ],
        // 巡更人员
        patrolUserId: [
          { required: true, message: "巡更人员不能为空并且为整数", trigger: "change" },
        ],
        // 巡更任务
        patrolTaskId: [
          { required: true, message: "巡更任务不能为空并且为整数", trigger: "change" },
        ],
        // 点位顺序
        pointOrder: [
          { required: true, message: "点位顺序不能为空并且为整数", trigger: "blur" },
          {validator: this.$validateRules.validatePositiveInteger, trigger: ["blur", "change"]},
        ],
        // 点位巡更时间
        arriveTime: [
          { required: true, message: "点位巡更时间不能为空", trigger: "blur" },
        ],
        // 巡更结果
        patrolResult: [
          { required: true, message: "巡更结果不能为空", trigger: "change" },
        ],
        // 结果描述
        resultDesc: [
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
        patrolPlanId: null ,
        patrolPathId: null ,
        patrolPointId: null ,
        patrolUserId: null ,
        patrolTaskId: null ,
        pointOrder: null ,
        arriveTime: null ,
        patrolResult: null ,
        resultDesc: null ,
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
    openPatrolRecordAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加巡更记录";
      this.addOrUpdate = "add";
      patrolRecordExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openPatrolRecordUpdateDialog(id, dicts) {
      this.reset();
      patrolRecord.selectDataByPkPatrolRecord(id).then(response => {
        this.addUpdateForm = response.data;
        this.title = "修改巡更记录";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          if (this.addUpdateForm.id != null) {
            patrolRecord.updateNullValueByPatrolRecord(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            patrolRecord.addPatrolRecord(this.addUpdateForm).then(response => {
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
    patrolRecordExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
