package icu.sunnyc.navicat.example.service.impl;

import icu.sunnyc.navicat.example.entity.po.DataSourcePO;
import icu.sunnyc.navicat.example.repository.DataSourceRepository;
import icu.sunnyc.navicat.example.service.DataSourceService;
import icu.sunnyc.navicat.example.utils.DbPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

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
        Optional<DataSourcePO> dataSourceOpt = dataSourceRepository.findById(dataSourceId);
        return dataSourceOpt.orElse(null);
    }

    @Override
    public Connection connectionByDataSourceId(Long dataSourceId) {
        DataSourcePO dataSource = getDataSourceById(dataSourceId);
        if (dataSource == null) {
            return null;
        }
        return DbPoolUtil.getConnection(dataSource.getHost(), dataSource.getPort(),
                dataSource.getUsername(), dataSource.getPassword());

    }
}
