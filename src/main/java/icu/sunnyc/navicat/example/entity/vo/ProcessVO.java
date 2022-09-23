package icu.sunnyc.navicat.example.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 16:40
 */
@Data
@Builder
public class ProcessVO {

    private Long id;

    private String user;

    private String host;

    private String db;

    private String command;

    private String time;

    private String state;

    private String info;
}
