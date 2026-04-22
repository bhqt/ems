package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.AttachmentInfoVo;
import com.ruoyi.system.domain.bo.AttachmentInfoBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 备件库管理Service接口
 *
 * @author ruoyi
 * @date 2023-09-11
 */
public interface IAttachmentInfoService {

    /**
     * 查询备件库管理
     */
    AttachmentInfoVo queryById(Long attachmentId);

    /**
     * 查询备件库管理列表
     */
    TableDataInfo<AttachmentInfoVo> queryPageList(AttachmentInfoBo bo, PageQuery pageQuery);

    /**
     * 查询备件库管理列表
     */
    List<AttachmentInfoVo> queryList(AttachmentInfoBo bo);

    /**
     * 新增备件库管理
     */
    Boolean insertByBo(AttachmentInfoBo bo);

    /**
     * 修改备件库管理
     */
    Boolean updateByBo(AttachmentInfoBo bo);

    /**
     * 校验并批量删除备件库管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}

