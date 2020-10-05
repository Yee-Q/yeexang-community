package top.yeexang.community.service;

import top.yeexang.community.dto.ResultDTO;

/**
 * @author yeeq
 * @date 2020/10/3
 */
public interface UserSev {

    /**
     * 用户注册
     *
     * @param email    注册邮箱
     * @param username 用户昵称
     * @param pass     用户密码
     * @return {@link ResultDTO}
     */
    ResultDTO<?> register(String email, String username, String pass);

    /**
     * 用户登入
     *
     * @param email    登入邮箱
     * @param pass     用户密码
     * @return {@link ResultDTO}
     */
    ResultDTO<?> login(String email, String pass);
}