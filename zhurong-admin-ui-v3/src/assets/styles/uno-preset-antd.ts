import type { Preset } from 'unocss'

export const presetAntd: Preset = {
  name: 'unocss-preset-antd',
  rules: [
    // AntD 组件类名映射
    [/^ant-btn$/, { 'display': 'inline-flex', 'align-items': 'center', 'justify-content': 'center', 'font-weight': '500', 'white-space': 'nowrap', 'cursor': 'pointer', 'transition': 'all var(--zhurong-transition-base) var(--zhurong-ease-out)', 'border': '1px solid transparent', 'border-radius': 'var(--zhurong-border-radius)', 'height': 'var(--zhurong-control-height)', 'padding': '0 var(--zhurong-control-padding-horizontal)', 'font-size': 'var(--zhurong-font-size)', 'line-height': 'var(--zhurong-line-height)' }],
    [/^ant-btn-primary$/, { 'background': 'var(--zhurong-color-primary)', 'border-color': 'var(--zhurong-color-primary)', 'color': '#fff' }],
    [/^ant-btn-default$/, { 'background': 'var(--zhurong-color-bg-container)', 'border-color': 'var(--zhurong-color-border)', 'color': 'var(--zhurong-color-text)' }],
    [/^ant-btn-dashed$/, { 'border-style': 'dashed', 'background': 'var(--zhurong-color-bg-container)', 'border-color': 'var(--zhurong-color-border)', 'color': 'var(--zhurong-color-text)' }],
    [/^ant-btn-text$/, { 'background': 'transparent', 'border-color': 'transparent', 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-btn-link$/, { 'background': 'transparent', 'border-color': 'transparent', 'color': 'var(--zhurong-color-primary)', 'padding': '0', 'height': 'auto' }],
    [/^ant-btn-danger$/, { 'background': 'var(--zhurong-color-error)', 'border-color': 'var(--zhurong-color-error)', 'color': '#fff' }],
    [/^ant-btn-ghost$/, { 'background': 'transparent', 'border-color': 'transparent', 'color': 'var(--zhurong-color-text)', 'box-shadow': 'none' }],

    // 尺寸
    [/^ant-btn-sm$/, { 'height': 'var(--zhurong-control-height-sm)', 'padding': '0 var(--zhurong-control-padding-horizontal-sm)', 'font-size': 'var(--zhurong-font-size-sm)' }],
    [/^ant-btn-lg$/, { 'height': 'var(--zhurong-control-height-lg)', 'padding': '0 var(--zhurong-control-padding-horizontal-lg)', 'font-size': 'var(--zhurong-font-size-lg)' }],

    // 状态
    [/^ant-btn-loading$/, { 'position': 'relative', 'color': 'transparent', 'pointer-events': 'none' }],
    [/^ant-btn-disabled$/, { 'opacity': '0.5', 'cursor': 'not-allowed' }],

    // Input
    [/^ant-input$/, { 'width': '100%', 'height': 'var(--zhurong-control-height)', 'padding': '0 var(--zhurong-control-padding-horizontal)', 'font-size': 'var(--zhurong-font-size)', 'line-height': 'var(--zhurong-line-height)', 'color': 'var(--zhurong-color-text)', 'background': 'var(--zhurong-color-bg-container)', 'border': '1px solid var(--zhurong-color-border)', 'border-radius': 'var(--zhurong-border-radius)', 'transition': 'all var(--zhurong-transition-base) var(--zhurong-ease-out)' }],
    [/^ant-input-sm$/, { 'height': 'var(--zhurong-control-height-sm)', 'padding': '0 var(--zhurong-control-padding-horizontal-sm)', 'font-size': 'var(--zhurong-font-size-sm)' }],
    [/^ant-input-lg$/, { 'height': 'var(--zhurong-control-height-lg)', 'padding': '0 var(--zhurong-control-padding-horizontal-lg)', 'font-size': 'var(--zhurong-font-size-lg)' }],
    [/^ant-input-disabled$/, { 'background': 'var(--zhurong-color-bg-active)', 'color': 'var(--zhurong-color-text-tertiary)', 'cursor': 'not-allowed' }],
    [/^ant-input-focused$/, { 'border-color': 'var(--zhurong-color-primary)', 'box-shadow': '0 0 0 2px var(--zhurong-color-primary-bg)', 'outline': 'none' }],

    // Select
    [/^ant-select-selector$/, { 'height': 'var(--zhurong-control-height)', 'border-radius': 'var(--zhurong-border-radius)', 'border': '1px solid var(--zhurong-color-border)', 'background': 'var(--zhurong-color-bg-container)', 'transition': 'all var(--zhurong-transition-base) var(--zhurong-ease-out)' }],
    [/^ant-select-focused$/, { 'border-color': 'var(--zhurong-color-primary)', 'box-shadow': '0 0 0 2px var(--zhurong-color-primary-bg)' }],
    [/^ant-select-disabled$/, { 'background': 'var(--zhurong-color-bg-active)', 'cursor': 'not-allowed' }],

    // Table
    [/^ant-table$/, { 'font-size': 'var(--zhurong-font-size)', 'color': 'var(--zhurong-color-text)', 'background': 'var(--zhurong-color-bg-container)' }],
    [/^ant-table-thead$/, { 'background': 'var(--zhurong-color-bg-container)' }],
    [/^ant-table-thead > tr > th$/, { 'padding': 'var(--zhurong-spacing-sm) var(--zhurong-spacing-md)', 'font-weight': '600', 'color': 'var(--zhurong-color-text-heading)', 'border-bottom': '1px solid var(--zhurong-color-border-secondary)', 'background': 'var(--zhurong-color-bg-container)', 'transition': 'background var(--zhurong-transition-fast)' }],
    [/^ant-table-tbody > tr > td$/, { 'padding': 'var(--zhurong-spacing-sm) var(--zhurong-spacing-md)', 'border-bottom': '1px solid var(--zhurong-color-border-secondary)', 'transition': 'background var(--zhurong-transition-fast)' }],
    [/^ant-table-tbody > tr:hover > td$/, { 'background': 'var(--zhurong-color-bg-hover)' }],
    [/^ant-table-row-selected$/, { 'background': 'var(--zhurong-color-primary-bg) !important' }],

    // Card
    [/^ant-card$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'border': '1px solid var(--zhurong-color-border)', 'background': 'var(--zhurong-color-bg-container)', 'box-shadow': 'var(--zhurong-box-shadow)' }],
    [/^ant-card-head$/, { 'border-bottom': '1px solid var(--zhurong-color-border-secondary)', 'padding': 'var(--zhurong-spacing-md) var(--zhurong-spacing-lg)', 'border-radius': 'var(--zhurong-border-radius-lg) var(--zhurong-border-radius-lg) 0 0' }],
    [/^ant-card-body$/, { 'padding': 'var(--zhurong-spacing-lg)' }],

    // Modal
    [/^ant-modal-content$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'background': 'var(--zhurong-color-bg-container)' }],
    [/^ant-modal-header$/, { 'border-bottom': '1px solid var(--zhurong-color-border-secondary)', 'border-radius': 'var(--zhurong-border-radius-lg) var(--zhurong-border-radius-lg) 0 0', 'padding': 'var(--zhurong-spacing-md) var(--zhurong-spacing-lg)' }],
    [/^ant-modal-body$/, { 'padding': 'var(--zhurong-spacing-lg)' }],
    [/^ant-modal-footer$/, { 'border-top': '1px solid var(--zhurong-color-border-secondary)', 'border-radius': '0 0 var(--zhurong-border-radius-lg) var(--zhurong-border-radius-lg)', 'padding': 'var(--zhurong-spacing-md) var(--zhurong-spacing-lg)' }],

    // Drawer
    [/^ant-drawer-content$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'background': 'var(--zhurong-color-bg-container)' }],
    [/^ant-drawer-header$/, { 'border-bottom': '1px solid var(--zhurong-color-border-secondary)', 'padding': 'var(--zhurong-spacing-md) var(--zhurong-spacing-lg)' }],
    [/^ant-drawer-body$/, { 'padding': 'var(--zhurong-spacing-lg)' }],

    // Breadcrumb
    [/^ant-breadcrumb$/, { 'font-size': 'var(--zhurong-font-size)' }],
    [/^ant-breadcrumb-link$/, { 'color': 'var(--zhurong-color-text-secondary)', 'transition': 'color var(--zhurong-transition-fast)' }],
    [/^ant-breadcrumb-link:hover$/, { 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-breadcrumb-separator$/, { 'color': 'var(--zhurong-color-text-tertiary)', 'margin': '0 var(--zhurong-spacing-xs)' }],
    [/^ant-breadcrumb-last > span$/, { 'color': 'var(--zhurong-color-text)', 'font-weight': '500' }],

    // Tabs
    [/^ant-tabs-nav$/, { 'margin-bottom': 'calc(-1px * var(--zhurong-border-radius))' }],
    [/^ant-tabs-tab$/, { 'padding': 'var(--zhurong-spacing-sm) var(--zhurong-spacing-md)', 'color': 'var(--zhurong-color-text-secondary)', 'font-weight': '500', 'transition': 'color var(--zhurong-transition-fast)' }],
    [/^ant-tabs-tab:hover$/, { 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-tabs-tab-active$/, { 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-tabs-ink-bar$/, { 'height': '2px', 'background': 'var(--zhurong-color-primary)', 'border-radius': '2px 2px 0 0' }],

    // Menu
    [/^ant-menu-item$/, { 'border-radius': 'var(--zhurong-border-radius)', 'margin': 'var(--zhurong-spacing-xs) var(--zhurong-spacing-sm)', 'transition': 'all var(--zhurong-transition-fast)' }],
    [/^ant-menu-item-selected$/, { 'background': 'var(--zhurong-color-primary-bg)', 'color': 'var(--zhurong-color-primary)', '&::after': { 'background': 'var(--zhurong-color-primary)' } }],
    [/^ant-menu-item:hover$/, { 'background': 'var(--zhurong-color-bg-hover)' }],
    [/^ant-menu-submenu-title$/, { 'border-radius': 'var(--zhurong-border-radius)', 'margin': 'var(--zhurong-spacing-xs) var(--zhurong-spacing-sm)' }],

    // Pagination
    [/^ant-pagination-item$/, { 'min-width': '32px', 'height': '32px', 'border-radius': 'var(--zhurong-border-radius)', 'margin': '0 var(--zhurong-spacing-xs)', 'border': '1px solid var(--zhurong-color-border)', 'transition': 'all var(--zhurong-transition-fast)' }],
    [/^ant-pagination-item-active$/, { 'background': 'var(--zhurong-color-primary)', 'border-color': 'var(--zhurong-color-primary)', 'color': '#fff' }],
    [/^ant-pagination-item:hover$/, { 'border-color': 'var(--zhurong-color-primary)' }],
    [/^ant-pagination-jump-prev$/, { 'border-color': 'var(--zhurong-color-border)' }],
    [/^ant-pagination-jump-next$/, { 'border-color': 'var(--zhurong-color-border)' }],
    [/^ant-pagination-options$/, { 'border-color': 'var(--zhurong-color-border)' }],

    // Tag
    [/^ant-tag$/, { 'display': 'inline-flex', 'align-items': 'center', 'height': '20px', 'padding': '0 var(--zhurong-spacing-xs)', 'font-size': 'var(--zhurong-font-size-sm)', 'line-height': '1', 'font-weight': '500', 'border-radius': 'var(--zhurong-border-radius-sm)', 'background': 'var(--zhurong-color-bg-container)', 'border': '1px solid var(--zhurong-color-border)', 'color': 'var(--zhurong-color-text)', 'white-space': 'nowrap' }],
    [/^ant-tag-primary$/, { 'background': 'var(--zhurong-color-primary-bg)', 'border-color': 'var(--zhurong-color-primary-border)', 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-tag-success$/, { 'background': 'var(--zhurong-color-success-bg)', 'border-color': 'var(--zhurong-color-success-border)', 'color': 'var(--zhurong-color-success)' }],
    [/^ant-tag-warning$/, { 'background': 'var(--zhurong-color-warning-bg)', 'border-color': 'var(--zhurong-color-warning-border)', 'color': 'var(--zhurong-color-warning)' }],
    [/^ant-tag-error$/, { 'background': 'var(--zhurong-color-error-bg)', 'border-color': 'var(--zhurong-color-error-border)', 'color': 'var(--zhurong-color-error)' }],
    [/^ant-tag-processing$/, { 'background': 'var(--zhurong-color-info-bg)', 'border-color': 'var(--zhurong-color-info-border)', 'color': 'var(--zhurong-color-info)' }],

    // Steps
    [/^ant-steps-item-icon$/, { 'width': '32px', 'height': '32px', 'border-radius': '50%', 'border': '2px solid var(--zhurong-color-border)', 'background': 'var(--zhurong-color-bg-container)', 'transition': 'all var(--zhurong-transition-base)' }],
    [/^ant-steps-item-finish .ant-steps-item-icon$/, { 'border-color': 'var(--zhurong-color-primary)', 'background': 'var(--zhurong-color-primary)' }],
    [/^ant-steps-item-process .ant-steps-item-icon$/, { 'border-color': 'var(--zhurong-color-primary)' }],
    [/^ant-steps-item-title$/, { 'color': 'var(--zhurong-color-text)', 'font-weight': '500' }],
    [/^ant-steps-item-title::after$/, { 'background': 'var(--zhurong-color-border)' }],
    [/^ant-steps-item-finish .ant-steps-item-title::after$/, { 'background': 'var(--zhurong-color-primary)' }],
    [/^ant-steps-item-process .ant-steps-item-title::after$/, { 'background': 'var(--zhurong-color-primary)' }],

    // Spin
    [/^ant-spin-dot$/, { 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-spin-text$/, { 'color': 'var(--zhurong-color-text-secondary)', 'margin-top': 'var(--zhurong-spacing-sm)' }],

    // Message / Notification
    [/^ant-message-notice$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'box-shadow': 'var(--zhurong-box-shadow-lg)', 'background': 'var(--zhurong-color-bg-container)' }],
    [/^ant-notification-notice$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'box-shadow': 'var(--zhurong-box-shadow-lg)', 'background': 'var(--zhurong-color-bg-container)', 'border': '1px solid var(--zhurong-color-border)' }],

    // Popover / Tooltip / Dropdown
    [/^ant-popover$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'box-shadow': 'var(--zhurong-box-shadow-lg)', 'background': 'var(--zhurong-color-bg-container)', 'border': '1px solid var(--zhurong-color-border)' }],
    [/^ant-tooltip-inner$/, { 'border-radius': 'var(--zhurong-border-radius)', 'padding': 'var(--zhurong-spacing-xs) var(--zhurong-spacing-sm)', 'background': 'rgba(0, 0, 0, 0.75)', 'color': '#fff', 'font-size': 'var(--zhurong-font-size-sm)' }],
    [/^ant-dropdown-menu$/, { 'border-radius': 'var(--zhurong-border-radius-lg)', 'box-shadow': 'var(--zhurong-box-shadow-lg)', 'background': 'var(--zhurong-color-bg-container)', 'border': '1px solid var(--zhurong-color-border)', 'padding': 'var(--zhurong-spacing-xs) 0' }],
    [/^ant-dropdown-menu-item$/, { 'padding': 'var(--zhurong-spacing-xs) var(--zhurong-spacing-md)', 'color': 'var(--zhurong-color-text)', 'transition': 'all var(--zhurong-transition-fast)' }],
    [/^ant-dropdown-menu-item:hover$/, { 'background': 'var(--zhurong-color-bg-hover)', 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-dropdown-menu-item-selected$/, { 'background': 'var(--zhurong-color-primary-bg)', 'color': 'var(--zhurong-color-primary)' }],
    [/^ant-dropdown-menu-divider$/, { 'margin': 'var(--zhurong-spacing-xs) 0', 'border-bottom': '1px solid var(--zhurong-color-border-secondary)' }],

    // Badge
    [/^ant-badge$/, { 'display': 'inline-flex', 'vertical-align': 'middle' }],
    [/^ant-badge-status-dot$/, { 'width': '8px', 'height': '8px', 'border-radius': '50%' }],
    [/^ant-badge-status-success$/, { 'background': 'var(--zhurong-color-success)' }],
    [/^ant-badge-status-error$/, { 'background': 'var(--zhurong-color-error)' }],
    [/^ant-badge-status-warning$/, { 'background': 'var(--zhurong-color-warning)' }],
    [/^ant-badge-status-processing$/, { 'background': 'var(--zhurong-color-info)', 'animation': 'pulse 1.5s ease-in-out infinite' }],

    // Progress
    [/^ant-progress-outer$/, { 'border-radius': 'var(--zhurong-border-radius-full)' }],
    [/^ant-progress-bg$/, { 'border-radius': 'var(--zhurong-border-radius-full)', 'background': 'var(--zhurong-color-primary)' }],
    [/^ant-progress-text$/, { 'color': 'var(--zhurong-color-text)', 'font-size': 'var(--zhurong-font-size-sm)' }],

    // Slider
    [/^ant-slider-rail$/, { 'height': '6px', 'border-radius': 'var(--zhurong-border-radius-full)', 'background': 'var(--zhurong-color-border-secondary)' }],
    [/^ant-slider-track$/, { 'height': '6px', 'border-radius': 'var(--zhurong-border-radius-full)', 'background': 'var(--zhurong-color-primary)' }],
    [/^ant-slider-handle$/, { 'width': '16px', 'height': '16px', 'border-radius': '50%', 'border': '2px solid var(--zhurong-color-primary)', 'background': 'var(--zhurong-color-bg-container)', 'box-shadow': 'var(--zhurong-box-shadow-sm)' }],
    [/^ant-slider-handle:hover$/, { 'box-shadow': '0 0 0 4px var(--zhurong-color-primary-bg)' }],

    // Rate
    [/^ant-rate-star$/, { 'color': 'var(--zhurong-color-border)', 'transition': 'all var(--zhurong-transition-fast)' }],
    [/^ant-rate-star-filled$/, { 'color': 'var(--zhurong-color-warning)' }],
    [/^ant-rate-star-hover$/, { 'transform': 'scale(1.1)' }],

    // Tree
    [/^ant-tree-node-content-wrapper$/, { 'border-radius': 'var(--zhurong-border-radius)', 'padding': 'var(--zhurong-spacing-xs) var(--zhurong-spacing-sm)', 'transition': 'all var(--zhurong-transition-fast)' }],
    [/^ant-tree-node-content-wrapper:hover$/, { 'background': 'var(--zhurong-color-bg-hover)' }],
    [/^ant-tree-node-selected$/, { 'background': 'var(--zhurong-color-primary-bg)', 'color': 'var(--zhurong-color-primary)' }],

    // Upload
    [/^ant-upload$/, { 'border-radius': 'var(--zhurong-border-radius)', 'border': '1px dashed var(--zhurong-color-border)', 'background': 'var(--zhurong-color-bg-container)', 'transition': 'all var(--zhurong-transition-base)' }],
    [/^ant-upload:hover$/, { 'border-color': 'var(--zhurong-color-primary)' }],
    [/^ant-upload-drag-hover$/, { 'border-color': 'var(--zhurong-color-primary)', 'background': 'var(--zhurong-color-primary-bg)' }],
  ],
  shortcuts: [
    // AntD 组件常用组合
    ['ant-form-item', 'mb-4'],
    ['ant-form-item-label', 'block mb-1 text-sm font-medium text-gray-700 dark:text-gray-300'],
    ['ant-form-item-required', 'relative before:content-["*"] before:text-red-500 before:ml-0.5'],
    ['ant-form-item-error', 'mt-1 text-sm text-red-500'],
    ['ant-form-item-explain', 'mt-1 text-sm text-gray-500 dark:text-gray-400'],
  ],
}