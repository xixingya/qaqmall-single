package tech.xixing.qaqmall.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.xixing.qaqmall.dao.ProductMapper;
import tech.xixing.qaqmall.enums.ProductStatusEnum;
import tech.xixing.qaqmall.enums.ResponseEnum;
import tech.xixing.qaqmall.form.CartAddForm;
import tech.xixing.qaqmall.form.CartUpdateForm;
import tech.xixing.qaqmall.pojo.Cart;
import tech.xixing.qaqmall.pojo.Product;
import tech.xixing.qaqmall.service.ICartService;
import tech.xixing.qaqmall.vo.CartProductVO;
import tech.xixing.qaqmall.vo.CartVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/8 9:09
 */
@Service
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";
    Integer quantity = 1;
    private Gson gson = new Gson();


    @Autowired
    ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVO<CartVO> add(Integer uid, CartAddForm cartAddForm) {
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        //商品是否存在
        if (product == null) {
            return ResponseVO.error(ResponseEnum.PRODUCT_NOT_EXIST);

        }


        //商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVO.error(ResponseEnum.PRODUCT_OFFSALE_OR_DELETE);
        }

        //商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVO.error(ResponseEnum.PROODUCT_STOCK_ERROR);
        }

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        //获取redis有没有这个商品
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart;

        if (StringUtils.isEmpty(value)) {
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        } else {
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);

        }
        opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));


        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> list(Integer uid) {

        List<CartProductVO> cartProductVOList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Map<Integer,Cart> carts = new HashMap<>();
        //是否全选
        Boolean selectAll = true;

        //购物车总数
        Integer cartTotalQuantity = 0;

        //购物车总价
        BigDecimal cartTotalPrice = new BigDecimal(0);

        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(String.format(CART_REDIS_KEY_TEMPLATE, uid));
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf((String) entry.getKey());
            Cart cart = gson.fromJson((String) entry.getValue(), Cart.class);
            carts.put(productId,cart);
            set.add(productId);

        }
        List<Product> products = productMapper.selectByPrimaryKeySet(set);

        if(carts.isEmpty()){
            return ResponseVO.successByData(null);
        }


        for (Product product : products) {
            Cart cart = carts.get(product.getId());
            //是否全选

            if (cart.getProductSelected() == false) {
                selectAll = false;
            }

            //购物车数量的总数
            cartTotalQuantity = cart.getQuantity() + cartTotalQuantity;
            CartProductVO cartProductVO = new CartProductVO(product.getId(), cart.getQuantity(),
                    product.getName(), product.getSubtitle(), product.getMainImage(), product.getPrice(),
                    product.getStatus(), product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())), product.getStock(), cart.getProductSelected());
            cartProductVOList.add(cartProductVO);
            cartTotalPrice = cartTotalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        }

        CartVO cartVO = new CartVO();
        cartVO.setCartProductVoList(cartProductVOList);
        cartVO.setSelectedAll(selectAll);
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartTotalQuantity(cartTotalQuantity);


        return ResponseVO.successByData(cartVO);
    }

    @Override
    public ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {

        //Product product = productMapper.selectByPrimaryKey(productId);


        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        //获取redis有没有这个商品
        String value = opsForHash.get(redisKey, String.valueOf(productId));


        if (StringUtils.isEmpty(value)) {
            ResponseVO.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        Cart cart = gson.fromJson(value, Cart.class);
        if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() > 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }


        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));


        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> delete(Integer uid, Integer productId) {
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        //获取redis有没有这个商品
        String value = opsForHash.get(redisKey, String.valueOf(productId));


        if (StringUtils.isEmpty(value)) {
            ResponseVO.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }



        opsForHash.delete(redisKey, String.valueOf(productId));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> selectAll(Integer uid) {
        List<Cart> cartList=listForCart(uid);
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));

        }


        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> unSelectAll(Integer uid) {
        List<Cart> cartList=listForCart(uid);
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));

        }


        return list(uid);
    }

    @Override
    public ResponseVO<Integer> sum(Integer uid) {
        List<Cart> cartList = listForCart(uid);
        Integer sums=0;
        for (Cart cart : cartList) {
            sums=cart.getQuantity()+sums;
        }


        return ResponseVO.successByData(sums);
    }

    @Override
    public List<Cart> listForCart(Integer uid){
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        List<Cart> cartList=new ArrayList<>();
        Map<String, String> entries = opsForHash.entries(redisKey);
        for(Map.Entry<String, String> entry:entries.entrySet()){
            cartList.add(gson.fromJson(entry.getValue(),Cart.class));

        }
        return cartList;
    }
}
