package top.yeexang.community.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/10/3
 */
@Data
@Accessors(chain = true)
public class User {

    private Long id;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String mail;
    private String password;
}
