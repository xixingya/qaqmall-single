package tech.xixing.qaqmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.xixing.qaqmall.consts.MallConst;
import tech.xixing.qaqmall.form.CartAddForm;
import tech.xixing.qaqmall.form.CartUpdateForm;
import tech.xixing.qaqmall.pojo.User;
import tech.xixing.qaqmall.service.ICartService;
import tech.xixing.qaqmall.vo.CartVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/8 8:55
 */
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;
    @GetMapping("/carts")
    public ResponseVO<CartVO> list(HttpSession session){
        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());

    }




    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session){
        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(),cartAddForm);

    }
    @PutMapping("/carts/{productId}")
    public ResponseVO<CartVO> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session){

        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(),productId,form);
    }
    @DeleteMapping("/carts/{productId}")
    public ResponseVO<CartVO> delete(@PathVariable Integer productId,
                                     HttpSession session){

        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }
    @PutMapping("/carts/selectAll")
    public ResponseVO<CartVO> selectAll(HttpSession session){

        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }
    @PutMapping("/carts/unSelectAll")
    public ResponseVO<CartVO> unSelectAll(HttpSession session){

        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVO<Integer> sum(HttpSession session){

        User user=(User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }

}
