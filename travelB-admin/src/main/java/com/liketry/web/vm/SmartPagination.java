package com.liketry.web.vm;

import java.io.Serializable;

/**
 * Created by liketry
 */
public class SmartPagination implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer start;

    private Integer number;

    private Integer numberOfPages;

    private Integer totalItemCount;

    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
