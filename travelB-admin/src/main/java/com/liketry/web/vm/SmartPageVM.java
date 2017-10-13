package com.liketry.web.vm;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * Created by liketry
 */
@ApiModel(value = "SmartPage分页对象")
public class SmartPageVM<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private SmartPagination pagination;

    private T search;

    private SmartSort sort;

    public SmartPagination getPagination() {
        return pagination;
    }

    private String startSearch;
    private String endSearch;

    public void setPagination(SmartPagination pagination) {
        this.pagination = pagination;
    }

    public T getSearch() {
        return search;
    }

    public void setSearch(T search) {
        this.search = search;
    }

    public SmartSort getSort() {
        return sort;
    }

    public void setSort(SmartSort sort) {
        this.sort = sort;
    }

    public String getStartSearch() {
        return startSearch;
    }

    public void setStartSearch(String startSearch) {
        this.startSearch = startSearch;
    }

    public String getEndSearch() {
        return endSearch;
    }

    public void setEndSearch(String endSearch) {
        this.endSearch = endSearch;
    }
}
