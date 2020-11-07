package com.lanswon.ssm.dao;

import com.lanswon.ssm.domain.entity.TJsQx;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TJsQxMapper extends Mapper<TJsQx> {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(@Param("list")List<TJsQx> list);
}