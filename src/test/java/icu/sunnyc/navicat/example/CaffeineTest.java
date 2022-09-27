package icu.sunnyc.navicat.example;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;

/**
 * @author hc
 * @date Created in 2022/9/27 22:47
 * @modified
 */
public class CaffeineTest {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(Duration.ofSeconds(3))
                .build();
        cache.put("a", "jack");

        Thread.sleep(2500);
        System.out.println(cache.getIfPresent("a"));

        Thread.sleep(1000);
        System.out.println(cache.getIfPresent("a"));

        Thread.sleep(2500);
        System.out.println(cache.getIfPresent("a"));

        Thread.sleep(2800);
        System.out.println(cache.getIfPresent("a"));

        Thread.sleep(3000);
        System.out.println(cache.getIfPresent("a"));
    }
}
