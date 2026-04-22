package com.ruoyi.system.service;

import com.ruoyi.system.domain.EnergyBalance;
import com.ruoyi.system.domain.vo.EnergyBalanceVo;
import com.ruoyi.system.domain.bo.EnergyBalanceBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 能源平衡Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface IEnergyBalanceService extends IService<EnergyBalance> {

    /**
     * 查询能源平衡
     *
     * @param id 主键
     * @return 能源平衡
     */
    EnergyBalanceVo queryById(Long id);

    /**
     * 查询能源平衡列表
     *
     * @param bo 能源平衡
     * @return 能源平衡集合
     */
    List<EnergyBalanceVo> queryList(EnergyBalanceBo bo);

    /**
     * 查询能源平衡列表（分页）
     *
     * @param bo 能源平衡
     * @return 能源平衡分页集合
     */
    TableDataInfo<EnergyBalanceVo> queryPageList(EnergyBalanceBo bo, PageQuery pageQuery);

    /**
     * 新增能源平衡
     *
     * @param bo 能源平衡
     * @return 结果
     */
    Boolean insertByBo(EnergyBalanceBo bo);

    /**
     * 修改能源平衡
     *
     * @param bo 能源平衡
     * @return 结果
     */
    Boolean updateByBo(EnergyBalanceBo bo);

    /**
     * 校验并批量删除能源平衡信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 计算能源平衡
     *
     * @param bo 能源平衡
     * @return 计算后的能源平衡
     */
    EnergyBalance calculateBalance(EnergyBalanceBo bo);

    /**
     * 查询能源介质统计
     *
     * @return 能源介质统计列表
     */
    List<Map<String, Object>> getMediumStatistics();

    /**
     * 查询平衡状态统计
     *
     * @return 平衡状态统计列表
     */
    List<Map<String, Object>> getStatusStatistics();
}
