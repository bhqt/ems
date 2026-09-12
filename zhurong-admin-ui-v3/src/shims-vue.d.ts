import 'vue-router'
import '@vueuse/core'

declare module 'vue-router' {
  interface RouteMeta {
    /** 菜单标题 */
    title: string
    /** 图标 */
    icon?: string
    /** 是否缓存 */
    keepAlive?: boolean
    /** 是否固定标签 */
    affix?: boolean
    /** 是否隐藏菜单 */
    hideMenu?: boolean
    /** 是否隐藏面包屑 */
    hideBreadcrumb?: boolean
    /** 是否隐藏标签页 */
    hideTab?: boolean
    /** 是否隐藏子菜单 */
    hideChildrenInMenu?: boolean
    /** 当前激活的菜单 (用于面包屑) */
    currentActiveMenu?: string
    /** 权限标识 */
    permissions?: string[]
    /** 角色标识 */
    roles?: string[]
    /** 外链地址 */
    href?: string
    /** 内链地址 */
    internalLink?: string
    /** 布局类型 */
    layout?: 'default' | 'blank' | 'iframe'
    /** 是否开启页面加载动画 */
    transition?: boolean
    /** 是否忽略权限 */
    ignoreAuth?: boolean
    /** 是否忽略KeepAlive */
    ignoreKeepAlive?: boolean
    /** 排序 */
    orderNo?: number
    /** 备注 */
    remark?: string
  }
}

declare module '@vueuse/core' {
  interface StorageSerializers {
    boolean: StorageSerializer<boolean>
    number: StorageSerializer<number>
    object: StorageSerializer<any>
    any: StorageSerializer<any>
  }
}