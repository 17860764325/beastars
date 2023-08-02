package com.lhrlyn.cn.lhrlynadmin.user.service;

import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;

import java.util.List;
import java.util.Map;

public interface DictService {

    List<DictDto> selectDictByType(String type);

    Map<String, List<DictDto>> selectAllDicts();

    List<DictDto> page(PageQuery query);

    boolean add(DictDto dictDto);

    DictDto edit(String id);

    ObjectRestResponse delete(String ids);

    boolean editDictDate(DictDto dictDto);


}
