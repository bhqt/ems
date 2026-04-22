package com.ruoyi.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2022-7-6 <br>
 * @author Double
 * @version 1.0.0
 */
public class SubTableCongfig {

    // 直接打开子表的Tab页面，进行子表数据的管理，不使用原来的在主表编辑页面显示子表的列表形式。
    private String openSubTableTabPage;
    private String openSubTableTabPageButtonTitle;
    private String openSubTableModuleName;
    private String openSubTableBusinessName;
    // 子表页面传入的关联的主表实体名称
    private String relaMainTableEntityName;
    // 当前表关联的主表的字段
    private List<RelaMainTableCol> relaMainTableColList = new ArrayList<>();
    private List<Button> gridButtonList;
    private String subTableGridIsEdit;

    public String getRelaMainTableEntityName() {
        return relaMainTableEntityName;
    }

    public void setRelaMainTableEntityName(String relaMainTableEntityName) {
        this.relaMainTableEntityName = relaMainTableEntityName;
    }

    public List<RelaMainTableCol> getRelaMainTableColList() {
        return relaMainTableColList;
    }

    public void setRelaMainTableColList(List<RelaMainTableCol> relaMainTableColList) {
        this.relaMainTableColList = relaMainTableColList;
    }

    public String getOpenSubTableTabPage() {
        return openSubTableTabPage;
    }

    public void setOpenSubTableTabPage(String openSubTableTabPage) {
        this.openSubTableTabPage = openSubTableTabPage;
    }

    public List<Button> getGridButtonList() {
        return gridButtonList;
    }

    public void setGridButtonList(List<Button> gridButtonList) {
        this.gridButtonList = gridButtonList;
    }

    public String getSubTableGridIsEdit() {
        return subTableGridIsEdit;
    }

    public void setSubTableGridIsEdit(String subTableGridIsEdit) {
        this.subTableGridIsEdit = subTableGridIsEdit;
    }

    public String getOpenSubTableTabPageButtonTitle() {
        return openSubTableTabPageButtonTitle;
    }

    public void setOpenSubTableTabPageButtonTitle(String openSubTableTabPageButtonTitle) {
        this.openSubTableTabPageButtonTitle = openSubTableTabPageButtonTitle;
    }

    public String getOpenSubTableModuleName() {
        return openSubTableModuleName;
    }

    public void setOpenSubTableModuleName(String openSubTableModuleName) {
        this.openSubTableModuleName = openSubTableModuleName;
    }

    public String getOpenSubTableBusinessName() {
        return openSubTableBusinessName;
    }

    public void setOpenSubTableBusinessName(String openSubTableBusinessName) {
        this.openSubTableBusinessName = openSubTableBusinessName;
    }
}
