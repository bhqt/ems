/// <reference types="vite/client" />
/// <reference types="vite-plugin-pages/client" />
/// <reference types="unocss" />
/// <reference types="@vueuse/core" />
/// <reference types="node" />
/// <reference types="js-cookie" />
/// <reference types="nprogress" />

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module '*.json' {
  const content: any
  export default content
}

declare module '*.scss' {
  const content: { [className: string]: string }
  export default content
}

declare module '*.png' {
  const src: string
  export default src
}

declare module '*.jpg' {
  const src: string
  export default src
}

declare module '*.jpeg' {
  const src: string
  export default src
}

declare module '*.gif' {
  const src: string
  export default src
}

declare module '*.svg' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module '*.webp' {
  const src: string
  export default src
}

declare module '*.woff' {
  const src: string
  export default src
}

declare module '*.woff2' {
  const src: string
  export default src
}

declare module '*.ttf' {
  const src: string
  export default src
}

declare module '*.eot' {
  const src: string
  export default src
}

interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string
  readonly VITE_PUBLIC_PATH: string
  readonly VITE_API_BASE_URL: string
  readonly VITE_WS_BASE_URL: string
  readonly VITE_PORT: string
  readonly VITE_DROP_CONSOLE: string
  readonly VITE_PROXY_DOMAIN: string
  readonly VITE_PROXY_DOMAIN_REAL: string
  readonly VITE_GLOB_APP_SHORT_NAME: string
  readonly VITE_BUILD_COMPRESS: string
  readonly VITE_BUILD_COMPRESS_DELETE_ORIGIN_FILE: string
  readonly VITE_LEGACY: string
  readonly VITE_USE_MOCK: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}