import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useSettingsStore = defineStore('settings', () => {
  const showSettings = ref(false)
  const showThemePicker = ref(false)
  const showLanguagePicker = ref(false)

  function toggleSettings() {
    showSettings.value = !showSettings.value
  }

  function closeSettings() {
    showSettings.value = false
  }

  function openThemePicker() {
    showThemePicker.value = true
  }

  function closeThemePicker() {
    showThemePicker.value = false
  }

  function openLanguagePicker() {
    showLanguagePicker.value = true
  }

  function closeLanguagePicker() {
    showLanguagePicker.value = false
  }

  return {
    showSettings,
    showThemePicker,
    showLanguagePicker,
    toggleSettings,
    closeSettings,
    openThemePicker,
    closeThemePicker,
    openLanguagePicker,
    closeLanguagePicker,
  }
}, {
  persist: {
    key: 'zhurong-settings',
  },
})