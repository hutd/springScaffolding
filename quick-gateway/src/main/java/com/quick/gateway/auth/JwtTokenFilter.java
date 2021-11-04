package com.quick.gateway.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import com.quick.gateway.properties.FilterProperties;
import com.quick.gateway.properties.TokenProperties;
import com.quick.utils.RedisUtils;
import com.quick.vo.ExceptionResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * gateway验证token
 */
@Component
@Data
@Slf4j
@EnableConfigurationProperties({FilterProperties.class, TokenProperties.class})
public class JwtTokenFilter implements GlobalFilter, Ordered {

    public final static String TOKEN_AUTHORIZATION = "Authorization";

    @Resource
    private TokenProperties tokenProperties;

    @Resource
    private FilterProperties filterProps;

    @Resource
    private RedisUtils redisUtils;

    private ObjectMapper objectMapper;

    public JwtTokenFilter(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }


    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();

        //判断白名单
        if (isAllowPath(url)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(TOKEN_AUTHORIZATION);

        ServerHttpResponse resp = exchange.getResponse();

        if (StringUtils.isBlank(token)) {
            //没有token
            return authErro(resp, new CamelliaException(ExceptionEnum.USER_TOKEN_FAILED));
        }
        //如果缓存中存在token直接验证通过
        if (!redisUtils.hasKey(token)) {
            // todo 如果redis中没有token
            return authErro(resp, new CamelliaException(ExceptionEnum.USER_TOKEN_FAILED));
        }
        // 刷新一下token时间
        redisUtils.expire(token, tokenProperties.getTokenExpire());
        return chain.filter(exchange);
    }

    /**
     * 认证错误输出
     *
     * @param resp              响应对象
     * @param camelliaException 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp, CamelliaException camelliaException) {
        resp.setStatusCode(HttpStatus.OK);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ExceptionResult returnData = new ExceptionResult(camelliaException.getExceptionEnum());
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(returnData);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    /**
     * 判断请求URI是不是白名单中的URI
     *
     * @param requestURI
     * @return
     */
    private Boolean isAllowPath(String requestURI) {
        boolean flag = false;

        for (String allowPath : filterProps.getAllowPaths()) {
            if (requestURI.startsWith(allowPath)) {
                //允许
                flag = true;
                break;
            }
        }
        return flag;

    }

    @Override
    public int getOrder() {
        return -100;
    }
}
