package com.lanswon.authserver.service.impl;

import com.lanswon.authserver.dao.TYhxxMapper;
import com.lanswon.authserver.dao.TYhzpMapper;
import com.lanswon.authserver.domain.entity.TYhxx;
import com.lanswon.authserver.domain.entity.TYhzp;
import com.lanswon.authserver.service.UserService;
import com.lanswon.base.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/15 10:41
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private TYhxxMapper yhxxMapper;

    @Resource
    private TYhzpMapper yhzpMapper;

    @Override
    public SimpleResponse getUser(String phone) {
        Map<String,Object> map = new HashMap<>();
        if(Objects.isNull(phone)){
            throw new NullPointerException("手机号码不能为空");
        }
        List<TYhxx> tYhxxes = yhxxMapper.selectOneByMobile(phone);
        if(CollectionUtils.isEmpty(tYhxxes)){
            return SimpleResponse.builder().code(HttpStatus.MULTIPLE_CHOICES.value()).msg("统一平台不存在用户信息").build();
        }
        TYhxx tYhxx = tYhxxes.get(0);
        TYhzp tYhzp = yhzpMapper.selectByPrimaryKey(tYhxx.getSfzhm());
        map.put("user",tYhxxes.get(0));
        if(Objects.isNull(tYhzp)){
            map.put("basecode",null);
        }else{
            map.put("basecode",tYhzp.getYhzp()==null?null:tYhzp.getYhzp());
        }

        return SimpleResponse.builder().code(HttpStatus.OK.value()).msg("查询成功").data(map).build();
    }
}
