<template>
  <div class="login-container">
    <div class="login-form">
      <div class="login-header">
        <div class="login-logo">
          <DashboardOutlined class="logo-icon" />
          <span>{{ t('common.appTitle') }}</span>
        </div>
        <h1 class="login-title">{{ t('login.title') }}</h1>
        <p class="login-subtitle">{{ t('login.subtitle') }}</p>
      </div>

      <a-form
        ref="formRef"
        :model="loginForm"
        :rules="rules"
        layout="vertical"
        @finish="handleSubmit"
        class="login-form-content"
      >
        <a-form-item name="username">
          <a-input
            v-model:value="loginForm.username"
            :placeholder="t('login.usernamePlaceholder')"
            autocomplete="username"
          >
            <template #prefix><UserOutlined /></template>
          </a-input>
        </a-form-item>

        <a-form-item name="password">
          <a-input-password
            v-model:value="loginForm.password"
            :placeholder="t('login.passwordPlaceholder')"
            autocomplete="current-password"
            @press-enter="handleSubmit"
          >
            <template #prefix><LockOutlined /></template>
          </a-input-password>
        </a-form-item>

        <a-form-item name="rememberMe" style="margin-bottom: 16px">
          <a-checkbox v-model:checked="loginForm.rememberMe">
            {{ t('login.rememberMe') }}
          </a-checkbox>
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            block
            :loading="loading"
            html-type="submit"
          >
            {{ t('login.signIn') }}
          </a-button>
        </a-form-item>
      </a-form>

      <div class="login-footer">
        <a href="javascript:;" @click="switchLanguage">{{ currentLocale === 'zh-CN' ? 'English' : '中文' }}</a>
        <span class="divider">|</span>
        <a href="javascript:;" @click="toggleTheme">{{ isDark ? t('common.themeLight') : t('common.themeDark') }}</a>
        <span class="divider">|</span>
        <a href="https://github.com" target="_blank" rel="noopener">GitHub</a>
      </div>
    </div>

    <div class="login-background">
      <div class="bg-shape shape-1"></div>
      <div class="bg-shape shape-2"></div>
      <div class="bg-shape shape-3"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { message } from 'ant-design-vue'
import { UserOutlined, LockOutlined, DashboardOutlined } from '@ant-design/icons-vue'
import { useAppStore } from '@/stores/modules/app'
import { useUserStore } from '@/stores/modules/user'

const router = useRouter()
const route = useRoute()
const { t } = useI18n()
const appStore = useAppStore()
const userStore = useUserStore()

const formRef = ref()
const loading = ref(false)
const isDark = computed(() => appStore.isDark)

const loginForm = reactive({
  username: 'admin',
  password: '123456',
  rememberMe: true,
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

const currentLocale = computed(() => appStore.locale)

async function handleSubmit() {
  try {
    await formRef.value?.validate()
    loading.value = true
    
    await userStore.login({
      username: loginForm.username,
      password: loginForm.password,
      rememberMe: loginForm.rememberMe,
    })
    
    message.success(t('login.signInSuccess'))
    
    const redirect = (route.query.redirect as string) || '/dashboard/workbench'
    router.push(redirect)
  } catch (error) {
    console.error('Login error:', error)
  } finally {
    loading.value = false
  }
}

function switchLanguage() {
  appStore.setLocale(currentLocale.value === 'zh-CN' ? 'en-US' : 'zh-CN')
}

function toggleTheme() {
  appStore.setTheme(isDark.value ? 'light' : 'dark')
}
</script>

<style scoped>
.login-container {
  position: relative;
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--zhurong-color-bg-layout);
  overflow: hidden;
}

.login-form {
  position: relative;
  z-index: 10;
  width: 100%;
  max-width: 400px;
  padding: 48px 40px;
  background: var(--zhurong-color-bg-container);
  border-radius: var(--zhurong-border-radius-lg);
  box-shadow: var(--zhurong-box-shadow-lg);
  border: 1px solid var(--zhurong-color-border);
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-logo {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
  color: var(--zhurong-color-primary);
}

.logo-icon {
  font-size: 32px;
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  color: var(--zhurong-color-text-heading);
  margin: 0 0 8px;
}

.login-subtitle {
  color: var(--zhurong-color-text-secondary);
  font-size: 14px;
  margin: 0;
}

.login-form-content {
  margin-bottom: 24px;
}

.login-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  padding-top: 16px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
  font-size: 13px;
  color: var(--zhurong-color-text-tertiary);
}

.divider {
  color: var(--zhurong-color-border);
}

.login-footer a {
  color: var(--zhurong-color-primary);
  text-decoration: none;
  transition: color 0.2s;
}

.login-footer a:hover {
  color: var(--zhurong-color-primary-hover);
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  pointer-events: none;
}

.bg-shape {
  position: absolute;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--zhurong-color-primary-bg), var(--zhurong-color-primary-border));
  opacity: 0.3;
  animation: float 20s infinite ease-in-out;
}

.shape-1 {
  width: 400px;
  height: 400px;
  top: -100px;
  right: -100px;
  animation-delay: 0s;
}

.shape-2 {
  width: 300px;
  height: 300px;
  bottom: -50px;
  left: -50px;
  animation-delay: -7s;
}

.shape-3 {
  width: 200px;
  height: 200px;
  top: 40%;
  left: 10%;
  animation-delay: -14s;
}

@keyframes float {
  0%, 100% {
    transform: translate(0, 0) scale(1);
  }
  25% {
    transform: translate(30px, -30px) scale(1.05);
  }
  50% {
    transform: translate(-20px, 20px) scale(0.95);
  }
  75% {
    transform: translate(20px, 30px) scale(1.02);
  }
}

@media (max-width: 480px) {
  .login-form {
    margin: 16px;
    padding: 32px 24px;
    max-width: none;
  }
  
  .login-title {
    font-size: 20px;
  }
}
</style>