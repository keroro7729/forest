package com.forest.forest_server.Category;

import org.springframework.stereotype.Service;

@Service
public class CategorySecvice {

    private final CategoryRepository categoryRepository;
    public CategorySecvice(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String catName){
        Category category = new Category();
        category.setCatName(catName);
        categoryRepository.save(category);
    }

    public Category getById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
}
