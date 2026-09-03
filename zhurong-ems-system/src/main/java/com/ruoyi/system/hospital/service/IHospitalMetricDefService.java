package com.ruoyi.system.hospital.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalMetricDefBo;
import com.ruoyi.system.hospital.vo.HospitalMetricDefVo;

import java.util.Collection;
import java.util.List;

/**
 * 医院设备指标定义 Service
 *
 * @author cpems
 */
public interface IHospitalMetricDefService {

    /**
     * 查询指标定义
     *
     * @param id 主键
     * @return 指标定义
     */
    HospitalMetricDefVo queryById(Long id);

    /**
     * 分页查询指标定义
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    TableDataInfo<HospitalMetricDefVo> queryPageList(HospitalMetricDefBo bo, PageQuery pageQuery);

    /**
     * 查询指标定义列表
     *
     * @param bo 查询条件
     * @return 指标列表
     */
    List<HospitalMetricDefVo> queryList(HospitalMetricDefBo bo);

    /**
     * 新增指标
     *
     * @param bo 指标信息
     * @return 是否成功
     */
    Boolean insertByBo(HospitalMetricDefBo bo);

    /**
     * 修改指标
     *
     * @param bo 指标信息
     * @return 是否成功
     */
    Boolean updateByBo(HospitalMetricDefBo bo);

    /**
     * 批量删除指标
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids);
}
