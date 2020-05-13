package tech.xixing.qaqmall.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.xixing.qaqmall.QaqmallApplicationTests;
import tech.xixing.qaqmall.service.IOrderService;
import tech.xixing.qaqmall.vo.OrderVO;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/11 15:49
 */
@Slf4j
//@Transactional
public class OrderServiceImplTest extends QaqmallApplicationTests {

    @Autowired
    private IOrderService orderService;
    Gson gson=new Gson();
    Integer uid=1;


    @Test
    public void create() {
        log.info("aaa");
        ResponseVO<OrderVO> orderVOResponseVO = orderService.create(uid, 6);
        log.info("orderVO={}",orderVOResponseVO);

    }

    @Test
    public void list(){
        ResponseVO<PageInfo> list = orderService.list(uid, 1, 10);
        log.info("pageInfo={}",gson.toJson(list));
    }
}