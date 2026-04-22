package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Merchant;
import com.ruoyi.system.mapper.MerchantMapper;
import com.ruoyi.system.service.IMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.MerchantBo;
import com.ruoyi.system.domain.vo.MerchantVo;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-07
 */
@RequiredArgsConstructor
@Service
public class MerchantServiceImpl implements IMerchantService {

    private final MerchantMapper baseMapper;

    /**
     * 查询商户信息
     */
    @Override
    public MerchantVo queryById(Long merchantId){
        return baseMapper.selectVoById(merchantId);
    }

    /**
     * 查询商户信息列表
     */
    @Override
    public TableDataInfo<MerchantVo> queryPageList(MerchantBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Merchant> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(Merchant::getUpdateTime);
        Page<MerchantVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询商户信息列表
     */
    @Override
    public List<MerchantVo> queryList(MerchantBo bo) {
        LambdaQueryWrapper<Merchant> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(Merchant::getUpdateTime);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Merchant> buildQueryWrapper(MerchantBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Merchant> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Merchant::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), Merchant::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getContact()), Merchant::getContact, bo.getContact());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), Merchant::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Merchant::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增商户信息
     */
    @Override
    public Boolean insertByBo(MerchantBo bo) {
        Merchant add = BeanUtil.toBean(bo, Merchant.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMerchantId(add.getMerchantId());
        }
        return flag;
    }

    /**
     * 修改商户信息
     */
    @Override
    public Boolean updateByBo(MerchantBo bo) {
        Merchant update = BeanUtil.toBean(bo, Merchant.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Merchant entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除商户信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
