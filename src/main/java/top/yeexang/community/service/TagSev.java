package top.yeexang.community.service;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/11/30
 */
public interface TagSev {

    /**
     * 获取帖子里的标签信息列表
     * @param tags 标签信息
     * @return 标签信息列表
     */
    List<String> getTopicTags(String tags);
}
