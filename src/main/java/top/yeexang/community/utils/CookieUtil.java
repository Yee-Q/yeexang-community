package top.yeexang.community.utils;

import javax.servlet.http.Cookie;

/**
 * @author yeeq
 * @date 2020/10/4
 */
public class CookieUtil {

    /**
     * 获取 Cookie
     *
     * @param key cookie 键
     * @param value cookie 值
     * @param time 过期时间
     * @return {@link Cookie}
     */
    public static Cookie getCookie(String key, String value, int time, String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(time);
        cookie.setDomain(domain);
        cookie.setPath("/");
        return cookie;
    }
}
