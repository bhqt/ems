import { listByIds } from '@/api/system/oss';

/**
 * dongbei 2025-9-13
 * 加载图片URL（兼容OSS ID和直接URL）
 * @param {string|Array} images - 图片字符串（逗号分隔）或数组
 * @returns {Promise<Array<{url: string, ossId: string|null}>>} - 返回图片对象数组
 */
export async function loadImages(images) {
  if (!images || (Array.isArray(images) && images.length === 0)) {
    return [];
  }

  // 统一转为数组并去除空白
  const imageList = Array.isArray(images)
    ? images.filter(item => item)
    : images.split(',').map(item => item.trim()).filter(item => item);

  if (imageList.length === 0) return [];

  // 判断是否是OSS ID（假设OSS ID是纯数字）
  const isOssId = imageList.every(img => /^\d+$/.test(img));

  try {
    if (isOssId) {
      // 处理OSS ID情况
      const response = await listByIds(imageList.join(','));
      return response.data.map(item => ({
        url: item.url,
        ossId: item.ossId
      }));
    } else {
      // 处理直接URL情况
      return imageList.map(url => ({
        url: url,
        ossId: null
      }));
    }
  } catch (error) {
    console.error('图片加载失败:', error);
    return imageList.map(item => ({
      url: '',
      ossId: item,
      error: true
    }));
  }
}

/**
 * 判断是否是OSS ID（可在外部覆盖）
 * @param {string} str
 * @returns {boolean}
 */
export function isOssId(str) {
  return /^\d+$/.test(str);
}
