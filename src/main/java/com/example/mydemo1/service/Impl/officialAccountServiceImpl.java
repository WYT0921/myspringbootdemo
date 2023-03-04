package com.example.mydemo1.service.Impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.mydemo1.Dao.OfficialAccountTextMessage;
import com.example.mydemo1.config.WeChatProperties;
import com.example.mydemo1.service.officialAccountService;
import com.example.mydemo1.utils.WechatUtil;
import com.example.mydemo1.utils.XmlUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class officialAccountServiceImpl implements officialAccountService {

    @Resource
    RestTemplate restTemplate;
    /**
     * 响应订阅事件--回复文本消息
     */
    @Override
    public String subscribeForText(String toUserName,String fromUserName){

        //获取公众号的接口凭证access_token
        String accessToken = WechatUtil.getAccessToken();
        System.out.println(accessToken);

        /**
         * 根据公众号openid获取公众号unionid
         */
        String unionIdRequestUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken;
        JSONObject jo = new JSONObject();
        JSONObject userInfo = restTemplate.postForObject(unionIdRequestUrl, jo, JSONObject.class);
        String unionId  = (String) userInfo.get("unionid");
        Map<String,Object> user = new HashMap<>();
        user.put("openid", fromUserName);
        user.put("unionid", unionId);


        OfficialAccountTextMessage text = new OfficialAccountTextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(WeChatProperties.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent("欢迎关注");
        System.out.println(user);
        return XmlUtil.textMsgToxml(text);
    }

    /**
     * 响应取消订阅事件
     */
    @Override
    public String unsubscribe(String fromUserName){
        return "用户" + fromUserName + "取消订阅";
    }
}
