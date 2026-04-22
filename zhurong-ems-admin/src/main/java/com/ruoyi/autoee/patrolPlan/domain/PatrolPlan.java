package com.ruoyi.autoee.patrolPlan.domain;

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
 * 巡更计划对象 a_patrol_plan
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_patrol_plan") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class PatrolPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
    /** 巡更计划名称 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "巡更计划名称" , type = Excel.Type.ALL )
    private String patrolPlanName;
    /** 巡更路线 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolPathId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更路线" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolPathIdExtend;
    /** 巡更人员 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolUserId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更人员" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolUserIdExtend;
    /** 开始时间 */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "开始时间", dateFormat = "HH:mm" , type = Excel.Type.ALL )
    private LocalTime startTime;
    /** 结束时间 */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "结束时间", dateFormat = "HH:mm" , type = Excel.Type.ALL )
    private LocalTime endTime;
    /** 巡更周期 */
    @JsonFormat(pattern = "HH:mm")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String patrolCycleType;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更周期" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolCycleTypeExtend;
    /** 巡更周期值 */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "巡更周期值" , type = Excel.Type.IMPORT )
    private String patrolCycleValue;
    /** 计划开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划开始日期", dateFormat = "yyyy-MM-dd" , type = Excel.Type.ALL )
    private Date startDate;
    /** 计划结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划结束日期", dateFormat = "yyyy-MM-dd" , type = Excel.Type.ALL )
    private Date endDate;
    /** 计划状态 */
    @JsonFormat(pattern = "yyyy-MM-dd")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String patrolPlanStatus;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "计划状态" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolPlanStatusExtend;
    /** 备注 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备注" , type = Excel.Type.ALL )
    private String remark;
    /** 所属用户 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Long userId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String userIdExtend;
    /** 所属部门 */
    @JsonFormat(pattern = "yyyy-MM-dd")
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

    public void setPatrolPlanName(String patrolPlanName)
    {
        this.patrolPlanName = patrolPlanName;
    }

    public String getPatrolPlanName()
    {
        return patrolPlanName;
    }

    public void setPatrolPathId(Long patrolPathId)
    {
        this.patrolPathId = patrolPathId;
    }

    public Long getPatrolPathId()
    {
        return patrolPathId;
    }

	public void setPatrolPathIdExtend(String patrolPathIdExtend)
    {
        this.patrolPathIdExtend = patrolPathIdExtend;
    }

    public String getPatrolPathIdExtend()
    {
        return patrolPathIdExtend;
    }
    public void setPatrolUserId(Long patrolUserId)
    {
        this.patrolUserId = patrolUserId;
    }

    public Long getPatrolUserId()
    {
        return patrolUserId;
    }

	public void setPatrolUserIdExtend(String patrolUserIdExtend)
    {
        this.patrolUserIdExtend = patrolUserIdExtend;
    }

    public String getPatrolUserIdExtend()
    {
        return patrolUserIdExtend;
    }
    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public void setEndTime(LocalTime endTime)
    {
        this.endTime = endTime;
    }

    public LocalTime getEndTime()
    {
        return endTime;
    }

    public void setPatrolCycleType(String patrolCycleType)
    {
        this.patrolCycleType = patrolCycleType;
    }

    public String getPatrolCycleType()
    {
        return patrolCycleType;
    }

	public void setPatrolCycleTypeExtend(String patrolCycleTypeExtend)
    {
        this.patrolCycleTypeExtend = patrolCycleTypeExtend;
    }

    public String getPatrolCycleTypeExtend()
    {
        return patrolCycleTypeExtend;
    }
    public void setPatrolCycleValue(String patrolCycleValue)
    {
        this.patrolCycleValue = patrolCycleValue;
    }

    public String getPatrolCycleValue()
    {
        return patrolCycleValue;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setPatrolPlanStatus(String patrolPlanStatus)
    {
        this.patrolPlanStatus = patrolPlanStatus;
    }

    public String getPatrolPlanStatus()
    {
        return patrolPlanStatus;
    }

	public void setPatrolPlanStatusExtend(String patrolPlanStatusExtend)
    {
        this.patrolPlanStatusExtend = patrolPlanStatusExtend;
    }

    public String getPatrolPlanStatusExtend()
    {
        return patrolPlanStatusExtend;
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
            .append("patrolPlanName", getPatrolPlanName())
            .append("patrolPathId", getPatrolPathId())
            .append("patrolUserId", getPatrolUserId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("patrolCycleType", getPatrolCycleType())
            .append("patrolCycleValue", getPatrolCycleValue())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("patrolPlanStatus", getPatrolPlanStatus())
            .append("remark", getRemark())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("delBy", getDelBy())
            .append("delTime", getDelTime())
            .toString();
    }
}
