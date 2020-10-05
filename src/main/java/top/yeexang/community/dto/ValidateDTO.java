package top.yeexang.community.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yeeq
 * @date 2020/10/2
 */
@Data
@Accessors(chain = true)
public class ValidateDTO {

    private Integer socre;
    private Integer success;
    private String msg;
}
