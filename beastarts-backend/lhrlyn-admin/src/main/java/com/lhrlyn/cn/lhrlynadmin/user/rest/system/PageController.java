package com.lhrlyn.cn.lhrlynadmin.user.rest.system;

import com.lhrlyn.cn.lhrlynadmin.user.dto.RouterDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.UserDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Page;
import com.lhrlyn.cn.lhrlynadmin.user.enity.User;
import com.lhrlyn.cn.lhrlynadmin.user.rest.Controller;
import com.lhrlyn.cn.lhrlynadmin.user.service.impl.PageServiceImpl;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.getUserInfo.GetUserInfo;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 页面管理
 *
 * @author lihaoran
 * @date 2022/8/26 11:25
 */
@RestController
@RequestMapping("/page")
public class PageController extends Controller {

    @Autowired
    private PageServiceImpl pageService;



    /**
     * @description: 获取所有页面的树型列表
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<java.util.List<com.lhrlyn.cn.lhrlynadmin.user.dto.TreeDateDto>>
     * @author lhr
     * @date: 2022/8/26 16:13
     */
    @GetMapping("/treeList")
    public ObjectRestResponse<List<TreeDateDto>> getPageListTreeData(){
        return pageService.getPageListTreeData();
    }

    /**
     * @description: 根据code获取页面
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<com.lhrlyn.cn.lhrlynadmin.user.enity.Page>
     * @author lhr
     * @date: 2022/8/26 17:55
     */
    @GetMapping("/getPageInfoByCode/{code}")
    public ObjectRestResponse<Page> getPageInfoByCode(@PathVariable("code") String code){
        return pageService.getPageInfoByCode(code);
    }

    /**
     * @description:  返回最大ID
     * @param:
     * @return: com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse<java.lang.Integer>
     * @author lhr
     * @date: 2022/8/26 20:14
     */
    @GetMapping("/getMaxPageCode")
    public ObjectRestResponse<Integer> getMaxPageCode(){
        return pageService.getMaxPageCode();
    }


}
