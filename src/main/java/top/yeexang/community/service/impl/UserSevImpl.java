package top.yeexang.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeexang.community.dao.UserDao;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.User;
import top.yeexang.community.entity.UserAccount;
import top.yeexang.community.entity.UserInfo;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.service.UserSev;
import top.yeexang.community.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yeeq
 * @date 2020/10/3
 */
@Service
@Transactional
public class UserSevImpl implements UserSev {

    @Autowired
    private UserDao userDao;

    @Override
    public ResultDTO<?> register(String email, String username, String pass) {
        // 邮箱是否已经被使用
        if (userDao.selectUserByMail(email) != null) {
            return ResultDTO.errorOf(ResponseCodeEnum.EMAIL_ALREADY_EXISTS);
        }
        // 昵称是否已经被使用
        if (userDao.selectUserByName(username) != null) {
            return ResultDTO.errorOf(ResponseCodeEnum.NAME_ALREADY_EXISTS);
        }
        // 添加用户信息
        User user = new User();
        user.setName(username).setMail(email).setPassword(pass).setAvatarUrl("/images/default-avatar.png'")
                .setGmtCreate(System.currentTimeMillis()).setGmtModified(System.currentTimeMillis());
        userDao.createUser(user);
        User dbUser = userDao.selectUserByMail(email);
        // 初始化账户信息并添加
        UserAccount userAccount = new UserAccount();
        initUserAccount(dbUser, userAccount);
        userDao.createUserAccount(userAccount);
        // 初始化用户资料信息并添加
        UserInfo userInfo = new UserInfo();
        initUserInfo(dbUser, userInfo);
        userDao.createUserInfo(userInfo);
        // 封装数据传输对象并返回
        return getResultDTO(dbUser);
    }

    @Override
    public ResultDTO<?> login(String email, String pass) {

        User user = userDao.selectUserByMail(email);
        if (user == null) {
            return ResultDTO.errorOf(ResponseCodeEnum.EMAIL_ERROR_OR_NOT_EXISTS);
        }
        if (!user.getPassword().equals(pass)) {
            return ResultDTO.errorOf(ResponseCodeEnum.PASS_ERROR);
        }
        return getResultDTO(user);
    }

    /**
     * 封装数据传输对象并返回
     *
     * @param user 用户
     * @return 数据传输对象
     */
    public ResultDTO<?> getResultDTO(User user) {

        UserDTO userDTO = getUserDTO(user);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(userDTO));
        Map<String, String> map = new HashMap<>(16, 0.75f);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return ResultDTO.successOf(JwtUtil.getToken(map));
    }

    /**
     * 初始化账户信息
     *
     * @param user        该账户对应的用户
     * @param userAccount 账户
     */
    public void initUserAccount(User user, UserAccount userAccount) {

        userAccount.setUserId(user.getId());
        userAccount.setLevel(1);
        userAccount.setVip(false);
        userAccount.setUserExp(0);
    }

    /**
     * 初始化用户资料
     *
     * @param user     用户
     * @param userInfo 用户资料
     */
    public void initUserInfo(User user, UserInfo userInfo) {
        userInfo.setUserId(user.getId());
    }

    /**
     * 将用户实体转换为可返回的数据传输实体
     *
     * @param user 用户
     * @return 数据传输对象
     */
    public UserDTO getUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        UserAccount userAccount = userDao.selectUserAccountByUserId(user.getId());
        userDTO.setLevel(userAccount.getLevel()).setVip(userAccount.getVip()).setUserExp(userAccount.getUserExp());
        return userDTO;
    }

}