package icu.sunnyc.navicat.example.entity.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 14:08
 */
@Data
@Entity
@ToString
@Table(name = "t_datasource")
public class DataSourcePO {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
