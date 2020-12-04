package com.ian.homework.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DynamicDataSourceContextHolder {

    /**
     * 用于存放数据源的本地线程
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        /**
         * 讲master数据源的key作为默认的数据源的key
         * @return
         */
        @Override
        protected String initialValue() {
            // return super.initialValue();//
            return "master";
        }
    };

    /**
     * 数据源的key集合，用于切换时判断数据源是否存在
     */
    private static List<Object> dataSourceKey = new ArrayList<Object>();

    /**
     * @return the value of contextHolder
     */
    public static ThreadLocal<String> getContextHolder() {
        return contextHolder;
    }

    /**
     * @return the value of dataSourceKey
     */
    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    /**
     * Sets the dataSourceKey
     *
     * @param dataSourceKey dataSourceKey
     */
    public static void setDataSourceKey(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }

    /**
     * 重置数据源
     */
    public static void cleanDataSource() {
        contextHolder.remove();
    }

    /**
     * 判断是否包含数据源
     *
     * @param key 数据源的key
     * @return yes no
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKey.contains(key);
    }

    /**
     * 添加数据源key,支持加多个，所以需要用collection
     *
     * @param keys 数据源key
     * @return
     */
    public static boolean addDataSourceKey(Collection<? extends Object> keys) {
        return dataSourceKey.addAll(keys);
    }
}