package com.ruoyi.system.hospital.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院 IOT 回调日志查询业务对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalCallbackLogBo extends BaseEntity {

    /** 主键 */
    private Long id;

    /** 请求 ID（IOT 平台消息 ID） */
    private String requestId;

    /** 来源 IP */
    private String sourceIp;

    /** 处理状态（success/auth_fail/parse_fail/fail） */
    private String status;
}
