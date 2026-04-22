package com.ruoyi.common.utils.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.Seq;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Objects;

/**
 * 文件上传工具类
 * @author ruoyi
 */
public class FileUploadUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
	/**
	 * 默认大小 50M
	 */
	public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

	/**
	 * 默认的文件名最大长度 100
	 */
	public static final int DEFAULT_FILE_NAME_LENGTH = 500;

	/**
	 * 默认上传的地址
	 */
	private static String defaultBaseDir = RuoYiConfig.getProfile();

	public static void setDefaultBaseDir(String defaultBaseDir) {
		FileUploadUtils.defaultBaseDir = defaultBaseDir;
	}

	public static String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	/**
	 * 以默认配置进行文件上传
	 * @param file 上传的文件
	 * @return 文件名称
	 */
	public static final String upload(MultipartFile file) throws IOException {
		try {
			return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 根据文件路径上传
	 * @param baseDir 相对应用的基目录
	 * @param file    上传的文件
	 * @return 文件名称
	 */
	public static final String upload(String baseDir, MultipartFile file) throws IOException {
		try {
			return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 文件上传
	 * @param baseDir          相对应用的基目录
	 * @param file             上传的文件
	 * @param allowedExtension 上传文件类型
	 * @return 返回上传成功的文件名
	 * @throws FileSizeLimitExceededException       如果超出最大大小
	 * @throws FileNameLengthLimitExceededException 文件名太长
	 * @throws IOException                          比如读写文件出错时
	 * @throws InvalidExtensionException            文件校验异常
	 */
	public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
			throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
			       InvalidExtensionException {
		int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
		}

		assertAllowed(file, allowedExtension);

		String fileName = getFilePathAndName(file);

		String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
		file.transferTo(Paths.get(absPath));
		return getFileUrl(baseDir, fileName);
	}

	/**
	 * 生成文件路径和文件名
	 */
	public static final String getFilePathAndName(MultipartFile file) {
		// Fuxs 保留原有的文件名，将文件放入不同的文件夹中
		//datePath() 为 日期路径 即年/月/日 如2018/08/08
		return StringUtils.format("{}/{}/{}.{}", DateUtils.datePath(), Seq.getId(Seq.uploadSeqType),
				FilenameUtils.getBaseName(file.getOriginalFilename()), getExtension(file));
		// return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
		// 		FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), getExtension(file));
	}

	/**
	 * 生成文件路径和文件名
	 */
	public static final String getFilePathAndName(String fileName) {
		// Fuxs 保留原有的文件名，将文件放入不同的文件夹中
		//datePath() 为 日期路径 即年/月/日 如2018/08/08/39999/a.mp3
		return StringUtils.format("{}/{}/{}", DateUtils.datePath(), Seq.getId(Seq.uploadSeqType), fileName);
	}

	public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
		File desc = new File(uploadDir + File.separator + fileName);

		if (!desc.exists()) {
			if (!desc.getParentFile().exists()) {
				desc.getParentFile().mkdirs();
			}
		}
		return desc;
	}

	/**
	 * 获取服务器文件的请求url
	 * @param uploadDir
	 * @param fileName
	 * @return
	 */
	public static final String getFileUrl(String uploadDir, String fileName) throws IOException {
		int dirLastIndex = RuoYiConfig.getProfile().length() + 1;
		String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
		return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
	}

	/**
	 * 文件大小校验
	 * @param file 上传的文件
	 * @return
	 * @throws FileSizeLimitExceededException 如果超出最大大小
	 */
	public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
			throws FileSizeLimitExceededException, InvalidExtensionException {
		long size = file.getSize();
		if (size > DEFAULT_MAX_SIZE) {
			throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
		}

		String fileName = file.getOriginalFilename();
		String extension = getExtension(file);
		allowedExtensionCheck(allowedExtension, fileName, extension);
	}

	private static void allowedExtensionCheck(String[] allowedExtension, String fileName, String extension) throws InvalidExtensionException {
		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
				throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
				throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
				throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
				throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
						fileName);
			} else {
				throw new InvalidExtensionException(allowedExtension, extension, fileName);
			}
		}
	}

	/**
	 * 判断MIME类型是否是允许的MIME类型
	 * @param extension
	 * @param allowedExtension
	 * @return
	 */
	public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件名的后缀
	 * @param file 表单文件
	 * @return 后缀名
	 */
	public static final String getExtension(MultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (StringUtils.isEmpty(extension)) {
			extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
		}
		return extension;
	}

	// ====上传普通文件================================================================================================================================

	/**
	 * 通过url下载文件到服务器上，并返回可以访问的url
	 * @param fileDownloadUrl
	 * @return
	 */
	public static HashMap<String,String> downloadFileByUrl(String fileDownloadUrl, int timeoutMs)
			throws IOException, FileSizeLimitExceededException, InvalidExtensionException {
		HashMap<String, String> hashMap = new HashMap<>();
		String fileName = FileUtil.getName(fileDownloadUrl);
		// 服务器上存放文件的根目录
		String serverUploadDir = RuoYiConfig.getUploadPath();
		// 获取当前文件保存到服务器上的具体路径和名称
		String filePathAndName = getFilePathAndName(fileName);
		// 获取文件的完整绝对路径
		String absPath = getAbsoluteFile(serverUploadDir, filePathAndName).getAbsolutePath();
		// 创建文件对象
		File file = new File(absPath);

		// 下载文件并保存到指定路径
		HttpUtil.downloadFile(fileDownloadUrl, file, timeoutMs);

		// 获取返回给前端的文件请求url，如：/profile/upload/2024/09/13/3999/a.mp3
		String fileUrl = getFileUrl(serverUploadDir, filePathAndName);
		hashMap.put("fileUrl",fileUrl);
		hashMap.put("absPath",absPath);
		return hashMap;
	}

	/**
	 * 通过url上传文件
	 * @param fileUrl
	 * @param allowedExtensions
	 * @return
	 */
	public static String uploadFileByUrl(String fileUrl)
			throws IOException, FileSizeLimitExceededException, InvalidExtensionException {
		File tempFile = null;
		String retPath = "";
		try {
			// Download file from URL
			tempFile = downloadFile(fileUrl);

			// Perform file upload and get return path
			retPath = uploadCommFile(tempFile);

		} finally {
			// Ensure the temporary file is deleted
			if (tempFile != null && tempFile.exists()) {
				try {
					Files.delete(tempFile.toPath());
				} catch (IOException e) {
					System.err.println("Failed to delete temporary file: " + e.getMessage());
				}
			}
		}
		return retPath;
	}

	private static File downloadFile(String fileUrl) throws IOException {
		URL url = new URL(fileUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);

		// Extract the filename from the URL
		String fileName = Paths.get(url.getPath()).getFileName().toString();
		if (fileName.isEmpty()) {
			fileName = "default-file-name.tmp"; // Fallback filename
		}

		// Create a temporary file with the extracted filename
		Path tempFilePath = Files.createTempFile("upload-", "-" + fileName);
		logger.info("[获取]-临时文件路径={}", tempFilePath);
		try (InputStream inputStream = connection.getInputStream()) {
			Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
		} finally {
			connection.disconnect(); // Ensure the connection is closed
		}

		return tempFilePath.toFile();
	}

	/**
	 * 以默认配置进行文件上传
	 * @param file 上传的文件
	 * @return 文件名称
	 */
	public static final String uploadCommFile(File file) throws IOException {
		try {
			return uploadCommFile(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 根据文件路径上传
	 * @param baseDir 相对应用的基目录
	 * @param file    上传的文件
	 * @return 文件名称
	 */
	public static final String uploadCommFile(String baseDir, File file) throws IOException {
		try {
			return uploadCommFile(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	public static final String uploadCommFile(String baseDir, File file, String[] allowedExtension)
			throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
			       InvalidExtensionException {
		String fileName = file.getName();
		int fileNamelength = fileName.length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
		}

		assertCommFileAllowed(file, allowedExtension); // 你需要定义处理 File 的方法

		String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
		Files.copy(file.toPath(), Paths.get(absPath)); // 使用 Files.copy 代替 transferTo

		return getFileUrl(baseDir, fileName);
	}

	public static final void assertCommFileAllowed(File file, String[] allowedExtension)
			throws FileSizeLimitExceededException, InvalidExtensionException, IOException {
		// Check file size
		long size = Files.size(file.toPath());
		if (size > FileUploadUtils.DEFAULT_MAX_SIZE) {
			throw new FileSizeLimitExceededException(FileUploadUtils.DEFAULT_MAX_SIZE / 1024 / 1024);
		}

		// Get file name and extension
		String fileName = file.getName();
		String extension = getExtension(file);

		allowedExtensionCheck(allowedExtension, fileName, extension);
	}

	// Utility method to extract file extension
	private static String getExtension(File file) {
		String fileName = file.getName();
		int lastDotIndex = fileName.lastIndexOf('.');
		return (lastDotIndex > 0) ? fileName.substring(lastDotIndex + 1).toLowerCase() : "";
	}

}
