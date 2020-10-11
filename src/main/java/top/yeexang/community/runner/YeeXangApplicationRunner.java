package top.yeexang.community.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.yeexang.community.cache.CategoryCache;
import top.yeexang.community.dao.CategoryDao;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Slf4j
@Component
public class YeeXangApplicationRunner implements ApplicationRunner {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryCache categoryCache;

    @Override
    public void run(ApplicationArguments args) {
        if (!categoryCache.initCategoryCache()) {
            log.warn("Category cache!initialization failded!Please check your configuration!");
        }
        log.info("Category cache initalization succeeded!");
    }
}