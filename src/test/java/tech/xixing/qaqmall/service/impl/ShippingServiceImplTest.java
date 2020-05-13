package tech.xixing.qaqmall.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.xixing.qaqmall.QaqmallApplicationTests;
import tech.xixing.qaqmall.service.IShippingService;
import tech.xixing.qaqmall.vo.ResponseVO;

import static org.junit.Assert.*;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/10 10:44
 */
public class ShippingServiceImplTest extends QaqmallApplicationTests {

    @Autowired
    private IShippingService shippingService;


    @Test
    public void list() {
        ResponseVO responseVO=shippingService.list(1,1,10);

    }
}