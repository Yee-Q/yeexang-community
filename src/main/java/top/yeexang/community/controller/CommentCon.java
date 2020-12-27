package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.yeexang.community.annotation.UserLoginToken;
import top.yeexang.community.dto.CommentCreateDTO;
import top.yeexang.community.dto.CommentDTO;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.service.CommentSev;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yeeq
 * @date 2020/11/30
 */
@Api(tags = "评论服务接口")
@Controller
public class CommentCon {

    @Autowired
    private CommentSev commentSev;

    @ResponseBody
    @UserLoginToken
    @PostMapping(value = "/comment")
    @ApiOperation(value = "发表评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentCreateDTO", value = "新增的评论", dataType = "CommentCreateDTO"),
    })
    public ResultDTO<?> post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        // 判断用户是否登录
        UserDTO userDTO = (UserDTO) request.getAttribute("loginUser");
        if (userDTO == null) {
            return ResultDTO.errorOf(ResponseCodeEnum.NO_LOGIN);
        }
        // 评论内容是否为空
        if (commentCreateDTO == null || commentCreateDTO.getContent().trim().length() == 0) {
            return ResultDTO.errorOf(ResponseCodeEnum.COMMENT_CONTENT_NOT_EMPTY);
        }
        return commentSev.createComment(commentCreateDTO, userDTO);
    }

    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    @ApiOperation(value = "获取二级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "父级评论id", dataType = "Long")
    })
    public ResultDTO<?> getSecondReply(@PathVariable(name = "id") Long id) {

        List<CommentDTO> commentList = commentSev.getCommentList(id, 2);
        return ResultDTO.successOf(commentList);
    }
}
