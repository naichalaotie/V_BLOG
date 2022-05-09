package com.xxx.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.blog.domain.Category;
import com.xxx.blog.domain.vo.CategoryVo;
import com.xxx.blog.mapper.CategoryMapper;
import com.xxx.blog.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public CategoryVo selectCategoryById(long id) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getId,id);
        Category category = categoryMapper.selectOne(lambdaQueryWrapper);

//        CategoryVo categoryVo = new CategoryVo();
//        BeanUtils.copyProperties(category,categoryVo);
//        categoryVo.setId(category.getId().toString());
//        return categoryVo;
        return copy(category);
    }


    @Override
    public List<CategoryVo> listCategory(Boolean description) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(description){
            lambdaQueryWrapper.select(Category::getId,Category::getAvatar,Category::getCategoryName,Category::getDescription);
        }else {
            lambdaQueryWrapper.select(Category::getId, Category::getAvatar, Category::getCategoryName);
        }
        List<Category> list = categoryMapper.selectList(null);
        return copyList(list);
    }



    //    @Override
//    public List<CategoryVo> selectCategoryById(long id) {
//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Category::getId,id);
//        List<Category> categorys = categoryMapper.selectList(lambdaQueryWrapper);
//        System.out.println(categorys+"`````````````````````````````````````````````````````````````````````````````");
//        return copyList(categorys);
//    }

    private CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        categoryVo.setId(category.getId().toString());
        return categoryVo;
    }

    private List<CategoryVo> copyList(List<Category> categories){
        List<CategoryVo> list = new ArrayList<>();
        for(Category category : categories){
            list.add(copy(category));
        }
        return list;
    }
}
