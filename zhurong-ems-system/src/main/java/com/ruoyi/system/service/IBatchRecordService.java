package com.ruoyi.system.service;

import com.ruoyi.system.domain.BatchRecord;
import com.ruoyi.system.domain.vo.BatchRecordVo;
import com.ruoyi.system.domain.bo.BatchRecordBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 批次实绩Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface IBatchRecordService extends IService<BatchRecord> {

    /**
     * 查询批次实绩
     *
     * @param id 主键
     * @return 批次实绩
     */
    BatchRecordVo queryById(Long id);

    /**
     * 查询批次实绩列表
     *
     * @param bo 批次实绩
     * @return 批次实绩集合
     */
    List<BatchRecordVo> queryList(BatchRecordBo bo);

    /**
     * 查询批次实绩列表（分页）
     *
     * @param bo 批次实绩
     * @return 批次实绩分页集合
     */
    TableDataInfo<BatchRecordVo> queryPageList(BatchRecordBo bo, PageQuery pageQuery);

    /**
     * 新增批次实绩
     *
     * @param bo 批次实绩
     * @return 结果
     */
    Boolean insertByBo(BatchRecordBo bo);

    /**
     * 修改批次实绩
     *
     * @param bo 批次实绩
     * @return 结果
     */
    Boolean updateByBo(BatchRecordBo bo);

    /**
     * 校验并批量删除批次实绩信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询批次状态统计
     *
     * @return 批次状态统计列表
     */
    List<Map<String, Object>> getBatchStatistics();

    /**
     * 完成批次
     *
     * @param batchId 批次ID
     * @return 结果
     */
    Boolean completeBatch(Long batchId);

    /**
     * 取消批次
     *
     * @param batchId 批次ID
     * @return 结果
     */
    Boolean cancelBatch(Long batchId);

    /**
     * 计算批次能耗指标
     *
     * @param batchId 批次ID
     * @return 能耗指标
     */
    Map<String, Object> calculateEnergyIndicators(Long batchId);
}
