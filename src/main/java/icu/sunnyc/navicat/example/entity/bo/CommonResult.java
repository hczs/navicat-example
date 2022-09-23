package icu.sunnyc.navicat.example.entity.bo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author houcheng
 * @version V1.0
 * @date 2022/9/23 15:17
 */
@Data
@Builder
@Accessors(chain = true)
public class CommonResult {

    /**
     * 状态码
     * 正常响应 200
     * 错误响应 -1
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public static CommonResult success() {
        return CommonResult.builder().code(200).build();
    }

    public static CommonResult success(Object data) {
        return CommonResult.builder().code(200).data(data).build();
    }

    public static CommonResult failed(String message) {
        return CommonResult.builder().code(-1).message(message).build();
    }
}
