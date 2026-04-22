package com.ruoyi.common.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StreamUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.handler.dictData.DictDataFactory;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用请求处理
 * @author ruoyi
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ISysDictTypeService dictTypeService;
    private static final String FILE_DELIMETER = ",";
    @Autowired
    private DictDataFactory dictDataFactory;
    @Autowired
    private ISysUserService userService;

    /**
     * 通过字典类型获取字典数据
     * @param dictType
     * @return
     */
    @GetMapping("/getDictDataByType/{dictType}")
    public AjaxResult getDictDataByType(@PathVariable String dictType) {
        log.info("【获取】-传入的代码表类型=" + dictType);

        // 判断传入的字典类型判断是否走自定义的字典数据获取方式
        List<SysDictData> data = dictDataFactory.getDictData(dictType, null);
        if (null == data || data.size() <= 0) {
            data = dictTypeService.selectDictDataByType(dictType);
        }
        return AjaxResult.success(data);
    }

    /**
     * 通过字典类型和参数获取字典数据
     * @param dictType
     * @param params
     * @return
     */
    @GetMapping("/getDictDataByType/{dictType}/params")
    public AjaxResult getDictDataByType(@PathVariable String dictType, @RequestParam Map<String, Object> params) {
        log.info("【获取】-传入的代码表类型=" + dictType + "，参数=" + params);

        // 判断传入的字典类型判断是否走自定义的字典数据获取方式
        List<SysDictData> data = dictDataFactory.getDictData(dictType, params);
        if (null == data || data.size() <= 0) {
            data = dictTypeService.selectDictDataByType(dictType);
        }
        return AjaxResult.success(data);
    }

    /**
     * 通过字典类型获取字典数据
     * @param sysDictType
     * @return
     */
    @PostMapping("/getDictDataByType")
    public AjaxResult getDictDataByType(@RequestBody SysDictType sysDictType) {
        log.info("【获取】-传入的代码表类型=" + sysDictType.getDictType());

        // 判断传入的字典类型判断是否走自定义的字典数据获取方式
        List<SysDictData> data = dictDataFactory.getDictData(sysDictType.getDictType(), null);
        if (null == data || data.size() <= 0) {
            data = dictTypeService.selectDictDataByType(sysDictType.getDictType());
        }
        return AjaxResult.success(data);
    }

    /**
     * 通过字典类型和参数获取字典数据
     * @param requestMap 包含dictType和params的Map
     * @return
     */
    @PostMapping("/getDictDataByTypeWithParams")
    public AjaxResult getDictDataByTypeWithParams(@RequestBody Map<String, Object> requestMap) {
        String dictType = (String) requestMap.get("dictType");
        Map<String, Object> params = (Map<String, Object>) requestMap.get("params");
        log.info("【获取】-传入的代码表类型=" + dictType + "，参数=" + params);

        // 判断传入的字典类型判断是否走自定义的字典数据获取方式
        List<SysDictData> data = dictDataFactory.getDictData(dictType, params);
        if (null == data || data.size() <= 0) {
            data = dictTypeService.selectDictDataByType(dictType);
        }
        return AjaxResult.success(data);
    }

    /**
     * 通用下载请求
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    // @PostMapping("/upload")
    // public AjaxResult uploadFile(MultipartFile file) throws Exception {
    // 	try {
    // 		// 上传文件路径
    // 		String filePath = RuoYiConfig.getUploadPath();
    // 		// 上传并返回新文件名称
    // 		String fileName = FileUploadUtils.upload(filePath, file);
    // 		String url = serverConfig.getUrl() + fileName;
    // 		AjaxResult ajax = AjaxResult.success();
    // 		ajax.put("url", url);
    // 		ajax.put("fileName", fileName);
    // 		ajax.put("newFileName", FileUtils.getName(fileName));
    // 		ajax.put("originalFilename", file.getOriginalFilename());
    // 		return ajax;
    // 	} catch (Exception e) {
    // 		return AjaxResult.error(e.getMessage());
    // 	}
    // }

    /**
     * 通用上传请求（多个）
     */
    // @PostMapping("/uploads")
    // public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception {
    // 	try {
    // 		// 上传文件路径
    // 		String filePath = RuoYiConfig.getUploadPath();
    // 		List<String> urls = new ArrayList<String>();
    // 		List<String> fileNames = new ArrayList<String>();
    // 		List<String> newFileNames = new ArrayList<String>();
    // 		List<String> originalFilenames = new ArrayList<String>();
    // 		for (MultipartFile file : files) {
    // 			// 上传并返回新文件名称
    // 			String fileName = FileUploadUtils.upload(filePath, file);
    // 			String url = serverConfig.getUrl() + fileName;
    // 			urls.add(url);
    // 			fileNames.add(fileName);
    // 			newFileNames.add(FileUtils.getName(fileName));
    // 			originalFilenames.add(file.getOriginalFilename());
    // 		}
    // 		AjaxResult ajax = AjaxResult.success();
    // 		ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
    // 		ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
    // 		ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
    // 		ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
    // 		return ajax;
    // 	} catch (Exception e) {
    // 		return AjaxResult.error(e.getMessage());
    // 	}
    // }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 根据用户编号获取基本信息
     * @param userId 用户ID
     */
    @GetMapping(value = {"/getUserSimpleInfo/{userId}"})
    public R<Map<String, Object>> getUserSimpleInfo(@PathVariable(value = "userId", required = false) Long userId) {

        Map<String, Object> ajax = new HashMap<>();
        if (ObjectUtil.isNotNull(userId)) {
            SysUser sysUser = userService.selectUserById(userId);
            SysUser sysUser2 = new SysUser();
            sysUser2.setUserId(sysUser.getUserId());
            sysUser2.setUserName(sysUser.getUserName());
            sysUser2.setNickName(sysUser.getNickName());
            sysUser2.setAvatar(sysUser.getAvatar());
            sysUser2.setEmail(sysUser.getEmail());
            sysUser2.setPhonenumber(sysUser.getPhonenumber());
            sysUser2.setSex(sysUser.getSex());
            ajax.put("user", sysUser2);
        }
        return R.ok(ajax);
    }

      /**
     * 修改用户
     */
    @PutMapping("updateUserSimpleInfo")
    public R<Void> updateUserSimpleInfo(@RequestBody SysUser user) {
        // userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.updateUserSimpleInfo(user));
    }


}
