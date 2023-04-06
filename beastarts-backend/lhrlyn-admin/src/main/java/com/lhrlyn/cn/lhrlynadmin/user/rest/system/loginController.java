package com.lhrlyn.cn.lhrlynadmin.user.rest.system;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lhrlyn.cn.lhrlynadmin.user.dto.RouterDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Userinfo;
import com.lhrlyn.cn.lhrlynadmin.user.rest.Controller;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.LoginServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.PageServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.ResultData;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.redis.RedisUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class loginController extends Controller {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private PageServiceImpl pageService;
    /**
     * 登录测试
     * http://127.0.0.1:8080/lhrlyn/admin/user/login
     *
     * @param
     * @author lhr
     * @date 2021/12/26 9:19 下午
     * @return: java.lang.String
     */
    @PostMapping("/user/login")
    public ResultData login(@RequestBody UserDto user, HttpServletRequest request) {
        return loginServiceImpl.checkUser(user, request);
    }

    /**
     * @param: user regist
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.ResultData
     * @author lhr
     * @date: 2022/8/16 09:56
     */
    @PostMapping("/user/regist")
    public ResultData regist(@RequestBody UserDto user) {
        return loginServiceImpl.regist(user);
    }


    /**
     * 获取用户信息，包括权限和一些基本信息
     *
     * @param
     * @author lhr
     * @date 2022/1/17 9:07 下午
     * @return: com.lhrlyn.cn.lhrlynadmin.user.enity.Userinfo
     */
    @GetMapping("/user/info")
    public ResultData<Userinfo> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        return loginServiceImpl.getUserInfo(token);
    }

    /**
     * @description: 返回该用户所有能访问的页面的path
     * @param: request
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<java.util.List < java.lang.String>>
     * @author lhr
     * @date: 2022/8/28 01:24
     */
    @GetMapping("/user/getUserFatherRouterPaths")
    public ObjectRestResponse<List<String>> getUserRouterFatherPaths(HttpServletRequest request) {
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return pageService.getUserFatherRouterPaths(userInfoByToken);
    }

    @GetMapping("/user/getUserSonRouterPaths")
    public ObjectRestResponse<List<String>> getUserRouterSonPaths(HttpServletRequest request) {
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return pageService.getUserSonRouterPaths(userInfoByToken);
    }

    /**
     * @description: 根据用户权限查询路由能现实的页面
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<com.lhrlyn.cn.lhrlynadmin.user.dto.RouterDto>
     * @author lhr
     * @date: 2022/8/26 13:48
     */
    @GetMapping("/user/routerList")
    public ObjectRestResponse<List<RouterDto>> getPageListRouter(HttpServletRequest request) {
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return pageService.getPageListRouter(userInfoByToken);
    }


    @GetMapping("/redis/test")
    public String redisTest(String key, String value) {
        redisUtils.set(key, value);
        return redisUtils.get(key).toString();
    }

    @PostMapping("/user/logout")
    public void Logout() {

    }


}
