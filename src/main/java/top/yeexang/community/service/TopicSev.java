package top.yeexang.community.service;

import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.Topic;

import java.util.List;

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
     * 将帖子实体转换为可返回的数据传输实体
     *
     * @param topic 帖子
     * @return 数据传输对象
     */
    TopicDTO getTopicDTO(Topic topic);

    /**
     * 通过 id 获取可返回的数据传输实体
     * @param id 帖子 id
     * @return 数据传输对象
     */
    TopicDTO visitTopic(Long id);

    /**
     * 获取最新帖子列表
     * @return 最新帖子列表
     */
    List<TopicDTO> getNewTopicList();

    /**
     * 增加帖子阅读数
     * @param id 帖子id
     */
    void incView(Long id);

    /**
     * 增加帖子评论数
     * @param id 帖子id
     */
    void incTopicComment(Long id);

    /**
     * 删除帖子
     * @param id 帖子id
     */
    void deleteTopic(Long id);
}
