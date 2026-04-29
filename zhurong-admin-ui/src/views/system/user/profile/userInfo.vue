<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item :label="$t('profileModule.nickName')" prop="nickName">
      <el-input v-model="user.nickName" maxlength="30" />
    </el-form-item>
    <el-form-item :label="$t('profileModule.phone')" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item :label="$t('profileModule.email')" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item :label="$t('profileModule.sex')">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">{{ $t('profileModule.male') }}</el-radio>
        <el-radio label="1">{{ $t('profileModule.female') }}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">{{ $t('common.save') }}</el-button>
      <el-button size="mini" @click="close">{{ $t('common.close') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      rules: {
        nickName: [
          { required: true, message: this.$t('validation.required', { field: this.$t('profileModule.nickName') }), trigger: "blur" }
        ],
        email: [
          { required: true, message: this.$t('validation.required', { field: this.$t('profileModule.email') }), trigger: "blur" },
          {
            type: "email",
            message: this.$t('validation.email'),
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: this.$t('validation.required', { field: this.$t('profileModule.phone') }), trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: this.$t('validation.phone'),
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
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
