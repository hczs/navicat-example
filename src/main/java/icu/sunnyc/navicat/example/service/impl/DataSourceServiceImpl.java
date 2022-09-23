package icu.sunnyc.navicat.example.service.impl;

import icu.sunnyc.navicat.example.entity.po.DataSourcePO;
import icu.sunnyc.navicat.example.repository.DataSourceRepository;
import icu.sunnyc.navicat.example.service.DataSourceService;
import icu.sunnyc.navicat.example.utils.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 14:24
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Override
    public List<DataSourcePO> listDataSources() {
        return dataSourceRepository.findAll();
    }

    @Override
    public void addDataSource(DataSourcePO dataSource) {
        dataSourceRepository.save(dataSource);
    }

    @Override
    public DataSourcePO getDataSourceById(Long dataSourceId) {
        return dataSourceRepository.getReferenceById(dataSourceId);
    }

    @Override
    public Connection connectionByDataSourceId(Long dataSourceId) {
        DataSourcePO dataSource = dataSourceRepository.getReferenceById(dataSourceId);
        return DbUtil.getConnection(dataSource.getHost(), dataSource.getPort(),
                dataSource.getUsername(), dataSource.getPassword());
    }
}
