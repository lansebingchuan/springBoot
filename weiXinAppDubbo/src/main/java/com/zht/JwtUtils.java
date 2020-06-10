package com.zht;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * jwt令牌工具类
 *
 * @author ZHT
 */
public class JwtUtils {
    /**
     * 生成jwt
     *
     * @param subject 标识
     * @param secret  密匙
     * @return jwt
     */
    public static String generate(Object subject, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(String.valueOf(subject))
                .sign(algorithm);
    }

    /**
     * 获取jwt中的标识
     *
     * @param jwt jwt
     * @return 标识
     */
    public static String getSubject(String jwt) {
        DecodedJWT decodedJwt = JWT.decode(jwt);
        return decodedJwt.getSubject();
    }

    /**
     * 效验jwt
     *
     * @param jwt     jwt
     * @param subject 标识
     * @param secret  密匙
     * @return 是否效验成功
     */
    public static boolean validate(String jwt, Object subject, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(String.valueOf(subject))
                    .build();
            verifier.verify(jwt);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
