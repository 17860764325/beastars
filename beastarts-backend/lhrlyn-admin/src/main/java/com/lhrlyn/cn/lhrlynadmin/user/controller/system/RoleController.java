package com.lhrlyn.cn.lhrlynadmin.user.controller.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.Role;
import com.lhrlyn.cn.lhrlynadmin.user.entity.VO.UserRolesVO;
import com.lhrlyn.cn.lhrlynadmin.user.controller.Controller;
import com.lhrlyn.cn.lhrlynadmin.user.service.RoleService;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lihaoran
 * @date 2023/3/29 16:32
 */
@RestController
@RequestMapping("/role")
public class RoleController extends Controller {
    @Autowired
    private RoleService roleService;

    /**
     * @description: 页面分页查询
     * @param: map
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse<java.util.List<com.lhrlyn.cn.lhrlynadmin.user.enity.Role>>
     * @author lhr
     * @date: 2023/3/29 16:43
     */
    @GetMapping("/page")
    public TableResultResponse<List<Role>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map, map);
        Page<DictDto> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Role> list = roleService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(), list);
        return tableResultResponse;
    }

    /** 
     * @description: 获取所有的角色权限
     * @param:  
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse 
     * @author lhr
     * @date: 2023/3/29 16:51
     */ 
    @GetMapping("/getAll")
    public ObjectRestResponse getAllRoles(){
        return roleService.getAll();
    }


    /**
     * @description: 根据俄用户id获取用户的权限以及角色
     * @param: id
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/29 16:54
     */
    @GetMapping("/getCharacterByUserId/{userid}")
    public ObjectRestResponse getCharacterByUserId(@PathVariable("userid") String id){
        return roleService.getCharacterByUserId(id);
    }

    /**
     * @description: 修改保存用户的权限
     * @param: userRolesVO
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse
     * @author lhr
     * @date: 2023/3/29 17:19
     */
    @PostMapping("/saveCharacter")
    public ObjectRestResponse  saveCharacter(@RequestBody UserRolesVO userRolesVO){
        return roleService.saveCharacter(userRolesVO);
    }
    
    /** 
     * @description: 删除权限
     * @param: ids 
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse 
     * @author lhr
     * @date: 2023/3/31 10:45
     */ 
    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return roleService.delete(ids);
    }


    @GetMapping("/edit/{id}")
    public ObjectRestResponse edit(@PathVariable("id") String id) {
        Role role = roleService.edit(id);
        return ObjectRestResponse.success(role);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody Role role) {
        boolean add = roleService.editDictDate(role);
        if (add) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed("修改失败！");
        }
    }

    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody Role role) {
        boolean add = roleService.add(role);
        if (add) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed("添加失败,数据库中已经有该权限值");
        }

    }

}
