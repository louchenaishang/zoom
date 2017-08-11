package com.github.louchen.zoom.api.captcha.interceptor;


import com.github.louchen.zoom.api.captcha.service.CaptchaService;
import com.github.louchen.zoom.utils.AssertUtils;
import com.github.louchen.zoom.utils.SpringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Captcha - 验证码拦截器
 *
 * @author louchen
 */
public class CaptchaInterceptor extends HandlerInterceptorAdapter {

    /**
     * 默认无需防护的请求方法
     */
    private static final String[] DEFAULT_NOT_REQUIRE_PROTECTION_REQUEST_METHODS = new String[]{"HEAD", "TRACE", "OPTIONS"};

    /**
     * "验证码ID"参数名称
     */
    private static final String CAPTCHA_ID_PARAMETER_NAME = "captchaId";

    /**
     * "验证码"参数名称
     */
    private static final String CAPTCHA_PARAMETER_NAME = "captcha";

    /**
     * 验证出错信息
     */
    private static final String CAPTCHA_INVALID_MESSAGE = "验证码输入错误";

    /**
     * 无需防护的请求方法
     */
    private String[] notRequireProtectionRequestMethods = DEFAULT_NOT_REQUIRE_PROTECTION_REQUEST_METHODS;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 请求前处理
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String captchaId = request.getParameter(CAPTCHA_ID_PARAMETER_NAME);
        String captcha = request.getParameter(CAPTCHA_PARAMETER_NAME);
        AssertUtils.isTrue(
                !(!containsIgnoreCase(getNotRequireProtectionRequestMethods(), request.getMethod()) && !captchaService.isValid(captchaId, captcha))
                , SpringUtils.getMessage("common.message.incorrectCaptcha"));

        return super.preHandle(request, response, handler);
    }

    /**
     * 判断数组是否包含字符串
     *
     * @param array     数组
     * @param searchStr 查找字符串(忽略大小写)
     * @return 是否包含字符串
     */
    private boolean containsIgnoreCase(String[] array, String searchStr) {
        if (ArrayUtils.isNotEmpty(array) && searchStr != null) {
            for (String str : array) {
                if (StringUtils.equalsIgnoreCase(str, searchStr)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取无需防护的请求方法
     *
     * @return 无需防护的请求方法
     */
    public String[] getNotRequireProtectionRequestMethods() {
        return notRequireProtectionRequestMethods;
    }

}