package icu.sunnyc.navicat.example.controller;

import icu.sunnyc.navicat.example.constant.CommandConstant;
import icu.sunnyc.navicat.example.entity.bo.CommonResult;
import icu.sunnyc.navicat.example.entity.vo.ProcessVO;
import icu.sunnyc.navicat.example.service.DataSourceService;
import icu.sunnyc.navicat.example.utils.DbPoolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 16:40
 */
@Slf4j
@RestController
@Api(tags = "线程管理")
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private DataSourceService dataSourceService;

    @GetMapping("/list/{dataSourceId}")
    @ApiOperation(value = "查询指定数据源下所有线程信息")
    @SneakyThrows
    public CommonResult list(@PathVariable("dataSourceId") Long dataSourceId) {
        try (Connection connection = dataSourceService.connectionByDataSourceId(dataSourceId)) {
            List<Map<String, Object>> maps = DbPoolUtil.executeQueryList(connection, CommandConstant.QUERY_PROCESS_LIST);
            ArrayList<ProcessVO> processInfoList = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                ProcessVO processInfo = ProcessVO.builder()
                        .id((Long) map.get("Id"))
                        .user(String.valueOf(map.get("User")))
                        .host(String.valueOf(map.get("Host")))
                        .db(String.valueOf(map.get("db")))
                        .command((String.valueOf(map.get("Command"))))
                        .time(String.valueOf(map.get("Time")))
                        .state(String.valueOf(map.get("State")))
                        .info(String.valueOf(map.get("info")))
                        .build();
                processInfoList.add(processInfo);
            }
            return CommonResult.success(processInfoList);
        }
    }

    @GetMapping("/kill/{dataSourceId}/{processId}")
    @ApiOperation(value = "终止指定线程")
    @SneakyThrows
    public CommonResult kill(@PathVariable("dataSourceId") Long dataSourceId,
                             @PathVariable("processId") Long processId) {
        try (Connection connection = dataSourceService.connectionByDataSourceId(dataSourceId)) {
            boolean success = DbPoolUtil.execute(connection, CommandConstant.KILL_PROCESS + processId);
            return success ? CommonResult.success().setMessage("线程终止成功") : CommonResult.failed("终止线程失败");
        }
    }
}
