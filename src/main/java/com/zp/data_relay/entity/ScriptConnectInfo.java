/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.entity;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;

/**
 * ssh连接信息
 * @title SSHConnectInfo
 * @author zmzhou
 * @version 1.0
 * @date 2021/2/23 21:05
 */
@Data
public class ScriptConnectInfo implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1555506471798748444L;
	/** WebSocketSession */
	private WebSocketSession webSocketSession;
	private String uuid;
	private String toUuid;
	private String usertype;
}
