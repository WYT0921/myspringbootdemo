package com.example.mydemo1.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatProperties {

    /**获取access_token的url*/
    public static String ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**公众号appid*/
    public static String OFFICIAL_ACCOUNT_APPID = "wx25a537a1b95649e5";

    /**公众号秘钥*/
    public static String OFFICIAL_ACCOUNT_SECRET = "64b606770a61a846cb34a8ee916fe063";

    /** 消息类型--事件 */
    public static final String MSGTYPE_EVENT = "event";

    /** 消息事件类型--订阅事件 */
    public static final String MESSAGE_SUBSCIBE = "subscribe";

    /** 消息事件类型--取消订阅事件 */
    public static final String MESSAGE_UNSUBSCIBE = "unsubscribe";

    /** 消息类型--文本消息 */
    public static final String MESSAGE_TEXT = "text";
}
