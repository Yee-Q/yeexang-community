package top.yeexang.community.service;

import com.github.pagehelper.PageInfo;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.Topic;

/**
 * @author yeeq
 * @date 2020/10/7
 */
public interface TopicSev {

    /**
     * 发布帖子
     *
     * @param topicDTO 帖子相关信息
     * @param userDTO      发布者信息
     * @return {@link ResultDTO}
     */
    ResultDTO<?> publishTopic(TopicDTO topicDTO, UserDTO userDTO);

    /**
     * 展示所有帖子
     * @param pageNum 页码
     * @param pageSize 页长
     * @return 包含所有帖子的列表
     */
    PageInfo<Topic> showAllTopic(Integer pageNum, Integer pageSize);

    /**
     * 将帖子实体转换为可返回的数据传输实体
     *
     * @param topic 帖子
     * @return 数据传输对象
     */
    TopicDTO getTopicDTO(Topic topic);
}
