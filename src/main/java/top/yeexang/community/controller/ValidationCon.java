package top.yeexang.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.provider.VaptchaProvider;
import top.yeexang.community.utils.IpUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yeeq
 * @date 2020/10/2
 */
@Controller
@Api(tags = "校验服务接口")
public class ValidationCon {

    @Autowired
    private VaptchaProvider vaptchaProvider;

    @PostMapping("/validate")
    @ResponseBody
    @ApiOperation(value = "手势验证码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "校验令牌", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "scene", value = "场景值", dataType = "Integer", paramType = "path")
    })
    public ResultDTO<?> validate(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "scene") Integer scene, HttpServletRequest request) {

        return vaptchaProvider.getValidateResult(token, scene, IpUtil.getRealIp(request));
    }
}