(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["components/common-upload/common-upload"],{

/***/ 498:
/*!*******************************************************************************************************!*\
  !*** D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue ***!
  \*******************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common-upload.vue?vue&type=template&id=1e6a27cc& */ 499);
/* harmony import */ var _common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common-upload.vue?vue&type=script&lang=js& */ 501);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__) if(["default"].indexOf(__WEBPACK_IMPORT_KEY__) < 0) (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var _common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./common-upload.vue?vue&type=style&index=0&lang=scss& */ 503);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/runtime/componentNormalizer.js */ 44);

var renderjs





/* normalize component */

var component = Object(_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__["default"])(
  _common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__["default"],
  _common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["render"],
  _common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"],
  false,
  null,
  null,
  null,
  false,
  _common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["components"],
  renderjs
)

component.options.__file = "components/common-upload/common-upload.vue"
/* harmony default export */ __webpack_exports__["default"] = (component.exports);

/***/ }),

/***/ 499:
/*!**************************************************************************************************************************************!*\
  !*** D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue?vue&type=template&id=1e6a27cc& ***!
  \**************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--17-0!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./common-upload.vue?vue&type=template&id=1e6a27cc& */ 500);
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "render", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["render"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["recyclableRender"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "components", function() { return _HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_template_id_1e6a27cc___WEBPACK_IMPORTED_MODULE_0__["components"]; });



/***/ }),

/***/ 500:
/*!**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--17-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue?vue&type=template&id=1e6a27cc& ***!
  \**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "render", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return staticRenderFns; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return recyclableRender; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "components", function() { return components; });
var components
try {
  components = {
    uniFilePicker: function () {
      return Promise.all(/*! import() | uni_modules/uni-file-picker/components/uni-file-picker/uni-file-picker */[__webpack_require__.e("common/vendor"), __webpack_require__.e("uni_modules/uni-file-picker/components/uni-file-picker/uni-file-picker")]).then(__webpack_require__.bind(null, /*! @/uni_modules/uni-file-picker/components/uni-file-picker/uni-file-picker.vue */ 544))
    },
  }
} catch (e) {
  if (
    e.message.indexOf("Cannot find module") !== -1 &&
    e.message.indexOf(".vue") !== -1
  ) {
    console.error(e.message)
    console.error("1. 排查组件名称拼写是否正确")
    console.error(
      "2. 排查组件是否符合 easycom 规范，文档：https://uniapp.dcloud.net.cn/collocation/pages?id=easycom"
    )
    console.error(
      "3. 若组件不符合 easycom 规范，需手动引入，并在 components 中注册该组件"
    )
  } else {
    throw e
  }
}
var render = function () {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
}
var recyclableRender = false
var staticRenderFns = []
render._withStripped = true



/***/ }),

/***/ 501:
/*!********************************************************************************************************************************!*\
  !*** D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue?vue&type=script&lang=js& ***!
  \********************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/babel-loader/lib!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--13-1!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./common-upload.vue?vue&type=script&lang=js& */ 502);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__) if(["default"].indexOf(__WEBPACK_IMPORT_KEY__) < 0) (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 502:
/*!***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--13-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue?vue&type=script&lang=js& ***!
  \***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;
var _regenerator = _interopRequireDefault(__webpack_require__(/*! @babel/runtime/regenerator */ 50));
var _typeof2 = _interopRequireDefault(__webpack_require__(/*! @babel/runtime/helpers/typeof */ 13));
var _asyncToGenerator2 = _interopRequireDefault(__webpack_require__(/*! @babel/runtime/helpers/asyncToGenerator */ 52));
var _commonUpload = __webpack_require__(/*! @/utils/commonUpload */ 110);
var _oss = __webpack_require__(/*! @/api/system/oss */ 453);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
/**
 * 公共上传组件
 * 封装了文件/图片上传的所有逻辑，提供统一的上传接口
 */
var _default = {
  name: 'CommonUpload',
  props: {
    // 绑定的值，用于表单提交
    value: {
      type: String,
      default: ''
    },
    // 文件类型，可选值：image, video, file, all
    fileType: {
      type: String,
      default: 'image'
    },
    // 文件扩展名，如：'png,jpg,pdf'
    // 如果不指定，会根据fileType自动设置不同的默认扩展名
    fileExtname: {
      type: String,
      default: ''
    },
    // 显示模式，可选值：grid, list
    // 如果不指定，会根据fileType自动设置：图片为grid，其他文件为list
    mode: {
      type: String,
      default: ''
    },
    // 是否自动上传
    autoUpload: {
      type: Boolean,
      default: true
    },
    // 最大选择数量
    limit: {
      type: [Number, String],
      default: 9
    },
    // 组件标题
    title: {
      type: String,
      default: ''
    },
    // 是否禁用
    disabled: {
      type: Boolean,
      default: false
    },
    // 是否只读
    readonly: {
      type: Boolean,
      default: false
    },
    // 是否显示删除按钮
    showDelete: {
      type: Boolean,
      default: true
    },
    // 是否禁用预览
    disablePreview: {
      type: Boolean,
      default: false
    },
    // 上传接口地址
    uploadUrl: {
      type: String,
      default: '/system/oss/upload'
    },
    // 上传文件的字段名
    uploadFieldName: {
      type: String,
      default: 'file'
    },
    // 表单分隔符
    separator: {
      type: String,
      default: ','
    },
    // 图片基础URL（用于显示已上传的图片）
    imageBaseUrl: {
      type: String,
      default: Object({"NODE_ENV":"development","VUE_APP_DARK_MODE":"false","VUE_APP_NAME":"AutoEE物联网","VUE_APP_PLATFORM":"mp-weixin","BASE_URL":"/"}).VUE_APP_BASE_API || ''
    }
  },
  data: function data() {
    return {
      fileList: [],
      // 文件列表，用于绑定uni-file-picker
      uploadFilesInfo: [] // 上传文件的信息，包含文件名等
    };
  },

  watch: {
    // 监听uploadFilesInfo变化，更新value值
    uploadFilesInfo: {
      handler: function handler() {
        this.updateValue();
      },
      deep: true
    },
    // 监听value变化，用于外部修改和初始化
    value: {
      handler: function handler(newVal) {
        var _this = this;
        return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee() {
          return _regenerator.default.wrap(function _callee$(_context) {
            while (1) {
              switch (_context.prev = _context.next) {
                case 0:
                  if (!(newVal !== _this.getValueFromFiles())) {
                    _context.next = 3;
                    break;
                  }
                  _context.next = 3;
                  return _this.initFromValue(newVal);
                case 3:
                case "end":
                  return _context.stop();
              }
            }
          }, _callee);
        }))();
      },
      immediate: true
    }
  },
  computed: {
    // 根据文件类型自动设置显示模式
    displayMode: function displayMode() {
      // 如果用户指定了mode，则使用用户指定的模式
      if (this.mode) {
        return this.mode;
      }

      // 根据文件类型自动设置显示模式：图片为grid，其他文件为list
      if (this.fileType === 'image') {
        return 'grid';
      } else {
        return 'list';
      }
    },
    // 根据文件类型自动设置文件扩展名
    effectiveFileExtname: function effectiveFileExtname() {
      // 如果用户明确指定了fileExtname，则使用用户指定的值
      if (this.fileExtname) {
        return this.fileExtname;
      }

      // 根据fileType自动设置默认扩展名
      switch (this.fileType) {
        case 'image':
          return 'png,jpg,jpeg,gif,bmp';
        case 'video':
          return 'mp4,avi,mov,wmv,flv,mkv';
        case 'file':
          return 'doc,docx,xls,xlsx,ppt,pptx,pdf,html,htm,txt,zip,rar';
        case 'all':
          return 'png,jpg,jpeg,gif,bmp,mp4,avi,mov,wmv,flv,mkv,doc,docx,xls,xlsx,ppt,pptx,pdf,html,htm,txt,zip,rar';
        default:
          return 'png,jpg,jpeg';
      }
    }
  },
  methods: {
    // 从value初始化文件列表和显示列表
    initFromValue: function initFromValue(value) {
      var _this2 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee2() {
        var fileList, response, fileNames, _fileNames;
        return _regenerator.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _this2.uploadFilesInfo = [];
                fileList = [];
                if (value) {
                  _context2.next = 5;
                  break;
                }
                _this2.fileList = fileList;
                return _context2.abrupt("return");
              case 5:
                _context2.prev = 5;
                _context2.next = 8;
                return (0, _oss.listByIds)(value);
              case 8:
                response = _context2.sent;
                if (response && response.data && response.data.length > 0) {
                  // 如果通过ossId获取到了数据
                  _this2.uploadFilesInfo = response.data.map(function (item) {
                    return {
                      fileName: item.fileName || item.ossId,
                      tempFilePath: item.url,
                      ossId: item.ossId
                    };
                  });

                  // 更新显示列表
                  response.data.forEach(function (item) {
                    fileList.push({
                      name: item.ossId,
                      // 使用ossId作为name，防止删除时出现重名问题
                      url: item.url,
                      filePath: item.url,
                      ossId: item.ossId
                    });
                  });
                } else {
                  // 如果不是ossId列表或者获取失败，则按照原来的方式处理
                  fileNames = value.split(_this2.separator);
                  _this2.uploadFilesInfo = fileNames.map(function (name) {
                    // 构建文件路径
                    var filePath = _this2.imageBaseUrl && _this2.fileType === 'image' ? _this2.imageBaseUrl + '/profile/' + name : '';

                    // 添加到显示列表
                    if (filePath) {
                      fileList.push({
                        name: name,
                        filePath: filePath,
                        url: filePath
                      });
                    }
                    return {
                      fileName: name,
                      tempFilePath: filePath
                    };
                  });
                }
                _this2.fileList = fileList;
                // 确保初始化完成后立即更新业务字段
                _this2.updateValue();
                _context2.next = 21;
                break;
              case 14:
                _context2.prev = 14;
                _context2.t0 = _context2["catch"](5);
                console.error('通过ossId获取图片数据失败:', _context2.t0);
                // 出错时降级为原来的处理方式
                _fileNames = value.split(_this2.separator);
                _this2.uploadFilesInfo = _fileNames.map(function (name) {
                  // 构建文件路径
                  var filePath = _this2.imageBaseUrl && _this2.fileType === 'image' ? _this2.imageBaseUrl + '/profile/' + name : '';

                  // 添加到显示列表
                  if (filePath) {
                    fileList.push({
                      name: name,
                      filePath: filePath,
                      url: filePath
                    });
                  }
                  return {
                    fileName: name,
                    tempFilePath: filePath
                  };
                });
                _this2.fileList = fileList;
                // 确保出错降级处理后也更新业务字段
                _this2.updateValue();
              case 21:
              case "end":
                return _context2.stop();
            }
          }
        }, _callee2, null, [[5, 14]]);
      }))();
    },
    // 从文件列表获取value值，只使用ossId（参考ImageUpload组件实现）
    getValueFromFiles: function getValueFromFiles() {
      //console.log('common-upload: getValueFromFiles called');
      if (!this.uploadFilesInfo || this.uploadFilesInfo.length === 0) {
        //console.log('common-upload: uploadFilesInfo is empty, returning empty string');
        return '';
      }

      // 只使用有ossId的文件，与参考组件保持一致
      var ossIds = [];
      for (var i = 0; i < this.uploadFilesInfo.length; i++) {
        if (this.uploadFilesInfo[i].ossId) {
          ossIds.push(this.uploadFilesInfo[i].ossId);
        }
      }
      var result = ossIds.join(this.separator);
      //console.log('common-upload: getValueFromFiles result:', result);
      return result;
    },
    // 更新value值
    updateValue: function updateValue() {
      //console.log('common-upload: updateValue called');
      var value = this.getValueFromFiles();
      //console.log('common-upload: emitting input event with value:', value);
      this.$emit('input', value);
      //console.log('common-upload: emitting change event with value:', value);
      this.$emit('change', value);
    },
    // 处理选择文件
    handleSelect: function handleSelect(e) {
      var _this3 = this;
      this.$emit('select', e);
      if (this.autoUpload && e.tempFilePaths && e.tempFilePaths.length > 0) {
        e.tempFilePaths.forEach(function (tempFilePath) {
          _this3.upload(tempFilePath);
        });
      }
    },
    // 处理删除文件
    handleDelete: function handleDelete(e) {
      this.$emit('delete', e);

      // 从uploadFilesInfo中删除对应的文件
      var index = this.uploadFilesInfo.findIndex(function (item) {
        // 处理不同的删除事件参数格式
        if ((0, _typeof2.default)(e) === 'object') {
          return item.tempFilePath === e.tempFilePath || item.fileName === e.name || item.ossId === e.ossId;
        }
        return false;
      });
      if (index > -1) {
        this.uploadFilesInfo.splice(index, 1);
        // 确保删除文件后立即更新业务字段
        this.updateValue();
      }
    },
    // 处理上传进度
    handleProgress: function handleProgress(e) {
      this.$emit('progress', e);
    },
    // 处理上传成功
    handleSuccess: function handleSuccess(e) {
      this.$emit('success', e);
    },
    // 处理内部上传成功（upload方法中调用）
    handleInternalSuccess: function handleInternalSuccess(_ref) {
      var result = _ref.result,
        filePath = _ref.filePath;
      //console.log('common-upload: handleInternalSuccess called with result:', result, 'filePath:', filePath);
      // 确保在上传成功后立即更新值
      this.updateValue();
      // 触发上传成功事件
      this.$emit('upload-success', {
        result: result,
        filePath: filePath
      });
    },
    // 处理上传失败
    handleFail: function handleFail(e) {
      this.$emit('fail', e);
      this.$api.msg('上传失败，请重试');
    },
    // 上传文件
    upload: function upload(filePath) {
      var _this4 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee3() {
        var result, uploadData, fileObj, existingIndex, fileIndex;
        return _regenerator.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _context3.prev = 0;
                if (!(_this4.fileType === 'image')) {
                  _context3.next = 7;
                  break;
                }
                _context3.next = 4;
                return (0, _commonUpload.uploadImage)(filePath, {
                  url: _this4.uploadUrl,
                  name: _this4.uploadFieldName,
                  onProgress: function onProgress(progress) {
                    _this4.$emit('upload-progress', {
                      progress: progress,
                      filePath: filePath
                    });
                  }
                });
              case 4:
                result = _context3.sent;
                _context3.next = 10;
                break;
              case 7:
                _context3.next = 9;
                return (0, _commonUpload.uploadFile)({
                  url: _this4.uploadUrl,
                  filePath: filePath,
                  name: _this4.uploadFieldName
                });
              case 9:
                result = _context3.sent;
              case 10:
                //console.log('common-upload: upload result:', result);
                // 处理上传结果
                // 根据用户提供的响应数据格式，正确解析data对象
                uploadData = result.data || result;
                if (uploadData && (uploadData.fileName || uploadData.ossId)) {
                  fileObj = {
                    fileName: uploadData.fileName,
                    tempFilePath: filePath,
                    ossId: uploadData.ossId || uploadData.fileName // 优先使用ossId
                  }; //console.log('common-upload: created fileObj:', fileObj);
                  // 检查是否已存在相同的文件
                  existingIndex = _this4.uploadFilesInfo.findIndex(function (item) {
                    return item.tempFilePath === filePath;
                  });
                  if (existingIndex > -1) {
                    //console.log('common-upload: updating existing file at index:', existingIndex);
                    _this4.uploadFilesInfo[existingIndex] = fileObj;
                  } else {
                    //console.log('common-upload: adding new file to uploadFilesInfo');
                    _this4.uploadFilesInfo.push(fileObj);
                  }

                  //console.log('common-upload: uploadFilesInfo after update:', this.uploadFilesInfo);
                  // 更新fileList，确保UI显示正确
                  fileIndex = _this4.fileList.findIndex(function (item) {
                    return item.filePath === filePath;
                  });
                  if (fileIndex > -1) {
                    //console.log('common-upload: updating fileList with ossId at index:', fileIndex);
                    _this4.fileList[fileIndex].ossId = fileObj.ossId;
                  }

                  // 调用内部成功处理方法
                  _this4.handleInternalSuccess({
                    result: result,
                    filePath: filePath
                  });
                } else {
                  console.warn('common-upload: upload result missing fileName and ossId:', result);
                }
                _context3.next = 19;
                break;
              case 14:
                _context3.prev = 14;
                _context3.t0 = _context3["catch"](0);
                console.error('上传文件失败:', _context3.t0);
                _this4.$emit('upload-error', {
                  error: _context3.t0,
                  filePath: filePath
                });
                throw _context3.t0;
              case 19:
              case "end":
                return _context3.stop();
            }
          }
        }, _callee3, null, [[0, 14]]);
      }))();
    },
    // 获取上传文件的信息列表
    getUploadFilesInfo: function getUploadFilesInfo() {
      return this.uploadFilesInfo;
    },
    // 清空所有文件
    clear: function clear() {
      this.fileList = [];
      this.uploadFilesInfo = [];
      this.updateValue();
    },
    // 添加文件（用于外部调用）
    addFile: function addFile(filePath) {
      if (this.autoUpload) {
        return this.upload(filePath);
      } else {
        // 如果不是自动上传，添加到文件列表但不上传
        var fileObj = {
          fileName: '',
          // 文件名需要在手动上传时设置
          tempFilePath: filePath
        };
        this.uploadFilesInfo.push(fileObj);
        return Promise.resolve(fileObj);
      }
    },
    // 手动上传所有文件（当autoUpload为false时使用）
    uploadAll: function uploadAll() {
      var _this5 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee4() {
        var results, i, fileInfo, result;
        return _regenerator.default.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                results = [];
                i = 0;
              case 2:
                if (!(i < _this5.uploadFilesInfo.length)) {
                  _context4.next = 18;
                  break;
                }
                fileInfo = _this5.uploadFilesInfo[i]; // 只上传未上传的文件
                if (!(!fileInfo.fileName && fileInfo.tempFilePath)) {
                  _context4.next = 15;
                  break;
                }
                _context4.prev = 5;
                _context4.next = 8;
                return _this5.upload(fileInfo.tempFilePath);
              case 8:
                result = _context4.sent;
                results.push(result);
                _context4.next = 15;
                break;
              case 12:
                _context4.prev = 12;
                _context4.t0 = _context4["catch"](5);
                results.push({
                  error: _context4.t0
                });
              case 15:
                i++;
                _context4.next = 2;
                break;
              case 18:
                return _context4.abrupt("return", results);
              case 19:
              case "end":
                return _context4.stop();
            }
          }
        }, _callee4, null, [[5, 12]]);
      }))();
    }
  }
};
exports.default = _default;

/***/ }),

/***/ 503:
/*!*****************************************************************************************************************************************!*\
  !*** D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue?vue&type=style&index=0&lang=scss& ***!
  \*****************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_8_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_8_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_8_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_sass_loader_dist_cjs_js_ref_8_oneOf_1_4_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_5_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/mini-css-extract-plugin/dist/loader.js??ref--8-oneOf-1-0!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/css-loader/dist/cjs.js??ref--8-oneOf-1-1!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--8-oneOf-1-2!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/postcss-loader/src??ref--8-oneOf-1-3!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/sass-loader/dist/cjs.js??ref--8-oneOf-1-4!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--8-oneOf-1-5!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./common-upload.vue?vue&type=style&index=0&lang=scss& */ 504);
/* harmony import */ var _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_8_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_8_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_8_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_sass_loader_dist_cjs_js_ref_8_oneOf_1_4_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_5_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_8_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_8_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_8_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_sass_loader_dist_cjs_js_ref_8_oneOf_1_4_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_5_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_8_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_8_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_8_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_sass_loader_dist_cjs_js_ref_8_oneOf_1_4_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_5_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_0__) if(["default"].indexOf(__WEBPACK_IMPORT_KEY__) < 0) (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_8_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_8_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_8_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_sass_loader_dist_cjs_js_ref_8_oneOf_1_4_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_5_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_8_oneOf_1_0_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_8_oneOf_1_1_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_2_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_8_oneOf_1_3_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_sass_loader_dist_cjs_js_ref_8_oneOf_1_4_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_8_oneOf_1_5_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_common_upload_vue_vue_type_style_index_0_lang_scss___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 504:
/*!*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/mini-css-extract-plugin/dist/loader.js??ref--8-oneOf-1-0!./node_modules/css-loader/dist/cjs.js??ref--8-oneOf-1-1!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--8-oneOf-1-2!./node_modules/postcss-loader/src??ref--8-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/sass-loader/dist/cjs.js??ref--8-oneOf-1-4!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--8-oneOf-1-5!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/aaProjects/autoee-iot-ems-app-20080/autoee-uniapp/components/common-upload/common-upload.vue?vue&type=style&index=0&lang=scss& ***!
  \*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin
    if(false) { var cssReload; }
  

/***/ })

}]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/components/common-upload/common-upload.js.map
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'components/common-upload/common-upload-create-component',
    {
        'components/common-upload/common-upload-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('2')['createComponent'](__webpack_require__(498))
        })
    },
    [['components/common-upload/common-upload-create-component']]
]);
