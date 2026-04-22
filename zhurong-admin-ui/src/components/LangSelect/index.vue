<template>
  <el-dropdown trigger="click" class="international" @command="handleSetLanguage">
    <div class="lang-select-wrapper">
      <svg-icon icon-class="international" class-name="international-icon" />
      <span class="lang-text">{{ currentLanguageName }}</span>
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item
        v-for="lang in supportLanguages"
        :key="lang.code"
        :command="lang.code"
        :disabled="language === lang.code"
      >
        <span class="lang-flag">{{ lang.flag }}</span>
        {{ lang.name }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import { mapGetters } from 'vuex'
import { supportLanguages } from '@/i18n'

export default {
  name: 'LangSelect',
  data() {
    return {
      supportLanguages
    }
  },
  computed: {
    ...mapGetters(['language']),
    currentLanguageName() {
      const lang = supportLanguages.find(l => l.code === this.language)
      return lang ? lang.name : '简体中文'
    }
  },
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('app/setLanguage', lang)
      this.$message.success(this.$t('login.loginSuccess'))
      // 刷新页面以重新加载菜单等数据
      // location.reload()
    }
  }
}
</script>

<style scoped lang="scss">
.international {
  display: inline-block;
  padding: 0 8px;
  cursor: pointer;

  .lang-select-wrapper {
    display: flex;
    align-items: center;
    height: 100%;

    .international-icon {
      font-size: 18px;
      margin-right: 4px;
    }

    .lang-text {
      font-size: 14px;
      color: #606266;
    }
  }

  &:hover {
    .lang-text {
      color: #409EFF;
    }
  }
}

.lang-flag {
  margin-right: 4px;
}
</style>
