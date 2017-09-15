package com.github.louchen.zoom.api.system.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by louchen on 2017/9/15.
 */
@Data
public class Setting implements Serializable {

    /**
     * 缓存名称
     */
    public static final String CACHE_NAME = "setting";

    /**
     * 分隔符
     */
    private static final String SEPARATOR = ",";

    /**
     * 区域设置
     */
    public enum Locale {

        /**
         * 中文(简体, 中国)
         */
        zh_CN

    }

    /**
     * 网站名称
     */
    @NotEmpty
    @Length(max = 200)
    private String siteName;

    /**
     * 网站网址
     */
    @NotEmpty
    @Length(max = 200)
    @Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/).*$")
    private String siteUrl;

    /**
     * 最大登录失败尝试次数
     */
    @NotNull
    @Min(1)
    private Integer maxFailedLoginAttempts;

    /**
     * 密码锁定时间
     */
    @NotNull
    @Min(1)
    private Integer passwordLockTime;

}