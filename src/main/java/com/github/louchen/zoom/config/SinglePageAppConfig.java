package com.github.louchen.zoom.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 配置类 - 单页配置
 *
 * @author louchen
 */
@Configuration
public class SinglePageAppConfig extends WebMvcConfigurerAdapter {

    private static final String API_PATH = "/api";
    private static final String PATH_PATTERNS = "/**";
    private static final String FRONT_CONTROLLER = "index.html";
    private static final String CONTEXT_PATH_PLACEHOLDER = "#context-path#";
    private static final String FRONT_CONTROLLER_ENCODING = "UTF-8";

    private final String contextPath;
    private final ResourceProperties resourceProperties;

    public SinglePageAppConfig(
            @Value("${server.contextPath}") final String contextPath,
            final ResourceProperties resourceProperties
    ) {
        this.contextPath = contextPath;
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH_PATTERNS)
                .addResourceLocations(resourceProperties.getStaticLocations())
                .setCachePeriod(resourceProperties.getCachePeriod())
                .resourceChain(true)
                .addResolver(new SinglePageAppResourceResolver());
    }

    private class SinglePageAppResourceResolver extends PathResourceResolver {

        private TransformedResource transformedResource(final Resource resource) throws IOException {
            String fileContent = IOUtils.toString(resource.getInputStream(), FRONT_CONTROLLER_ENCODING);
            fileContent = fileContent.replace(CONTEXT_PATH_PLACEHOLDER, contextPath + "/");
            return new TransformedResource(resource, fileContent.getBytes());
        }

        @Override
        protected Resource getResource(final String resourcePath, final Resource location) throws IOException {
            Resource resource = location.createRelative(resourcePath);
            if (resource.exists() && resource.isReadable()) {
                //if the asked resource is index.html, we serve it with the base-href rewritten
                if (resourcePath.contains(FRONT_CONTROLLER)) {
                    return transformedResource(resource);
                }

                return resource;
            }

            //do not serve a Resource on an reserved URI
            if (("/" + resourcePath).startsWith(API_PATH)) {
                return null;
            }

            //we just refreshed a page, no ?
            resource = location.createRelative(FRONT_CONTROLLER);
            if (resource.exists() && resource.isReadable()) {
                return transformedResource(resource);
            }

            return null;
        }
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter(){
        return new ByteArrayHttpMessageConverter();
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter(){
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return stringHttpMessageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        return mappingJackson2HttpMessageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());

        super.configureMessageConverters(converters);
    }

}