package icu.sunnyc.navicat.example;

import icu.sunnyc.navicat.example.utils.DbUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/22 19:02
 */
@SpringBootTest
public class DbUtilTest {

    @Test
    void testCreateConnection() throws SQLException {
        Connection connection = DbUtil.createConnection("127.0.0.1", 3306, "root", "123456");
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("show databases ");
        while (res.next()) {
            System.out.println(res.getString("Database"));
        }
        Assertions.assertNotNull(connection, "获取数据库连接测试");
    }
}
