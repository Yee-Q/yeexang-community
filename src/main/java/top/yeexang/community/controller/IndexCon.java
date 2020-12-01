package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.yeexang.community.annotation.UserLoginToken;
import top.yeexang.community.cache.CategoryCache;
import top.yeexang.community.dto.CategoryDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.service.TopicSev;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/1
 */
@Controller
@Api(tags = "首页服务接口")
public class IndexCon {

    @Autowired
    private CategoryCache categoryCache;

    @Autowired
    private TopicSev topicSev;

    @GetMapping("/")
    @ApiOperation(value = "跳转到首页")
    public String index(Model model) {
        loadCategoryCache(model);
        loadNewTopicList(model);
        return "index";
    }

    @UserLoginToken
    @GetMapping("/publish")
    @ApiOperation(value = "跳转到发表帖子页面")
    public String publish(Model model) {
        loadCategoryCache(model);
        return "topic/publish";
    }

    @ApiOperation(value = "分类专栏缓存预热")
    private void loadCategoryCache(Model model) {
        // 获取专栏分类缓存
        List<CategoryDTO> categoryDTOS = categoryCache.getAllCategory();
        model.addAttribute("categorieyDTOS", categoryDTOS);
    }

    @ApiOperation(value = "加载最新帖子列表")
    private void loadNewTopicList(Model model) {
        // 获取最新帖子列表
        List<TopicDTO> topicDTOList = topicSev.getNewTopicList();
        model.addAttribute("topicDTOList", topicDTOList);
    }
}