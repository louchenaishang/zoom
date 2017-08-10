package com.github.louchen.zoom.api.captcha.service;

import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * Service - 验证码
 *
 * @author louchen
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private Producer captchaProducer;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final int live = 3;

    public BufferedImage createImage(String captchaId) {
        Assert.hasText(captchaId);
        String keyInRedis = getKey(captchaId);

        String captcha = captchaProducer.createText();
        stringRedisTemplate.opsForValue().set(keyInRedis, captcha, 3, TimeUnit.MINUTES);

        return captchaProducer.createImage(captcha);
    }

    public boolean isValid(String captchaId, String captcha) {
        if (StringUtils.isEmpty(captchaId) || StringUtils.isEmpty(captcha)) {
            return false;
        }
        String keyInRedis = getKey(captchaId);

        String captchaInRedis = stringRedisTemplate.opsForValue().get(keyInRedis);
        if (StringUtils.isNotBlank(captchaInRedis)) {
            stringRedisTemplate.delete(keyInRedis);
            return StringUtils.equalsIgnoreCase(captcha, captchaInRedis);
        }

        return false;
    }

    /**
     * 获取redisKey
     *
     * @param captchaId
     * @return
     */
    private String getKey(String captchaId) {
        return new StringBuilder().append(appName).append("-").append(captchaId).toString();
    }

}