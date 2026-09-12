import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export interface Campus {
  id: string
  name: string
  code: string
  address: string
  timezone: string
  locale: 'zh-CN' | 'en-US'
  unitSystem: 'metric' | 'imperial'
}

export interface DeviceType {
  id: string
  name: string
  code: string
  icon: string
  category: 'CT' | 'MRI' | 'DR' | 'ULTRASOUND' | 'LAB_LINE' | 'DSA' | 'OTHER'
}

export const useHospitalStore = defineStore('hospital', () => {
  // ============ State ============
  const currentCampus = ref<Campus | null>(null)
  const campusList = ref<Campus[]>([])
  const deviceTypes = ref<DeviceType[]>([])
  const selectedDeviceTypes = ref<string[]>([])
  const dateRange = ref<[string, string] | null>(null)
  const energyTypes = ref<string[]>(['electricity', 'water', 'gas', 'steam'])
  const selectedEnergyType = ref<string>('electricity')

  // ============ Getters ============
  const campusOptions = computed(() => campusList.value.map(c => ({
    label: c.name,
    value: c.id,
  })))

  const deviceTypeOptions = computed(() => deviceTypes.value.map(d => ({
    label: d.name,
    value: d.id,
  })))

  // ============ Actions ============
  function setCurrentCampus(campus: Campus | null) {
    currentCampus.value = campus
    if (campus) {
      localStorage.setItem('hospital-campus', campus.id)
    }
  }

  function setCampusList(list: Campus[]) {
    campusList.value = list
  }

  function setDeviceTypes(types: DeviceType[]) {
    deviceTypes.value = types
  }

  function toggleDeviceType(typeId: string) {
    const index = selectedDeviceTypes.value.indexOf(typeId)
    if (index > -1) {
      selectedDeviceTypes.value.splice(index, 1)
    } else {
      selectedDeviceTypes.value.push(typeId)
    }
  }

  function setSelectedDeviceTypes(types: string[]) {
    selectedDeviceTypes.value = types
  }

  function clearSelectedDeviceTypes() {
    selectedDeviceTypes.value = []
  }

  function setDateRange(range: [string, string] | null) {
    dateRange.value = range
  }

  function setSelectedEnergyType(type: string) {
    selectedEnergyType.value = type
  }

  function initFromStorage() {
    const campusId = localStorage.getItem('hospital-campus')
    if (campusId && campusList.value.length > 0) {
      const campus = campusList.value.find(c => c.id === campusId)
      if (campus) {
        currentCampus.value = campus
      }
    }
  }

  return {
    // State
    currentCampus,
    campusList,
    deviceTypes,
    selectedDeviceTypes,
    dateRange,
    energyTypes,
    selectedEnergyType,

    // Getters
    campusOptions,
    deviceTypeOptions,

    // Actions
    setCurrentCampus,
    setCampusList,
    setDeviceTypes,
    toggleDeviceType,
    setSelectedDeviceTypes,
    clearSelectedDeviceTypes,
    setDateRange,
    setSelectedEnergyType,
    initFromStorage,
  }
}, {
  persist: {
    key: 'zhurong-hospital',
    paths: ['currentCampus', 'selectedDeviceTypes', 'dateRange', 'selectedEnergyType'],
  },
})