package tech.xixing.qaqmall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;
import tech.xixing.qaqmall.enums.ResponseEnum;

import java.util.Objects;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/29 15:58
 */
@Data
//@JsonSerialize(include = )
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {
    private Integer status;

    private String msg;

    private T data;

    public ResponseVO(Integer status,String msg){
        this.status=status;
        this.msg=msg;
    }
    public ResponseVO(Integer status,T data){
        this.status=status;
        this.data=data;
    }

    public static <T> ResponseVO<T> success(String msg){
        return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(),msg);

    }
    public static <T> ResponseVO<T> successByData(T data){
        return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(),data);

    }
    public static <T> ResponseVO<T> success(){
        return new ResponseVO<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMsg());

    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum){
        return new ResponseVO<>(responseEnum.getCode(),responseEnum.getMsg());

    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVO<>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+bindingResult.getFieldError().getDefaultMessage());

    }
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, String msg) {
        return new ResponseVO<>(responseEnum.getCode(),msg);
    }


}
