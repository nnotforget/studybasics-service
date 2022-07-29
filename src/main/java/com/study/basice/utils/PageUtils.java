package com.study.basice.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongXl on 2016/4/7.
 */
public class PageUtils<T> implements Serializable {

    public static final String ASC = "asc";

    public static final String DESC = "desc";

    private static final String COL_SPLIT = ",";
    private static final String ORD_SPLIT = " ";
    private static final int ORD_LENGTH = 2;

    public static final int DEFAULT_PAGE_SIZE = 200;

    /**
     * 排序方式
     */
    protected String order;
    /**
     * 需要排序的列
     * 如果只需要单列排序，则order为排序方式，orderby 为列名；
     * 如果需要多列排序，则order为null，orderby值为'col1 ASC,col2 DESC'格式
     */
    protected String orderBy;
    protected int pageIndex;
    protected int pageSize;
    private List<T> data;
    private long totalCount;

    public PageUtils() {
        this(1);
    }

    /**
     * @param pageIndex
     */
    public PageUtils(int pageIndex) {
        this(pageIndex, DEFAULT_PAGE_SIZE);
    }

    /**
     * @param pageIndex
     * @param pageSize
     */
    public PageUtils(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    /**
     * @return data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data 要设置的 data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return first
     */
    public int getFirst() {
        if (pageIndex < 1 || pageSize < 1) {
            return -1;
        } else {
            return ((pageIndex - 1) * pageSize);
        }
    }

    /**
     * @return inverseOrder
     */
    //TODO 多列排序未考虑
    public String getInverseOrder() {
        if (order.endsWith(DESC)) {
            return ASC;
        } else {
            return DESC;
        }
    }

    /**
     * @return nextPage
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageIndex + 1;
        } else {
            return pageIndex;
        }
    }

    /**
     * @return order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param order 要设置的 order
     */
    public void setOrder(String order) {
        if (null == order) {
            this.order = ASC;
        } else if (ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)) {
            this.order = order.toLowerCase();
        } else {
            throw new IllegalArgumentException("order should be 'desc' or 'asc'");
        }
    }

    /**
     * @return orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy 要设置的 orderBy
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return pageIndex
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * @param pageIndex 要设置的 pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * @return pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize 要设置的 pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageIndex - 1;
        } else {
            return pageIndex;
        }
    }

    /**
     * @return totalCount
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount 要设置的 totalCount
     */
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return totalPage
     */
    public long getTotalPage() {
        if (totalCount == -1) {
            return 1;
        }
        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * @return firstSetted
     */
    public boolean isFirstSetted() {
        return (pageIndex > 0 && pageSize > 0);

    }

    /**
     * @return hasNext
     */
    public boolean isHasNext() {
        return (pageIndex + 1 <= getTotalPage());
    }

    /**
     * @return hasPre
     */
    public boolean isHasPre() {
        return (pageIndex - 1 >= 1);
    }

    /**
     * @return orderBySetted
     */
    public boolean isOrderBySetted() {
        if (orderBy != null) {
            orderBy = orderBy.trim();
            return StringUtils.isNotBlank(order);
        }
        return false;
    }

    /**
     * @return pageSizeSetted
     */
    public boolean isPageSizeSetted() {
        return pageSize > -1;
    }

    public void clonePage(Page<T> page) {
        this.setData(page.getContent());
        this.setTotalCount(page.getTotalElements());
    }
}
