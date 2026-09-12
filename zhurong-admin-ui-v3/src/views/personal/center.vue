<template>
  <div class="personal-center">
    <a-card :title="t('personal.center')" :bordered="bordered">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="1" :tab="t('personal.info')">
          <a-form
            ref="infoFormRef"
            :model="infoForm"
            :layout="vertical"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 20 }"
          >
            <a-form-item :label="t('personal.username')">
              <a-input v-model:value="infoForm.userName" disabled>
                <template #prefix><UserOutlined /></template>
              </a-input>
            </a-form-item>
            <a-form-item :label="t('personal.nickName')">
              <a-input v-model:value="infoForm.nickName" :placeholder="t('placeholder.inputNickName')">
                <template #prefix><UserOutlined /></template>
              </a-input>
            </a-form-item>
            <a-form-item :label="t('personal.email')">
              <a-input v-model:value="infoForm.email" :placeholder="t('placeholder.inputEmail')">
                <template #prefix><MailOutlined /></template>
              </a-input>
            </a-form-item>
            <a-form-item :label="t('personal.phone')">
              <a-input v-model:value="infoForm.phone" :placeholder="t('placeholder.inputPhone')">
                <template #prefix><PhoneOutlined /></template>
              </a-input>
            </a-form-item>
            <a-form-item :label="t('personal.sex')">
              <a-radio-group v-model:value="infoForm.sex" button-style="solid">
                <a-radio-button value="0">{{ t('personal.male') }}</a-radio-button>
                <a-radio-button value="1">{{ t('personal.female') }}</a-radio-button>
              </a-radio-group>
            </a-form-item>
            <a-form-item :wrapper-col="{ offset: 4, span: 20 }">
              <a-button type="primary" @click="saveInfo">{{ t('common.save') }}</a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="2" :tab="t('personal.password')">
          <a-form
            ref="passwordFormRef"
            :model="passwordForm"
            :rules="passwordRules"
            :layout="vertical"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 20 }"
          >
            <a-form-item :label="t('personal.oldPassword')" name="oldPassword">
              <a-input-password v-model:value="passwordForm.oldPassword" :placeholder="t('placeholder.inputOldPassword')">
                <template #prefix><LockOutlined /></template>
              </a-input-password>
            </a-form-item>
            <a-form-item :label="t('personal.newPassword')" name="newPassword">
              <a-input-password v-model:value="passwordForm.newPassword" :placeholder="t('placeholder.inputNewPassword')">
                <template #prefix><LockOutlined /></template>
              </a-input-password>
            </a-form-item>
            <a-form-item :label="t('personal.confirmPassword')" name="confirmPassword">
              <a-input-password v-model:value="passwordForm.confirmPassword" :placeholder="t('placeholder.confirmNewPassword')">
                <template #prefix><LockOutlined /></template>
              </a-input-password>
            </a-form-item>
            <a-form-item :wrapper-col="{ offset: 4, span: 20 }">
              <a-button type="primary" @click="changePassword">{{ t('common.save') }}</a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { message } from 'ant-design-vue'
import { UserOutlined, LockOutlined, MailOutlined, PhoneOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/modules/user'

const { t } = useI18n()
const userStore = useUserStore()

const bordered = ref(true)
const activeTab = ref('1')
const infoFormRef = ref()
const passwordFormRef = ref()

const infoForm = reactive({
  userName: '',
  nickName: '',
  email: '',
  phone: '',
  sex: '0',
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 8, message: '密码长度不能少于8位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (_rule: any, value: string) => {
        if (value !== passwordForm.newPassword) {
          return Promise.reject(new Error('两次密码不一致'))
        }
        return Promise.resolve()
      },
      trigger: 'blur',
    },
  ],
}

async function fetchUserInfo() {
  try {
    const info = await userStore.fetchUserInfo()
    infoForm.userName = info.userName
    infoForm.nickName = info.nickName || ''
    infoForm.email = info.email || ''
    infoForm.phone = info.phone || ''
    infoForm.sex = info.sex || '0'
  } catch (error) {
    console.error('Fetch user info error:', error)
  }
}

async function saveInfo() {
  message.success('保存成功')
}

async function changePassword() {
  message.success('密码修改成功，请重新登录')
  userStore.logout()
}

onMounted(() => {
  fetchUserInfo()
})
</script>

<style scoped>
.personal-center {
  padding: 24px;
}
</style>