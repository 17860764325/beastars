package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.*;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.LoginIpLogMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.RoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserRoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.LoginService;
import com.lhrlyn.cn.lhrlynadmin.user.util.IdWorker;
import com.lhrlyn.cn.lhrlynadmin.user.util.ResultData;
import com.lhrlyn.cn.lhrlynadmin.user.util.ReturnCode;
import com.lhrlyn.cn.lhrlynadmin.user.util.addressUtil.AddressUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.getLoginIp.IPUtil;
import com.lhrlyn.cn.lhrlynadmin.user.util.tokenJWT.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lhrlyn.cn.lhrlynadmin.user.util.ReturnCode.USER_IS_HASED;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private LoginIpLogMapper loginIpLogMapper;

    /**
     * 校验用户账号密码正确性
     *
     * @param
     * @author lhr
     * @date 2021/12/30 11:09 PM
     * @return:
     */
    @Override
    public ResultData checkUser(UserDto user, HttpServletRequest request) {
        User user2 = BeanCopyUtils.beanCopy(user, User.class);
        // 进行查询
        User user1 = new User();
        user1.setUsername(user2.getUsername());
        User user3 = userMapper.selectOne(user1);
        if (user3 != null) {
            UserDto userDto = BeanCopyUtils.beanCopy(user3, UserDto.class);
            // 进行比较
            if (user2.getUsername().equals(user3.getUsername()) && user2.getPassword().equals(user3.getPassword())) {
                // 比较成功
                // 生成token
                String token = JwtUtil.sign(userDto.getUserid().intValue());
                redisTemplate.opsForValue().set(token, userDto, Duration.ofMinutes(30L));
                userDto.setToken(token);
                // 存储ip
                String ip = IPUtil.getIp(request);
                LoginIpLog loginIpLog = new LoginIpLog();
                IdWorker idWorker = new IdWorker(1,1);
                long l = idWorker.nextId();
                loginIpLog.setId(l);
                loginIpLog.setUserId(userDto.getUserid());
                loginIpLog.setUserIp(ip);
                loginIpLog.setUserName(userDto.getUsername());
                loginIpLog.setCreateBy(userDto.getUsername());
                loginIpLog.setCreateTime(new Date());
                String address = AddressUtils.getAlibaba(ip);
                loginIpLog.setAddress(address);
                loginIpLogMapper.insert(loginIpLog);
                return ResultData.success(userDto);

            }
            // 比较不成功
            return ResultData.fail(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getCode(), ReturnCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }
        // 没有这个用户名
        return ResultData.fail(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getCode(), ReturnCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
    }

    /**
     * 获取用户的信息根绝token
     *
     * @param token
     * @author lhr
     * @date 2022/1/18 2:19 下午
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.ResultData
     */
    @Override
    public ResultData getUserInfo(String token) {
        Object userDto = redisTemplate.opsForValue().get(token);//封装到redis里
        UserDto userDto1 = BeanCopyUtils.beanCopy(userDto, UserDto.class);
        if (userDto != null) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userDto1.getUserid());
            List<UserRole> select = userRoleMapper.select(userRole);
            List<String> list = new ArrayList<>();
            for (UserRole role : select) {
                Role role1 = new Role();
                role1.setId(role.getRoleId());
                Role role2 = roleMapper.selectOne(role1);
                list.add(role2.getRoleName());
            }
            Userinfo userinfo = new Userinfo();
            userinfo.setRoles(list);
            userinfo.setAvatar(userDto1.getUserHeadImg());
            userinfo.setName("Super Admin");
            userinfo.setIntroduction("I am a super administrator");
            return ResultData.success(userinfo);
        } else {
            return ResultData.fail(ReturnCode.RC401.getCode(), ReturnCode.RC401.getMessage());
        }
    }

    /**
     * @description: regist
     * @param: user
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.ResultData
     * @author lhr
     * @date: 2022/8/16 10:13
     */
    @Override
    public ResultData regist(UserDto user) {
        User userSelect = new User();
        userSelect.setUsername(user.getUsername());
        List<User> userList = userMapper.select(userSelect);
        if (userList.size() > 0){
            return ResultData.fail(ReturnCode.USER_IS_HASED.getCode(),ReturnCode.USER_IS_HASED.getMessage());
        }
        User user1 = BeanCopyUtils.beanCopy(user, User.class);
        user1.setIsVoid("1");
        user1.setUserHeadImg("1.png");
        user1.setBackagegroundImg("12.jpg");
        int insert = userMapper.insert(user1);
        if (insert > 0 ){
            return ResultData.success("");
        }
        return ResultData.fail(ReturnCode.RC999.getCode(),ReturnCode.RC999.getMessage());

    }
}
