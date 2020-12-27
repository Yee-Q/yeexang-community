package top.yeexang.community.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeexang.community.service.TagSev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yeeq
 * @date 2020/11/30
 */
@Service
@Transactional
public class TagSevImpl implements TagSev {

    @Override
    public List<String> getTopicTags(String tags) {
        // 判断是否有标签
        if (tags == null || tags.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] split = tags.split(",");
        List<String> tagList = Arrays.asList(split);
        return tagList;
    }
}
