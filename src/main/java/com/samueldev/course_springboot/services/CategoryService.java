package com.samueldev.course_springboot.services;

import com.samueldev.course_springboot.entities.Category;
import com.samueldev.course_springboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() { return repository.findAll();}

    public Category FindById(Long id) {
        Optional<Category> obj = repository.findById(id);
        return obj.get();
    }
}
