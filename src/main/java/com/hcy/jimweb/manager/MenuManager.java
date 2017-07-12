package com.hcy.jimweb.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcy.jimweb.pojo.AccessToken;
import com.hcy.jimweb.pojo.Button;
import com.hcy.jimweb.pojo.CommonButton;
import com.hcy.jimweb.pojo.ComplexButton;
import com.hcy.jimweb.pojo.Menu;
import com.hcy.jimweb.pojo.consts.Constant;
import com.hcy.jimweb.utils.WeixinUtil;

public class MenuManager {
    private static final Logger log = LoggerFactory.getLogger(MenuManager.class);
    
    /**
     * 创建菜单入口，打包时候记得注掉
     */
//    public static void main(String[] args) {
//        String appID = Constant.appID;
//        String appsecret = Constant.appsecret;
//        AccessToken accessToken = WeixinUtil.getAccessToken(appID,appsecret);
//        if(accessToken != null ) {
//            int result = WeixinUtil.createMenu(getMenu(),accessToken.getToken());
//            if(result == 0) {
//                log.info("创建菜单成功！");
//            }else {
//                log.info("创建菜单失败，result="+result);
//            }
//        }
//    }
    
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("手机充值");  
        btn11.setType("view");  
        btn11.setKey("11");  
        btn11.setUrl("http://www.sevend.xin/redirect/11"); 
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("手机账单");  
        btn12.setType("view");  
        btn12.setKey("12");  
        btn12.setUrl("http://www.sevend.xin/redirect/12");   
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("话费查询");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("流量查询");  
        btn14.setType("click");  
        btn14.setKey("14");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("水费");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("电费");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("燃气费");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("固话宽带");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        CommonButton btn25 = new CommonButton();  
        btn25.setName("有线电视");  
        btn25.setType("click");  
        btn25.setKey("25");  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("商圈资讯");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("汇玩家");  
        btn32.setType("click");  
        btn32.setKey("32");  
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("吃喝玩乐");  
        btn33.setType("click");  
        btn33.setKey("33");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("手机助手");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("生活缴费");  
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多体验");  
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}
