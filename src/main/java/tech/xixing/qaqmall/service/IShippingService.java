package tech.xixing.qaqmall.service;

import com.github.pagehelper.PageInfo;
import tech.xixing.qaqmall.form.ShippingForm;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.Map;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/10 9:46
 */
public interface IShippingService {

    ResponseVO<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm);

    ResponseVO delete(Integer uid,Integer shippingId);

    ResponseVO update(Integer uid,Integer shippingId,ShippingForm shippingForm);

    ResponseVO<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);



}
