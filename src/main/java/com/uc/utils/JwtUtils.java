package com.uc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.uc.entity.CurrentUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

    private static String signKey = "ucmeetingserverdx3906";
    private static Long expire = 43200000L;

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    public static CurrentUserInfo getCurrentUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return new CurrentUserInfo(jwt.getClaim("username").asString(),
                    jwt.getClaim("name").asString(),
                    jwt.getClaim("permission").asString(),
                    jwt.getClaim("id").asInt());
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
