package com.uniago.security.tools;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author uniago
 * Create by 2024/07/02 15:47
 */
public class WebUtils {

    /**
     * 将字符串渲染到客户端
     * @param response 渲染对象
     * @param str 待渲染的字符串
     * @return 原字符串
     */
    public static void render(HttpServletResponse response, String str) {
        try {
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(str);
        } catch (IOException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
    }
}
