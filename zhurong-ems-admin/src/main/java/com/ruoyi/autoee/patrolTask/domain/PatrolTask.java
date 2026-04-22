package com.ruoyi.autoee.patrolTask.domain;

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
 * 巡更任务对象 a_patrol_task
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_patrol_task") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class PatrolTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
    /** 任务名称 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "任务名称" , type = Excel.Type.ALL )
    private String patrolTaskName;
    /** 巡更计划 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolPlanId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更计划" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolPlanIdExtend;
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
    /** 巡更日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡更日期", dateFormat = "yyyy-MM-dd" , type = Excel.Type.ALL )
    private Date patrolDate;
    /** 开始时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "开始时间", dateFormat = "HH:mm:ss" , type = Excel.Type.ALL )
    private LocalTime startTime;
    /** 结束时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "结束时间", dateFormat = "HH:mm:ss" , type = Excel.Type.ALL )
    private LocalTime endTime;
    /** 任务状态 */
    @JsonFormat(pattern = "HH:mm:ss")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String patrolTaskStatus;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "任务状态" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolTaskStatusExtend;
    /** 备注 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "备注" , type = Excel.Type.ALL )
    private String remark;
    /** 所属用户 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Long userId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String userIdExtend;
    /** 所属部门 */
    @JsonFormat(pattern = "HH:mm:ss")
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

    public void setPatrolTaskName(String patrolTaskName)
    {
        this.patrolTaskName = patrolTaskName;
    }

    public String getPatrolTaskName()
    {
        return patrolTaskName;
    }

    public void setPatrolPlanId(Long patrolPlanId)
    {
        this.patrolPlanId = patrolPlanId;
    }

    public Long getPatrolPlanId()
    {
        return patrolPlanId;
    }

	public void setPatrolPlanIdExtend(String patrolPlanIdExtend)
    {
        this.patrolPlanIdExtend = patrolPlanIdExtend;
    }

    public String getPatrolPlanIdExtend()
    {
        return patrolPlanIdExtend;
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
    public void setPatrolDate(Date patrolDate)
    {
        this.patrolDate = patrolDate;
    }

    public Date getPatrolDate()
    {
        return patrolDate;
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

    public void setPatrolTaskStatus(String patrolTaskStatus)
    {
        this.patrolTaskStatus = patrolTaskStatus;
    }

    public String getPatrolTaskStatus()
    {
        return patrolTaskStatus;
    }

	public void setPatrolTaskStatusExtend(String patrolTaskStatusExtend)
    {
        this.patrolTaskStatusExtend = patrolTaskStatusExtend;
    }

    public String getPatrolTaskStatusExtend()
    {
        return patrolTaskStatusExtend;
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
            .append("patrolTaskName", getPatrolTaskName())
            .append("patrolPlanId", getPatrolPlanId())
            .append("patrolPathId", getPatrolPathId())
            .append("patrolUserId", getPatrolUserId())
            .append("patrolDate", getPatrolDate())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("patrolTaskStatus", getPatrolTaskStatus())
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
