package top.yeexang.community.cache;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * @author yeeq
 * @date 2020/10/3
 */
public class MailCodeCache {

    ExpiringMap<String, String> mailCode = ExpiringMap.builder()
            // 最大容量，防止恶意注入
            .maxSize(200)
            // 过期时间五分钟
            .expiration(5, TimeUnit.MINUTES)
            // 配置过期策略，到时即过期
            .expirationPolicy(ExpirationPolicy.CREATED)
            .variableExpiration()
            .build();

    /**
     * 将邮箱与对应验证码放入缓存
     */
    public void putMailCode(String mail, String code) {
        mailCode.put(mail, code);
    }

    /**
     * 从缓存获取邮箱对应的验证码
     */
    public String getMailCode(String mail) {
        return mailCode.get(mail);
    }
}