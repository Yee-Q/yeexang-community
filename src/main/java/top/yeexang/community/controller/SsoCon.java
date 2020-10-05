package top.yeexang.community.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.exception.CustomizeException;
import top.yeexang.community.service.UserSev;
import top.yeexang.community.utils.CookieUtil;
import top.yeexang.community.utils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author yeeq
 * @date 2020/10/1
 */
@Controller
@Api(tags = "Sso服务接口")
@RequestMapping("/sso")
public class SsoCon {

    /** 表示跳转到登录页面 */
    private static final String TO_LOGIN = "login";

    /** 表示跳转到注册页面 */
    private static final String TO_REGISTER = "register";

    @Value("${vaptcha.vid}")
    private String vaptchaVid;

    @Value("${site.main.domain}")
    private String domain;

    @Autowired
    private UserSev userSev;

    @GetMapping("/{action}")
    @ApiOperation(value = "跳转到注册或登录页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "action", value = "登录或注册指令", dataType = "String", paramType = "path"),
    })
    public String toLoginOrRegister(@PathVariable(name = "action") String action, HttpServletRequest request, Model model) {

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        // 判断用户是否已登录
        if (loginUser != null) {
            return "redirect:/";
        }
        model.addAttribute("vaptchaVid", vaptchaVid);
        if (TO_LOGIN.equals(action)) {
            model.addAttribute("scene", "login");
            model.addAttribute("sectionName", "登入");
        } else if (TO_REGISTER.equals(action)) {
            model.addAttribute("section", "register");
            model.addAttribute("sectionName", "注册");
        } else {
            return "redirect:/";
        }
        return "user/sso";
    }

    @PostMapping("/{action}")
    @ResponseBody
    @ApiOperation(value = "用户注册或登入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramsJson", value = "用户输入的信息", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "action", value = "登录或注册指令", dataType = "String", paramType = "path"),
    })
    public ResultDTO<?> loginOrRegister(@RequestBody String paramsJson, @PathVariable String action, HttpServletResponse response) {

        JSONObject jsonObject = JSONObject.parseObject(paramsJson);
        Map<String, Object> map = jsonObject.getInnerMap();
        ResultDTO<?> resultDTO;
        // 登入
        if (TO_LOGIN.equals(action)) {
            String email = map.get("email").toString();
            String pass = map.get("pass").toString();
            resultDTO = userSev.login(email, pass);
        } else if (TO_REGISTER.equals(action)) {
            // 注册
            String email = map.get("email").toString();
            String username = map.get("username").toString();
            String pass = map.get("pass").toString();
            resultDTO = userSev.register(email, username, pass);
        } else {
            resultDTO = ResultDTO.errorOf();
        }

        if (Objects.equals(resultDTO.getCode(), ResponseCodeEnum.REQUEST_SUCCESS.getCode())) {
            // 设置过期时间为三天
            Cookie cookie = CookieUtil.getCookie("token", "" + resultDTO.getData(), 86400 * 3, domain);
            response.addCookie(cookie);
        }
        return resultDTO;
    }
}
