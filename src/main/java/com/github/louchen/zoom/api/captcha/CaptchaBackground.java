package com.github.louchen.zoom.api.captcha;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.util.Configurable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Captcha - 验证码背景
 *
 * @author louchen
 */
@Slf4j
public class CaptchaBackground extends Configurable implements BackgroundProducer {

    /**
     * 背景图片缓存
     */
    private static final List<BufferedImage> BACKGROUND_IMAGES_CACHE = new CopyOnWriteArrayList<>();

    /**
     * 添加背景
     *
     * @param baseImage 基本图片
     * @return 目标图片
     */
    @Override
    public BufferedImage addBackground(BufferedImage baseImage) {
        List<BufferedImage> backgroundImages = null;
        try {
            backgroundImages = getBackgroundImages();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isEmpty(backgroundImages)) {
            return baseImage;
        }

        Graphics2D graphics2D = null;
        try {
            int width = baseImage.getWidth();
            int height = baseImage.getHeight();
            BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            graphics2D = destImage.createGraphics();
            int random = RandomUtils.nextInt(backgroundImages.size());
            BufferedImage backgroundImage = backgroundImages.get(random);
            graphics2D.drawImage(backgroundImage, 0, 0, null);
            graphics2D.drawImage(baseImage, 0, 0, null);
            return destImage;
        } finally {
            if (graphics2D != null) {
                graphics2D.dispose();
            }
        }
    }

    /**
     * 获取背景图片
     *
     * @return 背景图片
     */
    public synchronized List<BufferedImage> getBackgroundImages() throws FileNotFoundException {
        if (CollectionUtils.isNotEmpty(BACKGROUND_IMAGES_CACHE)) {
            return BACKGROUND_IMAGES_CACHE;
        }

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("/captcha/*.jpg");
            for (Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                BufferedImage backgroundImage = ImageIO.read(inputStream);
                if (backgroundImage != null) {
                    BACKGROUND_IMAGES_CACHE.add(backgroundImage);
                }
            }
        } catch (IOException e) {
            if (log.isWarnEnabled()) {
                log.warn("读取文件流失败", e);
            }
        }

        return BACKGROUND_IMAGES_CACHE;
    }

}