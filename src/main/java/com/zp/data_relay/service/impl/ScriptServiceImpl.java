/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.lzz.devops.Constants;
//import com.lzz.devops.vo.ScriptData;
//import com.lzz.devops.vo.ScriptConnectInfo;
//import io.renren.common.utils.ssh.WebShellUtils;
//import io.renren.modules.shell.service.ScriptService;
import com.zp.data_relay.entity.ScriptConnectInfo;
import com.zp.data_relay.entity.ScriptData;
import com.zp.data_relay.service.ScriptService;
import com.zp.data_relay.utils.BeanUtils;
import com.zp.data_relay.utils.Constants;
import com.zp.data_relay.utils.ObjectUtils;
import com.zp.data_relay.utils.WebShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;

/**
 * Web Shell业务逻辑实现
 *
 * @author zmzhou
 * @version 1.0
 * @title WebShellService
 * @date 2021/2/23 21:58
 */
@Slf4j
@Service("scriptService")
public class ScriptServiceImpl implements ScriptService {


    /**
     * 初始化连接
     *
     * @param uuid@author zmzhou
     * @date 2021/2/23 21:22
     */
    public void connection(WebSocketSession session) {
        String uuid = WebShellUtils.getUuid(session);
        log.info("【connection】{} 用户已连接", uuid);

        ScriptConnectInfo scriptConnectInfo = new ScriptConnectInfo();
//        scriptConnectInfo.setJsch(new JSch());
        scriptConnectInfo.setWebSocketSession(session);
        scriptConnectInfo.setUuid(uuid);
//        String uuid = WebShellUtils.getUuid(session);
        //将这个ssh连接信息放入缓存中
        Constants.SCRIPT_MAP.put(uuid, scriptConnectInfo);
    }


    @Override
    public void recvHandle(ScriptData scriptData, WebSocketSession webSocketSession) {
        String sessionId = scriptData.getSessionId();

        switch (scriptData.getOp()) {
            case "usertype":
                ScriptConnectInfo scriptConnectInfo = Constants.SCRIPT_MAP.get(sessionId);
                if (scriptConnectInfo != null) {
                    scriptConnectInfo.setUsertype((String) scriptData.getData());
                }
//                toSessionId
                if ("user".equals(scriptConnectInfo.getUsertype())) {
                    for (ScriptConnectInfo item : Constants.SCRIPT_MAP.values()) {
                        if ("admin".equals(item.getUsertype()) && ObjectUtils.isEmpty(item.getToUuid())) {
                            item.setToUuid(sessionId);
                            scriptConnectInfo.setToUuid(item.getUuid());
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("sessionId", item.getUuid());
                            sendMessage(sessionId, jsonObject);
                            jsonObject.put("sessionId", sessionId);
                            sendMessage(item.getUuid(), jsonObject);
                            return;
                        }
                    }
                }
                break;
            case "user":
            case "admin":
                JSONObject data = BeanUtils.toJSONObject(scriptData.getData());
                data.put("sessionId", sessionId);

                if ("all".equals(scriptData.getToSessionId())) {
                    for (ScriptConnectInfo item : Constants.SCRIPT_MAP.values()) {
                        if (!scriptData.getOp().equals(item.getUsertype())) {
                            sendMessage(item.getWebSocketSession(), data);
                        }
                    }
                } else {
                    sendMessage(null != scriptData.getToSessionId() ? scriptData.getToSessionId() : sessionId, data);
                }

                break;
        }
    }


    @Override
    public boolean isOff(String userId) {
        return !Constants.SCRIPT_MAP.containsKey(userId);
    }

    /**
     * 关闭连接
     *
     * @param userId
     * @author zmzhou
     * @date 2021/2/23 21:16
     */
    public void close(String userId) {
        ScriptConnectInfo shellConnectInfo = (ScriptConnectInfo) Constants.SCRIPT_MAP.get(userId);
        if (shellConnectInfo != null) {
            //map中移除
            Constants.SCRIPT_MAP.remove(userId);
        }
    }

    void sendMessage(String sessionId, JSONObject context) {
        ScriptConnectInfo scriptConnectInfo = Constants.SCRIPT_MAP.get(sessionId);
        sendMessage(null != scriptConnectInfo ? scriptConnectInfo.getWebSocketSession() : null, context);
    }

    /**
     * 数据写回前端
     *
     * @param session WebSocketSession
     * @author zmzhou
     * @date 2021/2/23 21:18
     */
    public void sendMessage(WebSocketSession session, JSONObject data) {
        try {
            if (null != session) {
                String context = JSON.toJSONString(data);
                session.sendMessage(new TextMessage(context.getBytes()));
            }
        } catch (IOException e) {
            log.error("数据写回前端异常：", e);
        }
    }


}
