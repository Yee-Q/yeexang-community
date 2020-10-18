package top.yeexang.community.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yeexang.community.dto.ResultDTO;

/**
 * @author yeeq
 * @date 2020/10/11
 */
public class TopicCon {

    @ResponseBody
    @PostMapping("/topic/{tid}")
    public ResultDTO<?> topic(@PathVariable(name = "tid") Long tid) {
        return null;
    }
}
