package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * Created by w7 on 2016/5/19.
 */
public class PaginationInfo implements Serializable {
    private int pageSize;//    "pageSize": 2, //每页数据量
    private int currentPage;//            "currentPage": 2, //当前页
    private int totalPages;//            "totalPages": 28, //总页数
    private int totalCount;//            "totalCount": 55//总数据

    public PaginationInfo() {
    }

    public PaginationInfo(int currentPage, int pageSize, int totalCount, int totalPages) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PaginationInfo{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalCount=" + totalCount +
                '}';
    }
}
