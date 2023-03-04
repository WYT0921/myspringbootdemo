package com.example.mydemo1.Dao;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文本消息类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JacksonXmlRootElement(localName = "xml")
public class OfficialAccountTextMessage extends OfficialAccountBaseMessageBean {
    private String Content;
}
