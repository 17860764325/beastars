package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 返回路由专用DTO
 *
 * @author lihaoran
 * @date 2022/8/26 11:41
 */
@Data
public class RouterDto {

    private String path;
    private String name;
    private String component;
    private Meta meta;
    private List<RouterDto> children;

}
