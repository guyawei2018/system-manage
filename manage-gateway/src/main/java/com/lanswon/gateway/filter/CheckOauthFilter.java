package com.lanswon.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.lanswon.ssm.service.UserService;
import com.lanswon.ssm.vo.OauthUrl;
import com.lanswon.ssm.vo.OauthUser;
import com.lanswon.ssm.vo.UserInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/16 9:17
 */
@Slf4j
@Component
public class CheckOauthFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    UserService userService;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String id = request.getRemoteAddress().getHostString();
        System.out.println(id);
        ServerHttpResponse response = exchange.getResponse();
        //TODO　指定编码，解决中文浏览器乱码
        response.getHeaders().add("Content-Type","text/plain;charset=UTF-8");

        String requestUri = request.getURI().getPath();
        String requestMethod = request.getMethod().name();

        JSONObject message = new JSONObject();

        log.debug("执行验证权限过滤器…………");

        log.debug("网关拦截的请求地址=[{}],请求方法[{}]",requestUri,requestMethod);

        if(!requestUri.contains("oauth") && !requestUri.contains("/system/user/add") && !requestUri.contains("/system/company/list")){

            String token = request.getHeaders().get("token").get(0);

           try {
               UserInfo o = userService.getCurrentUser(token);
               if(Objects.isNull(o)){
                    message.put("code",HttpStatus.UNAUTHORIZED.value());
                    message.put("msg","用户被重新审核授权,请重新登陆");
                    byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer wrap = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(wrap));
                }

               //TODO 排除游客
               if(!o.getSjhm().equals("-1") ){
                   OauthUser oauthUser = userService.getOauth(o.getSjhm());
                   if(Objects.isNull(oauthUser)){
                       //TODO 不存在用户权限
                       message.put("code",HttpStatus.UNAUTHORIZED.value());
                       message.put("msg","不存在用户权限,请重新登陆");
                       byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                       DataBuffer wrap = response.bufferFactory().wrap(bytes);
                       return response.writeWith(Mono.just(wrap));
                   }
//                   String sqdz = oauthUser.getSqdz();
//                   if(!sqdz.equals(id)){
//                       message.put("code",HttpStatus.UNAUTHORIZED.value());
//                       message.put("msg","用户已在异地登陆,请注意账号安全");
//                       byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
//                       DataBuffer wrap = response.bufferFactory().wrap(bytes);
//                       return response.writeWith(Mono.just(wrap));
//                   }

               }
           }catch (Exception ex){
               ex.printStackTrace();
               message.put("code",HttpStatus.UNAUTHORIZED.value());
               message.put("msg","登陆信息过期，请重新登陆。");
               byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
               DataBuffer wrap = response.bufferFactory().wrap(bytes);
               return response.writeWith(Mono.just(wrap));
           }

//            checkPermission(requestUri,requestMethod,oauthUser);

        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 检测权限
     * @param url
     * @param method
     * @param oauthUser
     * @return
     */
    private boolean checkPermission(String url,String method,OauthUser oauthUser){
        Boolean has = false;
        if(CollectionUtils.isEmpty(oauthUser.getUrls())){
            return has;
        }
        String newUrl = updateUrl(url);
        List<OauthUrl> urls = oauthUser.getUrls();
        for(OauthUrl oauthUrl :urls){
            if(antPathMatcher.match(oauthUrl.getQxlj(),newUrl) && StringUtils.equals(oauthUrl.getQxff(),method)){
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * 去掉多余一级请求地址
     * @param url
     * @return
     */
    private static String updateUrl(String url){
        String [] strings = StringUtils.splitPreserveAllTokens(url,"/");
        StringBuilder sb = new StringBuilder();
        for(int i = 2;i<strings.length;i++){
            sb.append("/").append(strings[i]);
        }
        return sb.toString();
    }
}
