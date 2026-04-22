package com.ruoyi.autoee.maintainOrder.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 维修工单对象 a_maintain_order
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_maintain_order") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class MaintainOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
    /** 工单编号 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "工单编号" , type = Excel.Type.ALL )
    private String orderNo;
    /** 问题描述 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "问题描述" , type = Excel.Type.ALL )
    private String description;
    /** 故障类型 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String orderFaultType;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "故障类型" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String orderFaultTypeExtend;
    /** 优先级 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String orderPriority;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "优先级" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String orderPriorityExtend;
    /** 工单状态 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String repairOrderStatus;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "工单状态" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String repairOrderStatusExtend;
    /** 故障位置 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "故障位置" , type = Excel.Type.ALL )
    private String location;
    /** 报修人 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long reporterId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "报修人" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String reporterIdExtend;
    /** 报修人电话 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "报修人电话" , type = Excel.Type.ALL )
    private String reporterContact;
    /** 报修时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报修时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" , type = Excel.Type.ALL )
    private Date reportTime;
    /** 维修人 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long assigneeId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "维修人" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String assigneeIdExtend;
    /** 维修结果 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "维修结果" , type = Excel.Type.ALL )
    private String repairResult;
    /** 维修图片 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "维修图片" , type = Excel.Type.ALL ,  cellType = Excel.ColumnType.IMAGE)
    private String repairImages;
    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" , type = Excel.Type.ALL )
    private Date completionTime;
    /** 备注 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "备注" , type = Excel.Type.ALL )
    private String remark;
    /** 所属用户 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long userId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String userIdExtend;
    /** 所属部门 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setOrderFaultType(String orderFaultType)
    {
        this.orderFaultType = orderFaultType;
    }

    public String getOrderFaultType()
    {
        return orderFaultType;
    }

	public void setOrderFaultTypeExtend(String orderFaultTypeExtend)
    {
        this.orderFaultTypeExtend = orderFaultTypeExtend;
    }

    public String getOrderFaultTypeExtend()
    {
        return orderFaultTypeExtend;
    }
    public void setOrderPriority(String orderPriority)
    {
        this.orderPriority = orderPriority;
    }

    public String getOrderPriority()
    {
        return orderPriority;
    }

	public void setOrderPriorityExtend(String orderPriorityExtend)
    {
        this.orderPriorityExtend = orderPriorityExtend;
    }

    public String getOrderPriorityExtend()
    {
        return orderPriorityExtend;
    }
    public void setRepairOrderStatus(String repairOrderStatus)
    {
        this.repairOrderStatus = repairOrderStatus;
    }

    public String getRepairOrderStatus()
    {
        return repairOrderStatus;
    }

	public void setRepairOrderStatusExtend(String repairOrderStatusExtend)
    {
        this.repairOrderStatusExtend = repairOrderStatusExtend;
    }

    public String getRepairOrderStatusExtend()
    {
        return repairOrderStatusExtend;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }

    public void setReporterId(Long reporterId)
    {
        this.reporterId = reporterId;
    }

    public Long getReporterId()
    {
        return reporterId;
    }

	public void setReporterIdExtend(String reporterIdExtend)
    {
        this.reporterIdExtend = reporterIdExtend;
    }

    public String getReporterIdExtend()
    {
        return reporterIdExtend;
    }
    public void setReporterContact(String reporterContact)
    {
        this.reporterContact = reporterContact;
    }

    public String getReporterContact()
    {
        return reporterContact;
    }

    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }

    public Date getReportTime()
    {
        return reportTime;
    }

    public void setAssigneeId(Long assigneeId)
    {
        this.assigneeId = assigneeId;
    }

    public Long getAssigneeId()
    {
        return assigneeId;
    }

	public void setAssigneeIdExtend(String assigneeIdExtend)
    {
        this.assigneeIdExtend = assigneeIdExtend;
    }

    public String getAssigneeIdExtend()
    {
        return assigneeIdExtend;
    }
    public void setRepairResult(String repairResult)
    {
        this.repairResult = repairResult;
    }

    public String getRepairResult()
    {
        return repairResult;
    }

    public void setRepairImages(String repairImages)
    {
        this.repairImages = repairImages;
    }

    public String getRepairImages()
    {
        return repairImages;
    }

    public void setCompletionTime(Date completionTime)
    {
        this.completionTime = completionTime;
    }

    public Date getCompletionTime()
    {
        return completionTime;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
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
            .append("orderNo", getOrderNo())
            .append("description", getDescription())
            .append("orderFaultType", getOrderFaultType())
            .append("orderPriority", getOrderPriority())
            .append("repairOrderStatus", getRepairOrderStatus())
            .append("location", getLocation())
            .append("reporterId", getReporterId())
            .append("reporterContact", getReporterContact())
            .append("reportTime", getReportTime())
            .append("assigneeId", getAssigneeId())
            .append("repairResult", getRepairResult())
            .append("repairImages", getRepairImages())
            .append("completionTime", getCompletionTime())
            .append("remark", getRemark())
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
