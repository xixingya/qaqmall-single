package tech.xixing.qaqmall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.xixing.qaqmall.service.IProductService;
import tech.xixing.qaqmall.vo.ProductDetailVO;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 9:37
 */
@RestController
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("/products")
    public ResponseVO<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return productService.list(categoryId, pageNum, pageSize);
    }

    @GetMapping("/products/{productId}")
    public ResponseVO<ProductDetailVO> detail(@PathVariable Integer productId){
        return productService.detail(productId);
    }



}
