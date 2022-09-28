package icu.sunnyc.navicat.example.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/28 10:35
 */
@Data
@ToString
public class DataSourceVO {

    /**
     * 数据源ID
     */
    private String dataSourceId;

    /**
     * 数据源名称
     */
    private String dataSourceName;

}
