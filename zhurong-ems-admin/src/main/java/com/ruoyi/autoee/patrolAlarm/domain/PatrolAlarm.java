package com.ruoyi.autoee.patrolAlarm.domain;

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
 * 巡更报警对象 a_patrol_alarm
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_patrol_alarm") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class PatrolAlarm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
    /** 报警编号 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "报警编号" , type = Excel.Type.ALL )
    private String alarmNo;
    /** 巡更计划 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolPlanId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更计划" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolPlanIdExtend;
    /** 巡更任务 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolTaskId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更任务" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolTaskIdExtend;
    /** 巡更人员 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolUserId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更人员" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolUserIdExtend;
    /** 报警类型 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String patrolAlarmType;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "报警类型" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolAlarmTypeExtend;
    /** 报警时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报警时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" , type = Excel.Type.ALL )
    private Date patrolAlarmTime;
    /** 报警内容 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报警内容" , type = Excel.Type.ALL )
    private String patrolAlarmContent;
    /** 报警状态 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String patrolAlarmStatus;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "报警状态" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolAlarmStatusExtend;
    /** 处理人 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long handleUserId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "处理人" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String handleUserIdExtend;
    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" , type = Excel.Type.ALL )
    private Date handleTime;
    /** 处理描述 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理描述" , type = Excel.Type.ALL )
    private String handleDesc;
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

    public void setAlarmNo(String alarmNo)
    {
        this.alarmNo = alarmNo;
    }

    public String getAlarmNo()
    {
        return alarmNo;
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
    public void setPatrolTaskId(Long patrolTaskId)
    {
        this.patrolTaskId = patrolTaskId;
    }

    public Long getPatrolTaskId()
    {
        return patrolTaskId;
    }

	public void setPatrolTaskIdExtend(String patrolTaskIdExtend)
    {
        this.patrolTaskIdExtend = patrolTaskIdExtend;
    }

    public String getPatrolTaskIdExtend()
    {
        return patrolTaskIdExtend;
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
    public void setPatrolAlarmType(String patrolAlarmType)
    {
        this.patrolAlarmType = patrolAlarmType;
    }

    public String getPatrolAlarmType()
    {
        return patrolAlarmType;
    }

	public void setPatrolAlarmTypeExtend(String patrolAlarmTypeExtend)
    {
        this.patrolAlarmTypeExtend = patrolAlarmTypeExtend;
    }

    public String getPatrolAlarmTypeExtend()
    {
        return patrolAlarmTypeExtend;
    }
    public void setPatrolAlarmTime(Date patrolAlarmTime)
    {
        this.patrolAlarmTime = patrolAlarmTime;
    }

    public Date getPatrolAlarmTime()
    {
        return patrolAlarmTime;
    }

    public void setPatrolAlarmContent(String patrolAlarmContent)
    {
        this.patrolAlarmContent = patrolAlarmContent;
    }

    public String getPatrolAlarmContent()
    {
        return patrolAlarmContent;
    }

    public void setPatrolAlarmStatus(String patrolAlarmStatus)
    {
        this.patrolAlarmStatus = patrolAlarmStatus;
    }

    public String getPatrolAlarmStatus()
    {
        return patrolAlarmStatus;
    }

	public void setPatrolAlarmStatusExtend(String patrolAlarmStatusExtend)
    {
        this.patrolAlarmStatusExtend = patrolAlarmStatusExtend;
    }

    public String getPatrolAlarmStatusExtend()
    {
        return patrolAlarmStatusExtend;
    }
    public void setHandleUserId(Long handleUserId)
    {
        this.handleUserId = handleUserId;
    }

    public Long getHandleUserId()
    {
        return handleUserId;
    }

	public void setHandleUserIdExtend(String handleUserIdExtend)
    {
        this.handleUserIdExtend = handleUserIdExtend;
    }

    public String getHandleUserIdExtend()
    {
        return handleUserIdExtend;
    }
    public void setHandleTime(Date handleTime)
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime()
    {
        return handleTime;
    }

    public void setHandleDesc(String handleDesc)
    {
        this.handleDesc = handleDesc;
    }

    public String getHandleDesc()
    {
        return handleDesc;
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
            .append("alarmNo", getAlarmNo())
            .append("patrolPlanId", getPatrolPlanId())
            .append("patrolTaskId", getPatrolTaskId())
            .append("patrolUserId", getPatrolUserId())
            .append("patrolAlarmType", getPatrolAlarmType())
            .append("patrolAlarmTime", getPatrolAlarmTime())
            .append("patrolAlarmContent", getPatrolAlarmContent())
            .append("patrolAlarmStatus", getPatrolAlarmStatus())
            .append("handleUserId", getHandleUserId())
            .append("handleTime", getHandleTime())
            .append("handleDesc", getHandleDesc())
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
