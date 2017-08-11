package com.github.louchen.zoom.utils;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

/**
 * Utils - 断言
 *
 * @author louchen
 */
public class AssertUtils extends Assert {

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

}
