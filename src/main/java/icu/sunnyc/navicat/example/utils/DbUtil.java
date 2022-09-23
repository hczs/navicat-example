package icu.sunnyc.navicat.example.utils;

import cn.hutool.core.util.StrUtil;
import icu.sunnyc.navicat.example.entity.bo.DataSourceBO;
import icu.sunnyc.navicat.example.exception.NavicatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/22 18:38
 */
@Slf4j
@Component
public class DbUtil {

    /**
     * 缓存数据源对象和数据库连接
     */
    private static final Map<DataSourceBO, List<Connection>> CONNECTION_CACHE = new ConcurrentHashMap<>();

    /**
     * 验证数据库连接超时时间
     */
    private static final int VALID_TIMEOUT = 5;

    static {
        try {
            // 加载驱动
            String mysqlDriverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(mysqlDriverName);
            log.info("数据库驱动加载完成，驱动类：{}", mysqlDriverName);
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败", e);
            throw new NavicatException("数据库驱动加载失败");
        }
    }

    /**
     * 获取数据库连接
     *
     * @param host 主机名
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return 数据库连接
     */
    public static Connection getConnection(String host, Integer port, String username, String password) {
        String url = StrUtil.format("jdbc:mysql://{}:{}", host, port);
        return getConnection(url, username, password);
    }

    /**
     * 获取数据库连接
     *
     * @param url 数据库连接url
     * @param username 用户名
     * @param password 密码
     * @return 数据库连接对象
     */
    public static Connection getConnection(String url, String username, String password) {
        try {
            // 首先查看是否可以命中缓存
            DataSourceBO datasource = DataSourceBO.builder().url(url).username(username).password(password).build();
            List<Connection> connectionList = CONNECTION_CACHE.get(datasource);
            // 验证连接是否有效，有效则返回，无效则重新建立一个连接
            if ( connectionList != null && !connectionList.isEmpty() ) {
                Connection connection = connectionList.get(0);
                if (connection.isValid(VALID_TIMEOUT)) {
                    log.info("使用缓存中的 MySQL 连接对象：{}", connection);
                    return connection;
                } else {
                    // 清理掉这个无效连接
                    connectionList.remove(0);
                }
            }
            log.info("正在连接 MySQL 服务端 [url: {} userName: {} password: {}]", url, username, password);
            // 缓存连接
            Connection connection = DriverManager.getConnection(url, username, password);
            if ( connectionList == null ) {
                connectionList = new CopyOnWriteArrayList<>();
            }
            connectionList.add(connection);
            CONNECTION_CACHE.put(datasource, connectionList);
            return connection;
        } catch (SQLException e) {
            log.error("数据库连接失败", e);
            throw new NavicatException(StrUtil.format("数据库 {} 连接异常", url));
        }
    }

    /**
     * 执行查询语句
     *
     * @param connection 数据源连接对象
     * @param sql 查询语句sql
     * @return 结果集
     */
    public static ResultSet executeQuery(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            log.error("SQL 语句执行出错", e);
        }
        return null;
    }

    /**
     * 一小时进行一次验证清理缓存中的无效连接
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void validateAndCleanConnection() {
        log.info("正在清理缓存中的无效连接");
        Collection<List<Connection>> values = CONNECTION_CACHE.values();
        for (List<Connection> connections : values) {
            connections.removeIf(item -> {
                try {
                    return !item.isValid(VALID_TIMEOUT);
                } catch (SQLException e) {
                    log.error("验证缓存中的连接异常", e);
                    // 出异常连接也要删掉
                    return true;
                }
            });
        }
        log.info("缓存中的无效连接清理完毕");
    }
}
