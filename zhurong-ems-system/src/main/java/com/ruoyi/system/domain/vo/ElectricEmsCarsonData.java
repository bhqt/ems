package com.ruoyi.system.domain.vo;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2025/9/14 0014 <br>
 * @author Double
 * @version 1.0.0
 */

import lombok.Data;

import java.util.List;

@Data
public class ElectricEmsCarsonData {
    private String id;
    private String version;
    private Integer ack;
    private List<Param> params;

    @Data
    public static class Param {
        private String clientID;
        private List<Property> properties;
    }

    @Data
    public static class Property {
        private String name;
        private Object value;
        private Long timestamp;
    }
}
