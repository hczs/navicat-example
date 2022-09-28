package icu.sunnyc.navicat.example.listener;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import icu.sunnyc.navicat.example.entity.bo.DataSourceBO;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/28 10:08
 */
@Slf4j
public class CacheRemoveListener implements RemovalListener<String, Set<DataSourceBO>> {

    @Override
    public void onRemoval(@Nullable String userId, @Nullable Set<DataSourceBO> dataSourceSet, @NonNull RemovalCause removalCause) {
        log.info("用户长时间未操作，缓存到期，移除缓存：key -> {} value -> {}; 移除原因: {}", userId, dataSourceSet, removalCause);
        if (dataSourceSet == null) {
            return;
        }
        dataSourceSet.forEach(item -> {
            if (item != null && item.getConnection() != null) {
                try {
                    item.getConnection().close();
                } catch (SQLException e) {
                    log.error("缓存到期，关闭连接异常，数据源信息：{}", item, e);
                }
            }
        });
    }
}
