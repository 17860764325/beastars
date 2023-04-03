package com.lhrlyn.cn.lhrlynadmin.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 返回树形结构专用DTO
 *
 * @author lihaoran
 * @date 2022/8/26 13:48
 */
@Data
public class TreeDateDto {
    private String id;
    private String label;
    private String ParentCode;
    private String path;
    private String name;
    private String icon;
    private List<TreeDateDto> children;
}
