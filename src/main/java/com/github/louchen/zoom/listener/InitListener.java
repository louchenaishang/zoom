package com.github.louchen.zoom.listener;

import com.github.louchen.zoom.api.captcha.CaptchaBackground;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
@Slf4j
public class InitListener {

    @Value("${sys.version}")
    private String systemVersion;


    @Value("${sys.storage}")
    private String systemStorage;

    /**
     * 事件处理
     *
     * @param contextRefreshedEvent ContextRefreshedEvent
     */
    @EventListener
    public void handle(ContextRefreshedEvent contextRefreshedEvent) throws FileNotFoundException {
        if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
            return;
        }
        //加载验证码背景图片
        CaptchaBackground captchaBackground = new CaptchaBackground();
        captchaBackground.getBackgroundImages();

        log.info("Initializing Project version:{},storage:{}", systemVersion, systemStorage);
    }

}
