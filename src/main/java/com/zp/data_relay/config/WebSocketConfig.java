/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.config;

import com.zp.data_relay.handler.ScriptWebSocketHandler;
import com.zp.data_relay.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * websocket配置
 *
 * @author Mr.Lv lvzhuozhuang@foxmail.com
 * @version 1.0
 * @title WebSocketConfig
 * @date 2022/8/13 2:21
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private ScriptWebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //socket通道
        //指定处理器和路径
        webSocketHandlerRegistry.addHandler(webSocketHandler, "/shellso")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
