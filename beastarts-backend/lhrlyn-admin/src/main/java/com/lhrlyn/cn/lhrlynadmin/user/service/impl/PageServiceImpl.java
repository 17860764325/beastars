package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.Meta;
import com.lhrlyn.cn.lhrlynadmin.user.dto.RouterDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Page;
import com.lhrlyn.cn.lhrlynadmin.user.enity.PageRole;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.enity.UserRole;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.PageMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.PageRoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.RoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.UserRoleMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.PageService;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面管理
 *
 * @author lihaoran
 * @date 2022/8/26 11:28
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PageRoleMapper pageRoleMapper;

    /**
     * @description: 获取路由
     * @param: user
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<com.lhrlyn.cn.lhrlynadmin.user.dto.RouterDto>
     * @author lhr
     * @date: 2022/8/26 14:07
     */
    @Override
    public ObjectRestResponse<List<RouterDto>> getPageListRouter(User user) {
        // 用户所能看到的所有页面
        List<Page> list = getPages(user);
        // 结构转换
        List<RouterDto> result = getRouterDtos(list);
        return ObjectRestResponse.success(result);
    }

    // 路由结构转换
    private List<RouterDto> getRouterDtos(List<Page> list) {
        // 一级目录
        List<Page> listFirst = new ArrayList<>();
        // 二级页面
        List<Page> listSecond = new ArrayList<>();

        // 将页面进行转换，转换成为路由的格式，返回给前端
        for (Page page : list) {
            if (!"0".equals(page.getPageParentCode())) {
                // 所有的二级菜单
                listSecond.add(page);
            } else {
                // 所有的一级菜单
                listFirst.add(page);
            }
        }

        List<RouterDto> result = new ArrayList<>();

        for (Page first : listFirst) {
            List<Page> childrens = new ArrayList<>();
            List<RouterDto> childrenRouter = new ArrayList<>();
            RouterDto routerDto = new RouterDto();
            for (Page second : listSecond) {
                // 如果二级菜单的父节点为以及菜单的code那么就将二级菜单加入到这个一级菜单的children里面
                if (second.getPageParentCode().equals(first.getPageCode())) {
                    childrens.add(second);
                }
            }
            // 将所有子页面转换成为路由形式
            for (Page children : childrens) {
                RouterDto routerDto1 = new RouterDto();
                routerDto1.setPath(children.getPath());
                routerDto1.setName(children.getName());
                routerDto1.setComponent("@/views" + children.getPagePath());
                Meta meta = new Meta();
                meta.setIcon(children.getIcon());
                meta.setTitle(children.getPageName());
                routerDto1.setMeta(meta);
                childrenRouter.add(routerDto1);
            }

            routerDto.setPath(first.getPath());
            routerDto.setComponent("layout");
            if (first.getPageName() != null) {
                Meta meta = new Meta();
                meta.setTitle(first.getPageName());
                if (first.getIcon() != null) {
                    meta.setIcon(first.getIcon());
                }
                routerDto.setMeta(meta);
            }
            routerDto.setChildren(childrenRouter);
            result.add(routerDto);
        }
        return result;
    }

    // 查询用户能看到的页面
    private List<Page> getPages(User user) {
        // 根据用户查询出他的角色，根据角色查询出相应的页面
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserid());
        List<Page> list = new ArrayList<>();
        List<UserRole> roles = userRoleMapper.select(userRole);
        for (UserRole role : roles) {
            PageRole pageRole = new PageRole();
            pageRole.setRoleCode(role.getRoleId().toString());
            List<PageRole> pages = pageRoleMapper.select(pageRole);
            for (PageRole page : pages) {
                Page selectPage = new Page();
                selectPage.setPageCode(page.getPageCode());
                List<Page> select = pageMapper.select(selectPage);
                for (Page page1 : select) {
                    list.add(page1);
                }
            }
        }
        return list;
    }

    /**
     * @description: 所有页面的树结构数据返回
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<java.util.List < com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto>>
     * @author lhr
     * @date: 2022/8/26 16:15
     */
    public ObjectRestResponse<List<TreeDateDto>> getPageListTreeData() {

        // select all pages
        List<Page> pages = pageMapper.selectAll();

        // first pages
        List<Page> firstPages = new ArrayList<>();
        // second pages
        List<Page> secondPages = new ArrayList<>();

        // divide pages
        for (Page page : pages) {
            if ("0".equals(page.getPageParentCode())) {
                firstPages.add(page);
            } else {
                secondPages.add(page);
            }
        }
        // result
        List<TreeDateDto> result = new ArrayList<>();
        // set value for first pasges
        for (Page firstPage : firstPages) {
            // new TreeData
            TreeDateDto treeDateDto = new TreeDateDto();
            // list<tree> for first pages children
            List<TreeDateDto> childrens = new ArrayList<>();
            // list<page> fir first pages children
            List<Page> childrensPage = new ArrayList<>();
            for (Page secondPage : secondPages) {
                // second father  == first code
                if (firstPage.getPageCode().equals(secondPage.getPageParentCode())) {
                    childrensPage.add(secondPage);
                }
            }
            // childrens Pages ----> children TreeData
            for (Page page : childrensPage) {
                TreeDateDto treeDateDto1 = new TreeDateDto();
                treeDateDto1.setId(page.getPageCode());
                treeDateDto1.setLabel(page.getPageName());
                treeDateDto1.setParentCode(page.getPageParentCode());
                childrens.add(treeDateDto1);
            }
            treeDateDto.setId(firstPage.getPageCode());
            treeDateDto.setLabel(firstPage.getPageName());
            treeDateDto.setParentCode(firstPage.getPageParentCode());
            treeDateDto.setChildren(childrens);
            result.add(treeDateDto);
        }
        return ObjectRestResponse.success(result);
    }

    @Override
    public ObjectRestResponse<Page> getPageInfoByCode(String code) {
        Page page = new Page();
        page.setPageCode(code);
        Page page1 = pageMapper.selectOne(page);
        return ObjectRestResponse.success(page1);
    }

    public ObjectRestResponse<Integer> getMaxPageCode() {
        Integer maxPageCode = pageMapper.getMaxPageCode();
        return ObjectRestResponse.success(maxPageCode);
    }

    @Override
    public ObjectRestResponse<List<String>> getUserFatherRouterPaths(User userDto1) {
        List<Page> pages = getPages(userDto1);
        List<String > paths = new ArrayList<>();

        // 将页面进行转换，转换成为路由的格式，返回给前端
        for (Page page : pages) {
            if ("0".equals(page.getPageParentCode())) {
                paths.add(page.getPath());
            }
        }
        return ObjectRestResponse.success(paths);
    }

    @Override
    public ObjectRestResponse<List<String>> getUserSonRouterPaths(User userDto1) {
        List<Page> pages = getPages(userDto1);
        List<String > paths = new ArrayList<>();

        // 将页面进行转换，转换成为路由的格式，返回给前端
        for (Page page : pages) {
            if (!"0".equals(page.getPageParentCode())) {
                paths.add(page.getPath());
            }
        }
        return ObjectRestResponse.success(paths);
    }
}
