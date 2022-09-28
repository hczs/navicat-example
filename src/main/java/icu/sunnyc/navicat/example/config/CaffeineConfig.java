package icu.sunnyc.navicat.example.config;

import icu.sunnyc.navicat.example.utils.DataSourceCache;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/28 10:48
 */
@Data
@Component
@ConfigurationProperties(prefix = "caffeine")
public class CaffeineConfig {

    /**
     * 最大缓存数（可以支持多少用户连接）
     */
    private Long maximumSize;

    /**
     * 每次访问后数据源信息及连接过期时间（分钟）
     */
    private Long expireAfterAccess;

    /**
     * 数据源连接超时时间（秒）
     */
    private int connectTimeout;

    @PostConstruct
    private void initCache() {
        DataSourceCache.initCache(maximumSize, expireAfterAccess);
    }
}
