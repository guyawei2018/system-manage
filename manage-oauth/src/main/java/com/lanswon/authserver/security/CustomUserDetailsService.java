package com.lanswon.authserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanswon.authcore.contants.MessageQueue;
import com.lanswon.authserver.dao.TYhxxMapper;
import com.lanswon.authserver.domain.entity.TYhxx;
import com.lanswon.base.error.ErrorCode;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.vo.OauthUser;
import com.lanswon.feign.ssm.SsmFeign;
import com.lanswon.ssm.vo.UserInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 处理用户信息
 * @Date 2019/10/29 9:22
 */
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Resource
	SsmFeign ssmFeign;

	@Resource
    TYhxxMapper yhxxMapper;

	private ObjectMapper objectMapper = new ObjectMapper();

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("系统登录用户名:" + username);
		return buildUser(username);
	}

	@SneakyThrows
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		log.info("第三方登录用户Id:" + userId);


		return buildUser(userId);

	}

	public SocialUserDetails loadUserByAnonymous(String userId) throws UsernameNotFoundException {
		log.info("游客登录用户Id:" + userId);
		List<TYhxx> tYhxxes = yhxxMapper.selectOneByMobile(userId);
		TYhxx tYhxx = new TYhxx();
		if(CollectionUtils.isEmpty(tYhxxes)){
		    tYhxx.setJh("");
		    tYhxx.setXm("");
		    tYhxx.setSfzhm("");
		    tYhxx.setJgbm("");
		    tYhxx.setJgqc("");
		    tYhxx.setYhzpdz("");
		}else{
            tYhxx = tYhxxes.get(0);
        }
		MessageQueue.ANONYMOUS_MAP.put(userId,tYhxx);
		return new SocialUser(userId, "123456", true, true,
				true,true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("anonymous"));
	}

	private SocialUserDetails buildUser(String userId) throws IOException {
		// TODO 根据用户名查找用户信息
		if(StringUtils.isEmpty(userId)){
			throw new UsernameNotFoundException("用户不能为空");
		}
		SimpleResponse<Map<String,Object>> simpleResponse = ssmFeign.queryOne(userId);

		if(Objects.isNull(simpleResponse.getData())){
			throw new UsernameNotFoundException(ErrorCode.getInstance(simpleResponse.getCode()).desc);
		}
		Map<String, Object> data = simpleResponse.getData();
		Object o = data.get("user");
		UserInfo user = objectMapper.readValue(objectMapper.writeValueAsString(o),UserInfo.class);
		Object o1 = data.get("oauth");
		OauthUser oauth = objectMapper.readValue(objectMapper.writeValueAsString(o1),OauthUser.class);
		MessageQueue.userInfo.put(user.getSfzhm(),user);
		MessageQueue.userOauth.put(user.getSfzhm(),oauth);

		//TODO 将所以认证之后的身份证号码放入认证信息中
		return new SocialUser(user.getSfzhm(), user.getYhmm(),
				user.getYhzt().equals("1"), true,
				true,true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
	}

	private String listToString(List<String> url){
		StringBuilder stringBuilder = new StringBuilder();
		if(CollectionUtils.isEmpty(url)){
			return stringBuilder.toString();
		}else{
			for(String str :url){
				stringBuilder.append(str).append(",");
			}
			return stringBuilder.substring(0,stringBuilder.length()-1);
		}
	}


}
