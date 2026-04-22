package com.ruoyi.system.service;

import com.ruoyi.system.domain.CalibrationRecord;
import com.ruoyi.system.domain.vo.CalibrationRecordVo;
import com.ruoyi.system.domain.bo.CalibrationRecordBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 校准记录Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface ICalibrationRecordService extends IService<CalibrationRecord> {

    /**
     * 查询校准记录
     *
     * @param id 主键
     * @return 校准记录
     */
    CalibrationRecordVo queryById(Long id);

    /**
     * 查询校准记录列表
     *
     * @param bo 校准记录
     * @return 校准记录集合
     */
    List<CalibrationRecordVo> queryList(CalibrationRecordBo bo);

    /**
     * 查询校准记录列表（分页）
     *
     * @param bo 校准记录
     * @return 校准记录分页集合
     */
    TableDataInfo<CalibrationRecordVo> queryPageList(CalibrationRecordBo bo, PageQuery pageQuery);

    /**
     * 新增校准记录
     *
     * @param bo 校准记录
     * @return 结果
     */
    Boolean insertByBo(CalibrationRecordBo bo);

    /**
     * 修改校准记录
     *
     * @param bo 校准记录
     * @return 结果
     */
    Boolean updateByBo(CalibrationRecordBo bo);

    /**
     * 校验并批量删除校准记录信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询校准结果统计
     *
     * @return 校准结果统计列表
     */
    List<Map<String, Object>> getCalibrationStatistics();

    /**
     * 查询器具的校准历史
     *
     * @param meterId 器具ID
     * @return 校准历史列表
     */
    List<CalibrationRecordVo> getMeterCalibrationHistory(Long meterId);
}
