package icu.sunnyc.navicat.example.utils;

import cn.hutool.core.util.StrUtil;
import icu.sunnyc.navicat.example.exception.NavicatException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hc
 * @date Created in 2022/9/27 22:25
 * @modified
 */
@Slf4j
@Component
public class DbUtil {

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
     * @param userName 用户名
     * @param password 密码
     * @return 数据库连接
     */
    public static Connection createConnection(String host, Integer port, String userName, String password) {
        String url = StrUtil.format("jdbc:mysql://{}:{}", host, port);
        return createConnection(url, userName, password);
    }

    /**
     * 获取数据库连接
     *
     * @param url 数据库连接url
     * @param userName 用户名
     * @param password 密码
     * @return 数据库连接对象
     */
    public static Connection createConnection(String url, String userName, String password) {
        try {
            log.info("正在连接 MySQL 服务端 [url: {} userName: {} password: {}]", url, userName, password);
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            log.error("数据库连接失败", e);
            throw new NavicatException(StrUtil.format("数据库 {} 连接异常", url));
        }
    }


    /**
     * 执行查询语句，返回多条数据
     *
     * @param connection 数据源连接对象
     * @param sql 查询语句 sql
     * @return List<Map<String,Object>>
     */
    @SneakyThrows
    public static List<Map<String,Object>> executeQueryList(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            return getResultListMap(statement.executeQuery(sql));
        }
    }

    /**
     * 执行查询语句，针对查询单条数据的情况
     *
     * @param connection 数据库连接对象
     * @param sql 查询 sql 语句
     * @return 对象 map
     */
    @SneakyThrows
    public static Map<String, String> executeQueryOne(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            return getResultMap(statement.executeQuery(sql));
        }
    }

    /**
     * 执行sql
     *
     * @param connection 数据库连接对象
     * @param sql sql语句
     * @return 是否成功执行
     */
    @SneakyThrows
    public static boolean execute(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
            return true;
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
