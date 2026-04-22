package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 标杆标准对象 ems_benchmark_standard
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_benchmark_standard")
public class BenchmarkStandard extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标准编码
     */
    private String standardCode;

    /**
     * 标准名称
     */
    private String standardName;

    /**
     * 标准类型(national:国家标杆, industry:行业标杆, enterprise:企业标杆)
     */
    private String standardType;

    /**
     * 指标名称
     */
    private String indicatorName;

    /**
     * 指标单位
     */
    private String indicatorUnit;

    /**
     * 标准值
     */
    private BigDecimal standardValue;

    /**
     * 生效日期
     */
    private Date effectiveDate;

    /**
     * 失效日期
     */
    private Date expiryDate;

    /**
     * 标准状态(active:有效, inactive:无效)
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 所属部门
     */
    private Long deptId;

    /**
     * 删除标志(0:正常, 1:删除)
     */
    @TableLogic
    private String delFlag;

    /**
     * 删除者
     */
    private String delBy;

    /**
     * 删除时间
     */
    private Date delTime;
}
