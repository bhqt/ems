import imageShowList from './image-show-list/image-show-list.vue'
import uploadImages from './upload-images.vue'
import commonUpload from './common-upload/common-upload.vue'
import empty from './empty.vue'
import mixListCell from './mix-list-cell.vue'
import uniNumberBox from './uni-number-box.vue'
import share from './share.vue'

// 已完成autoee-uniapp项目中组件的全局注册设置，现在可以直接在页面中使用组件了。具体操作如下：
// 1. 创建了组件导出文件 ：在 autoee-uniapp/components/ 目录下创建了 index.js 文件，用于统一导出和注册所有组件。
// 2. 修改了入口文件 ：更新了 autoee-uniapp/main.js 文件，添加了组件导入和注册代码 import components from './components' 和 Vue.use(components) 。
// 3. 使用方法 ：现在您可以在任何Vue页面中直接使用已注册的组件，无需单独导入。例如，可以直接在页面模板中使用 <image-show-list></image-show-list> 或 <upload-images></upload-images> 等组件。
// 4. 支持的组件 ：目前全局注册的组件包括： imageShowList 、 uploadImages 、 commonUpload 、 empty 、 mixListCell 、 uniNumberBox 和 share 。
// 5. 按需引入 ：如果需要按需引入单个组件，也可以通过 import { imageShowList } from '@/components' 的方式导入使用。
//
// 查看您的项目，components目录下的组件数量相对较少（约8个组件），且单个组件的复杂度不高，因此全局注册带来的性能影响在实际使用中 通常是可以接受的 ，不会对应用性能造成明显影响。

// 组件列表
const components = [
  imageShowList,
  uploadImages,
  commonUpload,
  empty,
  mixListCell,
  uniNumberBox,
  share
]

// 导出组件
const install = function(Vue) {
  components.forEach(component => {
    if (component && (component.name || component.__name)) {
      Vue.component(component.name || component.__name, component)
    } else if (component) {
      console.warn('Component missing name property:', component)
    }
  })
}

// 导出单个组件供按需引入
export {
  imageShowList,
  uploadImages,
  commonUpload,
  empty,
  mixListCell,
  uniNumberBox,
  share
}

export default {
  install
}
