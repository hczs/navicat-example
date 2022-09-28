package icu.sunnyc.navicat.example.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import java.time.Duration;

/**
 * @author hc
 * @date Created in 2022/9/27 22:47
 * @modified
 */
public class CaffeineTest {

    public static void main(String[] args) throws InterruptedException {
        Cache<Integer, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(Duration.ofSeconds(3))
                .removalListener((RemovalListener<Integer, String>) (key, value, removalCause) ->
                        System.out.printf("Key %s was removed (%s)%n", key, removalCause))
                .evictionListener((Integer key, String graph, RemovalCause cause) ->
                        System.out.printf("Key %s was evicted (%s)%n", key, cause))
                .build();
        cache.put(1, "jack");

        Thread.sleep(2500);
        System.out.println(cache.getIfPresent(1));

        Thread.sleep(1000);
        System.out.println(cache.getIfPresent(1));

        Thread.sleep(2500);
        System.out.println(cache.getIfPresent(1));

        Thread.sleep(2800);
        System.out.println(cache.getIfPresent(1));

        Thread.sleep(2900);
        System.out.println(cache.getIfPresent(1));

        Thread.sleep(3000);
        System.out.println(cache.getIfPresent(1));

        Thread.sleep(3100);
        System.out.println(cache.getIfPresent(1));
    }
}
