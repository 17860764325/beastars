package com.lhrlyn.cn.lhrlynadmin.user.util.pageExample;

import com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery.PageQuery;
import tk.mybatis.mapper.entity.Example;

public class PageExample<T> extends Example {
    protected T data;
    protected int page = 1;
    protected int limit = 10;

    public PageExample(Class<?> entityClass, T data) {
        super(entityClass);
        this.data = data;
    }

    public PageExample(Class<?> entityClass, PageQuery pageQuery) {
        super(entityClass);
        this.limit = pageQuery.getLimit();
        this.page = pageQuery.getPage();
        this.data = (T) pageQuery.getData();
    }

    public PageExample(Class<?> entityClass, boolean exists, T data) {
        super(entityClass, exists);
        this.data = data;
    }

    public PageExample(Class<?> entityClass, boolean exists, boolean notNull, T data) {
        super(entityClass, exists, notNull);
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
