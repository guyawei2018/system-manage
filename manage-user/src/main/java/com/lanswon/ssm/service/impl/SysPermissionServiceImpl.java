package com.lanswon.ssm.service.impl;

import com.lanswon.base.error.ErrorCode;
import com.lanswon.base.page.Page;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.service.SysPermissionService;
import com.lanswon.ssm.dao.TJsQxMapper;
import com.lanswon.ssm.dao.TQxMapper;
import com.lanswon.ssm.domain.dto.PermissionQueryDto;
import com.lanswon.ssm.domain.entity.TJsQx;
import com.lanswon.ssm.domain.entity.TQx;
import com.lanswon.ssm.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/2 18:35
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysPermissionServiceImpl implements SysPermissionService {

    private final TQxMapper qxMapper;

    private final TJsQxMapper jsQxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse addSysPermission(TQx permission) throws ApplicationException {
        Example example = new Example(TQx.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("qxmc",permission.getQxmc());
        int i = qxMapper.selectCountByExample(example);
        if(i > 0) throw new ApplicationException(ErrorCode.SSM_PERMISSION_HAS_EXITS.code
                ,ErrorCode.SSM_PERMISSION_HAS_EXITS.desc);
        qxMapper.insert(permission);
        return SimpleResponse.ok("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse updateSysPermission(TQx permission) throws ApplicationException {
        Example example = new Example(TQx.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("qxmc",permission.getQxmc());
        TQx tQx = qxMapper.selectOneByExample(example);
        if(tQx != null && tQx.getQxbh() != permission.getQxbh()) throw new ApplicationException(ErrorCode.SSM_PERMISSION_HAS_EXITS.code
                ,ErrorCode.SSM_PERMISSION_HAS_EXITS.desc);
        qxMapper.updateByPrimaryKeySelective(permission);
        return SimpleResponse.ok("修改成功");
    }

    @Override
    public SimpleResponse queryAll(PermissionQueryDto dto) {
        List<TQx> tQxes = qxMapper.queryAllPermission(dto);
        int count = qxMapper.count(dto);
        Page<TQx> page = new Page<>(dto.getPage(),dto.getLimit(),count,false,tQxes);
        return SimpleResponse.ok(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse deleteSysPermission(Integer permissionId) throws ApplicationException {
        Example example = new Example(TJsQx.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("qxbh",permissionId);
        int i = jsQxMapper.selectCountByExample(example);
        if(i > 0) throw new ApplicationException(ErrorCode.SSM_PERMISSION_HAS_BINDING.code
                ,ErrorCode.SSM_PERMISSION_HAS_BINDING.desc);
        qxMapper.deleteByPrimaryKey(permissionId);
        return SimpleResponse.ok("删除成功");
    }

//    @Override
//    public SimpleResponse queryTree() {
//        List<MenuTreeVo> menuTreeVos = qxMapper.queryPermissionToTree();
//        List<MenuTreeVo> tree = TreeUtil.toTree(menuTreeVos);
//        return SimpleResponse.ok(tree);
//    }
}
