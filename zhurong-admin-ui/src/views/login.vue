<template>
  <div class="login-page" :class="{ 'is-mobile': isMobile }">
    <div class="bg-layer">
      <span class="blob blob-1"></span>
      <span class="blob blob-2"></span>
      <span class="blob blob-3"></span>
      <div class="grid-mask"></div>
      <div class="noise"></div>
    </div>

    <header class="login-header">
      <div class="brand">
        <span class="brand-mark">
          <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="1.8"
            stroke-linecap="round" stroke-linejoin="round">
            <path d="M13 2 4 14h6l-1 8 9-12h-6l1-8z" />
          </svg>
        </span>
        <span class="brand-title">{{ sysTitle }}</span>
      </div>
      <div class="header-right">
        <lang-select />
      </div>
    </header>

    <main class="login-body">
      <section class="intro">
        <h1 class="intro-title">
          <span>{{ $t('login.title') }}</span>
        </h1>
        <p class="intro-desc">{{ $t('login.slogan') }}</p>
        <ul class="intro-points">
          <li v-for="item in highlights" :key="item.key">
            <span class="dot"></span>{{ item.text }}
          </li>
        </ul>
      </section>

      <section class="panel">
        <div class="panel-inner">
          <div class="panel-head">
            <h2 class="title">{{ $t('login.title') }}</h2>
            <p class="panel-tip">{{ $t('login.welcome') }}</p>
          </div>

          <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" type="text" auto-complete="off"
                :placeholder="$t('placeholder.username')">
                <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" auto-complete="off"
                :placeholder="$t('placeholder.password')" @keyup.enter.native="handleLogin" show-password>
                <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
              </el-input>
            </el-form-item>
            <el-form-item prop="code" v-if="captchaEnabled" class="code-item">
              <el-input v-model="loginForm.code" auto-complete="off" :placeholder="$t('placeholder.captcha')"
                @keyup.enter.native="handleLogin">
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
              </el-input>
              <div class="login-code">
                <img :src="codeUrl" @click="getCode" class="login-code-img" />
              </div>
            </el-form-item>

            <div class="select-click">
              <el-checkbox v-model="loginForm.rememberMe" class="unchanged">{{ $t('login.remember') }}</el-checkbox>
            </div>

            <el-form-item class="submit-item">
              <el-button :loading="loading" type="primary" class="submit-btn"
                @click.native.prevent="handleLogin">
                <span v-if="!loading">{{ $t('login.login') }}</span>
                <span v-else>{{ $t('login.logging') }}</span>
              </el-button>
            </el-form-item>

            <div class="panel-links">
              <router-link class="link-type" :to="'/applyAccount'" v-if="experienceShow">
                {{ $t('login.experience') || '获取体验账号' }}
              </router-link>
              <router-link class="link-type" :to="'/register'" v-if="register">
                {{ $t('login.register') || '立即注册' }}
              </router-link>
            </div>
          </el-form>
        </div>
      </section>
    </main>

    <footer class="el-login-footer">
      <span>{{ sysTitle }}</span>
    </footer>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import LangSelect from '@/components/LangSelect'

export default {
  name: "Login",
  components: {
    LangSelect
  },
  data() {
    return {
      isMobile: false, // 默认为Web端
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  mounted() {
    this.checkWindowSize(); // 在组件挂载后检查窗口大小
    window.addEventListener('resize', this.checkWindowSize); // 监听窗口大小变化
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.checkWindowSize); // 组件销毁前移除事件监听
  },
  computed: {
    highlights() {
      return [
        { key: 'h1', text: this.$t('login.point1') },
        { key: 'h2', text: this.$t('login.point2') },
        { key: 'h3', text: this.$t('login.point3') }
      ]
    },
    sysTitle: function () {
      // 登录页 Logo 标题：与 store/app.js 的 getLogoInfo 兜底逻辑保持一致，
      // 避免后端 sys_config 残留旧值（如「祝融能源管理系统」/「智碳能源管理系统」）导致标题错误。
      const backendSysTitle = this.$store.getters.logoInfo?.sysTitle
      const DEFAULT_SYS_TITLE = '智慧能源监控平台'
      return (backendSysTitle && backendSysTitle !== '祝融能源管理系统' && backendSysTitle !== '智碳能源管理系统')
        ? backendSysTitle
        : DEFAULT_SYS_TITLE
    },
    logoShow() {
      return this.$store.getters.logoInfo.loginLogo
    },
    experienceShow() {
      return this.$store.getters.logoInfo.experienceShow
    },
    loginRules() {
      // 通过访问 language 建立响应式依赖
      const _ = this.$store.getters.language;

      return {
        username: [
          { required: true, trigger: "blur", message: this.$t('login.usernameRequired') }
        ],
        password: [
          { required: true, trigger: "blur", message: this.$t('login.passwordRequired') }
        ],
        code: [{ required: true, trigger: "blur", message: this.$t('login.captchaRequired') }]
      }
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    checkWindowSize() {
      if (window.innerWidth <= 768) {
        this.isMobile = true; // 窗口宽度小于等于768px时，判断为手机端
      } else {
        this.isMobile = false; // 窗口宽度大于768px时，判断为Web端
      }
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.data.captchaEnabled === undefined ? true : res.data.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.data.img;
          this.loginForm.uuid = res.data.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(() => { });
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
$ink: #0a1630;
$accent: #4f8cff;

.login-page {
  position: relative;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  background:
    radial-gradient(1200px 620px at 12% -8%, #1b3a7a 0%, rgba(27, 58, 122, 0) 60%),
    radial-gradient(1000px 560px at 92% 108%, #0d5c63 0%, rgba(13, 92, 99, 0) 62%),
    linear-gradient(150deg, #060d1f 0%, #0a1730 48%, #071226 100%);
  color: #e8eefc;
}

/* ---------- 背景层 ---------- */
.bg-layer {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.blob {
  position: absolute;
  border-radius: 50%;
  filter: blur(90px);
  opacity: 0.55;
  animation: drift 18s ease-in-out infinite;
}

.blob-1 {
  width: 520px;
  height: 520px;
  left: -140px;
  top: -160px;
  background: radial-gradient(circle, #2f6bff 0%, rgba(47, 107, 255, 0) 70%);
}

.blob-2 {
  width: 460px;
  height: 460px;
  right: -120px;
  bottom: -180px;
  background: radial-gradient(circle, #16d0c0 0%, rgba(22, 208, 192, 0) 70%);
  animation-delay: -6s;
}

.blob-3 {
  width: 380px;
  height: 380px;
  right: 28%;
  top: 12%;
  background: radial-gradient(circle, #7a5cff 0%, rgba(122, 92, 255, 0) 70%);
  animation-delay: -12s;
}

@keyframes drift {

  0%,
  100% {
    transform: translate3d(0, 0, 0) scale(1);
  }

  50% {
    transform: translate3d(40px, -30px, 0) scale(1.12);
  }
}

.grid-mask {
  position: absolute;
  inset: 0;
  background-image:
    linear-gradient(rgba(255, 255, 255, 0.045) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255, 255, 255, 0.045) 1px, transparent 1px);
  background-size: 56px 56px;
  mask-image: radial-gradient(circle at 50% 40%, #000 0%, transparent 78%);
  -webkit-mask-image: radial-gradient(circle at 50% 40%, #000 0%, transparent 78%);
}

.noise {
  position: absolute;
  inset: 0;
  opacity: 0.16;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='120' height='120'%3E%3Cfilter id='n'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.85' numOctaves='3'/%3E%3C/filter%3E%3Crect width='120' height='120' filter='url(%23n)' opacity='0.5'/%3E%3C/svg%3E");
}

/* ---------- 顶栏 ---------- */
.login-header {
  position: relative;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 26px 48px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-mark {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 38px;
  height: 38px;
  border-radius: 12px;
  color: #ffffff;
  background: linear-gradient(135deg, #4f8cff 0%, #22d3ee 100%);
  box-shadow: 0 10px 26px rgba(79, 140, 255, 0.45);
}

.brand-title {
  font-size: 19px;
  font-weight: 700;
  letter-spacing: 3px;
  color: #ffffff;
}

/* ---------- 主体 ---------- */
.login-body {
  position: relative;
  z-index: 2;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 96px;
  padding: 24px 48px 72px;
  flex-wrap: wrap;
}

.intro {
  max-width: 520px;
  animation: rise 0.7s ease both;
}

.intro-title {
  margin: 0 0 18px;
  font-size: 46px;
  line-height: 1.2;
  font-weight: 700;
  letter-spacing: 6px;
  background: linear-gradient(92deg, #ffffff 10%, #7fd7ff 60%, #4f8cff 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.intro-desc {
  margin: 0 0 30px;
  font-size: 15px;
  letter-spacing: 4px;
  color: rgba(226, 236, 255, 0.72);
}

.intro-points {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 14px;

  li {
    display: flex;
    align-items: center;
    gap: 12px;
    font-size: 14px;
    letter-spacing: 1px;
    color: rgba(216, 230, 255, 0.78);
    padding: 12px 16px;
    border-radius: 12px;
    border: 1px solid rgba(255, 255, 255, 0.09);
    background: rgba(255, 255, 255, 0.045);
    backdrop-filter: blur(8px);
    width: fit-content;
  }
}

.dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: linear-gradient(135deg, #22d3ee, #4f8cff);
  box-shadow: 0 0 0 4px rgba(79, 140, 255, 0.18);
}

/* ---------- 表单卡片 ---------- */
.panel {
  width: 420px;
  border-radius: 24px;
  padding: 1px;
  background: linear-gradient(150deg, rgba(255, 255, 255, 0.35), rgba(255, 255, 255, 0.05) 42%, rgba(79, 140, 255, 0.28));
  box-shadow: 0 40px 90px rgba(3, 10, 26, 0.6);
  animation: rise 0.7s 0.12s ease both;
}

.panel-inner {
  border-radius: 23px;
  padding: 38px 36px 30px;
  background: linear-gradient(170deg, rgba(17, 30, 60, 0.92) 0%, rgba(9, 18, 40, 0.94) 100%);
  backdrop-filter: blur(20px);
}

.panel-head {
  margin-bottom: 26px;
}

.title {
  margin: 0 0 8px;
  font-size: 24px;
  font-weight: 700;
  letter-spacing: 4px;
  text-align: center;
  color: #ffffff;
}

.panel-tip {
  margin: 0;
  text-align: center;
  font-size: 12px;
  letter-spacing: 2px;
  color: rgba(190, 208, 240, 0.6);
}

.login-form {
  .el-input {
    height: 48px;

    input {
      height: 48px;
    }
  }

  .input-icon {
    height: 48px;
    width: 15px;
    margin-left: 2px;
  }

  ::v-deep .el-input__inner {
    height: 48px;
    line-height: 48px;
    padding-left: 40px;
    border-radius: 12px;
    color: #eaf1ff;
    background: rgba(255, 255, 255, 0.06);
    border: 1px solid rgba(255, 255, 255, 0.14);
    transition: all 0.25s ease;

    &::placeholder {
      color: rgba(190, 208, 240, 0.45);
    }

    &:hover {
      border-color: rgba(122, 176, 255, 0.5);
    }

    &:focus {
      border-color: $accent;
      background: rgba(79, 140, 255, 0.12);
      box-shadow: 0 0 0 4px rgba(79, 140, 255, 0.16);
    }
  }

  ::v-deep .el-input__prefix {
    left: 14px;
  }

  ::v-deep .el-input__suffix {
    right: 12px;
  }

  ::v-deep .el-input__icon {
    color: rgba(160, 190, 240, 0.6);
  }

  ::v-deep .el-form-item {
    margin-bottom: 20px;
  }

  ::v-deep .el-checkbox__label {
    color: rgba(200, 216, 245, 0.75);
    font-size: 13px;
  }

  ::v-deep .el-checkbox__inner {
    background: rgba(255, 255, 255, 0.06);
    border-color: rgba(255, 255, 255, 0.28);
  }
}

.code-item {
  display: flex;
  align-items: center;
  gap: 12px;

  ::v-deep .el-form-item__content {
    display: flex;
    align-items: center;
    gap: 12px;
  }

  ::v-deep .el-input {
    flex: 1;
  }
}

.login-code {
  flex: 0 0 118px;
  height: 46px;
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.14);
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-code-img {
  height: 46px;
  cursor: pointer;
}

.select-click {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.submit-item {
  margin-bottom: 6px !important;
}

.submit-btn {
  width: 100%;
  height: 48px;
  border: none;
  border-radius: 12px;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 4px;
  color: #ffffff;
  background: linear-gradient(120deg, #3d7dff 0%, #22b8ff 50%, #22d3ee 100%);
  box-shadow: 0 14px 30px rgba(45, 130, 255, 0.38);
  transition: transform 0.2s ease, box-shadow 0.2s ease, filter 0.2s ease;

  &:hover {
    filter: brightness(1.08);
    box-shadow: 0 18px 38px rgba(45, 130, 255, 0.5);
  }

  &:active {
    transform: translateY(1px);
  }
}

.panel-links {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 14px;
  min-height: 18px;
}

.link-type {
  color: #8fb6ff;
  font-size: 12px;
  letter-spacing: 1px;
  text-decoration: none;

  &:hover {
    color: #ffffff;
  }
}

@keyframes rise {
  from {
    opacity: 0;
    transform: translateY(24px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ---------- 底部 ---------- */
.el-login-footer {
  position: relative;
  z-index: 2;
  padding: 18px 0 24px;
  text-align: center;
  color: rgba(180, 200, 235, 0.45);
  font-size: 12px;
  letter-spacing: 3px;
}

/* ---------- 移动端 ---------- */
.login-page.is-mobile {
  .login-header {
    padding: 20px 20px 0;
  }

  .brand-title {
    font-size: 16px;
    letter-spacing: 2px;
  }

  .brand-mark {
    width: 32px;
    height: 32px;
    border-radius: 10px;
  }

  .login-body {
    gap: 32px;
    padding: 18px 18px 56px;
    justify-content: flex-start;
  }

  .intro {
    text-align: center;
    max-width: 100%;
  }

  .intro-title {
    font-size: 28px;
    letter-spacing: 3px;
  }

  .intro-desc {
    font-size: 13px;
    letter-spacing: 2px;
    margin-bottom: 20px;
  }

  .intro-points {
    display: none;
  }

  .panel {
    width: 100%;
    border-radius: 20px;
  }

  .panel-inner {
    padding: 28px 22px 22px;
  }

  .title {
    font-size: 20px;
  }
}
</style>