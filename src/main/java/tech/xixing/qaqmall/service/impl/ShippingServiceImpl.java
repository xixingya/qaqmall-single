package tech.xixing.qaqmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.xixing.qaqmall.dao.ShippingMapper;
import tech.xixing.qaqmall.enums.ResponseEnum;
import tech.xixing.qaqmall.form.ShippingForm;
import tech.xixing.qaqmall.pojo.Shipping;
import tech.xixing.qaqmall.service.IShippingService;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/10 9:50
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;



    @Override
    public ResponseVO<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);

        if(row==0){
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        Map<String,Integer> map=new HashMap<>();
        map.put("shippingId",shipping.getId());
        return ResponseVO.successByData(map);
    }

    @Override
    public ResponseVO delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(shippingId, uid);
        if(row==0){
            ResponseVO.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }



        return ResponseVO.success();
    }

    @Override
    public ResponseVO update(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(row==0){
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        return ResponseVO.success();
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);


        List<Shipping> shippingList = shippingMapper.selectByUid(uid);
        PageInfo pageInfo=new PageInfo(shippingList);
        return ResponseVO.successByData(pageInfo);
    }
}
