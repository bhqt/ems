package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 计量器具信息视图对象 ems_meter_info
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "计量器具信息视图对象")
@ExcelIgnoreUnannotated
public class MeterInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 器具编码
     */
    @Schema(description = "器具编码")
    @ExcelProperty(value = "器具编码")
    private String meterCode;

    /**
     * 器具名称
     */
    @Schema(description = "器具名称")
    @ExcelProperty(value = "器具名称")
    private String meterName;

    /**
     * 器具类型
     */
    @Schema(description = "器具类型")
    @ExcelProperty(value = "器具类型")
    private String meterType;

    /**
     * 型号
     */
    @Schema(description = "型号")
    @ExcelProperty(value = "型号")
    private String meterModel;

    /**
     * 精度等级
     */
    @Schema(description = "精度等级")
    @ExcelProperty(value = "精度等级")
    private String accuracyLevel;

    /**
     * 安装位置
     */
    @Schema(description = "安装位置")
    @ExcelProperty(value = "安装位置")
    private String installLocation;

    /**
     * 安装日期
     */
    @Schema(description = "安装日期")
    @ExcelProperty(value = "安装日期")
    private Date installDate;

    /**
     * 生产厂家
     */
    @Schema(description = "生产厂家")
    @ExcelProperty(value = "生产厂家")
    private String manufacturer;

    /**
     * 出厂日期
     */
    @Schema(description = "出厂日期")
    @ExcelProperty(value = "出厂日期")
    private Date manufacturerDate;

    /**
     * 器具状态
     */
    @Schema(description = "器具状态")
    @ExcelProperty(value = "器具状态")
    private String status;

    /**
     * 上次校准日期
     */
    @Schema(description = "上次校准日期")
    @ExcelProperty(value = "上次校准日期")
    private Date lastCalibrationDate;

    /**
     * 下次校准日期
     */
    @Schema(description = "下次校准日期")
    @ExcelProperty(value = "下次校准日期")
    private Date nextCalibrationDate;

    /**
     * 校准周期(月)
     */
    @Schema(description = "校准周期(月)")
    @ExcelProperty(value = "校准周期")
    private Integer calibrationCycle;

    /**
     * 证书编号
     */
    @Schema(description = "证书编号")
    @ExcelProperty(value = "证书编号")
    private String certificateNo;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createBy;
}
