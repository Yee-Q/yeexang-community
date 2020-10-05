package top.yeexang.community.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/10/3
 */
@Data
@Accessors(chain = true)
public class UserInfo {

    private Long id;
    private Long userId;
    private String realname;
    private String userdetail;
    private String birthday;
    private String marriage;
    private String sex;
    private String blood;
    private String figure;
    private String constellation;
    private String education;
    private String trade;
    private String job;
    private String location;
}
