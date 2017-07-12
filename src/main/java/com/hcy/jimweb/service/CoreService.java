package com.hcy.jimweb.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcy.jimweb.pojo.res.Music;
import com.hcy.jimweb.pojo.res.MusicMessage;
import com.hcy.jimweb.pojo.res.TextMessage;
import com.hcy.jimweb.utils.MessageUtil;
  
/** 
 * 核心服务类 
 */  
public class CoreService {
    
    private static final Logger log = LoggerFactory.getLogger(CoreService.class);
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {
        log.info("#######用户发来一条请求"+request.toString());
        String respMessage = null;  
        // 返回给微信服务器的xml消息  
        String respXml = null; 
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                respContent = "您发送的是文本消息！"; 
                String content = requestMap.get("Content").trim(); 
             // 如果以“歌曲”2个字开头  
                if (content.startsWith("歌曲")) {  
                    // 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
                    String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");  
                    // 如果歌曲名称为空  
                    if ("".equals(keyWord)) {  
                        respContent = getUsage();  
                    } else {  
                        String[] kwArr = keyWord.split("@");  
                        // 歌曲名称  
                        String musicTitle = kwArr[0];  
                        // 演唱者默认为空  
                        String musicAuthor = "";  
                        if (2 == kwArr.length)  
                            musicAuthor = kwArr[1];  
  
                        // 搜索音乐  
                        Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);  
                        // 未搜索到音乐  
                        if (null == music) {  
                            respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";  
                        } else {  
                            // 音乐消息  
                            MusicMessage musicMessage = new MusicMessage();  
                            musicMessage.setToUserName(fromUserName);  
                            musicMessage.setFromUserName(toUserName);  
                            musicMessage.setCreateTime(new Date().getTime());  
                            musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);  
                            musicMessage.setMusic(music);  
                            respXml = MessageUtil.musicMessageToXml(musicMessage);  
                        }  
                    }  
                }  
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                 // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
                    log.info("#######用户点击了"+eventKey+"菜单");
  
                    if (eventKey.equals("11")) {  
                        respContent = "手机充值菜单项被点击！";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "手机账单菜单项被点击！";  
                    } else if (eventKey.equals("13")) {  
                        respContent = "话费查询菜单项被点击！";  
                    } else if (eventKey.equals("14")) {  
                        respContent = "流量查询菜单项被点击！";  
                    } else if (eventKey.equals("21")) {  
                        respContent = "水费菜单项被点击！";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "电费菜单项被点击！";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "燃气费菜单项被点击！";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "固话宽带菜单项被点击！";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "有线电视菜单项被点击！";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "商圈资讯菜单项被点击！";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "汇玩家菜单项被点击！";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "吃喝玩乐菜单项被点击！";  
                    }  
                }  
            }  
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }
    
    /** 
     * 歌曲点播使用指南 
     *  
     * @return 
     */  
    public static String getUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("歌曲点播操作指南").append("\n\n");  
        buffer.append("回复：歌曲+歌名").append("\n");  
        buffer.append("例如：歌曲存在").append("\n");  
        buffer.append("或者：歌曲存在@汪峰").append("\n\n");  
        buffer.append("回复“?”显示主菜单");  
        return buffer.toString();  
    }
    
    /** 
     * emoji表情转换(hex -> utf-16) 
     *  
     * @param hexEmoji 
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }
    
    
}  
