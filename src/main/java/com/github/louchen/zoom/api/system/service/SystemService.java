package com.github.louchen.zoom.api.system.service;

import com.github.louchen.zoom.api.system.model.SystemConfig;
import com.github.louchen.zoom.base.BaseService;

/**
 * Created by louchen on 2017/9/15.
 */
public interface SystemService extends BaseService<SystemConfig, Long> {

    String getVersion();

    /**
     * 返回数据库唯一的记录

     * @return 系统配置，若不存在则返回null
     */
    SystemConfig findSystemConfig();

    /**
     * 保存系统配置
     *
     * @param systemConfig
     *            对象
     * @return 系统配置
     */
    SystemConfig saveSystemConfig(SystemConfig systemConfig);


}
