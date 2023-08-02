package com.lhrlyn.cn.lhrlynadmin.user.service.impl;

import com.lhrlyn.cn.lhrlynadmin.user.dto.DictDto;
import com.lhrlyn.cn.lhrlynadmin.user.entity.Dict;
import com.lhrlyn.cn.lhrlynadmin.user.mapper.DictMapper;
import com.lhrlyn.cn.lhrlynadmin.user.service.DictService;
import com.lhrlyn.cn.lhrlynadmin.user.util.beanCopy.BeanCopyUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.listBeanUtil.ListBeanUtil;
import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import com.lhrlyn.cn.lhrlynadmin.user.util.redis.RedisUtils;
import com.lhrlyn.cn.lhrlynadmin.user.util.response.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<DictDto> selectDictByType(String type) {
        List<DictDto> list = (List<DictDto>) redisUtils.get(type);
        if (list != null) {
            return list;
        }
        List<Dict> dicts = dictMapper.selectDictByType(type);
        List<DictDto> dictDtos = BeanCopyUtils.listCopy(dicts, DictDto.class);
        if (dictDtos != null) {
            redisUtils.set(type, dictDtos);
            return dictDtos;
        }
        return null;

    }

    @Override
    public Map<String, List<DictDto>> selectAllDicts() {
        // 如果redis中没有，那么就从数据库中获取 需要从数据库中进行查询的方式
        List<Dict> dicts = dictMapper.selectAll();
        if (dicts != null) {
            List<DictDto> dictDtos = BeanCopyUtils.listCopy(dicts, DictDto.class);
            List<List<DictDto>> dictDtoType = ListBeanUtil.xhfz(dictDtos, "dictType");
            Map<String, List<DictDto>> map = new HashMap<>();
            for (List<DictDto> dictDtoList : dictDtoType) {
                map.put(dictDtoList.get(0).getDictType(), dictDtoList);
            }
            return map;
        }

        return null;

    }

    @Override
    public List<DictDto> page(PageQuery query) {
        List<Dict> page = dictMapper.page(query);
        List<DictDto> DictDtos = BeanCopyUtils.listCopy(page, DictDto.class);
        return DictDtos;
    }

    @Override
    public boolean add(DictDto dictDto) {
        Dict dict = BeanCopyUtils.beanCopy(dictDto, Dict.class);
        Dict check = dictMapper.check(dict.getDictType(), dict.getDictValue());
        if (check != null) {
            return false;
        }
        dict.setCreateBy("admin");
        dict.setCreateTime(new Date());
        dict.setStatus("0");
        dictMapper.insert(dict);
        return true;
    }

    @Override
    public DictDto edit(String id) {
        Dict dict = new Dict();
        dict.setDictCode(Long.valueOf(id));
        Dict dictResult = dictMapper.selectOne(dict);
        DictDto dictDto = BeanCopyUtils.beanCopy(dictResult, DictDto.class);
        return dictDto;
    }

    @Override
    public boolean editDictDate(DictDto dictDto) {
        Dict dict = BeanCopyUtils.beanCopy(dictDto, Dict.class);
        int i = dictMapper.updateByPrimaryKey(dict);
        if (i > 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ObjectRestResponse delete(String ids) {
            int i = dictMapper.deleteByIds(ids);
        if(i >= 1){
            return ObjectRestResponse.success();
        }else {
            return ObjectRestResponse.failed();
        }

    }
}
