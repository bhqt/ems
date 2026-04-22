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
public class EnvAdapter extends BaseAdapter {

    private final ObjectMapper objectMapper;

    public EnvAdapter(SysIntegrationConfig config) {
        super(config);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getSystemCode() {
        return "ENV";
    }

    @Override
    public Object fetchData(String interfaceCode, Object params) {
        try {
            String url = buildUrl(interfaceCode);
            log.info("Fetching data from ENV system, interface: {}, url: {}", interfaceCode, url);
            
            ResponseEntity<String> response = doGetRequest(url, (Map<String, Object>) params);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();
                JsonNode jsonNode = objectMapper.readTree(body);
                return jsonNode;
            }
        } catch (Exception e) {
            log.error("Failed to fetch data from ENV system, interface: {}", interfaceCode, e);
        }
        return null;
    }

    @Override
    public boolean sendData(String interfaceCode, Object data) {
        try {
            String url = buildUrl(interfaceCode);
            log.info("Sending data to ENV system, interface: {}, url: {}", interfaceCode, url);
            
            ResponseEntity<String> response = doPostRequest(url, data);
            
            return response.getStatusCode() == HttpStatus.OK || 
                   response.getStatusCode() == HttpStatus.CREATED;
        } catch (Exception e) {
            log.error("Failed to send data to ENV system, interface: {}", interfaceCode, e);
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

    public Map<String, Object> fetchEmissionData(Object params) {
        Object result = fetchData("emission/data", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public Map<String, Object> fetchEnvironmentalMonitoringData(Object params) {
        Object result = fetchData("monitoring/data", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public Map<String, Object> fetchEnvironmentalIndexData(Object params) {
        Object result = fetchData("environmental/index", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public boolean sendEnergyConsumptionData(Object data) {
        return sendData("energy/consumption", data);
    }
}
