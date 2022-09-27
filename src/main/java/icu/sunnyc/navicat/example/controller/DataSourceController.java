package icu.sunnyc.navicat.example.controller;

import icu.sunnyc.navicat.example.entity.bo.CommonResult;
import icu.sunnyc.navicat.example.entity.dto.DataSourceDTO;
import icu.sunnyc.navicat.example.utils.DbUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/add")
    @ApiOperation(value = "添加数据源")
    public CommonResult add(@RequestBody DataSourceDTO dataSource) {
        return CommonResult.success();
    }

    @PostMapping("/connect")
    @ApiOperation(value = "通过数据源信息连接数据库")
    @SneakyThrows
    public CommonResult connect(@RequestBody DataSourceDTO dataSource) {
        try (Connection connection = DbUtil.createConnection(dataSource.getHost(), dataSource.getPort(),
                dataSource.getUserName(), dataSource.getPassword())) {
            return connection == null ? CommonResult.failed("数据库连接失败") : CommonResult.success().setMessage("数据库连接成功！");
        }
    }

    @GetMapping("/connect/{dataSourceId}")
    @ApiOperation(value = "通过数据源ID连接数据库")
    @SneakyThrows
    public CommonResult connect(@PathVariable("dataSourceId") Long dataSourceId) {
        return CommonResult.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询所有数据源")
    public CommonResult list() {
        return CommonResult.success();
    }

}
