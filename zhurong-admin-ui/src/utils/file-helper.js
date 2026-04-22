import { listByIds } from '@/api/system/oss';

/**
 * 加载文件URL（兼容OSS ID和直接URL）
 * @param {string|Array} files - 文件字符串（逗号分隔）或数组
 * @returns {Promise<Array<{url: string, ossId: string|null, name: string|null}>>} - 返回文件对象数组
 */
export async function loadFiles(files) {
  if (!files || (Array.isArray(files) && files.length === 0)) {
    return [];
  }

  // 统一转为数组并去除空白
  const fileList = Array.isArray(files)
    ? files.filter(item => item)
    : files.split(',').map(item => item.trim()).filter(item => item);

  if (fileList.length === 0) return [];

  try {
    // 判断是否是OSS ID（假设OSS ID是纯数字）
    const isOssId = fileList.every(file => /^\d+$/.test(file));

    if (isOssId) {
      // 处理OSS ID情况
      const response = await listByIds(fileList.join(','));
      return response.data.map(item => ({
        url: item.url,
        ossId: item.ossId,
        name: item.fileName || item.url.split('/').pop()
      }));
    } else {
      // 处理直接URL情况
      return fileList.map(url => ({
        url: url,
        ossId: null,
        name: url.split('/').pop()
      }));
    }
  } catch (error) {
    console.error('文件加载失败:', error);
    return fileList.map(item => ({
      url: '',
      ossId: item,
      name: null,
      error: true
    }));
  }
}

/**
 * 判断是否是OSS ID（可在外部覆盖）
 * @param {string} str
 * @returns {boolean}
 */
export function isFileOssId(str) {
  return /^\d+$/.test(str);
}
