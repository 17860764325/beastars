package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Role;
import com.lhrlyn.cn.lhrlynadmin.user.enity.VO.UserRolesVO;
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
}
