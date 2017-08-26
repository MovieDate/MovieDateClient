package com.example.zhuocong.comxzc9.commom;

/**
 * Created by wunaifu on 2017/8/9.
 */
public class APPConfig {

//    private static String base_url="http://localhost:8080/";
    private static String base_url="http://192.168.0.106:8080/";

    public static String login = base_url + "/userController/loginByPhonePsw";
    public static String findUserByPhone = base_url + "/userController/findUserByPhone";
    public static String register=base_url+"/userController/addUserByPhonePsw";
    public static String modify=base_url+"/userController/updateUser";
    public static String updatePhone=base_url+"/userController/updatePhoneById";
    public static String updatePassword=base_url+"/userController/updatePassword";
    public static String allUser=base_url+"/userController/allUser";
    public static String allPost=base_url+"/postController/findAllPost";

    public static String USERDATA = "userData";//获取当前用户的key
    public static String ACCOUNT = "account";
    public static String NAME = "name";
    public static String PSW = "password";

    public static String IS_LOGIN = "login";
}
