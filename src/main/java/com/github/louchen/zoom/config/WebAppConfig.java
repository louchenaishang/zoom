package com.github.louchen.zoom.config;

import com.github.louchen.zoom.api.captcha.interceptor.CaptchaInterceptor;
import com.github.louchen.zoom.config.jackson.JacksonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 配置类 - rest_web
 *
 * @author louchen
 */
@Configuration
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    public Environment env;

    private static final String prefixUrl = "/api";

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5MB");//单个文件最大
        factory.setMaxRequestSize("5MB");//一次request上传数据总大小
        return factory.createMultipartConfig();
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        return new ByteArrayHttpMessageConverter();
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));

        return stringHttpMessageConverter;
    }

    @Bean
    public JacksonObjectMapper jacksonObjectMapper(){
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        jacksonObjectMapper.setIgnoreProperty("version,handler,fieldHandler,hibernateLazyInitializer,JavassistLazyInitializer");
        jacksonObjectMapper.setDateFormatPattern("yyyy-MM-dd HH:mm:ss.SSS");
        jacksonObjectMapper.init();

        return jacksonObjectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(jacksonObjectMapper());

        return mappingJackson2HttpMessageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());

        super.configureMessageConverters(converters);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");

        super.addCorsMappings(registry);
    }

    @Bean
    public CaptchaInterceptor captchaInterceptor() {
        return new CaptchaInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String register = prefixUrl + env.getProperty("jwt.route.authentication.register");
        String login = prefixUrl + env.getProperty("jwt.route.authentication.path");
        registry.addInterceptor(captchaInterceptor())
                .addPathPatterns(register)
                .addPathPatterns(login);

        super.addInterceptors(registry);
    }


}
