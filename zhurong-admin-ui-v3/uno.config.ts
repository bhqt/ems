import { defineConfig, presetAttributify, presetIcons, presetUno, presetWebFonts, transformerDirectives, transformerVariantGroup } from 'unocss'
import { presetAntd } from './src/assets/styles/uno-preset-antd'

export default defineConfig({
  shortcuts: [
    // 布局
    ['flex-center', 'flex items-center justify-center'],
    ['flex-col-center', 'flex flex-col items-center justify-center'],
    ['flex-between', 'flex items-center justify-between'],
    ['flex-wrap', 'flex flex-wrap'],
    ['grid-center', 'grid place-items-center'],

    // 卡片
    ['card', 'bg-white dark:bg-gray-900 rounded-lg shadow-sm border border-gray-200 dark:border-gray-700'],
    ['card-hover', 'card transition-shadow hover:shadow-md'],

    // 表单
    ['form-item', 'flex items-center gap-2'],
    ['form-label', 'text-gray-700 dark:text-gray-300 font-medium'],
    ['form-input', 'w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all'],

    // 按钮
    ['btn', 'inline-flex items-center justify-center px-4 py-2 font-medium rounded-lg transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed'],
    ['btn-primary', 'btn bg-primary-600 text-white hover:bg-primary-700 focus:ring-primary-500'],
    ['btn-secondary', 'btn bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600 focus:ring-gray-500'],
    ['btn-success', 'btn bg-green-600 text-white hover:bg-green-700 focus:ring-green-500'],
    ['btn-warning', 'btn bg-yellow-600 text-white hover:bg-yellow-700 focus:ring-yellow-500'],
    ['btn-danger', 'btn bg-red-600 text-white hover:bg-red-700 focus:ring-red-500'],
    ['btn-ghost', 'btn bg-transparent hover:bg-gray-100 dark:hover:bg-gray-800 focus:ring-gray-500'],
    ['btn-link', 'btn bg-transparent text-primary-600 hover:text-primary-700 hover:bg-transparent focus:ring-primary-500'],

    // 表格
    ['table-container', 'overflow-x-auto rounded-lg border border-gray-200 dark:border-gray-700'],
    ['table-header', 'bg-gray-50 dark:bg-gray-800'],
    ['table-row', 'border-b border-gray-200 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors'],

    // 标签
    ['tag', 'inline-flex items-center px-2 py-1 text-xs font-medium rounded-full'],
    ['tag-primary', 'tag bg-primary-100 text-primary-700 dark:bg-primary-900/30 dark:text-primary-300'],
    ['tag-success', 'tag bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-300'],
    ['tag-warning', 'tag bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-300'],
    ['tag-danger', 'tag bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-300'],
    ['tag-gray', 'tag bg-gray-100 text-gray-700 dark:bg-gray-700 dark:text-gray-300'],

    // 页面容器
    ['page-container', 'p-4 md:p-6 lg:p-8'],
    ['page-header', 'mb-6 flex items-center justify-between'],
    ['page-title', 'text-2xl font-bold text-gray-900 dark:text-gray-100'],
    ['page-subtitle', 'text-gray-500 dark:text-gray-400'],

    // 滚动条
    ['scrollbar-thin', 'scrollbar-thin scrollbar-track-transparent scrollbar-thumb-gray-300 dark:scrollbar-thumb-gray-600'],
  ],
  theme: {
    colors: {
      primary: {
        50: '#f0fdf4',
        100: '#dcfce7',
        200: '#bbf7d0',
        300: '#86efac',
        400: '#4ade80',
        500: '#22c55e',
        600: '#16a34a',
        700: '#15803d',
        800: '#166534',
        900: '#14532d',
      },
      hospital: {
        50: '#f0fdf4',
        100: '#dcfce7',
        200: '#bbf7d0',
        300: '#86efac',
        400: '#4ade80',
        500: '#00B96B',
        600: '#00A05D',
        700: '#00874F',
        800: '#006E41',
        900: '#005533',
      },
      gray: {
        50: '#f9fafb',
        100: '#f3f4f6',
        200: '#e5e7eb',
        300: '#d1d5db',
        400: '#9ca3af',
        500: '#6b7280',
        600: '#4b5563',
        700: '#374151',
        800: '#1f2937',
        900: '#111827',
      },
    },
    fontFamily: {
      sans: ['PingFang SC', 'Microsoft YaHei', 'system-ui', 'sans-serif'],
      mono: ['JetBrains Mono', 'Fira Code', 'monospace'],
    },
    spacing: {
      0: '0px',
      1: '4px',
      2: '8px',
      3: '12px',
      4: '16px',
      5: '20px',
      6: '24px',
      8: '32px',
      10: '40px',
      12: '48px',
      16: '64px',
      20: '80px',
      24: '96px',
    },
    borderRadius: {
      none: '0',
      sm: '4px',
      DEFAULT: '6px',
      md: '8px',
      lg: '12px',
      xl: '16px',
      '2xl': '24px',
      full: '9999px',
    },
    boxShadow: {
      sm: '0 1px 2px 0 rgb(0 0 0 / 0.05)',
      DEFAULT: '0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1)',
      md: '0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1)',
      lg: '0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1)',
      xl: '0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1)',
      '2xl': '0 25px 50px -12px rgb(0 0 0 / 0.25)',
      inner: 'inset 0 2px 4px 0 rgb(0 0 0 / 0.05)',
    },
    animation: {
      'fade-in': 'fadeIn 0.3s ease-out',
      'fade-out': 'fadeOut 0.3s ease-in',
      'slide-up': 'slideUp 0.3s ease-out',
      'slide-down': 'slideDown 0.3s ease-out',
      'slide-left': 'slideLeft 0.3s ease-out',
      'slide-right': 'slideRight 0.3s ease-out',
      'zoom-in': 'zoomIn 0.2s ease-out',
      'zoom-out': 'zoomOut 0.2s ease-in',
      spin: 'spin 1s linear infinite',
      ping: 'ping 1s cubic-bezier(0, 0, 0.2, 1) infinite',
      pulse: 'pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite',
      bounce: 'bounce 1s infinite',
    },
    keyframes: {
      fadeIn: {
        '0%': { opacity: '0' },
        '100%': { opacity: '1' },
      },
      fadeOut: {
        '0%': { opacity: '1' },
        '100%': { opacity: '0' },
      },
      slideUp: {
        '0%': { transform: 'translateY(10px)', opacity: '0' },
        '100%': { transform: 'translateY(0)', opacity: '1' },
      },
      slideDown: {
        '0%': { transform: 'translateY(-10px)', opacity: '0' },
        '100%': { transform: 'translateY(0)', opacity: '1' },
      },
      slideLeft: {
        '0%': { transform: 'translateX(10px)', opacity: '0' },
        '100%': { transform: 'translateX(0)', opacity: '1' },
      },
      slideRight: {
        '0%': { transform: 'translateX(-10px)', opacity: '0' },
        '100%': { transform: 'translateX(0)', opacity: '1' },
      },
      zoomIn: {
        '0%': { transform: 'scale(0.95)', opacity: '0' },
        '100%': { transform: 'scale(1)', opacity: '1' },
      },
      zoomOut: {
        '0%': { transform: 'scale(1)', opacity: '1' },
        '100%': { transform: 'scale(0.95)', opacity: '0' },
      },
    },
    screens: {
      xs: '480px',
      sm: '640px',
      md: '768px',
      lg: '1024px',
      xl: '1280px',
      '2xl': '1536px',
      '3xl': '1920px',
    },
    zIndex: {
      auto: 'auto',
      0: '0',
      10: '10',
      20: '20',
      30: '30',
      40: '40',
      50: '50',
      dropdown: '1000',
      sticky: '1020',
      fixed: '1030',
      modal: '1050',
      popover: '1060',
      tooltip: '1070',
      toast: '1080',
    },
  },
  presets: [
    presetUno(),
    presetAttributify(),
    presetIcons({
      scale: 1.2,
      warn: true,
      collections: {
        ep: () => import('@iconify-json/ep/icons.json').then(i => i.default),
        ri: () => import('@iconify-json/ri/icons.json').then(i => i.default),
        carbon: () => import('@iconify-json/carbon/icons.json').then(i => i.default),
      },
    }),
    presetWebFonts({
      fonts: {
        sans: 'PingFang SC:400,500,600;Microsoft YaHei:400,500,600',
        mono: 'JetBrains Mono:400,500',
      },
      provider: 'none',
    }),
    presetAntd,
  ],
  transformers: [
    transformerDirectives(),
    transformerVariantGroup(),
  ],
  variants: [
    (matcher) => {
      if (!matcher.startsWith('dark:')) return matcher
      return {
        matcher: matcher.slice(5),
        selector: (s) => `.dark ${s}`,
      }
    },
    (matcher) => {
      if (!matcher.startsWith('hospital:')) return matcher
      return {
        matcher: matcher.slice(9),
        selector: (s) => `[data-theme="hospital"] ${s}`,
      }
    },
    (matcher) => {
      if (!matcher.startsWith('rtl:')) return matcher
      return {
        matcher: matcher.slice(4),
        selector: (s) => `[dir="rtl"] ${s}`,
      }
    },
  ],
  safelist: [
    // 动态生成的类名保留
    'i-ep-*', 'i-ri-*', 'i-carbon-*',
    'tag-primary', 'tag-success', 'tag-warning', 'tag-danger', 'tag-gray',
    'btn-primary', 'btn-secondary', 'btn-success', 'btn-warning', 'btn-danger', 'btn-ghost', 'btn-link',
    'animate-fade-in', 'animate-fade-out', 'animate-slide-up', 'animate-slide-down',
    'animate-zoom-in', 'animate-zoom-out', 'animate-spin', 'animate-pulse',
  ],
  preflights: [
    {
      getCSS: () => `
        * {
          box-sizing: border-box;
        }
        html {
          -webkit-font-smoothing: antialiased;
          -moz-osx-font-smoothing: grayscale;
          font-feature-settings: 'cv02', 'cv03', 'cv04', 'cv11';
        }
        body {
          @apply bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-gray-100;
        }
        ::selection {
          @apply bg-primary-200 text-primary-900 dark:bg-primary-800 dark:text-primary-100;
        }
        ::-webkit-scrollbar {
          width: 6px;
          height: 6px;
        }
        ::-webkit-scrollbar-track {
          @apply bg-transparent;
        }
        ::-webkit-scrollbar-thumb {
          @apply bg-gray-300 dark:bg-gray-600 rounded-full;
        }
        ::-webkit-scrollbar-thumb:hover {
          @apply bg-gray-400 dark:bg-gray-500;
        }
        .dark {
          color-scheme: dark;
        }
        [data-theme="hospital"] {
          @apply bg-hospital-50 text-gray-900;
        }
        [data-theme="hospital"] .dark {
          @apply bg-gray-900 text-gray-100;
        }
      `,
    },
  ],
})