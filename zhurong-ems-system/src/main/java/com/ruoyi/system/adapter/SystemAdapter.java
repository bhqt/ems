package com.ruoyi.system.adapter;

public interface SystemAdapter {

    String getSystemCode();

    Object fetchData(String interfaceCode, Object params);

    boolean sendData(String interfaceCode, Object data);

    boolean testConnection();
}
