package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.entity.Role;
import com.lhrlyn.cn.lhrlynadmin.user.entity.VO.UserRolesVO;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;

/**
 * @author lihaoran
 * @date 2023/3/29 16:35
 */
public interface RoleService {

    List<Role> page(PageQuery query);

    ObjectRestResponse getAll();

    ObjectRestResponse getCharacterByUserId(String id);

    ObjectRestResponse saveCharacter(UserRolesVO userRolesVO);

    ObjectRestResponse delete(String ids);

    Role edit(String id);

    boolean editDictDate(Role role);

    boolean add(Role role);
}
