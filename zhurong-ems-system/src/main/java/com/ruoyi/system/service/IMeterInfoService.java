package com.ruoyi.system.service;

import com.ruoyi.system.domain.MeterInfo;
import com.ruoyi.system.domain.vo.MeterInfoVo;
import com.ruoyi.system.domain.bo.MeterInfoBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 计量器具信息Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface IMeterInfoService extends IService<MeterInfo> {

    /**
     * 查询计量器具信息
     *
     * @param id 主键
     * @return 计量器具信息
     */
    MeterInfoVo queryById(Long id);

    /**
     * 查询计量器具信息列表
     *
     * @param bo 计量器具信息
     * @return 计量器具信息集合
     */
    List<MeterInfoVo> queryList(MeterInfoBo bo);

    /**
     * 查询计量器具信息列表（分页）
     *
     * @param bo 计量器具信息
     * @return 计量器具信息分页集合
     */
    TableDataInfo<MeterInfoVo> queryPageList(MeterInfoBo bo, PageQuery pageQuery);

    /**
     * 新增计量器具信息
     *
     * @param bo 计量器具信息
     * @return 结果
     */
    Boolean insertByBo(MeterInfoBo bo);

    /**
     * 修改计量器具信息
     *
     * @param bo 计量器具信息
     * @return 结果
     */
    Boolean updateByBo(MeterInfoBo bo);

    /**
     * 校验并批量删除计量器具信息信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询器具状态统计
     *
     * @return 器具状态统计列表
     */
    List<Map<String, Object>> getMeterStatistics();

    /**
     * 查询需要校准的器具列表
     *
     * @return 需要校准的器具列表
     */
    List<MeterInfoVo> getNeedCalibrationList();
}
