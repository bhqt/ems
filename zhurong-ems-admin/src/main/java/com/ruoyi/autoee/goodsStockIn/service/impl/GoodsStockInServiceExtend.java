package com.ruoyi.autoee.goodsStockIn.service.impl;

import java.util.*;
import javax.annotation.PostConstruct;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.autoee.goodsInfo.domain.GoodsInfo;
import com.ruoyi.autoee.goodsInfo.mapper.GoodsInfoMapper;
import com.ruoyi.autoee.goodsInfo.mapper.GoodsInfoMapperExtend;
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapper;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapperExtend;
import com.ruoyi.common.utils.RedisCacheUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysDeptService;

import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.autoee.goodsStockIn.mapper.GoodsStockInMapper;
import com.ruoyi.autoee.goodsStockIn.mapper.GoodsStockInMapperExtend;
import com.ruoyi.autoee.goodsStockIn.domain.GoodsStockIn;
import com.ruoyi.autoee.goodsStockIn.service.IGoodsStockInService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品入库记录Service业务层处理
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class GoodsStockInServiceExtend {
    private static final Logger logger = LoggerFactory.getLogger(GoodsStockInServiceExtend.class);
    @Autowired
    private GoodsStockInMapper goodsStockInMapper;
    @Autowired
    private GoodsStockInMapperExtend goodsStockInMapperExtend;
    @Autowired
    private ISysDictDataService iSysDictDataService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysDeptService iSysDeptService;
    @Autowired
    private GoodsInventoryMapper goodsInventoryMapper;
    @Autowired
    private GoodsInventoryMapperExtend goodsInventoryMapperExtend;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private GoodsInfoMapperExtend goodsInfoMapperExtend;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
    }

    /**
     * 通过唯一字段查询物品入库记录一条详细信息
     */
    public GoodsStockIn selectOneDataByCache(GoodsStockIn goodsStockIn) {
        GoodsStockIn goodsStockInR = new GoodsStockIn();
        if (null != goodsStockInR) {
            return goodsStockInR;
        } else {
            List<GoodsStockIn> goodsStockInList = goodsStockInMapper.selectDetailListByLikeGoodsStockIn(new GoodsStockIn());
            if (goodsStockInList.size() == 1) {
                goodsStockInR = goodsStockInList.get(0);
                RedisCacheUtils.setMapValue("DataCache_Map_goodsStockIn", String.valueOf(goodsStockIn.getId()), JSONUtil.toJsonStr(goodsStockInR));
                return goodsStockInR;
            } else {
                throw new ServiceException("查询信息结果不唯一！");
            }
        }
    }

    /**
     * 查询列表Start扩展
     */
    public void selectListStartExtend(GoodsStockIn goodsStockIn) {

    }

    /**
     * 查询列表End扩展
     */
    public void selectListEndExtend(GoodsStockIn goodsStockIn, List<GoodsStockIn> list) {

    }

    /**
     * 查询详细列表Start扩展
     */
    public void selectDetailListStartExtend(GoodsStockIn goodsStockIn) {

    }

    /**
     * 查询详细列表End扩展
     */
    public void selectDetailListEndExtend(GoodsStockIn goodsStockIn, List<GoodsStockIn> list) {
        changSelfDefineDict(goodsStockIn, list);
    }

    /**
     * 查询详细列表Start扩展
     */
    public void selectDetailListByEqGoodsStockInStartExtend(GoodsStockIn goodsStockIn) {

    }

    /**
     * 查询详细列表End扩展
     */
    public void selectDetailListByEqGoodsStockInEndExtend(GoodsStockIn goodsStockIn, List<GoodsStockIn> list) {
        changSelfDefineDict(goodsStockIn, list);
    }

    /**
     * 查询明细End扩展
     */
    public void selectDetailByPkGoodsStockInEndExtend(GoodsStockIn goodsStockIn) {
        List<GoodsStockIn> list = new ArrayList();
        list.add(goodsStockIn);
        changSelfDefineDict(goodsStockIn, list);
        goodsStockIn = list.get(0);
    }

    /**
     * 查询导出列表Start扩展
     */
    public void selectExportDetailListStartExtend(GoodsStockIn goodsStockIn) {

    }

    /**
     * 查询导出列表End扩展
     */
    public void selectExportDetailListEndExtend(GoodsStockIn goodsStockIn, List<GoodsStockIn> list) {
        changSelfDefineDict(goodsStockIn, list);
    }

    /**
     * 处理不能通过sql进行转码的下拉框
     */
    public void changSelfDefineDict(GoodsStockIn goodsStockIn, List<GoodsStockIn> list) {
        //HashMap<String, String> goodsIdMap = goodsIdMap(goodsStockIn);
        //HashMap<String, String> userIdMap = userIdMap(goodsStockIn);
        //HashMap<String, String> deptIdMap = deptIdMap(goodsStockIn);
        //list.forEach(e -> {
        //e.setGoodsIdExtend(goodsIdMap.get(e.getGoodsId() + ""));
        //e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
        //e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
        //});
    }

    /**
     * 校验数据唯一性
     */
    public void checkDataUnique(GoodsStockIn goodsStockIn, String flag) {
        GoodsStockIn qGoodsStockIn = new GoodsStockIn();

    }

    /**
     * 新增Start扩展
     */
    public void insertStartExtend(GoodsStockIn goodsStockIn) {
        checkDataUnique(goodsStockIn, "insert");
    }

    /**
     * 新增End扩展
     * 入库新增时如果没有对应的库存信息，则新增一条库存，如果存在对应的物品库存则修改库存数量
     */
    public void insertEndExtend(GoodsStockIn goodsStockIn, int rows) {
        // 根据物品ID查询库存信息
        GoodsInventory queryInventory = new GoodsInventory();
        queryInventory.setGoodsId(goodsStockIn.getGoodsId());
        List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

        if (inventoryList != null && !inventoryList.isEmpty()) {
            // 库存已存在，更新库存数量
            GoodsInventory updateInventory = inventoryList.get(0);
            Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
            updateInventory.setCurrentStock(currentStock + goodsStockIn.getQuantity());
            updateInventory.setUpdateBy(goodsStockIn.getUpdateBy());
            updateInventory.setUpdateTime(new Date());
            goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
        } else {
            GoodsInfo goodsInfo = goodsInfoMapper.selectDetailByPkGoodsInfo(goodsStockIn.getGoodsId());
            if (null == goodsInfo) {
                throw new ServiceException("当前入库的物品，没有对应的物品信息！请确认后再进行操作。");
            }

            // 库存不存在，新增库存记录
            GoodsInventory newInventory = new GoodsInventory();
            newInventory.setGoodsId(goodsStockIn.getGoodsId());
            newInventory.setCurrentStock(goodsStockIn.getQuantity());
            newInventory.setGoodsType(goodsInfo.getGoodsType());
            newInventory.setGoodsUnit(goodsInfo.getGoodsUnit());
            newInventory.setSpecification(goodsInfo.getSpecification());
            newInventory.setUserId(goodsStockIn.getUserId());
            newInventory.setDeptId(goodsStockIn.getDeptId());
            newInventory.setCreateBy(goodsStockIn.getCreateBy());
            newInventory.setCreateTime(new Date());
            newInventory.setUpdateBy(goodsStockIn.getUpdateBy());
            newInventory.setUpdateTime(new Date());
            goodsInventoryMapper.insert(newInventory);
        }
    }

    /**
     * 修改Start扩展
     */
    public void updateStartExtend(GoodsStockIn goodsStockIn, GoodsStockIn oldGoodsStockIn) {
        checkDataUnique(goodsStockIn, "update");
    }

    /**
     * 修改End扩展
     * 修改入库记录时，调整库存数量
     */
    public void updateEndExtend(GoodsStockIn goodsStockIn, GoodsStockIn oldGoodsStockIn, int rows) {
        // 查询库存信息
        GoodsInventory queryInventory = new GoodsInventory();
        queryInventory.setGoodsId(goodsStockIn.getGoodsId());
        List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

        if (inventoryList != null && !inventoryList.isEmpty()) {
            GoodsInventory updateInventory = inventoryList.get(0);
            // 计算库存变化量
            Integer oldQuantity = oldGoodsStockIn.getQuantity() != null ? oldGoodsStockIn.getQuantity() : 0;
            Integer newQuantity = goodsStockIn.getQuantity() != null ? goodsStockIn.getQuantity() : 0;
            Integer changeQuantity = newQuantity - oldQuantity;

            // 更新库存数量
            Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
            updateInventory.setCurrentStock(currentStock + changeQuantity);
            updateInventory.setUpdateBy(goodsStockIn.getUpdateBy());
            updateInventory.setUpdateTime(new Date());
            goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
        }
    }

    /**
     * 批量删除Start扩展
     */
    public void deleteByIdsStartExtend(GoodsStockIn goodsStockIn) {
        for (String id : goodsStockIn.getIds()) {
            //GoodsStockIn tGoodsStockIn = goodsStockInMapper.selectDataByPkGoodsStockIn(id);
        }
    }

    /**
     * 批量删除End扩展
     * 删除入库记录时，扣减库存数量
     */
    public void deleteByIdsEndExtend(GoodsStockIn goodsStockIn, int rows) {
        // 遍历所有要删除的入库记录
        for (String id : goodsStockIn.getIds()) {
            // 查询入库记录
            GoodsStockIn stockIn = goodsStockInMapper.selectDataByPkGoodsStockIn(Long.parseLong(id));
            if (stockIn != null) {
                // 查询对应的库存信息
                GoodsInventory queryInventory = new GoodsInventory();
                queryInventory.setGoodsId(stockIn.getGoodsId());
                List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

                if (inventoryList != null && !inventoryList.isEmpty()) {
                    // 扣减库存数量
                    GoodsInventory updateInventory = inventoryList.get(0);
                    Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
                    Integer newStock = currentStock - stockIn.getQuantity();
                    // 确保库存不会变为负数
                    updateInventory.setCurrentStock(Math.max(0, newStock));
                    updateInventory.setUpdateBy(goodsStockIn.getUpdateBy());
                    updateInventory.setUpdateTime(new Date());
                    goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
                }
            }
        }
    }

    /**
     * 批量删除ByEqGoodsStockInStart扩展
     */
    public void deleteByEqGoodsStockInStartExtend(GoodsStockIn goodsStockIn) {
    }

    /**
     * 批量删除ByEqGoodsStockInEnd扩展
     * 通过条件删除入库记录时，扣减库存数量
     */
    public void deleteByEqGoodsStockInEndExtend(GoodsStockIn goodsStockIn, int rows) {
        // 根据条件查询要删除的入库记录
        List<GoodsStockIn> stockInList = goodsStockInMapper.selectDataListByEqGoodsStockIn(goodsStockIn);

        // 遍历所有要删除的入库记录
        for (GoodsStockIn stockIn : stockInList) {
            // 查询对应的库存信息
            GoodsInventory queryInventory = new GoodsInventory();
            queryInventory.setGoodsId(stockIn.getGoodsId());
            List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

            if (inventoryList != null && !inventoryList.isEmpty()) {
                // 扣减库存数量
                GoodsInventory updateInventory = inventoryList.get(0);
                Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
                Integer newStock = currentStock - stockIn.getQuantity();
                // 确保库存不会变为负数
                updateInventory.setCurrentStock(Math.max(0, newStock));
                updateInventory.setUpdateBy(goodsStockIn.getUpdateBy());
                updateInventory.setUpdateTime(new Date());
                goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
            }
        }
    }

    /**
     * 导出前校验扩展
     */
    public void exportDataCheckExtend(GoodsStockIn goodsStockIn) {
        int count = goodsStockInMapper.selectCountByLikeGoodsStockIn(goodsStockIn);
        // 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
        int limitCount = 50000;
        if (count > limitCount) {
            throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
        }
    }

    /**
     * 导出数据预处理扩展
     */
    public void exportDataDealExtend(GoodsStockIn goodsStockIn, List<GoodsStockIn> list) {

    }

    /**
     * 导入开始扩展
     */
    public void importDataStartExtend(List<GoodsStockIn> dataList, GoodsStockIn pGoodsStockIn, String operName, Date operDate) {
        dataList.forEach(data -> {
            data.setCreateTime(operDate);
            data.setCreateBy(operName);
            data.setUpdateTime(operDate);
            data.setUpdateBy(operName);
        });
    }

    /**
     * 导入校验数据扩展
     */
    public String importDataCheckExtend(boolean isUpdateSupport, GoodsStockIn pGoodsStockIn, GoodsStockIn goodsStockIn, String operName) {
        String checkMsg = "";
        if (isUpdateSupport) {

        } else {
            //  如果当前不进行更新数据，当前数据不存在，则直接新增
            try {
                checkDataUnique(goodsStockIn, "insert");
            } catch (Exception e) {
                checkMsg = e.getMessage();
            }
        }

        return checkMsg;
    }

    /**
     * 导入完成扩展
     */
    public void importDataEndExtend(List<GoodsStockIn> dataList, GoodsStockIn pGoodsStockIn, String operName, int successNum) {

    }

    /**
     * 检查是否可以修改
     */
    public void checkEditExtend(GoodsStockIn pGoodsStockIn) {
        //        if(pGoodsStockIn.getXXX().equals("")) {
        //            throw new ServiceException("当前选中数据不可修改！");
        //        }
    }

}
