<template>
  <view class="common-upload">
    <uni-file-picker
      v-model="fileList"
      :fileMediatype="fileType"
      :file-extname="effectiveFileExtname"
      :mode="displayMode"
      :auto-upload="autoUpload"
      :limit="limit"
      :title="title"
      :disabled="disabled"
      :readonly="readonly"
      :delIcon="showDelete"
      :disablePreview="disablePreview"
      @select="handleSelect"
      @delete="handleDelete"
      @progress="handleProgress"
      @success="handleSuccess"
      @fail="handleFail"
    >
      <slot></slot>
    </uni-file-picker>
  </view>
</template>

<script>
import { uploadImage, uploadFile as commonUploadFile } from '@/utils/commonUpload';
import { listByIds } from '@/api/system/oss';

/**
 * 公共上传组件
 * 封装了文件/图片上传的所有逻辑，提供统一的上传接口
 */
export default {
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
      default: process.env.VUE_APP_BASE_API || ''
    }
  },
  data() {
    return {
      fileList: [], // 文件列表，用于绑定uni-file-picker
      uploadFilesInfo: [] // 上传文件的信息，包含文件名等
    };
  },
  watch: {
    // 监听uploadFilesInfo变化，更新value值
    uploadFilesInfo: {
      handler() {
        this.updateValue();
      },
      deep: true
    },
    // 监听value变化，用于外部修改和初始化
    value: {
      async handler(newVal) {
        if (newVal !== this.getValueFromFiles()) {
          await this.initFromValue(newVal);
        }
      },
      immediate: true
    }
  },
  computed: {
    // 根据文件类型自动设置显示模式
    displayMode() {
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
    effectiveFileExtname() {
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
    async initFromValue(value) {
      this.uploadFilesInfo = [];
      const fileList = [];

      if (!value) {
        this.fileList = fileList;
        return;
      }

      try {
        // 尝试通过ossId获取图片数据
        const response = await listByIds(value);
        if (response && response.data && response.data.length > 0) {
          // 如果通过ossId获取到了数据
          this.uploadFilesInfo = response.data.map(item => ({
            fileName: item.fileName || item.ossId,
            tempFilePath: item.url,
            ossId: item.ossId
          }));

          // 更新显示列表
          response.data.forEach(item => {
            fileList.push({
              name: item.ossId, // 使用ossId作为name，防止删除时出现重名问题
              url: item.url,
              filePath: item.url,
              ossId: item.ossId
            });
          });
        } else {
          // 如果不是ossId列表或者获取失败，则按照原来的方式处理
          const fileNames = value.split(this.separator);
          this.uploadFilesInfo = fileNames.map(name => {
            // 构建文件路径
            const filePath = this.imageBaseUrl && this.fileType === 'image'
              ? this.imageBaseUrl + '/profile/' + name
              : '';

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

        this.fileList = fileList;
        // 确保初始化完成后立即更新业务字段
        this.updateValue();
      } catch (error) {
        console.error('通过ossId获取图片数据失败:', error);
        // 出错时降级为原来的处理方式
        const fileNames = value.split(this.separator);
        this.uploadFilesInfo = fileNames.map(name => {
          // 构建文件路径
          const filePath = this.imageBaseUrl && this.fileType === 'image'
            ? this.imageBaseUrl + '/profile/' + name
            : '';

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

        this.fileList = fileList;
        // 确保出错降级处理后也更新业务字段
        this.updateValue();
      }
    },

    // 从文件列表获取value值，只使用ossId（参考ImageUpload组件实现）
    getValueFromFiles() {
      //console.log('common-upload: getValueFromFiles called');
      if (!this.uploadFilesInfo || this.uploadFilesInfo.length === 0) {
        //console.log('common-upload: uploadFilesInfo is empty, returning empty string');
        return '';
      }

      // 只使用有ossId的文件，与参考组件保持一致
      const ossIds = [];
      for (let i = 0; i < this.uploadFilesInfo.length; i++) {
        if (this.uploadFilesInfo[i].ossId) {
          ossIds.push(this.uploadFilesInfo[i].ossId);
        }
      }
      const result = ossIds.join(this.separator);
      //console.log('common-upload: getValueFromFiles result:', result);
      return result;
    },

    // 更新value值
    updateValue() {
      //console.log('common-upload: updateValue called');
      const value = this.getValueFromFiles();
      //console.log('common-upload: emitting input event with value:', value);
      this.$emit('input', value);
      //console.log('common-upload: emitting change event with value:', value);
      this.$emit('change', value);
    },

    // 处理选择文件
    handleSelect(e) {
      this.$emit('select', e);

      if (this.autoUpload && e.tempFilePaths && e.tempFilePaths.length > 0) {
        e.tempFilePaths.forEach(tempFilePath => {
          this.upload(tempFilePath);
        });
      }
    },

    // 处理删除文件
    handleDelete(e) {
      this.$emit('delete', e);

      // 从uploadFilesInfo中删除对应的文件
      const index = this.uploadFilesInfo.findIndex(item => {
        // 处理不同的删除事件参数格式
        if (typeof e === 'object') {
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
    handleProgress(e) {
      this.$emit('progress', e);
    },

    // 处理上传成功
    handleSuccess(e) {
      this.$emit('success', e);
    },

    // 处理内部上传成功（upload方法中调用）
    handleInternalSuccess({ result, filePath }) {
      //console.log('common-upload: handleInternalSuccess called with result:', result, 'filePath:', filePath);
      // 确保在上传成功后立即更新值
      this.updateValue();
      // 触发上传成功事件
      this.$emit('upload-success', { result, filePath });
    },

    // 处理上传失败
    handleFail(e) {
      this.$emit('fail', e);
      this.$api.msg('上传失败，请重试');
    },

    // 上传文件
    async upload(filePath) {
      //console.log('common-upload: upload called for filePath:', filePath);
      try {
        // 根据文件类型选择不同的上传方法
        let result;
        if (this.fileType === 'image') {
          result = await uploadImage(filePath, {
            url: this.uploadUrl,
            name: this.uploadFieldName,
            onProgress: (progress) => {
              this.$emit('upload-progress', { progress, filePath });
            }
          });
        } else {
          result = await commonUploadFile({
            url: this.uploadUrl,
            filePath: filePath,
            name: this.uploadFieldName
          });
        }

        //console.log('common-upload: upload result:', result);
        // 处理上传结果
        // 根据用户提供的响应数据格式，正确解析data对象
        const uploadData = result.data || result;
        if (uploadData && (uploadData.fileName || uploadData.ossId)) {
          const fileObj = {
            fileName: uploadData.fileName,
            tempFilePath: filePath,
            ossId: uploadData.ossId || uploadData.fileName // 优先使用ossId
          };

          //console.log('common-upload: created fileObj:', fileObj);
          // 检查是否已存在相同的文件
          const existingIndex = this.uploadFilesInfo.findIndex(item =>
            item.tempFilePath === filePath
          );

          if (existingIndex > -1) {
            //console.log('common-upload: updating existing file at index:', existingIndex);
            this.uploadFilesInfo[existingIndex] = fileObj;
          } else {
            //console.log('common-upload: adding new file to uploadFilesInfo');
            this.uploadFilesInfo.push(fileObj);
          }

          //console.log('common-upload: uploadFilesInfo after update:', this.uploadFilesInfo);
          // 更新fileList，确保UI显示正确
          const fileIndex = this.fileList.findIndex(item => item.filePath === filePath);
          if (fileIndex > -1) {
            //console.log('common-upload: updating fileList with ossId at index:', fileIndex);
            this.fileList[fileIndex].ossId = fileObj.ossId;
          }

          // 调用内部成功处理方法
          this.handleInternalSuccess({ result, filePath });
        } else {
          console.warn('common-upload: upload result missing fileName and ossId:', result);
        }
      } catch (error) {
        console.error('上传文件失败:', error);
        this.$emit('upload-error', { error, filePath });
        throw error;
      }
    },

    // 获取上传文件的信息列表
    getUploadFilesInfo() {
      return this.uploadFilesInfo;
    },

    // 清空所有文件
    clear() {
      this.fileList = [];
      this.uploadFilesInfo = [];
      this.updateValue();
    },

    // 添加文件（用于外部调用）
    addFile(filePath) {
      if (this.autoUpload) {
        return this.upload(filePath);
      } else {
        // 如果不是自动上传，添加到文件列表但不上传
        const fileObj = {
          fileName: '', // 文件名需要在手动上传时设置
          tempFilePath: filePath
        };
        this.uploadFilesInfo.push(fileObj);
        return Promise.resolve(fileObj);
      }
    },

    // 手动上传所有文件（当autoUpload为false时使用）
    async uploadAll() {
      const results = [];
      for (let i = 0; i < this.uploadFilesInfo.length; i++) {
        const fileInfo = this.uploadFilesInfo[i];
        // 只上传未上传的文件
        if (!fileInfo.fileName && fileInfo.tempFilePath) {
          try {
            const result = await this.upload(fileInfo.tempFilePath);
            results.push(result);
          } catch (error) {
            results.push({ error });
          }
        }
      }
      return results;
    }
  }
};
</script>

<style lang="scss">
.common-upload {
  /* 可以根据需要添加样式 */
}
</style>
