package com.example.mydemo1.Dao;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公众号事件消息体基础类
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class OfficialAccountBaseMessageBean {

    // 开发者微信号
    private String ToUserName;

    // 发送方帐号（一个OpenID）
    private String FromUserName;

    // 消息创建时间 （整型）
    private long CreateTime;

    // 消息类型（text/image/location/link）
    private String MsgType;

    // 消息id，64位整型
    private long MsgId;
}
