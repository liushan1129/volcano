package com.mdd.common.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liushan
 * @data 2023/4/1 17:10
 */
@Component
public class DozerUtils {
    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    public final static DozerBeanMapper dozer = new DozerBeanMapper();


    /**
     * 构造新的destinationClass实例对象，通过source对象中的字段内容
     * 映射到destinationClass实例对象中，并返回新的destinationClass实例对象。
     *
     * @param source           源数据对象
     * @param destinationClass 要构造新的实例对象Class
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 将对象source的所有属性值拷贝到对象destination中.
     *
     * @param source            对象source
     * @param destinationObject 对象destination
     */
    public static void map(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    /**
     * List数据源映射
     *
     * @param sourceList
     * @param destinationClass
     * @param <T>
     * @return List<T>
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List destinationList = new ArrayList<>();
        for (Iterator iterator = sourceList.iterator(); iterator.hasNext(); ) {
            Object sourceObject = iterator.next();
            Object destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }
}