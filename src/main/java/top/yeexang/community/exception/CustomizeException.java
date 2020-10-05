package top.yeexang.community.exception;

import top.yeexang.community.enums.ResponseCodeEnum;

/**
 * @author yeeq
 * @date 2020/10/2
 */
public class CustomizeException extends RuntimeException {

    private final String message;
    private final Integer code;

    public CustomizeException(ResponseCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
