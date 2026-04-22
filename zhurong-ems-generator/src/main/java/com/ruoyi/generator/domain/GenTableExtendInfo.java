package com.ruoyi.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2022-7-11 <br>
 * @author Double
 * @version 1.0.0
 */
public class GenTableExtendInfo {
    private String orderByStr= "";
    private String tableDefaultOrderByStr= "";
    // 主页面和主页面列表中隐藏的按钮：add|edit|delete|import|export 等
    private String showButtons= "";
    private String extendInfo= "";
	// 默认查询时间范围，单位：小时
    private String queryDateRange= "";
    private String dataScopeUserAlias= "";
    private String dataScopeDeptAlias= "";
    private String dataScopeStr= "";
    private String addDialogWidth= "";
    private String addDialogHeight= "";
    private String addDialogSpan= "";
    private String detailDialogWidth= "";
    private String detailDialogHeight= "";
    private String detailDialogSpan= "";
    // 当前表关联的主表名称
    private String relaMainTableName= "";
    // 当前表关联的主表主键名称
    private String relaMainTableKey= "";
    // 当前表关联的主表主键实体名称
    private String relaMainTableKeyEntityName= "";
    // 主页面列表中操作列额外添加的按钮
    private List<Button> addPageButtons = new ArrayList<>();
    // 查询数据过滤权限
    private String dataScopeFilter= "";
    // 主页面列表中操作列额外添加的按钮
    private List<Button> gridButton = new ArrayList<>();
    // // 当前表关联的主表的字段
    // private List<RelaMainTableCol> relaMainTableColList = new ArrayList<>();
    // // 当前表关联的子表配置信息
    // private SubTableCongfig subTableCongfig = new SubTableCongfig();

	public String getDataScopeStr() {
		return dataScopeStr;
	}

	public void setDataScopeStr(String dataScopeStr) {
		this.dataScopeStr = dataScopeStr;
	}

	public String getDataScopeDeptAlias() {
		return dataScopeDeptAlias;
	}

	public void setDataScopeDeptAlias(String dataScopeDeptAlias) {
		this.dataScopeDeptAlias = dataScopeDeptAlias;
	}

	public String getDataScopeUserAlias() {
		return dataScopeUserAlias;
	}

	public void setDataScopeUserAlias(String dataScopeUserAlias) {
		this.dataScopeUserAlias = dataScopeUserAlias;
	}

	public String getAddDialogSpan() {
		return addDialogSpan;
	}

	public void setAddDialogSpan(String addDialogSpan) {
		this.addDialogSpan = addDialogSpan;
	}

	public String getDetailDialogSpan() {
		return detailDialogSpan;
	}

	public void setDetailDialogSpan(String detailDialogSpan) {
		this.detailDialogSpan = detailDialogSpan;
	}

	public List<Button> getAddPageButtons() {
        return addPageButtons;
    }

    public void setAddPageButtons(List<Button> addPageButtons) {
        this.addPageButtons = addPageButtons;
    }

    public String getDataScopeFilter() {
        return dataScopeFilter;
    }

    public void setDataScopeFilter(String dataScopeFilter) {
        this.dataScopeFilter = dataScopeFilter;
    }

	public String getQueryDateRange() {
		return queryDateRange;
	}

	public String getTableDefaultOrderByStr() {
		return tableDefaultOrderByStr;
	}

	public void setTableDefaultOrderByStr(String tableDefaultOrderByStr) {
		this.tableDefaultOrderByStr = tableDefaultOrderByStr;
	}

	public void setQueryDateRange(String queryDateRange) {
		this.queryDateRange = queryDateRange;
	}

	public String getOrderByStr() {
        return orderByStr;
    }

    public void setOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
    }

    public String getShowButtons() {
        return showButtons;
    }

    public void setShowButtons(String showButtons) {
        this.showButtons = showButtons;
    }

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getRelaMainTableName() {
        return relaMainTableName;
    }

    public void setRelaMainTableName(String relaMainTableName) {
        this.relaMainTableName = relaMainTableName;
    }

    public String getRelaMainTableKey() {
        return relaMainTableKey;
    }

    public void setRelaMainTableKey(String relaMainTableKey) {
        this.relaMainTableKey = relaMainTableKey;
    }

    public String getRelaMainTableKeyEntityName() {
        return relaMainTableKeyEntityName;
    }

    public void setRelaMainTableKeyEntityName(String relaMainTableKeyEntityName) {
        this.relaMainTableKeyEntityName = relaMainTableKeyEntityName;
    }


    public List<Button> getGridButton() {
        return gridButton;
    }

    public void setGridButton(List<Button> gridButton) {
        this.gridButton = gridButton;
    }

	public String getAddDialogWidth() {
		return addDialogWidth;
	}

	public void setAddDialogWidth(String addDialogWidth) {
		this.addDialogWidth = addDialogWidth;
	}

	public String getAddDialogHeight() {
		return addDialogHeight;
	}

	public void setAddDialogHeight(String addDialogHeight) {
		this.addDialogHeight = addDialogHeight;
	}

	public String getDetailDialogWidth() {
		return detailDialogWidth;
	}

	public void setDetailDialogWidth(String detailDialogWidth) {
		this.detailDialogWidth = detailDialogWidth;
	}

	public String getDetailDialogHeight() {
		return detailDialogHeight;
	}

	public void setDetailDialogHeight(String detailDialogHeight) {
		this.detailDialogHeight = detailDialogHeight;
	}
}
