package com.lanswon.ssm.service;

import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.entity.TDw;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.domain.dto.CompanyDto;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/16 17:34
 */
public interface SysCompanyService {

    /**
     * 新增工作单位
     * @param dw
     * @return
     * @throws ApplicationException
     */
    SimpleResponse addCompany(TDw dw) throws ApplicationException;

    /**
     * 修改注册单位
     * @param dw
     * @return
     * @throws ApplicationException
     */
    SimpleResponse updateCompany(TDw dw) throws ApplicationException;

    /**
     * 条件分页查询所有注册单位
     * @param dto
     * @return
     */
    SimpleResponse queryAll(CompanyDto dto);

    /**
     * 删除注册单位
     * @param companyId
     * @return
     */
    SimpleResponse deleteCompany(Integer companyId) throws ApplicationException;

}
