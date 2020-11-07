package com.lanswon.base.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.Objects;

/**
 * @Description: 分页参数
 * @Author GU-YW
 * @Date 2019/12/4 9:32
 */
@ApiModel(value = "分页参数")
@ToString
public class BasePageDto {

    @ApiModelProperty(value = "分页参数-每页个数" )
    private int limit = 10;

    @ApiModelProperty(value = "分页参数-当前页数")
    private int page = 1;

    @ApiModelProperty(hidden = true)
    private int startLine= 0;

    @ApiModelProperty(value = "分页参数-是否分页，默认分页（1）")
    private int isPagination ;

    public int getLimit() {
        if(getIsPagination()==0){
            return 0;
        }
        if(limit == 0){
            return 10;
        }
        return limit;
    }

    public int getPage() {
        if(getIsPagination()==0){
            return 0;
        }
        if(page == 0){
            return 1;
        }
        return page;
    }

    public int getStartLine() {
        return this.startLine = (getPage()-1)*getLimit();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getIsPagination() {
        if(Objects.isNull(isPagination)){
            return 1;
        }
        return isPagination;
    }
    public void setIsPagination(int isPagination) {
        this.isPagination = isPagination;
    }
}
