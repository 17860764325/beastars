package com.lhrlyn.cn.lhrlynadmin.user.util.getUserInfo;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 获取用户心
 * @author lihaoran
 * @date 2022/8/24 15:38
 */
public class GetUserInfo {

    @Autowired
    private RedisTemplate redisTemplate;

    public UserDto getUserByToken(String token){
        // 首先从redis中查询出该用户登陆的token
        Object userDto = redisTemplate.opsForValue().get(token);
        UserDto userDto1 = BeanCopyUtils.beanCopy(userDto,UserDto.class);
        // 解析token获取其中用户userID
        return userDto1;
    }
}
