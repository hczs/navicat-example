package icu.sunnyc.navicat.example.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/22 18:38
 */
@Slf4j
public class DbUtil {

    static {
        try {
            // 加载驱动
            String mysqlDriverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(mysqlDriverName);
            log.info("数据库驱动加载完成，驱动类：{}", mysqlDriverName);
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败！", e);
        }
    }

    public static Connection createConnection(String host, Integer port, String userName, String password) {
        String url = StrUtil.format("jdbc:mysql://{}:{}", host, port);
        return createConnection(url, userName, password);
    }

    public static Connection createConnection(String url, String userName, String password) {
        try {
            log.info("正在连接 MySQL 服务端 [url: {} userName: {} password: {}]", url, userName, password);
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            log.error("数据库连接失败！", e);
        }
        return null;
    }

}
