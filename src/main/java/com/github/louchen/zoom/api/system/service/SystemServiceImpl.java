package com.github.louchen.zoom.api.system.service;

import com.github.louchen.zoom.api.system.model.SystemConfig;
import com.github.louchen.zoom.api.system.repository.SystemConfigRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import com.github.louchen.zoom.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by louchen on 2017/9/15.
 */
@Service
@Slf4j
public class SystemServiceImpl extends BaseServiceImpl<SystemConfig, Long> implements SystemService{

    @Value("${sys.version}")
    private String systemVersion;
    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Override
    public String getVersion() {
        return systemVersion;
    }

    @Override
    public SystemConfig findSystemConfig() {
        return systemConfigRepository.findSystemConfig();
    }

    @Override
    public SystemConfig saveSystemConfig(SystemConfig systemConfig) {
        AssertUtils.notNull(systemConfig);
        AssertUtils.notNull(systemConfig.getSetting());

        SystemConfig dataInDb = findSystemConfig();
        if(dataInDb==null){
            super.save(systemConfig);
        }else{
            systemConfig.setId(dataInDb.getId());
            super.update(systemConfig);
        }
        return systemConfig;
    }

}
