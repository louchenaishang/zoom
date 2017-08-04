package com.github.louchen.zoom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Created by louchen on 2017/6/28.
 */
@SpringBootApplication
@Slf4j
public class Application {

    public static void main(final String... args) {
        final ApplicationContext ctx = SpringApplication.run(Application.class, args);
        final String[] beanNames = ctx.getBeanDefinitionNames();

        Arrays.stream(beanNames)
                .sorted()
                .forEach(log::debug);
    }

}
