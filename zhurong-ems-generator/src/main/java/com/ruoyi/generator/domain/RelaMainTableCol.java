package com.ruoyi.generator.domain;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2022-6-24 <br>
 * @author Double
 * @version 1.0.0
 */
public class RelaMainTableCol {
    private String colName;
    private String entityName;
    private String colTitle;
    private String htmlType;
    private String selectDict;
    private String isQueryCol;
    private String isGridCol;
    private String isHidden;
    private String isPk;
    private String isDisabled;

    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(String isHidden) {
        this.isHidden = isHidden;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getIsGridCol() {
        return isGridCol;
    }

    public void setIsGridCol(String isGridCol) {
        this.isGridCol = isGridCol;
    }

    public String getIsQueryCol() {
        return isQueryCol;
    }

    public void setIsQueryCol(String isQueryCol) {
        this.isQueryCol = isQueryCol;
    }

    public String getColTitle() {
        return colTitle;
    }

    public void setColTitle(String colTitle) {
        this.colTitle = colTitle;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getHtmlType() {
        return htmlType;
    }

    public void setHtmlType(String htmlType) {
        this.htmlType = htmlType;
    }

    public String getSelectDict() {
        return selectDict;
    }

    public void setSelectDict(String selectDict) {
        this.selectDict = selectDict;
    }
}
