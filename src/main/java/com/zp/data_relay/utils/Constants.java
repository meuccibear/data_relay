/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.utils;


import com.zp.data_relay.entity.ScriptConnectInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 *
 * @author zmzhou
 * @version 1.0
 * @title ConstantPool
 * @date 2021/2/23 20:39
 */
public final class Constants {
    /**
     * 分隔符
     */
    public static final String SEPARATOR = "/";
    /**
     * 减号
     */
    public static final String MINUS = "-";
    /**
     * 点
     */
    public static final String DOT = ".";
    /**
     * 上级目录
     */
    public static final String PARENT_DIRECTORY = "..";
    /**
     * root用户
     */
    public static final String USER_ROOT = "root";
    /**
     * 缓存字符长度
     */
    public static final int BUFFER_SIZE = 2048;
    /**
     * 随机生成uuid的key名
     */
    public static final String USER_UUID_KEY = "user_uuid";
    /**
     * 发送指令：连接
     */
    public static final String OPERATE_CONNECT = "connect";
    /**
     * 发送指令：命令
     */
    public static final String OPERATE_COMMAND = "command";
    /**
     * 发送指令：安全文件传送
     */
    public static final String OPERATE_SFTP = "sftp";

    /**
     * 1kb
     */
    public static final long KB = 1024L;

    private Constants() {
    }

    public static List<String> BLACKLIST_PHRASES = new ArrayList<String>() {{
        String[] strs = new String[]{"\\\\u001b", "\u001B", "[?2004h", "[?2004l", "[01;31m", "[m", "[K", "[?"};
        for (String str : strs) {
            add(str.replaceAll("\\[", "\\\\[").replaceAll("\\?", "\\\\?"));
        }
//        [0m[01;34m
        //     \u001B
//        add("\u001B");
//        add("[?2004h");
//        add("[01;31m");
//        add("[?2004l");
//        add("[m");
//        add("[K");
//        add("[?");
    }};

    /**
     * 存放ssh连接信息的map
     */
    public static final Map<String, Object> SSH_MAP = new ConcurrentHashMap<>();
    public static final Map<String, ScriptConnectInfo> SCRIPT_MAP = new ConcurrentHashMap<>();


//.replaceAll('','').replaceAll('[?2004h','').replaceAll('[01;31m','').replaceAll('[?2004l','').replaceAll('[m','').replaceAll('[K','').replaceAll('[?','').replaceAll('\r\n\r','\n')

}
