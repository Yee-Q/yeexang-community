package top.yeexang.community.dto;

import lombok.Data;

/**
 * @author yeeq
 * @date 2020/12/25
 */
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
