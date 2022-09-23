package icu.sunnyc.navicat.example.service;

import icu.sunnyc.navicat.example.entity.po.DataSourcePO;

import java.sql.Connection;
import java.util.List;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 14:23
 */
public interface DataSourceService {

    /**
     * 列出所有数据源信息
     *
     * @return 数据源信息列表
     */
    List<DataSourcePO> listDataSources();

    /**
     * 新增非数据源信息
     *
     * @param dataSource 数据源信息对象
     */
    void addDataSource(DataSourcePO dataSource);

    /**
     * 根据ID获取数据源信息
     *
     * @param dataSourceId 数据源ID
     * @return 数据源信息
     */
    DataSourcePO getDataSourceById(Long dataSourceId);

    /**
     * 根据数据源ID获取连接信息
     *
     * @param dataSourceId 数据源ID
     * @return 数据源连接对象
     */
    Connection connectionByDataSourceId(Long dataSourceId);
}
