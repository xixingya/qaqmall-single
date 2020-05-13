package tech.xixing.qaqmall.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.xixing.qaqmall.QaqmallApplicationTests;
import tech.xixing.qaqmall.form.CartAddForm;
import tech.xixing.qaqmall.service.ICartService;
import tech.xixing.qaqmall.vo.CartVO;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/8 9:44
 */
@Slf4j
public class CartServiceImplTest extends QaqmallApplicationTests {

    @Autowired
    ICartService cartService;
    Gson gson=new Gson();

    @Test
    public void add() {
        CartAddForm cartAddForm=new CartAddForm();
        cartAddForm.setProductId(28);
        cartAddForm.setSelected(true);

        cartService.add(1,cartAddForm);
    }

    @Test
    public void list(){
        ResponseVO<CartVO> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));

    }
}