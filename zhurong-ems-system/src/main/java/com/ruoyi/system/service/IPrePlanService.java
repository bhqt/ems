package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.PrePlanVo;
import com.ruoyi.system.domain.bo.PrePlanBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 预案管理Service接口
 *
 * @author ruoyi
 * @date 2023-09-11
 */
public interface IPrePlanService {

    /**
     * 查询预案管理
     */
    PrePlanVo queryById(Long prePlanId);

    /**
     * 查询预案管理列表
     */
    TableDataInfo<PrePlanVo> queryPageList(PrePlanBo bo, PageQuery pageQuery);

    /**
     * 查询预案管理列表
     */
    List<PrePlanVo> queryList(PrePlanBo bo);

    /**
     * 新增预案管理
     */
    Boolean insertByBo(PrePlanBo bo);

    /**
     * 修改预案管理
     */
    Boolean updateByBo(PrePlanBo bo);

    /**
     * 校验并批量删除预案管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
