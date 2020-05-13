package tech.xixing.qaqmall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.xixing.qaqmall.consts.MallConst;
import tech.xixing.qaqmall.form.OrderCreateForm;
import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.service.IOrderService;
import tech.xixing.qaqmall.vo.OrderVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/13 8:58
 */
@RestController
public class OrderController {

    @Autowired
    IOrderService orderService;


    @PostMapping("/orders")
    public ResponseVO<OrderVO> create(@Valid @RequestBody OrderCreateForm orderCreateForm,
                                      HttpSession session){
        User user=(User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(),orderCreateForm.getShippingId());

    }

    @GetMapping("/orders")
    public ResponseVO<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                     HttpSession session){
        User user=(User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVO<OrderVO> detail(@PathVariable Long orderNo,
                                     HttpSession session){
        User user=(User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVO<OrderVO> cancel(@PathVariable Long orderNo,

                                      HttpSession session){
        User user=(User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(),orderNo);
    }






}
