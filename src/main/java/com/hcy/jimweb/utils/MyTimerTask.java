package com.hcy.jimweb.utils;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcy.jimweb.pojo.AccessToken;
import com.hcy.jimweb.pojo.consts.Constant;

/**
 * 定时任务获取accessToken
 * 
 * @author hcy
 *
 */
public class MyTimerTask extends TimerTask {
    
    private static final Logger log = LoggerFactory.getLogger(MyTimerTask.class);
    
    private static AccessToken accessToken = null;

    @Override
    public void run() {
        try {
            // 获取accessToken，将其存入内存，以供后期调微信接口使用
            accessToken = WeixinUtil.getAccessToken(Constant.appID, Constant.appsecret);
            log.info("######获取accessToken成功，ExpiresIn：" + accessToken.getExpiresIn() + ",Token:" + accessToken.getToken());
        } catch (Exception e) {
            log.error("######获取accessToken失败,原因：" + e.getMessage());
        }
    }

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        MyTimerTask.accessToken = accessToken;
    }

}
