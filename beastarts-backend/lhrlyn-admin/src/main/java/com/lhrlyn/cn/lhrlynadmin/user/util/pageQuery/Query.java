package com.lhrlyn.cn.lhrlynadmin.user.util.pageQuery;

import java.util.LinkedHashMap;
import java.util.Map;

public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static final String QUERY_PAGE = "page";
    private static final String QUERY_LIMIT = "limit";
    private static final String QUERY_ORDER = "orderBy";
    public static final String CUSTOM_OEDER_BY = "customOrderBy";
    private int page = 1;
    private int limit = 10;
    private String orderBy = "";
    private String customOrderBy = "";

    public Query(Map<String, Object> params) {
        this.putAll(params);
        if (params.get("page") != null) {
            this.page = Integer.parseInt(params.get("page").toString());
        }

        if (params.get("limit") != null) {
            this.limit = Integer.parseInt(params.get("limit").toString());
        }

        this.remove("page");
        this.remove("limit");
        if (params.get("orderBy") != null) {
            this.orderBy = params.get("orderBy").toString();
        }

        this.remove("orderBy");
        if (params.get("customOrderBy") != null) {
            this.customOrderBy = params.get("customOrderBy").toString();
        }

        this.remove("customOrderBy");
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

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getCustomOrderBy() {
        return this.customOrderBy;
    }

    public void setCustomOrderBy(String customOrderBy) {
        this.customOrderBy = customOrderBy;
    }
}
