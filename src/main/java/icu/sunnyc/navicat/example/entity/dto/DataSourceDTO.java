package icu.sunnyc.navicat.example.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 15:54
 */
@Data
@ToString
public class DataSourceDTO {

    /**
     * 主机地址
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
