package top.yeexang.community.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author yeeq
 * @date 2020/10/4
 */
public class JwtUtil {

    private static final String SECRET = "n2B69tcq2Uqpaax0y7BTJTgOypSueYOS";

    private static final Integer EXPIRESTIME = 3;

    /**
     * 生成 token
     *
     * @param map 有效负载，其中包含声明。声明是有关实体（通常是用户）和其他数据的声明
     * @return 令牌
     */
    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, EXPIRESTIME);
        JWTCreator.Builder builder = JWT.create();
        /* 创建 payload */
        map.forEach(builder::withClaim);
        /* 指定令牌的过期时间 */
        String token = builder.withExpiresAt(instance.getTime())
                /* 签名，并指定密钥 */
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 验证 token 的合法性，并返回有效负载的信息
     * @param token 令牌
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        Map<String, Claim> map = verify.getClaims();
        return map;
    }
}