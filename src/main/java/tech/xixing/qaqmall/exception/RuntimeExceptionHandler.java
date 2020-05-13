package tech.xixing.qaqmall.exception;

import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.xixing.qaqmall.enums.ResponseEnum;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/4/30 9:35
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.FORBIDDEN)//设置http状态码
    public ResponseVO handle(RuntimeException e){
        return ResponseVO.error(ResponseEnum.ERROR,e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVO handle(UserLoginException e){
        return ResponseVO.error(ResponseEnum.NEED_LOGIN);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseVO handles(HttpRequestMethodNotSupportedException e){
        return ResponseVO.error(ResponseEnum.NOT_SUPPORT_METHOD);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO notValidException(MethodArgumentNotValidException  e){
        BindingResult bindingResult = e.getBindingResult();

        return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
    }



}
