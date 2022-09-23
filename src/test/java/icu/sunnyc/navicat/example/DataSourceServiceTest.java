package icu.sunnyc.navicat.example;

import icu.sunnyc.navicat.example.entity.po.DataSourcePO;
import icu.sunnyc.navicat.example.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 14:44
 */
@Slf4j
@SpringBootTest
class DataSourceServiceTest {

    @Autowired
    private DataSourceService dataSourceService;

    @Test
    void testAddDataSource() {
        DataSourcePO dataSourcePO = new DataSourcePO();
        dataSourcePO.setHost("127.0.0.1");
        dataSourcePO.setPort(3306);
        dataSourcePO.setUsername("root");
        dataSourcePO.setPassword("123456");
        dataSourceService.addDataSource(dataSourcePO);
        Assertions.assertTrue(true, "添加数据源测试");
    }

    @Test
    void testListDataSources() {
        List<DataSourcePO> dataSourceList = dataSourceService.listDataSources();
        dataSourceList.forEach(item -> log.info("datasource: " + item));
        Assertions.assertNotNull(dataSourceList, "添加数据源测试");
    }
}
