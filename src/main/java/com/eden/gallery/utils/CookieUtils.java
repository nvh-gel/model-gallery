package com.eden.gallery.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

/**
 * Handler for cookies.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtils {

    private static final Gson GSON = new Gson();

    /**
     * Retrieve saved cookies for http request.
     *
     * @param request http request
     * @param name    cookie name
     * @return found cookie
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && 0 < cookies.length) {
            return Arrays.stream(cookies).filter(c -> name.equals(c.getName())).findFirst();
        }
        return Optional.empty();
    }

    /**
     * Serialize object to string.
     *
     * @param object object to serialize
     * @return serialized string
     */
    public static String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(GSON.toJson(object).getBytes());
    }

    /**
     * Deserialize json string in cookie to object.
     *
     * @param cookie       cookie to deserialize
     * @param requestClass class to cast to
     * @param <T>          class to cast to
     * @return object of class <T>
     */
    public static <T> T deserialize(Cookie cookie, Class<T> requestClass) {
        return GSON.fromJson(new String(Base64.getUrlDecoder().decode(cookie.getValue())), requestClass);
    }

    /**
     * Remove saved request cookie.
     *
     * @param request  http request
     * @param response http response
     * @param name     cookie name
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            Cookie cookie = Arrays.stream(cookies).filter(c -> name.equals(c.getName())).findFirst().orElse(null);
            if (null != cookie) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    /**
     * Add cookie to response.
     *
     * @param response http response
     * @param name     cookie name
     * @param value    cookie value
     * @param maxAge   time in second for cookie to expire
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
