package top.yeexang.community.cache;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yeexang.community.dao.CategoryDao;
import top.yeexang.community.dao.TopicDao;
import top.yeexang.community.dao.UserDao;
import top.yeexang.community.dto.CategoryDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.entity.Category;
import top.yeexang.community.entity.Topic;
import top.yeexang.community.entity.User;
import top.yeexang.community.service.UserSev;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yeeq
 * @date 2020/10/19
 */
@Component
public class HotTopicCache {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSev userSev;

    @Autowired
    private CategoryDao categoryDao;

    private final static List<TopicDTO> NEW_TOPIC_LIST = new LinkedList<>();

    private final static Map<TopicDTO, Object> NEW_TOPIC_MAP = new HashMap<>();

    private static final Object PRESENT = new Object();

    private Lock lock = new ReentrantLock();

    private int cacheSize = 20;

    /**
     * 初始化热点帖子缓存
     *
     * @return true 为成功初始化，false 为初始化失败
     */
    public boolean initHotTopicCache() {
        lock.lock();
        try {
            List<Topic> hotTopicList = topicDao.selectHotTopics(cacheSize);
            if (hotTopicList.isEmpty()) {
                return false;
            }
            NEW_TOPIC_MAP.clear();
            Stream<TopicDTO> topicDTOStream = hotTopicList.stream().map(topic -> {
                TopicDTO topicDTO = new TopicDTO();
                BeanUtils.copyProperties(topic, topicDTO);
                User creator = userDao.selectUserById(topic.getCreator());
                topicDTO.setCreator(userSev.getUserDTO(creator));
                Category category = categoryDao.selectCategoryById(topic.getCategoryId());
                topicDTO.setCategory(category);
                NEW_TOPIC_MAP.put(topicDTO, PRESENT);
                return topicDTO;
            });
            List<TopicDTO> topicDTOList = topicDTOStream.collect(Collectors.toList());
            NEW_TOPIC_LIST.clear();
            NEW_TOPIC_LIST.addAll(topicDTOList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }


}
