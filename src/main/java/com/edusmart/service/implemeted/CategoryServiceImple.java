package com.edusmart.service.implemeted;

import com.edusmart.dto.CategoryDTO;
import com.edusmart.entity.Categories;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.CategoryRepository;
import com.edusmart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImple implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if(categoryRepository.existsByName(categoryDTO.getName())){
            throw new ResourcesNotFound("Category with name "+categoryDTO.getName()+" already exists.");
        }
        Categories category=mapToEntity(categoryDTO);
        Categories saved=categoryRepository.save(category);
        return mapToDTO(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Categories category=categoryRepository.findById(id).orElseThrow(()->new ResourcesNotFound("Category does not exists with id: "+id));

        return mapToDTO(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new ResourcesNotFound("The Category with id: "+id+" Does not exists");
        }
        categoryRepository.deleteById(id);
    }


    //mapping helpers

    private CategoryDTO mapToDTO(Categories category){
        CategoryDTO dto=new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    private Categories mapToEntity(CategoryDTO dto){
        Categories category=new Categories();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
