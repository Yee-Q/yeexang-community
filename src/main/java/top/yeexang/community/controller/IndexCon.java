package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import top.yeexang.community.annotation.UserLoginToken;
import top.yeexang.community.cache.CategoryCache;
import top.yeexang.community.dto.CategoryDTO;

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

    @GetMapping("/")
    @ApiOperation(value = "跳转到首页")
    public String index() {
        return "index";
    }

    @UserLoginToken
    @GetMapping("/publish")
    @ApiOperation(value = "跳转到发表帖子页面")
    public ModelAndView publish() {

        // 获取专栏分类缓存
        List<CategoryDTO> categoryDTOS = categoryCache.getAllCategory();
        // 获取标签分类缓存
        ModelAndView mv = new ModelAndView("topic/publish");
        mv.addObject("categorieyDTOS", categoryDTOS);
        return mv;
    }
}
