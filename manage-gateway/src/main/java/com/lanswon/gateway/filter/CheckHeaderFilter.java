package com.lanswon.gateway.filter;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/6/29 13:50
 */
@Component
@Slf4j
public class CheckHeaderFilter implements GlobalFilter, Ordered {


    private final String SERVICE_NAME = "swat-oauthmanage";

    protected final static int ORDER = -1;

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String requestUri = request.getURI().getPath();

        JSONObject message = new JSONObject();

        log.debug("执行验证请求头过滤器…………");

        if(!requestUri.contains("oauth") && !requestUri.contains("/system/user/add") && !requestUri.contains("/system/company/list")){

            //TODO 请求头验证
            List<Object> list = checkHead(exchange, request);

            if(list.get(0) instanceof ServerHttpResponse){
                response = (ServerHttpResponse)list.get(0);
                message.put("code",HttpStatus.UNAUTHORIZED.value());
                message.put("msg","请求无TOKEN");
                byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                DataBuffer wrap = response.bufferFactory().wrap(bytes);
                return response.writeWith(Mono.just(wrap));

            }else{

                String token = (String) list.get(0);
                try {
                    //TODO 验证token有效性
                    Instance instance = discoveryProperties.namingServiceInstance().selectOneHealthyInstance(SERVICE_NAME);
                    String url = "http://"+instance.toInetAddr()+"/oauth/check_token?token="+token;
                    log.debug("授权服务检验TOKEN地址=[{}]",url);
                    Map<String, Object> result = restTemplate.getForObject(url, Map.class);
                    if(!(Boolean) result.get("active")) {
                        message.put("code",HttpStatus.UNAUTHORIZED.value());
                        message.put("msg","请求TOKEN非法");
                        byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                        DataBuffer wrap = response.bufferFactory().wrap(bytes);
                        return response.writeWith(Mono.just(wrap));
                    }

                } catch (NacosException e) {

                    e.printStackTrace();
                    message.put("code",HttpStatus.BAD_GATEWAY.value());
                    message.put("msg","无授权服务");
                    byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer wrap = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(wrap));

                }catch (Exception exception){

                    exception.printStackTrace();
                    message.put("code",HttpStatus.UNAUTHORIZED.value());
                    message.put("msg","请求TOKEN失效");
                    byte[] bytes = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer wrap = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(wrap));
                }
            }

        }
       return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return ORDER;
    }


    private List<Object> checkHead(ServerWebExchange exchange, ServerHttpRequest request){
        List<Object> list = new ArrayList<>();
        ServerHttpResponse response = exchange.getResponse();
        if(request.getHeaders().isEmpty()){
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.getHeaders().set("error","请求无TOKEN");
            list.add(response) ;
            return list;
        }
        List<String> tokenList = request.getHeaders().get("token");
        if(CollectionUtils.isEmpty(tokenList)){
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.getHeaders().set("error","请求无TOKEN");
            list.add(response) ;
            return list;
        }
        String token = tokenList.get(0);
        if(StringUtils.isEmpty(token)){
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.getHeaders().set("error","TOKEN为空");
            list.add(response) ;
            return list;
        }
        list.add(token);
        return list;
    }
}
