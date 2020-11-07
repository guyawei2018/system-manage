package com.lanswon.ssm.dao;

import com.lanswon.ssm.domain.entity.TYhJs;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TYhJsMapper extends Mapper<TYhJs> {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(@Param("list")List<TYhJs> list);
}