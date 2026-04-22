package com.ruoyi.autoee.patrolRecord.domain;

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
 * 巡更记录对象 a_patrol_record
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_patrol_record") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class PatrolRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
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
    /** 巡更点位 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolPointId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更点位" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolPointIdExtend;
    /** 巡更人员 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolUserId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更人员" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolUserIdExtend;
    /** 巡更任务 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private Long patrolTaskId;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更任务" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolTaskIdExtend;
    /** 点位顺序 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "点位顺序" , type = Excel.Type.ALL )
    private Integer pointOrder;
    /** 点位巡更时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "点位巡更时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" , type = Excel.Type.ALL )
    private Date arriveTime;
    /** 巡更结果 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String patrolResult;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "巡更结果" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String patrolResultExtend;
    /** 结果描述 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结果描述" , type = Excel.Type.ALL )
    private String resultDesc;
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
    public void setPatrolPointId(Long patrolPointId)
    {
        this.patrolPointId = patrolPointId;
    }

    public Long getPatrolPointId()
    {
        return patrolPointId;
    }

	public void setPatrolPointIdExtend(String patrolPointIdExtend)
    {
        this.patrolPointIdExtend = patrolPointIdExtend;
    }

    public String getPatrolPointIdExtend()
    {
        return patrolPointIdExtend;
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
    public void setPointOrder(Integer pointOrder)
    {
        this.pointOrder = pointOrder;
    }

    public Integer getPointOrder()
    {
        return pointOrder;
    }

    public void setArriveTime(Date arriveTime)
    {
        this.arriveTime = arriveTime;
    }

    public Date getArriveTime()
    {
        return arriveTime;
    }

    public void setPatrolResult(String patrolResult)
    {
        this.patrolResult = patrolResult;
    }

    public String getPatrolResult()
    {
        return patrolResult;
    }

	public void setPatrolResultExtend(String patrolResultExtend)
    {
        this.patrolResultExtend = patrolResultExtend;
    }

    public String getPatrolResultExtend()
    {
        return patrolResultExtend;
    }
    public void setResultDesc(String resultDesc)
    {
        this.resultDesc = resultDesc;
    }

    public String getResultDesc()
    {
        return resultDesc;
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
            .append("patrolPlanId", getPatrolPlanId())
            .append("patrolPathId", getPatrolPathId())
            .append("patrolPointId", getPatrolPointId())
            .append("patrolUserId", getPatrolUserId())
            .append("patrolTaskId", getPatrolTaskId())
            .append("pointOrder", getPointOrder())
            .append("arriveTime", getArriveTime())
            .append("patrolResult", getPatrolResult())
            .append("resultDesc", getResultDesc())
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
