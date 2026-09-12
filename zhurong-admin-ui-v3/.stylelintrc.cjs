module.exports = {
  root: true,
  plugins: ['stylelint-scss'],
  customSyntax: 'postcss-scss',
  extends: [
    'stylelint-config-standard-scss',
    'stylelint-config-recess-order',
  ],
  rules: {
    'scss/at-rule-no-unknown': [
      true,
      {
        ignoreAtRules: ['use', 'forward', 'mixin', 'include', 'function', 'return', 'if', 'else', 'for', 'each', 'while'],
      },
    ],
    'scss/dollar-variable-pattern': '^[a-z][a-z0-9]*(-[a-z0-9]+)*$',
    'scss/percent-placeholder-pattern': '^[a-z][a-z0-9]*(-[a-z0-9]+)*$',
    'selector-class-pattern': '^[a-z][a-z0-9]*(-[a-z0-9]+)*(__[a-z0-9]+(-[a-z0-9]+)*)?(--[a-z0-9]+(-[a-z0-9]+)*)?$',
    'custom-property-pattern': '^[a-z][a-z0-9]*(-[a-z0-9]+)*$',
    'color-hex-case': 'lower',
    'color-hex-length': 'short',
    'unit-case': 'lower',
    'value-keyword-case': 'lower',
    'selector-pseudo-class-no-unknown': [
      true,
      {
        ignorePseudoClasses: ['global', 'local', 'deep'],
      },
    ],
    'property-no-unknown': [
      true,
      {
        ignoreProperties: ['box-sizing', 'tap-highlight-color', 'user-select', 'text-size-adjust'],
      },
    ],
    'at-rule-no-unknown': [
      true,
      {
        ignoreAtRules: ['tailwind', 'apply', 'layer', 'variants', 'responsive', 'screen', 'import'],
      },
    ],
  },
  ignoreFiles: ['node_modules/**', 'dist/**', '.git/**', 'public/**'],
}