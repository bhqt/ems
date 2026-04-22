package com.ruoyi.common.core.page;

import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页数据
 *
 * @author ruoyi
 */
public class PageDomain
{
	private static final Logger logger = LoggerFactory.getLogger(PageDomain.class);
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    /** 分页参数合理化 */
	// 如果设置 reasonable: false，则表示插件不会对分页参数进行任何调整或校正。
	//  reasonable: false 的含义
    // 当 reasonable: false 时，PageHelper不会对分页参数进行任何校验和调整，这意味着：
    //
    // 如果传入的页码小于等于0，则查询结果为空。
    // 如果传入的页码大于总页数，也会返回空结果。
    // 页大小（page size）为任意值，不会被调整或限制。
    // 这种配置方式对于需要完全控制分页参数的应用场景很有用，但也可能导致意外的分页结果，如页码无效时查询不到数据。
    private Boolean reasonable = false;

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
	    String s = StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
		logger.info("[获取]-查询排序字符串=[{}]", s);
	    return s;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn()
    {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn)
    {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc()
    {
        return isAsc;
    }

    public void setIsAsc(String isAsc)
    {
        if (StringUtils.isNotEmpty(isAsc))
        {
            // 兼容前端排序类型
            if ("ascending".equals(isAsc))
            {
                isAsc = "asc";
            }
            else if ("descending".equals(isAsc))
            {
                isAsc = "desc";
            }
            this.isAsc = isAsc;
        }
    }

    public Boolean getReasonable()
    {
        if (StringUtils.isNull(reasonable))
        {
            return Boolean.TRUE;
        }
        return reasonable;
    }

    public void setReasonable(Boolean reasonable)
    {
        this.reasonable = reasonable;
    }
}
