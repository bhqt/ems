import { ref, computed, onMounted, onUnmounted } from 'vue'

const breakpoints = {
  xs: 480,
  sm: 640,
  md: 768,
  lg: 1024,
  xl: 1280,
  '2xl': 1536,
  '3xl': 1920,
} as const

type Breakpoint = keyof typeof breakpoints

export function useBreakpoints() {
  const windowWidth = ref(window.innerWidth)
  const windowHeight = ref(window.innerHeight)

  const currentBreakpoint = ref<Breakpoint>('lg')

  function updateBreakpoint() {
    windowWidth.value = window.innerWidth
    windowHeight.value = window.innerHeight

    const width = windowWidth.value
    if (width < breakpoints.xs) {
      currentBreakpoint.value = 'xs'
    } else if (width < breakpoints.sm) {
      currentBreakpoint.value = 'sm'
    } else if (width < breakpoints.md) {
      currentBreakpoint.value = 'md'
    } else if (width < breakpoints.lg) {
      currentBreakpoint.value = 'lg'
    } else if (width < breakpoints.xl) {
      currentBreakpoint.value = 'xl'
    } else if (width < breakpoints['2xl']) {
      currentBreakpoint.value = '2xl'
    } else {
      currentBreakpoint.value = '3xl'
    }
  }

  const isMobile = computed(() => currentBreakpoint.value === 'xs' || currentBreakpoint.value === 'sm')
  const isTablet = computed(() => currentBreakpoint.value === 'md')
  const isDesktop = computed(() => currentBreakpoint.value === 'lg' || currentBreakpoint.value === 'xl')
  const isWide = computed(() => currentBreakpoint.value === '2xl' || currentBreakpoint.value === '3xl')

  const greaterThan = (bp: Breakpoint) => {
    const bpValue = breakpoints[bp]
    return windowWidth.value >= bpValue
  }

  const lessThan = (bp: Breakpoint) => {
    const bpValue = breakpoints[bp]
    return windowWidth.value < bpValue
  }

  const between = (min: Breakpoint, max: Breakpoint) => {
    return windowWidth.value >= breakpoints[min] && windowWidth.value < breakpoints[max]
  }

  onMounted(() => {
    updateBreakpoint()
    window.addEventListener('resize', updateBreakpoint)
  })

  onUnmounted(() => {
    window.removeEventListener('resize', updateBreakpoint)
  })

  return {
    windowWidth,
    windowHeight,
    currentBreakpoint,
    isMobile,
    isTablet,
    isDesktop,
    isWide,
    greaterThan,
    lessThan,
    between,
    breakpoints,
  }
}