package icu.sunnyc.navicat.example.entity.bo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Connection;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 9:03
 */
@Data
@Builder
@ToString
@EqualsAndHashCode(exclude = { "dataSourceId", "dataSourceName", "connection" })
public class DataSourceBO {

    /**
     * 数据源ID
     */
    private String dataSourceId;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 数据库连接URL
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据源连接对象
     */
    private Connection connection;

}
