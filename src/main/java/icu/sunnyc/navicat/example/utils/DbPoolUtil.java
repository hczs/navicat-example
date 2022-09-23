package icu.sunnyc.navicat.example.utils;

import cn.hutool.core.util.StrUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import icu.sunnyc.navicat.example.entity.bo.DataSourceBO;
import icu.sunnyc.navicat.example.exception.NavicatException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hc
 * @date Created in 2022/9/23 23:04
 * @modified
 */
@Slf4j
public class DbPoolUtil {

    /**
     * 缓存数据源对象和数据库连接池
     */
    private static final Map<DataSourceBO, HikariDataSource> CONNECTION_CACHE = new ConcurrentHashMap<>();

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
     * 获取数据库连接对象
     *
     * @param url jdbc连接url
     * @param username 用户名
     * @param password 密码
     * @return 数据源连接对象
     */
    @SneakyThrows
    public static Connection getConnection(String url, String username, String password) {
        DataSourceBO datasource = DataSourceBO.builder().url(url).username(username).password(password).build();
        HikariDataSource hikariDataSource = CONNECTION_CACHE.get(datasource);
        if (hikariDataSource != null && hikariDataSource.isRunning()) {
            log.debug("使用缓存中的数据库连接池；数据库对象：{} 连接池：{}", datasource, hikariDataSource);
            return hikariDataSource.getConnection();
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(url);
        HikariDataSource dataSourcePool = new HikariDataSource(hikariConfig);
        CONNECTION_CACHE.put(datasource, dataSourcePool);
        log.debug("已将数据库连接池放入缓存；数据库对象：{} 连接池：{}", datasource, hikariDataSource);
        return dataSourcePool.getConnection();
    }

    /**
     * 执行查询语句，返回多条数据
     *
     * @param connection 数据源连接对象
     * @param sql 查询语句 sql
     * @return List<Map<String,Object>>
     */
    public static List<Map<String,Object>> executeQueryList(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            return getResultListMap(statement.executeQuery(sql));
        } catch (SQLException e) {
            log.error("SQL 语句执行出错", e);
            throw new NavicatException("SQL 语句执行出错");
        }
    }

    /**
     * 执行查询语句，针对查询单条数据的情况
     *
     * @param connection 数据库连接对象
     * @param sql 查询 sql 语句
     * @return 对象 map
     */
    public static Map<String, String> executeQueryOne(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            return getResultMap(statement.executeQuery(sql));
        } catch (SQLException e) {
            log.error("SQL 语句执行出错", e);
            throw new NavicatException("SQL 语句执行出错");
        }
    }

    /**
     * 执行sql
     *
     * @param connection 数据库连接对象
     * @param sql sql语句
     * @return 是否成功执行
     */
    public static boolean execute(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            log.error("SQL 语句执行出错", e);
            throw new NavicatException("SQL 语句执行出错");
        }
    }

    /**
     * 将查询结果 resultSet 转换为 List<Map<String,Object>>
     *
     * @param rs ResultSet
     * @return List<Map<String,Object>>
     */
    @SneakyThrows
    private static List<Map<String,Object>> getResultListMap(ResultSet rs) {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String,Object> rowData = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * resultSet 转 map
     *
     * @param rs ResultSet
     * @return map
     */
    @SneakyThrows
    private static Map<String, String> getResultMap(ResultSet rs) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String key = resultSetMetaData.getColumnLabel(i);
            String value = rs.getString(i);
            resultMap.put(key, value);
        }
        return resultMap;
    }

}
