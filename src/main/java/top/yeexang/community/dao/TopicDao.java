package top.yeexang.community.dao;

import org.springframework.stereotype.Repository;
import top.yeexang.community.entity.Topic;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/7
 */
@Repository
public interface TopicDao {

    /**
     * 添加帖子信息
     *
     * @param topic 待添加的帖子信息
     * @return 成功执行行数
     */
    Integer createTopic(Topic topic);

    /**
     * 获取所有帖子信息列表
     * @return 所有帖子信息列表
     */
    List<Topic> selectAllTopics();
}
