package com.sangeng.sangengspringsecurity.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 集合相关工具类
 */
public class CollectionTools {

    /**
     * 去除Lsit<String>集合中的null和空符串
     * @param list
     * @return
     */
    public static List<String> removeEmptyString(List<String> list){
        //去掉list中的null
        List<String> newList = list.stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
        //去掉list空字符串
        List<String> filtered=newList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        return filtered;
    }

}
