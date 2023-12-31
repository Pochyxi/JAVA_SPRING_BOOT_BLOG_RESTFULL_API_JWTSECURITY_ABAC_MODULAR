package com.developez.service.impl;

import com.developez.DTO.CategoryDto;
import com.developez.entity.Category;
import com.developez.repository.CategoryRepository;
import com.developez.security.exception.ResourceNotFoundException;
import com.developez.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl( CategoryRepository categoryRepository, ModelMapper modelMapper ) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory( CategoryDto categoryDto ) {

        Category category = modelMapper.map( categoryDto, Category.class );
        Category savedCategory = categoryRepository.save( category );

        return modelMapper.map( savedCategory, CategoryDto.class );
    }

    @Override
    public CategoryDto getCategory( Long categoryId ) {

        Category category = categoryRepository.findById( categoryId )
                .orElseThrow(() -> new ResourceNotFoundException( "Category", "id", categoryId ) );

        return modelMapper.map( category, CategoryDto.class );
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map( category -> modelMapper.map( category, CategoryDto.class ) )
                .toList();
    }

    @Override
    public CategoryDto updateCategory( CategoryDto categoryDto, Long categoryId ) {

        Category category = categoryRepository.findById( categoryId )
                .orElseThrow(() -> new ResourceNotFoundException( "Category", "id", categoryId ) );

        category.setName( categoryDto.getName() );
        category.setDescription( categoryDto.getDescription() );

        Category savedCategory = categoryRepository.save( category );

        return modelMapper.map( savedCategory, CategoryDto.class );
    }

    @Override
    public void deleteCategory( Long categoryId ) {

        Category category = categoryRepository.findById( categoryId )
                .orElseThrow(() -> new ResourceNotFoundException( "Category", "id", categoryId ) );

        categoryRepository.delete( category );
    }
}
