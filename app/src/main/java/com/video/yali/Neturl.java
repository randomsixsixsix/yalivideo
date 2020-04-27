package com.video.yali;

public class Neturl {

    public static String PWDlOGIN = "";
    public static String BASEURL = "http://usapi.xhappserve.com";
    //首页
    public static String HOMEDATA = BASEURL + "/api/channel/home/custom";
    public static String HOMEPAGEDATA = BASEURL + "/api/channel/page";
    public static String HOMEMOVIES = BASEURL + "/api/column/movies";//专栏视频列表
    //专栏
    public static String COLUMNYEAR = BASEURL + "/api/column/year";//年度推荐
    public static String COLUMNAD = BASEURL + "/api/ad/column";//专栏轮播图
    public static String COLUMNHOT = BASEURL + "/api/column/hot";//热门专栏
    public static String COLUMNSTAR = BASEURL + "/api/star/column";//专栏影星
    public static String COLUMNDETAILS = BASEURL + "/api/column";//专栏详情(抓兰列表上边的大图)
    public static String STARS = BASEURL + "/api/stars";//影星列表
    public static String STAR = BASEURL + "/api/star";//影星详情
    public static String STARMOVIES = BASEURL + "/api/star/movies";//影星视频

    //搜索
    public static String CHANNELCATEGORY = BASEURL + "/api/channel/category";
    public static String CHANNELAREA = BASEURL + "/api/movie/area";
    public static String CHANNELDURATION = BASEURL + "/api/channel/duration";
    public static String MOVEPUBLISHTIME = BASEURL + "/api/movie/publish-time";
    public static String MOVIESORT = BASEURL + "/api/movie/sort";
    public static String CHANNELMOVIES = BASEURL + "/api/channel/movies";//
    public static String SEARCHQUERY = BASEURL + "/api/search";
    //排行榜
    public static String TOPSEARCH = BASEURL + "/api/top/search";//热搜
    public static String TOPPLAY = BASEURL + "/api/top/play";//观影
    public static String TOPNEW = BASEURL + "/api/top/new";//新片
    public static String TOPMASTURBATION = BASEURL + "/api/top/masturbation";//鲁sir
    public static String TOPSTAR = BASEURL + "/api/top/star";//影星榜

    //登陆模块
    public static String LOGIN_USER = BASEURL + "/api/login";
    public static String LOGIN_REGISTER = BASEURL + "/api/register";
    public static String REGISTER_SMSCODE = BASEURL + "/api/sms-code/register";
    public static String PSWDFORGET_SMSCODE = BASEURL + "/api/sms-code/reset-password";
    public static String PSWDFORGET_CHECKCODE = BASEURL + "/api/sms-code/verify-reset-password";
    public static String PSWDFORGET_RESET = BASEURL + "/api/reset-pwd";


    //个人中心--设置
    public static String PSWDMODIFY_SMSCODE = BASEURL + "/api/sms-code/update-password";
    public static String PSWDMODIFY_NEWPSWD = BASEURL + "/api/update-pwd";
    public static String PERSONAL_NICK = BASEURL + "/api/user/name";
    public static String PERSONAL_SEX = BASEURL + "/api/user/sex";
    public static String PERSONAL_AGE = BASEURL + "/api/user/age";
    public static String PERSONAL_WORK = BASEURL + "/api/user/job";
    public static String PERSONAL_MATE = BASEURL + "/api/user/partner-status";
    public static String PERSONAL_HEADLOGO = BASEURL + "/api/user/updateAvatar";
    public static String VERSIONCHECK = BASEURL + "/api/version/check";
    //个人中心--主功能
    public static String MY_USERINFO = BASEURL + "/api/userInfo";
    public static String MY_LIKE = BASEURL + "/api/favorites";
    public static String MY_HISTORY = BASEURL + "/api/histories";
    public static String MY_HISTORY_DELETE = BASEURL + "/api/histories/delete";
    public static String MY_OPINION_PUT = BASEURL + "/api/feedback";
    public static String MY_OPINION_LIST = BASEURL + "/api/feedbacks";
    public static String MY_OPINION_TYPE = BASEURL + "/api/feedback/types";
    public static String MY_NOTIFY = BASEURL + "/api/announcements";
    public static String MY_NOTIFY_READ = BASEURL + "/api/announcements/read";
    public static String MY_OPINION_QUESTION = BASEURL + "/api/problems";
    public static String MY_PROMOTE_CODE = BASEURL + "/api/QR/Code";
    public static String MY_PROMOTE_CODE_BACK = BASEURL + "/api/QR/saveQRCode";
    public static String MY_PROMOTE_RECORD = BASEURL + "/api/user/promotionHistory";
    public static String MY_FILE_IMAGE = BASEURL + "/api/upload/image";
    public static String MY_AD = BASEURL + "/api/ad/profile";
    public static String TASK_ADREAD = BASEURL + "/api/task/dailyTaskAd";
    public static String MY_PROMOTE_TASK = BASEURL + "/api/task/list";

    public static String MAIN_NOTICE = BASEURL + "/api/indexAnnouncement";
    public static String SPLASH_AD = BASEURL + "/api/ad/boot";

    //视频详情
    public static String VIDEO_DETAILS = BASEURL + "/api/movie";
    public static String VIDEO_ABOUT = BASEURL + "/api/movie/like";
    public static String VIDEO_COMMENT_LIST = BASEURL + "/api/comments/list";
    public static String VIDEO_COMMENT_PUT = BASEURL + "/api/comments/add";
    public static String VIDEO_ZAN = BASEURL + "/api/movie/praise";
    public static String VIDEO_ADD_HISTORY = BASEURL + "/api/movie/play";
    public static String VIDEO_COMMENT_ZAN = BASEURL + "/api/comment/praise";
    public static String VIDEO_COMMENT_DETAILS = BASEURL + "/api/comments/info";
    public static String VIDEO_DOWN_NOTIFY = BASEURL + "/api/movie/download";
    public static String VIDEO_AD = BASEURL + "/api/ad/movie-play";

}
