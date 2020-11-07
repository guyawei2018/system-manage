package com.lanswon.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 分页对象
 * @Author GU-YW
 * @Date 2019/12/3 16:35
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 8875576360114757274L;
    /**
     * 当前页数，默认第一页
     */
    private int page = 1;
    /**
     * 每页个数，默认10
     */
    private int limit = 10;
    /**
     * 总数，默认0
     */
    private int totalCount = 0;

    /**
     * 返回的集合
     */
    private List<T> list = new ArrayList<T>();

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 是否系统分页
     */
    private boolean isSysLimit = false;

    /**
     * 计算总页数
     * @return
     */
    public int getPageCount() {
        if(limit==0){
            return 0;
        }
        pageCount=totalCount/limit;
        if(totalCount%limit!=0){
            pageCount++;
        }
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 判断是否是首页
     * @return
     */
    public boolean isFirstPage() {
        return page<=1?true:false;
    }

    /**
     * 判断是否是尾页
     * @return
     */
    public boolean isLastPage() {
        return page>=getPageCount()?true:false;
    }

//    /** 分页构造器
//     * @param page 当前页
//     * @param limit  每页显示记录条数
//     * @param totalCount  总记录
//     */
//    public Page(int page, int limit, int totalCount) {
//        super();
//        this.page = page;
//        this.limit = limit;
//        this.totalCount = totalCount;
//    }

    /**
     * 内存分页构造器
     * @param page 当前页
     * @param limit  每页显示记录条数
     * @param totalCount  总记录
     * @param isSysLimit 是否需要在内存分页
     * @param list  数据集
     */
    public Page(int page, int limit, int totalCount, boolean isSysLimit, List<T> list) {
        super();
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
        this.isSysLimit = isSysLimit;
        this.list = getPagedList(list);
    }


    /**
     * 内存分页方式(list分页)
     *
     * @return 分页后结果
     */
    private List<T> getPagedList(List<T> list) {
        if(isSysLimit) {
            int fromIndex = (page - 1) * limit;
            if (fromIndex >= list.size()) {
                //空数组
                return Collections.emptyList();
            }
            if (fromIndex < 0) {
                //空数组
                return Collections.emptyList();
            }
            int toIndex = page * limit;
            if (toIndex >= list.size()) {
                toIndex = list.size();
            }
            return list.subList(fromIndex, toIndex);
        }else{
            return list;
        }
    }


    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLimite(int limit) {
        this.limit = limit;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

//    /**
//     *  页标数字多少个，默认10
//     */
//    private int pageNumShown = 10;
//    /**
//     * 排序方式
//     */
//    private String orderField;
//    /**
//     * 升序降序
//     */
//    private String orderDirection;

}
