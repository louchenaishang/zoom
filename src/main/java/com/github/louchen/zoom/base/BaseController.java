package com.github.louchen.zoom.base;

import com.github.louchen.zoom.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by louchen on 2017/9/15.
 */
@Slf4j
public class BaseController {

    /**
     * "验证结果"属性名称
     */
    private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

    @Autowired
    private Validator validator;

    /**
     * 数据验证
     *
     * @param target
     *            验证对象
     * @param groups
     *            验证组
     * @return 验证结果
     */
    protected boolean isValid(Object target, Class<?>... groups) {
        AssertUtils.notNull(target);

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
        if (constraintViolations.isEmpty()) {
            return true;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
        return false;
    }

    /**
     * 数据验证
     *
     * @param targets
     *            验证对象
     * @param groups
     *            验证组
     * @return 验证结果
     */
    protected boolean isValid(Collection<Object> targets, Class<?>... groups) {
        AssertUtils.notEmpty(targets);

        for (Object target : targets) {
            if (!isValid(target, groups)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数据验证
     *
     * @param type
     *            类型
     * @param property
     *            属性
     * @param value
     *            值
     * @param groups
     *            验证组
     * @return 验证结果
     */
    protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
        AssertUtils.notNull(type);
        AssertUtils.hasText(property);

        Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
        if (constraintViolations.isEmpty()) {
            return true;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
        return false;
    }

    /**
     * 数据验证
     *
     * @param type
     *            类型
     * @param properties
     *            属性
     * @param groups
     *            验证组
     * @return 验证结果
     */
    protected boolean isValid(Class<?> type, Map<String, Object> properties, Class<?>... groups) {
        AssertUtils.notNull(type);
        AssertUtils.notEmpty(properties);

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (!isValid(type, entry.getKey(), entry.getValue(), groups)) {
                return false;
            }
        }
        return true;
    }
    
}
