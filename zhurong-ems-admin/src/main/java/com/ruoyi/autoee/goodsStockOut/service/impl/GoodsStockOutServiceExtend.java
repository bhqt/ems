package com.ruoyi.autoee.goodsStockOut.service.impl;

import java.util.*;
import javax.annotation.PostConstruct;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.autoee.goodsInfo.mapper.GoodsInfoMapper;
import com.ruoyi.autoee.goodsInfo.mapper.GoodsInfoMapperExtend;
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapper;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapperExtend;
import com.ruoyi.autoee.goodsStockIn.domain.GoodsStockIn;
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
import com.ruoyi.autoee.goodsStockOut.mapper.GoodsStockOutMapper;
import com.ruoyi.autoee.goodsStockOut.mapper.GoodsStockOutMapperExtend;
import com.ruoyi.autoee.goodsStockOut.domain.GoodsStockOut;
import com.ruoyi.autoee.goodsStockOut.service.IGoodsStockOutService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品出库记录Service业务层处理
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class GoodsStockOutServiceExtend {
    private static final Logger logger = LoggerFactory.getLogger(GoodsStockOutServiceExtend.class);
    @Autowired
    private GoodsStockOutMapper goodsStockOutMapper;
    @Autowired
    private GoodsStockOutMapperExtend goodsStockOutMapperExtend;
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
     * 通过唯一字段查询物品出库记录一条详细信息
     */
    public GoodsStockOut selectOneDataByCache(GoodsStockOut goodsStockOut) {
        GoodsStockOut goodsStockOutR = new GoodsStockOut();
        if (null != goodsStockOutR) {
            return goodsStockOutR;
        } else {
            List<GoodsStockOut> goodsStockOutList = goodsStockOutMapper.selectDetailListByLikeGoodsStockOut(new GoodsStockOut());
            if (goodsStockOutList.size() == 1) {
                goodsStockOutR = goodsStockOutList.get(0);
                RedisCacheUtils.setMapValue("DataCache_Map_goodsStockOut", String.valueOf(goodsStockOut.getId()), JSONUtil.toJsonStr(goodsStockOutR));
                return goodsStockOutR;
            } else {
                throw new ServiceException("查询信息结果不唯一！");
            }
        }
    }

    /**
     * 查询列表Start扩展
     */
    public void selectListStartExtend(GoodsStockOut goodsStockOut) {

    }

    /**
     * 查询列表End扩展
     */
    public void selectListEndExtend(GoodsStockOut goodsStockOut, List<GoodsStockOut> list) {

    }

    /**
     * 查询详细列表Start扩展
     */
    public void selectDetailListStartExtend(GoodsStockOut goodsStockOut) {

    }

    /**
     * 查询详细列表End扩展
     */
    public void selectDetailListEndExtend(GoodsStockOut goodsStockOut, List<GoodsStockOut> list) {
        changSelfDefineDict(goodsStockOut, list);
    }

    /**
     * 查询详细列表Start扩展
     */
    public void selectDetailListByEqGoodsStockOutStartExtend(GoodsStockOut goodsStockOut) {

    }

    /**
     * 查询详细列表End扩展
     */
    public void selectDetailListByEqGoodsStockOutEndExtend(GoodsStockOut goodsStockOut, List<GoodsStockOut> list) {
        changSelfDefineDict(goodsStockOut, list);
    }

    /**
     * 查询明细End扩展
     */
    public void selectDetailByPkGoodsStockOutEndExtend(GoodsStockOut goodsStockOut) {
        List<GoodsStockOut> list = new ArrayList();
        list.add(goodsStockOut);
        changSelfDefineDict(goodsStockOut, list);
        goodsStockOut = list.get(0);
    }

    /**
     * 查询导出列表Start扩展
     */
    public void selectExportDetailListStartExtend(GoodsStockOut goodsStockOut) {

    }

    /**
     * 查询导出列表End扩展
     */
    public void selectExportDetailListEndExtend(GoodsStockOut goodsStockOut, List<GoodsStockOut> list) {
        changSelfDefineDict(goodsStockOut, list);
    }

    /**
     * 处理不能通过sql进行转码的下拉框
     */
    public void changSelfDefineDict(GoodsStockOut goodsStockOut, List<GoodsStockOut> list) {
        //HashMap<String, String> goodsIdMap = goodsIdMap(goodsStockOut);
        //HashMap<String, String> userIdMap = userIdMap(goodsStockOut);
        //HashMap<String, String> deptIdMap = deptIdMap(goodsStockOut);
        //list.forEach(e -> {
        //e.setGoodsIdExtend(goodsIdMap.get(e.getGoodsId() + ""));
        //e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
        //e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
        //});
    }

    /**
     * 校验数据唯一性
     */
    public void checkDataUnique(GoodsStockOut goodsStockOut, String flag) {
        GoodsStockOut qGoodsStockOut = new GoodsStockOut();

    }

    /**
     * 新增Start扩展
     */
    public void insertStartExtend(GoodsStockOut goodsStockOut) {
        checkDataUnique(goodsStockOut, "insert");
    }

    /**
     * 新增End扩展
     * 出库新增时，扣减对应的库存数量
     */
    public void insertEndExtend(GoodsStockOut goodsStockOut, int rows) {
        // 根据物品ID查询库存信息
        GoodsInventory queryInventory = new GoodsInventory();
        queryInventory.setGoodsId(goodsStockOut.getGoodsId());
        List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

        if (inventoryList != null && !inventoryList.isEmpty()) {
            // 检查库存是否足够
            GoodsInventory updateInventory = inventoryList.get(0);
            Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
            Integer outQuantity = goodsStockOut.getQuantity() != null ? goodsStockOut.getQuantity() : 0;

            if (currentStock < outQuantity) {
                throw new ServiceException("物品库存不足，当前库存：" + currentStock + "，请求出库：" + outQuantity);
            }

            // 扣减库存数量
            updateInventory.setCurrentStock(currentStock - outQuantity);
            updateInventory.setUpdateBy(goodsStockOut.getUpdateBy());
            updateInventory.setUpdateTime(new Date());
            goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
        } else {
            throw new ServiceException("物品库存不存在，无法出库");
        }
    }

    /**
     * 修改Start扩展
     */
    public void updateStartExtend(GoodsStockOut goodsStockOut, GoodsStockOut oldGoodsStockOut) {
        checkDataUnique(goodsStockOut, "update");
    }

    /**
     * 修改End扩展
     * 修改出库记录时，调整库存数量
     */
    public void updateEndExtend(GoodsStockOut goodsStockOut, GoodsStockOut oldGoodsStockOut, int rows) {
        // 根据物品ID查询库存信息
        GoodsInventory queryInventory = new GoodsInventory();
        queryInventory.setGoodsId(goodsStockOut.getGoodsId());
        List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

        if (inventoryList != null && !inventoryList.isEmpty()) {
            GoodsInventory updateInventory = inventoryList.get(0);
            // 计算库存变化量
            Integer oldQuantity = oldGoodsStockOut.getQuantity() != null ? oldGoodsStockOut.getQuantity() : 0;
            Integer newQuantity = goodsStockOut.getQuantity() != null ? goodsStockOut.getQuantity() : 0;
            Integer changeQuantity = newQuantity - oldQuantity;

            // 检查库存是否足够（如果是增加出库数量）
            Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
            if (changeQuantity > 0 && currentStock < changeQuantity) {
                throw new ServiceException("物品库存不足，无法增加出库数量");
            }

            // 更新库存数量
            updateInventory.setCurrentStock(currentStock - changeQuantity);
            updateInventory.setUpdateBy(goodsStockOut.getUpdateBy());
            updateInventory.setUpdateTime(new Date());
            goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
        } else {
            throw new ServiceException("物品库存不存在，无法修改出库记录");
        }
    }

    /**
     * 批量删除Start扩展
     */
    public void deleteByIdsStartExtend(GoodsStockOut goodsStockOut) {
        for (String id : goodsStockOut.getIds()) {
            //GoodsStockOut tGoodsStockOut = goodsStockOutMapper.selectDataByPkGoodsStockOut(id);
        }
    }

    /**
     * 批量删除End扩展
     * 删除出库记录时，恢复库存数量
     */
    public void deleteByIdsEndExtend(GoodsStockOut goodsStockOut, int rows) {
        // 遍历所有要删除的出库记录
        for (String id : goodsStockOut.getIds()) {
            // 查询出库记录
            GoodsStockOut stockOut = goodsStockOutMapper.selectDataByPkGoodsStockOut(Long.parseLong(id));
            if (stockOut != null) {
                // 查询对应的库存信息
                GoodsInventory queryInventory = new GoodsInventory();
                queryInventory.setGoodsId(stockOut.getGoodsId());
                List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

                if (inventoryList != null && !inventoryList.isEmpty()) {
                    // 恢复库存数量
                    GoodsInventory updateInventory = inventoryList.get(0);
                    Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
                    Integer outQuantity = stockOut.getQuantity() != null ? stockOut.getQuantity() : 0;
                    updateInventory.setCurrentStock(currentStock + outQuantity);
                    updateInventory.setUpdateBy(goodsStockOut.getUpdateBy());
                    updateInventory.setUpdateTime(new Date());
                    goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
                }
            }
        }
    }

    /**
     * 批量删除ByEqGoodsStockOutStart扩展
     */
    public void deleteByEqGoodsStockOutStartExtend(GoodsStockOut goodsStockOut) {
    }

    /**
     * 批量删除ByEqGoodsStockOutEnd扩展
     * 通过条件删除出库记录时，恢复库存数量
     */
    public void deleteByEqGoodsStockOutEndExtend(GoodsStockOut goodsStockOut, int rows) {
        // 根据条件查询要删除的出库记录
        List<GoodsStockOut> stockOutList = goodsStockOutMapper.selectDataListByEqGoodsStockOut(goodsStockOut);

        // 遍历所有要删除的出库记录
        for (GoodsStockOut stockOut : stockOutList) {
            // 查询对应的库存信息
            GoodsInventory queryInventory = new GoodsInventory();
            queryInventory.setGoodsId(stockOut.getGoodsId());
            List<GoodsInventory> inventoryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryInventory);

            if (inventoryList != null && !inventoryList.isEmpty()) {
                // 恢复库存数量
                GoodsInventory updateInventory = inventoryList.get(0);
                Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
                Integer outQuantity = stockOut.getQuantity() != null ? stockOut.getQuantity() : 0;
                updateInventory.setCurrentStock(currentStock + outQuantity);
                updateInventory.setUpdateBy(goodsStockOut.getUpdateBy());
                updateInventory.setUpdateTime(new Date());
                goodsInventoryMapper.updateNotNullValueByGoodsInventory(updateInventory);
            }
        }
    }

    /**
     * 导出前校验扩展
     */
    public void exportDataCheckExtend(GoodsStockOut goodsStockOut) {
        int count = goodsStockOutMapper.selectCountByLikeGoodsStockOut(goodsStockOut);
        // 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
        int limitCount = 50000;
        if (count > limitCount) {
            throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
        }
    }

    /**
     * 导出数据预处理扩展
     */
    public void exportDataDealExtend(GoodsStockOut goodsStockOut, List<GoodsStockOut> list) {

    }

    /**
     * 导入开始扩展
     */
    public void importDataStartExtend(List<GoodsStockOut> dataList, GoodsStockOut pGoodsStockOut, String operName, Date operDate) {
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
    public String importDataCheckExtend(boolean isUpdateSupport, GoodsStockOut pGoodsStockOut, GoodsStockOut goodsStockOut, String operName) {
        String checkMsg = "";
        if (isUpdateSupport) {

        } else {
            //  如果当前不进行更新数据，当前数据不存在，则直接新增
            try {
                checkDataUnique(goodsStockOut, "insert");
            } catch (Exception e) {
                checkMsg = e.getMessage();
            }
        }

        return checkMsg;
    }

    /**
     * 导入完成扩展
     */
    public void importDataEndExtend(List<GoodsStockOut> dataList, GoodsStockOut pGoodsStockOut, String operName, int successNum) {

    }

    /**
     * 检查是否可以修改
     */
    public void checkEditExtend(GoodsStockOut pGoodsStockOut) {
        //        if(pGoodsStockOut.getXXX().equals("")) {
        //            throw new ServiceException("当前选中数据不可修改！");
        //        }
    }

}
