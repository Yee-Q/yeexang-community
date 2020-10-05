package top.yeexang.community.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import top.yeexang.community.enums.ResponseCodeEnum;
import top.yeexang.community.exception.CustomizeException;

/**
 * @author yeeq
 * @date 2020/10/2
 */
@Data
@Accessors(chain = true)
public class ResultDTO <T> {

    private Integer code;
    private String message;
    private T data;

    /**
     * 返回一个响应失败结果
     * @return 响应失败结果
     */
    public static ResultDTO<Object> errorOf() {
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResponseCodeEnum.REQUEST_FAILED.getCode());
        resultDTO.setMessage(ResponseCodeEnum.REQUEST_FAILED.getMessage());
        return resultDTO;
    }

    /**
     * 返回一个附带失败响应码和失败信息的响应结果
     * @param emailAlreadyExists 异常枚举
     * @return 附带失败响应码和失败信息的响应结果
     */
    public static ResultDTO<?> errorOf(ResponseCodeEnum emailAlreadyExists) {

        ResultDTO<?> resultDTO = new ResultDTO<>();
        resultDTO.setCode(emailAlreadyExists.getCode());
        resultDTO.setMessage(emailAlreadyExists.getMessage());
        return resultDTO;
    }

    /**
     * 返回一个响应成功结果
     * @return 响应成功结果
     */
    public static ResultDTO<?> successOf() {
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResponseCodeEnum.REQUEST_SUCCESS.getCode());
        resultDTO.setMessage(ResponseCodeEnum.REQUEST_SUCCESS.getMessage());
        return resultDTO;
    }

    /**
     * 返回一个附带数据的响应成功结果
     * @param data 附带数据
     * @param <T> 数据类型
     * @return 附带数据的响应成功结果
     */
    public static <T> ResultDTO<T> successOf(T data) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(ResponseCodeEnum.REQUEST_SUCCESS.getCode());
        resultDTO.setMessage(ResponseCodeEnum.REQUEST_SUCCESS.getMessage());
        resultDTO.setData(data);
        return resultDTO;
    }
}