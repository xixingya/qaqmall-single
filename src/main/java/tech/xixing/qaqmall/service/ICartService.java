package tech.xixing.qaqmall.service;

import tech.xixing.qaqmall.form.CartAddForm;
import tech.xixing.qaqmall.form.CartUpdateForm;
import tech.xixing.qaqmall.pojo.Cart;
import tech.xixing.qaqmall.vo.CartVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/8 9:08
 */
public interface ICartService {
    ResponseVO<CartVO> add(Integer uid, CartAddForm cartAddForm);
    ResponseVO<CartVO> list(Integer uid);

    ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);


    ResponseVO<CartVO> delete(Integer uid,Integer productId);

    ResponseVO<CartVO> selectAll(Integer uid);
    ResponseVO<CartVO> unSelectAll(Integer uid);
    ResponseVO<Integer> sum(Integer uid);

    public List<Cart> listForCart(Integer uid);
}
