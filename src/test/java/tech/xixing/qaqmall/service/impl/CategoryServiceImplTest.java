package tech.xixing.qaqmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.xixing.qaqmall.QaqmallApplicationTests;
import tech.xixing.qaqmall.service.ICategoryService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/5 15:49
 */
@Slf4j
public class CategoryServiceImplTest extends QaqmallApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void testfindByProductId(){
        Set<Integer> set=new HashSet<Integer>();
        categoryService.findSubCategoryId(100001,set);
        log.info("set={}",set);
    }

}