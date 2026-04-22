package com.ruoyi.system.adapter;

import com.ruoyi.system.domain.SysIntegrationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AdapterFactory {

    private final Map<String, SystemAdapter> adapters = new HashMap<>();

    public void registerAdapter(SystemAdapter adapter) {
        adapters.put(adapter.getSystemCode(), adapter);
        log.info("Registered adapter for system: {}", adapter.getSystemCode());
    }

    public SystemAdapter getAdapter(String systemCode) {
        return adapters.get(systemCode);
    }

    public SystemAdapter createAdapter(SysIntegrationConfig config) {
        String systemCode = config.getSystemCode();
        
        SystemAdapter adapter = null;
        switch (systemCode) {
            case "MES":
                adapter = new MesAdapter(config);
                break;
            case "ERP":
                adapter = new ErpAdapter(config);
                break;
            case "ENV":
                adapter = new EnvAdapter(config);
                break;
            case "FIRE":
                adapter = new FireAdapter(config);
                break;
            default:
                log.warn("Unknown system code: {}", systemCode);
        }
        
        if (adapter != null) {
            registerAdapter(adapter);
        }
        
        return adapter;
    }

    public Map<String, SystemAdapter> getAllAdapters() {
        return adapters;
    }

    public boolean testConnection(String systemCode) {
        SystemAdapter adapter = getAdapter(systemCode);
        if (adapter != null) {
            return adapter.testConnection();
        }
        return false;
    }
}
