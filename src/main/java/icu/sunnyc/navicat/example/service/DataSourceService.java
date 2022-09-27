package icu.sunnyc.navicat.example.service;

import java.sql.Connection;

/**
 * @author ：hc
 * @date ：Created in 2022/9/27 20:49
 * @modified ：
 */
public interface DataSourceService {

    /**
     * 获取指定数据源ID的连接对象
     *
     * @param dataSourceId 数据源ID
     * @return 数据源连接对象
     */
    Connection connectionByDataSourceId(String dataSourceId);
}
