package tech.xixing.qaqmall.listener;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.xixing.qaqmall.dao.OrderMapper;
import tech.xixing.qaqmall.pojo.Order;
import tech.xixing.qaqmall.pojo.PayInfo;
import tech.xixing.qaqmall.service.IOrderService;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/13 9:30
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @RabbitHandler
    public void process(String msg){
        log.info("【接收到消息】:{}",msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        Order order = orderMapper.selectByOrderNo(payInfo.getOrderNo());
        ///有用
        /*if(payInfo.getPlatformStatus().equals("SUCCESS")&&payInfo.getPayAmount().equals(order.getPayment())){
            orderService.paid(payInfo.getOrderNo());
        }else {
            throw new RuntimeException("支付失败");
        }*/

        if(payInfo.getPlatformStatus().equals("SUCCESS")){
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
