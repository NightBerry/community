package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author HAN
 * @date 2022/4/17 11:37
 */
public class CookieUtil {

    /**
     * 从浏览器传回的cookie中取值
     * 用于拦截器
     *
     * @param request   使用cookie需要用到request对象
     * @param name      要取的值name是什么
     * @return          name对应cookie中的值
     */
    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数为空!");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
