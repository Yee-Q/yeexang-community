package top.yeexang.community.service;

import top.yeexang.community.dto.NotificationDTO;
import top.yeexang.community.entity.Comment;
import top.yeexang.community.entity.Notification;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/12/12
 */
public interface NotificationSev {

    /**
     * 创建通知
     * @param comment 评论
     * @param receiver 接收者
     * @param notifierName 通知者名称
     * @param outerTitle 父类标题
     * @param type 通知类型
     */
    void createNotification(Comment comment, Long receiver, String notifierName, String outerTitle, int type);

    /**
     * 获取对应接收者的所有未接收消息
     * @param id 接收者id
     * @return 所有未接收消息数量
     */
    List<NotificationDTO> getNewNotificationList(Long id);

    /**
     * 将通知消息实体转换为可返回的数据传输实体
     * @param notification 消息通知
     * @return 数据传输对象
     */
    NotificationDTO getNotificationDTO(Notification notification);
}
