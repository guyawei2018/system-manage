package com.lanswon.ssm.service.impl;

import com.lanswon.base.page.Page;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.dao.TDwMapper;
import com.lanswon.ssm.domain.dto.CompanyDto;
import com.lanswon.ssm.domain.entity.TDw;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.service.SysCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/2 18:34
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysCompanyServiceimpl implements SysCompanyService {

    private final TDwMapper dwMapper;

    @Override
    public SimpleResponse addCompany(TDw dw) throws ApplicationException {
        return null;
    }

    @Override
    public SimpleResponse updateCompany(TDw dw) throws ApplicationException {
        return null;
    }

    @Override
    public SimpleResponse queryAll(CompanyDto dto) {
        Example example = new Example(TDw.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(dto.getCompanyCode())){
            criteria.andLike("jgbm",dto.getCompanyCode());
        }
        if(StringUtils.isNotEmpty(dto.getCompanyName())){
            criteria.andLike("jgqc",dto.getCompanyName());
        }
        List<TDw> tDws = dwMapper.selectByExample(example);
        Page<TDw> page = new Page<>(dto.getPage(),dto.getLimit(),tDws.size(),dto.getIsPagination()==1,tDws);
        return SimpleResponse.ok(page);
    }

    @Override
    public SimpleResponse deleteCompany(Integer companyId) throws ApplicationException {
        return null;
    }
}
