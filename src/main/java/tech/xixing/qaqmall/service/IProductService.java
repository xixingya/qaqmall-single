package tech.xixing.qaqmall.service;

import com.github.pagehelper.PageInfo;
import tech.xixing.qaqmall.vo.ProductDetailVO;
import tech.xixing.qaqmall.vo.ResponseVO;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/6 8:55
 */
public interface IProductService {

    ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVO<ProductDetailVO> detail(Integer productId);


}
