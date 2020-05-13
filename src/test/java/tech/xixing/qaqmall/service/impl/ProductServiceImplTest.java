package tech.xixing.qaqmall.service.impl;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tech.xixing.qaqmall.QaqmallApplicationTests;
import tech.xixing.qaqmall.dao.ProductMapper;
import tech.xixing.qaqmall.pojo.Product;
import tech.xixing.qaqmall.service.IProductService;
import tech.xixing.qaqmall.vo.ProductVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 9:14
 */
@Slf4j
public class ProductServiceImplTest extends QaqmallApplicationTests {

    @Autowired
    IProductService productService;

    @Autowired
    ProductMapper productMapper;


    @Test
    public void list() {
        String str="{\"status\":0,\"data\":{\"total\":19,\"list\":[{\"id\":30,\"categoryId\":100012,\"name\":\"小米CC9\",\"subtitle\":\"3200万+4800万 前后双旗舰相机\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/f515ab05232ed14ccd78ec67e024495a.png\",\"price\":1799.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":31,\"categoryId\":100012,\"name\":\"小米CC9e\",\"subtitle\":\"3200万+4800万 前后双旗舰相机\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/df9b3e7a562e02a33eb069b3f0119815.png\",\"price\":1299.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":32,\"categoryId\":100012,\"name\":\"小米CC9 美图定制版\",\"subtitle\":\"直出超质感美颜\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/b02f50c9dd2e01c139a06a12502755ef.png\",\"price\":2599.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":33,\"categoryId\":100012,\"name\":\"小米9\",\"subtitle\":\"小米9 战斗天使\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/2c9307e9690dfbca39d8de770a7a8664.png\",\"price\":2599.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":34,\"categoryId\":100012,\"name\":\"小米9 Pro 5G\",\"subtitle\":\"快的不只是5G\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/41c566d825a3ae3b5751a78d4c7cffdb.png\",\"price\":3699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":35,\"categoryId\":100012,\"name\":\"小米MIX Alpha\",\"subtitle\":\"5G环绕屏概念手机\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/5d19da60f9f62eb2aa5dcdbd7df19f0f.png\",\"price\":19999.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":36,\"categoryId\":100012,\"name\":\"Redmi Note 8 Pro\",\"subtitle\":\"6400万全场景四摄\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/6f2493e6c6fe8e2485c407e5d75e3651.jpg\",\"price\":1399.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":37,\"categoryId\":100012,\"name\":\"Redmi Note 8\",\"subtitle\":\"千元4800万四摄\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/4c87947d104ee5833913e4c520108f16.jpg\",\"price\":999.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":38,\"categoryId\":100012,\"name\":\"Redmi K20 Pro 尊享版\",\"subtitle\":\"骁龙855 Plus， 弹出全面屏\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/8737a33c78a94bc36afb860ab23b3939.jpg\",\"price\":2699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":39,\"categoryId\":100012,\"name\":\"Redmi Note 7\",\"subtitle\":\"4800万拍照千元机\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/0ce61b71e2f81df62bd0c05aaa901d22.jpg\",\"price\":1099.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":40,\"categoryId\":100012,\"name\":\"Redmi 7 \",\"subtitle\":\"4000mAh超长续航\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9aab8a7fa9005ef918c9aa2d5f17c806.jpg\",\"price\":699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":41,\"categoryId\":100012,\"name\":\"Redmi 7A\",\"subtitle\":\"小巧大电量 持久流畅\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/3c1af9783bdc53ed843af5655ca92009.jpg\",\"price\":579.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":42,\"categoryId\":100012,\"name\":\"小米CC9\",\"subtitle\":\"3200万自拍，4800万三摄\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/bd25cc614a670f4d5546fe82e239ef86.jpg\",\"price\":1799.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":43,\"categoryId\":100012,\"name\":\"小米9 Pro 5G\",\"subtitle\":\"5G双卡全网通，骁龙855Plus\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ca9b4f81af709935556bef9aa21a90e8.jpg\",\"price\":3699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"}],\"pageNum\":1,\"pageSize\":14,\"size\":14,\"startRow\":1,\"endRow\":14,\"pages\":2,\"prePage\":0,\"nextPage\":2,\"isFirstPage\":true,\"isLastPage\":false,\"hasPreviousPage\":false,\"hasNextPage\":true,\"navigatePages\":8,\"navigatepageNums\":[1,2],\"navigateFirstPage\":1,\"navigateLastPage\":2}}";
        String str2="{\"status\":0,\"data\":{\"total\":19,\"list\":[{\"id\":30,\"categoryId\":100012,\"name\":\"小米CC9\",\"subtitle\":\"3200万+4800万 前后双旗舰相机\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/f515ab05232ed14ccd78ec67e024495a.png\",\"price\":1799.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":31,\"categoryId\":100012,\"name\":\"小米CC9e\",\"subtitle\":\"3200万+4800万 前后双旗舰相机\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/df9b3e7a562e02a33eb069b3f0119815.png\",\"price\":1299.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":32,\"categoryId\":100012,\"name\":\"小米CC9 美图定制版\",\"subtitle\":\"直出超质感美颜\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/b02f50c9dd2e01c139a06a12502755ef.png\",\"price\":2599.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":33,\"categoryId\":100012,\"name\":\"小米9\",\"subtitle\":\"小米9 战斗天使\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/2c9307e9690dfbca39d8de770a7a8664.png\",\"price\":2599.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":34,\"categoryId\":100012,\"name\":\"小米9 Pro 5G\",\"subtitle\":\"快的不只是5G\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/41c566d825a3ae3b5751a78d4c7cffdb.png\",\"price\":3699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":35,\"categoryId\":100012,\"name\":\"小米MIX Alpha\",\"subtitle\":\"5G环绕屏概念手机\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/5d19da60f9f62eb2aa5dcdbd7df19f0f.png\",\"price\":19999.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":36,\"categoryId\":100012,\"name\":\"Redmi Note 8 Pro\",\"subtitle\":\"6400万全场景四摄\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/6f2493e6c6fe8e2485c407e5d75e3651.jpg\",\"price\":1399.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":37,\"categoryId\":100012,\"name\":\"Redmi Note 8\",\"subtitle\":\"千元4800万四摄\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/4c87947d104ee5833913e4c520108f16.jpg\",\"price\":999.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":38,\"categoryId\":100012,\"name\":\"Redmi K20 Pro 尊享版\",\"subtitle\":\"骁龙855 Plus， 弹出全面屏\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/8737a33c78a94bc36afb860ab23b3939.jpg\",\"price\":2699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":39,\"categoryId\":100012,\"name\":\"Redmi Note 7\",\"subtitle\":\"4800万拍照千元机\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/0ce61b71e2f81df62bd0c05aaa901d22.jpg\",\"price\":1099.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":40,\"categoryId\":100012,\"name\":\"Redmi 7 \",\"subtitle\":\"4000mAh超长续航\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/9aab8a7fa9005ef918c9aa2d5f17c806.jpg\",\"price\":699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":41,\"categoryId\":100012,\"name\":\"Redmi 7A\",\"subtitle\":\"小巧大电量 持久流畅\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/3c1af9783bdc53ed843af5655ca92009.jpg\",\"price\":579.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":42,\"categoryId\":100012,\"name\":\"小米CC9\",\"subtitle\":\"3200万自拍，4800万三摄\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/bd25cc614a670f4d5546fe82e239ef86.jpg\",\"price\":1799.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":43,\"categoryId\":100012,\"name\":\"小米9 Pro 5G\",\"subtitle\":\"5G双卡全网通，骁龙855Plus\",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ca9b4f81af709935556bef9aa21a90e8.jpg\",\"price\":3699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":44,\"categoryId\":100012,\"name\":\"红米8A\",\"subtitle\":\"5000mAh 充电宝级大电量\",\"mainImage\":\"https://i1.mifile.cn/f/i/2019/redmi1014a/part39.png\",\"price\":699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":45,\"categoryId\":100012,\"name\":\"红米Note 7\",\"subtitle\":\"4800万拍照千元机 / 品质“小金刚”\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-shopapi-pms/pms_1551869450.61563110.jpg\",\"price\":999.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":46,\"categoryId\":100012,\"name\":\"AI 4K 全面屏电视\",\"subtitle\":\"小米全面屏电视 55英寸\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/a40904b6a053b73b631a152334388d0e.jpg\",\"price\":1799.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":47,\"categoryId\":100012,\"name\":\"小米MIX 3\",\"subtitle\":\"一面科技，一面艺术\",\"mainImage\":\"https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/9e83fabc3efeb8dd9f880ce26220c294.png\",\"price\":2599.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"},{\"id\":48,\"categoryId\":100012,\"name\":\"Redmi K20 Pro 尊享版\",\"subtitle\":\"真旗舰、敢K.O. \",\"mainImage\":\"https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/8737a33c78a94bc36afb860ab23b3939.jpg\",\"price\":2699.00,\"status\":1,\"imageHost\":\"http://img.springboot.cn\"}],\"pageNum\":1,\"pageSize\":19,\"size\":19,\"startRow\":1,\"endRow\":19,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}}";



        Gson gson=new Gson();
        ResponseVO responseVO = gson.fromJson(str2, ResponseVO.class);
        Object data = responseVO.getData();
        LinkedTreeMap linkedTreeMap= (LinkedTreeMap) responseVO.getData();
        List<LinkedTreeMap> list= (List) linkedTreeMap.get("list");
        List<ProductVO> productVOList = cast(list);



        for (ProductVO productVO : productVOList) {
            Product product=new Product();
            BeanUtils.copyProperties(productVO,product);
            product.setStock(99);
            productMapper.insertSelective(product);
        }
        log.info("aaa");


        //ResponseVO<List<ProductVO>> listResponseVO = productService.list(null, 1, 2);
    }
    private List<ProductVO> cast(List<LinkedTreeMap> linkedTreeMap){
        List<ProductVO> productVOList=new ArrayList<>();
        for (LinkedTreeMap treeMap : linkedTreeMap) {
            Integer id=((Double)treeMap.get("id")).intValue();
            Integer categoryId = ((Double)treeMap.get("categoryId")).intValue();
            String name = treeMap.get("name").toString();
            String subtitle = treeMap.get("subtitle").toString();
            String mainImage = treeMap.get("mainImage").toString();
            Integer status = ((Double)treeMap.get("status")).intValue();
            Double pricetemp = (Double) treeMap.get("price");
            BigDecimal price=new BigDecimal(pricetemp);
            ProductVO productVO=new ProductVO(id,categoryId,name,subtitle,mainImage,status,price);
            productVOList.add(productVO);

        }
        return productVOList;
    }
}