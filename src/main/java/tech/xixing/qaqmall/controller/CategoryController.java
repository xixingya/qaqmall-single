package tech.xixing.qaqmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xixing.qaqmall.service.ICategoryService;
import tech.xixing.qaqmall.vo.CategoryVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/5 9:42
 */
@RestController
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVO<List<CategoryVO>> selectAll(){
        return categoryService.selectAll();
    }
}
