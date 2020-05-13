package tech.xixing.qaqmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tech.xixing.qaqmall.dao.OrderItemMapper;
import tech.xixing.qaqmall.dao.OrderMapper;
import tech.xixing.qaqmall.dao.ProductMapper;
import tech.xixing.qaqmall.dao.ShippingMapper;
import tech.xixing.qaqmall.enums.OrderStatusEnum;
import tech.xixing.qaqmall.enums.PaymentTypeEnum;
import tech.xixing.qaqmall.enums.ResponseEnum;
import tech.xixing.qaqmall.pojo.*;
import tech.xixing.qaqmall.service.ICartService;
import tech.xixing.qaqmall.service.IOrderService;
import tech.xixing.qaqmall.vo.OrderItemVO;
import tech.xixing.qaqmall.vo.OrderVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/11 10:29
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Override
    @Transactional
    public ResponseVO<OrderVO> create(Integer uid, Integer shippingId) {
        //收货地址校验
        Shipping shipping = shippingMapper.selectByShippingIdAndUid(shippingId, uid);
        if(shipping==null){
            return ResponseVO.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }

        //购物车校验,是否有商品，库存是否充足
        List<Cart> carts = cartService.listForCart(uid);
        List<Cart> cartList=new ArrayList<>();
        for (Cart cart : carts) {
            if(cart.getProductSelected()){
                cartList.add(cart);
            }
        }
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVO.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        //获取productIds
        Set<Integer> productIds = cartList.stream().map(e -> e.getProductId()).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByPrimaryKeySet(productIds);
        Map<Integer,Product> map=productList.stream().collect(Collectors.toMap(Product::getId,product -> product));


        List<OrderItem> orderItemList=new ArrayList<>();
        Long orderNo=generateOrderNoId();

        //改完库存的数据存放在这里，然后写入mysql
        List<Product> products=new ArrayList<>();

        for (Cart cart : cartList) {
            Product product=map.get(cart.getProductId());
            //商品上下架状态
            if(product==null){
                return ResponseVO.error(ResponseEnum.PRODUCT_NOT_EXIST,"商品不存在,或者已下架,productId="+cart.getProductId());
            }

            //库存是否充足
            if(product.getStock()<cart.getQuantity()){
                return ResponseVO.error(ResponseEnum.PROODUCT_STOCK_ERROR,"库存不正确:商品名为"+product.getName());
            }
            //改完库存后，在for循环外面一次性写入数据库
            product.setStock(product.getStock()-cart.getQuantity());
            products.add(product);
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);


        }

        //改完库存写入数据库
        int i1=productMapper.updateByListPrimaryKey(products);
        if(i1<=0){
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        //计算总价，选中的商品
        //生成订单，入库
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int row=orderMapper.insertSelective(order);
        if(row<=0){
            return ResponseVO.error(ResponseEnum.ERROR);

        }
        int i = orderItemMapper.batchInsert(orderItemList);
        System.out.println();


        //更新购物车
        //redis有事务（打包命令），但是不会回滚
        for(Cart cart:cartList){
            cartService.delete(uid,cart.getProductId());
        }
        OrderVO orderVO = buildOrderVO(order, orderItemList, shippingId, shipping);

        return ResponseVO.successByData(orderVO);
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        List<Order> orders = orderMapper.selectByUid(uid);
        PageHelper.startPage(pageNum,pageSize);
        Set<Long> orderNoSet = orders.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream().collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orders.stream().map(Order::getShippingId).collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);

        Map<Integer, Shipping> shippingMap = shippingList.stream().collect(Collectors.toMap(Shipping::getId,shipping -> shipping));

        List<OrderVO> orderVOList=new ArrayList<>();

        for (Order order : orders) {
            OrderVO orderVO = buildOrderVO(order, orderItemMap.get(order.getOrderNo()), order.getShippingId(), shippingMap.get(order.getShippingId()));
            orderVOList.add(orderVO);
        }


        PageInfo pageInfo=new PageInfo(orders);
        pageInfo.setList(orderVOList);

        return ResponseVO.successByData(pageInfo);
    }

    @Override
    public ResponseVO<OrderVO> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null||!order.getUserId().equals(uid)){
            return ResponseVO.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVO orderVO = buildOrderVO(order, orderItemList, shipping.getId(), shipping);
        return ResponseVO.successByData(orderVO);
    }

    @Override
    public ResponseVO cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null||!order.getUserId().equals(uid)){
            return ResponseVO.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVO.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row=orderMapper.updateByPrimaryKey(order);
        if(row<=0){
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        return ResponseVO.success();
    }

    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getMsg()+"订单id="+orderNo);
        }
        //只有未付款订单可以支付
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getMsg());
        }
        order.setPaymentTime(new Date());
        order.setStatus(OrderStatusEnum.PAID.getCode());
        int row=orderMapper.updateByPrimaryKey(order);
        if(row<=0){
            throw new RuntimeException("将订单更新为已支付状态失败，订单id="+order.getOrderNo());
        }
    }

    private OrderVO buildOrderVO(Order order, List<OrderItem> orderItemList, Integer shippingId,Shipping shipping) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        if(order.getStatus().equals(10)){
            orderVO.setStatusDesc("未支付");
        }else if(order.getStatus().equals(20)){
            orderVO.setStatusDesc("已支付");
        }else if(order.getStatus().equals(0)){
            orderVO.setStatusDesc("已取消");
        }else if(order.getStatus().equals(40)){
            orderVO.setStatusDesc("已发货");
        }else if(order.getStatus().equals(50)){
            orderVO.setStatusDesc("交易成功");
        }else if(order.getStatus().equals(60)){
            orderVO.setStatusDesc("交易关闭");
        }else {
            orderVO.setStatusDesc("未知状态");
        }
        if(order.getPaymentType().equals(1)){
            orderVO.setPaymentTypeDesc("在线支付");
        }else {
            orderVO.setPaymentTypeDesc("其他支付");
        }
        List<OrderItemVO> orderItemVOList=orderItemList.stream().map(e->{
            OrderItemVO orderItemVO=new OrderItemVO();
            BeanUtils.copyProperties(e,orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());

        orderVO.setOrderItemVoList(orderItemVOList);
        if(shipping!=null) {
            orderVO.setShippingId(shippingId);
            orderVO.setShippingVo(shipping);
        }

        return orderVO;



    }

    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(uid);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    /**
     * 企业级是使用分布式唯一id/主键
     * @return
     */
    private Long generateOrderNoId(){
        return System.currentTimeMillis()+new Random().nextInt(999);
    }

    private Order buildOrder(Integer uid,Long orderNo,Integer shippingId,List<OrderItem> orderItemList){
        Order order = new Order();
        order.setUserId(uid);
        order.setOrderNo(orderNo);
        order.setShippingId(shippingId);
        BigDecimal sums=new BigDecimal(0);

        for (OrderItem orderItem : orderItemList) {
            sums=sums.add(orderItem.getTotalPrice());
        }
        order.setPayment(sums);

        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());


        return order;
    }
}
