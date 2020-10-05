package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yeeq
 * @date 2020/10/1
 */
@Controller
@Api(tags = "首页服务接口")
public class IndexCon {

    @GetMapping("/")
    @ApiOperation(value = "跳转到首页")
    public String index() {
        return "index";
    }
}
