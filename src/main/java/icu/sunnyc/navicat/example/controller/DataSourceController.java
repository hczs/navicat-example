package icu.sunnyc.navicat.example.controller;

import cn.hutool.core.bean.BeanUtil;
import icu.sunnyc.navicat.example.entity.bo.CommonResult;
import icu.sunnyc.navicat.example.entity.dto.DataSourceDTO;
import icu.sunnyc.navicat.example.entity.po.DataSourcePO;
import icu.sunnyc.navicat.example.service.DataSourceService;
import icu.sunnyc.navicat.example.utils.DbPoolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 14:28
 */
@Slf4j
@Api(tags = "数据源管理")
@RestController
@RequestMapping("/datasource")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @PostMapping("/add")
    @ApiOperation(value = "添加数据源")
    public CommonResult add(@RequestBody DataSourceDTO dataSource) {
        DataSourcePO datasourceEntity = new DataSourcePO();
        BeanUtil.copyProperties(dataSource, datasourceEntity);
        dataSourceService.addDataSource(datasourceEntity);
        return CommonResult.success();
    }

    @PostMapping("/connect")
    @ApiOperation(value = "通过数据源信息连接数据库")
    @SneakyThrows
    public CommonResult connect(@RequestBody DataSourceDTO dataSource) {
        try (Connection connection = DbPoolUtil.getConnection(dataSource.getHost(), dataSource.getPort(),
                dataSource.getUsername(), dataSource.getPassword())) {
            return connection == null ? CommonResult.failed("数据库连接失败") : CommonResult.success().setMessage("数据库连接成功！");
        }
    }

    @GetMapping("/connect/{dataSourceId}")
    @ApiOperation(value = "通过数据源ID连接数据库")
    @SneakyThrows
    public CommonResult connect(@PathVariable("dataSourceId") Long dataSourceId) {
        try(Connection connection = dataSourceService.connectionByDataSourceId(dataSourceId)) {
            return connection == null ? CommonResult.failed("数据库连接失败") : CommonResult.success().setMessage("数据库连接成功！");
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询所有数据源")
    public CommonResult list() {
        return CommonResult.success(dataSourceService.listDataSources());
    }

}
