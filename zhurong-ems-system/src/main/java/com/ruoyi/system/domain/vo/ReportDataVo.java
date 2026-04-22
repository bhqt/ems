package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 报表数据
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
public class ReportDataVo {

    /** 报表标题 */
    private String title;

    /** 报表数据 */
    private List<Map<String, Object>> data;

    /** 报表元数据 */
    private Map<String, Object> metaData;

    /** 报表统计信息 */
    private Map<String, Object> statistics;

}
