package com.github.louchen.zoom.api.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by louchen on 2017/8/03.
 */
@RestController
@RequestMapping(path = "/api/version")
@PreAuthorize("hasRole('USER')")
public class VersionController {

    @Value("${sys.version}")
    private String systemVersion;

    @GetMapping(path = "")
    public String getSystemVersion() {
        return systemVersion;
    }

}
