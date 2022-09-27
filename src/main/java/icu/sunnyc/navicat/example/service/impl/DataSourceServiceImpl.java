package icu.sunnyc.navicat.example.service.impl;

import icu.sunnyc.navicat.example.service.DataSourceService;
import org.springframework.stereotype.Service;

import java.sql.Connection;

/**
 * @author hc
 * @date Created in 2022/9/27 20:49
 * @modified
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Override
    public Connection connectionByDataSourceId(String dataSourceId) {
        return null;
    }
}
