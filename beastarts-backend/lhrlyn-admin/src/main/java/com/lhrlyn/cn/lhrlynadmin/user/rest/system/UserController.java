package com.lhrlyn.cn.lhrlynadmin.user.rest.system;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.UserServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.getUserInfo.GetUserInfo;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lhr
 * @version 1.0
 * @description: 用户类
 * @date 2022/8/12 16:03
 */
@RestController
@RequestMapping("/user/sth")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @description: 返回用户的数据
     * @author lhr
     * @date 2022/8/12 16:28
     * @version 1.0
     */
    @GetMapping("/list")
    public ObjectRestResponse<List<UserDto>> list(){
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
    public ObjectRestResponse<UserDto> gteUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        Object userDto = redisTemplate.opsForValue().get(token);
        return ObjectRestResponse.success(userDto);
    }


}
