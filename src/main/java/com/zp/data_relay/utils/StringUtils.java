package com.zp.data_relay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: DemoJava
 * @description:
 * @author: Zhuozhuang.Lv
 * @create: 2020-01-06 16:20
 */
@Slf4j
public class StringUtils<resultMap> {

    public static Boolean isEmpty(Object value) {
        return !notIsEmpty(value);
    }

    public static Boolean notIsEmpty(Object value) {
        if (value == null) {
            return false;
        } else if (value instanceof String) {
//            System.out.println("String");
            return ((String) value).length() > 0;
        } else if (value instanceof Integer) {
//            System.out.println("Integer");
            return (Integer) value > 0;
        }
        return true;
    }

    public static String substringSup(String str, String beginStr, String endStr) {
        beginStr = StringUtils.clearSpace(beginStr);
        endStr = StringUtils.clearSpace(endStr);
        return substringx(str, beginStr, endStr);

    }

    public static String substringx(String str, String beginStr, String endStr) {
        int beginIndex = str.indexOf(beginStr);
        if (beginIndex > -1) {
            String lastStr = str.substring(beginIndex + beginStr.length() - beginStr.length(), str.length());
//            System.out.println(lastStr);
            int lastIndex = lastStr.indexOf(endStr);
            if (StringUtils.notIsEmpty(lastIndex) && lastIndex > -1) {
                return lastStr.substring(0, lastIndex + endStr.length()).toString().replaceAll(" ", "");
            } else {
                return "";
            }
        }
        return "";
    }

    /**
     * @throws
     * @title 截取字符串高级版
     * @description
     * @author Mr.Lv lvzhuozhuang@foxmail.com
     * @updateTime 2022/7/25 16:33
     */
    public static String substring(String str, String beginStr, String endStr) {
        Integer beginIndex;
        String lastStr = null;
        if (beginStr != null) {
            beginIndex = str.indexOf(beginStr);

            if (beginIndex > -1) {
                lastStr = str.substring(beginIndex + beginStr.length(), str.length());
            }
        } else {
            lastStr = str;
        }

        if (null != lastStr && null != endStr) {
            int lastIndex = lastStr.indexOf(endStr);
            if (StringUtils.notIsEmpty(lastIndex) && lastIndex > -1) {
                return lastStr.substring(0, lastIndex).toString().replaceAll(" ", "");
            } else {
                return "";
            }
        }

        return "";
    }

    public static String getString(String res, String regex) {
        regex = StringUtils.clearSpace(regex);

        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
        // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(regex);
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(res);
        // 如果找到了
        if (matcher.find()) {
            // 打印出结果
            return matcher.group(1);
        }

        return "";
    }

    public static String clearSpace(String str) {
        return str.replaceAll(" ", "");
    }

    public static boolean indexOf(String str, String indexOfStr) {
        int index = str.indexOf(".html");
        return index >= 0;
    }

    public static String toUrl(Object obj) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(obj);
        Iterator<String> iterator = jsonObject.keySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = (String) jsonObject.get(key);
            if (StringUtils.notIsEmpty(value)) {
                stringBuilder.append(String.format("%s=%s", key, value)).append("; ");
            }
        }
        return stringBuilder.toString();
    }

    public static String outStr(String str, Object... clos) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object clo : clos) {
            if (ObjectUtils.notIsEmpty(clo)) {
                stringBuffer.append(clo);
            } else {
                stringBuffer.append("");
            }
            stringBuffer.append(str);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }


    public static void writeList(String str, Object... clos) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object clo : clos) {
            stringBuffer.append(str);
            stringBuffer.append(clo);
        }
        System.out.println(stringBuffer.substring(1, stringBuffer.length()));
    }


    /**
     * @param tempStr
     * @param str
     * @return
     */
    public static String toTempStr(String tempStr, String str) {
        int num = tempStr.length() - str.length();
        return tempStr.substring(0, num) + str;
    }


    /**
     * @param groupStr
     * @return
     */
    public static List<List<String>> toTableList(String groupStr) {
        String[] vals;
        List<List<String>> table = new ArrayList<>();
        for (String group : ArrUtils.split("\n", groupStr)) {
            vals = group.split("\t");
//            StringUtils.writeList(" ", vals);
            table.add(BeanUtils.toJavaObject(vals, new TypeReference<List<String>>() {
            }));
        }
        return table;
    }


    /**
     * @param groupStr
     * @return
     */
    public static Map<String, String> toTableMap(String groupStr) {
        String[] vals;
        Map<String, String> resultMap = new HashMap<>();
        for (String group : ArrUtils.split("\n", groupStr)) {
            vals = group.split("\t");
            if (ObjectUtils.notIsEmpty(vals)) {
                resultMap.put(vals[0], vals[1]);
            }
        }
        return resultMap;
    }

    /**
     * 省略中间字符串
     *
     * @param str 字符串
     * @return 省略的字符串
     * @eg omitMiddle(" 112oAt69WMJnMbipmESZeQ88sZSzRWi8t3AiDecdSuXnzkyBrfoh ") ==》 112oAt...yBrfoh
     */
    public static String omitMiddle(int showNum, String str) {
        return String.format("%s...%s", str.substring(0, showNum), str.substring(str.length() - showNum));
    }


    public static String formatKV(String str, Object data) {
        if (ObjectUtils.isEmpty(str) || ObjectUtils.isEmpty(data)) {
            return null;
        }
        JSONObject jsonObject = BeanUtils.toJSONObject(data);
        for (Object o : jsonObject.keySet().toArray()) {
            str = str.replaceAll(String.format("\\$\\{%s}", o), String.valueOf(jsonObject.get(o)));
        }
        return str;
    }

    public static String formatV(String str, Object... datas) {
        if (ObjectUtils.isEmpty(str) || ObjectUtils.isEmpty(datas)) {
            return str;
        }
        for (Object data : datas) {
            str = str.replace(substringx(str, "$", "}"), String.valueOf(data));
        }
        return str;
    }

    public static String formatV(String str, Map<String, Object> datas) {
        if (ObjectUtils.isEmpty(str) || ObjectUtils.isEmpty(datas)) {
            return str;
        }
        String name = "";
//        for (Object data : datas) {
        name = substringx(str, "${", "}");
        str = str.replace(name, String.valueOf(datas.get(substring(str, "${", "}"))));
//        }
        return str;
    }


    //__________________________________字符串——净化start__________________________________

    /**
     * 净化ANSI编码
     *
     * @throws
     * @description
     * @author Mr.Lv lvzhuozhuang@foxmail.com
     * @updateTime 2022/8/17 16:30
     */
    public static String purgeANSI(String code) {
        return code.replaceAll("\\[..;..[m]|\\[.{0,2}[m]|\\(Page \\d+\\)|\u001B\\[[K]|\u001B|\u000F", "");
    }

    /**
     * 净化重复字符串
     *
     * @throws
     * @description
     * @author Mr.Lv lvzhuozhuang@foxmail.com
     * @updateTime 2022/8/17 16:45
     */
    public static String purgeRepeatStr(String regex, String replacement, String code) {
        if (isEmpty(regex) || isEmpty(code))
            return code;
        String searchStr = regex + regex;
        int indexOfIndex = code.indexOf(searchStr);
        String result = indexOfIndex > -1 ? code.replaceAll(searchStr, replacement) : code;
        return code.indexOf(searchStr) > -1 ? purgeRepeatStr(regex, replacement, result) : result;
    }

    //__________________________________字符串——净化end__________________________________


/*    public static void replaceAlls(String str, String replacement, Object[] regex) {
        if (notIsEmpty(regex) && regex.length > 0) {
            for (Object s : regex) {
                str = str.replaceAll((String) s, replacement);
            }
        }
    }*/

    public static String removeIllegalStr(String data, List<String> codes) {
        return toCharStr(removeIllegalChar(toStrChar(data), codes));
    }

    public static String removeIllegalChar(String data, List<String> codes) {
        if (notIsEmpty(data) && notIsEmpty(codes)) {
            String[] split;
            for (String s : codes) {
                split = s.split("@@");
                data = data.replaceAll(toStrChar(split[0]), split.length > 1 ? toStrChar(split[1]) : "@@");
            }
            return data;
        }
        return data;
    }

    public static String toCharStr(String code) {
        if (notIsEmpty(code)) {
            String[] split = code.split("@@");
            StringBuffer re = new StringBuffer();
            for (String s : split) {
                if (StringUtils.notIsEmpty(s)) {
                    re.append((char) ((int) Integer.valueOf(s)));
                }
            }
            return re.toString();
        }
        return code;
    }

    public static String toStrChar(String code) {
        if (notIsEmpty(code)) {
            char[] chars = code.toCharArray();
            StringBuffer re = new StringBuffer("@@");
            for (char aChar : chars) {
                re.append((int) aChar + "@@");
            }
            return re.toString();
        }
        return code;
    }


    public static void showChar(String code) {
        if (notIsEmpty(code)) {
            char[] chars = code.toCharArray();
            StringBuffer re = new StringBuffer();
            int index = 8;
            for (char aChar : chars) {
//                log.info("showChar：{}\t{}", aChar, (int) aChar);
                index--;
                re.append(aChar + "-" + (int) aChar + "\t");
                if (index < 1) {
                    index = 8;
                    re.append("\n");
                }
            }
            log.info("showChar：\n{}", re.toString());
        }
    }

//replaceAll("\\p{C}", "") 替换字符串中不可见字符


    public static String substr(String str, String target, String replacement) {
        int i = str.indexOf(target);
        if (i > -1) {
            return String.format("%s%s%s", str.substring(0, i), replacement, str.substring(i + target.length()));
        }
        return str;
    }

    public static String pegNum(String str, String reStr) {
        int reStrIndex = 0;
        String l = System.currentTimeMillis() + "";
//        String l = "`";
        int index = 0;
        while (index > -1) {
            reStrIndex = reStrIndex + 1;
            System.out.println(l + reStrIndex);
            index = str.indexOf(reStr);
            str = substr(str, reStr, l + reStrIndex);

        }
        return str.replace(l, reStr);
    }


    /**
     * 删除两侧 内容
     * @param str
     * @param replacement
     * @return
     */
    public static String deletesBothSides(String str, String replacement) {
        int index = str.indexOf(replacement);

        if (index == 0) {
            str = str.substring(index + replacement.length());
        }
        index = str.lastIndexOf(replacement);
        if (index == str.length() - replacement.length()) {
            str = str.substring(0, index);
        }

        return str;
    }


    public static String lastSubstring(String str, String beginStr, String endStr) {
        Integer beginIndex;
        String lastStr = null;
        if (beginStr != null) {
            beginIndex = str.lastIndexOf(beginStr);
            if (beginIndex > -1) {
                lastStr = str.substring(beginIndex + beginStr.length(), str.length());
//                System.out.println(lastStr);
            }
        } else {
            lastStr = str;
        }

        if (null != lastStr && null != endStr) {
            int lastIndex = lastStr.lastIndexOf(endStr);
            if (StringUtils.notIsEmpty(lastIndex) && lastIndex > -1) {
                return lastStr.substring(0, lastIndex).replaceAll(" ", "");
            } else {
                return null;
            }
        }

        return lastStr;
    }

}
