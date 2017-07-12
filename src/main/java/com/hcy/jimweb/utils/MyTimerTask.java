package com.hcy.jimweb.utils;

import java.util.TimerTask;

import com.hcy.jimweb.pojo.AccessToken;
import com.hcy.jimweb.pojo.consts.Constant;

/**
 * 定时任务工具类
 * @author hcy
 *
 */
public class MyTimerTask extends TimerTask {
    
    /**
     * 定时任务执行延迟(ms)
     */
    private static final Long delay = 0L;
    
    /**
     * 定时任务执行频率(ms)
     */
    private static final Long period = 7000*1000L;

    @Override
    public void run() {
        AccessToken accessToken = WeixinUtil.getAccessToken(Constant.appID,Constant.appsecret);
        
        
    }
    
    
}
