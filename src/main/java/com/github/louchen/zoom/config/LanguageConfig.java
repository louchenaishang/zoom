package com.github.louchen.zoom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

/**
 * 配置类 - 国际化
 *
 * @author louchen
 */
@Configuration
public class LanguageConfig {

    private static final int cacheSeconds = 3600;

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setCacheSeconds(cacheSeconds);
        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        reloadableResourceBundleMessageSource.setBasename("classpath:i18n/message");

        return reloadableResourceBundleMessageSource;
    }

    @Bean(name = "localeResolver")
    public FixedLocaleResolver fixedLocaleResolver() {
        return new FixedLocaleResolver();
    }

}
