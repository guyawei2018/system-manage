package com.lanswon.ssm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tobato.fastdfs.domain.conn.TrackerConnectionManager;
import com.lanswon.base.error.ErrorCode;
import com.lanswon.base.page.Page;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.service.SysUserService;
import com.lanswon.ssm.service.UserService;
import com.lanswon.ssm.vo.OauthUrl;
import com.lanswon.ssm.vo.OauthUser;
import com.lanswon.ssm.dao.TJsMapper;
import com.lanswon.ssm.dao.TYhJsMapper;
import com.lanswon.ssm.dao.TYhMapper;
import com.lanswon.ssm.dao.TZpMapper;
import com.lanswon.ssm.dfs.FastDFSClient;
import com.lanswon.ssm.domain.dto.UserQueryDto;
import com.lanswon.ssm.domain.dto.UserDto;
import com.lanswon.ssm.domain.dto.UserRoleDto;
import com.lanswon.ssm.domain.entity.TJs;
import com.lanswon.ssm.domain.entity.TYh;
import com.lanswon.ssm.domain.entity.TYhJs;
import com.lanswon.ssm.domain.entity.TZp;
import com.lanswon.ssm.domain.enums.Enable;
import com.lanswon.ssm.domain.enums.UserStatus;
import com.lanswon.ssm.domain.vo.MenuTreeVo;
import com.lanswon.ssm.domain.vo.UserVo;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.util.TreeUtil;
import com.lanswon.ssm.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/2 18:37
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    private final TYhMapper yhMapper;

    private final TYhJsMapper yhJsMapper;

    private final TrackerConnectionManager trackerConnectionManager;

    private final FastDFSClient fastDFSClient ;

    private final TZpMapper zpMapper;

    private final TJsMapper jsMapper;

    private final UserService userService;

    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse addSysUser(UserDto usrDto) throws ApplicationException, JsonProcessingException {
        Example example = new Example(TYh.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("sfzhm",usrDto.getSfzhm());
        criteria.orEqualTo("yhmc",usrDto.getYhmc());
        criteria.orEqualTo("sjhm",usrDto.getSjhm());
        List<TYh> user = yhMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(user)){
            throw new ApplicationException(Integer.valueOf(ErrorCode.SSM_USER_HAS_EXITS.code),
                    ErrorCode.SSM_USER_HAS_EXITS.desc);
        }
        //TODO 系统注册
        if(StringUtils.isNotEmpty(usrDto.getSjly())){
            usrDto.setSjly("0");
        }
        usrDto.setCjsj(new Date());
        if(usrDto.getIsSystemIn() == 0){
        }else if(usrDto.getIsSystemIn() == 2){
            //TODO APP注册
            usrDto.setSjly("1");
        }
        if(!Objects.isNull(usrDto.getImageBase64())){
            //TODO 添加人脸逻辑 +绑定人脸编号
        }
        usrDto.setZhzt(Enable.ON.code);
        usrDto.setYhzt(UserStatus.OFF.code);
        usrDto.setYhmm(usrDto.getYhmm() != null ? passwordEncoder.encode(usrDto.getYhmm()) : passwordEncoder.encode(
                "123456"));
        yhMapper.insert(usrDto);
        //TODO 发送注册信息
//        UumContant.REGISTERQUEUE.add(usrDto);
        return SimpleResponse.ok("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse upload(UserDto usrDto) throws ApplicationException, IOException {
        TYh yh = yhMapper.selectByPrimaryKey(usrDto.getYhbh());
        if(Objects.isNull(yh)){
            throw new ApplicationException(Integer.valueOf(ErrorCode.SSM_USER_NO_EXITS.code),
                    ErrorCode.SSM_USER_NO_EXITS.desc);
        }
//        String prePath ="http://"+trackerConnectionManager.getTrackerList().get(0).split(":")[0]+"/";
//        MultipartFile multipartFile = FileUtil.base64ToMultipartFile(usrDto.getImageBase64());
//        StorePath storePath = fastDFSClient.uploadBase64(multipartFile);
        TZp zp = zpMapper.selectByPrimaryKey(yh.getSfzhm());
        if(!Objects.isNull(zp)){
            //TODO 暂时关闭文件上传
//            fastDFSClient.deleteFile("qz/"+zp.getYslj());
//            fastDFSClient.deleteFile("qz"+zp.getLj().split("qz")[1]);
//            zp.setLj(prePath+storePath.getFullPath());
//            zp.setYslj(storePath.getPath());
            zp.setZp64m(usrDto.getImageBase64());
            zpMapper.updateByPrimaryKeySelective(zp);
        }else{
            zp = new TZp();
            zp.setSfzhm(usrDto.getSfzhm());
//            zp.setLj(prePath+storePath.getFullPath());
//            zp.setYslj(storePath.getPath());
            zp.setZp64m(usrDto.getImageBase64());
            zpMapper.insertSelective(zp);
        }

        return  SimpleResponse.builder()
                .code(HttpStatus.OK.value())
                .data(null)
                .msg("导入成功")
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse updateSysUser(UserDto usrDto) throws ApplicationException, JsonProcessingException {
        Example example = new Example(TYh.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("sfzhm",usrDto.getSfzhm());
        criteria.orEqualTo("yhmc",usrDto.getYhmc());
        criteria.orEqualTo("sjhm",usrDto.getSjhm());
        List<TYh> user = yhMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(user)){
            for(TYh instance : user){
                if( instance.getYhbh().intValue() != usrDto.getYhbh().intValue()){
                    throw new ApplicationException(Integer.valueOf(ErrorCode.SSM_USER_HAS_EXITS.code)
                            ,ErrorCode.SSM_USER_HAS_EXITS.desc);
                }
            }
        }

        if(!Objects.isNull(usrDto.getImageBase64())){
            //TODO 更新人脸操作
        }
        yhMapper.updateByPrimaryKeySelective(usrDto);
        return SimpleResponse.ok("更新成功");
    }

    @Override
    public SimpleResponse queryAll(UserQueryDto dto) {
        List<UserVo> sysUserVos = yhMapper.queryAllUsers(dto);
        int count = yhMapper.count(dto);
        sysUserVos.forEach(instance ->{
            if(instance.getZhzt() != null){
                instance.setEnabledDec(Enable.getInstance(instance.getZhzt()).desc);
            }
            if(instance.getYhzt() != null) {
                instance.setUserStatusDec(UserStatus.getInstance(instance.getYhzt()).desc);
            }
        });
        Page<UserVo> page = new Page<>(dto.getPage(),dto.getLimit(),count,false,sysUserVos);
        return SimpleResponse.ok(page);
    }

    @Override
    public SimpleResponse selectUserRole(Integer userId) {
        Map<String,Object> result = new HashMap<>();
        Example example = new Example(TYhJs.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("yhbh",userId);
        List<TYhJs> tYhJs = yhJsMapper.selectByExample(example);
        List<TJs> jsList = jsMapper.selectAll();
        result.put("has",tYhJs);
        result.put("all",jsList);
        return SimpleResponse.ok(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse userRole(UserRoleDto dto) {
        Example example = new Example(TYhJs.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("yhbh",dto.getYhbh());
        yhJsMapper.deleteByExample(example);
        if(!CollectionUtils.isEmpty(dto.getIds())){
            List<TYhJs> list = new ArrayList<>();
            for(Integer id:dto.getIds()){
                TYhJs jsQx = new TYhJs();
                jsQx.setJsbh(id);
                jsQx.setYhbh(dto.getYhbh());
                list.add(jsQx);
            }
            yhJsMapper.batchInsert(list);
        }
        return SimpleResponse.ok("绑定成功");
    }

    @Override
    public SimpleResponse queryUserInfo(String param) throws ApplicationException {
        System.out.println(passwordEncoder.encode("111111"));
        System.out.println(passwordEncoder.encode("admin").equals("$2a$10$jpcSiBFLGz1cuMxJXCHwTO8bew.jCj8XWFQPLolOkB/cSjistgPy2"));
        Map<String,Object> result = new HashMap<>();
        UserInfo userInfo = yhMapper.queryUserInfo(param);
        if(userInfo == null){
            throw new ApplicationException(ErrorCode.SSM_USER_NO_EXITS.code,ErrorCode.SSM_USER_NO_EXITS.desc);
        }
        OauthUser oauthUser = new OauthUser();
        oauthUser.setSfzhm(userInfo.getSfzhm());
        List<OauthUrl> oauthUrls = yhMapper.queryUserPermission(userInfo.getSfzhm(), null);
        oauthUser.setUrls(oauthUrls);
        result.put("user",userInfo);
        result.put("oauth",oauthUser);
        return SimpleResponse.ok(result);
    }

    @Override
    public SimpleResponse me(String sfzhm) throws UnsupportedEncodingException {
        UserInfo userInfo = userService.getCurrentUserById(sfzhm);
        if(userInfo == null){
            //TODO 再次缓存个人信息和权限信息
            userInfo = yhMapper.queryUserInfo(sfzhm);
            userService.setCurrentUser(userInfo);
            OauthUser oauthUser = new OauthUser();
            oauthUser.setSfzhm(userInfo.getSfzhm());
            List<OauthUrl> oauthUrls = yhMapper.queryUserPermission(userInfo.getSfzhm(), null);
            oauthUser.setUrls(oauthUrls);
            userService.setOauth(oauthUser);
        }
        Map<String,Object> result = new HashMap<>();
        //TODO 查询菜单权限
        List<OauthUrl> oauthUrls = yhMapper.queryUserPermission(userInfo.getSfzhm(), "0");
        List<MenuTreeVo> list = new ArrayList<>();
        for(OauthUrl oauthUrl:oauthUrls){
            MenuTreeVo vo = new MenuTreeVo();
            BeanUtils.copyProperties(oauthUrl,vo);
            list.add(vo);
        }
        List<MenuTreeVo> tree = TreeUtil.toTree(list);
        result.put("me",userInfo);
        result.put("menu",tree);

        return SimpleResponse.ok(result);
    }
}
