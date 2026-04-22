package com.ruoyi.system.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.domain.SysIntegrationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MesAdapter extends BaseAdapter {

    private final ObjectMapper objectMapper;

    public MesAdapter(SysIntegrationConfig config) {
        super(config);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getSystemCode() {
        return "MES";
    }

    @Override
    public Object fetchData(String interfaceCode, Object params) {
        try {
            String url = buildUrl(interfaceCode);
            log.info("Fetching data from MES, interface: {}, url: {}", interfaceCode, url);
            
            ResponseEntity<String> response = doGetRequest(url, (Map<String, Object>) params);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();
                JsonNode jsonNode = objectMapper.readTree(body);
                return jsonNode;
            }
        } catch (Exception e) {
            log.error("Failed to fetch data from MES, interface: {}", interfaceCode, e);
        }
        return null;
    }

    @Override
    public boolean sendData(String interfaceCode, Object data) {
        try {
            String url = buildUrl(interfaceCode);
            log.info("Sending data to MES, interface: {}, url: {}", interfaceCode, url);
            
            ResponseEntity<String> response = doPostRequest(url, data);
            
            return response.getStatusCode() == HttpStatus.OK || 
                   response.getStatusCode() == HttpStatus.CREATED;
        } catch (Exception e) {
            log.error("Failed to send data to MES, interface: {}", interfaceCode, e);
            return false;
        }
    }

    private String buildUrl(String interfaceCode) {
        String baseUrl = config.getConnectionUrl();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl + interfaceCode;
    }

    public Map<String, Object> fetchProductionPlan(Object params) {
        Object result = fetchData("production/plan", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public Map<String, Object> fetchProductionProgress(Object params) {
        Object result = fetchData("production/progress", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public Map<String, Object> fetchDeviceStatus(Object params) {
        Object result = fetchData("device/status", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public boolean sendEnergyConsumption(Object data) {
        return sendData("energy/consumption", data);
    }
}
