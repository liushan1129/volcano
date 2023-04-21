package com.mdd.common.util;

import com.mdd.common.core.basics.CodeEnum;

/**
 * @author liushan
 * @data 2023/4/17 13:19
 */
public class EnumUtils {
     public static <T extends CodeEnum> String getByCode(Integer code, Class<T> t){
            for(T item: t.getEnumConstants()){
                if(item.getCode() == code){
                    return item.getMsg();
                }
            }
            return "";
        }
    }
