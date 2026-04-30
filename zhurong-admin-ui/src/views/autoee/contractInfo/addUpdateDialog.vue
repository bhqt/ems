<template>
  <div class="app-container">
    <!-- 新增或修改合同信息管理对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
       <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef"  :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
                <el-form-item v-show='true' label="合同编号(新)" prop="contractNoNew" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.contractNoNew" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="续签编号(老)" prop="contractNoOld" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.contractNoOld" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="所属客户" prop="belongCustomer" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.belongCustomer" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="客户方联系人" prop="customerContact" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.customerContact" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                  <el-form-item v-show='true' label="合同类型" prop="contractType" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.contractType" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.contract_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item v-show='true' label="合同子类型" prop="contractSubtype" style="display: inline-block;width: 45%;">
                    <el-select clearable v-model="addUpdateForm.contractSubtype" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
                      <el-option
                        v-for="dict in dict.type.contract_subtype"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                <el-form-item v-show='true' label="签约公司" prop="signCompany" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.signCompany" :placeholder="$t('common.pleaseInput')" maxlength="100" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="业务员" prop="salesmanId" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.salesmanId" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="技术支持" prop="techSupport" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.techSupport" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="报价单号" prop="quoteNo" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.quoteNo" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="合同总价" prop="contractTotal" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.contractTotal" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="已收金额" prop="receivedAmount" style="display: inline-block;width: 45%;">
                      <el-input v-model="addUpdateForm.receivedAmount" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
                </el-form-item>
                <el-form-item v-show='true' label="签约日期" prop="signDate" style="display: inline-block;width: 45%;">
                  <el-date-picker v-model="addUpdateForm.signDate" type="date" value-format="yyyy-MM-dd" :picker-options="datePickerOptions" :placeholder="$t('common.pleaseSelect')" clearable style="width: 100%"/>
                </el-form-item>
                <el-form-item v-show='true' label="附件" prop="attachmentFiles" style="width: 90%;">
                  <file-upload v-model="addUpdateForm.attachmentFiles"/>
                  <!-- <file-upload v-model="addUpdateForm.attachmentFiles"/> -->
                </el-form-item>
                <el-form-item v-show='true' :label="$t('common.remark')" prop="remark" style="display: inline-block;width: 90%;">
                  <el-input v-model="addUpdateForm.remark" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" maxlength="250" show-word-limit/>
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
        <el-button type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment';
import { getToken } from "@/utils/auth";
import { getDeptTreeFilterData } from "@/api/system/dept.js";
import contractInfo from "@/api/autoee/contractInfo";
import contractInfoExtend from "@/api/autoee/contractInfoExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['sys_user', 'contract_type', 'contract_subtype', 'sys_dept'],
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
        // 合同编号(新)
        contractNoNew: [
          { required: true, message: "合同编号(新)不能为空", trigger: "blur" },
        ],
        // 续签编号(老)
        contractNoOld: [
        ],
        // 所属客户
        belongCustomer: [
          { required: true, message: "所属客户不能为空", trigger: "blur" },
        ],
        // 客户方联系人
        customerContact: [
          { required: true, message: "客户方联系人不能为空", trigger: "blur" },
        ],
        // 合同类型
        contractType: [
          { required: true, message: "合同类型不能为空", trigger: "change" },
        ],
        // 合同子类型
        contractSubtype: [
        ],
        // 签约公司
        signCompany: [
        ],
        // 业务员
        salesmanId: [
        ],
        // 技术支持
        techSupport: [
        ],
        // 报价单号
        quoteNo: [
        ],
        // 合同总价
        contractTotal: [
        ],
        // 已收金额
        receivedAmount: [
        ],
        // 签约日期
        signDate: [
        ],
        // 附件
        attachmentFiles: [
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
        contractNoNew: null ,
        contractNoOld: null ,
        belongCustomer: null ,
        customerContact: null ,
        contractType: null ,
        contractSubtype: null ,
        signCompany: null ,
        salesmanId: null ,
        techSupport: null ,
        quoteNo: null ,
        contractTotal: null ,
        receivedAmount: null ,
        signDate: null ,
        attachmentFiles: null ,
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
    openContractInfoAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加合同信息管理";
      this.addOrUpdate = "add";
      contractInfoExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openContractInfoUpdateDialog(id, dicts) {
      this.reset();
      contractInfo.selectDataByPkContractInfo(id).then(response => {
        this.addUpdateForm = response.data;
        this.title = "修改合同信息管理";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {
          if (this.addUpdateForm.id != null) {
            contractInfo.updateNullValueByContractInfo(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            contractInfo.addContractInfo(this.addUpdateForm).then(response => {
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
    contractInfoExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style>
</style>
