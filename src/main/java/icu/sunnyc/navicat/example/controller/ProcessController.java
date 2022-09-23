package icu.sunnyc.navicat.example.controller;

import icu.sunnyc.navicat.example.constant.CommandConstant;
import icu.sunnyc.navicat.example.entity.bo.CommonResult;
import icu.sunnyc.navicat.example.service.DataSourceService;
import icu.sunnyc.navicat.example.utils.DbUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 16:40
 */
@RestController
@Api(tags = "线程管理")
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping("/list/{dataSourceId}")
    @ApiOperation(value = "查询指定数据源下所有线程信息")
    public CommonResult list(@PathVariable("dataSourceId") Long dataSourceId) {
        Connection connection = dataSourceService.connectionByDataSourceId(dataSourceId);
        ResultSet resultSet = DbUtil.executeQuery(connection, CommandConstant.QUERY_PROCESS_LIST);
        return null;
    }
}
