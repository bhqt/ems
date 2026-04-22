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
public class FireAdapter extends BaseAdapter {

    private final ObjectMapper objectMapper;

    public FireAdapter(SysIntegrationConfig config) {
        super(config);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getSystemCode() {
        return "FIRE";
    }

    @Override
    public Object fetchData(String interfaceCode, Object params) {
        try {
            String url = buildUrl(interfaceCode);
            log.info("Fetching data from FIRE system, interface: {}, url: {}", interfaceCode, url);
            
            ResponseEntity<String> response = doGetRequest(url, (Map<String, Object>) params);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();
                JsonNode jsonNode = objectMapper.readTree(body);
                return jsonNode;
            }
        } catch (Exception e) {
            log.error("Failed to fetch data from FIRE system, interface: {}", interfaceCode, e);
        }
        return null;
    }

    @Override
    public boolean sendData(String interfaceCode, Object data) {
        try {
            String url = buildUrl(interfaceCode);
            log.info("Sending data to FIRE system, interface: {}, url: {}", interfaceCode, url);
            
            ResponseEntity<String> response = doPostRequest(url, data);
            
            return response.getStatusCode() == HttpStatus.OK || 
                   response.getStatusCode() == HttpStatus.CREATED;
        } catch (Exception e) {
            log.error("Failed to send data to FIRE system, interface: {}", interfaceCode, e);
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

    public Map<String, Object> fetchFireAlarmData(Object params) {
        Object result = fetchData("alarm/list", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public Map<String, Object> fetchFireDeviceStatus(Object params) {
        Object result = fetchData("device/status", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public Map<String, Object> fetchFireDrillData(Object params) {
        Object result = fetchData("drill/list", params);
        if (result != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", result);
            return data;
        }
        return null;
    }

    public boolean sendEnergyDeviceStatus(Object data) {
        return sendData("energy/device/status", data);
    }
}
