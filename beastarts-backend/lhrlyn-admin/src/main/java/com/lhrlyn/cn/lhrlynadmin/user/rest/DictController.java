package com.lhrlyn.cn.lhrlynadmin.user.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.dto.ScheduleHeaderDto;
import com.lhrlyn.cn.lhrlynadmin.user.enity.Dict;
import com.lhrlyn.cn.lhrlynadmin.user.service.DictService;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/dict")
@RestController
public class DictController extends Controller {

    @Autowired
    private DictService dictService;

    @GetMapping(value = "/type/{dictType}")
    public ObjectRestResponse dictType(@PathVariable("dictType") String dictType) {
        List<DictDto> dictDtos = dictService.selectDictByType(dictType);
        return new ObjectRestResponse().data(dictDtos);
    }

    @GetMapping(value = "/dictsMap")
    public ObjectRestResponse<Map<String, List<DictDto>>> selectAllDictsMap() {
        Map<String, List<DictDto>> stringListMap = dictService.selectAllDicts();
        return ObjectRestResponse.success(stringListMap);
    }

    @GetMapping("/page")
    public TableResultResponse<List<DictDto>> page(@RequestParam Map<String, Object> map) {
        PageQuery query = new PageQuery(map, map);
        Page<DictDto> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<DictDto> list = dictService.page(query);
        TableResultResponse tableResultResponse = new TableResultResponse<>(result.getTotal(), list);
        return tableResultResponse;
    }

    @PostMapping("/add")
    public ObjectRestResponse add(@RequestBody DictDto dictDto) {
        boolean add = dictService.add(dictDto);
        if (add) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed("添加失败,数据库中已经有该字典值");
        }

    }


    @GetMapping("/delete/{ids}")
    public ObjectRestResponse delete(@PathVariable("ids") String ids) {
        return dictService.delete(ids);
    }

    @GetMapping("/edit/{id}")
    public ObjectRestResponse<DictDto> edit(@PathVariable("id") String id) {
        DictDto dictDto = dictService.edit(id);
        return ObjectRestResponse.success(dictDto);
    }

    @PostMapping("/edit")
    public ObjectRestResponse edit(@RequestBody DictDto dictDto) {
        boolean add = dictService.editDictDate(dictDto);
        if (add) {
            return ObjectRestResponse.success();
        } else {
            return ObjectRestResponse.failed("修改失败！");
        }

    }


}
