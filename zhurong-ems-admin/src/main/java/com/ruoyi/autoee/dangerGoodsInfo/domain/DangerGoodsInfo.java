package com.ruoyi.autoee.dangerGoodsInfo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.handler.MyExportSelfDictTypeHandler;

/**
 * 危化品信息管理对象 a_danger_goods_info
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_danger_goods_info") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class DangerGoodsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
    /** 危化品名称 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "危化品名称" , type = Excel.Type.ALL )
    private String dangerGoodsName;
    /** 危化品类型 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String dangerGoodsType;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "危化品类型" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String dangerGoodsTypeExtend;
    /** 危化品状态 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String dangerGoodsStatus;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "危化品状态" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String dangerGoodsStatusExtend;
    /** 规格型号 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "规格型号" , type = Excel.Type.ALL )
    private String specification;
    /** 单位 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String goodsUnit;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "单位" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String goodsUnitExtend;
    /** 存储位置 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "存储位置" , type = Excel.Type.ALL )
    private String storageLocation;
    /** 所属用户 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long userId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String userIdExtend;
    /** 所属部门 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long deptId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String deptIdExtend;
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setDangerGoodsName(String dangerGoodsName)
    {
        this.dangerGoodsName = dangerGoodsName;
    }

    public String getDangerGoodsName()
    {
        return dangerGoodsName;
    }

    public void setDangerGoodsType(String dangerGoodsType)
    {
        this.dangerGoodsType = dangerGoodsType;
    }

    public String getDangerGoodsType()
    {
        return dangerGoodsType;
    }

	public void setDangerGoodsTypeExtend(String dangerGoodsTypeExtend)
    {
        this.dangerGoodsTypeExtend = dangerGoodsTypeExtend;
    }

    public String getDangerGoodsTypeExtend()
    {
        return dangerGoodsTypeExtend;
    }
    public void setDangerGoodsStatus(String dangerGoodsStatus)
    {
        this.dangerGoodsStatus = dangerGoodsStatus;
    }

    public String getDangerGoodsStatus()
    {
        return dangerGoodsStatus;
    }

	public void setDangerGoodsStatusExtend(String dangerGoodsStatusExtend)
    {
        this.dangerGoodsStatusExtend = dangerGoodsStatusExtend;
    }

    public String getDangerGoodsStatusExtend()
    {
        return dangerGoodsStatusExtend;
    }
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getSpecification()
    {
        return specification;
    }

    public void setGoodsUnit(String goodsUnit)
    {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsUnit()
    {
        return goodsUnit;
    }

	public void setGoodsUnitExtend(String goodsUnitExtend)
    {
        this.goodsUnitExtend = goodsUnitExtend;
    }

    public String getGoodsUnitExtend()
    {
        return goodsUnitExtend;
    }
    public void setStorageLocation(String storageLocation)
    {
        this.storageLocation = storageLocation;
    }

    public String getStorageLocation()
    {
        return storageLocation;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

	public void setUserIdExtend(String userIdExtend)
    {
        this.userIdExtend = userIdExtend;
    }

    public String getUserIdExtend()
    {
        return userIdExtend;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

	public void setDeptIdExtend(String deptIdExtend)
    {
        this.deptIdExtend = deptIdExtend;
    }

    public String getDeptIdExtend()
    {
        return deptIdExtend;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dangerGoodsName", getDangerGoodsName())
            .append("dangerGoodsType", getDangerGoodsType())
            .append("dangerGoodsStatus", getDangerGoodsStatus())
            .append("specification", getSpecification())
            .append("goodsUnit", getGoodsUnit())
            .append("storageLocation", getStorageLocation())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("delBy", getDelBy())
            .append("delTime", getDelTime())
            .toString();
    }
}
