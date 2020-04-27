package com.video.yali.utils;

import java.util.ArrayList;

public class ConstantUtils {

    public static final String tag_userInfo = "freshUserInfo";
    public static final String TAG_NO_PARAM = "TagNoParam";
    public static final String KEY_LANGUAGE = "applanguage";
    public static final int EVENT_FRESHUSERINFO = 1;
    public static final int EVENT_HISTORYEDIT_CHOSE = 2;
    public static final int EVENT_HISTORYEDIT_NO = 3;


    public static final int pageSize = 5;
    public static final int video_type_id = 0;
    public static final int video_type_down = 1;

    public static final int adtype_guide = 11;      //启动页
    public static final int adtype_banner = 12;      //轮播图的页面
    public static final int adtype_colum = 13;      //专题栏页面
    public static final int adtype_video = 14;      //视频详情页


    public static final ArrayList<String> getHeadImages() {

        ArrayList<String> headList = new ArrayList<String>();
        headList.add("http://static.1995519.com/headimg/mine_personal_head_1.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_2.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_3.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_4.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_5.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_6.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_7.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_8.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_9.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_10.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_11.png");
        headList.add("http://static.1995519.com/headimg/mine_personal_head_12.png");

        return headList;

    }
}
