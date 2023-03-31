package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.RouterDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Page;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.enity.VO.PageRoleVO;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;

/**
 * 页面关联
 *
 * @author lihaoran
 * @date 2022/8/26 11:28
 */
public interface PageService {

    ObjectRestResponse<List<RouterDto>> getPageListRouter(User user);

    ObjectRestResponse<List<TreeDateDto>> getPageListTreeData();

    ObjectRestResponse<Page> getPageInfoByCode(String code);

    ObjectRestResponse<Integer> getMaxPageCode();

    ObjectRestResponse<List<String>> getUserFatherRouterPaths(User userDto1);

    ObjectRestResponse<List<String>> getUserSonRouterPaths(User userDto1);
    public ObjectRestResponse getPagesByRoleId(String roleId) ;

    public ObjectRestResponse getAllPages();

    public ObjectRestResponse saveRolePages(PageRoleVO pageRoleVO,User user);
}
