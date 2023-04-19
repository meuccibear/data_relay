/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.service;

import com.zp.data_relay.entity.ScriptData;
import org.springframework.web.socket.WebSocketSession;

/**
 * Web Shell业务逻辑实现
 *
 * @author zmzhou
 * @version 1.0
 * @title WebShellService
 * @date 2021/2/23 21:58
 */
public interface ScriptService {

    /**
     * 初始化连接
     *
     * @param uuid@author zmzhou
     * @date 2021/2/23 21:22
     */
    void connection(WebSocketSession session);



    void recvHandle(ScriptData scriptData, WebSocketSession webSocketSession);

    boolean isOff(String userId);

    /**
     * 关闭连接
     *
     * @param userId
     * @author zmzhou
     * @date 2021/2/23 21:16
     */
    void close(String userId);

    /**
     * 数据写回前端
     *
     * @param session WebSocketSession
     * @author zmzhou
     * @date 2021/2/23 21:18
     */
//    void sendMessage(WebSocketSession session, byte[] buffer);

}
