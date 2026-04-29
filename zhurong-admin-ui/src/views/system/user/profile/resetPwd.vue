<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item :label="$t('profileModule.oldPassword')" prop="oldPassword">
      <el-input v-model="user.oldPassword" :placeholder="$t('profileModule.placeholder.inputOldPwd')" type="password" show-password/>
    </el-form-item>
    <el-form-item :label="$t('profileModule.newPassword')" prop="newPassword">
      <el-input v-model="user.newPassword" :placeholder="$t('profileModule.placeholder.inputNewPwd')" type="password" show-password/>
    </el-form-item>
    <el-form-item :label="$t('profileModule.confirmPassword')" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" :placeholder="$t('profileModule.placeholder.confirmNewPwd')" type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">{{ $t('common.save') }}</el-button>
      <el-button size="mini" @click="close">{{ $t('common.close') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error(this.$t('profileModule.pwdMismatch')));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      rules: {
        oldPassword: [
          { required: true, message: this.$t('profileModule.pwdNotEmpty'), trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: this.$t('profileModule.pwdNotEmpty'), trigger: "blur" },
          { min: 6, max: 20, message: this.$t('profileModule.pwdLength'), trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: this.$t('profileModule.confirmPwdNotEmpty'), trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(response => {
            this.$modal.msgSuccess(this.$t('common.success'));
            this.$emit('closeDialog', true)
          });
        }
      });
    },
    close() {
      this.$emit('closeDialog', false)
    }
  },
  beforeDestroy() {
    this.$refs['form'].resetFields()
  }
};
</script>
