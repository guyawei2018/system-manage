package com.lanswon.authserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/** 校验Token和鉴权
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/15 19:46
 */
@RestController
@Slf4j
public class CheckTokenController {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${server.port}")
    private String port;

    @Resource
    RestTemplate restTemplate;

    @GetMapping("/oauth/checkToken")
    public Map<String ,Object> checkToken(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter("token");
        String uri = "http://127.0.0.1:"+port+"/oauth/check_token?token="+token;
        log.debug("授权服务ID=[{}]",uri);
        Map<String,Object> result=new HashMap<>();
        try{
        result = restTemplate.getForObject(uri,Map.class);
        result.put("code",HttpStatus.OK);
        log.debug("result=[{}]",result.toString());
        }catch (Exception ex){
           result.put("code",HttpStatus.UNAUTHORIZED);
           result.put("message","token非法");
           return result;
        }
        log.debug("Token验证结果=[{}]",result.get("active"));
        if(!(Boolean) result.get("active")){
            result.put("code",HttpStatus.UNAUTHORIZED);
            result.put("message","token失效");
            //TODO token失效加入处理队列

        }
        return result;

    }


}
