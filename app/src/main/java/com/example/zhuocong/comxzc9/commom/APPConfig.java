package com.example.zhuocong.comxzc9.commom;

/**
 * Created by wunaifu on 2017/8/9.
 */
public class APPConfig {

//    private static String base_url="http://localhost:8080/";
    private static String base_url="http://192.168.1.100:8080/";

    public static String login = base_url + "/userController/loginByPhonePsw";
    public static String findUserByPhone = base_url + "/userController/findUserByPhone";
    public static String findUserById = base_url + "/userController/findUserById";
    public static String register=base_url+"/userController/addUserByPhonePsw";
    public static String modify=base_url+"/userController/updateUser";
    public static String updatePhone=base_url+"/userController/updatePhoneById";
    public static String updatePassword=base_url+"/userController/updatePassword";
    public static String allUser=base_url+"/userController/allUser";
    public static String allPost=base_url+"/postController/findAllPost";
    public static String post=base_url+"/postController/addPostByPostPersonId";
    public static String findPostByid=base_url+"/postController/findPostByid";
    public static String addCollectByCollecterId=base_url+"/collectController/addCollectByCollecterId";
    public static String findCollectByCollectPostId=base_url+"/collectController/findCollectByCollectPostId";//验证收藏
    public static String findCollectByCollecterId=base_url+"/collectController/findCollectByCollecterId";//我的收藏
    public static String findCollectByCollecterId2=base_url+"/collectController/findCollectByCollecterId2";
    public static String findFriendByMyId=base_url+"/friendtController/findFriendByMyId";//我的好友列表
    public static String addPersonByPostId=base_url+"/personController/addPersonByPostId";
    public static String findPersonByPostId=base_url+"/personController/findPersonByPostId";


    public static String USERDATA = "userData";//获取当前用户的key
    public static String ACCOUNT = "account";
    public static String NAME = "name";
    public static String PSW = "password";
    public static String PID="pid";//获取帖子id
    public static String POSTDATA = "postData";//获取当前用户的key
    public static String COLLECTDATA = "list1";
    public static String SIZE="size";
    public static String SI="si";

    public static String IS_LOGIN = "login";
}
