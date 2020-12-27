package top.yeexang.community.entity;

import lombok.Data;

/**
 * @author yeeq
 * @date 2020/12/12
 */
@Data
public class Notification {

    private Long id;
    private Long notifier;
    private String notifierName;
    private Long receiver;
    private Long outerid;
    private String outerTitle;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
}
