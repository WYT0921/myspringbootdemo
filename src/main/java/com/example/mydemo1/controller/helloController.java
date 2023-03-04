package com.example.mydemo1.controller;


import com.example.mydemo1.config.WeChatProperties;
import com.example.mydemo1.service.officialAccountService;
import com.example.mydemo1.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description
 * 	公众号用户表（用于进行消息推送） controller
 *
 *
 * @author wiselink
 * @since 2022-10-08
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class helloController  {

    @Autowired
    officialAccountService service;


    // URL:   http://www.xxxx.com/wechat/
    // Token: 此处TOKEN即为微信接口配置信息的Token

    private String TOKEN = "wechat";

    /**
     * 排序方法
     * @param token     Token
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return    加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // 创建 16进制字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 验证微信后台配置的服务器地址有效性
     *
     * 接收并校验四个请求参数
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return echostr
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String checkName(@RequestParam(name = "signature") String signature,
                            @RequestParam(name = "timestamp") String timestamp,
                            @RequestParam(name = "nonce") String nonce,
                            @RequestParam(name = "echostr") String echostr) {

        log.info("微信-开始校验签名");
        log.info("收到来自微信的 echostr 字符串:{}", echostr);

//        加密/校验流程如下:
//        1. 将token、timestamp、nonce三个参数进行字典序排序
//       3 2. 将三个参数字符串拼接成一个字符串进行sha1加密
//        3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

        // 1.排序
        String sortString = sort(TOKEN, timestamp, nonce);
        // 2.sha1加密
        String myString = sha1(sortString);
        // 3.字符串校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            log.info("微信-签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            log.info("回复给微信的 echostr 字符串:{}", echostr);
            return echostr;
        } else {
            log.error("微信-签名校验失败");
            return "";
        }
    }


    @RequestMapping(value="/check", method=RequestMethod.POST)
    public void eventHandle(@RequestBody HttpServletRequest request) throws Exception {
        String message = "success";
        request.setCharacterEncoding("UTF-8");
         //把微信返回的xml信息转义成map
        Map<String,String> map=new HashMap();
        SAXReader reader=new SAXReader();
        try {
            Document document=reader.read(request.getInputStream());
            Element root=document.getRootElement();
            List<Element> elements=root.elements();
            //elements中的内容包括请求道的xml数据包内容
            for(Element e:elements) {
                map.put(e.getName(), e.getStringValue());
                String fromUserName = map.get("FromUserName");//消息来源用户标识

                String toUserName = map.get("ToUserName");//消息目的用户标识
                String msgType = map.get("MsgType");//消息类型
                String content = map.get("Content");//消息内容

                String eventType = map.get("Event");
                if(WeChatProperties.MSGTYPE_EVENT.equals(msgType)){//如果为事件类型
                    //处理订阅事件
                    if(WeChatProperties.MESSAGE_SUBSCIBE.equals(eventType)){
                        message = service.subscribeForText(toUserName, fromUserName);
                    }
                    //处理取消订阅事件
                    else if(WeChatProperties.MESSAGE_UNSUBSCIBE.equals(eventType)){
                        message = service.unsubscribe(fromUserName);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println(map);
        System.out.println(message);

    }
}
//        log.info("获取关注");
//        Map<String, Object> user = new HashMap<>();
//        req.setCharacterEncoding("UTF-8");
//        String message = "success";
//        //把微信返回的xml信息转义成map
//        String xml = XmlUtil.inputStream2String(req.getInputStream(), "UTF-8");
//        Map<String, String> map = XmlUtil.xmlToMap(req);
//        String fromUserName = map.get("FromUserName");//消息来源用户标识
//
//        String toUserName = map.get("ToUserName");//消息目的用户标识
//        String msgType = map.get("MsgType");//消息类型
//        String content = map.get("Content");//消息内容
//
//        String eventType = map.get("Event");
//        if(WeChatProperties.MSGTYPE_EVENT.equals(msgType)){//如果为事件类型
//            //处理订阅事件
//            if(WeChatProperties.MESSAGE_SUBSCIBE.equals(eventType)){
//                message = service.subscribeForText(toUserName, fromUserName);
//            }
//            //处理取消订阅事件
//            else if(WeChatProperties.MESSAGE_UNSUBSCIBE.equals(eventType)){
//                message = service.unsubscribe(fromUserName);
//            }
//        }
//
//        System.out.println(message);
//    }
//}