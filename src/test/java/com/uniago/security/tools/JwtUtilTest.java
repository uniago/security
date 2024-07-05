package com.uniago.security.tools;

import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class JwtUtilTest {

    @Test
    public void testCreateJWT() {
        String jwt = JwtUtil.createJWT("123456");
        System.out.println("jwt = " + jwt);
        jwt = JwtUtil.createJWT("123456");
        System.out.println("jwt = " + jwt);
    }

    @Test
    public void testParseJWT() {
        // 第一部分base64 {"alg":"HS256"}
        // 第二部分base64 {"jti":"817aecfcef2d4a8e9ab0b86ea6139b4c","sub":"123456","iss":"Jan","iat":1719909878,"exp":1719913478}
        // 第三部分无法base64
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MTdhZWNmY2VmMmQ0YThlOWFiMGI4NmVhNjEzOWI0YyIsInN1YiI6IjEyMzQ1NiIsImlzcyI6IkphbiIsImlhdCI6MTcxOTkwOTg3OCwiZXhwIjoxNzE5OTEzNDc4fQ.74bX2JsLWfxIR5cJ6ocjEAdiMIBKBs5IhrNZbBdmCwY";
        Claims claims = JwtUtil.parseJWT(token);
        System.out.println("claims = " + claims);
    }

    @Test
    public void testBcrypt() {
        String password = "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        String encode2 = encoder.encode(password);
        // $2a$10$rE6cf811INTvCVOqlOQbz.svexabvlTEO9GjuPQ87hr9IssaLiY/q
        System.out.println("encode = " + encode);
        // $2a$10$6NFOCciQiN5jOrnOZMeVQO7ra6igXno8ImENm0Ofh5LpbG/yt9kFK
        System.out.println("encode2 = " + encode2);
        boolean matches = encoder.matches(password, "$2a$10$6NFOCciQiN5jOrnOZMeVQO7ra6igXno8ImENm0Ofh5LpbG/yt9kFK");
        System.out.println("matches = " + matches);
    }
}