package com.cpems.oss.core.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.cpems.oss.core.OssClient;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import com.cpems.oss.entity.UploadResult;
import com.cpems.oss.enumd.AccessPolicyType;
import com.cpems.oss.exception.OssException;
import com.cpems.oss.properties.OssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * 本地文件存储实现
 */
public class LocalOssClient extends OssClient {
    private static final Logger logger = LoggerFactory.getLogger(LocalOssClient.class);
    private final OssProperties properties;
    private final String configKey;

    public LocalOssClient(String configKey, OssProperties properties) {
        super(configKey, properties);
        this.configKey = configKey;
        this.properties = properties;
        // 初始化时创建存储目录
        init();
    }

    private void init() {
        String uploadPath = getUploadPath();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new OssException("创建本地存储目录失败: " + uploadPath);
            }
        }
    }

    public UploadResult upload(InputStream inputStream, String path, String contentType, String originalfileName) {
        File targetFile = null;
        try {
            // 1. 构建路径
            String uploadPath = getUploadPath();
            String uploadFullPath = Paths.get(uploadPath, path, originalfileName).toString();
            targetFile = new File(uploadFullPath);

            logger.info("===== 文件上传调试信息 =====");
            logger.info("上传基础路径: {}", uploadPath);
            logger.info("完整文件路径: {}", uploadFullPath);
            // logger.info("父目录是否存在: {}", targetFile.getParentFile().exists());
            // logger.info("父目录可写: {}", targetFile.getParentFile().canWrite());
            // logger.info("磁盘剩余空间: {} MB", targetFile.getParentFile().getFreeSpace() / 1024 / 1024);

            // 2. 确保目录存在
            File parentDir = targetFile.getParentFile();
            if (!parentDir.exists()) {
                logger.info("尝试创建目录: {}", parentDir.getAbsolutePath());
                boolean mkdirResult = parentDir.mkdirs();
                logger.info("目录创建结果: {}", mkdirResult);
                if (!mkdirResult) {
                    throw new OssException("目录创建失败: " + parentDir.getAbsolutePath());
                }
            }

            // 3. 写入文件
            byte[] data = IoUtil.readBytes(inputStream);
            logger.info("读取到数据大小: {} 字节", data.length);

            logger.info("开始写入文件...");
            FileUtil.writeBytes(data, uploadFullPath);
            logger.info("文件写入完成");

            // 4. 验证写入结果
            boolean fileExists = targetFile.exists();
            long fileSize = fileExists ? targetFile.length() : 0;
            logger.info("文件验证 - 存在: {}, 大小: {} 字节", fileExists, fileSize);

            if (!fileExists || fileSize == 0) {
                throw new OssException("文件写入验证失败 (存在: " + fileExists + ", 大小: " + fileSize + ")");
            }

            // 5. 返回结果
            UploadResult result = UploadResult.builder()
                .url(getUrl() + Constants.RESOURCE_PREFIX + "/" + path + "/" + originalfileName)
                .filename(path)
                .build();

            logger.info("文件上传成功: {}", JsonUtils.toJsonString(result));
            return result;

        } catch (Exception e) {
            logger.error("文件上传失败详情", e);
            if (targetFile != null) {
                logger.error("目标文件状态 - 存在: {}, 可读: {}, 可写: {}, 大小: {}",
                    targetFile.exists(),
                    targetFile.canRead(),
                    targetFile.canWrite(),
                    targetFile.length());
            }
            throw new OssException("上传文件到本地失败: " + e.getMessage());
        }
    }

    // public UploadResult upload(InputStream inputStream, String path, String contentType, String originalfileName) {
    //     try {
    //         // 1. 构建完整路径
    //         String uploadPath = getUploadPath();
    //         String uploadFullPath = Paths.get(uploadPath, path, originalfileName).toString();
    //
    //         logger.info("准备写入文件到: {}", uploadFullPath); // 路径日志
    //
    //         // 2. 确保目录存在
    //         File targetFile = new File(uploadFullPath);
    //         File parentDir = targetFile.getParentFile();
    //
    //         logger.info("父目录: {}", parentDir.getAbsolutePath());
    //         logger.info("目录可写: {}", parentDir.canWrite());
    //
    //         if (!parentDir.exists()) {
    //             logger.info("尝试创建目录...");
    //             if (!parentDir.mkdirs()) {
    //                 throw new OssException("创建目录失败: " + parentDir.getAbsolutePath() +
    //                     " 权限: " + (parentDir.canWrite() ? "可写" : "不可写"));
    //             }
    //         }
    //
    //         // 3. 检查输入流
    //         if (inputStream == null) {
    //             throw new OssException("输入流为null");
    //         }
    //
    //         byte[] data = IoUtil.readBytes(inputStream);
    //         if (data == null || data.length == 0) {
    //             throw new OssException("读取到的数据为空");
    //         }
    //
    //         // 4. 写入文件
    //         logger.info("准备写入 {} 字节数据", data.length);
    //         FileUtil.writeBytes(data, uploadFullPath);
    //
    //         // 5. 验证文件是否写入成功
    //         if (!targetFile.exists() || targetFile.length() == 0) {
    //             throw new OssException("文件写入后验证失败");
    //         }
    //
    //         // 6. 构建返回结果
    //         UploadResult uploadResult = UploadResult.builder()
    //             .url(getUrl() + Constants.RESOURCE_PREFIX + "/" + path + "/" + originalfileName)
    //             .filename(path)
    //             .build();
    //
    //         logger.info("文件上传成功: {}", JsonUtils.toJsonString(uploadResult));
    //         return uploadResult;
    //
    //     } catch (Exception e) {
    //         logger.error("文件上传失败", e);
    //         throw new OssException("上传文件到本地失败: " + e.getMessage());
    //     }
    // }

    public void delete(String path) {
        try {
            String uploadPath = getUploadPath();
            String fullPath = Paths.get(uploadPath, path.replace(getUrl() + "/", "")).toString();
            File file = new File(fullPath);
            if (file.exists()) {
                if (!file.delete()) {
                    throw new OssException("删除文件失败: " + fullPath);
                }
            }
        } catch (Exception e) {
            throw new OssException("删除本地文件失败: " + e.getMessage());
        }
    }

    public String getUrl() {
        String domain = properties.getDomain();
        if (StringUtils.isNotBlank(domain)) {
            return domain;
        }
        // 本地存储直接返回文件路径
        return "file:" + properties.getEndpoint();
    }

    public String getPath(String prefix, String suffix) {
        // 生成唯一文件夹名
        String folderName = IdUtil.fastSimpleUUID();

        // 构建路径：prefix/日期/文件夹名/原始文件名
        String path = DateUtils.datePath() + "/" + folderName;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path;
    }

    /**
     * 获取实际上传路径
     */

    private String getUploadPath() {
        String basePath = RuoYiConfig.getProfile();
        // String basePath = properties.getEndpoint();
        //
        // // 如果配置了bucketName，将其作为子目录
        // if (StringUtils.isNotBlank(properties.getBucketName())) {
        //     basePath = Paths.get(basePath, properties.getBucketName()).toString();
        // }

        return basePath;
    }

    public String getPrivateUrl(String objectKey, Integer second) {
        // 本地存储不需要生成临时URL，直接返回访问URL
        return getUrl() + "/" + objectKey;
    }

    public AccessPolicyType getAccessPolicy() {
        // 本地存储默认为公开访问
        return AccessPolicyType.PUBLIC;
    }

    public String getConfigKey() {
        return configKey;
    }

    public boolean checkPropertiesSame(OssProperties properties) {
        return this.properties.equals(properties);
    }
}
