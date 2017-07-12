package com.hcy.jimweb.controller;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcy.jimweb.pojo.AccessToken;
import com.hcy.jimweb.pojo.common.UserInfo;
import com.hcy.jimweb.pojo.consts.Constant;
import com.hcy.jimweb.utils.WeixinUtil;

@Controller
@RequestMapping("auth")
public class UserInfoController {

    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);


    @RequestMapping(value = "userinfo", method = RequestMethod.GET)
    public String getUserInfo(String code) {
        log.info("############获取用户信息方法执行！code="+code);
        return "index";
    }
    
    @RequestMapping(value = "getUserinfo", method = RequestMethod.GET)
    public String getIndedUserInfo(String code,Model model) {
        log.info("############获取用户信息方法getIndedUserInfo执行！code="+code);
        //得到code
        String appID = Constant.appID;
        String appsecret = Constant.appsecret;
        if(StringUtils.isNotBlank(code)) {
            //换取access_token 其中包含了openid
            String url1 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", appID).replace("SECRET", appsecret).replace("CODE", code);
            JSONObject jsonStr = WeixinUtil.httpRequest(url1, "GET", null); 
            String openid = jsonStr.get("openid").toString();
            //TODO  待优化
            AccessToken aToken = WeixinUtil.getAccessToken(appID,appsecret);
            //有了用户的opendi就可以的到用户的信息,url2为获取用户信息接口
            String url2 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+aToken.getToken()+"&openid="+openid+"&lang=zh_CN";
            JSONObject userObj = WeixinUtil.httpRequest(url2, "GET", null);
//            UserInfo userInfo = (UserInfo) JSONObject.toBean(userObj, UserInfo.class);
            //得到用户信息之后返回到页面
            log.info("#############weixinOAuth方法完成，openid="+openid+";nickname="+userObj.get("nickname"));
//            model.addAttribute("nickname", userObj.get("nickname"));
        }
        return "index";
    }
    

}
