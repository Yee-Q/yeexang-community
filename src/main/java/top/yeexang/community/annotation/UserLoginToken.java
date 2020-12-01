package top.yeexang.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>每次操作拦截器都会扫描该注解是否存在，如果存在且属性为 true，则该操作必须登录才能完成<p/>
 *
 * @author yeeq
 * @date 2020/10/4
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
}
