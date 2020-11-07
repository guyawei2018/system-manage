package com.lanswon.authserver.dao;

import com.lanswon.authserver.domain.entity.TYhxx;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TYhxxMapper extends Mapper<TYhxx> {

    /**
     * 通过手机号码查询个人信息
     * @param mobile
     * @return
     */
    List<TYhxx> selectOneByMobile(@Param("mobile") String mobile);
}