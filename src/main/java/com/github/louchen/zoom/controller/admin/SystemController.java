package com.github.louchen.zoom.controller.admin;

import com.github.louchen.zoom.api.system.domain.Setting;
import com.github.louchen.zoom.api.system.model.SystemConfig;
import com.github.louchen.zoom.api.system.service.SystemService;
import com.github.louchen.zoom.base.BaseController;
import com.github.louchen.zoom.base.BaseEntity;
import com.github.louchen.zoom.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by louchen on 2017/9/15.
 */
@RestController
@RequestMapping(path = "/api/admin/system")
@PreAuthorize("hasRole('USER')")
public class SystemController extends BaseController {

    @Autowired
    private SystemService systemService;

    @GetMapping(path = "/version")
    public String getSystemVersion() {
        return systemService.getVersion();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/setting")
    public Boolean saveSystemConfig(@RequestBody Setting setting) {
        Assert.isTrue(isValid(setting, BaseEntity.Save.class, BaseEntity.Update.class), SpringUtils.getMessage("common.message.invalidParameter"));

        systemService.saveSystemConfig(new SystemConfig(setting));
        return true;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/setting")
    public Setting getSystemConfig() {
        SystemConfig systemConfig = systemService.findSystemConfig();
        return systemConfig != null ? systemConfig.getSetting() : null;
    }

}
