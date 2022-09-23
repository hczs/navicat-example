package icu.sunnyc.navicat.example.exception;

/**
 * 项目异常信息类
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 9:08
 */
public class NavicatException extends RuntimeException{

    public NavicatException() {}

    public NavicatException(String message) {
        super(message);
    }

}
