package com.ruoyi.system.service;

import com.ruoyi.system.domain.BenchmarkStandard;
import com.ruoyi.system.domain.vo.BenchmarkStandardVo;
import com.ruoyi.system.domain.bo.BenchmarkStandardBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 标杆标准Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface IBenchmarkStandardService extends IService<BenchmarkStandard> {

    /**
     * 查询标杆标准
     *
     * @param id 主键
     * @return 标杆标准
     */
    BenchmarkStandardVo queryById(Long id);

    /**
     * 查询标杆标准列表
     *
     * @param bo 标杆标准
     * @return 标杆标准集合
     */
    List<BenchmarkStandardVo> queryList(BenchmarkStandardBo bo);

    /**
     * 查询标杆标准列表（分页）
     *
     * @param bo 标杆标准
     * @return 标杆标准分页集合
     */
    TableDataInfo<BenchmarkStandardVo> queryPageList(BenchmarkStandardBo bo, PageQuery pageQuery);

    /**
     * 新增标杆标准
     *
     * @param bo 标杆标准
     * @return 结果
     */
    Boolean insertByBo(BenchmarkStandardBo bo);

    /**
     * 修改标杆标准
     *
     * @param bo 标杆标准
     * @return 结果
     */
    Boolean updateByBo(BenchmarkStandardBo bo);

    /**
     * 校验并批量删除标杆标准信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询标准类型统计
     *
     * @return 标准类型统计列表
     */
    List<Map<String, Object>> getStandardStatistics();

    /**
     * 启用标准
     *
     * @param standardId 标准ID
     * @return 结果
     */
    Boolean activateStandard(Long standardId);

    /**
     * 停用标准
     *
     * @param standardId 标准ID
     * @return 结果
     */
    Boolean deactivateStandard(Long standardId);

    /**
     * 获取有效标准
     *
     * @return 有效标准列表
     */
    List<BenchmarkStandardVo> getActiveStandards();
}
