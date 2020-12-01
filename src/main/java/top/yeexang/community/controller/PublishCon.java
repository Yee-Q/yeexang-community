package top.yeexang.community.controller;

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
import top.yeexang.community.service.TopicSev;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Controller
public class PublishCon {

    @Autowired
    private TopicSev topicSev;

    @UserLoginToken
    @ResponseBody
    @PostMapping("/publish/topic")
    @ApiOperation(value = "发表帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicDTO", value = "待发表的帖子信息", dataType = "TopicDTO", paramType = "body"),
    })
    public ResultDTO<?> publishTopic(@RequestBody TopicDTO topicDTO, HttpServletRequest request) {

        UserDTO userDTO = (UserDTO) request.getAttribute("loginUser");
        ResultDTO<?> resultDTO = null;
        try {
            resultDTO = topicSev.publishTopic(topicDTO, userDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultDTO;
    }

}
