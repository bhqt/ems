package com.ruoyi.autoee.contractInfo.domain;

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
 * 合同信息管理对象 a_contract_info
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
// 设置实体类对应的表名
@TableName("a_contract_info") // 如果使用了 MyBatis-Plus，表名默认是根据实体类名自动转换的，如果你的实体类名为 PatrolRecord，MyBatis-Plus 默认会将其转换为 patrol_record表名，而不是你期望的 a_patrol_record。
public class ContractInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    private Long id;
    /** 合同编号(新) */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "合同编号(新)" , type = Excel.Type.ALL )
    private String contractNoNew;
    /** 续签编号(老) */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "续签编号(老)" , type = Excel.Type.ALL )
    private String contractNoOld;
    /** 所属客户 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "所属客户" , type = Excel.Type.ALL )
    private String belongCustomer;
    /** 客户方联系人 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "客户方联系人" , type = Excel.Type.ALL )
    private String customerContact;
    /** 合同类型 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String contractType;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "合同类型" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String contractTypeExtend;
    /** 合同子类型 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
	// 下拉框，直接通过扩展字段进行导出，避免字段类型不一致，无法设置值
    private String contractSubtype;
	// 如果是下拉框、自定义下拉框，直接取对应的扩展字段进行前端显示或导出
	@Excel(name = "合同子类型" , type = Excel.Type.ALL )
	@TableField(select = false) // 在扩展字段上添加注解，告诉 MyBatis-Plus 这些字段不是数据库表中的列，避免查询报错
    private String contractSubtypeExtend;
    /** 签约公司 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "签约公司" , type = Excel.Type.ALL )
    private String signCompany;
    /** 业务员 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "业务员" , type = Excel.Type.ALL )
    private String salesmanId;
    /** 技术支持 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "技术支持" , type = Excel.Type.ALL )
    private String techSupport;
    /** 报价单号 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "报价单号" , type = Excel.Type.ALL )
    private String quoteNo;
    /** 合同总价 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "合同总价" , type = Excel.Type.ALL )
    private String contractTotal;
    /** 已收金额 */
    @JsonFormat(shape = JsonFormat.Shape.STRING) // 其他数字等类型都序列化为字符串类型，避免前端转码问题和long型数字精度丢失问题
    @Excel(name = "已收金额" , type = Excel.Type.ALL )
    private String receivedAmount;
    /** 签约日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签约日期", dateFormat = "yyyy-MM-dd" , type = Excel.Type.ALL )
    private Date signDate;
    /** 附件 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "附件" , type = Excel.Type.IMPORT )
    private String attachmentFiles;
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
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss" , type = Excel.Type.EXPORT )
    private Date updateTime;
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setContractNoNew(String contractNoNew)
    {
        this.contractNoNew = contractNoNew;
    }

    public String getContractNoNew()
    {
        return contractNoNew;
    }

    public void setContractNoOld(String contractNoOld)
    {
        this.contractNoOld = contractNoOld;
    }

    public String getContractNoOld()
    {
        return contractNoOld;
    }

    public void setBelongCustomer(String belongCustomer)
    {
        this.belongCustomer = belongCustomer;
    }

    public String getBelongCustomer()
    {
        return belongCustomer;
    }

    public void setCustomerContact(String customerContact)
    {
        this.customerContact = customerContact;
    }

    public String getCustomerContact()
    {
        return customerContact;
    }

    public void setContractType(String contractType)
    {
        this.contractType = contractType;
    }

    public String getContractType()
    {
        return contractType;
    }

	public void setContractTypeExtend(String contractTypeExtend)
    {
        this.contractTypeExtend = contractTypeExtend;
    }

    public String getContractTypeExtend()
    {
        return contractTypeExtend;
    }
    public void setContractSubtype(String contractSubtype)
    {
        this.contractSubtype = contractSubtype;
    }

    public String getContractSubtype()
    {
        return contractSubtype;
    }

	public void setContractSubtypeExtend(String contractSubtypeExtend)
    {
        this.contractSubtypeExtend = contractSubtypeExtend;
    }

    public String getContractSubtypeExtend()
    {
        return contractSubtypeExtend;
    }
    public void setSignCompany(String signCompany)
    {
        this.signCompany = signCompany;
    }

    public String getSignCompany()
    {
        return signCompany;
    }

    public void setSalesmanId(String salesmanId)
    {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanId()
    {
        return salesmanId;
    }

    public void setTechSupport(String techSupport)
    {
        this.techSupport = techSupport;
    }

    public String getTechSupport()
    {
        return techSupport;
    }

    public void setQuoteNo(String quoteNo)
    {
        this.quoteNo = quoteNo;
    }

    public String getQuoteNo()
    {
        return quoteNo;
    }

    public void setContractTotal(String contractTotal)
    {
        this.contractTotal = contractTotal;
    }

    public String getContractTotal()
    {
        return contractTotal;
    }

    public void setReceivedAmount(String receivedAmount)
    {
        this.receivedAmount = receivedAmount;
    }

    public String getReceivedAmount()
    {
        return receivedAmount;
    }

    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }

    public Date getSignDate()
    {
        return signDate;
    }

    public void setAttachmentFiles(String attachmentFiles)
    {
        this.attachmentFiles = attachmentFiles;
    }

    public String getAttachmentFiles()
    {
        return attachmentFiles;
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
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contractNoNew", getContractNoNew())
            .append("contractNoOld", getContractNoOld())
            .append("belongCustomer", getBelongCustomer())
            .append("customerContact", getCustomerContact())
            .append("contractType", getContractType())
            .append("contractSubtype", getContractSubtype())
            .append("signCompany", getSignCompany())
            .append("salesmanId", getSalesmanId())
            .append("techSupport", getTechSupport())
            .append("quoteNo", getQuoteNo())
            .append("contractTotal", getContractTotal())
            .append("receivedAmount", getReceivedAmount())
            .append("signDate", getSignDate())
            .append("attachmentFiles", getAttachmentFiles())
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
