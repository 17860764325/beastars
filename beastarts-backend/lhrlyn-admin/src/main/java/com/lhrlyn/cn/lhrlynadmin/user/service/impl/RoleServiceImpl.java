package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Role;
import com.lhrlyn.cn.lhrlynadmin.user.entity.UserRole;
import com.lhrlyn.cn.lhrlynadmin.user.entity.VO.UserRolesVO;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.RoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserRoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.RoleService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihaoran
 * @date 2023/3/29 16:35
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * @description: 页面分页查询
     * @param: query
     * @return: java.util.List<com.lhrlyn.cn.lhrlynadmin.user.enity.Role>
     * @author lhr
     * @date: 2023/3/29 16:53
     */
     @Override
    public List<Role> page(PageQuery query) {
        List<Role> page = roleMapper.page(query);
            List<Role> DictDtos = BeanCopyUtils.listCopy(page, Role.class);
        return DictDtos;
    }

    /**
     * @description: 获取所有权限角色
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/29 16:54
     */
    @Override
    public ObjectRestResponse getAll() {
        return ObjectRestResponse.success(roleMapper.selectAll());
    }

    /**
     * @description: 根据用户ID获取用户的角色和权限
     * @param: id
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/29 16:54
     */
    @Override
    public ObjectRestResponse getCharacterByUserId(String id) {
        return ObjectRestResponse.success(roleMapper.getCharacterByUserId(id));
    }

    @Override
    public ObjectRestResponse saveCharacter(UserRolesVO userRolesVO) {
        List<UserRole> resultList = new ArrayList<>();
        for (String role : userRolesVO.getRoles()) {
            UserRole userRole = new UserRole();
            userRole.setUserId(Long.valueOf(userRolesVO.getUserId()));
            userRole.setRoleId(Long.valueOf(role));
            userRole.setIsVoid("1");
            resultList.add(userRole);
        }
        // 进行全删全建，将之前这个用户的全部删除，然后重新插入
        userRoleMapper.deleteByUserId(userRolesVO.getUserId());
        
        // 全建
        int i = userRoleMapper.insertList(resultList);
        if (i <= 0){
            return ObjectRestResponse.failed("保存失败");
        }
        return ObjectRestResponse.success();
    }

   @Override
    public ObjectRestResponse delete(String ids) {
            int i = roleMapper.deleteByIds(ids);
        if(i >= 1){
            return ObjectRestResponse.success();
        }else {
            return ObjectRestResponse.failed();
        }

    }
    

    @Override
    public Role edit(String id) {
        Role role = new Role();
        role.setId(Long.valueOf(id));
        Role role1 = roleMapper.selectOne(role);
        return role1;
    }

    @Override
    public boolean editDictDate(Role role) {
        int i = roleMapper.updateByPrimaryKey(role);
         if (i > 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean add(Role role) {

        int  i = roleMapper.insert(role);

        if (i > 0) {
            return true;
        }else {
            return false;
        }
    }


}
