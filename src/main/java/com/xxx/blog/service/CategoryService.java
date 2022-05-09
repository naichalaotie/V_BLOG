package com.xxx.blog.service;

import com.xxx.blog.domain.Category;
import com.xxx.blog.domain.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
//    public List<CategoryVo> selectCategoryById(long id);
    public CategoryVo selectCategoryById(long id);

    public List<CategoryVo> listCategory(Boolean description);

}
