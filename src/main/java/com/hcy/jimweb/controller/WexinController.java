package com.hcy.jimweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcy.jimweb.service.CoreService;
import com.hcy.jimweb.utils.SignUtil;

@Controller
public class WexinController {

    private static final Logger log = LoggerFactory.getLogger(WexinController.class);

    /**
     * 确认请求来自微信服务器
     */
    @RequestMapping(value = "wechat", method = RequestMethod.GET)
    public String wechatGet(HttpServletRequest request, HttpServletResponse response) {
        log.info("####接受一条新的请求###########" + request.toString());
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
        return null;
    }

    @RequestMapping(value = "wechat", method = RequestMethod.POST)
    public String wechatPost(HttpServletRequest request, HttpServletResponse response) {
        log.info("####响应一条新的请求###########" + request.toString());
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respMessage = CoreService.processRequest(request);

        // 响应消息
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(respMessage);
        out.close();
        return null;
    }

    @RequestMapping(value = "/redirect/{selectKey}", method = RequestMethod.GET)
    public String weixinRedirect(@PathVariable String selectKey,HttpServletRequest request, HttpServletResponse response) {
        log.info("##########weixinRedirect方法执行！！！！");
        String redirectUrl = null;
        if("12".equals(selectKey)) {//查询账单，需要获取用户信息
            redirectUrl =  "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86bda038dfa3472b&redirect_uri=http%3A%2F%2Fwww.sevend.xin%2Fauth%2FgetUserinfo?response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=123#wechat_redirect"; 
        }else {
            redirectUrl =  "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86bda038dfa3472b&redirect_uri=http%3A%2F%2Fwww.sevend.xin%2Fauth%2Fuserinfo?response_type=code&scope=snsapi_base&state=1&connect_redirect=123#wechat_redirect"; 
        }
        return redirectUrl;
    }
    

}
