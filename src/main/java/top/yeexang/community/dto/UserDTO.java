package top.yeexang.community.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/10/1
 */
@Data
@Accessors(chain = true)
public class UserDTO {

    private Long id;
    private String name;
    private String avatarUrl;
    private Integer level;
    private Boolean vip;
    private Integer exp;
}
