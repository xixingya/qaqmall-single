package tech.xixing.qaqmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.xixing.qaqmall.dao.ProductMapper;
import tech.xixing.qaqmall.enums.ProductStatusEnum;
import tech.xixing.qaqmall.enums.ResponseEnum;
import tech.xixing.qaqmall.pojo.Product;
import tech.xixing.qaqmall.service.ICategoryService;
import tech.xixing.qaqmall.service.IProductService;
import tech.xixing.qaqmall.vo.ProductDetailVO;
import tech.xixing.qaqmall.vo.ProductVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 8:57
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;



    @Override
    public ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {

        Set<Integer> categoryIdSet=new HashSet<>();
        if(categoryId!=null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum,pageSize);

        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVO> productVOList=new ArrayList<>();

        for (Product product : productList) {
            ProductVO productVO=new ProductVO();
            BeanUtils.copyProperties(product,productVO);
            productVOList.add(productVO);
        }

        PageInfo pageInfo=new PageInfo<>(productList);
        pageInfo.setList(productVOList);


        return ResponseVO.successByData(pageInfo);
    }

    @Override
    public ResponseVO<ProductDetailVO> detail(Integer productId) {

        Product product = productMapper.selectByPrimaryKey(productId);
        ProductDetailVO productDetailVO=new ProductDetailVO();

        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())||product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVO.error(ResponseEnum.PRODUCT_OFFSALE_OR_DELETE);
        }

        BeanUtils.copyProperties(product,productDetailVO);

        //对敏感数据隐藏真实数据
        productDetailVO.setStock(product.getStock()>100?100:product.getStock());



        return ResponseVO.successByData(productDetailVO);
    }

}
