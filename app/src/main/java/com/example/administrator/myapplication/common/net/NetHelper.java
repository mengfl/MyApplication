package com.example.administrator.myapplication.common.net;

import com.example.administrator.myapplication.common.utils.ConstantUtil;

/**
 * 网络地址常量定义
 */
public class NetHelper {
    public final static int PAGENUM = 10;
    public static String KEY_CODE = "status";
    public static String KEY_MSG = "message";
    public static String KEY_RESULTS = "results";

    public static int COUNT = 3;  //并行请求数量

    //    public static String BASEURL="http://47.92.124.250:8088/jeesite/a";
//    public static String BASEURL="http://47.92.124.250:8080/jeesite/a";
//    public static String BASEURL="http://124.126.15.129:8088/jeesite/a";
    public static String BASEURL = "http://evct.catarc.info";
//    public static String BASEURL="http://10.10.10.116:8080/jeesite";

    public static String URL_LOGIN = BASEURL + "/sysMobile/login";//登录s
    public static String URL_EXIT = BASEURL + "/sysMobile/loginOut";//退出
    public static String URL_SEARCH_NOT_CHECK_CAR = BASEURL + "/formCarMobile/searchFormCar";//车辆查询--待检测
    public static String URL_SEARCH_CHECKING_CAR = BASEURL + "/formCarMobile/testingFormCar";//车辆查询--检测中
    public static String URL_UPLOAD_PIC = BASEURL + "/photopath/formPicturePath/PhotoUpload";//上传图片1张
    public static String URL_UPLOAD_PICS = BASEURL + "/photopath/formPicturePath/PhotoUploads";//上传图片5张
    public static String URL_STEP = BASEURL + "/formStep/stepSave";//步骤记录
    public static String URL_STEP_RESULT = BASEURL + "/formStep/stepResultState";//查询步骤是否完成

    public static String URL_FINISH_CHECK = BASEURL + "/formStep/checkStep";//终端直连  平台转发  平台登出是否完成
    public static String URL_RECORD_CHOOSE = BASEURL + "/formCarMobile/testStepCar";//测试记录-查车类型
    public static String URL_RECORD_TIMES = BASEURL + "/formStep/findTestRecord";//测试记录-查该车测试次数
    public static String URL_RECORD = BASEURL + "/formStep/findTestStep";
    public static String URL_BIND_CARID = BASEURL + "/formCarMobile/bindingIccid";   //绑定iccid
    public static String URL_CONNECT_SERVER = BASEURL + "/formStep/loginState";   //服务连接 防掉线


}
