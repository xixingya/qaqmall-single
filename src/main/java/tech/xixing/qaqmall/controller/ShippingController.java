package tech.xixing.qaqmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.xixing.qaqmall.consts.MallConst;
import tech.xixing.qaqmall.form.ShippingForm;
import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.service.IShippingService;
import tech.xixing.qaqmall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/10 10:49
 */
@RestController
public class ShippingController {

    @Autowired
    IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVO add(@Valid @RequestBody ShippingForm shippingForm,
                          HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(),shippingForm);

    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVO delete(@PathVariable Integer shippingId,
                             HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVO update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm shippingForm,
                             HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId,shippingForm);
    }
    @GetMapping("/shippings")
    public ResponseVO list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                             HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
