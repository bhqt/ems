import permissionDirective from './permission'
import loadingDirective from './loading'
import copyDirective from './copy'
import watermarkDirective from './watermark'

export function setupDirectives(app: any) {
  app.directive('permission', permissionDirective)
  app.directive('loading', loadingDirective)
  app.directive('copy', copyDirective)
  app.directive('watermark', watermarkDirective)
}

export {
  permissionDirective,
  loadingDirective,
  copyDirective,
  watermarkDirective,
}