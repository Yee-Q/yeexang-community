package top.yeexang.community.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.yeexang.community.entity.Notification;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/12/12
 */
@Repository
public interface NotificationDao {

    /**
     * 创建通知
     * @param notification 通知
     */
    void createNotification(Notification notification);

    /**
     * 根据接收者id获取所有未接收消息
     * @param id 接收者id
     * @return 所有未接收消息
     */
    List<Notification> selectNotReceiveNotificationByReceiverId(@Param("id") Long id);
}
