package com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery;

import java.util.Map;

public class PageQuery<T> extends Query {
    protected T data;

    public PageQuery(Map<String, Object> params, T data) {
        super(params);
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
