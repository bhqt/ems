package com.ruoyi.system.domain.bo;

import lombok.Data;

/**
 * 报表生成参数
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
public class ReportGenerateBo {

    /** 模板ID */
    private Long templateId;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 区域ID */
    private Long[] areaIds;

    /** 能源类型 */
    private String energyType;

    /** 日期类型 */
    private String dateType;

    /** 报表名称 */
    private String reportName;

    /** 导出格式 */
    private String exportFormat;

}
