// 浅色主题
export const lightTheme = {
  // 色板
  colorPrimary: '#1677ff',
  colorPrimaryHover: '#4096ff',
  colorPrimaryActive: '#0958d9',
  colorPrimaryBg: '#e6f7ff',
  colorPrimaryBorder: '#91d5ff',

  colorSuccess: '#52c41a',
  colorSuccessHover: '#73d13d',
  colorSuccessActive: '#389e0d',
  colorSuccessBg: '#f6ffed',
  colorSuccessBorder: '#b7eb8f',

  colorWarning: '#faad14',
  colorWarningHover: '#ffc53d',
  colorWarningActive: '#d48806',
  colorWarningBg: '#fffbe6',
  colorWarningBorder: '#ffe58f',

  colorError: '#ff4d4f',
  colorErrorHover: '#ff7875',
  colorErrorActive: '#d9363e',
  colorErrorBg: '#fff1f0',
  colorErrorBorder: '#ffa39e',

  colorInfo: '#1677ff',
  colorInfoHover: '#4096ff',
  colorInfoActive: '#0958d9',
  colorInfoBg: '#e6f7ff',
  colorInfoBorder: '#91d5ff',

  // 中性色
  colorText: 'rgba(0, 0, 0, 0.85)',
  colorTextHeading: 'rgba(0, 0, 0, 0.85)',
  colorTextSecondary: 'rgba(0, 0, 0, 0.45)',
  colorTextTertiary: 'rgba(0, 0, 0, 0.25)',
  colorTextQuaternary: 'rgba(0, 0, 0, 0.15)',
  colorTextInverse: '#fff',

  colorBgLayout: '#f0f2f5',
  colorBgContainer: '#fff',
  colorBgElevated: '#fff',
  colorBgHover: '#fafafa',
  colorBgActive: '#f5f5f5',
  colorBgMask: 'rgba(0, 0, 0, 0.45)',

  colorBorder: '#d9d9d9',
  colorBorderHover: '#bfbfbf',
  colorBorderActive: '#8c8c8c',
  colorBorderSecondary: '#f0f0f0',

  // 字体
  fontFamily: "'PingFang SC', 'Microsoft YaHei', system-ui, sans-serif",
  fontFamilyMono: "'JetBrains Mono', 'Fira Code', monospace",

  fontSize: 14,
  fontSizeSM: 12,
  fontSizeLG: 16,
  fontSizeXL: 20,
  fontSize2XL: 24,
  fontSize3XL: 30,
  fontSize4XL: 36,

  lineHeight: 1.5715,
  lineHeightSM: 1.5,
  lineHeightLG: 1.5,

  fontWeightNormal: 400,
  fontWeightMedium: 500,
  fontWeightSemibold: 600,
  fontWeightBold: 700,

  // 间距
  paddingXXS: 4,
  paddingXS: 8,
  paddingSM: 12,
  padding: 16,
  paddingMD: 20,
  paddingLG: 24,
  paddingXL: 32,
  padding2XL: 48,
  padding3XL: 64,

  // 圆角
  borderRadius: 6,
  borderRadiusSM: 4,
  borderRadiusLG: 8,
  borderRadiusXL: 12,
  borderRadius2XL: 16,
  borderRadiusOuter: 8,

  // 控件
  controlHeight: 40,
  controlHeightSM: 32,
  controlHeightLG: 48,
  controlPaddingHorizontal: 12,
  controlPaddingHorizontalSM: 8,
  controlPaddingHorizontalLG: 16,
  controlOutlineWidth: 2,
  controlOutline: '0 0 0 2px var(--zhurong-color-primary-bg)',

  // 阴影
  boxShadow: '0 1px 2px -2px rgba(0, 0, 0, 0.16), 0 3px 6px 0 rgba(0, 0, 0, 0.12), 0 5px 12px 4px rgba(0, 0, 0, 0.09)',
  boxShadowSM: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
  boxShadowMD: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -2px rgba(0, 0, 0, 0.1)',
  boxShadowLG: '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -4px rgba(0, 0, 0, 0.1)',
  boxShadowXL: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1)',
  boxShadowSecondary: '0 6px 16px 0 rgba(0, 0, 0, 0.08), 0 3px 6px -4px rgba(0, 0, 0, 0.12), 0 9px 28px 8px rgba(0, 0, 0, 0.05)',

  // 过渡
  motionDurationFast: '0.1s',
  motionDurationMid: '0.2s',
  motionDurationSlow: '0.3s',
  motionEaseInOut: 'cubic-bezier(0.645, 0.045, 0.355, 1)',
  motionEaseOut: 'cubic-bezier(0.25, 0.46, 0.45, 0.94)',
  motionEaseIn: 'cubic-bezier(0.55, 0.055, 0.675, 0.19)',

  // 层级
  zIndexPopup: 1000,
  zIndexBase: 100,
} as const


// 深色主题
export const darkTheme = {
  ...lightTheme,

  colorText: 'rgba(255, 255, 255, 0.85)',
  colorTextHeading: 'rgba(255, 255, 255, 0.85)',
  colorTextSecondary: 'rgba(255, 255, 255, 0.65)',
  colorTextTertiary: 'rgba(255, 255, 255, 0.45)',
  colorTextQuaternary: 'rgba(255, 255, 255, 0.25)',
  colorTextInverse: 'rgba(0, 0, 0, 0.85)',

  colorBgLayout: '#141414',
  colorBgContainer: '#1f1f1f',
  colorBgElevated: '#262626',
  colorBgHover: '#262626',
  colorBgActive: '#333',
  colorBgMask: 'rgba(0, 0, 0, 0.65)',

  colorBorder: '#434343',
  colorBorderHover: '#595959',
  colorBorderActive: '#737373',
  colorBorderSecondary: '#303030',

  colorPrimaryBg: '#111b2f',
  colorPrimaryBorder: '#1a3b6e',
  colorSuccessBg: '#13230a',
  colorSuccessBorder: '#2a4a0d',
  colorWarningBg: '#2b1e00',
  colorWarningBorder: '#5c3c00',
  colorErrorBg: '#2b0e0e',
  colorErrorBorder: '#5c0d0d',
  colorInfoBg: '#111b2f',
  colorInfoBorder: '#1a3b6e',

  boxShadow: '0 1px 2px -2px rgba(0, 0, 0, 0.4), 0 3px 6px 0 rgba(0, 0, 0, 0.3), 0 5px 12px 4px rgba(0, 0, 0, 0.2)',
  boxShadowSM: '0 1px 2px 0 rgba(0, 0, 0, 0.2)',
  boxShadowMD: '0 4px 6px -1px rgba(0, 0, 0, 0.3), 0 2px 4px -2px rgba(0, 0, 0, 0.2)',
  boxShadowLG: '0 10px 15px -3px rgba(0, 0, 0, 0.3), 0 4px 6px -4px rgba(0, 0, 0, 0.2)',
  boxShadowXL: '0 20px 25px -5px rgba(0, 0, 0, 0.3), 0 8px 10px -6px rgba(0, 0, 0, 0.2)',
} as const


// 医院主题
export const hospitalTheme = {
  ...lightTheme,

  colorPrimary: '#00B96B',
  colorPrimaryHover: '#00D47A',
  colorPrimaryActive: '#00A05D',
  colorPrimaryBg: '#E8F7F0',
  colorPrimaryBorder: '#A5E8C8',

  colorSuccess: '#00B96B',
  colorSuccessHover: '#00D47A',
  colorSuccessActive: '#00A05D',
  colorSuccessBg: '#E8F7F0',
  colorSuccessBorder: '#A5E8C8',

  colorInfo: '#1890FF',
  colorInfoHover: '#40A9FF',
  colorInfoActive: '#096DD9',
  colorInfoBg: '#E6F4FF',
  colorInfoBorder: '#91D5FF',

  colorBgLayout: '#F0F5FA',
  colorBgContainer: '#FFFFFF',
  colorBorder: '#D9E2EF',
  colorBorderHover: '#C0CCDA',
  colorText: '#1F1F1F',
  colorTextSecondary: '#595959',

  fontFamily: "'PingFang SC', 'Microsoft YaHei', system-ui, sans-serif",

  // 组件级 Token 覆盖
  components: {
    Button: {
      borderRadius: 6,
      controlHeight: 40,
      primaryColor: '#fff',
      primaryHoverColor: '#fff',
      primaryActiveColor: '#fff',
    },
    Input: {
      borderRadius: 6,
      controlHeight: 40,
    },
    Select: {
      borderRadius: 6,
      controlHeight: 40,
    },
    Table: {
      headerBg: '#F5FAF8',
      rowHoverBg: '#E8F7F0',
      borderColor: '#D9E2EF',
    },
    Card: {
      borderRadius: 8,
      boxShadow: '0 1px 4px rgba(0, 0, 0, 0.06)',
    },
    Modal: {
      borderRadius: 8,
      headerBg: '#F5FAF8',
    },
    Drawer: {
      borderRadius: 8,
    },
    Tabs: {
      inkBarColor: '#00B96B',
    },
    Menu: {
      itemBg: 'transparent',
      itemSelectedBg: '#E8F7F0',
      itemSelectedColor: '#00B96B',
    },
    Pagination: {
      itemBg: 'transparent',
      itemActiveBg: '#00B96B',
    },
    Tag: {
      borderRadius: 4,
    },
    Steps: {
      dotBorderWidth: 2,
    },
  },
} as const