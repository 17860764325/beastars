package com.lhrlyn.cn.lhrlynadmin.user.rest.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.UserServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.getUserInfo.GetUserInfo;
import com.lhrlyn.cn.lhrlynadmin.user.util.redis.RedisUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lhr
 * @version 1.0
 * @description: 用户类
 * @date 2022/8/12 16:03
 */
@RestController
@RequestMapping("/system/user/sth")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @description: 返回用户的数据
     * @author lhr
     * @date 2022/8/12 16:28
     * @version 1.0
     */
    @GetMapping("/list")
    public ObjectRestResponse<List<UserDto>> list() {
        return userServiceImpl.list();
    }

    /**
     * @description: 根据token获取用户信息
     * @param: token
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto>
     * @author lhr
     * @date: 2022/8/24 15:51
     */
    @GetMapping("/getUserInfoByToken")
    public ObjectRestResponse<UserDto> gteUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        UserDto userDtoCopy = BeanCopyUtils.beanCopy(userInfoByToken, UserDto.class);
        UserDto userDto2 = userServiceImpl.getUserInfo(userDtoCopy);
        return ObjectRestResponse.success(userDto2);
    }

    /**
     * @description: 存储user的头像
     * @param: userHeadImg
     * @return: void
     * @author lhr
     * @date: 2022/8/31 23:45
     */
    @GetMapping("/setUserHeadImg/navbar_image/{userHeadImg}")
    public ObjectRestResponse setUserHeadImg(@PathVariable("userHeadImg") String userHeadImg, HttpServletRequest request) {
     String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return userServiceImpl.saveUserHeadImg(userHeadImg, userInfoByToken);
    }

    /**
     * @description: 存储user的背景图片
     * @param: userHeadImg
     * @return: void
     * @author lhr
     * @date: 2022/8/31 23:45
     */
    @GetMapping("/setUserBackgroundImg/weekWallPaper/{userBackgroundImg}")
    public ObjectRestResponse setUserBackgroundImg(@PathVariable("userBackgroundImg") String userBackgroundImg, HttpServletRequest request) {
      String token = request.getHeader("token");
        User userInfoByToken = redisUtils.getUserInfoByToken(token);
        return userServiceImpl.saveUserBackageImg(userBackgroundImg, userInfoByToken);
    }

    /** 
     * @description: 修改密码
     * @param: user 
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse 
     * @author lhr
     * @date: 2023/3/28 19:45
     */ 
    @PostMapping("/changeUserPassword")
    public ObjectRestResponse changePassword(@RequestBody User user) {
       return  userServiceImpl.changePassword(user);
    }

    /**
     * @description: 用户自己修改自己的信息
     * @param: userDto
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/28 20:17
     */
    @PostMapping("/changeUserInfo")

    public ObjectRestResponse changUserInfo(@RequestBody UserDto userDto){
        return userServiceImpl.changUserInfo(userDto);
    }


}
