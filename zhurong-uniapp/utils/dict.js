// utils/dict.js
import { getDictDataByType } from '@/api/system/dictManage.js'

export const getDictData = (dictType, params = {}) => {
  return new Promise((resolve, reject) => {
    getDictDataByType({ dictType, ...params })
      .then(res => {  // 注意参数名应该是res
        // console.log('[DEBUG] 完整接口响应:', res)

        // 基础校验
        if (!res) {
          return reject(new Error('接口返回空响应'))
        }

        // 状态码校验
        if (res.code !== 200) {
          return reject(new Error(res.msg || `接口响应异常 code: ${res.code}`))
        }

        // 数据结构校验（根据你的接口文档调整）
        let rawData = []
        if (Array.isArray(res.data)) {
          // 情况1：直接返回数组
          rawData = res.data
        } else if (res.data && res.data.records) {
          // 情况2：分页结构（如{ records: [...] }）
          rawData = res.data.records
        } else if (res.data && res.data.list) {
          // 情况3：其他列表结构
          rawData = res.data.list
        } else {
          console.warn('未识别的数据结构:', res.data)
        }
        // console.log('[DEBUG] 处理后数据:', rawData)

        // 数据转换
        const result = rawData
          .filter(item => {
            const isValid = item.dictValue !== undefined && item.dictLabel !== undefined
            if (!isValid) {
              console.warn('无效数据项:', item)
            }
            return isValid
          })
          .map(item => ({
            value: String(item.dictValue), // 强制转为字符串
            text: item.dictLabel || '未知标签' // 空值保护
          }))

        // console.log('[DEBUG] 最终字典数据:', result)
        resolve(result)
      })
      .catch(error => {
        console.error('[ERROR] 请求失败:', error)
        reject(new Error(`获取字典失败: ${error.message}`))
      })
  })
}
