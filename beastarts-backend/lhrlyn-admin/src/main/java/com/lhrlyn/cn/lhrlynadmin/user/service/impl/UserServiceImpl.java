package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.Role;
import com.lhrlyn.cn.lhrlynadmin.user.entity.User;
import com.lhrlyn.cn.lhrlynadmin.user.entity.UserRole;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.RoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserRoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.UserService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhr
 * @version 1.0
 * @description: TODO
 * @date 2022/8/12 16:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public ObjectRestResponse<List<UserDto>> list() {
        ObjectRestResponse<List<UserDto>> objectRestResponse = new ObjectRestResponse<>();
        List<UserDto> userDtos = BeanCopyUtils.listCopy(userMapper.selectAll(), UserDto.class);
        for (UserDto userDto : userDtos) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userDto.getUserid());
            List<String> roleList = new ArrayList<>();
            List<UserRole> select = userRoleMapper.select(userRole);
            for (UserRole role : select) {
                Role role1 =new Role();
                role1.setId(role.getRoleId());
                Role role2 = roleMapper.selectOne(role1);
                roleList.add(role2.getRoleName());
            }
            userDto.setRole(roleList);
        }
        return  objectRestResponse.data(userDtos);
    }

    @Override
    public ObjectRestResponse saveUserHeadImg(String userHeadImg, User user) {
        User selectUser = new User();
        selectUser.setUserid(user.getUserid());
        User user1 = userMapper.selectOne(selectUser);
        user1.setUserHeadImg("navbar_image/" + userHeadImg);
        userMapper.updateByPrimaryKey(user1);
        return ObjectRestResponse.success();
    }

    @Override
    public ObjectRestResponse saveUserBackageImg(String userBackGroundImg, User user) {
        User selectUser = new User();
        selectUser.setUserid(user.getUserid());
        User user1 = userMapper.selectOne(selectUser);
        user1.setBackagegroundImg("weekWallPaper/" + userBackGroundImg);
        userMapper.updateByPrimaryKey(user1);
        return ObjectRestResponse.success();
    }

    @Override
    public UserDto getUserInfo(UserDto userDto1) {
        User user = new User();
        user.setUserid(userDto1.getUserid());
        User user1 = userMapper.selectOne(user);
        return BeanCopyUtils.beanCopy(user1,UserDto.class);
    }

    /** 
     * @description: 修改密码逻辑 
     * @param: user 
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse 
     * @author lhr
     * @date: 2023/3/28 19:45
     */ 
    public ObjectRestResponse changePassword(User user) {
        User userSelect = userMapper.selectByPrimaryKey(user);
        userSelect.setPassword(user. getPassword());
        int i = userMapper.updateByPrimaryKeySelective(userSelect);
        if (i>0){
            return ObjectRestResponse. success("修改成功");
        }
        return ObjectRestResponse. failed("修改失败");
    }

    /**
     * @description: 用户自己修改自己的信息
     * @param: userDto
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/28 20:17
     */
    public ObjectRestResponse changUserInfo(UserDto userDto) {
        User user = BeanCopyUtils.beanCopy(userDto, User.class);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i > 0){
            return ObjectRestResponse. success("修改成功");
        }
        return ObjectRestResponse. failed("修改失败");
    }
}
