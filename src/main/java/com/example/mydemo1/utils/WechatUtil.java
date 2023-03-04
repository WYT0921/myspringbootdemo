package com.example.mydemo1.utils;

import com.example.mydemo1.config.WeChatProperties;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class WechatUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取微信公众号的access_token
     * 使用定时任务每1小时50分钟发送一次请求，然后在Redis中设置有效时间为1小时55分钟
     * @return
     */
    public static String getAccessToken(){

        String url = WeChatProperties.ACCESS_URL;
        // 这里的参数要和下面的Map Key值对应
        String path = "?grant_type={grant_type}&appid={appid}&secret={secret}";
        Map<String, String> params = new HashMap<>(3);
        params.put("grant_type", "client_credential");
        params.put("appid", WeChatProperties.OFFICIAL_ACCOUNT_APPID);
        params.put("secret", WeChatProperties.OFFICIAL_ACCOUNT_SECRET);
        ResponseEntity<String> forObject = restTemplate.getForEntity(url + path, String.class, params);
        JSONObject jsonObject = com.alibaba.fastjson2.JSONObject.parseObject(forObject.getBody());
        String accessToken = jsonObject.getString("access_token");

        return accessToken;
    }
}
