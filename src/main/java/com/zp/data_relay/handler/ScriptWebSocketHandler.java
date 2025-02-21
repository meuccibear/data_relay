/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.handler;

import com.alibaba.fastjson.TypeReference;
import com.zp.data_relay.entity.ScriptData;
import com.zp.data_relay.service.ScriptService;
import com.zp.data_relay.utils.BeanUtils;
import com.zp.data_relay.utils.WebShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import javax.annotation.Resource;


/**
 * WebSocket处理器
 *
 * @author zmzhou
 * @version 1.0
 * @title WebShellWebSocketHandler
 * @date 2021/2/22 20:58
 */
@Slf4j
@Component
public class ScriptWebSocketHandler implements WebSocketHandler {
    @Resource
    private ScriptService scriptService;

    /**
     * 用户连接上WebSocket回调
     *
     * @param webSocketSession WebSocketSession
     * @author zmzhou
     * @date 2021/2/23 20:35
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //调用初始化连接
        scriptService.connection(session);
    }

    /**
     * 收到消息回调
     *
     * @param webSocketSession WebSocketSession
     * @param webSocketMessage WebSocketMessage
     * @author zmzhou
     * @date 2021/2/23 20:41
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        if (webSocketMessage instanceof TextMessage) {
//            String uuid = WebShellUtils.getUuid(webSocketSession);
            String payload = ((TextMessage) webSocketMessage).getPayload();
            ScriptData scriptData = BeanUtils.toJavaObject(payload, new TypeReference<ScriptData>() {
            });

//            scriptData.setSessionId(uuid);
//            log.info("【recvHandle】{} 收到用户数据:{}", uuid, scriptData);

            //调用service接收消息
            scriptService.recvHandle(scriptData, webSocketSession);
        } else if (webSocketMessage instanceof BinaryMessage) {
            log.info("BinaryMessage:{}", webSocketMessage);
        } else if (webSocketMessage instanceof PongMessage) {
            log.info("PongMessage:{}", webSocketMessage);
        } else {
            log.error("Unexpected WebSocket message type: " + webSocketMessage);
        }
    }


    /**
     * 错误的回调
     *
     * @param webSocketSession WebSocketSession
     * @author zmzhou
     * @date 2021/2/23 20:41
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        log.error("用户:{},数据传输错误:{}", WebShellUtils.getUuid(webSocketSession), throwable);
    }

    /**
     * 连接关闭的回调
     *
     * @param webSocketSession WebSocketSession
     * @author zmzhou
     * @date 2021/2/23 20:43
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        String uuid = WebShellUtils.getUuid(webSocketSession);
        log.info("close-用户:{},断开webSocket连接:{}", uuid, closeStatus);
        //调用service关闭连接
        scriptService.close(uuid);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
