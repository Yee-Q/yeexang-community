package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yeexang.community.annotation.UserLoginToken;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.service.TopicSev;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Controller
@Api(tags = "发布帖子服务接口")
public class PublishCon {

    @Autowired
    private TopicSev topicSev;

    @UserLoginToken
    @ResponseBody
    @PostMapping("/publish/topic")
    @ApiOperation(value = "发表帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicDTO", value = "待发表的帖子信息", dataType = "TopicDTO"),
    })
    public ResultDTO<?> publishTopic(@RequestBody TopicDTO topicDTO, HttpServletRequest request) {
        // 判断用户是否登录
        UserDTO userDTO = (UserDTO) request.getAttribute("loginUser");
        if (userDTO == null) {
            return ResultDTO.errorOf(ResponseCodeEnum.NO_LOGIN);
        }
        return topicSev.publishTopic(topicDTO, userDTO);
    }
}
