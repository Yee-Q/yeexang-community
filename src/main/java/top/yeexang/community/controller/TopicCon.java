package top.yeexang.community.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.yeexang.community.dto.CommentDTO;
import top.yeexang.community.dto.TopicDTO;
import top.yeexang.community.service.CommentSev;
import top.yeexang.community.service.TagSev;
import top.yeexang.community.service.TopicSev;

import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/11
 */
@Controller
public class TopicCon {

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private CommentSev commentSev;

    @Autowired
    private TagSev tagSev;

    @GetMapping("/topic/{id}")
    @ApiOperation(value = "访问帖子详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子id", dataType = "Long", paramType = "path"),
    })
    public String visitTopic(@PathVariable(name = "id") Long id, Model model) {
        // 返回帖子信息
        TopicDTO topicDTO = topicSev.visitTopic(id);
        // 返回帖子的评论信息
        List<CommentDTO> commentDTOList = commentSev.getCommentList(id, 1);
        // 返回帖子标签信息
        List<String> tags = tagSev.getTopicTags(topicDTO.getTag());
        model.addAttribute("topicDTO", topicDTO);
        model.addAttribute("commentDTOList", commentDTOList);
        model.addAttribute("tags", tags);
        return "topic/detail";
    }

}
