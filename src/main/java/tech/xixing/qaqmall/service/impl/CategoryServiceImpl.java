package tech.xixing.qaqmall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.xixing.qaqmall.consts.MallConst;
import tech.xixing.qaqmall.dao.CategoryMapper;
import tech.xixing.qaqmall.pojo.Category;
import tech.xixing.qaqmall.service.ICategoryService;
import tech.xixing.qaqmall.vo.CategoryVO;
import tech.xixing.qaqmall.vo.ResponseVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/5 9:06
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 请求耗时 http(微信api)>磁盘>java程序
     * @return
     */

    @Override
    public ResponseVO<List<CategoryVO>> selectAll() {


        List<Category> categoryList = categoryMapper.selectAll();
        List<CategoryVO> categoryVOList=new ArrayList<>();

        for(Category category:categoryList){
            if(category.getParentId().equals(MallConst.ROOT_PARENT_ID)){
                CategoryVO categoryVO=new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);

            }
            categoryVOList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());
        }
        findSubCategory(categoryVOList,categoryList);



        return ResponseVO.successByData(categoryVOList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categoryList = categoryMapper.selectAll();
        findSubCategoryId(id,resultSet,categoryList);

    }

    public void findSubCategoryId(Integer id, Set<Integer> resultSet,List<Category>categoryList){
        for (Category category : categoryList) {
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(),resultSet,categoryList);
            }
        }
    }

    private void findSubCategory(List<CategoryVO> categoryVOList,List<Category> categoryList) {
        for(CategoryVO categoryVO:categoryVOList){
            
            List<CategoryVO> subCategoryVOList =new ArrayList<>();
            
            for(Category category:categoryList){
                //查子目录，查的到继续查
                if(categoryVO.getId().equals(category.getParentId())){
                    CategoryVO subCategoryVO = category2CategoryVO(category);
                    subCategoryVOList.add(subCategoryVO);
                }
                findSubCategory(subCategoryVOList,categoryList);
            }
            subCategoryVOList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());
            categoryVO.setSubCategories(subCategoryVOList);
        }
    }

    private CategoryVO category2CategoryVO(Category category){
        CategoryVO categoryVO=new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        return categoryVO;
    }
}
