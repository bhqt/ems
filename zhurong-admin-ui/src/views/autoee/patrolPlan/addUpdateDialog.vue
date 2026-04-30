<template>
  <div class="app-container">
    <!-- 新增或修改巡更计划对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="80%" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
                <el-form-item v-show='true' label="巡更计划名称" prop="patrolPlanName" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.patrolPlanName" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='true' label="巡更路线" prop="patrolPathId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolPathId" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.a_patrol_path"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="巡更人员" prop="patrolUserId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolUserId" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.sys_user"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="开始时间" prop="startTime" style="display: inline-block;width: 45%;">
                  <el-time-picker v-model="addUpdateForm.startTime"  format="HH:mm" value-format="HH:mm" :placeholder="$t('common.pleaseSelect')" clearable style="width: 100%"/>
                </el-form-item>
                <el-form-item v-show='true' label="结束时间" prop="endTime" style="display: inline-block;width: 45%;">
                  <el-time-picker v-model="addUpdateForm.endTime"  format="HH:mm" value-format="HH:mm" :placeholder="$t('common.pleaseSelect')" clearable style="width: 100%"/>
                </el-form-item>
                  <el-form-item v-show='true' label="巡更周期" prop="patrolCycleType" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolCycleType" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.patrol_cycle_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show="addUpdateForm.patrolCycleType && addUpdateForm.patrolCycleType.toString() === 'week'" label="巡更周期值" prop="patrolCycleValue" style="display: inline-block;width: 90%;">
                  <el-checkbox-group v-model="addUpdateForm.patrolCycleValue">
                    <el-checkbox label="周一" style="margin-right: 20px;"></el-checkbox>
                    <el-checkbox label="周二" style="margin-right: 20px;"></el-checkbox>
                    <el-checkbox label="周三" style="margin-right: 20px;"></el-checkbox>
                    <el-checkbox label="周四" style="margin-right: 20px;"></el-checkbox>
                    <el-checkbox label="周五" style="margin-right: 20px;"></el-checkbox>
                    <el-checkbox label="周六" style="margin-right: 20px;"></el-checkbox>
                    <el-checkbox label="周日" style="margin-right: 20px;"></el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
                <el-form-item v-show='true' label="计划开始日期" prop="startDate" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.startDate" type="date" value-format="yyyy-MM-dd" :picker-options="datePickerOptions" :placeholder="$t('common.pleaseSelect')" clearable style="width: 100%"/>
                </el-form-item>
                <el-form-item v-show='true' label="计划结束日期" prop="endDate" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.endDate" type="date" value-format="yyyy-MM-dd" :picker-options="datePickerOptions" :placeholder="$t('common.pleaseSelect')" clearable style="width: 100%"/>
                </el-form-item>
                  <el-form-item v-show='true' label="计划状态" prop="patrolPlanStatus" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.patrolPlanStatus" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.patrol_plan_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' :label="$t('common.remark')" prop="remark" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.remark" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" maxlength="300" show-word-limit/>
                </el-form-item>
                  <el-form-item v-show='false' label="所属用户" prop="userId" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.userId" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
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
import patrolPlan from "@/api/autoee/patrolPlan";
import patrolPlanExtend from "@/api/autoee/patrolPlanExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['patrol_cycle_type', 'sys_user', 'a_patrol_path', 'patrol_plan_status', 'sys_dept'],
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
      addUpdateForm: {
        id: null,
        patrolPlanName: null,
        patrolPathId: null,
        patrolUserId: null,
        startTime: null,
        endTime: null,
        patrolCycleType: null,
        patrolCycleValue: [], // 初始化巡更周期值为数组，用于存储多选的星期
        startDate: null,
        endDate: null,
        patrolPlanStatus: null,
        remark: null,
        userId: null,
        deptId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        delBy: null,
        delTime: null
      },
      addUpdateFormRules: {
        // 巡更计划名称
        patrolPlanName: [
          { required: true, message: "巡更计划名称不能为空", trigger: "blur" },
        ],
        // 巡更路线
        patrolPathId: [
          { required: true, message: "巡更路线不能为空并且为整数", trigger: "change" },
        ],
        // 巡更人员
        patrolUserId: [
          { required: true, message: "巡更人员不能为空并且为整数", trigger: "change" },
        ],
        // 开始时间
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        // 结束时间
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        // 巡更周期
        patrolCycleType: [
          { required: true, message: "巡更周期不能为空", trigger: "change" },
        ],
        // 巡更周期值
        patrolCycleValue: [
        ],
        // 计划开始日期
        startDate: [
          { required: true, message: "计划开始日期不能为空", trigger: "blur" },
        ],
        // 计划结束日期
        endDate: [
          { required: true, message: "计划结束日期不能为空", trigger: "blur" },
        ],
        // 计划状态
        patrolPlanStatus: [
          { required: true, message: "计划状态不能为空", trigger: "change" },
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
      // 逐个重置属性，而不是替换整个对象，保持Vue响应式系统正常工作
      this.addUpdateForm.id = null;
      this.addUpdateForm.patrolPlanName = null;
      this.addUpdateForm.patrolPathId = null;
      this.addUpdateForm.patrolUserId = null;
      this.addUpdateForm.startTime = null;
      this.addUpdateForm.endTime = null;
      this.addUpdateForm.patrolCycleType = null;
      // 清空数组而不是替换整个数组
      this.addUpdateForm.patrolCycleValue.splice(0, this.addUpdateForm.patrolCycleValue.length);
      this.addUpdateForm.startDate = null;
      this.addUpdateForm.endDate = null;
      this.addUpdateForm.patrolPlanStatus = null;
      this.addUpdateForm.remark = null;
      this.addUpdateForm.userId = null;
      this.addUpdateForm.deptId = null;
      this.addUpdateForm.createBy = null;
      this.addUpdateForm.createTime = null;
      this.addUpdateForm.updateBy = null;
      this.addUpdateForm.updateTime = null;
      this.addUpdateForm.delFlag = null;
      this.addUpdateForm.delBy = null;
      this.addUpdateForm.delTime = null;

      this.resetForm("addUpdateFormRef");
    },
    /** 打开新增窗口 */
    openPatrolPlanAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加巡更计划";
      this.addOrUpdate = "add";
      patrolPlanExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openPatrolPlanUpdateDialog(id, dicts) {
      this.reset();
      patrolPlan.selectDataByPkPatrolPlan(id).then(response => {
        const data = response.data;
        // 逐个赋值属性，而不是替换整个对象，保持Vue响应式系统正常工作
        this.addUpdateForm.id = data.id;
        this.addUpdateForm.patrolPlanName = data.patrolPlanName;
        this.addUpdateForm.patrolPathId = data.patrolPathId;
        this.addUpdateForm.patrolUserId = data.patrolUserId;
        this.addUpdateForm.startTime = data.startTime;
        this.addUpdateForm.endTime = data.endTime;
        this.addUpdateForm.patrolCycleType = data.patrolCycleType;
        this.addUpdateForm.startDate = data.startDate;
        this.addUpdateForm.endDate = data.endDate;
        this.addUpdateForm.patrolPlanStatus = data.patrolPlanStatus;
        this.addUpdateForm.remark = data.remark;

        // 特别处理patrolCycleValue
        // 当巡更周期类型为week时，将字符串类型的patrolCycleValue转换为数组类型
        if (this.addUpdateForm.patrolCycleType === 'week') {
          // 清空现有数组
          this.addUpdateForm.patrolCycleValue.splice(0, this.addUpdateForm.patrolCycleValue.length);
          // 将字符串"1,2,3"转换为数组["周一","周二","周三"]
          const weekMap = {1: '周一', 2: '周二', 3: '周三', 4: '周四', 5: '周五', 6: '周六', 7: '周日'};
          // 确保patrolCycleValue是数组类型，无论是否有值
          if (data.patrolCycleValue) {
            // 确保patrolCycleValue是字符串，并且处理可能的空值
            const cycleValueStr = String(data.patrolCycleValue);
            if (cycleValueStr && cycleValueStr.trim()) {
                // 将解析后的星期添加到现有数组，而不是替换整个数组
                const parsedDays = cycleValueStr.split(',').map(day => {
                  const dayNum = parseInt(day.trim(), 10);
                  return weekMap[dayNum] || day; // 如果映射不存在，保留原始值
                }).filter(day => day); // 过滤掉空值

                // 添加解析后的日期到现有数组
                parsedDays.forEach(day => {
                  this.addUpdateForm.patrolCycleValue.push(day);
                });
              } else {
                // 保持数组为空（已经在前面清空过了）
              }
          } else {
              // 保持数组引用不变，仅清空内容
              this.addUpdateForm.patrolCycleValue.splice(0, this.addUpdateForm.patrolCycleValue.length);
            }
          }
        this.title = "修改巡更计划";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          // 创建一个表单数据的深拷贝
          const formData = {...this.addUpdateForm};

          // 确保patrolCycleValue始终是字符串类型
          if (formData.patrolCycleType === 'week') {
            // 当巡更周期类型为week时，将数组类型的patrolCycleValue转换为字符串类型
            // 确保patrolCycleValue是数组
            const cycleValueArray = Array.isArray(formData.patrolCycleValue) ? formData.patrolCycleValue : [];

            if (cycleValueArray.length > 0) {
              // 将数组["周一","周二","周三"]转换为数字数组并按1-7排序，然后转换为字符串"1,2,3"
              const weekMap = {'周一': 1, '周二': 2, '周三': 3, '周四': 4, '周五': 5, '周六': 6, '周日': 7};
              formData.patrolCycleValue = cycleValueArray
                .map(day => weekMap[day])
                .filter(day => day !== undefined)
                .sort((a, b) => a - b) // 按数字1-7排序
                .join(',');
            } else {
              formData.patrolCycleValue = ''; // 如果没有选择任何星期，设置为空字符串
            }
          } else {
            // 对于非week类型，确保patrolCycleValue是字符串类型
            if (Array.isArray(formData.patrolCycleValue)) {
              formData.patrolCycleValue = formData.patrolCycleValue.toString();
            } else if (formData.patrolCycleValue === null || formData.patrolCycleValue === undefined) {
              formData.patrolCycleValue = ''; // 空值转换为空字符串
            } else {
              formData.patrolCycleValue = String(formData.patrolCycleValue); // 其他类型转换为字符串
            }
          }

          if (formData.id != null) {
            patrolPlan.updateNullValueByPatrolPlan(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            patrolPlan.addPatrolPlan(formData).then(response => {
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
    patrolPlanExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
