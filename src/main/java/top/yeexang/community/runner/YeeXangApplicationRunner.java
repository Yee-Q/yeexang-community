package top.yeexang.community.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.yeexang.community.cache.CategoryCache;
import top.yeexang.community.cache.HotTopicCache;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Slf4j
@Component
public class YeeXangApplicationRunner implements ApplicationRunner {

    @Autowired
    private CategoryCache categoryCache;

    @Autowired
    private HotTopicCache hotTopicCache;

    @Override
    public void run(ApplicationArguments args) {
        // 加载专栏分类缓存
        if (!categoryCache.initCategoryCache()) {
            log.warn("Category cache initialization failded!Please check your configuration!");
        } else {
            log.info("Category cache initalization succeeded!");
        }
        // 加载热门帖子缓存
        if (!hotTopicCache.initHotTopicCache()) {
            log.warn("HotTopic cache initialization failded!Please check your configuration!");
        } else {
            log.info("HotTopic cache initalization succeeded!");
        }
    }
}