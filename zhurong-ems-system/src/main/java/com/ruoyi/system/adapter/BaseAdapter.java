package com.ruoyi.system.adapter;

import com.ruoyi.system.domain.SysIntegrationConfig;
import com.ruoyi.system.domain.SysInterfaceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
public abstract class BaseAdapter implements SystemAdapter {

    protected SysIntegrationConfig config;
    protected RestTemplate restTemplate;

    public BaseAdapter(SysIntegrationConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Object fetchData(String interfaceCode, Object params) {
        return null;
    }

    @Override
    public boolean sendData(String interfaceCode, Object data) {
        return false;
    }

    @Override
    public boolean testConnection() {
        try {
            String url = config.getConnectionUrl();
            if (url == null || url.isEmpty()) {
                return false;
            }
            HttpHeaders headers = createHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            log.error("Connection test failed for system: {}", config.getSystemCode(), e);
            return false;
        }
    }

    protected HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String username = config.getUsername();
        String password = config.getPassword();
        
        if (username != null && password != null) {
            String auth = username + ":" + password;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);
        }
        
        String apiKey = config.getApiKey();
        if (apiKey != null && !apiKey.isEmpty()) {
            headers.set("X-API-Key", apiKey);
        }
        
        return headers;
    }

    protected ResponseEntity<String> doGetRequest(String url, Map<String, Object> params) {
        HttpHeaders headers = createHeaders();
        
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url = sb.toString();
        }
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    protected ResponseEntity<String> doPostRequest(String url, Object body) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    protected ResponseEntity<String> doPutRequest(String url, Object body) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    protected ResponseEntity<String> doDeleteRequest(String url) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
    }
}
