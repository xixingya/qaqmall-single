package tech.xixing.qaqmall.service;

import com.github.pagehelper.PageInfo;
import tech.xixing.qaqmall.vo.OrderVO;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/11 10:28
 */
public interface IOrderService {
    public ResponseVO<OrderVO> create(Integer uid,Integer shippingId);
    ResponseVO<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);
    ResponseVO<OrderVO> detail(Integer uid,Long orderNo);
    ResponseVO cancel(Integer uid,Long orderNo);
    void paid(Long orderNo);

}
