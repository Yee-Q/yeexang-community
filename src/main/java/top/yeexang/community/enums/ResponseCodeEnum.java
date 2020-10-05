package top.yeexang.community.enums;

/**
 * @author yeeq
 * @date 2020/10/2
 */
public enum ResponseCodeEnum {

    /**
     * 成功码和响应信息
     */
    REQUEST_SUCCESS(2000, "请求成功"),

    /**
     * 错误码和详细信息
     */
    REQUEST_FAILED(4000, "请求失败，请稍后再试"),
    EMAIL_ALREADY_EXISTS(4001, "该邮箱已被使用过，换个邮箱试试吧"),
    NAME_ALREADY_EXISTS(4002, "该昵称已被使用，换个昵称试试吧"),
    EMAIL_ERROR_OR_NOT_EXISTS(4003, "邮箱错误或不存在"),
    PASS_ERROR(4003, "密码错误"),
    NO_LOGIN(4004, "请先登录"),
    ;

    private final Integer code;
    private final String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}