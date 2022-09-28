package icu.sunnyc.navicat.example.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import icu.sunnyc.navicat.example.entity.bo.DataSourceBO;
import icu.sunnyc.navicat.example.listener.CacheRemoveListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/28 9:48
 */
@Slf4j
public class DataSourceCache {

    /**
     * 数据源缓存对象
     * key: userId
     * value: Set<DataSourceBO>
     */
    private static Cache<String, Set<DataSourceBO>> cache;

    public static void initCache(long maximumSize, long expireAfterAccess) {
        cache = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(Duration.ofMinutes(expireAfterAccess))
                .removalListener(new CacheRemoveListener())
                .build();
        log.info("数据源缓存对象创建完毕，最大缓存数：{} 个 访问后缓存过期时间：{} 分钟", maximumSize, expireAfterAccess);
    }

    /**
     * 添加数据源
     *
     * @param userId 用户ID
     * @param dataSourceInfo 数据源信息
     * @return 添加成功返回true，添加失败返回false
     */
    public static boolean addDataSource(String userId, DataSourceBO dataSourceInfo) {
        Set<DataSourceBO> dataSourceSet = cache.get(userId, k -> new HashSet<>());
        assert dataSourceSet != null;
        return dataSourceSet.add(dataSourceInfo);
    }

    /**
     * 获取指定用户下的数据源列表
     *
     * @param userId 用户ID
     * @return 数据源集合
     */
    public static Set<DataSourceBO> getDataSourceSet(String userId) {
        return cache.get(userId, k -> new HashSet<>());
    }

    /**
     * 根据指定用户下的，指定数据源信息
     *
     * @param userId 用户ID
     * @param dataSourceId 数据源ID
     * @return 数据源信息对象，如果未找到则返回null
     */
    public static DataSourceBO getDataSource(String userId, String dataSourceId) {
        Set<DataSourceBO> dataSourceSet = cache.get(userId, k -> new HashSet<>());
        assert dataSourceSet != null;
        Optional<DataSourceBO> dataSourceOptional = dataSourceSet.stream()
                .filter(item -> item.getDataSourceId().equals(dataSourceId))
                .findFirst();
        return dataSourceOptional.orElse(null);
    }

    /**
     * 获取指定用户，指定数据源的连接对象
     *
     * @param userId 用户ID
     * @param dataSourceId 数据源ID
     * @param timeout 验证连接超时时间
     * @return Connection
     */
    @SneakyThrows
    public static Connection getConnection(String userId, String dataSourceId, int timeout) {
        DataSourceBO dataSource = getDataSource(userId, dataSourceId);
        if (dataSource == null) {
            return null;
        }
        Connection connection = dataSource.getConnection();
        if (connection == null || !connection.isValid(timeout)) {
            Connection newConnection = DbUtil.createConnection(dataSource.getHost(), dataSource.getPort(), dataSource.getUserName(),
                    dataSource.getPassword());
            dataSource.setConnection(newConnection);
            return newConnection;
        }
        return connection;
    }

}
