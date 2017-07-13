package com.hcy.jimweb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcy.jimweb.utils.WeixinUtil;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    
    private static final Logger log = LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WeixinUtil.startTimeJob();
        log.info("##########web容器启动！开始获取access_token");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("##########web容器销毁！");
    }

}
