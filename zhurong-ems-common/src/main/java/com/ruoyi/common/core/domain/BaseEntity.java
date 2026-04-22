package com.ruoyi.common.core.domain;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.utils.sql.SqlUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 * @author cpems
 */

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

    /** 删除标志（0代表存在 2代表删除） */
    @TableField(exist = false)
    private String delFlag;

    /** 删除者 */
    @TableField(exist = false)
    private String delBy;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date delTime;

    /** 请求参数 */
    @TableField(exist = false)
    private String paramsJsonStr;

    /** 排序字符串 */
    @TableField(exist = false)
    private String orderByParamStr;

    /** 取前几条数据 */
    @TableField(exist = false)
    private String limitTopN;

    /** id数组 */
    @TableField(exist = false)
    private String[] ids;
    /** id字符串 1,2,3 */
    @TableField(exist = false)
    private String idsStr;

    public String[] getIds() {
        return ids;
    }

    /** 后台可以设置的where条件，前端不可以传入，避免sql注入 */
    //  使用 @JsonIgnore 的字段在转换为 JSON 时不会被包含在内，这意味着它不会出现在生成的 JSON 字符串中；同样，在将 JSON 转换为 Java 对象时，该字段也不会被赋值。
    // 这样做的好处是可以保护敏感信息，或者避免将不必要的数据暴露在 API 接口中。
    @JsonIgnore
    @TableField(exist = false)
    private String whereSql;

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

    public void setIds(String[] ids) {
        this.idsStr = ArrayUtil.join(ids, ",");
        this.ids = ids;
    }

    public String getIdsStr() {
        return idsStr;
    }

    public void setIdsStr(String idsStr) {
        if (null != idsStr) {
            this.ids = idsStr.split(",");
        }
        this.idsStr = idsStr;
    }

    public String getParamsJsonStr() {
        return paramsJsonStr;
    }

    public void setParamsJsonStr(String paramsJsonStr) {
        this.paramsJsonStr = paramsJsonStr;
        Map map = JSONUtil.toBean(paramsJsonStr, Map.class);
        if (this.params == null) {
            this.params = new HashMap<>();
            this.params.putAll(map);
        } else {
            this.params.putAll(map);
        }
    }

    public String getOrderByParamStr() {
        return orderByParamStr;
    }

    public void setOrderByParamStr(String orderByParamStr) {
        // 防止sql注入检查
        SqlUtil.escapeOrderBySql(orderByParamStr);
        SqlUtil.filterKeyword(orderByParamStr);
        this.orderByParamStr = orderByParamStr;
    }

}
