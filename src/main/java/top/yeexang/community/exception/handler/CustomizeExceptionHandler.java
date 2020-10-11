package top.yeexang.community.exception.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yeeq
 * @date 2020/10/6
 */
@Component
public class CustomizeExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView("error");
        // 判断不同异常
        if (e instanceof CustomizeException) {
            mv.addObject("message", e.getMessage());
        } else {
            mv.addObject("message", ResponseCodeEnum.REQUEST_FAILED.getMessage());
        }
        return mv;
    }
}
