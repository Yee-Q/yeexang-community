package top.yeexang.community.provider;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.yeexang.community.dto.ResultDTO;
import top.yeexang.community.dto.ValidateDTO;
import top.yeexang.community.utils.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Vpatcha 人机校验服务服务提供者
 *
 * @author yeeq
 * @date 2020/10/2
 */
@Slf4j
@Component
public class VaptchaProvider {

    @Value("${vaptcha.vid}")
    private String vid;

    @Value("${vaptcha.key}")
    private String key;

    /**
     * 获取 vaptcha 服务端二次验证的响应结果
     *
     * @param token vaptcha 令牌
     * @param ip    客户端 ip 地址
     * @param scene 场景值
     * @return 响应结果
     */
    public ResultDTO<?> getValidateResult(String token, int scene, String ip) {

        Map<String, String> params = new HashMap<>(16, 0.75f);
        params.put("id", vid);
        params.put("secretkey", key);
        params.put("token", token);
        params.put("scene", String.valueOf(scene));
        params.put("ip", ip);
        String response = OkHttpUtil.doGet("http://0.vaptcha.com/verify", params);
        if (response == null) {
            return ResultDTO.errorOf();
        }
        log.info("vaptcha response [{}]", response);
        JSONObject obj = JSONObject.parseObject(response);
        Integer success = obj.getInteger("success");
        Integer score = obj.getInteger("score");
        String msg = obj.getString("msg");
        ValidateDTO validateDTO = new ValidateDTO();
        validateDTO.setMsg(msg).setSocre(score).setSuccess(success);
        return ResultDTO.successOf(validateDTO);
    }
}