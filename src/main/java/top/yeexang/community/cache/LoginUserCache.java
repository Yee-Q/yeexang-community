package top.yeexang.community.cache;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.stereotype.Component;
import top.yeexang.community.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yeeq
 * @date 2020/10/5
 */
@Component
public class LoginUserCache {

    /**
     * 登录用户缓存
     */
    private List<User> loginUsers = new ArrayList<>();

    /**
     * 可以设置过期时间的，线程安全的 ConcurrentMap
     */
    private final ExpiringMap<Long, Long> loginUserMap = ExpiringMap.builder()
            //最大容量，防止恶意注入
            .maxSize(16)
            //过期时间十分钟
            .expiration(10, TimeUnit.MINUTES)
            // 配置过期策略，到时即过期
            .expirationPolicy(ExpirationPolicy.CREATED)
            .variableExpiration()
            .build();

    /**
     * 更新登录用户缓存
     */
    public void updateLoginUsers(List<User> loginUsers) {

        this.loginUsers = loginUsers;
    }

    /**
     * 将登录用户信息放入缓存
     */
    public void putLoginUser(Long uid, Long gmt) {

        loginUserMap.put(uid, gmt);
    }
}