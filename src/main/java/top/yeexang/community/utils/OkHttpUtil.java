package top.yeexang.community.utils;

import okhttp3.*;

import java.util.Map;
import java.util.Objects;

/**
 * 基于 okhttp 封装的工具类
 *
 * @author yeeq
 * @date 2020/10/2
 */
public class OkHttpUtil {

    /**
     * Get 请求
     * @param url 请求 url
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, String> params) {
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        // 拼接 url 与请求参数
        StringBuilder sb = new StringBuilder();
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        RequestBody body = RequestBody.create(mediaType, sb.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}