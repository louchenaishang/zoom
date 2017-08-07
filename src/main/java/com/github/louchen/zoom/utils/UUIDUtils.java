package com.github.louchen.zoom.utils;

import java.util.UUID;

/**
 * UUID - uuid
 *
 * @author louchen
 */
public class UUIDUtils {

    /**
     * 获取32位长度的uuid
     *
     * @return
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

}
