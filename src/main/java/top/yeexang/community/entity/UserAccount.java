package top.yeexang.community.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/10/3
 */
@Data
@Accessors(chain = true)
public class UserAccount {

    private Long id;
    private Long userId;
    private Integer level;
    private Boolean vip;
    private Integer userExp;
}
