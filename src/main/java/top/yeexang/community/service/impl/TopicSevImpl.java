package top.yeexang.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeexang.community.dao.CategoryDao;
import top.yeexang.community.dao.TopicDao;
import top.yeexang.community.dao.UserDao;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.entity.Category;
import top.yeexang.community.entity.Topic;
import top.yeexang.community.entity.User;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.service.TopicSev;
import top.yeexang.community.service.UserSev;

import java.util.List;

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

    @Override
    public ResultDTO<?> publishTopic(TopicDTO topicDTO, UserDTO userDTO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDTO, topic);
        topic.setGmtCreate(System.currentTimeMillis()).setGmtModified(System.currentTimeMillis())
                .setCreator(userDTO.getId()).setCommentCount(0).setViewCount(1).setLikeCount(0)
                .setTag(topicDTO.getTag()).setGmtLatestComment(System.currentTimeMillis())
                .setStatus(0).setCategoryId(topicDTO.getCategoryId());
        if (topicDao.createTopic(topic) != 1) {
            return ResultDTO.errorOf(ResponseCodeEnum.REQUEST_FAILED);
        }
        return ResultDTO.successOf();
    }

    @Override
    public PageInfo<Topic> showAllTopic(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Topic> topicList = topicDao.selectAllTopics();
        PageInfo<Topic> pageInfo = new PageInfo<>(topicList);
        return pageInfo;
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
}
