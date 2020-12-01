package top.yeexang.community.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yeexang.community.entity.User;
import top.yeexang.community.entity.UserAccount;
import top.yeexang.community.entity.UserInfo;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/3
 */
@Repository
public interface UserDao {

    /**
     * 根据id查询用户信息
     *
     * @param id id
     * @return 符合查询条件的用户信息
     */
    User selectUserById(@Param("id") Long id);

    /**
     * 根据邮箱查询用户信息
     *
     * @param mail 邮箱
     * @return 符合查询条件的用户信息
     */
    User selectUserByMail(@Param("mail") String mail);

    /**
     * 根据用户昵称查询用户信息
     *
     * @param name 用户昵称
     * @return 符合条件的用户信息
     */
    User selectUserByName(@Param("name") String name);

    /**
     * 根据用户 id 查询账户信息
     *
     * @param userId 用户id
     * @return 符合条件的账户信息
     */
    UserAccount selectUserAccountByUserId(@Param("userId") Long userId);

    /**
     * 添加用户信息
     *
     * @param user 待添加的用户信息
     */
    void createUser(User user);

    /**
     * 添加账户信息
     *
     * @param userAccount 待添加的账户信息
     */
    void createUserAccount(UserAccount userAccount);

    /**
     * 添加用户资料信息
     *
     * @param userInfo 用户资料
     */
    void createUserInfo(UserInfo userInfo);
}