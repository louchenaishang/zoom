package com.github.louchen.zoom.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * rest结果
 *
 * @author louchen
 */
@Slf4j
public class ResultUtils {

    /**
     * JSON内容类型
     */
    private static final String JSON_CONTENT_TYPE = "application/json";

    /**
     * 消息KEY
     */
    private static final String MESSAGE_KEY = "message";

    /**
     * PATHKEY
     */
    private static final String PATH_KEY = "path";

    /**
     * 构造方法
     */
    private ResultUtils() {
    }

    /**
     * 设置状态
     *
     * @param response   HttpServletResponse
     * @param httpStatus HttpStatus
     * @param message    消息
     * @param args       参数
     */
    public static void status(HttpServletResponse response, HttpStatus httpStatus, String message, Object... args) {
        AssertUtils.notNull(response);
        AssertUtils.notNull(httpStatus);
        AssertUtils.hasText(message);

        Map<String, String> data = new HashMap<>();
        data.put(MESSAGE_KEY, SpringUtils.getMessage(message, args));
        data.put(PATH_KEY, getPath());

        response.setContentType(JSON_CONTENT_TYPE);
        response.setStatus(httpStatus.value());
        try {
            JsonUtils.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 返回状态ResponseEntity
     *
     * @param httpStatus HttpStatus
     * @param message    消息
     * @param args       参数
     * @return ResponseEntity
     */
    public static ResponseEntity<Map<String, String>> status(HttpStatus httpStatus, String message, Object... args) {
        AssertUtils.notNull(httpStatus);
        AssertUtils.hasText(message);

        Map<String, String> data = new HashMap<>();
        data.put(MESSAGE_KEY, SpringUtils.getMessage(message, args));
        data.put(PATH_KEY, getPath());

        return new ResponseEntity<>(data, httpStatus);
    }

    private static String getPath() {
        return WebUtils.getRequest() == null ? null : WebUtils.getRequest().getRequestURI();
    }

}
