package top.yeexang.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeexang.community.dao.NotificationDao;
import top.yeexang.community.dto.NotificationDTO;
import top.yeexang.community.entity.Comment;
import top.yeexang.community.entity.Notification;
import top.yeexang.community.service.NotificationSev;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2020/12/12
 */
@Service
@Transactional
public class NotificationSevImpl implements NotificationSev {

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public void createNotification(Comment comment, Long receiver, String notifierName, String outerTitle, int type) {

        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(0);
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationDao.createNotification(notification);
    }

    @Override
    public List<NotificationDTO> getNewNotificationList(Long id) {

        List<Notification> notifications = notificationDao.selectNotReceiveNotificationByReceiverId(id);
        List<NotificationDTO> notificationDTOS = notifications.stream().map(this::getNotificationDTO).collect(Collectors.toList());
        return notificationDTOS;
    }

    @Override
    public NotificationDTO getNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        if (notification.getType() == 1) {
            notificationDTO.setTypeName("回复了帖子");
        } else if(notification.getType() == 2) {
            notificationDTO.setTypeName("回复了评论");
        } else {
            notificationDTO.setTypeName("系统通知：");
        }
        return notificationDTO;
    }
}
