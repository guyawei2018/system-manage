package com.lanswon.ssm.service.impl;

import com.lanswon.base.error.ErrorCode;
import com.lanswon.base.page.Page;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.dao.TJsMapper;
import com.lanswon.ssm.dao.TJsQxMapper;
import com.lanswon.ssm.dao.TQxMapper;
import com.lanswon.ssm.domain.dto.RolePermissionDto;
import com.lanswon.ssm.domain.dto.RoleQueryDto;
import com.lanswon.ssm.domain.entity.TJs;
import com.lanswon.ssm.domain.entity.TJsQx;
import com.lanswon.ssm.domain.entity.TQx;
import com.lanswon.ssm.domain.entity.TYhJs;
import com.lanswon.ssm.domain.vo.MenuTreeVo;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.service.SysRoleService;
import com.lanswon.ssm.util.TreeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/2 18:36
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysRoleServiceImpl implements SysRoleService {

    private final TJsMapper jsMapper;

    private final TJsQxMapper jsQxMapper;

    private final TQxMapper qxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse addSysRole(TJs role) throws ApplicationException {
        Example example = new Example(TQx.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("jsmc",role.getJsmc());
        criteria.orEqualTo("jsdm",role.getJsdm());
        int i = jsMapper.selectCountByExample(example);
        if(i > 0) throw new ApplicationException(ErrorCode.SSM_SYS_ROLE_HAS_EXITS.code
                ,ErrorCode.SSM_SYS_ROLE_HAS_EXITS.desc);
        jsMapper.insert(role);
        return SimpleResponse.ok("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse updateSysRole(TJs role) throws ApplicationException {
        Example example = new Example(TQx.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("jsmc",role.getJsmc());
        criteria.orEqualTo("jsdm",role.getJsdm());
        List<TJs> jsList = jsMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(jsList)){
            for(TJs js :jsList){
                if(js.getJsbh() != role.getJsbh()){
                    throw new ApplicationException(ErrorCode.SSM_SYS_ROLE_HAS_EXITS.code
                            ,ErrorCode.SSM_SYS_ROLE_HAS_EXITS.desc);
                }
            }
        }
        jsMapper.updateByPrimaryKey(role);
        return SimpleResponse.ok("修改成功");
    }

    @Override
    public SimpleResponse queryAll(RoleQueryDto dto) {
        List<TJs> jsList = jsMapper.queryAllRoles(dto);
        int count = jsMapper.count(dto);
        Page<TJs> page = new Page<>(dto.getPage(),dto.getLimit(),count,dto.getIsPagination() == 0,jsList);
        return SimpleResponse.ok(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse deleteSysRole(Integer roleId) throws ApplicationException {
        Example example = new Example(TYhJs.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("jsbh",roleId);
        int i = jsQxMapper.selectCountByExample(example);
        if(i > 0) throw new ApplicationException(ErrorCode.SSM_ROLE_HAS_BINDING.code
                ,ErrorCode.SSM_ROLE_HAS_BINDING.desc);
        jsMapper.deleteByPrimaryKey(roleId);
        return SimpleResponse.ok("删除成功");
    }

    @Override
    public SimpleResponse selectSysRolePermission(Integer roleId) {
        Map<String,Object> result = new HashMap<>();
        List<MenuTreeVo> menuTreeVos = qxMapper.queryPermissionToTree();
        List<MenuTreeVo> tree = TreeUtil.toTree(menuTreeVos);
        Example example = new Example(TJsQx .class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("jsbh",roleId);
        List<TJsQx> list = jsQxMapper.selectByExample(example);
        result.put("tree",tree);
        result.put("has",list);
        return SimpleResponse.ok(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse bindingPermission(RolePermissionDto dto) {
        Example example = new Example(TJsQx.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("jsbh",dto.getRoleId());
        jsQxMapper.deleteByExample(example);
        if(!CollectionUtils.isEmpty(dto.getPermissionId())){
            List<TJsQx> list = new ArrayList<>();
            for(Integer id:dto.getPermissionId()){
                TJsQx jsQx = new TJsQx();
                jsQx.setJsbh(dto.getRoleId());
                jsQx.setQxbh(id);
                list.add(jsQx);
            }
            jsQxMapper.batchInsert(list);
        }
        return SimpleResponse.ok("绑定成功");
    }
}
