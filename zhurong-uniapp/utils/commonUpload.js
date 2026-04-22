import config from '@/config'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { toast, showConfirm, tansParams } from '@/utils/common'
import store from '@/store'

let timeout = 10000
const uploadReqUrl = config.uploadReqUrl

/**
 * 通用文件上传方法
 * @param {Object} config 上传配置
 * @param {String} config.url 上传接口路径
 * @param {String} config.filePath 文件路径
 * @param {String} config.name 上传文件的字段名
 * @param {Object} config.header 请求头
 * @param {Object} config.formData 表单数据
 * @param {Number} config.timeout 超时时间
 * @returns {Promise} 返回上传结果
 */
export const uploadFile = (config) => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  config.header = config.header || {}
  if (getToken() && !isToken) {
    config.header['Authorization'] = 'Bearer ' + getToken()
  }
  // get请求映射params参数
  if (config.params) {
    let url = config.url + '?' + tansParams(config.params)
    url = url.slice(0, -1)
    config.url = url
  }
  return new Promise((resolve, reject) => {
      uni.uploadFile({
        timeout: config.timeout || timeout,
        url: uploadReqUrl  +config.url,
        filePath: config.filePath,
        name: config.name || 'file',
        header: config.header,
        formData: config.formData,
        success: (res) => {
          let result = JSON.parse(res.data)
          const code = result.code || 200
          const msg = errorCode[code] || result.msg || errorCode['default']
          if (code === 200) {
            resolve(result)
          } else if (code == 401) {
            showConfirm("当前功能需登陆后访问，确认登陆吗？?").then(res => {
              if (res.confirm) {
                store.dispatch('LogOut').then(res => {
                  uni.reLaunch({ url: '/pages/login/login' })
                })
              }
            })
            reject('无效的会话，或者会话已过期，请重新登录。')
          } else if (code === 500) {
            toast(msg)
            reject('500')
          } else if (code !== 200) {
            toast(msg)
            reject(code)
          }
        },
        fail: (error) => {
          let { message } = error
          if (message == 'Network Error') {
            message = '后端接口连接异常'
          } else if (message.includes('timeout')) {
            message = '系统接口请求超时'
          } else if (message.includes('Request failed with status code')) {
            message = '系统接口' + message.substr(message.length - 3) + '异常'
          }
          toast(message)
          reject(error)
        }
      })
  })
}

/**
 * 上传图片方法
 * @param {String} tempFilePath 图片临时路径
 * @param {Object} options 上传选项
 * @param {String} options.url 上传接口路径
 * @param {String} options.name 上传字段名，默认为'file'
 * @param {Object} options.formData 表单数据
 * @param {Function} options.onProgress 上传进度回调
 * @returns {Promise} 返回上传结果
 */
export const uploadImage = (tempFilePath, options = {}) => {
  const {
    name = 'file',
    formData = {},
    onProgress
  } = options

  return new Promise((resolve, reject) => {
    const uploadTask = uni.uploadFile({
      url: uploadReqUrl,
      filePath: tempFilePath,
      name: name,
      formData: formData,
      header: {
        'Authorization': 'Bearer ' + getToken()
      },
      success: (res) => {
        try {
          let result = JSON.parse(res.data)
          if (result.code === 200) {
            resolve(result)
          } else {
            toast(result.msg || '上传失败')
            reject(result)
          }
        } catch (e) {
          toast('上传返回数据格式错误')
          reject(e)
        }
      },
      fail: (error) => {
        toast('上传失败，请重试')
        reject(error)
      }
    })

    // 监听上传进度
    if (onProgress && typeof onProgress === 'function') {
      uploadTask.onProgressUpdate((progressRes) => {
        onProgress(progressRes.progress)
      })
    }
  })
}

/**
 * 上传多个图片方法
 * @param {Array} tempFilePaths 图片临时路径数组
 * @param {Object} options 上传选项
 * @param {String} options.url 上传接口路径
 * @param {String} options.name 上传字段名，默认为'file'
 * @param {Object} options.formData 表单数据
 * @param {Function} options.onProgress 上传进度回调
 * @param {Function} options.onFileSuccess 单个文件上传成功回调
 * @returns {Promise} 返回所有图片上传结果数组
 */
export const uploadImages = async (tempFilePaths, options = {}) => {
  const results = []
  for (let i = 0; i < tempFilePaths.length; i++) {
    try {
      const result = await uploadImage(tempFilePaths[i], {
        ...options,
        onProgress: (progress) => {
          if (options.onProgress) {
            options.onProgress(progress, i, tempFilePaths.length)
          }
        }
      })
      results.push(result)
      if (options.onFileSuccess) {
        options.onFileSuccess(result, i)
      }
    } catch (error) {
      // 可以选择继续上传下一个或者抛出错误中断上传
      // 这里选择继续上传下一个
      console.error(`上传第${i+1}个图片失败:`, error)
      results.push({ error })
    }
  }
  return results
}

/**
 * 选择图片并上传
 * @param {Object} options 选项
 * @param {Number} options.count 选择图片数量
 * @param {String[]} options.sizeType 图片类型
 * @param {String[]} options.sourceType 图片来源
 * @param {String} options.url 上传接口路径
 * @param {Function} options.onProgress 上传进度回调
 * @returns {Promise} 返回选择并上传的结果
 */
export const chooseAndUploadImage = async (options = {}) => {
  const {
    count = 9,
    sizeType = ['original', 'compressed'],
    sourceType = ['album', 'camera'],
    ...uploadOptions
  } = options

  return new Promise((resolve, reject) => {
    uni.chooseImage({
      count: count,
      sizeType: sizeType,
      sourceType: sourceType,
      success: async (res) => {
        try {
          const results = await uploadImages(res.tempFilePaths, uploadOptions)
          resolve(results)
        } catch (error) {
          reject(error)
        }
      },
      fail: (error) => {
        reject(error)
      }
    })
  })
}

/**
 * 选择文件并上传
 * @param {Object} options 选项
 * @param {Number} options.count 选择文件数量
 * @param {String[]} options.extension 文件扩展名
 * @param {String} options.url 上传接口路径
 * @returns {Promise} 返回选择并上传的结果
 */
export const chooseAndUploadFile = async (options = {}) => {
  const {
    count = 1,
    extension = ['doc', 'docx', 'xls', 'xlsx', 'pdf', 'zip', 'rar'],
    url = '/system/oss/upload'
  } = options

  return new Promise((resolve, reject) => {
    uni.chooseMessageFile({
      count: count,
      type: 'file',
      extension: extension,
      success: async (res) => {
        try {
          const results = []
          for (let i = 0; i < res.tempFiles.length; i++) {
            const file = res.tempFiles[i]
            const uploadResult = await uploadFile({
              url: url,
              filePath: file.path,
              name: 'file'
            })
            results.push({
              ...uploadResult,
              originalFileObj: file
            })
          }
          resolve(results)
        } catch (error) {
          reject(error)
        }
      },
      fail: (error) => {
        reject(error)
      }
    })
  })
}

export default {
  uploadFile,
  uploadImage,
  uploadImages,
  chooseAndUploadImage,
  chooseAndUploadFile
}
