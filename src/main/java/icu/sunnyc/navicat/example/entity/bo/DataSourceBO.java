package icu.sunnyc.navicat.example.entity.bo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 9:03
 */
@Data
@Builder
@ToString
@EqualsAndHashCode
public class DataSourceBO {

    /**
     * 数据库连接URL
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
