package com.ruoyi.common.utils;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.sql.SqlUtil;

/**
 * 分页工具类
 *
 * @author ruoyi
 */
public class PageUtils extends PageHelper
{
    // /**
    //  * 设置请求分页数据
    //  */
    // public static void startPage()
    // {
    //     PageDomain pageDomain = TableSupport.buildPageRequest();
    //     Integer pageNum = pageDomain.getPageNum();
    //     Integer pageSize = pageDomain.getPageSize();
    //     String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
    //     Boolean reasonable = false;
    //     // Boolean reasonable = pageDomain.getReasonable();
    //     PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    // }
    /**
     * 设置请求分页数据 - 可以传入是否查询count，对于大表不进行count查询，否则长时间无响应
     * add Dongbei 2024-12-13
     */
    public static void startPage(boolean runCount)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = false;
        // Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable).setCount(runCount);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
