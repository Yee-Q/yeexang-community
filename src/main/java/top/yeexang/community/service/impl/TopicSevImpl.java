package top.yeexang.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeexang.community.dao.CategoryDao;
import top.yeexang.community.dao.CommentDao;
import top.yeexang.community.dao.TopicDao;
import top.yeexang.community.dao.UserDao;
import top.yeexang.community.dto.CommentDTO;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.Category;
import top.yeexang.community.entity.Topic;
import top.yeexang.community.entity.User;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.exception.CustomizeException;
import top.yeexang.community.service.CommentSev;
import top.yeexang.community.service.TopicSev;
import top.yeexang.community.service.UserSev;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2020/10/7
 */
@Service
@Transactional
public class TopicSevImpl implements TopicSev {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserSev userSev;

    @Autowired
    private CommentSev commentSev;

    @Override
    public ResultDTO<?> publishTopic(TopicDTO topicDTO, UserDTO userDTO) {

        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDTO, topic);
        topic.setGmtCreate(System.currentTimeMillis()).setGmtModified(System.currentTimeMillis())
                .setCreator(userDTO.getId()).setCommentCount(0).setViewCount(1).setLikeCount(0)
                .setTag(topicDTO.getTag()).setGmtLatestComment(System.currentTimeMillis())
                .setStatus(0).setCategoryId(topicDTO.getCategoryId());
        topicDao.createTopic(topic);
        return ResultDTO.successOf();
    }

    @Override
    public TopicDTO getTopicDTO(Topic topic) {

        TopicDTO topicDTO = new TopicDTO();
        BeanUtils.copyProperties(topic, topicDTO);
        User creator = userDao.selectUserById(topic.getCreator());
        topicDTO.setCreator(userSev.getUserDTO(creator));
        Category category = categoryDao.selectCategoryById(topic.getCategoryId());
        topicDTO.setCategory(category);
        return topicDTO;
    }

    @Override
    public TopicDTO visitTopic(Long id) {
        // 增加帖子阅读数
        incView(id);
        Topic topic = topicDao.selectTopicById(id);
        if (topic == null) {
            throw new CustomizeException(ResponseCodeEnum.TOPIC_NOT_EXISTS);
        }
        TopicDTO topicDTO = getTopicDTO(topic);
        return topicDTO;
    }

    @Override
    public List<TopicDTO> getNewTopicList() {
        List<Topic> topics = topicDao.selectHotTopics(20);
        List<TopicDTO> topicDTOList = topics.stream().map(this::getTopicDTO).collect(Collectors.toList());
        return topicDTOList;
    }

    @Override
    public void incView(Long id) {
        topicDao.updateTopicViewCountById(id);
    }

    @Override
    public void incTopicComment(Long id) {
        topicDao.updateTopicCommentCountById(id);
    }

    @Override
    public void deleteTopic(Long id) {
        // 首先把帖子下所有评论删除
        commentSev.deleteCommentByParentId(id);
        // 删除帖子
        topicDao.deleteTopicById(id);
    }

    @Override
    public Topic getTopic(Long id) {
        return topicDao.selectTopicById(id);
    }
}
