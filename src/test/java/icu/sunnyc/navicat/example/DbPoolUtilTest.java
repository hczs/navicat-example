package icu.sunnyc.navicat.example;

import icu.sunnyc.navicat.example.constant.CommandConstant;
import icu.sunnyc.navicat.example.utils.DbPoolUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author hc
 * @date Created in 2022/9/23 23:15
 * @modified
 */
@SpringBootTest
public class DbPoolUtilTest {

    @Test
    void testGetConnection() throws SQLException {
        Connection connection = DbPoolUtil.getConnection("127.0.0.1", 3306, "root", "123456");
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(CommandConstant.QUERY_DATABASES);
        while (res.next()) {
            System.out.println(res.getString("Database"));
        }
        Assertions.assertNotNull(connection, "获取数据库连接测试");
    }

    @Test
    void testExecuteQueryList() {
        Connection connection = DbPoolUtil.getConnection("127.0.0.1", 3306, "root", "123456");
        List<Map<String, Object>> maps = DbPoolUtil.executeQueryList(connection, CommandConstant.QUERY_PROCESS_LIST);
        System.out.println(maps);
        Assertions.assertNotNull(maps, "测试查询返回多条数据");
    }

    @Test
    void testExecute() {
        Connection connection = DbPoolUtil.getConnection("127.0.0.1", 3306, "root", "123456");
        boolean result = DbPoolUtil.execute(connection, CommandConstant.KILL_PROCESS + "123");
        Assertions.assertTrue(result, "测试执行sql语句");
    }

}
