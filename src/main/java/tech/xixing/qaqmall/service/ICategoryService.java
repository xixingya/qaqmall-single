package tech.xixing.qaqmall.service;

import tech.xixing.qaqmall.vo.CategoryVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.List;
import java.util.Set;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/5 9:04
 */
public interface ICategoryService {

    ResponseVO<List<CategoryVO>> selectAll();
    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
