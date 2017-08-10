package com.github.louchen.zoom.config;

import com.github.louchen.zoom.api.captcha.CaptchaBackground;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * 配置类 - 验证码
 *
 * @author louchen
 */
@Configuration
public class CaptchaConfig {

    @Autowired
    public Environment env;

    @Bean(name = "captchaProducer")
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.image.width", env.getProperty("captcha.imageWidth"));
        properties.setProperty("kaptcha.image.height", env.getProperty("captcha.imageHeight"));
        properties.setProperty("kaptcha.textproducer.char.string", env.getProperty("captcha.charString"));
        properties.setProperty("kaptcha.textproducer.char.length", env.getProperty("captcha.charLength"));
        properties.setProperty("kaptcha.textproducer.char.space", env.getProperty("captcha.charSpace"));
        properties.setProperty("kaptcha.textproducer.font.color", env.getProperty("captcha.fontColor"));
        properties.setProperty("kaptcha.textproducer.font.size", env.getProperty("captcha.fontSize"));
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        properties.setProperty("kaptcha.background.impl", "com.github.louchen.zoom.api.captcha.CaptchaBackground");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

    @Bean
    public CaptchaBackground captchaBackground(){
        CaptchaBackground captchaBackground = new CaptchaBackground();
        captchaBackground.getBackgroundImages();

        return captchaBackground;
    }

    /*
     1 kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
     2 kaptcha.border.color   边框颜色   默认为Color.BLACK
     3 kaptcha.border.thickness  边框粗细度  默认为1
     4 kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
     5 kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
     6 kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
     7 kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
     8 kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
     9 kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
    10 kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
    11 kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
    12 kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
    13 kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
    14 kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
    15 kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
    16 kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
    17 kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
    18 kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
    19 kaptcha.image.width   验证码图片宽度  默认为200
    20 kaptcha.image.height  验证码图片高度  默认为50
     */

}
