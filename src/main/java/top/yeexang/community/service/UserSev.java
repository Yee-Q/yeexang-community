package top.yeexang.community.service;

import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.User;

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

    /**
     * 将用户实体转换为可返回的数据传输实体
     *
     * @param user 用户
     * @return 数据传输对象
     */
    UserDTO getUserDTO(User user);
}