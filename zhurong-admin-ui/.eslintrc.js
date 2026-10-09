// ESlint 检查配置
module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint',
    sourceType: 'module'
  },
  env: {
    browser: true,
    node: true,
    es6: true,
  },
  globals: {
    // 百度地图 JSAPI 由 index.html 的 <script> 引入，属全局对象
    BMapGL: 'readonly',
    BMap: 'readonly',
    // webpack 注入
    __webpack_public_path__: 'writable',
  },
  extends: ['plugin:vue/recommended', 'eslint:recommended'],

  // add your custom rules here
  //it is base on https://vuejs.github.io/eslint-config-vue
  //
  // ============================================================================
  // lint 分层说明（2026-10）
  //
  // 背景：.eslintignore 曾因 `*.js` / `*.vue` 递归匹配所有层级，导致 src/ 下
  // 无文件可检，npm run lint 长期空跑。修复后实际检查 501 个文件，
  // 实测 28728 条问题（8603 error + 20125 warning）。
  //
  // 其中约 97% 为纯排版问题（缩进/引号/空格/分号），批量 --fix 会产生横跨
  // 341 个文件的巨型 diff，评审不可读且破坏 git blame，因此统一降级为 warn。
  // 剩下约 3% 是需要人判断的真实代码问题，保留为 error —— 让 lint 保持"红中
  // 有信号"，而不是被格式噪音淹没。
  //
  // error 保留的规则分两类：
  //   1. 正确性：no-undef / no-dupe-keys / no-redeclare / no-eval /
  //      no-implied-eval / no-prototype-builtins / handle-callback-err /
  //      no-sequences / no-empty / no-unused-vars 等
  //   2. Vue 契约：vue/no-mutating-props / vue/no-side-effects-in-computed-properties /
  //      vue/no-v-model-argument / vue/require-valid-default-prop 等
  // ============================================================================
  rules: {
    // 纯排版规则，与现有代码风格冲突且存量巨大（下方注释所列规则同理）。
    // 统一降级为 warn：npm run lint 不再因此失败，但信息仍然可见。
    // 这些规则均为 --fix 可自动修复的机械格式问题，建议在日常改动对应文件时顺手修。
    //
    // 存量违规规模（2026-10 静态扫描 src/ 下 501 个 .js/.vue 所得，仅供参考）：
    //   vue/max-attributes-per-line ~6600   quotes(已降级) ~67000
    //   no-mixed-spaces-and-tabs ~2960      semi(已降级)    ~11400
    //   object-curly-spacing     ~1700      no-multi-spaces  ~1170
    //   vue/name-property-casing    ~96      space-before-function-paren ~71
    "vue/max-attributes-per-line": ["warn", {
      "singleline": 10,
      "multiline": {
        "max": 1,
        "allowFirstLine": false
      }
    }],
    "vue/singleline-html-element-content-newline": "off",
    "vue/multiline-html-element-content-newline":"off",
    "vue/name-property-casing": ["warn", "PascalCase"],
    "vue/no-v-html": "off",
    'accessor-pairs': 2,
    'arrow-spacing': ['warn', {
      'before': true,
      'after': true
    }],
    'block-spacing': [2, 'always'],
    'brace-style': [2, '1tbs', {
      'allowSingleLine': true
    }],
    'camelcase': [0, {
      'properties': 'always'
    }],
    'comma-dangle': ['warn', 'never'],
    'comma-spacing': ['warn', {
      'before': false,
      'after': true
    }],
    'comma-style': [2, 'last'],
    'constructor-super': 2,
    'curly': [2, 'multi-line'],
    'dot-location': [2, 'property'],
    'eol-last': 'warn',
    'eqeqeq': ["error", "always", {"null": "ignore"}],
    'generator-star-spacing': [2, {
      'before': true,
      'after': true
    }],
    'handle-callback-err': [2, '^(err|error)$'],
    'indent': ['warn', 2, {
      'SwitchCase': 1
    }],
    'jsx-quotes': ['warn', 'prefer-single'],
    'key-spacing': ['warn', {
      'beforeColon': false,
      'afterColon': true
    }],
    'keyword-spacing': ['warn', {
      'before': true,
      'after': true
    }],
    'new-cap': [2, {
      'newIsCap': true,
      'capIsNew': false
    }],
    'new-parens': 2,
    'no-array-constructor': 2,
    'no-caller': 2,
    'no-console': 'off',
    'no-class-assign': 2,
    'no-cond-assign': 2,
    'no-const-assign': 2,
    'no-control-regex': 0,
    'no-delete-var': 2,
    'no-dupe-args': 2,
    'no-dupe-class-members': 2,
    'no-dupe-keys': 2,
    'no-duplicate-case': 2,
    'no-empty-character-class': 2,
    'no-empty-pattern': 2,
    'no-eval': 2,
    'no-ex-assign': 2,
    'no-extend-native': 2,
    'no-extra-bind': 2,
    'no-extra-boolean-cast': 2,
    'no-extra-parens': ['warn', 'functions'],
    'no-fallthrough': 2,
    'no-floating-decimal': 2,
    'no-func-assign': 2,
    'no-implied-eval': 2,
    'no-inner-declarations': [2, 'functions'],
    'no-invalid-regexp': 2,
    'no-irregular-whitespace': 2,
    'no-iterator': 2,
    'no-label-var': 2,
    'no-labels': [2, {
      'allowLoop': false,
      'allowSwitch': false
    }],
    'no-lone-blocks': 2,
    'no-mixed-spaces-and-tabs': 2,
    'no-multi-spaces': 'warn',
    'no-multi-str': 2,
    'no-multiple-empty-lines': ['warn', {
      'max': 1
    }],
    'no-native-reassign': 2,
    'no-negated-in-lhs': 2,
    'no-new-object': 2,
    'no-new-require': 2,
    'no-new-symbol': 2,
    'no-new-wrappers': 2,
    'no-obj-calls': 2,
    'no-octal': 2,
    'no-octal-escape': 2,
    'no-path-concat': 2,
    'no-proto': 2,
    'no-redeclare': 2,
    'no-regex-spaces': 2,
    'no-return-assign': [2, 'except-parens'],
    'no-self-assign': 2,
    'no-self-compare': 2,
    'no-sequences': 2,
    'no-shadow-restricted-names': 2,
    'no-spaced-func': 2,
    'no-sparse-arrays': 2,
    'no-this-before-super': 2,
    'no-throw-literal': 2,
    'no-trailing-spaces': 'warn',
    'no-undef': 2,
    'no-undef-init': 2,
    'no-unexpected-multiline': 2,
    'no-unmodified-loop-condition': 2,
    'no-unneeded-ternary': ['warn', {
      'defaultAssignment': false
    }],
    'no-unreachable': 2,
    'no-unsafe-finally': 2,
    'no-unused-vars': [2, {
      'vars': 'all',
      'args': 'none'
    }],
    'no-useless-call': 2,
    'no-useless-computed-key': 2,
    'no-useless-constructor': 2,
    'no-useless-escape': 0,
    'no-whitespace-before-property': 2,
    'no-with': 2,
    'one-var': ['warn', {
      'initialized': 'never'
    }],
    'operator-linebreak': ['warn', 'after', {
      'overrides': {
        '?': 'before',
        ':': 'before'
      }
    }],
    'padded-blocks': ['warn', 'never'],
    // 以下两条纯格式规则与现有代码风格冲突（现存约 8 万处违规：双引号 67k / 分号 11k），
    // 批量修复会产生横跨 346 个文件的巨型 diff，评审不可读且会破坏 git blame，
    // 风险远大于收益。因此降级为 warn：信息保留但不再阻塞 npm run lint。
    // 新代码建议遵循，存量代码在日常修改对应文件时顺手 --fix 即可。
    'quotes': ['warn', 'single', {
      'avoidEscape': true,
      'allowTemplateLiterals': true
    }],
    'semi': ['warn', 'never'],
    'semi-spacing': ['warn', {
      'before': false,
      'after': true
    }],
    'space-before-blocks': ['warn', 'always'],
    'space-before-function-paren': ['warn', 'never'],
    'space-in-parens': ['warn', 'never'],
    'space-infix-ops': 'warn',
    'space-unary-ops': [2, {
      'words': true,
      'nonwords': false
    }],
    'spaced-comment': ['warn', 'always', {
      'markers': ['global', 'globals', 'eslint', 'eslint-disable', '*package', '!', ',']
    }],
    'template-curly-spacing': [2, 'never'],
    'use-isnan': 2,
    'valid-typeof': 2,
    'wrap-iife': [2, 'any'],
    'yield-star-spacing': [2, 'both'],
    'yoda': ['warn', 'never'],
    'prefer-const': 2,
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
    'object-curly-spacing': ['warn', 'always', {
      objectsInObjects: false
    }],
    'array-bracket-spacing': [2, 'never']
  }
}
