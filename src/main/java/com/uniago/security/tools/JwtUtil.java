package com.uniago.security.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类 json web token
 * @author uniago
 * Create by 2024/07/02 14:34
 */
public class JwtUtil {

    /**
     * 有效期
     * 默认一小时
     */
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    /**
     * 密钥key
     */
    public static final String JWT_KEY = "uniago";

    /**
     * 生成随机字符串
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成加密后的密钥
     * @return secretKey
     */
    public static SecretKey generalKey() {
        byte[] key = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(key, 0, key.length, "AES");
    }

    /**
     * 构建jwt builder
     * @param subject 要保存的数据
     * @param ttlMillis 过期时间
     * @param uuid 唯一id
     * @return jwt builder
     */
    public static JwtBuilder jwtBuilder(String subject, Long ttlMillis, String uuid) {
        // 加密算法
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        // 计算当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 设置默认过期长
        ttlMillis = ttlMillis == null ? JwtUtil.JWT_TTL : ttlMillis;
        // 计算过期时间
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)    // 设置唯一id
                .setSubject(subject)    // 主体,可以是JSON数据
                .setIssuer("Jan")   // 签发者
                .setIssuedAt(now)   // 签发时间
                .signWith(hs256, secretKey) // 签名算法和密钥
                .setExpiration(expDate);    // 失效时间
    }

    /**
     * 生成JWT
     * @param subject token中要存放的数据(json格式)
     * @param ttlMillis token超时时间(毫秒)
     * @param uuid 唯一id
     * @return jwt
     */
    public static String createJWT(String subject, Long ttlMillis, String uuid) {
        JwtBuilder jwtBuilder = jwtBuilder(subject, ttlMillis, uuid);
        return jwtBuilder.compact();
    }

    /**
     * 生成JWT
     * @param subject token中要存放的数据(json格式)
     * @param ttlMillis token超时时间(毫秒)
     * @return jwt
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return createJWT(subject, ttlMillis, uuid());
    }

    /**
     * 生成JWT
     * @param subject token中要存放的数据(json格式)
     * @return jwt
     */
    public static String createJWT(String subject) {
        return createJWT(subject, null);
    }

    /**
     * 解析JWT
     * @param jwt 加密后的token
     * @return 加密前的明文
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
