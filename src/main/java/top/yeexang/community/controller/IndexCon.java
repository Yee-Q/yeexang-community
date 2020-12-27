package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.yeexang.community.annotation.UserLoginToken;
import top.yeexang.community.cache.CategoryCache;
import top.yeexang.community.dto.CategoryDTO;
import top.yeexang.community.dto.NotificationDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.service.NotificationSev;
import top.yeexang.community.service.TopicSev;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/1
 */
@Slf4j
@Controller
@Api(tags = "跳转页面服务接口")
public class IndexCon {

    @Autowired
    private CategoryCache categoryCache;

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private NotificationSev notificationSev;

    @GetMapping("/")
    @ApiOperation(value = "跳转到首页")
    public String index(Model model) {
        // 加载专栏分类缓存
        loadCategoryCache(model);
        // 加载最新帖子列表
        loadNewTopicList(model);
        return "index";
    }

    @GetMapping("/publish")
    @ApiOperation(value = "跳转到发表帖子页面")
    public String publish(Model model, HttpServletRequest request) {
        // 加载专栏分类缓存
        loadCategoryCache(model);
        return "topic/publish";
    }

    @UserLoginToken
    @GetMapping("/user/message")
    @ApiOperation(value = "跳转到消息中心页面")
    public String message(Model model, HttpServletRequest request) {
        // 加载待接收通知
        loadNewNotificationList(model, request);
        return "user/message";
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

    @ApiOperation(value = "加载最新通知消息列表")
    private void loadNewNotificationList(Model model, HttpServletRequest request) {
        // 获取最新消息通知列表
        List<NotificationDTO> notificationDTOList = notificationSev.getNewNotificationList(((UserDTO) request.getAttribute("loginUser")).getId());
        model.addAttribute("notificationDTOList", notificationDTOList);
    }
}