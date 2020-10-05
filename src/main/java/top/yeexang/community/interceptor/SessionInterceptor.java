package top.yeexang.community.interceptor;

import com.auth0.jwt.interfaces.Claim;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import top.yeexang.community.annotation.UserLoginToken;
import top.yeexang.community.cache.LoginUserCache;
import top.yeexang.community.dto.UserDTO;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.exception.CustomizeException;
import top.yeexang.community.utils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yeeq
 * @date 2020/10/4
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginUserCache loginUserCache;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        /* 告知服务器请求的原始资源的 URI，其用于所有类型的请求，包括：协议+域名+查询参数（注意，不包含锚点信息）
         * 因为原始的 URI 中的查询参数可能包含 ID 或密码等敏感信息，如果写入 referer，则可能导致信息泄露 */
        String referer = request.getHeader("referer");
        /* 客户端指定自己想访问的 WEB 服务器的域名/IP地址和端口号。在任何类型请求中，request 都会包含此 header 信息 */
        String host = request.getHeader("host");
        /* 处理静态资源 */
        if (handler instanceof ResourceHttpRequestHandler) {
            /* 静态资源防盗链 */
            if (referer != null && (!host.equals(referer.split("//")[1].split("/")[0]))) {
                response.setStatus(403);
                return false;
            }
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Cookie[] cookies = request.getCookies();
        boolean hasToken = false;

        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (token != null) {
                        hasToken = true;
                        UserDTO userDTO;
                        try {
                            Map<String, Claim> map = JwtUtil.verifyToken(token);
                            userDTO = new UserDTO();
                            userDTO.setId(map.get("id").asLong());
                            userDTO.setName(map.get("name").asString());
                            userDTO.setAvatarUrl(map.get("avatarUrl").asString());
                            userDTO.setLevel(map.get("level").asInt());
                            userDTO.setVip(map.get("vip").asBoolean());
                            userDTO.setExp(map.get("exp").asInt());
                            request.setAttribute("loginUser", userDTO);
                            loginUserCache.putLoginUser(userDTO.getId(), System.currentTimeMillis());
                        } catch (Exception e) {
                            throw new CustomizeException(ResponseCodeEnum.REQUEST_FAILED);
                        }
                    }
                    break;
                }
            }
        }

        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (!hasToken) {
                    throw new CustomizeException(ResponseCodeEnum.NO_LOGIN);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable Exception ex) {

    }
}