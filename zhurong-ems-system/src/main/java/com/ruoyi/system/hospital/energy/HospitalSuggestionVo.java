package com.ruoyi.system.hospital.energy;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院节能建议视图对象（规则引擎生成，可导出为分析报告）
 *
 * @author cpems
 */
@Data
@ExcelIgnoreUnannotated
public class HospitalSuggestionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 建议类型（STANDBY待机浪费/PEAK高耗能时段/ABNORMAL异常设备） */
    @ExcelProperty(value = "建议类型")
    private String type;

    /** 建议类型名称 */
    @ExcelProperty(value = "类型说明")
    private String typeName;

    /** 设备ID（时段类建议为空） */
    private Long deviceId;

    /** 设备名称 */
    @ExcelProperty(value = "相关设备")
    private String deviceName;

    /** 建议内容 */
    @ExcelProperty(value = "建议内容")
    private String content;

    /** 处置建议 */
    @ExcelProperty(value = "处置建议")
    private String action;
}
